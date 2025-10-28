function sumar (a , b){
    return a+b;
}

let num1 = prompt("ingresa n1");
num1 = parseInt(num1);
let num2 = prompt("ingresa n2");
num2 = parseInt(num2);


if (isNaN(num1) || isNaN(num2)){
    alert("ingresa un numero valido");
}else{
    let resultado = sumar(num1,num2);
    alert("La suma es: " + resultado);
}