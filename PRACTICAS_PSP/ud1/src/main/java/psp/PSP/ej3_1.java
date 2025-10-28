package psp.PSP;

import java.io.*;

public class ej3_1 {


    public static void main(String[] args) {
        try {
            System.out.println("=== CONTANDO PROCESOS BASH ===");
            
            // Ejecutar ps aux para listar procesos
            Process proceso = new ProcessBuilder("ps", "aux").start();
            
            // Leer la salida
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(proceso.getInputStream()));
            
            int contador = 0;
            String linea;
            
            System.out.println("Procesos encontrados:");
            System.out.println("---------------------");
            
            // Contar procesos de bash y MOSTRARLOS
            while ((linea = reader.readLine()) != null) {
                if (linea.contains("bash")) {
                    contador++;
                    System.out.println(contador + ". " + linea);
                }
            }
            
            reader.close();
            proceso.waitFor();
            
            System.out.println("---------------------");
            System.out.println("Total de procesos 'bash': " + contador);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
