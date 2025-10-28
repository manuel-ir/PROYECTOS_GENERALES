package ficheros.EJ_REPASO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class RepasoFicheroE {

    // Variables de instancia para guardar el maximo global
    // Se inicializa al valor más bajo posible
    private int maxEncontrado = Integer.MIN_VALUE;
    // Guarda la ruta absoluta del fichero con el máximo
    private String archivoMaximo = "Ninguno";
    
    // Constantes para la ruta base de pruebas
    private static final String RUTA_BASE = "/home/alumnadotarde/Escritorio";
    private static final String DIR_PRUEBA = RUTA_BASE + File.separator + "busqueda_enteros";

    /**
     * NOTA 3: Método principal para probar el buscador.
     * Crea los ficheros de prueba y pide el directorio al usuario.
     */
    public static void main(String[] args) {
        
        // 1. Primero, creamos los archivos de prueba en el Escritorio
        try {
            crearArchivosDePrueba();
        } catch (IOException e) {
            System.err.println("Error fatal al crear archivos de prueba: " + e.getMessage());
            return; // Salimos si no podemos crear los ficheros
        }

        Scanner sc = new Scanner(System.in);
        File directorioBusqueda;
        // Creamos una instancia de nuestra clase
        RepasoFicheroE buscador = new RepasoFicheroE();

        // 2. Bucle de validación de directorio (Petición del ejercicio)
        while (true) {
            System.out.println("\nIntroduce la ruta del directorio donde buscar:");
            
            String ruta = sc.nextLine();
            
            directorioBusqueda = new File(ruta);
            
            // Comprobamos si la ruta es válida
            if (directorioBusqueda.exists() && directorioBusqueda.isDirectory()) {
                break; // Directorio válido, salimos del bucle
            } else {
                // Mensaje de error y pedimos de nuevo
                System.err.println("Error: El directorio no existe o no es válido. Inténtalo de nuevo.");
            }
        }
        
        // 3. Llamada al método principal que hace la búsqueda
        String resultado = buscador.encontrarMaximoEntero(directorioBusqueda);
        
        // 4. Mostrar resultado final
        System.out.println("\nBúsqueda Finalizada:");
        System.out.println(resultado);
        
        sc.close();
    }

    /**
     * NOTA 3: Método para crear los archivos y carpetas de prueba.
     * Esto crea la estructura en /home/alumnadotarde/Escritorio/busqueda_enteros/
     */
    public static void crearArchivosDePrueba() throws IOException {
        System.out.println("Creando archivos de prueba en: " + DIR_PRUEBA);
        File dirPrincipal = new File(DIR_PRUEBA);
        File subDir = new File(DIR_PRUEBA, "subcarpeta");

        // Creamos los directorios
        dirPrincipal.mkdirs(); // Crea el directorio principal
        subDir.mkdirs(); // Crea el subdirectorio

        // NOTA 2: Creamos ficheros .dat con enteros separados por espacios
        // Usamos PrintWriter (Flujo de caracteres)
        
        // Fichero 1
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("10 50 20 150 2");
        }
        // Fichero 2
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos2.dat")))) {
            pw.print("5 99 30 -50 10");
        }
        // Fichero 3 (subcarpeta)
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(subDir, "datos3.dat")))) {
            pw.print("300 1 2 3"); // 300 es el maximo hasta ahora
        }
        // Fichero 4 (subcarpeta) (empate)
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(subDir, "datos4.dat")))) {
            pw.print("10 5 300 299"); // NOTA 1: Empate, se debe quedar con datos3.dat
        }
        // Fichero 5 (subcarpeta) (nuevo máximo)
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(subDir, "datos5.dat")))) {
            pw.print("301 0 50"); // 301 es el nuevo maximo
        }
        // Fichero 6 (será ignorado por la extensión)
        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(subDir, "ignorar.txt")))) {
            pw.print("1000 5000 9999");
        }
        System.out.println("Archivos de prueba creados.");
    }


    /**
     * 1. EJERCICIO: Método principal que inicia la búsqueda.
     * Recibe el directorio y devuelve el string con el resultado.
     */
    public String encontrarMaximoEntero(File directorio) {
        // Reiniciamos los valores por si se usa el objeto varias veces
        this.maxEncontrado = Integer.MIN_VALUE;
        this.archivoMaximo = "Ninguno";

        // Iniciamos la búsqueda recursiva
        buscarRecursivamente(directorio);

        // Devolvemos el string formateado
        if (this.archivoMaximo.equals("Ninguno")) {
            return "No se encontraron archivos .dat con números enteros.";
        } else {
            // Este es el formato de string pedido
            return "El número más alto es " + this.maxEncontrado + " y se ha encontrado en " + this.archivoMaximo;
        }
    }

    /**
     * Método recursivo privado que navega por los directorios.
     */
    private void buscarRecursivamente(File directorio) {
        // Obtenemos todo el contenido del directorio
        File[] contenido = directorio.listFiles();

        // Si el directorio está vacío o no se puede leer, salimos
        if (contenido == null) {
            return;
        }

        // Recorremos cada fichero o carpeta
        for (File f : contenido) {
            if (f.isDirectory()) {
                // Si es un directorio, volvemos a llamar (recursión)
                buscarRecursivamente(f);
            } else if (f.isFile() && f.getName().endsWith(".dat")) {
                // Si es un fichero .dat, lo procesamos
                procesarArchivoDat(f);
            }
            // Si no es directorio ni .dat, se ignora (ej: ignorar.txt)
        }
    }

    /**
     * NOTA 2: Método que lee un fichero .dat y actualiza el máximo.
     * Lee el fichero de texto, lo divide por espacios y compara los números.
     */
    private void procesarArchivoDat(File archivoDat) {
        // Usamos BufferedReader para leer líneas (Flujo de caracteres)
        try (BufferedReader br = new BufferedReader(new FileReader(archivoDat))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                // Separamos la línea por espacios
                String[] numerosComoTexto = linea.split(" ");
                
                for (String s : numerosComoTexto) {
                    try {
                        // Convertimos el texto a entero
                        int numero = Integer.parseInt(s.trim());
                        
                        // NOTA 1: Comprobamos si es el nuevo máximo
                        // Si (numero > max) se actualiza.
                        // Si (numero == max) NO se actualiza.
                        // Esto cumple la Nota 1 (quedarse con el primero encontrado).
                        if (numero > this.maxEncontrado) {
                            this.maxEncontrado = numero;
                            this.archivoMaximo = archivoDat.getAbsolutePath();
                        }
                    } catch (NumberFormatException e) {
                        // Ignoramos si un "trozo" no es un número
                        // (por ejemplo, si hay dos espacios seguidos)
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + archivoDat.getAbsolutePath());
        }
    }
}
