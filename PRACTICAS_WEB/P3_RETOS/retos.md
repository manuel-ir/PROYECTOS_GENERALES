Aquí tienes la transcripción íntegra de los 10 retos, respetando el orden, la estructura de puntos y la terminología técnica de las imágenes:

---

## Reto 1 – ¡Hola, evento!

**- RETO:** Conseguir que dos botones hagan cosas distintas al hacer clic: uno muestra un mensaje, y el otro cambia el fondo de la página, detectando eventos con JavaScript y modificando elementos del DOM de forma básica.

**PASOS:**

1. Crea un documento HTML con:
a. Un botón que diga "**¡Hola, evento!**" (id="hola")
b. Otro botón que diga "**Cambiar fondo**" (id="cambiarFondo")
c. Un párrafo vacío con id="resultado"
2. Haz que al pulsar el botón de saludo, aparezca el texto: "¡Hola! Has hecho clic en el botón."
3. Haz que al pulsar el botón de fondo, cambie el color del fondo del body.
4. Con CSS, el cursor sobre los botones debe cambiar a forma de mano (cursor: pointer).

**AS PISTAS:**

* getElementById() o querySelector()
* .innerText o .textContent
* .style.backgroundColor
* addEventListener("click", ...)
* (CSS) selector de botón con cursor: pointer

---

## Reto 2 – Formulario vigilado

**- RETO:** Crear un formulario sencillo y comprobar que los datos introducidos son válidos antes de enviarlo.

**PASOS:**

1. Crea un formulario desde cero con estos campos:
Nombre (id="nombre"), Correo electrónico (id="email"), Teléfono (id="telefono")
2. Añade un botón para enviar el formulario.
3. Cuando el usuario envíe el formulario (usando el evento submit sobre el formulario, no click sobre el botón):
a. Comprueba que el nombre no esté vacío
b. Que el correo incluya una @
c. Que el teléfono tenga 9 números
4. Si hay algún error, muestra un alert() con un mensaje de aviso.
5. Si todo está bien, muestra: "Formulario enviado correctamente. ¡Gracias!"
6. Evita que el formulario se recargue automáticamente usando event.preventDefault().

**LAS PISTAS:**

* getElementById()
* .value
* .includes()
* .length
* alert()
* addEventListener("submit", function(event) { ... })
* event.preventDefault()

---

## Reto 3 – ¿Cuántos has escrito?

**RETO 3 – ¿Cuántos has escrito?**

**- RETO:** Contar en tiempo real cuántos caracteres escribe el usuario en un área de texto y mostrar el resultado.

**PASOS:**

1. Sobre el html del reto 2, crea un textarea con id="comentarios" para que el usuario escriba un comentario o instrucción con una longitud máxima de 50 caracteres.
2. Añade justo debajo un span vacío con id="contador".
3. Cada vez que el usuario escriba, borre o pegue algo, el número de caracteres escritos debe aparecer dentro del span, en formato "X / 50". (Ejemplo, si el usuario escribe "Hola", el contador debe mostrar: 4)

**AS PISTAS:**

* getElementById()
* .value
* .length
* .textContent
* Evento oninput

---

## Reto 4 – ¿Seguro que lo mandas?

**RETO 4 – ¿Seguro que lo mandas?**

**- RETO:** Añadir una confirmación antes de enviar el formulario, y personalizar el mensaje final con el nombre de quien lo envía.

**PASOS:**

1. Usa el mismo formulario que en el reto anterior. Asegúrate de que tiene:
* Un textarea con id="comentarios"
* Un campo de nombre con id="nombre"
* Todo dentro de un `<form id="formulario">`
* Un botón con id="enviar"


2. Cuando el usuario pulse el botón de enviar:
Usa confirm() para preguntar si está seguro de enviar el formulario.
* Si responde que sí, muestra: "¡Preparando tu desayuno, [nombre]!"
* Si responde que no, no debe pasar nada.



**AS PISTAS:**

* getElementById()
* .value
* alert()
* confirm()
* addEventListener("submit", function(event) { ... })
* event.preventDefault()

---

## Reto 5 – ¿Cuánto cuesta tu desayuno?

**Reto 5 – ¿Cuánto cuesta tu desayuno?**

**- RETO:** Calcular el precio total de un desayuno según lo que el usuario seleccione, y mostrarlo al pulsar un botón.

**PASOS:**

1. Debéis incorporar al código del reto 4 el HTML base que se os da, y añadir lo necesario:
* Un botón que diga "**Calcular precio**"
* Los atributos id en cada input (tipo, unidades, extras, botón y span)


2. Con JavaScript, al hacer clic en el botón:
* Detecta qué desayuno ha elegido el usuario y su precio.
* Multiplica ese precio por el número de unidades.
* Suma 1 € por cada extra seleccionado.
* Muestra el total en el `<span>`



**AS PISTAS:**

* getElementById(), querySelectorAll()
* .value, .checked
* parseFloat() o Number()
* .textContent
* addEventListener("click", ...)

---

## Reto 6 – Formulario más accesible

**Reto 6 – Formulario más accesible**

