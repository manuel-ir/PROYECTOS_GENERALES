package psp;

// EjercicioCajas.java
import java.util.Random; // Importamos Random, aunque usaremos Math.random() que es más directo.

/*
 * EJERCICIO 1: Simulación de Cajas de Supermercado
 *
 * Creamos una clase 'Caja' que hereda de 'Thread'.
 * Cada instancia de 'Caja' será un hilo independiente.
 */
class Caja extends Thread {

    // Constructor que recibe el nombre del hilo
    public Caja(String nombre) {
        // Llamamos al constructor de la clase padre (Thread) para asignar el nombre
        super(nombre);
    }

    // El método run() contiene el código que ejecutará el hilo cuando se inicie
    @Override
    public void run() {
        try {
            /*
             * Math.random() devuelve un valor entre 0.0 y 0.999...
             * Multiplicamos por 4001 para tener un rango de 0 a 4000.
             * Sumamos 1000 para desplazar el rango a 1000 - 5000 (milisegundos).
             */
            int tiempoCliente = (int) (Math.random() * 4001) + 1000;

            // Imprimimos un mensaje indicando qué hilo (caja) empieza y cuánto tardará
            System.out.println(getName() + " comienza a atender un cliente (durará " + (tiempoCliente / 1000) + "s)");

            /*
             * Thread.sleep() pone a "dormir" al hilo actual (a esta caja).
             * Libera la CPU y le dice al sistema "no me despiertes hasta que pasen X milisegundos".
             * Esto simula el tiempo que la caja tarda en atender al cliente.
             */
            Thread.sleep(tiempoCliente);

            // Una vez que el hilo "despierta" (pasa el tiempo), imprime que ha terminado
            System.out.println(getName() + " ha terminado de atender al cliente.");

        } catch (InterruptedException e) {
            /*
             * Si otro hilo "interrumpe" a este hilo mientras duerme, se lanzará
             * esta excepción. Es obligatorio manejarla (hacerle 'try-catch').
             */
            System.out.println(getName() + " fue interrumpido.");
        }
    }
}

// Clase principal que contiene el método main para ejecutar el programa
public class EjercicioCajas {
    
    // 'throws InterruptedException' es necesario porque llamaremos a join()
    public static void main(String[] args) throws InterruptedException {

        System.out.println("--- ABRIENDO LAS CAJAS ---");

        // 1. Creamos las 3 instancias de nuestros hilos (las 3 cajas)
        Caja caja1 = new Caja("Caja 1");
        Caja caja2 = new Caja("Caja 2");
        Caja caja3 = new Caja("Caja 3");

        // 2. Iniciamos los hilos con start()
        // IMPORTANTE: NUNCA se llama a run() directamente.
        // start() le pide al sistema que cree un nuevo hilo de ejecución y llame a run() por nosotros.
        caja1.start();
        caja2.start();
        caja3.start();

        /*
         * 3. Usamos join() para esperar a que los hilos terminen.
         * El hilo 'main' (el que ejecuta este código) se detendrá en caja1.join()
         * y no continuará hasta que el hilo 'caja1' haya terminado su método run().
         */
        caja1.join();
        
        // Una vez que caja1 termina, el 'main' sigue y se detiene en caja2.join()
        caja2.join();
        
        // Finalmente, espera a que caja3 termine.
        caja3.join();

        /*
         * 4. Este mensaje solo se imprimirá DESPUÉS de que los tres hilos
         * hayan terminado, gracias a join().
         */
        System.out.println("--- TODAS LAS CAJAS HAN TERMINADO SU JORNADA ---");
    }
}