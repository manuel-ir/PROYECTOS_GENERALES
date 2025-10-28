package ficheros.EJ_REPASO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class EjerciciosRepasoFicheros {

    /**
     * Ejercicio 1: Muestra los nombres de los archivos y directorios en la ruta dada.
     */
    public static void listarContenidoDirectorio(String ruta) {
        File dir = new File(ruta);
        // Comprobamos que la ruta existe y es un directorio
        if (dir.exists() && dir.isDirectory()) {
            File[] contenido = dir.listFiles();
            System.out.println("--- Contenido de " + dir.getAbsolutePath() + " ---");
            if (contenido != null) {
                // Recorremos el array
                for (File f : contenido) {
                    if (f.isDirectory()) {
                        System.out.println("[DIR]  " + f.getName());
                    } else {
                        System.out.println("[FILE] " + f.getName());
                    }
                }
            }
        } else {
            System.out.println("La ruta '" + ruta + "' no es un directorio válido.");
        }
    }

    /**
     * Ejercicio 2: Muestra los archivos en un directorio que coinciden con una extensión.
     */
    public static void mostrarArchivosConExtension(String ruta, String extension) {
        File dir = new File(ruta);
        // Validamos que sea un directorio
        if (dir.exists() && dir.isDirectory()) {
            File[] archivos = dir.listFiles();
            if (archivos == null) {
                System.out.println("No se pudo leer el directorio.");
                return;
            }
            // Usamos un ArrayList para guardar los archivos que coinciden
            ArrayList<File> archivosFiltrados = new ArrayList<>();
            for (File f : archivos) {
                // Comprobamos que es un fichero y que el nombre termina con la extensión
                if (f.isFile() && f.getName().endsWith(extension)) {
                    archivosFiltrados.add(f);
                }
            }
            // Mostramos los resultados
            if (archivosFiltrados.isEmpty()) {
                System.out.println("No se encontraron archivos con la extensión " + extension);
            } else {
                System.out.println("--- Archivos con extensión " + extension + " en " + ruta + " ---");
                for (File f : archivosFiltrados) {
                    System.out.println("\t" + f.getName());
                }
            }
        } else {
            System.out.println("La ruta '" + ruta + "' no es un directorio válido.");
        }
    }

    /**
     * Ejercicio 3: Copia un archivo origen a un destino, con bandera de reemplazo.
     */
    public static void copiarArchivo(String rutaOrigen, String rutaDestino, boolean reemplazar) throws IOException {
        File origen = new File(rutaOrigen);
        File destino = new File(rutaDestino);
        // Validar que el origen existe y es un fichero
        if (!origen.exists() || !origen.isFile()) {
            throw new IOException("El archivo de origen no existe o no es un fichero.");
        }
        File destinoFinal;
        // Si el destino es un directorio...
        if (destino.isDirectory()) {
            // ...el fichero final será dentro de ese directorio
            destinoFinal = new File(destino, origen.getName());
        } else {
            // Si no, asumimos que es la ruta completa del fichero
            destinoFinal = destino;
        }
        // Si el destino existe y la bandera es false, lanzamos excepción
        if (destinoFinal.exists() && !reemplazar) {
            throw new IOException("El archivo de destino '" + destinoFinal.getAbsolutePath() + "' ya existe y la bandera de reemplazo es false.");
        }
        // Usamos flujos de bytes (try-with-resources) para la copia
        try (FileInputStream fis = new FileInputStream(origen);
             FileOutputStream fos = new FileOutputStream(destinoFinal)) {
            // Creamos un buffer para leer por partes
            byte[] buffer = new byte[4096];
            int bytesLeidos;
            // Leemos del origen y escribimos en el destino
            while ((bytesLeidos = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesLeidos);
            }
            System.out.println("Archivo copiado exitosamente de '" + rutaOrigen + "' a '" + destinoFinal.getAbsolutePath() + "'");
        }
    }

    /**
     * Ejercicio 4: Copia un directorio (origen) y todo su contenido recursivamente
     * en un directorio destino.
     */
    public static void copiarDirectorioRecursivo(String rutaOrigen, String rutaDestino) throws IOException {
        File dirOrigen = new File(rutaOrigen);
        File dirDestino = new File(rutaDestino);
        // Validamos el origen
        if (!dirOrigen.exists() || !dirOrigen.isDirectory()) {
            throw new IOException("El directorio de origen no existe o no es válido.");
        }
        // Si el destino no existe, lo creamos (incluyendo padres)
        if (!dirDestino.exists()) {
            if (!dirDestino.mkdirs()) {
                throw new IOException("No se pudo crear el directorio destino: " + rutaDestino);
            }
        }
        // Obtenemos el contenido del origen
        File[] contenido = dirOrigen.listFiles();
        if (contenido == null) {
            return; // Directorio vacío o error
        }
        // Recorremos cada item
        for (File item : contenido) {
            String nuevoOrigen = item.getAbsolutePath();
            String nuevoDestino = dirDestino.getAbsolutePath() + File.separator + item.getName();

            if (item.isDirectory()) {
                // Si es un directorio, llamamos recursivamente
                copiarDirectorioRecursivo(nuevoOrigen, nuevoDestino);
            } else {
                // Si es un archivo, lo copiamos (lógica Ex 3)
                try (FileInputStream fis = new FileInputStream(item);
                     FileOutputStream fos = new FileOutputStream(nuevoDestino)) {
                    byte[] buffer = new byte[4096];
                    int bytesLeidos;
                    while ((bytesLeidos = fis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesLeidos);
                    }
                }
            }
        }
    }

    /**
     * Ejercicio 5: Escribe arrays de referencias (int) y precios (double) en un
     * archivo binario.
     */
    public static void guardarArticulos(String nombreArchivo, int[] referencias, double[] precios) throws IOException {
        if (referencias.length != precios.length) {
            throw new IllegalArgumentException("Los arrays deben tener la misma longitud.");
        }
        // Usamos DataOutputStream para escribir tipos primitivos (binario)
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombreArchivo))) {
            // Recorremos los arrays y escribimos las parejas
            for (int i = 0; i < referencias.length; i++) {
                dos.writeInt(referencias[i]);
                dos.writeDouble(precios[i]);
            }
            System.out.println("Datos de artículos guardados en " + nombreArchivo);
        }
    }

    /**
     * Ejercicio 6: Lee el archivo de artículos (Ex 5) y muestra su contenido.
     */
    public static void mostrarArticulos(String nombreArchivo) throws IOException {
        System.out.println("--- Contenido del archivo " + nombreArchivo + " ---");
        // Usamos DataInputStream para leer tipos primitivos
        try (DataInputStream dis = new DataInputStream(new FileInputStream(nombreArchivo))) {
            // Leemos en un bucle infinito
            while (true) {
                // Leemos en el mismo orden que escribimos (int, double)
                int referencia = dis.readInt();
                double precio = dis.readDouble();
                System.out.printf("\tReferencia: %d, Precio: %.2f€%n", referencia, precio);
            }
        } catch (EOFException e) {
            // Capturamos la excepción de Fin de Fichero (esperada)
            System.out.println("--- Fin del archivo ---");
        }
    }

    /**
     * Ejercicio 7: Actualiza los precios en el archivo de artículos (Ex 5).
     */
    public static void actualizarPrecios(String nombreArchivo) throws IOException {
        // Usamos RandomAccessFile para leer y escribir ("rw")
        try (RandomAccessFile raf = new RandomAccessFile(nombreArchivo, "rw")) {
            long posicion = 0;
            long longitudTotal = raf.length();
            // Mientras nuestra posición no llegue al final
            while (posicion < longitudTotal) {
                // 1. Nos posicionamos para leer
                raf.seek(posicion);
                // 2. Leemos los datos
                int referencia = raf.readInt();
                double precio = raf.readDouble();
                // 3. Calculamos nuevo precio
                double nuevoPrecio;
                if (precio >= 100.0) {
                    nuevoPrecio = precio * 0.5; // Decremento 50%
                } else {
                    nuevoPrecio = precio * 1.5; // Incremento 50%
                }
                // 4. Retrocedemos el puntero (solo el double) y sobrescribimos
                raf.seek(posicion + 4); // 4 bytes del int
                raf.writeDouble(nuevoPrecio);
                // 5. Avanzamos al siguiente registro (4 + 8 = 12 bytes)
                posicion += 12;
            }
            System.out.println("Precios actualizados en " + nombreArchivo);
        }
    }


    /**
     * Método Main para probar todos los ejercicios.
     * MODIFICADO: Usa la ruta absoluta del Escritorio.
     * MODIFICADO: No usa 'finally' para borrar los ficheros.
     */
    public static void main(String[] args) {

        // --- Definimos la ruta base ---
        String rutaBase = "/home/alumnadotarde/Escritorio";
        
        // --- Preparamos un entorno de prueba ---
        String dirPrueba = rutaBase + File.separator + "directorio_prueba";
        String subDir = dirPrueba + File.separator + "subdir";
        String dirCopia = rutaBase + File.separator + "directorio_copia";
        String archivoPrueba1 = dirPrueba + File.separator + "doc1.txt";
        String archivoPrueba2 = dirPrueba + File.separator + "imagen.jpg";
        String archivoPrueba3 = subDir + File.separator + "doc2.txt";
        String archivoCopia = dirPrueba + File.separator + "copia_doc1.txt";
        String archivoArticulos = rutaBase + File.separator + "articulos.dat";

        System.out.println("--- Creando ficheros y directorios de prueba en: ---");
        System.out.println(rutaBase);

        try {
            // --- Creación del entorno ---
            new File(dirPrueba).mkdir();
            new File(subDir).mkdir();
            new File(archivoPrueba1).createNewFile();
            new File(archivoPrueba2).createNewFile();
            new File(archivoPrueba3).createNewFile();

            System.out.println("\n*** EJERCICIO 1: Listar Directorio ***");
            listarContenidoDirectorio(dirPrueba);

            System.out.println("\n*** EJERCICIO 2: Filtrar por Extensión ***");
            mostrarArchivosConExtension(dirPrueba, ".txt");

            System.out.println("\n*** EJERCICIO 3: Copiar Archivo ***");
            // Copiamos doc1.txt (reemplazando)
            copiarArchivo(archivoPrueba1, archivoCopia, true);
            // Intentamos copiar de nuevo sin reemplazar (debe fallar)
            try {
                copiarArchivo(archivoPrueba1, archivoCopia, false);
            } catch (IOException e) {
                System.out.println("Error esperado (está bien que falle): " + e.getMessage());
            }
            // Copiamos doc2.txt al directorio padre
            copiarArchivo(archivoPrueba3, dirPrueba, true);

            System.out.println("\n*** EJERCICIO 4: Copiar Directorio Recursivo ***");
            copiarDirectorioRecursivo(dirPrueba, dirCopia);
            System.out.println("--- Verificando copia recursiva (Listando destino) ---");
            listarContenidoDirectorio(dirCopia);
            listarContenidoDirectorio(dirCopia + File.separator + "subdir");

            // --- Pruebas Ejercicios 5, 6 y 7 ---
            int[] refs = {101, 102, 103, 104};
            double[] precios = {50.0, 150.0, 99.0, 200.0};

            System.out.println("\n*** EJERCICIO 5: Guardar Artículos (Binario) ***");
            guardarArticulos(archivoArticulos, refs, precios);

            System.out.println("\n*** EJERCICIO 6: Mostrar Artículos (Binario) ***");
            mostrarArticulos(archivoArticulos);

            System.out.println("\n*** EJERCICIO 7: Actualizar Precios (Aleatorio) ***");
            actualizarPrecios(archivoArticulos);
            System.out.println("--- Precios después de actualizar ---");
            mostrarArticulos(archivoArticulos);
            
            // --- Mensajes finales ---
            // (El bloque 'finally' se ha eliminado)
            System.out.println("\n--- Ejecución finalizada ---");
            System.out.println("Los ficheros de prueba NO han sido borrados.");
            System.out.println("Puedes encontrarlos en: " + rutaBase);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

