import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';
import fs from 'fs';

const app = express();
const PORT = 3000;

// Configuración de variables de entorno para manejo de rutas en módulos ES
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Definición de la ruta absoluta a la carpeta de archivos estáticos
const rutaPublic = path.join(__dirname, '../public');

// INICIO MODIFICACIONES UD2_AC8 - PERSISTENCIA CON JSON

// Rutas a los archivos de datos
const rutaUsuarios = path.join(__dirname, './data/usuarios.json');
const rutaReservas = path.join(__dirname, './data/reservas.json');

// Función para leer datos de un archivo JSON
function leerJSON(ruta) {
    try {
        const datos = fs.readFileSync(ruta, 'utf8');
        return JSON.parse(datos);
    } catch (error) {
        console.error('Error al leer archivo JSON:', error);
        return [];
    }
}

// Función para escribir datos en un archivo JSON
function escribirJSON(ruta, datos) {
    try {
        fs.writeFileSync(ruta, JSON.stringify(datos, null, 2), 'utf8');
    } catch (error) {
        console.error('Error al escribir archivo JSON:', error);
    }
}

// Carga de datos iniciales al arrancar el servidor
console.log('=== CARGA DE DATOS INICIALES ===');
const usuarios = leerJSON(rutaUsuarios);
const reservas = leerJSON(rutaReservas);
console.log(`Usuarios cargados: ${usuarios.length}`);
console.log(`Reservas cargadas: ${reservas.length}`);
console.log('===============================');

// FIN MODIFICACIONES UD2_AC8

// Middleware para procesar los datos enviados por POST en el cuerpo de la petición
app.use(express.urlencoded({ extended: true }));

// Middleware para servir automáticamente el contenido de la carpeta public
app.use(express.static(rutaPublic));

// INICIO MODIFICACIONES UD2_AC8 - PERSISTENCIA CON JSON

// Ruta POST para recibir y validar los datos del Login
app.post('/login', (req, res) => {
    const email = req.body.email;
    const password = req.body.password;

    console.log("Datos login recibidos:", req.body);

    if (!email || !password) {
        res.send("Faltan datos en el formulario (email o contraseña).");
        return;
    }

    const usuarios = leerJSON(rutaUsuarios);

    const usuarioEncontrado = usuarios.find(
        u => u.email === email && u.password === password
    );

    if (usuarioEncontrado) {
        res.send("<h1>Bienvenido</h1><p>Has iniciado sesión correctamente.</p><a href='/reserva.html'>Ir a Reservas</a>");
    } else {
        res.send("<h1>Error</h1><p>Credenciales incorrectas.</p><a href='/login.html'>Volver a intentar</a>");
    }
});

// FIN MODIFICACIONES UD2_AC8

// INICIO MODIFICACIONES UD2_AC8 - RUTA RESERVA CON PERSISTENCIA
// Ruta POST para recibir y validar los datos de la Reserva
app.post('/reserva', (req, res) => {
    const evento = req.body.evento;
    const entradas = req.body.entradas;
    const extras = req.body.extras;
    const fecha = req.body.fecha || '';
    const comentarios = req.body.comentarios || '';

    console.log("Datos reserva recibidos:", req.body);

    if (!evento || !entradas) {
        res.send("Faltan datos obligatorios en la reserva (evento o número de entradas).");
        return;
    }

    if (Number(entradas) <= 0) {
        res.send("El número de entradas debe ser mayor que cero.");
        return;
    }

    let extrasTexto = String(extras);

    if (extrasTexto.includes("50") && Number(entradas) < 2) {
        res.send("Error: Para acceder al Backstage debes reservar al menos 2 entradas.");
        return;
    }

    const reservas = leerJSON(rutaReservas);

    const nuevaReserva = {
        id: Date.now(),
        fecha: fecha,
        evento: evento,
        entradas: Number(entradas),
        extras: extras,
        comentarios: comentarios,
        fechaCreacion: new Date().toISOString()
    };

    reservas.push(nuevaReserva);
    escribirJSON(rutaReservas, reservas);

    console.log(`Reserva guardada. Total de reservas: ${reservas.length}`);

    res.send(`<h1>Reserva procesada</h1><p>Reserva guardada correctamente. Total de reservas: ${reservas.length}</p><a href='/index.html'>Volver al inicio</a>`);
});
// FIN MODIFICACIONES UD2_AC8 

// INICIO MODIFICACIONES UD2_AC8 - RUTA PARA VER RESERVAS

// Ruta GET para ver todas las reservas guardadas
app.get('/ver-reservas', (req, res) => {
    const reservas = leerJSON(rutaReservas);

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
        html += '<strong>Evento:</strong> ' + reserva.evento + ' | ';
        html += '<strong>Entradas:</strong> ' + reserva.entradas;
        html += '</li>';
    });

    html += '</ul>';
    html += '<a href="/index.html">Volver al inicio</a>';

    res.send(html);
});

// FIN MODIFICACIONES UD2_AC8

// Ruta principal para confirmación de estado del servidor
app.get('/', (req, res) => {
    res.send('<h1>Servidor funcionando</h1><p>Accede a tus archivos estáticos (ej: /login.html).</p>');
});

// Ruta de prueba para verificar endpoints personalizados
app.get('/saludo', (req, res) => {
    res.send('Hola. Esta es la ruta de prueba /saludo funcionando correctamente.');
});

// Middleware genérico para capturar cualquier ruta no definida (Error 404)
app.use((req, res) => {
    res.status(404).send('<h1>Error 404</h1><p>Página no encontrada en el servidor.</p>');
});

// Inicio de la escucha del servidor en el puerto definido
app.listen(PORT, () => {
    console.log(`Servidor escuchando en http://localhost:${PORT}`);
    console.log(`Carpeta de archivos estáticos: ${rutaPublic}`);
});