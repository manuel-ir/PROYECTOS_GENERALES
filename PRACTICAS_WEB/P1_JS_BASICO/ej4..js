let edad1 = prompt("ingresa una edad");
edad1 = parseInt(edad1);
let edad2 = prompt("ingresa otra edad");
edad2 = parseInt(edad2);

if (isNaN(edad1, edad2)){
    alert("Ingresa una edad valida")
}else{
    if(edad1 && edad2 >= 18) {
        alert ("ambos mayores de edad");
        }else if(edad1 >= 18 || edad2 >= 18) {
            alert ("Al menos uno es mayor de edad");
}else{
    alert("ninguno es mayor de edad");
  }
}
