# Guía de funciones y métodos para las prácticas de JavaScript

### FUNCIONES DE ACCESO Y SELECCIÓN
- `getElementById("id")`: busca un elemento del HTML usando su atributo id.
- `querySelector("selector")`: selecciona el primer elemento que coincida con un selector CSS.
- `querySelectorAll("selector")`: selecciona todos los elementos que coincidan con el selector (devuelve un array de nodos).

### PROPIEDADES PARA TRABAJAR CON FORMULARIOS
- `.value`: obtiene o modifica el contenido de un input, select o textarea.
- `.checked`: indica si un checkbox o radio button está marcado (true o false).
- `.disabled = true / false`: activa o desactiva un elemento del formulario (como un botón).
- `.textContent` / `.innerText`: permite leer o cambiar el contenido textual de un elemento. Muy útil para actualizar contadores, mensajes o resultados en pantalla.
- `.length`: da el número de caracteres de un string, o la cantidad de elementos en un array.
- `.trim()`: elimina los espacios en blanco al inicio y al final de una cadena (útil para validar si un campo está vacío).

### VALIDACIONES
- `.includes("texto")`: comprueba si una cadena contiene una subcadena concreta.
- `isNaN(valor)`: comprueba si algo no es un número.

### CONVERSIÓN DE DATOS
- `parseInt(valor)`: convierte una cadena en un número entero.
- `parseFloat(valor)`: convierte una cadena en un número decimal.
- `Number(valor)`: convierte cualquier valor a número si puede.
- `.join(", ")`: une los elementos de un array en una sola cadena separada por comas.

### ESTILOS Y CLASES
- `.style.propiedad`: permite cambiar estilos CSS desde JavaScript (por ejemplo: fondo, color...).
- `.classList.toggle("clase")`: añade la clase si no la tiene, o la quita si ya la tiene.
- `.classList.contains("clase")`: devuelve true si el elemento tiene esa clase, y false si no.

### EVENTOS
- `addEventListener("evento", función)`: conecta una función a un evento (como click, change, submit...).
- `oninput`: se activa cuando el usuario escribe, borra o pega texto en un input o textarea.
- `onclick`, `onchange`, `onmouseover`, `onmouseout`, `onsubmit`: eventos que puedes asignar directamente a elementos.
- `event.preventDefault()`: evita que el navegador haga la acción por defecto (por ejemplo, recargar al enviar un formulario).

### VENTANAS EMERGENTES
- `alert("mensaje")`: muestra un mensaje de aviso al usuario.
- `confirm("mensaje")`: muestra un mensaje con botones "Aceptar" y "Cancelar" y devuelve true o false.