package ficheros.EJ_XML;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
   public Main() {
   }

   public static void main(String[] args) {
      escribir("prueba2.txt", "siguo por aquí");
      leerCarACar("prueba2.txt");
   }

   public static void crearEstructura() {
      File pruebaTxt = new File("./datos/prueba.txt");
      File dir1 = new File("./datos/dir1");
      File aTxt = new File("./datos/dir1/a.txt");
      File dir2 = new File("./datos/dir2");

      try {
         if (pruebaTxt.createNewFile()) {
            System.out.println("He creado " + pruebaTxt.getName());
         }

         if (dir1.mkdir()) {
            System.out.println("He creado " + dir1.getName());
         }

         if (aTxt.createNewFile()) {
            System.out.println("He creado " + aTxt.getName());
         }

         if (dir2.mkdir()) {
            System.out.println("He creado " + dir2.getName());
         }
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public static void listarDatos() {
      File datos = new File("./datos");
      File[] archivos = datos.listFiles();
      File[] var5 = archivos;
      int var4 = archivos.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         File a = var5[var3];
         System.out.println(a.getName());
         if (a.isDirectory()) {
            File[] archivos2 = a.listFiles();
            File[] var10 = archivos2;
            int var9 = archivos2.length;

            for(int var8 = 0; var8 < var9; ++var8) {
               File b = var10[var8];
               System.out.println("\t" + b.getName());
            }
         }
      }

   }

   public static void mostrarDirectoriosDentro() {
   }

   public static void leerCarACar(String ruta) {
      try {
         FileReader fr = new FileReader(ruta);
         int caracter = 0;

         while(caracter != -1) {
            caracter = fr.read();
            if (caracter != -1) {
               System.out.print((char)caracter);
            }
         }

         System.out.println();
         fr.close();
      } catch (FileNotFoundException var3) {
         var3.printStackTrace();
      } catch (IOException var4) {
         var4.printStackTrace();
      }

   }

   public static void escribir(String ruta, String texto) {
      try {
         FileWriter fw = new FileWriter(ruta, false);
         fw.write(texto);
         fw.flush();
         fw.close();
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public static void leerLineaALinea(String ruta) {
      try {
         FileReader fr = new FileReader(ruta);
         BufferedReader br = new BufferedReader(fr);

         for(String linea = br.readLine(); linea != null; linea = br.readLine()) {
            System.out.println(linea);
         }

         fr.close();
      } catch (FileNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }
}
