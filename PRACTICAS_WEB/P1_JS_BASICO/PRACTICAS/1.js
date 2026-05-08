let edad = prompt ("Introduce la edad");
edad = parseInt(edad);

if (isNaN(edad)){
 } else{
    alert("Introduce un número válido");
    if (edad > 18){
        alert ("Es mayor de edad");
    }else if (edad >= 13 && edad <= 17) {
        alert ("Eres adolescente");
    } else if (edad > 13){
        alert ("Eres un niño");
    }
}
