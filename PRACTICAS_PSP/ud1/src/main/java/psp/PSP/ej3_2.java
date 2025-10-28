package psp.PSP;

import java.io.*;

public class ej3_2 {
    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("=== INICIANDO DOS PROCESOS CON HILOS ===");
        
        // Hilo 1 - Listar archivos
        Thread hilo1 = new Thread(() -> {
            try {
                System.out.println("Hilo 1: Listando archivos...");
                Process p = new ProcessBuilder("ls", "-la").start();
                
                // LEER Y MOSTRAR SALIDA
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println("LS: " + linea);
                }
                reader.close();
                
                p.waitFor();
                System.out.println("Hilo 1: Terminado");
            } catch (Exception e) {
                System.out.println("Error hilo 1: " + e.getMessage());
            }
        });
        
        // Hilo 2 - Mostrar fecha
        Thread hilo2 = new Thread(() -> {
            try {
                System.out.println("Hilo 2: Mostrando fecha...");
                Process p = new ProcessBuilder("date").start();
                
                // LEER Y MOSTRAR SALIDA
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
                String linea;
                while ((linea = reader.readLine()) != null) {
                    System.out.println("DATE: " + linea);
                }
                reader.close();
                
                p.waitFor();
                System.out.println("Hilo 2: Terminado");
            } catch (Exception e) {
                System.out.println("Error hilo 2: " + e.getMessage());
            }
        });
        
        // Iniciar hilos
        hilo1.start();
        hilo2.start();
        
        // Esperar a que terminen
        hilo1.join();
        hilo2.join();
        
        System.out.println("=== AMBOS HILOS TERMINARON ===");
    }
}