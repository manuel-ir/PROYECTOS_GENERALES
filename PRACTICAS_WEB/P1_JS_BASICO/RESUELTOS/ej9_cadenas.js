let nombre = prompt("Introduce tu nombre:");

if (nombre === null || nombre.trim() === "") {
    alert("Error: Por favor, introduce un nombre valido.");
} else {
    let longitud = nombre.length;
    let mayusculas = nombre.toUpperCase();
    let minusculas = nombre.toLowerCase();
    
    alert("Longitud: " + longitud + " letras\n" +
          "Mayusculas: " + mayusculas + "\n" +
          "Minusculas: " + minusculas);
}