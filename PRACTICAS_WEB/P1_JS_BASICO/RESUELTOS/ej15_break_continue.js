let numeros = [10, -5, 25, 150, -3, 80, 200, 30];
let numerosValidos = "";
let acumulador = "";

for (let i = 0; i < numeros.length; i++) {
    if (numeros[i] < 0) {
        continue; // Salta números negativos
    }
    if (numeros[i] > 100) {
        break; // Para el bucle si el número es mayor que 100
    }
    acumulador += numeros[i] + ", ";
}

if (acumulador.length > 0) {
    numerosValidos = acumulador.slice(0, -2); // Elimina la última coma y espacio
} else {
    numerosValidos = "No hay numeros validos";
}

alert("Numeros que pasaron el filtro:\n" + numerosValidos);