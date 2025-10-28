let edad = prompt("Ingresa tu edad")
edad = parseInt(edad)

if (isNaN(edad)){
    alert("Error: Ingresa un numero")
}else{
    if(edad <= 18){
        alert("Eres mayor de edad")
    }else if(edad <=17 && edad >= 13){
        alert("Eres adolescente")
    }else if(edad < 13){
        alert("Eres un niño")
    }

}