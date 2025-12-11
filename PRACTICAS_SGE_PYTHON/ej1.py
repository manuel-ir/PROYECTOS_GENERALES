# Ejercicio 1: Saludo personalizado

print("Nombre introducido: " + input("Introduce tu nombre: "))


# Ejercicio 2: Operaciones matemáticas

print(((3+2)/(2*5))**2)


# Ejercicio 3: Suma de números desde 1 hasta n

n = int(input("Introduce un número: "))
suma = (n*(n+1))/2
print("La suma de los números desde 1 hasta " + str(n) + " es: " + str(suma))   


# Ejercicio 4: Índice de masa corporal

imc = float(input("Introduce tu peso en kg: ")) / (float(input("Introduce tu altura en metros: ")) ** 2)
print("Tu índice de masa corporal es: " + str(imc))


# Ejercicio 5: Cociente y resto de una división

a= int(input("introduce un entero: ")) 
b= int(input("introduce otro entero: "))

cociente = a // b # Devuelve la parte entera del resultado redondeando hacia abajo. La / sola devuelve parte decimal
resto = a % b # Devuelve el resto de la división

print("El resto de la división es: " + str(resto) + " y el cociente es: " + str(cociente))

# Ejercicio 6: Validar mayoría de edad

edad = int(input("Introduce tu edad: "))
if edad >= 18:
    print("Eres mayor de edad.")
else:
   print("Eres menor de edad.")

# Ejercicio 7: Validación de contraseña

password = input("Introduce la contraseña: ")
password1  = input("Confirma la contraseña: ")

if password.lower() == password1.lower():
    print("Contraseña correcta.")
else:
    print("Contraseña incorrecta.")

# Ejercicio 8: Número par o impar

a= int(input("Introduce un número entero: "))
if a % 2 == 0:
    print("El número es par.")
else:
    print("El número es impar.")

# Ejercicio 9: Obligación de tributar

edad = int(input("Introduce tu edad: "))
ingresos = float(input("Introduce tus ingresos): "))
if edad < 18 and ingresos > 1000:
    print("No puedes tributar.")
elif edad >= 18 and ingresos < 1000:
    print("No tienes que tributar.")
else:
    print("Tienes que tributar.")

# Ejercicio 10: Repetir palabra

palabra = str(input("Introduce una palabra: "))
for i in range (10):
    print("1." + palabra)

# Ejercicio 11: Repetir edad

edad = int(input("Introduce tu edad: "))
for i in range(edad):
    print ("Tienes " + str(i+1) + " años")

# Ejercicio 12: Pirámide de asteriscos
# Si metemos num solo como parametro, se come la última fila ya que multiplica * por 0, por lo que no lo imprime, para ello debemos poner el rango que empiece en 1

num = int(input("Introduce un número entero: "))
for i in range(1, num + 1):
     print("*"*i)

# Ejercicio 13: Tabla de multiplicar

n = int(input("Introduce un número entero del 1 al 10: "))
for i in range(1, 10+1):
    print(str(n) + " x " + str(i) + " = " + str(n*i))
    