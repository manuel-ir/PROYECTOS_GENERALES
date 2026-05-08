package com.hibernate.operaciones;

import com.hibernate.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.sql.Timestamp; 

public class Main {
    public static void main(String[] args) {
      

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Biblioteca1");
   
        
        EntityManager em = emf.createEntityManager();

        System.out.println("INSERCION DE DATOS");
        // Inicia la transaccion
        em.getTransaction().begin();

        // Inserta 5 libros usando un bucle
        for (int i = 1; i <= 5; i++) {
            Libro l = new Libro(i, "Autor " + i, "Titulo " + i, "Disponible", 100 + (i * 10));
            em.persist(l);
        }

        // Inserta 5 usuarios con datos manuales
        em.persist(new Usuario(1, new NombreCompleto("Juan", "Garcia", "Perez"), "Madrid", 1.0, new Date()));
        em.persist(new Usuario(2, new NombreCompleto("Ana", "Lopez", "Sanchez"), "Sevilla", 2.0, new Date()));
        em.persist(new Usuario(3, new NombreCompleto("Luis", "Martinez", "Ruiz"), "Valencia", 1.5, new Date()));
        em.persist(new Usuario(4, new NombreCompleto("Maria", "Fernandez", "Diaz"), "Barcelona", 1.2, new Date()));
        em.persist(new Usuario(5, new NombreCompleto("Carlos", "Sanz", "Moreno"), "Bilbao", 1.8, new Date()));

        // Inserta 10 prestamos usando Timestamp para asegurar fechas unicas
        
        // Prestamos para el usuario 5
        em.persist(new Prestamo(new PrestamoId(1, 5), Timestamp.valueOf("2025-01-01 10:00:01"), null));
        em.persist(new Prestamo(new PrestamoId(2, 5), Timestamp.valueOf("2025-01-01 10:00:02"), null));

        // Prestamos para el usuario 3
        em.persist(new Prestamo(new PrestamoId(3, 3), Timestamp.valueOf("2025-01-01 10:00:03"), null));
        em.persist(new Prestamo(new PrestamoId(4, 3), Timestamp.valueOf("2025-01-01 10:00:04"), null));

        // Prestamos para el usuario 4
        em.persist(new Prestamo(new PrestamoId(5, 4), Timestamp.valueOf("2025-01-01 10:00:05"), null));
        em.persist(new Prestamo(new PrestamoId(1, 4), Timestamp.valueOf("2025-01-01 10:00:06"), null));

        // Relleno de prestamos restantes
        em.persist(new Prestamo(new PrestamoId(2, 1), Timestamp.valueOf("2025-01-01 10:00:07"), null));
        em.persist(new Prestamo(new PrestamoId(3, 1), Timestamp.valueOf("2025-01-01 10:00:08"), null));
        em.persist(new Prestamo(new PrestamoId(4, 2), Timestamp.valueOf("2025-01-01 10:00:09"), null));
        em.persist(new Prestamo(new PrestamoId(5, 2), Timestamp.valueOf("2025-01-01 10:00:10"), null));

        // Confirma la transaccion de insercion
        em.getTransaction().commit();


        System.out.println("MODIFICACIONES");
        em.getTransaction().begin();

        // Modifica el estado del libro con ID 1 a Prestado
        Libro libro = em.find(Libro.class, 1);
        if (libro != null) {
            libro.setEstado("Prestado");
            System.out.println("Libro 1 actualizado a Prestado");
        }

        // Aumenta la categoria del usuario 3 un 25 por ciento
        Usuario usuario3 = em.find(Usuario.class, 3);
        if (usuario3 != null) {
            double nuevaCategoria = usuario3.getCategoria() * 1.25;
            usuario3.setCategoria(nuevaCategoria);
            System.out.println("Categoria Usuario 3 aumentada");
        }

        // Modifica la fecha de fin del prestamo del libro 3 al usuario 3
        PrestamoId pid = new PrestamoId(3, 3); 
        Prestamo prestamo = em.find(Prestamo.class, pid);
        if (prestamo != null) {
            prestamo.setFechaFin(new Date()); 
            System.out.println("Fecha fin Prestamo actualizada");
        }

        // Confirma las modificaciones
        em.getTransaction().commit();


        System.out.println("ELIMINACIONES");
        em.getTransaction().begin();

        // Elimina al usuario 5 y sus prestamos asociados manualmente
        
        // Borra prestamo 1 del usuario 5
        Prestamo p5_1 = em.find(Prestamo.class, new PrestamoId(1, 5));
        if (p5_1 != null) em.remove(p5_1);

        // Borra prestamo 2 del usuario 5
        Prestamo p5_2 = em.find(Prestamo.class, new PrestamoId(2, 5));
        if (p5_2 != null) em.remove(p5_2);

        // Borra al usuario 5 una vez limpio de prestamos
        Usuario usuario5 = em.find(Usuario.class, 5);
        if (usuario5 != null) {
            em.remove(usuario5); 
            System.out.println("Usuario 5 eliminado con sus prestamos");
        }

        // Elimina los prestamos del usuario 4 manteniendo al usuario
        Prestamo p4_1 = em.find(Prestamo.class, new PrestamoId(5, 4));
        if (p4_1 != null) em.remove(p4_1);

        Prestamo p4_2 = em.find(Prestamo.class, new PrestamoId(1, 4));
        if (p4_2 != null) em.remove(p4_2);
        
        System.out.println("Prestamos del Usuario 4 eliminados");

        // Confirma las eliminaciones
        em.getTransaction().commit();

        // Cierra los recursos
        em.close();
        emf.close();
    }
}