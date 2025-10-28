function sumar(a, b) {
    return a + b;
}

let num1 = prompt("Introduce el primer numero:");
num1 = parseFloat(num1);
let num2 = prompt("Introduce el segundo numero:");
num2 = parseFloat(num2);

if (isNaN(num1) || isNaN(num2)) {
    alert("Error: Por favor, introduce números validos.");
} else {
    let resultado = sumar(num1, num2);
    alert("La suma es: " + resultado);
}