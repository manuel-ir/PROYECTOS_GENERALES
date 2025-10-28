package ficheros.SUELTOS;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ej_1 {

    public static void main(String[] args) {
        try {
            // Crea ProcessBuilder para ejecutar tasklist
            ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "tasklist");
            
            // Inicia el proceso
            Process p = pb.start();
            
            // Obtiene el stream de entrada (salida del proceso)
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            // Lee y muestra la salida línea por línea
            String linea;
            System.out.println("=== LISTA DE PROCESOS EN EJECUCIÓN ===");
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
            
            // Espera a que el proceso termine
            int exitCode = p.waitFor();
            System.out.println("\nEl proceso terminó con código: " + exitCode);
            
            // Cierra recursos
            br.close();
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al ejecutar el comando: " + e.getMessage());
        }
    }
}

