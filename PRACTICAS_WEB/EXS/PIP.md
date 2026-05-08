Aquí tienes la transcripción completa de la prueba **UD1_PIP**, siguiendo el orden de las imágenes (anverso y reverso) y respetando fielmente el texto, los identificadores y la estructura de puntos.

---

# UD1_PIP - REC Prueba de Integración Práctica

**FPA IES Polígono Sur** **Docente:** Myriam Jiménez Moreno

> **RA1 - CE a,b,c,d,e** > **Objetivo:** desarrollar una página web (HTML + JavaScript embebido) que permita simular el alquiler de material, aplicando validaciones de formulario, cálculo automático del precio y actualización dinámica del DOM.
> El ejercicio es único y progresivo; cada requisito es independiente y puntúa por separado.

---

### Instrucciones generales

* Entregar un único archivo llamado **base.html**, con el js integrado, que encontrarás en el repositorio de esta prueba con la estructura base.
* No se permiten archivos .js o .css separados.
* **No usar alert() ni console.log()** como salida final. Todos los mensajes deben mostrarse en la propia página.
* **Usa los id EXACTOS** indicados en el enunciado: así podré verificar más rápido.
* Comenta tu código adecuadamente.
* Antes de comenzar el examen, te recomiendo que mires con detenimiento el archivo base pues ya contiene información para realizar correctamente el mismo.

---

### Requisitos funcionales (10 puntos)

#### 1) Modo claro/oscuro (CE b,e) (2 puntos)

Haz que el botón (id="**temaBtn**") permita alternar entre dos modos visuales de la página (claro / oscuro) durante la sesión. El cambio debe aplicarse de forma inmediata y ser reversible con el mismo botón.

#### 2) Validación básica del formulario (CE a,b,c,e) (2 puntos)

Al pulsar el botón **Calcular alquiler**, el formulario (id="**formAlquiler**") debe comprobar que los datos introducidos son válidos antes de continuar, en este orden:

* El campo **nombre del cliente** (id="**nomCliente**") no puede estar vacío.
* El campo **código de cliente** (id="**codigo**") debe contener la letra C (ejemplos C1542, C658...)
* El campo **código postal** (id="**cp**") debe contener exactamente **5 dígitos numéricos**.
* Debe haberse elegido un **tipo de material** (id="**material**").
* El programa debe mostrar **mensajes de error específicos** según el tipo de dato incorrecto, incluyendo el caso en que todos los campos estén vacíos (ver ejemplos al final).
* Si hay errores, se mostrará un mensaje en el elemento id="**avisos**", con el texto en color rojo.
* Si todos los datos son correctos, se mostrará un mensaje de confirmación en color azul.

#### 3) Cálculo del precio total con arrays (CE a,d,e) (3 puntos)

Al pulsar el botón **Calcular alquiler** (id="**calcularAlquilerBtn**"), el programa debe calcular el precio del alquiler utilizando arrays:

* Un array con los **precios base del material**:
* bicicleta: 5 €
* patinete: 4 €
* kayak: 8 €


* Otro array con los **precios de los extras**:
* casco: 1 €
* seguro: 2 €
* luces: 0,5 €



El total se calculará sumando el precio del material elegido y el de todos los extras seleccionados.

**Requisitos:**

* Usar al menos **un bucle** para recorrer los extras seleccionados.
* Mostrar el resultado con **dos decimales** y el símbolo de euro.
* El resultado debe mostrarse en el elemento id="**detalleAlquiler**", no en la consola.
* El botón **Confirmar alquiler** (id="**confirmarBtn**") debe estar deshabilitado al cargar la página y solo activarse cuando el cálculo sea correcto.

#### 4) Función principal y actualización del DOM (CE b,e) (3 puntos)

Al enviar el formulario (id="**formAlquiler**"), el programa debe ejecutar una función principal que integre las validaciones y el cálculo del alquiler.

**Requisitos:**

* **Obtener el total del alquiler** reutilizando la función de cálculo.
* **Mostrar un detalle claro** en el elemento id="**detalleAlquiler**", indicando:
* el material elegido,
* los extras seleccionados,
* y el precio total.


* El área de detalle del alquiler debe **cambiar de color de fondo** para destacar el resultado final.
* El proceso debe realizarse **sin recargar la página**.

---

### Ejemplo de resultado esperado:

*(En la imagen se muestran cuatro capturas de pantalla de la interfaz:)*

1. *Interfaz inicial en modo claro con campos vacíos.*
2. *Interfaz en modo oscuro con mensaje de error: "Complete todos los campos" en rojo.*
3. *Interfaz con error de validación: "El código de cliente debe empezar por C".*
4. *Interfaz con validación correcta y resumen final: "Material: Bicicleta, Extras: Seguro, Precio total: 7.00 €".*

---

**IES Polígono Sur**