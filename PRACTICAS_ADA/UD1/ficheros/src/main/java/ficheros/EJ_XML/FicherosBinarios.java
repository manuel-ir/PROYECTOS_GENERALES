package ficheros.EJ_XML;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class FicherosBinarios {
   public FicherosBinarios() {
   }

   public static void main(String[] args) {
      leerFichero();
   }

   public static void escribirFichero() {
      try {
         Throwable var0 = null;
         Object var1 = null;

         try {
            FileOutputStream fos = new FileOutputStream("notas");

            try {
               DataOutputStream dos = new DataOutputStream(fos);
               dos.writeInt(1);
               dos.writeChar(32);
               dos.writeDouble(8.75);
               dos.writeChar(10);
               dos.writeInt(2);
               dos.writeChar(32);
               dos.writeDouble(6.4);
               dos.writeChar(10);
               dos.writeInt(3);
               dos.writeChar(32);
               dos.writeDouble(4.97);
               dos.writeChar(10);
            } finally {
               if (fos != null) {
                  fos.close();
               }

            }
         } catch (Throwable var11) {
            if (var0 == null) {
               var0 = var11;
            } else if (var0 != var11) {
               var0.addSuppressed(var11);
            }

            throw var0;
         }
      } catch (IOException var12) {
         var12.printStackTrace();
      }

   }

   public static void obtenerNotasEnLista() {
      try {
         Throwable var0 = null;
         Object var1 = null;

         try {
            FileInputStream fis = new FileInputStream("notas");

            try {
               DataInputStream dis = new DataInputStream(fis);
               ArrayList<Double> notas = new ArrayList();

               try {
                  while(true) {
                     dis.readInt();
                     dis.readChar();
                     Double nota = dis.readDouble();
                     dis.readChar();
                     notas.add(nota);
                  }
               } catch (EOFException var16) {
                  System.out.println("He salidpo deñl bucle");
                  Iterator var7 = notas.iterator();

                  while(var7.hasNext()) {
                     Double d = (Double)var7.next();
                     System.out.println(d);
                  }
               }
            } finally {
               if (fis != null) {
                  fis.close();
               }

            }
         } catch (Throwable var18) {
            if (var0 == null) {
               var0 = var18;
            } else if (var0 != var18) {
               var0.addSuppressed(var18);
            }

            throw var0;
         }
      } catch (IOException var19) {
         var19.printStackTrace();
      }

   }

   public static void leerFichero() {
      try {
         Throwable var0 = null;
         Object var1 = null;

         try {
            FileInputStream fis = new FileInputStream("notas");

            try {
               DataInputStream dis = new DataInputStream(fis);

               try {
                  while(true) {
                     int id = dis.readInt();
                     dis.readChar();
                     Double nota = dis.readDouble();
                     dis.readChar();
                     System.out.println("" + id + " tiene la nota " + String.valueOf(nota));
                  }
               } catch (EOFException var15) {
                  System.out.println("He salidpo deñl bucle");
               }
            } finally {
               if (fis != null) {
                  fis.close();
               }

            }
         } catch (Throwable var17) {
            if (var0 == null) {
               var0 = var17;
            } else if (var0 != var17) {
               var0.addSuppressed(var17);
            }

            throw var0;
         }
      } catch (IOException var18) {
         var18.printStackTrace();
      }

   }
}
