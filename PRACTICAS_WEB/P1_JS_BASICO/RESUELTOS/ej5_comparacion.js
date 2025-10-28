let numero = prompt("Introduce un numero:");
let numeroConvertido = parseInt(numero);

if (isNaN(numeroConvertido)) {
    alert("No es cinco");
} else {
    if (numeroConvertido === 5) {
        alert("Exactamente cinco");
    } else if (numero == 5) {
        alert("Cinco (tipo distinto)");
    } else {
        alert("No es cinco");
    }
}