import express from 'express';
import session from 'express-session';
import path from 'path';
import { fileURLToPath } from 'url';
import Reserva from './models/Reserva.js';
import { guardarReserva, obtenerReservasPorUsuario, actualizarReserva, eliminarReserva } from './services/reservasService.js';
import { leerUsuarios } from './services/usuariosService.js';
import { requiereAutenticacion } from './middlewares/authMiddleware.js';
import { comprobarConexion } from './config/db.js';

const app = express();

comprobarConexion();
const PORT = 3000;

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const rutaPublic = path.join(__dirname, '../public');

// Configuración del middleware de sesión
app.use(session({
    secret: 'mi_clave_secreta',
    resave: false,
    saveUninitialized: false
}));

// Middleware para procesar datos de formularios
app.use(express.urlencoded({ extended: true }));

// Servir archivos estáticos
app.use(express.static(rutaPublic));

// Ruta POST para login
app.post('/login', (req, res) => {
    const email = req.body.email;
    const password = req.body.password;

    console.log('Datos login recibidos:', req.body);

    if (!email || !password) {
        res.send('Faltan datos en el formulario (email o contraseña).');
        return;
    }

    const usuarios = leerUsuarios();

    const usuarioEncontrado = usuarios.find(
        u => u.email === email && u.password === password
    );

    if (usuarioEncontrado) {
        req.session.autenticado = true;
        req.session.usuario = email;
        res.send('<h1>Bienvenido</h1><p>Has iniciado sesión correctamente.</p><a href="/reserva.html">Ir a Reservas</a>');
    } else {
        res.send('<h1>Error</h1><p>Credenciales incorrectas.</p><a href="/login.html">Volver a intentar</a>');
    }
});

// Ruta POST para crear reserva (protegida)
app.post('/reserva', requiereAutenticacion, async (req, res) => {
    try {
        const evento = req.body.evento;
        const entradas = req.body.entradas;
        const extras = req.body.extras;
        const fecha = req.body.fecha || '';
        const precio = req.body.precio || 0;

        console.log('Datos reserva recibidos:', req.body);

        if (!evento || !entradas) {
            res.send('Faltan datos obligatorios en la reserva (evento o número de entradas).');
            return;
        }

        if (Number(entradas) <= 0) {
            res.send('El número de entradas debe ser mayor que cero.');
            return;
        }

        let extrasTexto = String(extras);

        if (extrasTexto.includes('50') && Number(entradas) < 2) {
            res.send('Error: Para acceder al Backstage debes reservar al menos 2 entradas.');
            return;
        }

        const usuario = req.session.usuario;
        const nuevaReserva = new Reserva(fecha, evento, entradas, precio, usuario);

        const idGenerado = await guardarReserva(nuevaReserva);

        console.log(`Reserva guardada con ID: ${idGenerado}`);

        res.send(`
            <h1>Reserva procesada</h1>
            <p>Reserva guardada correctamente en MySQL.</p>
            <p>Identificador de la reserva: ${idGenerado}</p>
            <a href="/index.html">Volver al inicio</a>
        `);
    } catch (error) {
        console.error('Error al guardar la reserva:', error);
        res.status(500).send('Error al guardar la reserva en la base de datos.');
    }
});

// Ruta GET para consultar reservas del usuario autenticado
app.get('/reservas', requiereAutenticacion, async (req, res) => {
    try {
        const usuario = req.session.usuario;
        const reservas = await obtenerReservasPorUsuario(usuario);
        res.json(reservas);
    } catch (error) {
        console.error('Error al consultar reservas:', error);
        res.status(500).send('Error al consultar las reservas.');
    }
});

// Ruta POST para actualizar una reserva
app.post('/reservas/actualizar', requiereAutenticacion, async (req, res) => {
    try {
        const { id, fecha, tipo, unidades, precio } = req.body;

        if (!id || !fecha || !tipo || !unidades || !precio) {
            return res.status(400).send('Faltan datos para actualizar la reserva.');
        }

        const usuario = req.session.usuario;
        const reservaActualizada = new Reserva(fecha, tipo, unidades, precio, usuario);

        const filasModificadas = await actualizarReserva(id, reservaActualizada, usuario);

        if (filasModificadas === 0) {
            return res.status(404).send('No se ha actualizado ninguna reserva. Puede que no exista o que no pertenezca al usuario autenticado.');
        }

        res.send('Reserva actualizada correctamente.');
    } catch (error) {
        console.error('Error al actualizar reserva:', error);
        res.status(500).send('Error al actualizar la reserva.');
    }
});

// Ruta POST para eliminar una reserva
app.post('/reservas/eliminar', requiereAutenticacion, async (req, res) => {
    try {
        const { id } = req.body;

        if (!id) {
            return res.status(400).send('Falta el id de la reserva que se quiere eliminar.');
        }

        const usuario = req.session.usuario;
        const filasEliminadas = await eliminarReserva(id, usuario);

        if (filasEliminadas === 0) {
            return res.status(404).send('No se ha eliminado ninguna reserva. Puede que no exista o que no pertenezca al usuario autenticado.');
        }

        res.send('Reserva eliminada correctamente.');
    } catch (error) {
        console.error('Error al eliminar reserva:', error);
        res.status(500).send('Error al eliminar la reserva.');
    }
});

// Ruta para cerrar sesión
app.get('/logout', (req, res) => {
    console.log('Sesión cerrada por:', req.session.usuario || 'usuario desconocido');
    req.session.destroy((error) => {
        if (error) {
            console.error('Error al cerrar la sesión:', error);
            return res.status(500).send('Error al cerrar la sesión');
        }
        res.send('<h1>Sesión cerrada</h1><p>Has cerrado sesión correctamente.</p><a href="/index.html">Volver al inicio</a>');
    });
});

// Ruta principal
app.get('/', (req, res) => {
    res.send('<h1>Servidor funcionando</h1><p>Accede a tus archivos estáticos (ej: /login.html).</p>');
});

// Ruta de prueba
app.get('/saludo', (req, res) => {
    res.send('Hola. Esta es la ruta de prueba /saludo funcionando correctamente.');
});

// Manejo de rutas no encontradas
app.use((req, res) => {
    res.status(404).send('<h1>Error 404</h1><p>Página no encontrada en el servidor.</p>');
});

// Iniciar el servidor
app.listen(PORT, () => {
    console.log(`Servidor escuchando en http://localhost:${PORT}`);
    console.log(`Carpeta de archivos estáticos: ${rutaPublic}`);
});