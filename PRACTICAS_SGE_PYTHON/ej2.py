# Calcular el área de un rectángulo
# Función normal
def calcular_area(base, altura):
    area = base * altura
    return area

# Programa principal
base = float(input("Ingresa la base del rectángulo): "))
altura =  float(input("Ingresa la altura del rectángulo): "))

resultado = calcular_area(base, altura)
print("El área del rectángulo es: ", resultado)


# Funciones dinámicas 
def duplicar(valor):
    return valor * 2
 
print(duplicar(5))  # Salida: 10
print(duplicar("Hola")) # Salida: HolaHola


def pedir_nombre():
    nombre = input("Introduce tu nombre: ")
    return nombre   

def saludar(nombre):
    print("Hola, " + nombre)
    
# Programa principal
nombre_usuario = pedir_nombre()
saludar(nombre_usuario)


# Función con número variable de argumentos usando args. Se usa cuando no se sabe cuantos argumentos se van a pasar
def sumar(*args):
    total = 0
    for numero in args:
        total += numero
    print("La suma es:", total)
   
# Llamadas a la funnción
sumar (2, 4)
sumar (1, 2, 3)
sumar (10, 20, 30, 40)

# Lectura de archivos en python

# Ruta del directorio donde esta el archivo
ruta = "PRACTICAS_SGE_PYTHON/Texto.txt"

# Abrimos el archivo con open(archivo, modo). 
archivo = open(ruta, "r")

# Lee el contenido del archivo. read() lee todo el archivo o hasta donde le indiquemos los bytes. Si no indicamos nada, devuelve -1 al final del archivo.
print("READ(:)")
print(archivo.read())

# Volvemos al inicio del archivo. Seek indica el sitio desde donde empezara a leer.
archivo.seek(0)

# Lee una linea. readline() lee una linea, que se puede delimiar pasandole parámetros

print("READLINE():")
print(archivo.readline())

#Volvemos al inicio del archivo
archivo.seek(0)

print("READLINES():") 
# Lee todas las lineas con readlines(). Podemos pasarle parámetros, si lo excede, no devuelve lineas adicionales
print(archivo.readlines())

# Cerramos el archivo con close()
archivo.close()

# Crear y escribir archivos
# r = Read: lee pero no puede ecribir y arroja error si no existe el archivo
# a = Append: Añade lineas después de la última del documento. Si no existe el archivo, lo crea.
# w = Write: Abre/crea un archivo si no existe. Sobreescribe el contenido que haya con el nuevo
# x = Create: Crea un archivo. Si ya existe en el direcotorio, arroja error


# Leemos un archivo pasando una 
import os

ruta_script = os.path.dirname(__file__)  # __file__ usa la ruta actual del programa
ruta_archivo = os.path.join(ruta_script, "Texto.txt")

with open(ruta_archivo, "r", encoding="utf-8") as archivo:  # with actua como el close(), cuando termina de operar, cierra el archivo
    print(archivo.readline())
    
# Creación y lectura del archivo con append
ruta_script = os.path.dirname(__file__)
ruta_archivo = os.path.join(ruta_script, "Texto1.txt")

with open(ruta_archivo, "w+", encoding="utf-8") as archivo: # El parámetro más el +, hace que el archivo sea leible
    archivo.write("Primera linea\n")
    archivo.write("Lo que va a continuacion\n")
    archivo.seek(0)
    print(archivo.read())
    
ruta_script = os.path.dirname(__file__)
ruta_archivo = os.path.join(ruta_script, "Texto1.txt")

with open(ruta_archivo, "a+", encoding="utf-8") as archivo:
    archivo.write("Última línea añadida con append\n")
    archivo.seek(0)
    print(archivo.read())


ruta_script = os.path.dirname(__file__)
ruta_archivo = os.path.join(ruta_script, "Texto1.txt")

with open(ruta_archivo, "x+", encoding="utf-8") as archivo: # La x crear un archivo y arroja error si ya existe en ese mismo directorio
    archivo.write("Archivo creado con x")
    archivo.seek(0)
    print(archivo.read())