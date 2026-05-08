# Chuleta — métodos y eventos del DOM
**FPA IES Polígono Sur** | **Docente: Myriam Jiménez Moreno**

| Método / Evento | Qué hace |
| :--- | :--- |
| `document.getElementById('id')` | Selecciona un elemento por su id. |
| `document.querySelector('selector')` | Selecciona el primer elemento que cumple el selector CSS. |
| `document.querySelectorAll('selector')` | Selecciona todos los elementos que cumplen el selector CSS. |
| `element.textContent` | Cambia solo el texto interno del elemento. |
| `element.innerHTML` | Cambia el contenido, interpretando etiquetas HTML. |
| `element.setAttribute('atributo', 'valor')` | Cambia o crea un atributo del elemento. |
| `element.getAttribute('atributo')` | Obtiene el valor de un atributo. |
| `element.classList.add('clase')` | Añade una clase CSS al elemento. |
| `element.classList.remove('clase')` | Elimina una clase CSS. |
| `element.classList.toggle('clase')` | Alterna entre añadir o quitar una clase. |
| `document.createElement('etiqueta')` | Crea un nuevo elemento HTML. |
| `padre.appendChild(hijo)` | Inserta un nuevo elemento dentro de otro, al final. |
| `padre.insertBefore(nuevo, referencia)` | Inserta un elemento antes del indicado. |
| `padre.removeChild(hijo)` | Elimina un elemento hijo de su contenedor. |
| `element.style.propiedad` | Modifica un estilo CSS directamente. |
| `onclick` | Se activa al hacer clic sobre el elemento. |
| `oninput` | Se activa cuando el usuario escribe en un campo. |
| `onsubmit` | Se activa al enviar un formulario. |
| `mouseover / mouseout` | Detectan cuándo el ratón entra o sale de un elemento. |
| `keydown` | Detecta qué tecla se ha presionado. |