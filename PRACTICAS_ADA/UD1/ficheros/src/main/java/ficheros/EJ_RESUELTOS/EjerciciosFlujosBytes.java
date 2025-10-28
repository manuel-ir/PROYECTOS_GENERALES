package ficheros.EJ_RESUELTOS;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EjerciciosFlujosBytes {
   public EjerciciosFlujosBytes() {
   }

   public static void main(String[] args) {
      ejercicio2();
      leerDocBinario(new File("parejas.dat"));
      ejercicio3();
   }

   public static void ejercicio2() {
      Scanner sc = new Scanner(System.in);
      String nombreArchivo = "parejas.dat";

      try {
         Throwable var2 = null;
         Object var3 = null;

         try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombreArchivo));

            try {
               System.out.println("Introduce parejas de números enteros. Pulsa ENTER sin escribir nada para finalizar.");

               while(true) {
                  System.out.print("Número 1: ");
                  String linea1 = sc.nextLine().trim();
                  if (linea1.isEmpty()) {
                     break;
                  }

                  System.out.print("Número 2: ");
                  String linea2 = sc.nextLine().trim();
                  if (linea2.isEmpty()) {
                     break;
                  }

                  try {
                     int num1 = Integer.parseInt(linea1);
                     int num2 = Integer.parseInt(linea2);
                     dos.writeInt(num1);
                     dos.writeChar(32);
                     dos.writeInt(num2);
                     dos.writeChar(10);
                  } catch (NumberFormatException var17) {
                     System.out.println("Debes introducir un número entero válido.");
                  }
               }

               System.out.println("Archivo binario creado correctamente: " + nombreArchivo);
            } finally {
               if (dos != null) {
                  dos.close();
               }

            }
         } catch (Throwable var19) {
            if (var2 == null) {
               var2 = var19;
            } else if (var2 != var19) {
               var2.addSuppressed(var19);
            }

            throw var2;
         }
      } catch (IOException var20) {
         var20.printStackTrace();
      }

      sc.close();
   }

   public static void leerDocBinario(File f) {
      try {
         Throwable var1 = null;
         Object var2 = null;

         try {
            DataInputStream dis = new DataInputStream(new FileInputStream(f));

            try {
               while(true) {
                  System.out.print(dis.readInt());
                  System.out.print(dis.readChar());
                  System.out.print(dis.readInt());
                  System.out.print(dis.readChar());
               }
            } catch (EOFException var13) {
               System.out.println("Se ha leído el fichero por completo");
            } finally {
               if (dis != null) {
                  dis.close();
               }

            }
         } catch (Throwable var15) {
            if (var1 == null) {
               var1 = var15;
            } else if (var1 != var15) {
               var1.addSuppressed(var15);
            }

            throw var1;
         }
      } catch (IOException var16) {
         var16.printStackTrace();
      }

   }

   public static void ejercicio3() {
      String nombreArchivo = "parejas.dat";
      double media = 0.0;
      double mediaPonderada = 0.0;
      ArrayList<Integer> primeraColumna = new ArrayList();
      ArrayList<Integer> segundaColumna = new ArrayList();

      try {
         Throwable var7 = null;
         Object var8 = null;

         try {
            DataInputStream dis = new DataInputStream(new FileInputStream(nombreArchivo));

            try {
               while(true) {
                  primeraColumna.add(dis.readInt());
                  dis.readChar();
                  segundaColumna.add(dis.readInt());
                  dis.readChar();
               }
            } catch (EOFException var19) {
               System.out.println("Se ha leído el fichero por completo");
            } finally {
               if (dis != null) {
                  dis.close();
               }

            }
         } catch (Throwable var21) {
            if (var7 == null) {
               var7 = var21;
            } else if (var7 != var21) {
               var7.addSuppressed(var21);
            }

            throw var7;
         }
      } catch (IOException var22) {
         var22.printStackTrace();
      }

      int dividendoMediaPonderada = 0;

      for(int i = 0; i < primeraColumna.size(); ++i) {
         media += (double)(Integer)primeraColumna.get(i);
         mediaPonderada = (double)((Integer)primeraColumna.get(i) * (Integer)segundaColumna.get(i));
         dividendoMediaPonderada += (Integer)segundaColumna.get(i);
      }

      media /= (double)primeraColumna.size();
      mediaPonderada /= (double)dividendoMediaPonderada;
      System.out.println("La media es: " + media);
      System.out.println("La media ponderada es: " + mediaPonderada);
   }
}
