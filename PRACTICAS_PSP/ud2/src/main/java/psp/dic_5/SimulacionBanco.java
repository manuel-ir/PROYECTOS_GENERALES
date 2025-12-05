package psp.dic_5;

import java.util.Random;

public class SimulacionBanco {

    public static void main(String[] args) {
        // Inicializa la caja fuerte con el saldo inicial especificado
        CajaFuerte cajaFuerte = new CajaFuerte(10000);

        // Crea los hilos de los cajeros compartiendo la misma instancia de caja fuerte
        Cajero cajero1 = new Cajero(cajaFuerte, "Cajero-1");
        Cajero cajero2 = new Cajero(cajaFuerte, "Cajero-2");
        Cajero cajero3 = new Cajero(cajaFuerte, "Cajero-3");

        // Crea un hilo encargado de reponer dinero automáticamente para que la simulación continúe
        AgenteRecarga recarga = new AgenteRecarga(cajaFuerte);

        // Inicia la ejecución de todos los hilos
        cajero1.start();
        cajero2.start();
        cajero3.start();
        recarga.start();
    }
}

/**
 * Recurso compartido donde se almacena el dinero.
 * Utiliza métodos sincronizados para garantizar la exclusión mutua.
 */
class CajaFuerte {
    private int dineroDisponible;

    public CajaFuerte(int dineroInicial) {
        this.dineroDisponible = dineroInicial;
    }

    /**
     * Gestiona la retirada de dinero de forma segura.
     * Si no hay saldo, el hilo espera.
     */
    public synchronized void retirar(int cantidad, String nombreCajero) {
        System.out.println("[" + nombreCajero + "] intenta retirar " + cantidad + " € | Dinero actual: " + dineroDisponible + " €");

        // Utiliza un bucle while para reevaluar la condición tras despertar
        // Evita condiciones de carrera si otro hilo retira el dinero antes
        while (dineroDisponible < cantidad) {
            System.out.println(">>> [" + nombreCajero + "] ESPERA: Dinero insuficiente para retirar " + cantidad + " €");
            try {
                // Libera el monitor y pone el hilo en espera hasta ser notificado
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Resta la cantidad solicitada una vez asegurado el saldo
        dineroDisponible -= cantidad;
        System.out.println("+++ [" + nombreCajero + "] RETIRO EXITOSO. Dinero restante: " + dineroDisponible + " €");
    }

    /**
     * Permite ingresar dinero en la caja y notifica a los hilos en espera.
     */
    public synchronized void depositar(int cantidad) {
        // Aumenta el saldo disponible
        dineroDisponible += cantidad;
        System.out.println("\n*** [SISTEMA] Se han repuesto " + cantidad + " € en la caja. Total: " + dineroDisponible + " € ***\n");

        // Despierta a todos los hilos que estaban bloqueados en wait()
        // Permite que intenten realizar su retiro nuevamente
        notifyAll();
    }
}

/**
 * Hilo que simula un cajero automático.
 * Realiza retiros aleatorios indefinidamente.
 */
class Cajero extends Thread {
    private final CajaFuerte caja;
    private final Random random;

    public Cajero(CajaFuerte caja, String nombre) {
        super(nombre);
        this.caja = caja;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            // Ejecuta el ciclo de atención de forma indefinida
            while (true) {
                // Genera una cantidad aleatoria a retirar entre 50 y 500
                int cantidadRetiro = random.nextInt(451) + 50;

                // Intenta retirar el dinero de la caja compartida
                caja.retirar(cantidadRetiro, this.getName());

                // Simula el tiempo de procesamiento de la operación (50 a 200 ms)
                Thread.sleep(random.nextInt(151) + 50);
            }
        } catch (InterruptedException e) {
            System.out.println(this.getName() + " interrumpido.");
        }
    }
}

/**
 * Hilo auxiliar para simular la reposición automática de dinero.
 * Evita que la simulación se detenga por falta de fondos.
 */
class AgenteRecarga extends Thread {
    private final CajaFuerte caja;

    public AgenteRecarga(CajaFuerte caja) {
        this.caja = caja;
        // Establece el hilo como Daemon para que finalice si los hilos principales terminan
        setDaemon(true); 
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Espera un tiempo considerable antes de recargar (ej. cada 3 segundos)
                Thread.sleep(3000);
                
                // Repone una cantidad fija de dinero
                caja.depositar(5000);
            }
        } catch (InterruptedException e) {
            // Finaliza el hilo silenciosamente
        }
    }
}

