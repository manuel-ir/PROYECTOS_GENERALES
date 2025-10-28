let num = prompt("ingresa un numero");
let numCon = parseInt(num);

if (isNaN(numCon)){
    alert("no es cinco")
}else{
    if (num === 5){
        alert("exactamente 5");
    }else if (num == 5){
        alert("cinco distinto");
    }else{
        alert("no es cinco");
    }
}