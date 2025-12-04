package com.hibernate_ej1.operaciones;

import com.hibernate_ej1.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.sql.Timestamp; 

public class Main {
    public static void main(String[] args) {
        // Inicializa la factoría y el gestor de entidades con la unidad de persistencia
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidadBiblioteca");
        EntityManager em = emf.createEntityManager();

        // Abre la transacción para insertar datos
        em.getTransaction().begin();

        // Inserta 5 libros generados automáticamente mediante un bucle
        for (int i = 1; i <= 5; i++) {
            em.persist(new Libro(i, "Autor " + i, "Titulo " + i, "Disponible", 100 + (i * 10)));
        }

        // Inserta 5 usuarios con datos reales definidos manualmente
        em.persist(new Usuario(1, new NombreCompleto("Juan", "Garcia", "Perez"), "Madrid", 1.0, new Date()));
        em.persist(new Usuario(2, new NombreCompleto("Ana", "Lopez", "Sanchez"), "Sevilla", 2.0, new Date()));
        em.persist(new Usuario(3, new NombreCompleto("Luis", "Martinez", "Ruiz"), "Valencia", 1.5, new Date()));
        em.persist(new Usuario(4, new NombreCompleto("Maria", "Fernandez", "Diaz"), "Barcelona", 1.2, new Date()));
        em.persist(new Usuario(5, new NombreCompleto("Carlos", "Sanz", "Moreno"), "Bilbao", 1.8, new Date()));

        // Inserta préstamos asignando fechas exactas distintas para cumplir la restricción de unicidad
        
        // Asigna préstamos al Usuario 5 que serán borrados posteriormente
        em.persist(new Prestamo(new PrestamoId(1, 5), Timestamp.valueOf("2025-01-01 10:00:01"), null));
        em.persist(new Prestamo(new PrestamoId(2, 5), Timestamp.valueOf("2025-01-01 10:00:02"), null));

        // Asigna préstamos al Usuario 3 para realizar modificaciones
        em.persist(new Prestamo(new PrestamoId(3, 3), Timestamp.valueOf("2025-01-01 10:00:03"), null));
        em.persist(new Prestamo(new PrestamoId(4, 3), Timestamp.valueOf("2025-01-01 10:00:04"), null));

        // Asigna préstamos al Usuario 4 para realizar eliminaciones selectivas
        em.persist(new Prestamo(new PrestamoId(5, 4), Timestamp.valueOf("2025-01-01 10:00:05"), null));
        em.persist(new Prestamo(new PrestamoId(1, 4), Timestamp.valueOf("2025-01-01 10:00:06"), null));

        // Rellena el resto de préstamos hasta llegar a 10 registros
        em.persist(new Prestamo(new PrestamoId(2, 1), Timestamp.valueOf("2025-01-01 10:00:07"), null));
        em.persist(new Prestamo(new PrestamoId(3, 1), Timestamp.valueOf("2025-01-01 10:00:08"), null));
        em.persist(new Prestamo(new PrestamoId(4, 2), Timestamp.valueOf("2025-01-01 10:00:09"), null));
        em.persist(new Prestamo(new PrestamoId(5, 2), Timestamp.valueOf("2025-01-01 10:00:10"), null));

        // Confirma los cambios de inserción en la base de datos
        em.getTransaction().commit();

        // Abre una nueva transacción para realizar modificaciones
        em.getTransaction().begin();

        // Busca el libro con id 1 y modifica su estado
        Libro libro = em.find(Libro.class, 1);
        if (libro != null) libro.setEstado("Prestado");

        // Busca el usuario 3 y aumenta su categoría un 25 por ciento
        Usuario usuario3 = em.find(Usuario.class, 3);
        if (usuario3 != null) usuario3.setCategoria(usuario3.getCategoria() * 1.25);

        // Busca el préstamo específico del usuario 3 y establece la fecha de fin actual
        Prestamo prestamo = em.find(Prestamo.class, new PrestamoId(3, 3));
        if (prestamo != null) prestamo.setFechaFin(new Date());

        // Confirma las modificaciones realizadas
        em.getTransaction().commit();

        // Abre una nueva transacción para realizar eliminaciones
        em.getTransaction().begin();

        // Elimina los préstamos asociados al usuario 5 
        Prestamo p5_1 = em.find(Prestamo.class, new PrestamoId(1, 5));
        if (p5_1 != null) em.remove(p5_1);
        
        Prestamo p5_2 = em.find(Prestamo.class, new PrestamoId(2, 5));
        if (p5_2 != null) em.remove(p5_2);

        // Elimina al usuario 5 una vez liberado de sus préstamos
        Usuario usuario5 = em.find(Usuario.class, 5);
        if (usuario5 != null) em.remove(usuario5);

        // Elimina únicamente los préstamos realizados por el usuario 4
        Prestamo p4_1 = em.find(Prestamo.class, new PrestamoId(5, 4));
        if (p4_1 != null) em.remove(p4_1);

        Prestamo p4_2 = em.find(Prestamo.class, new PrestamoId(1, 4));
        if (p4_2 != null) em.remove(p4_2);

        // Confirma las eliminaciones
        em.getTransaction().commit();

        // Cierra el gestor de entidades y la factoría
        em.close();
        emf.close();
    }
}