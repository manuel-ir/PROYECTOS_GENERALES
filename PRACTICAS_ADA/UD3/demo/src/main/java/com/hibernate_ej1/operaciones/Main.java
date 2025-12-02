package com.hibernate_ej1.operaciones;

import com.hibernate_ej1.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        
        // Inicializa la factoría y el gestor de entidades
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidadBiblioteca");
        EntityManager em = emf.createEntityManager();

        // Abre la transacción para inserciones
        em.getTransaction().begin();

        // Crea 5 libros
        for (int i = 1; i <= 5; i++) {
            Libro l = new Libro(i, "Autor " + i, "Titulo " + i, "Disponible", 100 + (i * 10));
            em.persist(l);
        }

        // Crea 5 usuarios con objeto embebido
        for (int i = 1; i <= 5; i++) {
            NombreCompleto nc = new NombreCompleto("Nombre" + i, "Apellido1_" + i, "Apellido2_" + i);
            Usuario u = new Usuario(i, nc, "Ciudad " + i, 1.0, new Date());
            em.persist(u);
        }

        // Crea 10 préstamos asignando IDs manualmente para su gestión posterior
        // Asigna préstamos al Usuario 5 para el ejercicio de borrado en cascada manual
        em.persist(new Prestamo(new PrestamoId(1, 5), new Date(), null));
        em.persist(new Prestamo(new PrestamoId(2, 5), new Date(), null));

        // Asigna préstamos al Usuario 3 para el ejercicio de modificación
        em.persist(new Prestamo(new PrestamoId(3, 3), new Date(), null));
        em.persist(new Prestamo(new PrestamoId(4, 3), new Date(), null));

        // Asigna préstamos al Usuario 4 para eliminar sus préstamos
        em.persist(new Prestamo(new PrestamoId(5, 4), new Date(), null));

        // Rellena el resto de préstamos con libros ficticios para cumplir el requisito de 10
        for (int i = 6; i <= 10; i++) {
            em.persist(new Prestamo(new PrestamoId(i, 1), new Date(), null));
        }

        // Confirma los cambios de inserción
        em.getTransaction().commit();

        // Abre nueva transacción para modificaciones
        em.getTransaction().begin();

        // Modifica el estado de un libro
        Libro libro = em.find(Libro.class, 1);
        if (libro != null) {
            libro.setEstado("Prestado");
        }

        // Aumenta un 25% la categoría del usuario 3
        Usuario usuario3 = em.find(Usuario.class, 3);
        if (usuario3 != null) {
            usuario3.setCategoria(usuario3.getCategoria() * 1.25);
        }

        // Modifica la fecha fin de un préstamo específico
        Prestamo prestamo = em.find(Prestamo.class, new PrestamoId(3, 3));
        if (prestamo != null) {
            prestamo.setFechaFin(new Date());
        }

        // Confirma las modificaciones
        em.getTransaction().commit();

        // Abre nueva transacción para eliminaciones
        em.getTransaction().begin();

        // Elimina al usuario 5 y sus préstamos asociados
        // Elimina primero los préstamos del usuario 5 manualmente
        Prestamo p1 = em.find(Prestamo.class, new PrestamoId(1, 5));
        if (p1 != null) em.remove(p1);

        Prestamo p2 = em.find(Prestamo.class, new PrestamoId(2, 5));
        if (p2 != null) em.remove(p2);

        // Elimina al usuario 5 una vez borrados sus préstamos
        Usuario usuario5 = em.find(Usuario.class, 5);
        if (usuario5 != null) {
            em.remove(usuario5);
        }

        // Elimina los préstamos realizados por el usuario 4
        Prestamo p3 = em.find(Prestamo.class, new PrestamoId(5, 4));
        if (p3 != null) {
            em.remove(p3);
        }

        // Confirma las eliminaciones y cierra recursos
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}