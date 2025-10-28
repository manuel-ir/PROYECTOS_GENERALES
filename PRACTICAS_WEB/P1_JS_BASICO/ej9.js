let nombre = prompt("ingresa un nombre");

if (nombre === null || nombre.trim() === ""){
    alert ("nombre invaslido");

}else{
    let longitud = nombre.length;
    let mayusculas = nombre.toUpperCase();
    let minusculas = nombre.toLowerCase();

    alert("longitud " + longitud + "\n" + 
        "mayusculas " + mayusculas + "\n" + 
        "minusculas " +  minusculas);
}