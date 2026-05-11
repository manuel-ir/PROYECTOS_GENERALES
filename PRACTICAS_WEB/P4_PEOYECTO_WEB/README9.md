# UD2_AC9 - Organización backend y uso de clases

**Autor:** Manuel Infantes Rodríguez
**Fecha:** Mayo 2026
**Módulo:** Programación Web (2º DAM)

---

## 1. Versiones usadas

- **Node.js:** v24.15.0
- **Express:** 5.2.1

---

## 2. Organización del proyecto en AC8

En la actividad anterior (AC8), todo el código relacionado con las reservas estaba en un único archivo `server.js`. Esto incluía la configuración del servidor, las rutas, la lógica de validación, las funciones para leer y escribir archivos JSON, y la creación de objetos.

![Estructura AC8](./public/img/CapturasAC9/AC9%20-%20Estructura%20Proyecto%20AC8.png)

---

## 3. Organización del proyecto tras AC9

Se ha reorganizado el proyecto creando módulos separados para cada responsabilidad. La carpeta `data/` contiene los archivos JSON con los datos persistentes. La carpeta `models/` contiene la clase que representa una reserva. La carpeta `services/` contiene las funciones que acceden a los archivos JSON. El archivo `server.js` coordina todo sin acceder directamente al sistema de archivos.

![Estructura AC9](./public/img/CapturasAC9/AC9%20-%20Estructura%20Proyecto.png)

---

## 4. Responsabilidad de cada archivo

### server.js
Es el archivo principal que controla el flujo de la aplicación. Se encarga de configurar Express, registrar los middlewares y definir las rutas para recibir las peticiones de los usuarios.

### models/Reserva.js
Contiene la clase Reserva que representa los datos de una reserva. Su única función es almacenar la información que se le pasa al crear una nueva instancia.

### services/reservasService.js
Contiene las funciones `leerReservas()` y `guardarReservas()`. Estas funciones son las únicas que acceden al archivo JSON donde se guardan las reservas, utilizando el módulo `fs` de Node.js.

### services/usuariosService.js
Contiene la función `leerUsuarios()` que permite leer los datos de los usuarios desde su archivo JSON.

---

## 5. Flujo completo de una reserva

### Paso 1: El usuario envía el formulario
El usuario completa el formulario de reserva y lo envía. Los datos llegan al servidor mediante una petición POST a la ruta `/reserva`.

### Paso 2: server.js recibe los datos
El servidor extrae los datos del cuerpo de la petición (req.body): fecha, evento, entradas, extras y comentarios.

### Paso 3: Validación
Se comprueba que los campos obligatorios existan y se aplican las reglas de negocio, como exigir al menos 2 entradas si se selecciona el extra backstage.

### Paso 4: Se leen las reservas existentes
Se llama a la función `leerReservas()` del servicio para obtener el array actual de reservas desde el archivo JSON.

### Paso 5: Se crea el objeto Reserva
Se crea una nueva instancia de la clase Reserva con los datos recibidos del formulario.

### Paso 6: Se guarda la reserva
La nueva reserva se añade al array y se llama a `guardarReservas()` para escribir los cambios en el archivo JSON.

### Paso 7: Respuesta al usuario
El servidor envía una confirmación al navegador indicando que la reserva se ha guardado correctamente.

---

## 6. Capturas de pantalla

### Imports en server.js
![Imports](./public/img/CapturasAC9/AC9%20-%20Imports.png)

### Clase Reserva.js
![Clase Reserva](./public/img/CapturasAC9/AC9%20-%20Clase%20Reserva.js.png)

### Servicio de reservas
![reservasService](./public/img/CapturasAC9/AC9%20-%20reservasServices.png)

### Servicio de usuarios
![usuariosService](./public/img/CapturasAC9/AC9%20-%20usuariosService.png)

### Inicio del servidor
![Inicio Servidor](./public/img/CapturasAC9/AC9%20-%20Inicio%20Servidor%20y%20Reservas.png)

### Reserva guardada en JSON
![Reserva JSON](./public/img/CapturasAC9/AC9%20-%20Reserva%20JSON.png)

---

## 7. Ventajas de esta organización

- **Código más limpio:** El archivo server.js es más corto y fácil de leer.
- **Separación de responsabilidades:** Cada módulo tiene una función clara.
- **Mantenimiento más fácil:** Si hay que cambiar cómo se guardan los datos, solo se modifica el servicio correspondiente.
- **Reutilización:** Los servicios pueden usarse desde distintas partes del código.
- **Escalabilidad:** Si en el futuro se necesita una base de datos, solo hay que modificar los servicios, no todo el código.

---

## 8. Errores y dificultades

### Error 1: El precio total no se actualizaba
**Problema:** Al seleccionar evento o marcar extras, el precio total no cambiaba.

**Causa:** Faltaba definir la variable pageId al inicio del código, y el formulario estaba previniendo el envío con event.preventDefault().

**Solución:** Mover la definición de pageId al inicio del archivo y eliminar el event.preventDefault() para permitir el envío normal al servidor.

### Error 2: Los comentarios no se guardaban
**Problema:** El campo de comentarios no aparecía en las reservas guardadas.

**Causa:** La clase Reserva no tenía el parámetro comentarios en su constructor.

**Solución:** Añadir el parámetro comentarios al constructor de la clase y pasarlo al crear la nueva instancia.

### Error 3: Problemas con export e import
**Problema:** Errores al intentar importar las funciones de los servicios.

**Causa:** Confundir el uso de export default con export nombrado.

**Solución:** Recordar que para la clase Reserva se usa export default (sin llaves al importar) y para las funciones de los servicios se usa export nombrado (con llaves al importar).