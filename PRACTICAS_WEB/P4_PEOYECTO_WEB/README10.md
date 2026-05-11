# UD2_AC10 - Sesiones y mantenimiento de estado

**Autor:** Manuel Infantes Rodríguez
**Fecha:** Mayo 2026
**Módulo:** Programación Web (2º DAM)

---

## 1. Versiones usadas

- **Node.js:** v24.15.0
- **Express:** 5.2.1

---

## 2. Mecanismo de sesión implementado

Se ha implementado un sistema de sesiones utilizando la librería `express-session`. Este middleware permite que el servidor reconozca al mismo usuario en distintas peticiones HTTP, manteniendo información persistente entre ellas.

El funcionamiento es el siguiente:
- Cuando un usuario inicia sesión correctamente, el servidor guarda información en `req.session`
- En cada petición posterior, el middleware de sesión recupera automáticamente esa información
- El servidor puede consultar si el usuario está autenticado o no

La configuración básica incluye una clave secreta para firmar las sesiones, y opciones para evitar escrituras innecesarias.

---

## 3. Información almacenada en req.session

Se guardan dos propiedades en la sesión:

- **req.session.autenticado**: Indicador booleano que marca si el usuario ha iniciado sesión correctamente. Su valor es `true` cuando las credenciales son correctas.
- **req.session.usuario**: Almacena el email del usuario que ha iniciado sesión. Permite identificar al usuario en cada petición.

Estas dos propiedades son suficientes para saber si un usuario está autenticado y quién es. No se almacena información sensible como la contraseña.

---

## 4. Protección de la ruta /reserva

La ruta `/reserva` está protegida mediante un middleware de autenticación. Este middleware se ejecuta antes de que la petición llegue a la lógica de la reserva y comprueba si el usuario está autenticado.

Si el usuario no tiene `req.session.autenticado` con valor `true`, el middleware devuelve un error 401 y bloquea el acceso. Si está autenticado, llama a `next()` y la petición continúa hacia la ruta.

El middleware está definido en un archivo separado (`authMiddleware.js`) para mantener la separación de responsabilidades y poder reutilizarse en otras rutas si fuera necesario.

---

## 5. Proceso de logout (/logout)

Al ejecutar la ruta `/logout` ocurre lo siguiente:

1. El servidor destruye la sesión con `req.session.destroy()`
2. Se elimina toda la información almacenada en la sesión del servidor
3. El identificador de sesión deja de estar vinculado a datos válidos
4. El usuario ya no puede acceder a rutas protegidas
5. Se muestra un mensaje de confirmación en el navegador

El logout no vuelve a pedir credenciales, simplemente elimina el estado de autenticación.

---

## 6. Capturas de pantalla

### Instalación de express-session
![Instalación](./public/img/CapturasAC10/AC10%20-%20Instalacion%20express-session.png)

### Imports y configuración de sesión en server.js
![Imports y session](./public/img/CapturasAC10/AC10%20-%20Imports%20y%20configuracion%20session.png)

### Inicio del servidor
![Inicio Servidor](./public/img/CapturasAC10/AC10%20-%20Inicio%20Servidor.png)

### Middleware de autenticación
![authMiddleware](./public/img/CapturasAC10/AC10%20-%20authMiddleware.png)

### Login con req.session
![Login](./public/img/CapturasAC10/AC10%20-%20Login%20con%20req.session.png)

### Ruta /reserva protegida
![Ruta protegida](./public/img/CapturasAC10/AC10%20-%20Ruta%20reserva%20protegida.png)

### Intento de acceder a reserva sin login
![Acceso sin login](./public/img/CapturasAC10/AC10%20-%20Acceso%20a%20Reserva%20sin%20Login.png)

### Intento de reserva sin login (navegador)
![Intento reserva](./public/img/CapturasAC10/AC10%20-%20Intento%20de%20Reserva%20sin%20Login.png)

### Reserva realizada tras login
![Reserva tras login](./public/img/CapturasAC10/AC10%20-%20Reserva%20tras%20Login.png)

### Ruta /logout
![Ruta logout](./public/img/CapturasAC10/AC10%20-%20Ruta%20logout.png)

### Logout ejecutado (consola)
![Logout](./public/img/CapturasAC10/AC10%20-%20Logout.png)

---

## 7. Diferencia entre validación puntual y autenticación basada en sesión

### Validación puntual de formulario
Ocurre una sola vez, cuando el usuario envía el formulario de login. El servidor comprueba si las credenciales coinciden con los datos almacenados. Si son correctas, el usuario recibe una respuesta positiva, pero en la siguiente petición el servidor ya no sabe quién es.

### Autenticación basada en sesión
Una vez validadas las credenciales, la información del usuario se guarda en la sesión. En cada petición posterior, el servidor puede consultar si el usuario está autenticado sin necesidad de volver a pedir credenciales. La sesión mantiene el estado hasta que se destruya con logout o expire.

La autenticación mediante sesión permite proteger rutas de forma continua, no solo en el momento del login.

---

## 8. Por qué crear un middleware propio

Se ha creado un middleware propio (`authMiddleware.js`) en lugar de hacer la comprobación directamente dentro de cada ruta por las siguientes ventajas:

- **Reutilización:** El mismo middleware puede aplicarse a múltiples rutas sin duplicar código.
- **Separación de responsabilidades:** La lógica de autenticación está separada de la lógica de negocio.
- **Mantenimiento más fácil:** Si hay que cambiar cómo se comprueba la autenticación, solo se modifica un archivo.
- **Código más limpio:** Las rutas se centran en su funcionalidad principal sin mezclas de lógica.

El middleware recibe los parámetros `req`, `res` y `next`. Si el usuario no está autenticado, devuelve un error 401. Si lo está, llama a `next()` para continuar con la ruta.

---

## 9. Dificultades técnicas encontradas

### Dificultad 1: El logout no funcionaba correctamente
**Problema:** Al hacer clic en el botón de cerrar sesión, no se destruía la sesión en el servidor.

**Solución:** Se modificó el código JavaScript del cliente para que hiciera una llamada fetch a la ruta `/logout` antes de redirigir. Así el servidor destruye la sesión y luego el navegador redirige a login.

### Dificultad 2: No había forma de demostrar el logout en una captura estática
**Problema:** El logout ocurre en el backend y la transición es rápida, no se puede capturar en una imagen.

**Solución:** Se añadió un mensaje en la consola del servidor (`console.log('Sesión cerrada por: ...')`) que aparece cuando se ejecuta el logout. Así se puede capturar en la misma pantalla tanto el código como el resultado en consola.

### Dificultad 3: Falta de mensaje de bienvenida tras logout
**Problema:** Al redirigir a login, no se limpiaba la variable de sesión del frontend.

**Solución:** Se añadió `localStorage.clear()` antes de la redirección para limpiar cualquier dato almacenado en el navegador.