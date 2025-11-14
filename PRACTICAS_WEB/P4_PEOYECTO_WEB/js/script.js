document.addEventListener("DOMContentLoaded", function() {

    // LÓGICA COMÚN A VARIAS PÁGINAS 

    // LÓGICA MODO OSCURO
    const botonModo = document.getElementById("boton-modo");
    
    if (localStorage.getItem("modo") === "dark") {
        document.body.classList.add("dark-mode");
    }

    if (botonModo) {
        botonModo.addEventListener("click", function() {
            document.body.classList.toggle("dark-mode");
            if (document.body.classList.contains("dark-mode")) {
                localStorage.setItem("modo", "dark");
            } else {
                localStorage.setItem("modo", "light");
            }
        });
    }
    
    // LÓGICA BOTÓN CERRAR SESIÓN 
    const botonLogout = document.getElementById("boton-logout");
    if (botonLogout) {
        botonLogout.addEventListener("click", function() {
            localStorage.clear();
            window.location.href = "login.html";
        });
    }

    // LÓGICA VERIFICACIÓN DE SESIÓN
    const pageId = document.body.id; 
    const usuario = localStorage.getItem("usuario"); 

    
    if (pageId !== "pagina-login" && pageId !== "pagina-index" && !usuario) {
        alert("Debes iniciar sesión para acceder a esta página.");
        window.location.href = "login.html";
        return; 
    }

    // LÓGICA POR PÁGINA 

    // LÓGICA login
    if (pageId === "pagina-login") {
        const formLogin = document.getElementById("form-login");
        const emailInput = document.getElementById("email");
        const passwordInput = document.getElementById("password");
        const mensajeError = document.getElementById("mensaje-error");

        formLogin.addEventListener("submit", function(event) {
            event.preventDefault(); 
            
            const email = emailInput.value.trim();
            const password = passwordInput.value.trim();
            
            // Validaciones 
            if (email === "" || password === "") {
                mensajeError.textContent = "Ambos campos son obligatorios.";
            } else if (!email.includes("@")) { 
                mensajeError.textContent = "Por favor, introduce un correo válido.";
            } else if (password.length < 9) { // Contraseña mínima de 9 caracteres
                mensajeError.textContent = "La contraseña debe tener al menos 9 caracteres.";
            } else {
                
                mensajeError.textContent = "";
                localStorage.setItem("usuario", email);
                
                // Redirige a 'reserva.html'
                window.location.href = "reserva.html"; 
            }
        });
    }

    // 2. LÓGICA index
    else if (pageId === "pagina-index") {
        
    }

    // 3. LÓGICA reserva
    else if (pageId === "pagina-reserva") {
    
        // Añade el mensaje de bienvenida
        const mensajeBienvenida = document.getElementById("mensaje-bienvenida");
        if (mensajeBienvenida) {
            mensajeBienvenida.textContent = `Bienvenido/a, ${usuario}. Gestiona tu reserva.`;
        }

        // Almacena los elementos del formulario en constantes
        const formReserva = document.getElementById("form-reserva");
        const selectEvento = document.getElementById("evento");
        const inputEntradas = document.getElementById("entradas");
        const checkboxesExtra = document.querySelectorAll(".extra"); 
        const spanPrecioTotal = document.getElementById("precio-total");
        const textareaComentarios = document.getElementById("comentarios");
        const spanContador = document.getElementById("contador-caracteres");

        // Define la función parta calcular el precio total
        function calcularTotal() {

            // Convierte valores de texto a número
            let precioEvento = parseFloat(selectEvento.value) || 0;
            let numEntradas = parseInt(inputEntradas.value) || 1;
            
            let totalExtras = 0;

            // Suma el precio de los extras seleccionados
            checkboxesExtra.forEach(function(checkbox) { 
                if (checkbox.checked) {
                    totalExtras += parseFloat(checkbox.value); 
                }
            });
            
            // Calcula el precio total de los extras marcados
            let precioTotal = (precioEvento + totalExtras) * numEntradas;
            spanPrecioTotal.textContent = precioTotal.toFixed(2);
        }

        // Contador de caracteres
        textareaComentarios.addEventListener("input", function() {
            const longitud = textareaComentarios.value.length; 
            spanContador.textContent = `${longitud}/200`;
        });

        // Asigna la función calcularTotal a los eventos 
        // Y es llamado cada vez que el usuario cambia de opción
        selectEvento.addEventListener("change", calcularTotal);
        inputEntradas.addEventListener("input", calcularTotal);
        checkboxesExtra.forEach(function(checkbox) {
            checkbox.addEventListener("change", calcularTotal);
        });

        // Evento para enviar el formulario
        formReserva.addEventListener("submit", function(event) {
            event.preventDefault(); 
            
            // Validación básica
            if (selectEvento.value === "") {
                alert("Por favor, selecciona un evento.");
                return;
            }

            // Obtiene el texto del evento seleccionado
            const eventoTexto = selectEvento.options[selectEvento.selectedIndex].text;
            const extrasSeleccionados = [];

            // Recorre los extras para obtener sus nombres
            checkboxesExtra.forEach(function(checkbox) {
                if (checkbox.checked) {
                    const label = document.querySelector(`label[for="${checkbox.id}"]`);
                    extrasSeleccionados.push(label.textContent);
                }
            });

            // Crea un obejeto con los datos de la reserva
            const datosReserva = {
                evento: eventoTexto,
                entradas: inputEntradas.value,
                extras: extrasSeleccionados, 
                comentarios: textareaComentarios.value,
                precioTotal: spanPrecioTotal.textContent
            };
            
            // Guarda el objeto como String JSON en localStorage
            localStorage.setItem("reserva", JSON.stringify(datosReserva));
            window.location.href = "resumen.html";
        });

        calcularTotal();
    }

    // 4. LÓGICA resumen
    else if (pageId === "pagina-resumen") {
        const divResumen = document.getElementById("resumen-detalles");

        // Obtiene el string JSON de la reserva
        const datosGuardados = localStorage.getItem("reserva");
        
        // Convierte el string de vuelta a un objeto JS
        if (datosGuardados) {
            const reserva = JSON.parse(datosGuardados);
            
            // Inserta el bloque de HTML con los datalles de la reserva
            divResumen.innerHTML = `
                <p><strong>Evento:</strong> ${reserva.evento}</p>
                <p><strong>Número de Entradas:</strong> ${reserva.entradas}</p>
                <p><strong>Extras:</strong> ${reserva.extras.length > 0 ? reserva.extras.join(", ") : "Ninguno"}</p>
                <p><strong>Comentarios:</strong> ${reserva.comentarios ? reserva.comentarios : "Sin comentarios"}</p>
                <hr>
                <p><strong>PRECIO TOTAL: ${reserva.precioTotal}€</strong></p>
            `; 
            
        } else {

            // Muestra un error si no hay datos guardados
            divResumen.innerHTML = "<p>No se ha encontrado ninguna reserva. Por favor, realiza una reserva primero.</p>";
        }
    }

});