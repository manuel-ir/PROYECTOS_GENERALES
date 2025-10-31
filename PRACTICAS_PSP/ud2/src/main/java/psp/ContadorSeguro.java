package psp;

// ContadorSeguro.java

/*
 * EJERCICIO 2: Contador Seguro Multihilo
 *
 * 1. Creamos la clase 'Contador' que almacena el valor compartido.
 * Esta clase NO es un hilo, es solo un objeto que los hilos usarán.
 */
class Contador {
    private int valor = 0; // El recurso compartido que queremos proteger

    /*
     * La palabra 'synchronized' convierte este método en una "sección crítica".
     * Significa que solo UN HILO a la vez puede estar ejecutando este método
     * sobre la MISMA instancia de 'Contador'.
     * Actúa como un cerrojo (lock): el primer hilo que llega, cierra la puerta,
     * ejecuta el código, y abre la puerta al salir. Los demás hilos esperan en la puerta.
     */
    public synchronized void incrementar() {
        // Esta operación (leer valor, sumarle 1, escribir valor)
        // ahora es "atómica" gracias a synchronized, evitando la condición de carrera.
        this.valor++;
    }

    // También sincronizamos el método de lectura.
    // Esto asegura que un hilo no lea el valor mientras otro lo está modificando.
    public synchronized int obtenerValor() {
        return this.valor;
    }
}

/*
 * 2. Creamos la TAREA que realizarán los hilos.
 * Implementamos 'Runnable', que es la forma recomendada de crear tareas.
 * Un 'Runnable' es solo un "trabajo para hacer", no es un hilo en sí mismo.
 */
class TareaIncremento implements Runnable {
    // Guardamos una referencia al ÚNICO objeto Contador que todos compartirán
    private Contador contadorCompartido;

    // Constructor para "inyectar" el contador compartido
    public TareaIncremento(Contador contador) {
        this.contadorCompartido = contador;
    }

    @Override
    public void run() {
        // Este es el trabajo: cada hilo llamará 1000 veces a incrementar()
        for (int i = 0; i < 1000; i++) {
            contadorCompartido.incrementar();
        }
        // Cuando el bucle termina, el método run() termina y el hilo "muere".
    }
}

// Clase principal para ejecutar el programa
public class ContadorSeguro {
    public static void main(String[] args) throws InterruptedException {

        // Creamos UNA ÚNICA instancia del Contador.
        // Este es el objeto que protegeremos y compartiremos.
        Contador contador = new Contador();

        // Creamos UNA ÚNICA instancia de la TAREA.
        // Le pasamos el contador que debe modificar.
        Runnable tarea = new TareaIncremento(contador);

        // Creamos un array para guardar nuestros 10 hilos
        Thread[] hilos = new Thread[10];

        System.out.println("Lanzando 10 hilos...");

        // Creamos e iniciamos los 10 hilos
        for (int i = 0; i < 10; i++) {
            // Creamos un nuevo Hilo (Thread) y le pasamos la TAREA (Runnable) que debe ejecutar.
            // LOS 10 HILOS APUNTAN A LA MISMA TAREA, QUE APUNTA AL MISMO CONTADOR.
            hilos[i] = new Thread(tarea);
            hilos[i].start(); // Iniciamos el hilo
        }

        // Esperamos a que TODOS los hilos terminen
        for (int i = 0; i < 10; i++) {
            hilos[i].join(); // El hilo main espera a que el hilo i termine
        }

        /*
         * Gracias a 'synchronized' en el método incrementar(),
         * no se perdió ningún incremento, y el resultado final debe ser 10.000
         * (10 hilos * 1000 incrementos cada uno).
         */
        System.out.println("Valor final del contador: " + contador.obtenerValor());
    }
}