**- RETO:** Mejorar la accesibilidad del formulario para que el botón de envío solo esté disponible cuando los datos obligatorios sean correctos.

**PASOS:**
A partir del formulario del reto 5, realiza estos cambios:

* El botón de envío con id="enviar" debe comenzar desactivado (disabled).
* Crea una función llamada validar() que se encargue de comprobar si los datos están bien escritos.
* Esta función debe ejecutarse cada vez que el usuario escriba en los campos de nombre, email o teléfono.
* Solo cuando los tres campos estén correctamente rellenados a la vez, el botón debe activarse automáticamente. Si alguno de los datos es incorrecto, el botón debe permanecer desactivado.

**AS PISTAS:**

* getElementById()
* .value, .includes(), .length
* .disabled = true / false
* addEventListener("input", ...)
* addEventListener("submit", ...)
* preventDefault()

---

## Reto 7 – Fondo camaleónico

**Reto 7 – Fondo camaleónico**

**- RETO:** Añadir un botón que permita cambiar entre modo claro y modo oscuro, y que además actualice su texto según el modo activo.

**PASOS:**

1. Debajo justo del formulario, crea un botón con id="modoOscuro".
2. En el CSS, asegúrate de tener:
* Un estilo por defecto para el body (modo claro)
* Una clase .modoOscuro para el body con fondo oscuro y texto claro.


3. Al hacer clic en el botón:
* Si la página está en modo claro, añade la clase .modoOscuro al body
* Si está en modo oscuro, quítala.


4. El texto del botón debe cambiar dinámicamente:
* En modo claro: "**Modo oscuro**"
* En modo oscuro: "**Modo claro**"



**AS PISTAS:**

* getElementById()
* .classList.toggle()
* .textContent
* onclick
**Consejo:** usar .classList.contains() para saber en qué modo estás. También puedes usar una variable booleana como let enOscuro = false;

---

## Reto 8 – Efecto hover en JS

**Reto 8 – Efecto hover en JS**

**- RETO:** Aplicar un efecto visual al botón de envío del formulario cuando el usuario pasa el ratón por encima, controlándolo desde JavaScript.

**PASOS:**
A partir del formulario completo del reto anterior (reto 7), usa el botón de envío que ya existe con id="enviar".

1. Cuando el ratón pase por encima del botón:
* El fondo debe cambiar a blanco
* El texto debe cambiar a azul


2. Cuando el ratón se retire del botón:
* El fondo debe volver a azul
* El texto debe volver a blanco



**AS PISTAS:**

* getElementById()
* .style.backgroundColor
* .style.color
* addEventListener("mouseover", ...)
* addEventListener("mouseout", ...)

**NOTA:** este efecto también se puede hacer solo con CSS, pero en este reto lo vas a controlar directamente desde JavaScript para entender mejor cómo funcionan los eventos.

---

## Reto 9 – Precio en directo

**Reto 9 – Precio en directo**

**- RETO:** Actualizar automáticamente el precio total del desayuno cada vez que el usuario cambie cualquier opción, sin necesidad de pulsar ningún botón.

**PASOS:**

1. A partir del formulario del reto anterior, elimina el botón de "Calcular precio".
2. Cada vez que el usuario cambie cualquiera de las opciones (tipo de desayuno, nº de unidades, o extras), el precio total debe recalcularse al instante y mostrarse en un span con id "precioTotal".
3. Crea una función llamada calcularPrecio() que se encargue de hacer todos los cálculos. Esta función debe ejecutarse cada vez que el usuario cambie el tipo de desayuno, la cantidad o los extras. Así evitas repetir el mismo código en cada evento.
4. Muestra el total actualizado en el span

**Cómo calcular el precio:**

* Toma el precio base del producto seleccionado.
* Multiplicalo por la cantidad.
* Suma 1 € por cada extra marcado.

**AS PISTAS:**

* getElementById()
* .value, .checked
* parseFloat() o Number()
* .textContent
* forEach()
* addEventListener("change", ...)

---

## Reto 10 – Cuadro de resumen en vivo

**Reto 10 – Cuadro de resumen en vivo**

**- RETO: Mostrar al momento un resumen del pedido del usuario, actualizado en cuanto cambie cualquier opción.**

**PASOS:**

1. A partir del formulario anterior, debajo del todo, añade una zona de resumen en vivo con este formato:
`<p id="resumen-tipo">Tipo: </p>`
`<p id="resumen-cantidad">Cantidad: </p>`
`<p id="resumen-extras">Extras: </p>`
2. Cada vez que el usuario cambie cualquier dato del pedido, el resumen debe actualizarse automáticamente. Para ello, crea una función actualizarResumen() y llama a esa función desde cada addEventListener("change", ...).

**AS PISTAS:**

* getElementById() o querySelector(), querySelectorAll()
* .value
* .checked
* .textContent
* addEventListener("change", ...)
* forEach() (para recorrer los checkbox)
**Consejo:** empieza con el tipo de producto y la cantidad. Después añade el listado de extras. Puedes guardar los acompañamientos seleccionados en un Array y unirlos con .join(", "). 