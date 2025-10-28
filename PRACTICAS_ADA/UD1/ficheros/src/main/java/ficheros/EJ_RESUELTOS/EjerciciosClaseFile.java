package ficheros.EJ_RESUELTOS;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class EjerciciosClaseFile {
   public EjerciciosClaseFile() {
   }

   public static void main(String[] args) {
   }

   public static void listarDirectoriosRecursivo(File dir, int nivelTab) {
      String tabulacion = "";

      for(int i = 0; i < nivelTab; ++i) {
         tabulacion = tabulacion + "\t";
      }

      if (dir.isDirectory()) {
         File[] archiviosDentro = dir.listFiles();
         File[] var7 = archiviosDentro;
         int var6 = archiviosDentro.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            File f = var7[var5];
            if (f.isDirectory()) {
               System.out.println(tabulacion + f.getName());
               ++nivelTab;
               listarDirectoriosRecursivo(f, nivelTab);
               --nivelTab;
            } else {
               System.out.println(tabulacion + f.getName());
            }
         }
      } else {
         System.out.println("No ha introducido un directorio.");
      }

   }

   public static void mostrarArchivosConExtension(File dir, String extension) {
      if (dir.exists() && dir.isDirectory()) {
         File[] archivos = dir.listFiles();
         ArrayList<File> archivosConExtensionX = new ArrayList();
         File[] var7 = archivos;
         int var6 = archivos.length;

         File archivo;
         for(int var5 = 0; var5 < var6; ++var5) {
            archivo = var7[var5];
            if (archivo.getName().endsWith(extension)) {
               archivosConExtensionX.add(archivo);
            }
         }

         if (archivosConExtensionX.size() == 0) {
            System.out.println("No se encontraron archivos con la extensión ." + extension);
         } else {
            System.out.println("Archivos con extensión ." + extension + ":");
            Iterator var8 = archivosConExtensionX.iterator();

            while(var8.hasNext()) {
               archivo = (File)var8.next();
               System.out.println("\t" + archivo.getName());
            }
         }

      } else {
         System.out.println("La ruta no es un directorio válido.");
      }
   }

   public static void borrarArchivosTxt(File dir) {
      String extension = "txt";
      if (dir.exists() && dir.isDirectory()) {
         File[] archivos = dir.listFiles();
         ArrayList<File> archivosConExtensionX = new ArrayList();
         File[] var7 = archivos;
         int var6 = archivos.length;

         File archivo;
         for(int var5 = 0; var5 < var6; ++var5) {
            archivo = var7[var5];
            if (archivo.getName().endsWith(extension)) {
               archivosConExtensionX.add(archivo);
            }
         }

         if (archivosConExtensionX.size() == 0) {
            System.out.println("No hay ningún archivo a borrar.");
         } else {
            System.out.println("Archivos con extensión ." + extension + ":");
            Iterator var8 = archivosConExtensionX.iterator();

            while(var8.hasNext()) {
               archivo = (File)var8.next();
               if (archivo.delete()) {
                  System.out.println("Se ha borrado el archivo " + archivo.getName());
               } else {
                  System.out.println("No se ha podido borrar el archivo " + archivo.getName());
               }
            }
         }

      } else {
         System.out.println("La ruta no es un directorio válido.");
      }
   }
}