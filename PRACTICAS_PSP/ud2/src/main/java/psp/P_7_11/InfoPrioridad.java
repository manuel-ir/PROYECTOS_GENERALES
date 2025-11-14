package psp.P_7_11;

public class InfoPrioridad {

    public static void main(String[] args) {
        // Obtiene la prioridad mínima que un hilo puede tener
        int minPrioridad = Thread.MIN_PRIORITY;

        // Obtiene la prioridad máxima que un hilo puede tener
        int maxPrioridad = Thread.MAX_PRIORITY;

        // Obtiene la prioridad por defecto
        int defaultPrioridad = Thread.NORM_PRIORITY;

        System.out.println("Información de Prioridades de Hilos en Java");
        System.out.println("Prioridad Mínima: " + minPrioridad + " (Constante: Thread.MIN_PRIORITY)");
        System.out.println("Prioridad Máxima: " + maxPrioridad + " (Constante: Thread.MAX_PRIORITY)");
        System.out.println("Prioridad Normal (por defecto): " + defaultPrioridad + " (Constante: Thread.NORM_PRIORITY)");
    }
}