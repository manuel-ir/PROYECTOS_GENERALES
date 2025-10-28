let num = prompt("elige un menu del 1 al 3");
num = parseInt(num);

if (isNaN(num) || num < 1 || num > 3) {
    alert("ingresa un numero valido)")
}else{
    switch (num) {
        case 1:
            alert("has elegido 1")
            break;
        
        case 2:
            alert("has elegido 2");
        
        case 3:
            alert ("has elegido 3");
    
        default:
            alert("opcion no valida");
    }
}