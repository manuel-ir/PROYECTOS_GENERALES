import express from 'express';
import session from 'express-session';
import path from 'path';
import { fileURLToPath } from 'url';
import Reserva from './models/Reserva.js';
import { leerReservas, guardarReservas } from './services/reservasService.js';
import { leerUsuarios } from './services/usuariosService.js';
import { requiereAutenticacion } from './middlewares/authMiddleware.js';

const app = express();
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

// Carga de datos iniciales
console.log('=== CARGA DE DATOS INICIALES ===');
const reservas = leerReservas();
console.log(`Reservas cargadas: ${reservas.length}`);
console.log('===============================');

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
app.post('/reserva', requiereAutenticacion, (req, res) => {
    const evento = req.body.evento;
    const entradas = req.body.entradas;
    const extras = req.body.extras;
    const fecha = req.body.fecha || '';
    const comentarios = req.body.comentarios || '';

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

    const reservasActuales = leerReservas();
    const nuevaReserva = new Reserva(fecha, evento, entradas, extras, comentarios);
    reservasActuales.push(nuevaReserva);
    guardarReservas(reservasActuales);

    console.log(`Reserva guardada. Total de reservas: ${reservasActuales.length}`);

    res.send(`<h1>Reserva procesada</h1><p>Reserva guardada correctamente. Total de reservas: ${reservasActuales.length}</p><a href="/index.html">Volver al inicio</a>`);
});

// Ruta GET para ver todas las reservas
app.get('/ver-reservas', (req, res) => {
    const reservas = leerReservas();

    if (reservas.length === 0) {
        res.send('<h1>Reservas</h1><p>No hay ninguna reserva guardada.</p><a href="/index.html">Volver</a>');
        return;
    }

    let html = '<h1>Reservas Guardadas</h1>';
    html += '<p>Total: ' + reservas.length + ' reserva(s)</p>';
    html += '<ul>';

    reservas.forEach(reserva => {
        html += '<li>';
        html += '<strong>ID:</strong> ' + reserva.id + ' | ';
        html += '<strong>Evento:</strong> ' + reserva.tipo + ' | ';
        html += '<strong>Entradas:</strong> ' + reserva.unidades;
        html += '</li>';
    });

    html += '</ul>';
    html += '<a href="/index.html">Volver al inicio</a>';

    res.send(html);
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