let nota = prompt("Introduce una nota (0-10):");
nota = parseFloat(nota);

if (isNaN(nota) || nota < 0 || nota > 10) {
    alert("Error: Por favor, introduce una nota valida entre 0 y 10.");
} else {
    let resultado = (nota >= 5) ? "Aprobado" : "Suspenso";
    alert(resultado);
}