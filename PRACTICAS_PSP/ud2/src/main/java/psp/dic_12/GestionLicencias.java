package psp.dic_12;

import java.util.Random;

public class GestionLicencias {

    public static void main(String[] args) {
        // Crea el inventario con 30 copias iniciales
        Inventario inventario = new Inventario(30);

        // Crea e inicia los 5 hilos de empleados mediante un bucle
        for (int i = 1; i <= 5; i++) {
            Empleado empleado = new Empleado(inventario, "Empleado " + i);
            empleado.start();
        }

        // Crea el hilo reponedor que vigilara el stock bajo
        Reponedor reponedor = new Reponedor(inventario);
        // Configura el reponedor como servicio para finalizar si los empleados terminan
        reponedor.setDaemon(true);
        reponedor.start();
    }
}

class Inventario {
    private int stock;

    public Inventario(int stockInicial) {
        this.stock = stockInicial;
    }

    // Metodo sincronizado para gestionar la venta de licencias
    public synchronized void vender(String nombreEmpleado) {
        // Usa un bucle while para verificar la condicion tras despertar
        while (stock < 1) {
            System.out.println(nombreEmpleado + " ESPERA stock agotado");
            try {
                // Libera el monitor y detiene el hilo hasta recibir notificacion
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Decrementa el stock tras confirmar existencias
        stock--;
        System.out.println(nombreEmpleado + " vendio una copia quedando " + stock);
    }

    // Metodo sincronizado para reponer el inventario
    public synchronized void reponer(int cantidad) {
        // Incrementa el stock con la cantidad recibida
        stock += cantidad;
        System.out.println("REPONEDOR anade " + cantidad + " copias dejando el total en " + stock);
        
        // Despierta a todos los hilos empleados que estaban esperando
        notifyAll();
    }

    // Consulta el stock de forma segura para el reponedor
    public synchronized int obtenerStock() {
        return stock;
    }
}

class Empleado extends Thread {
    private final Inventario inventario;
    private final Random random;

    public Empleado(Inventario inventario, String nombre) {
        super(nombre);
        this.inventario = inventario;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // Ejecuta el ciclo de ventas de forma indefinida
            while (true) {
                // Intenta realizar una venta en el inventario compartido
                inventario.vender(this.getName());

                // Simula el tiempo de procesamiento entre 100 y 400 ms
                int tiempoProcesamiento = 100 + random.nextInt(301);
                Thread.sleep(tiempoProcesamiento);
            }
        } catch (InterruptedException e) {
            System.out.println(this.getName() + " finalizado");
        }
    }
}

class Reponedor extends Thread {
    private final Inventario inventario;

    public Reponedor(Inventario inventario) {
        this.inventario = inventario;
    }

    @Override
    public void run() {
        try {
            // Revisa constantemente el estado del inventario
            while (true) {
                // Comprueba si el stock es critico menor a 5 unidades
                if (inventario.obtenerStock() < 5) {
                    // Repone 10 unidades si el stock es bajo
                    inventario.reponer(10);
                }

                // Espera un breve periodo para no saturar el procesador
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            System.out.println("Hilo reponedor finalizado");
        }
    }
}
