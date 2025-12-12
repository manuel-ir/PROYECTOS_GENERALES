# Imprime en pantalla "Hola mundo"
print("Hola mundo")

# Calcula e imprime la suma de 150 + 30
print(150+30)

# Concatena e imprime las cadenas "Hola ", "Marcos " y "Qué tal?"
print("Hola " + "Marcos " + "Qué tal?")

# Imprime en pantalla "¿Cómo te llamas? " seguido del nombre que el usuario introduce por teclado
print("¿Cómo te llamas? " + input("Dime tu nombre: "))

# Declara un input en a y lo reusa dentro del print concaternandolo
a= input("Dime un número: ")
print("El número que has introducido es: " + a)

# Concatena varias variables input y las imprime en una sola línea
a= input("Qué ciudad te gustaria visitar?: ")
b= input("Cuál es tu color favorito?:")
print("Te gustaría visitar " + a + " y tu color favorito es " + b)

# Tipos de datos en python:
# Texto: str
# Números enteros: int
# Números decimales: float
# Booleanos: bool (True/False)

# Declara dos variables numéricas, una entera y otra decimal, e imprime su tipo de dato
num1=9
num2=9.463523

print(type(num1))
print(type(num2))   

# Imprime el caracteres desde la posición 2 a la 5 de la cadena "Hola Mundo"
saludo= "Hola Mundo"
print(saludo[2:6])

# Cuenta las veces que aparece la letra "o" en la cadena "Hola Mundo" e imprime el resultado
print("Hola Mundo".count("o"))  

# Busca la posición de la subcadena "Mundo" en la cadena "Hola Mundo" e imprime el resultado. Si no la encuentra devuelve -1
# Index devolvería un error si no la encuentra
print("Hola Mundo".find("Mundo"))

print("Hola Mundo".index("Python"))

# Imprime la posición de la última aparición del carácter "/" en la cadena "/home/alumnadotarde"
print("/home/alumnadotarde".rfind("/"))

# Comprueba si la cadena "Hola Mundo" empieza por "Hola" y termina por "Mundo", e imprime el resultado (True/False)
print("Hola Mundo".startswith("Hola"))
print("Hola Mundo".endswith("Mundo"))

# isdigit(): Comprueba si todos los caracteres de la cadena son dígitos y devuelve True o False
print("Hola Mundo".isdigit())  # False
print("12345".isdigit())       # True

# isnumeric(): Comprueba si todos los caracteres de la cadena son numéricos y devuelve True o False (incluye caracteres como fracciones y números romanos)
print("hola Mundo".isnumeric())  # False
print("12345".isnumeric())       # True

# isalnum(): Comprueba si todos los caracteres de la cadena son alfanuméricos (letras y números) y devuelve True o False
print("Hola Mundo".isalnum())  # False
print("HolaMundo123".isalnum()) # True

# isalpha(): Comprueba si todos los caracteres de la cadena son letras y devuelve True o False
print("Hola Mundo".isalpha())  # False
print("HolaMundo".isalpha())    # True  

# lower() y upper(): Convierte todos los caracteres de la cadena a minúsculas o mayúsculas respectivamente
print("Hola Mundo".lower())  # hola mundo
print("Hola Mundo".upper())  # HOLA MUNDO

# isprintable(): Comprueba si todos los caracteres de la cadena son imprimibles y devuelve True o False (no son caracteres de control)
print("Hola Mundo".isprintable())  # True
print("Hola\nMundo".isprintable())  # False

# isspace(): Comprueba si todos los caracteres de la cadena son espacios en blanco y devuelve True o False
print("Hola Mundo".isspace())  # False
print("   ".isspace())         # True

# LISTAS

lista_1= [5, 2, 3, 7, 5] # Lista de números enteros
lista_2= ["PHP", "Python", "Java", "C++"] # Lista de cadenas de texto
lista_3= ["a", 2, "b", 4.5, True] # Lista mixta

# Añade el elemento 6 al final de la lista 
print(lista_1.append(6))

# Elimina el último elemento de la lista (también podemos eleminar un elemento en una posición concreta indicando su índice)
print(lista_1.pop()) 

# Ordena la lista de menor a mayor
print(lista_1.sort())  

# Invierte el orden de los elementos de la lista
print(lista_1.reverse())  

# Valores de comparación en Python:
# Igual: ==
# Distinto: !=
# Mayor que: >
# Menor que: <
# Mayor o igual que: >=
# Menor o igual que: <= 

# Estructuras condicionales en Python
# if, elif, else
a = 10
if (a > 5):
    print("El valor es mayor que 5")
elif (a == 5):
    print("El valor es igual a 5")
else:
    print("El valor es menor que 5")
    
# Estructuras repetitivas en Python
# while 
a = 0
while a < 5:
    print(a)
    a += 1

# for
for numero in [1, 2, 3, 4, 5]:
    print(numero)   

# Repite el bucle 5 veces e imprime el número de iteración en cada repetición
for i in range(5):
    print("Numero: ",i)
    
