package ficheros.EJ_RESUELTOS;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class EjerciciosAccesoAleatorio {
   public EjerciciosAccesoAleatorio() {
   }

   public static void main(String[] args) {
      File archivo = new File("/home/alumnadotarde/Escritorio/parejas.txt");
      archivoSinEspaciosYEnMayus(archivo);
   }

   public static void archivoSinEspaciosYEnMayus(File archivo) {
      if (archivo.exists() && archivo.isFile()) {
         try {
            Throwable var1 = null;
            Object var2 = null;

            try {
               RandomAccessFile raf = new RandomAccessFile(archivo, "rw");

               try {
                  int car = -1;

                  try {
                     while(true) {
                        car = raf.readByte();
                        if (car >= 97 && car <= 122) {
                           raf.seek(raf.getFilePointer() - 1L);
                           raf.writeByte(car - 32);
                        }

                        if (car == 32) {
                           desplazarContenidoArchivo(raf, raf.getFilePointer());
                           raf.seek(raf.getFilePointer() - 1L);
                        }
                     }
                  } catch (EOFException var14) {
                     System.out.println("Se ha leído el fichero por completo");
                  }
               } finally {
                  if (raf != null) {
                     raf.close();
                  }

               }
            } catch (Throwable var16) {
               if (var1 == null) {
                  var1 = var16;
               } else if (var1 != var16) {
                  var1.addSuppressed(var16);
               }

               throw var1;
            }
         } catch (IOException var17) {
            var17.printStackTrace();
         }
      } else {
         System.out.println("El archivo pasado por parámetro no es válido.");
      }

   }

      public static void desplazarContenidoArchivo(RandomAccessFile raf, long desdePos) throws IOException {
         int car = -1;

         try {
             car = raf.readByte();
         raf.seek(raf.getFilePointer() - 2L);
         raf.writeByte(car);

         while(true) {
            raf.seek(raf.getFilePointer() + 1L);
            car = raf.readByte();
            raf.seek(raf.getFilePointer() - 2L);
            raf.writeByte(car);
         }
      } catch (EOFException var5) {
         raf.setLength(raf.length() - 1L);
         raf.seek(desdePos);
      }
   }
}
