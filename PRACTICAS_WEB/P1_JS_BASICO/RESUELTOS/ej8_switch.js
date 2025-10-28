let numero = prompt("Introduce un numero del 1 al 3:");
numero = parseInt(numero);

if (isNaN(numero)) {
    alert("Opcion no valida");
} else {
    switch (numero) {
        case 1:
            alert("Has elegido uno");
            break;
        case 2:
            alert("Has elegido dos");
            break;
        case 3:
            alert("Has elegido tres");
            break;
        default:
            alert("Opción no valida");
    }
}