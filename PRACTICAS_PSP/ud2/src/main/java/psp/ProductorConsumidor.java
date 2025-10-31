package psp;

// ProductorConsumidor.java

/*
 * EJERCICIO 3: Modelo Productor-Consumidor
 *
 * 1. Creamos el 'Buffer' (o Almacén).
 * Es un objeto compartido que mediará entre el Productor y el Consumidor.
 * Solo puede almacenar UN dato a la vez.
 */
class Buffer {
    private int dato; // El "hueco" donde se guarda el número
    
    // 'disponible' es la bandera que controla el estado del buffer.
    // false = buffer vacío (el Productor debe rellenarlo)
    // true = buffer lleno (el Consumidor debe vaciarlo)
    private boolean disponible = false;

    /*
     * 2. Método para PRODUCIR (guardar) un dato.
     * Es 'synchronized' porque modifica el estado compartido (dato y disponible).
     */
    public synchronized void producir(int valor) throws InterruptedException {
        /*
         * Usamos un bucle 'while' en lugar de un 'if'.
         * Si el buffer está lleno ('disponible' es true), el productor debe esperar.
         * El 'while' es crucial por si el hilo es "despertado por error" (spurious wakeup),
         * así vuelve a comprobar la condición antes de continuar.
         */
        while (disponible) {
            // wait() hace 3 cosas:
            // 1. Libera el 'lock' (cerrojo) del 'synchronized' (para que el Consumidor pueda entrar).
            // 2. Pone al hilo Productor actual a "dormir".
            // 3. Cuando es despertado (por notify()), intenta readquirir el 'lock' antes de continuar.
            wait();
        }

        // Si el hilo está aquí, es porque 'disponible' es false (el buffer está vacío).
        // Así que produce el dato.
        this.dato = valor;
        this.disponible = true; // Marca el buffer como lleno
        System.out.println("Productor produce: " + valor);

        // notify() despierta a UN hilo que esté esperando (wait()) en este mismo objeto.
        // En este caso, despierta al Consumidor (si estaba esperando).
        notify();
    }

    /*
     * 3. Método para CONSUMIR (leer) un dato.
     * Es 'synchronized' por la misma razón: modifica el estado compartido.
     */
    public synchronized int consumir() throws InterruptedException {
        // Mientras el buffer esté vacío ('disponible' es false), el consumidor debe esperar.
        while (!disponible) {
            wait(); // Libera el lock y se pone a dormir, esperando al Productor.
        }

        // Si el hilo está aquí, es porque 'disponible' es true (el buffer está lleno).
        // Así que consume el dato.
        System.out.println("Consumidor consume: " + dato);
        this.disponible = false; // Marca el buffer como vacío

        // Notifica al Productor que el hueco está libre y puede volver a producir.
        notify();
        return dato; // Devuelve el dato consumido
    }
}

// 4. El Hilo Productor (implementa Runnable)
class Productor implements Runnable {
    private Buffer buffer; // Referencia al almacén compartido

    public Productor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            // Producirá los números del 1 al 10
            for (int i = 1; i <= 10; i++) {
                buffer.producir(i); // Intenta producir el número
                
                // Dormimos un poco para que la alternancia con el consumidor sea más visible
                Thread.sleep(100); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 5. El Hilo Consumidor (implementa Runnable)
class Consumidor implements Runnable {
    private Buffer buffer; // Referencia al MISMO almacén compartido

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            // Consumirá 10 números
            for (int i = 1; i <= 10; i++) {
                buffer.consumir(); // Intenta consumir el número
                
                // Dormimos un poco más para simular que consumir es más lento
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// 6. Clase principal para lanzar los hilos
public class ProductorConsumidor {
    public static void main(String[] args) {
        // Creamos UNA ÚNICA instancia del Buffer que ambos hilos compartirán
        Buffer bufferCompartido = new Buffer();

        // Creamos las tareas de Productor y Consumidor, pasándoles el MISMO buffer
        Runnable tareaProductor = new Productor(bufferCompartido);
        Runnable tareaConsumidor = new Consumidor(bufferCompartido);

        // Creamos los hilos, les asignamos sus tareas y les damos nombres
        Thread hiloProductor = new Thread(tareaProductor, "Productor");
        Thread hiloConsumidor = new Thread(tareaConsumidor, "Consumidor");

        // Lanzamos los hilos para que compitan por el buffer
        hiloProductor.start();
        hiloConsumidor.start();
        
        // No usamos join() aquí, simplemente dejamos que los hilos se ejecuten
        // y terminen cuando acaben sus bucles 'for'.
    }
}