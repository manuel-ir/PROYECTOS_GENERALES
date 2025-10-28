let edad = prompt("Introduce tu edad:");
edad = parseInt(edad);

if (isNaN(edad)) {
    alert("Error: Por favor, introduce un número válido.");
} else {
    if (edad > 18) {
        alert("Eres mayor de edad.");
    } else if (edad >= 13 && edad <= 17) {
        alert("Eres adolescente.");
    } else if (edad < 13) {
        alert("Eres un niño.");
    }
} 