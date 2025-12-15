import express from 'express';
import path from 'path';
import { fileURLToPath } from 'url';

const app = express();
const PORT = 3000;

// Configuración de variables de entorno para manejo de rutas en módulos ES
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Definición de la ruta absoluta a la carpeta de archivos estáticos
const rutaPublic = path.join(__dirname, '../public');

// Esto permite leer los datos que vienen en el 'body' de la petición
app.use(express.urlencoded({ extended: true }));

// Middleware para servir automáticamente el contenido de la carpeta public
app.use(express.static(rutaPublic));

// Aquí llegan los datos cuando pulsas "Iniciar Sesión" en el formulario
app.post('/login', (req, res) => {
    console.log("Datos recibidos en el servidor:");
    console.log(req.body); // Muestra el objeto con email, password, etc.
    
    // Respuesta al navegador
    res.send("Datos recibidos correctamente. Mira la terminal de VS Code para ver el objeto.");
});

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