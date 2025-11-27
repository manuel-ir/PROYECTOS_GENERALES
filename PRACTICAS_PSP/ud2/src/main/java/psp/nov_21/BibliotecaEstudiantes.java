package psp.nov_21;

import java.util.Random;

class Libro {
    private int id;
    public Libro(int id) { this.id = id; }
    public int getId() { return id; }
}

class Estudiante extends Thread {
    private int idEstudiante;
    private Libro[] libros;
    private Random random = new Random();

    public Estudiante(int id, Libro[] libros) {
        this.idEstudiante = id;
        this.libros = libros;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Seleccionar dos libros al azar (índices distintos)
                int idx1 = random.nextInt(libros.length);
                int idx2 = random.nextInt(libros.length);
                while (idx1 == idx2) {
                    idx2 = random.nextInt(libros.length);
                }

                // Determinamos el orden para evitar DEADLOCK 
                // Siempre bloqueamos primero el índice menor y luego el mayor.
                Libro libro1 = libros[Math.min(idx1, idx2)];
                Libro libro2 = libros[Math.max(idx1, idx2)];

                System.out.println("Estudiante " + idEstudiante + " quiere libros " + libro1.getId() + " y " + libro2.getId());

                // 2. Intenta coger los libros (sincronización ordenada)
                synchronized (libro1) {
                    synchronized (libro2) {
                        System.out.println("--> Estudiante " + idEstudiante + " TIENE libros " + libro1.getId() + " y " + libro2.getId() + ". Leyendo...");
                        
                        // 3. Usa tiempos aleatorios (1 a 3 horas simuladas -> 60 a 180 ms)
                        int tiempoLectura = 60 + random.nextInt(121); // 60 a 180
                        Thread.sleep(tiempoLectura);
                        
                        System.out.println("<-- Estudiante " + idEstudiante + " DEVUELVE libros " + libro1.getId() + " y " + libro2.getId());
                    }
                }

                // Descansa (1 a 2 horas simuladas -> 60 a 120 ms)
                int tiempoDescanso = 60 + random.nextInt(61); // 60 a 120
                System.out.println("Estudiante " + idEstudiante + " descansando " + tiempoDescanso + "ms...");
                Thread.sleep(tiempoDescanso);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class BibliotecaEstudiantes {
    public static void main(String[] args) {
        // Crea 9 libros
        Libro[] libros = new Libro[9];
        for (int i = 0; i < 9; i++) {
            libros[i] = new Libro(i);
        }

        // Crea 4 estudiantes y lanzarlos
        for (int i = 0; i < 4; i++) {
            new Estudiante(i, libros).start();
        }
    }
}
