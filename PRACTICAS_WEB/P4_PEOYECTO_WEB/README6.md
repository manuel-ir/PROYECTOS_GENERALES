# UD2_AC6 Enviar datos del formulario al servidor mediante POST

**Objetivo** Integrar el frontend con el backend enviando datos desde un formulario HTML hacia el servidor Express mediante el método POST

## 1. Cambios Realizados en el Código

Para lograr la comunicación entre cliente y servidor he modificado tres archivos clave

### A. Preparación del Formulario login.html
He configurado el formulario para cumplir los requisitos de envío de datos
* **Método** method="POST" para enviar datos en el cuerpo de la petición
* **Acción** action="/login" la ruta del servidor que procesará los datos
* **Atributos Name** He añadido name="email" name="password" y name="recordar" a los inputs para que Express pueda leerlos

![Código Login Modificado](./capturas/CapLoginModificadoPOST.png)

### B. Desactivación de Lógica Cliente script.js
He comentado temporalmente la función event.preventDefault() en el script del cliente. Esto es necesario porque mi validación anterior bloqueaba el envío nativo del formulario impidiendo que los datos llegaran al servidor

![Código Script Desactivado](./capturas/CapScriptLogin.png)

### C. Configuración del Servidor server.js
He implementado dos elementos fundamentales en el backend
1. **Middleware** Activé app.use(express.urlencoded({ extended: true })) antes de las rutas. Esto permite a Express interpretar los datos que llegan en el body de la petición
2. **Ruta POST** Creé la ruta app.post('/login', ...) que recibe la petición muestra el objeto req.body en la consola del servidor y envía una respuesta de confirmación al navegador

![Código Server JS](./capturas/CapServerJsModificado.png)

## 2. Pruebas de Funcionamiento

A continuación documento las pruebas realizadas para verificar el flujo completo Cliente Servidor Cliente

### Captura 1 Formulario antes del envío
Estado inicial del formulario en el navegador con datos de prueba
![Formulario Login](./capturas/CapLoginPOST.png)

### Captura 2 Recepción de datos en el Servidor
Consola de Visual Studio Code mostrando el objeto req.body con los datos recibidos email password y checkbox
![Consola Servidor](./capturas/CapLoginRespuestaConsola.png)

### Captura 3 Respuesta al Cliente
El navegador muestra el mensaje de texto plano enviado desde el servidor confirmando la recepción
![Respuesta Navegador](./capturas/CapLoginRespuesta.png)

## 3. Aprendizaje y Dificultades

### ¿Qué he aprendido?
* **Diferencia GET vs POST** He aprendido que mientras GET envía datos en la URL inseguro para contraseñas POST los envía ocultos en el cuerpo de la petición
* **Importancia del Middleware** Entendí que Express no lee el cuerpo de las peticiones automáticamente es imprescindible activar express.urlencoded para que req.body no esté vacío
* **Atributo name** Descubrí que el servidor ignora los id de HTML y solo reconoce los valores mediante el atributo name

### Dificultades Resueltas
* **Conflicto con Script del Cliente** Al principio el servidor no recibía nada porque mi antiguo código JavaScript script.js interceptaba el botón de Enviar
* **Solución** Comenté el bloque de validación en script.js para permitir el comportamiento por defecto del formulario HTML