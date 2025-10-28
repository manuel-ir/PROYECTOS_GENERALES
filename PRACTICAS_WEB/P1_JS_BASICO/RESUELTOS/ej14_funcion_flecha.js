const doble = (n) => n * 2;

let numero = prompt("Introduce un numero:");
numero = parseFloat(numero);

if (isNaN(numero)) {
    alert("Error: Por favor, introduce un número válido.");
} else {
    let resultado = doble(numero);
    alert("El doble de " + numero + " es: " + resultado);
}