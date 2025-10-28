// Array para almacenar los números del usuario
let numeros = [];
let numero;

// Pedir números hasta que se ingrese 0
alert("Vamos a ingresar números. Ingresa 0 para terminar.");

while (true) {
    numero = prompt("Ingresa un número (0 para terminar):");
    numero = parseFloat(numero); // Usamos parseFloat por si ingresan decimales
    
    // Validar si es un número
    if (isNaN(numero)) {
        alert("Por favor, ingresa un número válido");
        continue;
    }
    
    // Condición de salida
    if (numero === 0) {
        break;
    }
    
    // Guardar el número en el array
    numeros.push(numero);
    alert("Número " + numero + " guardado. Llevas " + numeros.length + " números.");
}

// Procesar el array con break y continue
let numerosValidos = "";
let acumulador = "";

alert("Ahora vamos a procesar los " + numeros.length + " números que ingresaste...");

for (let i = 0; i < numeros.length; i++) {
    let num = numeros[i];
    
    // Si el número es negativo, lo salta (continue)
    if (num < 0) {
        continue; // Salta a la siguiente iteración
    }
    
    // Si el número es mayor que 100, para el bucle (break)
    if (num > 100) {
        alert("¡Encontramos un número mayor que 100 (" + num + ")! Parando el bucle...");
        break; // Termina el bucle completamente
    }
    
    // Si no es negativo ni mayor que 100, lo acumula
    acumulador += num + ", ";
    numerosValidos += num + "\n";
}

// Mostrar resultados
if (acumulador.length > 0) {
    // Eliminar la última coma y espacio
    acumulador = acumulador.slice(0, -2);
} else {
    acumulador = "No hay números válidos";
}

alert("RESULTADO FINAL:\n\n" +
      "Números que ingresaste: " + numeros.join(", ") + "\n\n" +
      "Números que pasaron el filtro:\n" + acumulador);

