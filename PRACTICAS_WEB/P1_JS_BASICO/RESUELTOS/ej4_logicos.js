let edad1 = prompt("Introduce la primera edad:");
edad1 = parseInt(edad1);
let edad2 = prompt("Introduce la segunda edad:");
edad2 = parseInt(edad2);

if (isNaN(edad1) || isNaN(edad2)) {
    alert("Error: Por favor, introduce edades válidas.");
} else {
    if (edad1 >= 18 && edad2 >= 18) {
        alert("Ambas personas son mayores de edad.");
    } else if (edad1 >= 18 || edad2 >= 18) {
        alert("Al menos una persona es mayor de edad.");
    } else {
        alert("Ninguna persona es mayor de edad.");
    }
}