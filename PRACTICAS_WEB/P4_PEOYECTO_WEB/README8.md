# UD2_AC8 - Persistencia con JSON

**Autor:** Manuel Infantes Rodríguez
**Fecha:** Mayo 2026
**Módulo:** Programación Web (2º DAM)

---

## 1. Versiones usadas

- **Node.js:** v24.15.0
- **Express:** 5.2.1

---

## 2. Descripción de la actividad

El objetivo de esta práctica es implementar persistencia de datos utilizando archivos JSON en el servidor Express. Los datos de usuarios y reservas ya no se almacenan solo en memoria (variables), sino que se guardan en archivos del disco duro, permitiendo que sobrevivan al reinicio del servidor.

---

## 3. Explicación del flujo de una reserva

Cuando un usuario envía una reserva, el servidor realiza los siguientes pasos:

### Paso 1: Recepción de datos
El formulario de `reserva.html` envía los datos mediante POST a `/reserva`. El servidor recibe los datos del cuerpo de la petición gracias al middleware `express.urlencoded()`.

### Paso 2: Validación
El servidor valida los datos:
- Comprueba que existen evento y número de entradas
- Verifica que el número de entradas es mayor que cero
- Aplica la regla de negocio: si se selecciona backstage, se requieren al menos 2 entradas

### Paso 3: Lectura del archivo JSON
El servidor lee el archivo `reservas.json` usando la función `leerJSON()`:
```javascript
const reservas = leerJSON(rutaReservas);
```
Esto convierte el contenido del archivo JSON en un array de JavaScript.

### Paso 4: Creación del objeto reserva
Se crea un nuevo objeto con los datos de la reserva:
```javascript
const nuevaReserva = {
    id: Date.now(),
    fecha: fecha,
    evento: evento,
    entradas: Number(entradas),
    extras: extras,
    comentarios: comentarios,
    fechaCreacion: new Date().toISOString()
};
```

### Paso 5: Guardado en el array
La nueva reserva se añade al array existente:
```javascript
reservas.push(nuevaReserva);
```

### Paso 6: Escritura en el archivo JSON
El array actualizado se guarda en el archivo usando `escribirJSON()`:
```javascript
escribirJSON(rutaReservas, reservas);
```
Esto convierte el array de JavaScript a texto JSON y lo escribe en el disco.

### Paso 7: Confirmación
El servidor envía una respuesta HTML confirmando que la reserva fue guardada, incluyendo el número total de reservas.

---

## 4. Estructura de archivos

La carpeta `data/` se encuentra dentro de `src/`, contiene los archivos `usuarios.json` y `reservas.json`.

---

## 5. Capturas de pantalla

### 5.1. Inicio del servidor y login

**Inicio del servidor:**
![Inicio Servidor](./public/img/CapturasAC8/AC8%20-%201.%20InicioServidor.png)

**Login correcto:**
![Login Correcto](./public/img/CapturasAC8/AC8%20-%202.%20LoginCorrecto.png)

**Login incorrecto:**
![Login Incorrecto](./public/img/CapturasAC8/AC8%20-%203.%20LoginIncorrrecto.png)

---

### 5.2. Realización de una reserva

**Formulario de reserva:**
![Formulario Reserva](./public/img/CapturasAC8/AC8%20-%204.0.%20Reserva.png)

**Reserva procesada correctamente:**
![Reserva Correcta](./public/img/CapturasAC8/AC8%20-%204.1.%20ReservaCorrrecta.png)

---

### 5.3. Verificación de datos persistidos

**Ver todas las reservas:**
![Ver Reservas](./public/img/CapturasAC8/AC8%20-%206.%20Ver%20Reservas.png)

**Archivos JSON con datos guardados:**
![JSON Usuarios y Reservas](./public/img/CapturasAC8/AC8%20-%205.%20JSON%20Usuarios%20y%20Reservas.png)

---

### 5.4. Confirmación en consola

**Mensajes del servidor al guardar reservas:**
![Confirmación Consola](./public/img/AC8%20-%207.%20Confirmación%20Consola.png)

---

### 5.5. Código modificado en server.js

**Parte 1: Imports, rutas a archivos JSON y funciones auxiliares (líneas 1-40)**
![Modificaciones Server.js 1](./public/img/CapturasAC8/AC8%20-%20MOD%20Server.js%201.png)

**Parte 2: Carga de datos iniciales (líneas 42-48)**
![Modificaciones Server.js 2](./public/img/CapturasAC8/AC8%20-%20MOD%20Server.js%202.png)

**Parte 3: Ruta POST /login (líneas 58-83)**
![Modificaciones Server.js 3](./public/img/CapturasAC8/AC8%20-%20MOD%20Server.js%203.png)

**Parte 4: Ruta POST /reserva con persistencia (líneas 87-134)**
![Modificaciones Server.js 4](./public/img/CapturasAC8/AC8%20-%20MOD%20Server.js%204.png)

---

## 6. Qué he aprendido

### 6.1. Persistencia de datos en servidor
- Los datos almacenados en variables JavaScript se pierden al reiniciar el servidor
- Los archivos JSON permiten guardar datos de forma permanente en el disco duro
- Al iniciar el servidor, los datos se cargan desde los archivos JSON

### 6.2. Uso del módulo `fs` de Node.js
- `fs.readFileSync()`: Lee archivos de forma síncrona
- `fs.writeFileSync()`: Escribe archivos de forma síncrona
- Estas funciones devuelven el contenido como texto, por lo que se debe usar `JSON.parse()` para convertirlo

### 6.3. Conversión entre JSON y JavaScript
- `JSON.parse(texto)`: Convierte texto JSON a objetos JavaScript
- `JSON.stringify(objeto, null, 2)`: Convierte objetos JavaScript a texto JSON formateado (el segundo parámetro null es para funciones de reemplazo, el tercero "2" es para indentación de 2 espacios)

### 6.4. Estructura de datos
- Cada reserva tiene un identificador único (ID) generado con `Date.now()`
- Se añaden marcas de tiempo para saber cuándo se creó cada reserva

### 6.5. Rutas dinámicas en Express
- `app.post('/reserva')`: Recibe datos de formularios
- `app.get('/ver-reservas')`: Muestra todas las reservas guardadas

---

## 7. Errores y dificultades

### Error 1: JSON inválido
**Problema:** El servidor mostraba error al intentar leer los archivos JSON.

**Causa:** Los archivos contenían errores de formato (comas mal colocadas, comillas sin cerrar, etc.).

**Solución:** Revisar el formato del archivo JSON y asegurarse de que es válido. Usar un validador JSON online para verificar.

### Error 2: Datos no persisten después de reiniciar
**Problema:** Al reiniciar el servidor, las reservas desaparecían.

**Causa:** No se estaba llamando a `escribirJSON()` después de añadir la nueva reserva al array.

**Solución:** Asegurarse de que después de `reservas.push(nuevaReserva)` se llama a `escribirJSON(rutaReservas, reservas)` para guardar los cambios en el archivo.

### Error 3: Reservas se duplican
**Problema:** Al hacer una reserva, aparecían múltiples veces en el archivo JSON.

**Causa:** Se leía el archivo dentro de la ruta POST, pero se escribía sin verificar el estado actual.

**Solución:** Asegurarse de que `leerJSON()` se llama al inicio de cada operación para obtener los datos actualizados antes de modificar.