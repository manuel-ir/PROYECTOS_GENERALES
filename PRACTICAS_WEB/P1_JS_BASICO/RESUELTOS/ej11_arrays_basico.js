let nombres = [];

for (let i = 0; i < 3; i++) {
  let nombre = prompt("Introduce el nombre " + (i + 1) + ":");
  nombres.push(nombre);
}

let cantidad = nombres.length;
let primero = nombres[0];
let ultimo = nombres[nombres.length - 1];

alert(
  "Cantidad de elementos: " +
    cantidad +
    "\n" +
    "Primer nombre: " +
    primero +
    "\n" +
    "Último nombre: " +
    ultimo
);
