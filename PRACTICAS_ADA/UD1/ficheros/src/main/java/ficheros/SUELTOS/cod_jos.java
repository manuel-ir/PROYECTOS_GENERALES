package ficheros.SUELTOS;

import java.io.File;
import java.io.IOException;


public class cod_jos {


   public static void main(String[] args) {


        /* Movemos "rutaBase" al Escritorio */
       String rutaBase = System.getProperty("user.home") + "/Desktop";


       /*Declara el método con la ruta donde se van a crear los directorios*/
       crearEstructura(rutaBase); 


       // Ejercicio 2
       File raiz = new File(rutaBase, "d");   // Declara raiz que es igual a la ruta en dir d
       System.out.println("\n Listado ");
       listarDirectorio(raiz, "");


       // Ejercicio 3
       System.out.println("LISTADO MODIFICADO");
       listarModificar(rutaBase + "/d");
      
       // Ejercicio 4
       System.out.println("Archivos .txt en d1 ");
       listarPorExtension(rutaBase + "/d/d1", "");


       // Ejercicio 5
       System.out.println("Borrando archivos .txt en d1 ");
       eliminarArchivos(rutaBase + "/d/d1");


   }


   public static void crearEstructura(String rutaBase) {
       try {
           /* Crea d en ruta base*/
           File d = new File(rutaBase, "d");
           d.mkdir(); // Crea un directorio de d


           File d1 = new File(d, "d1");
           d1.mkdir();
           new File(d1, "f11.txt").createNewFile(); //Crea archivos en d1, con el directorio hijo y el método
           new File(d1, "f12.txt").createNewFile();


           File d2 = new File(d, "d2");
           d2.mkdir();
           File d21 = new File(d2, "d21");
           d21.mkdir();
           new File(d2, "f21.txt").createNewFile();
           File d22 = new File(d2, "d22");
           d22.mkdir();
           new File(d22, "f222.txt").createNewFile();


           File d3 = new File(d, "d3");
           d3.mkdirs();
           File d31 = new File(d3, "d31");
           d31.mkdir();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }
       //2. Listar
   public static void listarDirectorio(File dir, String prefijo) {  // Directorio actual y prefijo para el sangrado
       if (dir.isDirectory()) {
           System.out.println(prefijo + dir.getName()); // Primero imprime los directorios primeros
           File[] hijos = dir.listFiles(); // Array de los subdirectorios (hijos), lista los directorios de dir
           if (hijos != null) { // Mientras haya hijos
               for (File f : hijos) { // Recorre los hijos
                   listarDirectorio(f, prefijo + "         "); // Método y los lista
               }
           }
       } else {
           System.out.println(prefijo + dir.getName()); // Imprime los sub directorios y archivos con el sangrado
       }
   }


       // 3. Modificación del método anterior
       public static void listarModificar(String ruta) { // Define el objeto ruta
       File raiz = new File(ruta); // raiz, que es ruta en dir d, nos referimos a ella como ruta también
       if (raiz.exists()) {
           listarDirectorio(raiz, "");
       } else {
           System.out.println("El directorio no existe");
       }
   }
       // 4. Mostrar archivos
   public static void listarPorExtension(String ruta, String extension) {  // Extensión toma el segundo valor de la llamada al método, que en este caso es "" (más óptimo usando .endWith)
       System.out.println("Contenidos del directorio " + ruta);


       File raiz = new File(ruta); // raiz es igual a ruta, y ruta es igual a rutaBase (en el dir d)
       File[] hijos = raiz.listFiles(); // Array donde mete a los hijos, y lista todo lo que este en raiz (rutaBase pero en el dir d)


       for (File f : hijos) { // Este for recorre a los hijos que se guarda en f
           if (f.getName().contains(extension)) { //pilla el nombre de todos los f, luego los que contengan la extensión (que está vacia "" por lo que lo pilla todo, y no algo concreto como .txt)
               System.out.println(f.getName()); // imprime todos los valores de f
           }
       }
   }

   // 5. Eliminar archivos
   public static void eliminarArchivos(String ruta) { // String ruta es redundante, se puede pasar el File directamente
       File raiz = new File(ruta);
       File[] hijos = raiz.listFiles();


       for (File f : hijos) {
           if (f.getName().contains(".txt")) { // obtiene los nombres y la extension .txt
               f.delete(); // los borra
           }
       }


       System.out.println("Los archivos se eliminaron correctamente.");
   }
}
