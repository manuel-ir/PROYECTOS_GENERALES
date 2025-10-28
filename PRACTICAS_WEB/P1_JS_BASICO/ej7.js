let nota = prompt("introduce una");
nota = parseFloat(nota);

if (isNaN(nota) || nota < 0 || nota > 10){
    alert ("error, introduce una valida");
}else{
    let resultado = (nota >= 5) ? "aprobado" : "Suspenso";
    alert(resultado);
}
   