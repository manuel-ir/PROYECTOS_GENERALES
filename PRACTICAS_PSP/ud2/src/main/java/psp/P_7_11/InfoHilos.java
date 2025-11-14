package psp.P_7_11;

public class InfoHilos {

    public static void main(String[] args) throws InterruptedException {

        // Crea un nuevo hilo simple que solo espera un momento
        Thread hiloNuevo = new Thread(() -> {
            try {
                // Hace que el hilo duerma 100ms
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Obtiene el hilo actual (el hilo main)
        Thread hiloPrincipal = Thread.currentThread();
        hiloPrincipal.setName("Hilo-Principal"); // Le pone un nombre

        // Muestra información ANTES de iniciar hiloNuevo
        System.out.println("ANTES DE INICIAR");
        System.out.println("Hilo Principal");
        mostrarInfo(hiloPrincipal);
        System.out.println("Hilo Nuevo");
        mostrarInfo(hiloNuevo); // Estado debe ser NEW

        // Inicia el nuevo hilo
        hiloNuevo.start();

        // Muestra información MIENTRAS hiloNuevo está vivo
        System.out.println("\nDURANTE LA EJECUCIÓN");
        System.out.println("Hilo Principal");
        mostrarInfo(hiloPrincipal); // Estado debe ser RUNNABLE
        System.out.println("Hilo Nuevo");
        mostrarInfo(hiloNuevo); // Estado puede ser RUNNABLE o TIMED_WAITING

        // Espera a que el nuevo hilo termine
        hiloNuevo.join();

        // Muestra información DESPUÉS de que hiloNuevo termine
        System.out.println("\nDESPUÉS DE TERMINAR");
        System.out.println("Hilo Principal");
        mostrarInfo(hiloPrincipal);
        System.out.println("Hilo Nuevo");
        mostrarInfo(hiloNuevo); // Estado debe ser TERMINATED
    }

     // Método auxiliar para imprimir información de un hilo.
     
    public static void mostrarInfo(Thread t) {
        // Obtiene varios datos usando los métodos de la clase Thread
        System.out.println("  Nombre: " + t.getName());
        System.out.println("  ID: " + t.getId());
        System.out.println("  Estado: " + t.getState());
        System.out.println("  Prioridad: " + t.getPriority());
        System.out.println("  ¿Está vivo?: " + t.isAlive());
        System.out.println("  ¿Es Daemon?: " + t.isDaemon());
    }
}