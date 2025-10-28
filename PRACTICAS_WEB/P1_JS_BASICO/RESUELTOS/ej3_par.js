let numero = prompt("Introduce un numero mayor que 0:");
numero = parseInt(numero);

if (isNaN(numero) || numero <= 0) {
    alert("Error: Por favor, introduce un numero valido mayor que 0.");
} else {
    let suma = 0;
    for (let i = 1; i <= numero; i++) {
        suma += i;
    }
    alert("La suma de todos los numeros desde 1 hasta " + numero + " es: " + suma);
}