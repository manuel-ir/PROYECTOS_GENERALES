package psp.nov_28;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

class Libro {
    private final int id;
    // Usamos ReentrantLock como pide el ejercicio para gestionar la concurrencia
    private final ReentrantLock cerrojo;

    public Libro(int id) {
        this.id = id;
        this.cerrojo = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    // Método para adquirir el libro (bloqueante)
    public void adquirir() {
        cerrojo.lock();
    }

    // Método para devolver el libro
    public void liberar() {
        cerrojo.unlock();
    }
}

// Representa el hilo cliente (Estudiante)
class Estudiante extends Thread {
    private final int idEstudiante;
    private final Libro[] biblioteca; // Referencia a todos los libros disponibles
    private final Random random;
    
    // Configuración de tiempo: 1 minuto simulado = 1 milisegundo real
    private static final int MS_POR_MINUTO = 1;

    public Estudiante(int id, Libro[] libros) {
        this.idEstudiante = id;
        this.biblioteca = libros;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // El ciclo se repite indefinidamente hasta que el hilo sea interrumpido
            while (!Thread.currentThread().isInterrupted()) {
                
                // Selecciona dos libros al azar (índices distintos)
                int idx1 = random.nextInt(biblioteca.length);
                int idx2 = random.nextInt(biblioteca.length);
                while (idx1 == idx2) {
                    idx2 = random.nextInt(biblioteca.length);
                }

                Libro l1 = biblioteca[idx1];
                Libro l2 = biblioteca[idx2];

                
                // ESTRATEGIA PARA EVITAR DEADLOCK (Interbloqueo)
                
                // Siempre adquirimos el cerrojo del libro con ID menor primero.
                // Esto impone un orden global en la adquisición de recursos.
                Libro primero = (l1.getId() < l2.getId()) ? l1 : l2;
                Libro segundo = (l1.getId() < l2.getId()) ? l2 : l1;

                System.out.println("Estudiante " + idEstudiante + " quiere leer libros " + l1.getId() + " y " + l2.getId());

                // Adquiere los libros en orden
                primero.adquirir();
                try {
                    segundo.adquirir();
                    try {
                        // Sección Crítica: Usar los libros
                        System.out.println("--> Estudiante " + idEstudiante + " ESTÁ USANDO libros " + primero.getId() + " y " + segundo.getId());
                        
                        // Tiempo de uso: entre 1 y 3 horas (60 a 180 minutos)
                        int minutosUso = 60 + random.nextInt(121); 
                        Thread.sleep(minutosUso * MS_POR_MINUTO);

                    } finally {
                        // Siempre liberamos el segundo recurso en el finally interno
                        segundo.liberar();
                    }
                } finally {
                    // Siempre liberamos el primer recurso en el finally externo
                    System.out.println("<-- Estudiante " + idEstudiante + " DEVUELVE libros " + primero.getId() + " y " + segundo.getId());
                    primero.liberar();
                }

                // Descanso: entre 1 y 2 horas (60 a 120 minutos)
                int minutosDescanso = 60 + random.nextInt(61);
                System.out.println("Estudiante " + idEstudiante + " descansa " + minutosDescanso + " min. simulados.");
                Thread.sleep(minutosDescanso * MS_POR_MINUTO);
            }
        } catch (InterruptedException e) {
            System.out.println("Estudiante " + idEstudiante + " ha terminado su sesión (Interrumpido).");
        }
    }
}

public class SimulacionBiblioteca {
    public static void main(String[] args) {
        // Configuración inicial
        final int NUM_LIBROS = 9;
        final int NUM_ESTUDIANTES = 4;

        // Crea el array de libros (recursos compartidos)
        Libro[] libros = new Libro[NUM_LIBROS];
        for (int i = 0; i < NUM_LIBROS; i++) {
            libros[i] = new Libro(i);
        }

        System.out.println("INICIO DE LA SIMULACIÓN DE BIBLIOTECA");

        // Crea y lanzar los hilos de estudiantes
        Thread[] hilosEstudiantes = new Thread[NUM_ESTUDIANTES];
        for (int i = 0; i < NUM_ESTUDIANTES; i++) {
            hilosEstudiantes[i] = new Estudiante(i + 1, libros);
            hilosEstudiantes[i].start();
        }

        // Se ejecuta durante un tiempo y detener para no dejarlo infinito
        try {
            Thread.sleep(10000); // Ejecuta la simulación durante 10 segundos reales
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("DETENIENDO SIMULACIÓN");
        for (Thread t : hilosEstudiantes) {
            t.interrupt(); // Solicitamos a los hilos que terminen
        }
    }
}
