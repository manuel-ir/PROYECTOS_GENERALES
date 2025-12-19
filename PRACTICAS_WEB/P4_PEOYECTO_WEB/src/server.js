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

// Middleware para procesar los datos enviados por POST en el cuerpo de la petición
app.use(express.urlencoded({ extended: true }));

// Middleware para servir automáticamente el contenido de la carpeta public
app.use(express.static(rutaPublic));

// INICIO MODIFICACIONES UD2_AC7 

// Ruta POST para recibir y validar los datos del Login
app.post('/login', (req, res) => {
    // Captura de datos del formulario
    const email = req.body.email;
    const password = req.body.password;

    console.log("Datos login recibidos:", req.body);

    // Validación 1: Comprobar que llegan los datos
    if (!email || !password) {
        res.send("Faltan datos en el formulario (email o contraseña).");
        return;
    }

    // Validación 2: Comprobar credenciales (Simulación)
    // Se usan valores fijos para simular la base de datos
    const usuarioCorrecto = "manuel.infantes.rodriguez.al@iespoligonosur.org";
    const passwordCorrecta = "123456789";

    if (email === usuarioCorrecto && password === passwordCorrecta) {
        res.send("<h1>Bienvenido</h1><p>Has iniciado sesión correctamente.</p><a href='/reserva.html'>Ir a Reservas</a>");
    } else {
        res.send("<h1>Error</h1><p>Credenciales incorrectas.</p><a href='/login.html'>Volver a intentar</a>");
    }
});

// Ruta POST para recibir y validar los datos de la Reserva
app.post('/reserva', (req, res) => {
    // Captura de datos del formulario
    const evento = req.body.evento;
    const entradas = req.body.entradas;
    const extras = req.body.extras; // Puede ser undefined, un string o un array

    console.log("Datos reserva recibidos:", req.body);

    // Validación 1: Campos obligatorios
    if (!evento || !entradas) {
        res.send("Faltan datos obligatorios en la reserva (evento o número de entradas).");
        return;
    }

    // Validación 2: Lógica de negocio (número de entradas positivo)
    if (Number(entradas) <= 0) {
        res.send("El número de entradas debe ser mayor que cero.");
        return;
    }

    // Validación 3 (Ampliación): Lógica condicional con los extras
    // Si se elige el extra con valor "50" (Backstage), se exigen mínimo 2 entradas
    // Se convierte a string para asegurar que podemos buscar el valor
    let extrasTexto = String(extras); 
    
    if (extrasTexto.includes("50") && Number(entradas) < 2) {
        res.send("Error: Para acceder al Backstage debes reservar al menos 2 entradas.");
        return;
    }

    // Respuesta de éxito si todo es correcto
    res.send("<h1>Reserva procesada</h1><p>Los datos de la reserva son correctos y han sido recibidos en el servidor.</p><a href='/index.html'>Volver al inicio</a>");
});

// FIN MODIFICACIONES UD2_AC7 

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