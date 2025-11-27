package psp.nov_21;

class Cuenta {
    private String id;
    private double saldo;

    public Cuenta(String id, double saldoInicial) {
        this.id = id;
        this.saldo = saldoInicial;
    }

    public String getId() { return id; }

    // Métodos synchronized para gestión de saldo
    public synchronized void ingresar(double cantidad) {
        saldo += cantidad;
    }

    public synchronized boolean retirar(double cantidad) {
        if (saldo >= cantidad) {
            saldo -= cantidad;
            return true;
        }
        return false;
    }

    public synchronized double getSaldo() { return saldo; }
}

class GestorTransferencias {
    public static void transferir(Cuenta c1, Cuenta c2, double cantidad) {
        
        synchronized (c1) { // 1. Bloquea la cuenta origen
            
            // Esto imprimirá un mensaje justo antes de que se produzca el desastre
            System.out.println(Thread.currentThread().getName() + " tiene " + c1.getId() + " y quiere " + c2.getId());
            
            try { 
                Thread.sleep(20); 
            } catch (InterruptedException e) {}

            synchronized (c2) { // 2. Intenta bloquear la cuenta destino (Aquí se quedará pillado)
                
                if (c1.retirar(cantidad)) {
                    c2.ingresar(cantidad);
                    System.out.println("Transferencia realizada: " + c1.getId() + " -> " + c2.getId());
                } else {
                    System.out.println("Saldo insuficiente en " + c1.getId());
                }
            }
        }
    }
}

public class TransferenciaSinBloqueo {
    public static void main(String[] args) {
        final Cuenta cuentaA = new Cuenta("ES001", 1000);
        final Cuenta cuentaB = new Cuenta("ES002", 1000);

        // Hilo 1: Transfiere de A a B
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                GestorTransferencias.transferir(cuentaA, cuentaB, 10);
            }
        });

        // Hilo 2: Transfiere de B a A
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                GestorTransferencias.transferir(cuentaB, cuentaA, 10);
            }
        });

        t1.start();
        t2.start();
    }
}
