package com.example.view;

import java.util.List;
import java.util.Scanner;

import com.example.model.Periferico;
import com.example.model.Videojuego;

public class TiendaVista {
    // Scanner para leer datos del teclado
    private Scanner sc = new Scanner(System.in);

    // Muestra el menú de opciones al usuario

    public void mostrarOpciones() {
    System.out.println("\n Menú Tienda");
    System.out.println("1. Ver videojuegos");
    System.out.println("2. Añadir videojuego");
    System.out.println("3. Ver periféricos");
    System.out.println("4. Añadir periférico");
    System.out.println("5. Borrar videojuego");
    System.out.println("6. Borrar periférico");
    System.out.println("7. Salir");
    System.out.print("Seleccione una opción: ");
}

    // Lee la opción elegida por el usuario

     public int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return -1; // opción inválida
        }
    }

    // Muestra la lista de videojuegos

    public void mostrarVideojuegos(List<Videojuego> lista) {
        System.out.println("\n Lista de Videojuegos ");
        for (Videojuego v : lista) {
            System.out.println("ID: " + v.getId() + " | Nombre: " + v.getNombre() + " | Precio: " + v.getPrecio());
        }
    }

    // Muestra la lista de periféricos

    public void mostrarPerifericos(List<Periferico> lista) {
        System.out.println("\n Lista de Periféricos ");
        for (Periferico p : lista) {
            System.out.println("ID: " + p.getId() + " | Nombre: " + p.getNombre() + " | Tipo: " + p.getTipo()
                    + " | Precio: " + p.getPrecio());
        }
    }

    // Muestra un mensaje por consola

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    // Pide al usuario los datos de un nuevo videojuego y lo crea

    public Videojuego crearVideojuego() {
        sc.nextLine();
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble();
        sc.nextLine();
        System.out.print("Duración: ");
        String duracion = sc.nextLine();
        System.out.print("Plataforma: ");
        String plataforma = sc.nextLine();
        System.out.print("Lanzamiento: ");
        String lanzamiento = sc.nextLine();
        System.out.print("Clasificación: ");
        String clasificacion = sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Videojuego v = new Videojuego();
        v.setId(id);
        v.setNombre(nombre);
        v.setPrecio(precio);
        v.setDuracion(duracion);
        v.setPlataforma(plataforma);
        v.setLanzamiento(lanzamiento);
        v.setClasificacion(clasificacion);
        v.setDescripcion(descripcion);

        return v;
    }

    // Pide al usuario los datos de un nuevo periférico y lo crea

    public Periferico crearPeriferico() {
        sc.nextLine();
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Tipo: ");
        String tipo = sc.nextLine();
        System.out.print("Compatibilidad: ");
        String compatibilidad = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble();
        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();
        System.out.print("Descripción: ");
        String descripcion = sc.nextLine();

        Periferico p = new Periferico();
        p.setId(id);
        p.setNombre(nombre);
        p.setTipo(tipo);
        p.setCompatibilidad(compatibilidad);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setDescripcion(descripcion);

        return p;
    }

    // Pide al usuario el ID de un videojuego o periférico

    public String pedirId(String tipo) {
        sc.nextLine(); // Limpia el buffer
        System.out.print("Introduce el ID del " + tipo + ": ");
        return sc.nextLine();
    }

    
}
