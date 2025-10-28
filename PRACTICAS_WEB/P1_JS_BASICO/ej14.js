const doble = (a) => a * 2; // se declara la funcion

let num = prompt("introduce numero");  // se pide el num al usuario
num = parseInt(num); // hacemos conversion a int o float

let resultado  = doble(num); // guardamos el resultado de la funcion en una variable

alert ("resultado= " + resultado);