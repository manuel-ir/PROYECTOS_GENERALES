package psp.PSP;

import java.io.*;

public class ej3_3 {

    public static void main(String[] args) {
        try {
            System.out.println("=== GUARDANDO SALIDA EN ARCHIVO ===");
            
            // Ejecutar proceso y MOSTRAR salida en consola
            Process procesoConsola = new ProcessBuilder("ps", "aux").start();
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(procesoConsola.getInputStream()));
            
            System.out.println("Mostrando primeros 5 procesos:");
            System.out.println("-------------------------------");
            
            int contador = 0;
            String linea;
            while ((linea = reader.readLine()) != null && contador < 5) {
                System.out.println(linea);
                contador++;
            }
            reader.close();
            procesoConsola.waitFor();
            
            // Ahora guardar en archivo
            ProcessBuilder pb = new ProcessBuilder("ps", "aux");
            pb.redirectOutput(new File("procesos.txt"));
            Process p = pb.start();
            p.waitFor();
            
            System.out.println("-------------------------------");
            System.out.println("Salida completa guardada en 'procesos.txt'");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
