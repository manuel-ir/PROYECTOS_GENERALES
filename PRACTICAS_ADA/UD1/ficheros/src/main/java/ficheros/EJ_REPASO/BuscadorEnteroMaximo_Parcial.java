package ficheros.EJ_REPASO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BuscadorEnteroMaximo_Parcial {

    private int maxEncontrado = Integer.MIN_VALUE;
    private String archivoMaximo = "Ninguno";
    
    private static final String RUTA_BASE = "/home/alumnadotarde/Escritorio";
    private static final String DIR_PRUEBA = RUTA_BASE + File.separator + "busqueda_enteros";

    public static void main(String[] args) {
        
        try {
            crearArchivosDePrueba();
        } catch (IOException e) {
            System.err.println("Error fatal al crear archivos de prueba: " + e.getMessage());
            return;
        }

        Scanner sc = new Scanner(System.in);
        File directorioBusqueda;
        BuscadorEnteroMaximo_Parcial buscador = new BuscadorEnteroMaximo_Parcial();

        while (true) {
            System.out.println("\nIntroduce la ruta del directorio donde buscar:");
            System.out.println("(Sugerencia: " + DIR_PRUEBA + ")");
            String ruta = sc.nextLine();
            
            directorioBusqueda = new File(ruta);
            
            if (directorioBusqueda.exists() && directorioBusqueda.isDirectory()) {
                break; 
            } else {
                System.err.println("Error: El directorio no existe o no es válido. Inténtalo de nuevo.");
            }
        }
        
        String resultado = buscador.encontrarMaximoEntero(directorioBusqueda);
        
        System.out.println("\nBúsqueda Finalizada:");
        System.out.println(resultado);
        
        sc.close();
    }

    public static void crearArchivosDePrueba() throws IOException {
        System.out.println("Creando archivos de prueba en: " + DIR_PRUEBA);
        File dirPrincipal = new File(DIR_PRUEBA);
        File subDir = new File(DIR_PRUEBA, "subcarpeta"); 

        dirPrincipal.mkdirs(); 
        subDir.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("10 50 20 150 2");
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos2.dat")))) {
            pw.print("5 99 30 150 10"); 
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(subDir, "datos3.dat")))) {
            pw.print("5000"); 
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "ignorar.txt")))) {
            pw.print("9999");
        }
        System.out.println("Archivos de prueba creados.");
    }


    public String encontrarMaximoEntero(File directorio) {
        this.maxEncontrado = Integer.MIN_VALUE;
        this.archivoMaximo = "Ninguno";

        File[] contenido = directorio.listFiles();

        if (contenido == null) {
            return "El directorio está vacío.";
        }

        for (File f : contenido) {
            
            if (f.isFile() && f.getName().endsWith(".dat")) {
                procesarArchivoDat(f);
            }
        }

        if (this.archivoMaximo.equals("Ninguno")) {
            return "No se encontraron archivos .dat con números enteros.";
        } else {
            return "El número más alto es " + this.maxEncontrado + " y se ha encontrado en " + this.archivoMaximo;
        }
    }

    private void procesarArchivoDat(File archivoDat) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivoDat))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                String[] numerosComoTexto = linea.split(" ");
                
                for (String s : numerosComoTexto) {
                    
                    int numero = Integer.parseInt(s.trim());
                        
                    if (numero >= this.maxEncontrado) { 
                        this.maxEncontrado = numero;
                        this.archivoMaximo = archivoDat.getAbsolutePath();
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + archivoDat.getAbsolutePath());
        } catch (NumberFormatException e) {
            System.err.println("Error: El fichero " + archivoDat.getName() + " contiene datos no numéricos.");
        }
    }
}
