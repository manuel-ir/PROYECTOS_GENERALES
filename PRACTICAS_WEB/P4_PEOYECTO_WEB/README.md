# UD2_AC5 - Servir contenido estático con Express

**Alumno:** Manuel Infantes Rodríguez
**Módulo:** Desarrollo Web en Entorno Servidor
**Centro:** IES Polígono Sur

---

## 1. Versiones Utilizadas

* **Node.js:** v24.11.1
* **Express:** v5.1.0

---

## 2. Capturas de Pantalla

A continuación se muestra el funcionamiento del servidor entregando los archivos estáticos desde la carpeta `public`.

### Página de Inicio (Index)
![Página de Inicio](./capturas/CapIndex.png)

### Página de Login (Modo Claro y Oscuro)
![Login Modo Claro](./capturas/CapLoginLightMode.png)
![Login Modo Oscuro](./capturas/CapLoginDarkMode.png)

### Página de Reserva
![Página de Reserva](./capturas/CapReserva.png)

### Página de Resumen
![Página de Resumen](./capturas/CapResumen.png)

### Ruta de Error (404)
Al intentar acceder a una ruta inexistente (ej: `/indexx`), el servidor responde con el error genérico configurado:
![Error 404](./capturas/CapError404.png)

---

## 3. Funcionalidades e Interactividad (JavaScript)

El archivo `script.js` gestiona la lógica del cliente implementando las siguientes funcionalidades y restricciones:

* **Gestión de Sesión y Seguridad:**
    * Simulación de sesión mediante `localStorage`. Si el usuario no está logueado, se bloquea el acceso a las páginas protegidas (`reserva.html` y `resumen.html`) redirigiendo automáticamente al login.
    * Botón de "Cerrar Sesión" que limpia el almacenamiento y redirige al inicio.

* **Validaciones de Formulario (Login):**
    * **Email:** Se verifica que el campo contenga el carácter `@`.
    * **Contraseña:** Se exige una longitud mínima de **9 caracteres**.
    * Control de errores visual mediante mensajes en el DOM sin recargar la página.

* **Lógica de Reserva Dinámica:**
    * **Cálculo en tiempo real:** El precio total se actualiza automáticamente al cambiar el evento, el número de entradas o seleccionar extras (checkboxes), usando eventos `change` e `input`.
    * **Contador de caracteres:** El área de comentarios incluye un contador dinámico que limita el texto a **200 caracteres** y muestra el progreso visualmente (ej: 45/200).

* **Persistencia de Datos y Modo Oscuro:**
    * **Modo Oscuro:** La preferencia del usuario se guarda en `localStorage` para mantenerse activa al navegar entre las distintas páginas HTML.
    * **Resumen de Reserva:** Los datos del formulario de reserva se serializan en un objeto JSON y se guardan localmente para ser recuperados y mostrados dinámicamente en la página `resumen.html`.

---

## 4. ¿Qué he aprendido en esta práctica?

En esta actividad he aprendido a integrar el frontend con un backend básico en Node.js:

1.  **Servir contenido estático:** He aprendido a utilizar el middleware `express.static()` para que el servidor entregue automáticamente archivos HTML, CSS, JS e imágenes sin necesidad de crear una ruta manual para cada archivo.
2.  **Manejo de rutas en ESM:** Al usar `"type": "module"`, he aprendido que la variable `__dirname` no existe por defecto, por lo que he tenido que reconstruirla utilizando `fileURLToPath` y `path.dirname` para obtener la ubicación real del servidor.
3.  **Rutas absolutas seguras:** He comprendido la importancia de usar `path.join()` para enlazar la carpeta `public` de forma segura, independientemente del sistema operativo en el que se ejecute el proyecto.
4.  **Middleware de Error:** He configurado un `app.use()` al final del código para capturar cualquier petición que no coincida con los archivos estáticos ni con las rutas definidas, devolviendo un error 404.

---

## 5. Dificultades encontradas y soluciones

Durante el desarrollo me he encontrado con varios problemas que he logrado resolver:

* **Estructura de carpetas incorrecta:** Al principio, mis carpetas `css`, `js` e `img` estaban fuera de la carpeta `public`. Esto provocaba que el servidor no las encontrara y la web se viera sin estilos.
    * *Solución:* He movido todas esas carpetas **dentro** de `public`, ya que Express solo tiene acceso a lo que hay dentro de esa carpeta estática.
* **Rutas relativas en HTML:** Mis archivos HTML tenían enlaces del tipo `../css/style.css`, lo cual fallaba porque para el servidor la raíz ya es la carpeta `public`.
    * *Solución:* He corregido todas las rutas en los HTML para que empiecen desde la raíz (ej: `/css/style.css`), eliminando los `../`.
* **Conflicto de módulos (require vs import):** Al principio intenté usar `require` (CommonJS) mientras el proyecto estaba configurado como módulo (ESM).
    * *Solución:* Actualicé el código de `server.js` para usar la sintaxis moderna `import ... from ...` y configuré las variables de ruta correctamente.