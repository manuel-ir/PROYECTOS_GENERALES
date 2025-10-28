let numeros = [3, 7, 1, 5, 9];
let resultado = "";

for (let i = 0; i < numeros.length; i++) {
    resultado += (numeros[i] * 2) + "\n";
}

alert("Numeros multiplicados por 2:\n" + resultado);