package ficheros.EX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class BuscadorEnteroMaximo {

    // Variables para guardar el maximo global
    private int maxEncontrado = Integer.MIN_VALUE;
    private String archivoMaximo = "Ninguno";
    
    // Ruta base de pruebas
    private static final String RUTA_BASE = "/home/alumnadotarde/Escritorio";
    private static final String DIR_PRUEBA = RUTA_BASE + File.separator + "busqueda_enteros";

    
    public static void main(String[] args) {
        
        // Creamos los archivos de prueba
        try {
                    crearArchivosDePrueba();
        } catch (IOException e) {
            System.err.println("Error al crear archivos de prueba: " + e.getMessage());
            return; 
        }

        Scanner sc = new Scanner(System.in);
        File directorioBusqueda;
        BuscadorEnteroMaximo buscador = new BuscadorEnteroMaximo();

        // Bucle de validación de directorio
        while (true) {
            System.out.println("\nIntroduce la ruta del directorio donde buscar:");
            String ruta = sc.nextLine();
            
            directorioBusqueda = new File(ruta);
            
            if (directorioBusqueda.exists() && directorioBusqueda.isDirectory()) {
                break; 
            } else {
                System.err.println("Error: El directorio no existe o no es válido.");
            }
        }
        
        // Llamada al método de búsqueda
        String resultado = buscador.encontrarMaximoEntero(directorioBusqueda);
        
        // Muestra resultado final
        System.out.println("\nBúsqueda Finalizada:");
        System.out.println(resultado);
        
        sc.close();
    }

    /*
     * Método para crear los archivos
     */
    public static void crearArchivosDePrueba() throws IOException {
        System.out.println("Creando archivos de prueba en: " + DIR_PRUEBA);
        File dirPrincipal = new File(DIR_PRUEBA);
        
        dirPrincipal.mkdirs();

        // Fichero 1
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("10 50 20 150 2");
        }
        // Fichero 2
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos2.dat")))) {
            pw.print("5 99 30 -50 10");
        }
        // Fichero 3 
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos3.dat")))) {
            pw.print("10 5 150 149"); 
        }
        // Fichero 4 
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos4.dat")))) {
            pw.print("200 1 2 3"); 
        }
        // Fichero 5
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "ignorar.txt")))) {
            pw.print("1000 5000 9999");
        }
        System.out.println("Archivos de prueba creados.");
    }


    /*
     Método encontrar máximo
     */
    public String encontrarMaximoEntero(File directorio) {
        this.maxEncontrado = Integer.MIN_VALUE;
        this.archivoMaximo = "Ninguno";

        // Obtenemos todo el contenido del directorio
        File[] contenido = directorio.listFiles();

        // Sale si el directorio está vacío o no se puede leer
        if (contenido == null) {
            return "El directorio está vacío o no se puede leer.";
        }

        // Recorremos cada fichero o carpeta
        for (File f : contenido) {
            // solo ejecuta si es un fichero y termina en .dat
            if (f.isFile() && f.getName().endsWith(".dat")) {
                procesarArchivoDat(f);
            }
        }

        // Devolvemos el string formateado
        if (this.archivoMaximo.equals("Ninguno")) {
            return "No se encontraron archivos .dat con números enteros.";
        } else {
            return "El número más alto es " + this.maxEncontrado + " y se ha encontrado en " + this.archivoMaximo;
        }
    }

    /* Método que lee un fichero .dat y actualiza el máximo.*/
    private void procesarArchivoDat(File archivoDat) {
        
        try (BufferedReader br = new BufferedReader(new FileReader(archivoDat))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por espacios
                String[] numerosComoTexto = linea.split(" ");
                
                for (String s : numerosComoTexto) {
                    try {
                        // Convertimos el texto a entero
                        int numero = Integer.parseInt(s.trim());
                        
                        // Si (numero > max) se actualiza.
                        // Si (numero == max) no se actualiza 
                        if (numero > this.maxEncontrado) {
                            this.maxEncontrado = numero;
                            this.archivoMaximo = archivoDat.getAbsolutePath();
                        }
                    } catch (NumberFormatException e) {
                       
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + archivoDat.getAbsolutePath());
        }
    }
}
