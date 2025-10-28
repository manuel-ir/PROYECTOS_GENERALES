let noms = [];

for (let i = 0; i < 3; i++){
    let nom = prompt("ingresa un nombre" + (i+1)); //el i+1 es para añadir 1 a la posicion en el que empieza el array
    noms.push(nom);
    
}

let cant = noms.length;
let primero = noms[0];
let ultimo = noms[noms.length - 1];

alert("cant = " + cant + "\n" + 
        "primero = " + primero + "\n" +
        "ultimo = " + ultimo);

