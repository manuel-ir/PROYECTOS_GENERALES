package psp.P_7_11;

import java.util.Random;

class HiloPausado extends Thread {

    private Random random = new Random();

    public HiloPausado(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(getName() + " - paso " + i);

            // Calcula una pausa entre 10ms (min) y 500ms (max) (random.nextInt(max - min + 1) + min)
            
            int pausa = random.nextInt(500 - 10 + 1) + 10;

            try {
                // El hilo actual duerme por el tiempo aleatorio
                Thread.sleep(pausa);
            } catch (InterruptedException e) {
                // Esta excepción es obligatoria de capturar
                System.out.println(getName() + " fue interrumpido.");
            }
        }
        System.out.println(">>> " + getName() + " ha terminado.");
    }
}

// Clase principal para probar los hilos y el método join()

public class PausasConJoin {

    public static void main(String[] args) {

        System.out.println("El hilo main inicia la ejecución.");

        // Crea los dos hilos
        HiloPausado hilo1 = new HiloPausado("Hilo 1");
        HiloPausado hilo2 = new HiloPausado("Hilo 2");

        // Inicia los hilos para que se ejecuten en paralelo
        hilo1.start();
        hilo2.start();

        // Usa join() para esperar a que los hilos terminen
        try {
            
            // El hilo main se bloquea aquí y espera hasta que 'hilo1' termine su ejecución
             
            System.out.println("Main espera a que Hilo 1 termine...");
            hilo1.join();

           // Una vez hilo1 ha terminado, el main continúa y se bloquea aquí, esperando a que 'hilo2' termine
             
            System.out.println("Main espera a que Hilo 2 termine...");
            hilo2.join();

        } catch (InterruptedException e) {
            // join() también puede lanzar esta excepción
            System.out.println("El hilo main fue interrumpido mientras esperaba.");
        }

        // Este mensaje solo se imprimirá cuando ambos hilos hayan terminado
        System.out.println(" El hilo main ha terminado.");
    }
}
