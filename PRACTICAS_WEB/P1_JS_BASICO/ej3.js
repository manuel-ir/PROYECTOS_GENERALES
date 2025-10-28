let num = prompt("Ingresa un numero mayor que 0");
num = parseInt(num);


if (isNaN(num) || num < 0){
    alert ("Ingresa un numero valido");
}else{
    let suma=0;
    for (let i=1; i<= num; i++){
       suma +=i; 
    }
    alert ("suma");
}
