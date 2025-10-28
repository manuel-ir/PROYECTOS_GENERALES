let resultado = "";

for (let i = 1; i <= 10; i++) {
    if (i % 3 === 0 && i % 5 === 0) {
        resultado += "FizzBuzz\n";
    } else if (i % 3 === 0) {
        resultado += "Fizz\n";
    } else if (i % 5 === 0) {
        resultado += "Buzz\n";
    } else {
        resultado += i + "\n";
    }
}

alert(resultado);