let numero = prompt("Introduce un numero:");
numero = parseInt(numero);

if (isNaN(numero)) {
    alert("Error: Por favor, introduce un numero válido.");
} else {
    let original = numero;
    let incrementado = numero;
    incrementado++;
    let decrementado = numero;
    decrementado--;
    
    alert("Valor original: " + original + "\n" +
          "Valor incrementado: " + incrementado + "\n" +
          "Valor decrementado: " + decrementado);
}