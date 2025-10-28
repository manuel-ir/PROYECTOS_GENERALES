package ficheros.SUELTOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ej_2 {
    public static void main(String[] args) {
        try {
            // Crea ProcessBuilder para el comando específico
            ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "tasklist", "/svc", "/fi", "imagename eq svchost.exe");
            // Inicia el proceso
            Process p = pb.start();
            // Obtiene el stream de entrada (salida del proceso)
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "CP850")); // Codificación para caracteres especiales
            // Crea archivo de salida
            File archivo = new File("SVCHOST.TXT");
            PrintWriter pw = new PrintWriter(new FileWriter(archivo));
            
            // Leer la salida del proceso y escribir en el archivo
            String linea;
            pw.println("=== SERVICIOS EJECUTÁNDOSE BAJO SVCHOST.EXE ===");
            pw.println("Fecha: " + java.time.LocalDateTime.now());
            pw.println("=================================================");
            
            while ((linea = br.readLine()) != null) {
                pw.println(linea);
                System.out.println(linea); // También muestra en la consola
            }
                        // Espera a que el proceso termine
            int exitCode = p.waitFor();
            pw.println("\nEl comando terminó con código: " + exitCode);
            
            // Cierra recursos
            br.close();
            pw.close();
            
            System.out.println("\nLa información se ha guardado en: " + archivo.getAbsolutePath());
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

