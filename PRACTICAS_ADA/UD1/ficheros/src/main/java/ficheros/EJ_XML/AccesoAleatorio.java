package ficheros.EJ_XML;


import java.io.EOFException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class AccesoAleatorio {
   public AccesoAleatorio() {
   }

   public static void main(String[] args) {
      cambiarNota();
   }

   public static void cambiarNota() {
      try {
         Throwable var0 = null;
         Object var1 = null;

         try {
            RandomAccessFile raf = new RandomAccessFile("notas", "rw");

            try {
               int id = false;

               try {
                  for(int id = raf.readInt(); id != 2; id = raf.readInt()) {
                     raf.skipBytes(12);
                  }

                  raf.skipBytes(2);
                  raf.writeDouble(7.0);
               } catch (EOFException var13) {
                  System.out.println("He salido del archivo sin encontrar el alumno");
               }
            } finally {
               if (raf != null) {
                  raf.close();
               }

            }
         } catch (Throwable var15) {
            if (var0 == null) {
               var0 = var15;
            } else if (var0 != var15) {
               var0.addSuppressed(var15);
            }

            throw var0;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

   }
}
