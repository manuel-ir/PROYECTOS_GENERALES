package ficheros.SUELTOS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ej1_cal {
   public static void main(String[] args) {
       //crearEstructura();
       listarDatos();
       //escribir("prueba2.txt","siguo por aquí");
       //leerCarACar("prueba2.txt");
       //leerLineaALinea("./ud1/prueba.txt");


   }


   public static void crearEstructura(){


       File d=new File("/home/alumnadotarde/Desktop/d");
       File d1=new File("/home/alumnadotarde/Desktop/d/d1");
       File f11=new File("/home/alumnadotarde/Desktop/d/d1/f11.txt");
       File f12=new File("/home/alumnadotarde/Desktop/d/d1/f12.txt");


       File d2=new File("/home/alumnadotarde/Desktop/d/d2");
       File d21=new File("/home/alumnadotarde/Desktop/d/d2/d21");
       File f21=new File("/home/alumnadotarde/Desktop/d/d2/d21/f21.txt");
       File d22=new File("/home/alumnadotarde/Desktop/d/d2/d22"); 
       File f222=new File("/home/alumnadotarde/Desktop/d/d2/d22/f222.txt");
      
       File d3=new File("/home/alumnadotarde/Desktop/d/d3");
       File d31=new File("/home/alumnadotarde/Desktop/d/d3/d31");


       try {
             
           if(d.mkdir()){
               System.out.println("He creado "+d.getName());
           }


           if(d1.mkdir()){
               System.out.println("He creado "+d1.getName());
           }


           if(f11.createNewFile()){
               System.out.println("He creado "+f11.getName());
           }


           if(f12.createNewFile()){
               System.out.println("He creado "+f12.getName());
           }


           if(d2.mkdir()){
               System.out.println("He creado "+d2.getName());
           }


           if(d21.mkdir()){
               System.out.println("He creado "+d21.getName());
           }


           if(f21.createNewFile()){
               System.out.println("He creado "+f21.getName());
           }


           if(d22.mkdir()){
               System.out.println("He creado "+d22.getName());
           }


           if(f222.createNewFile()){
               System.out.println("He creado "+f222.getName());
           }


           if(d3.mkdir()){
               System.out.println("He creado "+d3.getName());
           }


           if(d31.mkdir()){
               System.out.println("He creado "+d31.getName());
           }


       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }


   }


   /* public static void listarDatos(){
       File datos=new File("/home/alumnadotarde/Desktop/d");


       File[] archivos = datos.listFiles();


       for(File a:archivos){
           System.out.println(a.getName());


           if(a.isDirectory()){
               File[] archivos2 = a.listFiles();
               for(File b:archivos2){
                   System.out.println("\t"+b.getName());
               }


           }


       }
   } */


     public static void listarDatos(){
       File datos=new File("/home/alumnadotarde/Desktop/d");


       File[] archivos = datos.listFiles();


       for(File a:archivos){
           System.out.println(a.getName());


           if(a.isDirectory()){
               File[] archivos2 = a.listFiles();
               for(File b:archivos2){
                   System.out.println("\t"+b.getName());
               }


           }


       }
   }




   public static void mostrarDirectoriosDentro(){}


   public static void leerCarACar(String ruta){
       try {
           // 1
           FileReader fr=new FileReader(ruta);


           // 2. Operación


           int caracter=0;
           while (caracter!=-1){
               caracter=fr.read();
               if(caracter!=-1){
                   System.out.print((char)caracter);
               }
  
           }


           System.out.println();
          


           // 3. cerrar
           fr.close();






       } catch (FileNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
   }


   public static void escribir(String ruta,String texto){
       try {
           // 1
           FileWriter fw=new FileWriter(ruta,false);






           // 2. Operación


           fw.write(texto);
          


           // 3. cerrar
           fw.flush();
           fw.close();






       }catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
   }




   public static void leerLineaALinea(String ruta){
       try {
           // 1
           FileReader fr=new FileReader(ruta);
           BufferedReader br=new BufferedReader(fr);


           // 2. Operación


           String linea=br.readLine();
           while (linea!=null){
               System.out.println(linea);
               linea=br.readLine();
  
           }
          


           // 3. cerrar
           fr.close();






       } catch (FileNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
   }


}

