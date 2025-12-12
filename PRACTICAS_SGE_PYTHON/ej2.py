import os
import math 

ruta_script = os.path.dirname(__file__)

# BLOQUE 1 MATEMATICAS

# Ejercicio Factorial
# Definición de la función
def calcular_factorial(n):
    resultado = 1
    for i in range(1, n + 1):
        resultado = resultado * i
    return resultado

# Ejecución del ejercicio Factorial
num = 5
print("El factorial de " + str(num) + " es: " + str(calcular_factorial(num)))


# Ejercicio Factura con IVA
# Definición de la función
def calcular_factura(cantidad, iva=21):
    total = cantidad + (cantidad * iva / 100)
    return total

# Ejecución del ejercicio Factura
print("Total factura IVA defecto 21%: " + str(calcular_factura(100)))
print("Total factura IVA 10%: " + str(calcular_factura(100, 10)))


# Ejercicio Area Circulo y Volumen Cilindro
# Definición de las funciones
def area_circulo(radio):
    return math.pi * (radio ** 2)

def volumen_cilindro(radio, altura):
    return area_circulo(radio) * altura

# Ejecución del ejercicio Cilindro
r = 3
h = 5
print("El volumen del cilindro es: " + str(volumen_cilindro(r, h)))


# BLOQUE 2 FICHEROS

# Ejercicio Tabla de multiplicar en fichero
# Pide número, guarda en archivo y muestra por pantalla
n = int(input("Introduce un número del 1 al 10 para la tabla de multiplicar: "))
nombre_archivo = "tabla" + str(n) + ".txt"
ruta_tabla = os.path.join(ruta_script, nombre_archivo)

print("Guardando tabla en fichero...")
with open(ruta_tabla, "w", encoding="utf-8") as archivo:
    for i in range(1, 11):
        linea = str(n) + " x " + str(i) + " = " + str(n*i) + "\n"
        archivo.write(linea)

# Lectura inmediata del resultado
print("Mostrando contenido del archivo creado:")
with open(ruta_tabla, "r", encoding="utf-8") as archivo:
    print(archivo.read())


# Ejercicio Contar líneas de un archivo
ruta_lectura = os.path.join(ruta_script, "archivo_lectura.txt")

# Primero creamos el archivo de prueba
with open(ruta_lectura, "w", encoding="utf-8") as archivo:
    archivo.write("Línea 1: Principio\n")
    archivo.write("Línea 2: Medio\n")
    archivo.write("Línea 3: Final\n")

# Ahora contamos las líneas
with open(ruta_lectura, "r", encoding="utf-8") as archivo:
    lineas = archivo.readlines()
    print("El archivo tiene " + str(len(lineas)) + " líneas.")


# Ejercicio Leer primera línea
# Usamos el mismo archivo anterior
with open(ruta_lectura, "r", encoding="utf-8") as archivo:
    print("La primera línea es: " + archivo.readline().strip())


# Ejercicio Append Añadir y leer todo
ruta_append = os.path.join(ruta_script, "archivo_append.txt")

# Crear base
with open(ruta_append, "w", encoding="utf-8") as f:
    f.write("Contenido original.\n")

# Añadir nueva linea y leer
with open(ruta_append, "a+", encoding="utf-8") as archivo:
    archivo.write("Esta es la nueva línea añadida.\n")
    
    # Rebobinar para leer
    archivo.seek(0) 
    print("Contenido tras el append:")
    print(archivo.read())


# BLOQUE 3 LOGICA

# Ejercicio Es Par
# Definición de la función
def es_par(numero):
    if numero % 2 == 0:
        return True
    else:
        return False

# Ejecución del ejercicio Par
num_usuario = int(input("Introduce un número para saber si es par: "))
print("¿El número es par?: " + str(es_par(num_usuario)))


# Ejercicio Mayor de Tres
# Definición de la función
def mayor_de_tres(n1, n2, n3):
    if n1 >= n2 and n1 >= n3:
        return n1
    elif n2 >= n1 and n2 >= n3:
        return n2
    else:
        return n3

# Ejecución del ejercicio Mayor de Tres
a = int(input("Primer número: "))
b = int(input("Segundo número: "))
c = int(input("Tercer número: "))
print("El mayor es: " + str(mayor_de_tres(a, b, c)))


# Ejercicio Sumar args variable
# Definición de la función
def sumar_numeros(*args):
    total = 0
    for numero in args:
        total += numero
    print("Suma de " + str(args) + ": " + str(total))

# Ejecución del ejercicio args
sumar_numeros(5, 5)
sumar_numeros(10, 20, 30)
sumar_numeros(1, 1, 1, 1, 1)