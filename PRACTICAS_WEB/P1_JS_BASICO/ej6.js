let num = prompt("ingresa un numero");
num = parseInt(num);

if (isNaN(num)){
    alert("ingresa un numero valido");
    }else {
    
        let original = num;
        let incrementado = num;
        incrementado ++;
        let decrementado = num;
        decrementado --;

        alert ("Original: " + original + "\n"+
                "incrementado: " + incrementado + "\n" + 
                "decremento: " + decrementado);

    }
