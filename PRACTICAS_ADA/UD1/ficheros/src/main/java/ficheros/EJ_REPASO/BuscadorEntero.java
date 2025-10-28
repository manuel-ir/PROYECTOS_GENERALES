package ficheros.EJ_REPASO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BuscadorEntero {

    private int maxEncontrado = Integer.MIN_VALUE;
    private String archivoMaximo = "Ninguno";

    private static final String RUTA_BASE = "/home/alumnadotarde/Escritorio";
    private static final String DIR_PRUEBA = RUTA_BASE + File.separator + "busqueda_entero";

    public static void main(String[] args) {

        try {
            crearArchivosDePrueba();
        } catch (Exception e) {
            System.err.println("Error al crear el archivo de prueba");
            return;
        }

        Scanner sc = new Scanner(System.in);
        File directorioBusqueda;
        BuscadorEntero buscador = new BuscadorEntero();

        while (true) {

            System.out.println("\nIntroduce la ruta del directorio donde buscar");

            String ruta = sc.nextLine();

            directorioBusqueda = new File(ruta);

            if (directorioBusqueda.exists() && directorioBusqueda.isDirectory()) {
                break;
            } else {
                System.err.println("Error, eldirectorio no existe o no se encuentra");
            }

        }

        String resultado = buscador.encontrarMaximoEntero(directorioBusqueda);
        System.out.println("\nBusqueda Finalizada");
        System.out.println(resultado);

        sc.close();
    }

    public static void crearArchivosDePrueba() throws IOException {
        File dirPrincipal = new File(DIR_PRUEBA);
        File subDir = new File(DIR_PRUEBA, "subcarpeta");

        dirPrincipal.mkdirs();
        subDir.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("10 50 20 150 2");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("5 99 30 -50 10");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("300 1 2 3");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("10 5 300 299");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("301 0 50");
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(new File(dirPrincipal, "datos1.dat")))) {
            pw.print("1000 5000 9999");
        }

    }

    public String encontarMaximoEntero

}
