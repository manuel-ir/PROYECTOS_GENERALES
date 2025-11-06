// Espera a que todo el contenido del DOM esté cargado antes de ejecutar el script
document.addEventListener("DOMContentLoaded", function() {

    // --- LÓGICA COMÚN A VARIAS PÁGINAS ---

    // 1. LÓGICA MODO OSCURO
    const botonModo = document.getElementById("boton-modo");
    
    // Al cargar la página, comprueba si el modo oscuro estaba guardado
    if (localStorage.getItem("modo") === "dark") {
        document.body.classList.add("dark-mode");
    }

    if (botonModo) {
        botonModo.addEventListener("click", function() {
            // Alterna la clase en el body
            document.body.classList.toggle("dark-mode");
            
            // Guarda la preferencia en localStorage
            if (document.body.classList.contains("dark-mode")) {
                localStorage.setItem("modo", "dark");
            } else {
                localStorage.setItem("modo", "light");
            }
        });
    }
    
    // 2. LÓGICA BOTÓN CERRAR SESIÓN
    const botonLogout = document.getElementById("boton-logout");
    if (botonLogout) {
        botonLogout.addEventListener("click", function() {
            // Borra todos los datos guardados
            localStorage.clear();
            // Redirige al login
            window.location.href = "login.html";
        });
    }

    // 3. LÓGICA VERIFICACIÓN DE SESIÓN
    const pageId = document.body.id; 
    const usuario = localStorage.getItem("usuario"); 

    if (pageId !== "pagina-login" && !usuario) {
        alert("Debes iniciar sesión para acceder a esta página.");
        window.location.href = "login.html";
        return; 
    }

    // --- LÓGICA POR PÁGINA ---

    // 1. LÓGICA PARA: pagina-login
    if (pageId === "pagina-login") {
        const formLogin = document.getElementById("form-login");
        const emailInput = document.getElementById("email");
        const passwordInput = document.getElementById("password");
        const mensajeError = document.getElementById("mensaje-error");

        formLogin.addEventListener("submit", function(event) {
            event.preventDefault(); 
            
            const email = emailInput.value.trim();
            const password = passwordInput.value.trim();
            
            // --- VALIDACIONES ACTUALIZADAS ---
            if (email === "" || password === "") {
                mensajeError.textContent = "Ambos campos son obligatorios.";
            } else if (!email.includes("@")) { 
                mensajeError.textContent = "Por favor, introduce un correo válido.";
            } else if (password.length < 9) {
                mensajeError.textContent = "La contraseña debe tener al menos 9 caracteres.";
            } else {
                // Éxito: Simulación de inicio de sesión
                mensajeError.textContent = "";
                localStorage.setItem("usuario", email);
                window.location.href = "index.html";
            }
        });
    }

    // 2. LÓGICA PARA: pagina-index
    else if (pageId === "pagina-index") {
        const mensajeBienvenida = document.getElementById("mensaje-bienvenida");
        mensajeBienvenida.textContent = `Bienvenido/a, ${usuario}. Gracias por iniciar sesión.`;
    }

    // 3. LÓGICA PARA: pagina-reserva
    else if (pageId === "pagina-reserva") {
        const formReserva = document.getElementById("form-reserva");
        const selectEvento = document.getElementById("evento");
        const inputEntradas = document.getElementById("entradas");
        const checkboxesExtra = document.querySelectorAll(".extra"); 
        const spanPrecioTotal = document.getElementById("precio-total");
        const textareaComentarios = document.getElementById("comentarios");
        const spanContador = document.getElementById("contador-caracteres");

        function calcularTotal() {
            let precioEvento = parseFloat(selectEvento.value) || 0;
            let numEntradas = parseInt(inputEntradas.value) || 1;
            
            let totalExtras = 0;
            checkboxesExtra.forEach(function(checkbox) { 
                if (checkbox.checked) {
                    totalExtras += parseFloat(checkbox.value); 
                }
            });
            
            let precioTotal = (precioEvento + totalExtras) * numEntradas;
            spanPrecioTotal.textContent = precioTotal.toFixed(2);
        }

        textareaComentarios.addEventListener("input", function() {
            const longitud = textareaComentarios.value.length; 
            spanContador.textContent = `${longitud}/200`;
        });

        selectEvento.addEventListener("change", calcularTotal);
        inputEntradas.addEventListener("input", calcularTotal);
        checkboxesExtra.forEach(function(checkbox) {
            checkbox.addEventListener("change", calcularTotal);
        });

        formReserva.addEventListener("submit", function(event) {
            event.preventDefault(); 
            
            if (selectEvento.value === "") {
                alert("Por favor, selecciona un evento.");
                return;
            }

            const eventoTexto = selectEvento.options[selectEvento.selectedIndex].text;
            const extrasSeleccionados = [];
            checkboxesExtra.forEach(function(checkbox) {
                if (checkbox.checked) {
                    const label = document.querySelector(`label[for="${checkbox.id}"]`);
                    extrasSeleccionados.push(label.textContent);
                }
            });

            const datosReserva = {
                evento: eventoTexto,
                entradas: inputEntradas.value,
                extras: extrasSeleccionados, 
                comentarios: textareaComentarios.value,
                precioTotal: spanPrecioTotal.textContent
            };
            
            localStorage.setItem("reserva", JSON.stringify(datosReserva));
            window.location.href = "resumen.html";
        });

        calcularTotal();
    }

    // 4. LÓGICA PARA: pagina-resumen
    else if (pageId === "pagina-resumen") {
        const divResumen = document.getElementById("resumen-detalles");
        const datosGuardados = localStorage.getItem("reserva");
        
        if (datosGuardados) {
            const reserva = JSON.parse(datosGuardados);
            
            divResumen.innerHTML = `
                <p><strong>Evento:</strong> ${reserva.evento}</p>
                <p><strong>Número de Entradas:</strong> ${reserva.entradas}</p>
                <p><strong>Extras:</strong> ${reserva.extras.length > 0 ? reserva.extras.join(", ") : "Ninguno"}</p>
                <p><strong>Comentarios:</strong> ${reserva.comentarios ? reserva.comentarios : "Sin comentarios"}</p>
                <hr>
                <p><strong>PRECIO TOTAL: ${reserva.precioTotal}€</strong></p>
            `; 
            
        } else {
            divResumen.innerHTML = "<p>No se ha encontrado ninguna reserva. Por favor, realiza una reserva primero.</p>";
        }
    }

});