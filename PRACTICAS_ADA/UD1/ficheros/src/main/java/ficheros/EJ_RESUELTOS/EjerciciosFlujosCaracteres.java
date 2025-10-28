package ficheros.EJ_RESUELTOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EjerciciosFlujosCaracteres {
   public EjerciciosFlujosCaracteres() {
   }

   public static void main(String[] args) {
      File archivo = new File("C:\\Users\\JOLMRIV\\Downloads\\p\\A.txt");
      copiaSinEspaciosYEnMayus(archivo);
   }

   public static int contarPalabras(File archivo) {
      int palabras = 0;
      int ultimoCaracter = 10;
      if (archivo.exists() && archivo.isFile()) {
         try {
            Throwable var3 = null;
            Object var4 = null;

            try {
               FileReader fr = new FileReader(archivo);

               try {
                  int caracter = -1;

                  for(caracter = fr.read(); caracter != -1; caracter = fr.read()) {
                     if (caracter != 32 && caracter != 10 && caracter != 13 && (ultimoCaracter == 32 || ultimoCaracter == 10 || ultimoCaracter == 13)) {
                        ++palabras;
                     }

                     ultimoCaracter = caracter;
                  }
               } finally {
                  if (fr != null) {
                     fr.close();
                  }

               }
            } catch (Throwable var14) {
               if (var3 == null) {
                  var3 = var14;
               } else if (var3 != var14) {
                  var3.addSuppressed(var14);
               }

               throw var3;
            }
         } catch (IOException var15) {
            var15.printStackTrace();
         }
      } else {
         System.out.println("El archivo pasado por parámetro no es válido.");
      }

      return palabras;
   }

   public static void copiaSinEspaciosYEnMayus(File archivo) {
      String mensaje = "";
      String mensajeModificado = "";
      if (archivo.exists() && archivo.isFile()) {
         try {
            String var10002 = archivo.getParentFile().getAbsolutePath();
            FileWriter fw = new FileWriter(var10002 + File.separator + "copia.txt");
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            for(mensaje = br.readLine(); mensaje != null; mensaje = br.readLine()) {
               mensajeModificado = mensaje.replace(" ", "").toUpperCase();
               System.out.println(mensajeModificado);
               fw.write(mensajeModificado + "\n");
            }

            fr.close();
            br.close();
            fw.flush();
            fw.close();
         } catch (IOException var6) {
            var6.printStackTrace();
         }
      } else {
         System.out.println("El archivo pasado por parámetro no es válido.");
      }

   }
}
