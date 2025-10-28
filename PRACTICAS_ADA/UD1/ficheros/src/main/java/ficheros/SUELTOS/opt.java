package ficheros.SUELTOS;

import java.io.File;
import java.io.IOException;

public class opt {
    
    public static void main(String[] args) {

        // 1. Configuración base - UNA SOLA VEZ
        File directorioBase = new File(System.getProperty("user.home"), "Desktop");
        File directorioRaiz = new File(directorioBase, "d");
        
        // 2. Ejecutar ejercicios
        crearEstructura(directorioRaiz);
        
        System.out.println("\n EJERCICIO 2: Listado ");
        listarDirectorio(directorioRaiz, "");
        
        System.out.println("\n EJERCICIO 3: Listado Modificado ");
        listarModificado(directorioRaiz);
        
        System.out.println("\n EJERCICIO 4: Archivos .txt en d1 ");
        File directorioD1 = new File(directorioRaiz, "d1");
        listarPorExtension(directorioD1, ".txt");
        
        System.out.println("\n EJERCICIO 5: Eliminar archivos .txt en d1 ");
        eliminarArchivos(directorioD1, ".txt");
        
        // Verificar resultado final
        System.out.println("\n ESTRUCTURA FINAL ");
        listarDirectorio(directorioRaiz, "");
    }
    
    // EJERCICIO 1: Crear estructura
    public static void crearEstructura(File directorioRaiz) {
        try {
            // Crear directorio principal
            directorioRaiz.mkdir();
            
            // Directorio d1 y sus archivos
            File d1 = new File(directorioRaiz, "d1");
            d1.mkdir();
            new File(d1, "f11.txt").createNewFile();
            new File(d1, "f12.txt").createNewFile();
            
            // Directorio d2 y subdirectorios
            File d2 = new File(directorioRaiz, "d2");
            d2.mkdir();
            File d21 = new File(d2, "d21");
            d21.mkdir();
            new File(d2, "f21.txt").createNewFile();
            File d22 = new File(d2, "d22");
            d22.mkdir();
            new File(d22, "f222.txt").createNewFile();
            
            // Directorio d3
            File d3 = new File(directorioRaiz, "d3");
            d3.mkdir();
            File d31 = new File(d3, "d31");
            d31.mkdir();
            
        } catch (IOException e) {
            System.err.println("Error creando estructura: " + e.getMessage());
        }
    }
    
    // EJERCICIO 2: Listar directorio recursivo
    public static void listarDirectorio(File directorio, String indentacion) {
        if (!directorio.exists()) {
            System.out.println(indentacion + "[NO EXISTE] " + directorio.getName());
            return;
        }
        
        System.out.println(indentacion + "[DIR] " + directorio.getName());
        
        File[] elementos = directorio.listFiles();
        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isDirectory()) {
                    listarDirectorio(elemento, indentacion + "  ");
                } else {
                    System.out.println(indentacion + "  [ARCHIVO] " + elemento.getName());
                }
            }
        }
    }
    
    // EJERCICIO 3: Listado con validación
    public static void listarModificado(File directorio) {
        if (directorio.exists() && directorio.isDirectory()) {
            listarDirectorio(directorio, "");
        } else {
            System.out.println("El directorio no existe o no es un directorio válido");
        }
    }
    
    // EJERCICIO 4: Listar por extensión (MEJORADO)
    public static void listarPorExtension(File directorio, String extension) {
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.out.println("Directorio no válido: " + directorio.getAbsolutePath());
            return;
        }
        
        System.out.println("Archivos con extensión '" + extension + "' en " + directorio.getName() + ":");
        
        File[] elementos = directorio.listFiles();
        boolean encontrados = false;
        
        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isFile() && elemento.getName().toLowerCase().endsWith(extension.toLowerCase())) {
                    System.out.println("  • " + elemento.getName());
                    encontrados = true;
                }
            }
        }
        
        if (!encontrados) {
            System.out.println("  No se encontraron archivos con esa extensión");
        }
    }
    
    // EJERCICIO 5: Eliminar archivos (MEJORADO)
    public static void eliminarArchivos(File directorio, String extension) {
        if (!directorio.exists() || !directorio.isDirectory()) {
            System.out.println("Directorio no válido para eliminar archivos");
            return;
        }
        
        File[] elementos = directorio.listFiles();
        int eliminados = 0;
        
        if (elementos != null) {
            for (File elemento : elementos) {
                if (elemento.isFile() && elemento.getName().toLowerCase().endsWith(extension.toLowerCase())) {
                    if (elemento.delete()) {
                        System.out.println("  Eliminado: " + elemento.getName());
                        eliminados++;
                    } else {
                        System.out.println("  Error eliminando: " + elemento.getName());
                    }
                }
            }
        }
        
        System.out.println("Se eliminaron " + eliminados + " archivos correctamente.");
    }
}
