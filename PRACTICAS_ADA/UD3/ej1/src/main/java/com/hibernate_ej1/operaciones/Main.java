package com.hibernate_ej1.operaciones;

import com.hibernate_ej1.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import java.sql.Timestamp; // Importa Timestamp para definir fechas con hora exacta

public class Main {
    public static void main(String[] args) {
        // Carga la configuración de 'persistence.xml' con nombre 'unidadBiblioteca'
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidadBiblioteca");
        
        // Crea el EntityManager: es el objeto principal para guardar/borrar datos
        EntityManager em = emf.createEntityManager();

        // ---------------------------------------------------------
        // APARTADO B: INSERCIÓN DE DATOS
        // ---------------------------------------------------------
        System.out.println("=== INSERCIÓN DE DATOS ===");
        
        // Inicia una transacción (Todo o nada)
        em.getTransaction().begin();

        // Inserta 5 Libros
        // Usamos un bucle para ahorrar código, generando datos autoincrementales
        for (int i = 1; i <= 5; i++) {
            Libro l = new Libro(i, "Autor " + i, "Titulo " + i, "Disponible", 100 + (i * 10));
            // persist(): Pasa el objeto al estado "Managed". Se guardará al hacer commit.
            em.persist(l);
        }

        // Inserta 5 Usuarios (Manual) [cite: 22]
        // Creamos usuarios con datos reales para el ejemplo
        em.persist(new Usuario(1, new NombreCompleto("Juan", "Garcia", "Perez"), "Madrid", 1.0, new Date()));
        em.persist(new Usuario(2, new NombreCompleto("Ana", "Lopez", "Sanchez"), "Sevilla", 2.0, new Date()));
        em.persist(new Usuario(3, new NombreCompleto("Luis", "Martinez", "Ruiz"), "Valencia", 1.5, new Date())); // Este es el ID 3
        em.persist(new Usuario(4, new NombreCompleto("Maria", "Fernandez", "Diaz"), "Barcelona", 1.2, new Date()));
        em.persist(new Usuario(5, new NombreCompleto("Carlos", "Sanz", "Moreno"), "Bilbao", 1.8, new Date())); // Este es el ID 5

        // Inserta 10 Préstamos (Manual con Timestamp) [cite: 22]
        // REQUISITO CRÍTICO: 'fecha_inicio' debe ser ÚNICA[cite: 20].
        // Usamos Timestamp.valueOf(...) cambiando el segundo en cada línea para evitar duplicados.
        
        // Préstamos del Usuario 5 (Para el ejercicio de borrado posterior)
        em.persist(new Prestamo(new PrestamoId(1, 5), Timestamp.valueOf("2025-01-01 10:00:01"), null));
        em.persist(new Prestamo(new PrestamoId(2, 5), Timestamp.valueOf("2025-01-01 10:00:02"), null));

        // Préstamos del Usuario 3 (Para el ejercicio de modificación)
        em.persist(new Prestamo(new PrestamoId(3, 3), Timestamp.valueOf("2025-01-01 10:00:03"), null));
        em.persist(new Prestamo(new PrestamoId(4, 3), Timestamp.valueOf("2025-01-01 10:00:04"), null));

        // Préstamos del Usuario 4 (Para borrar solo préstamos)
        em.persist(new Prestamo(new PrestamoId(5, 4), Timestamp.valueOf("2025-01-01 10:00:05"), null));
        em.persist(new Prestamo(new PrestamoId(1, 4), Timestamp.valueOf("2025-01-01 10:00:06"), null));

        // Relleno hasta 10 préstamos
        em.persist(new Prestamo(new PrestamoId(2, 1), Timestamp.valueOf("2025-01-01 10:00:07"), null));
        em.persist(new Prestamo(new PrestamoId(3, 1), Timestamp.valueOf("2025-01-01 10:00:08"), null));
        em.persist(new Prestamo(new PrestamoId(4, 2), Timestamp.valueOf("2025-01-01 10:00:09"), null));
        em.persist(new Prestamo(new PrestamoId(5, 2), Timestamp.valueOf("2025-01-01 10:00:10"), null));

        // Cierra la transacción y vuelca los datos a la BD
        em.getTransaction().commit();


        // ---------------------------------------------------------
        // APARTADOS C, D, E: MODIFICACIONES
        // ---------------------------------------------------------
        System.out.println("=== MODIFICACIONES ===");
        em.getTransaction().begin();

        // Apartado C: Modifica el estado de un libro [cite: 23]
        // 1. Buscamos el libro por su PK (find)
        Libro libro = em.find(Libro.class, 1);
        if (libro != null) {
            // 2. Modificamos el atributo en memoria
            libro.setEstado("Prestado");
            // 3. Al hacer commit al final, Hibernate detecta el cambio (Dirty Checking) y hace el UPDATE solo
            System.out.println("Libro 1 actualizado a Prestado.");
        }

        // Apartado D: Aumenta categoría del usuario 3 un 25% [cite: 24]
        Usuario usuario3 = em.find(Usuario.class, 3);
        if (usuario3 != null) {
            double nuevaCategoria = usuario3.getCategoria() * 1.25;
            usuario3.setCategoria(nuevaCategoria);
            System.out.println("Categoría Usuario 3 aumentada.");
        }

        // Apartado E: Modifica fecha_fin de un préstamo [cite: 25]
        // Necesitamos la clave compuesta para buscar
        PrestamoId pid = new PrestamoId(3, 3); // Libro 3, Usuario 3
        Prestamo prestamo = em.find(Prestamo.class, pid);
        if (prestamo != null) {
            prestamo.setFechaFin(new Date()); // Pone fecha actual
            System.out.println("Fecha fin Préstamo (3,3) actualizada.");
        }

        em.getTransaction().commit();


        // ---------------------------------------------------------
        // APARTADOS F, G: ELIMINACIONES
        // ---------------------------------------------------------
        System.out.println("=== ELIMINACIONES ===");
        em.getTransaction().begin();

        // Apartado F: Elimina usuario 5 y sus préstamos [cite: 26]
        // IMPORTANTE: En este ejercicio NO tenemos relaciones @OneToMany automáticas.
        // Debemos borrar primero los hijos (préstamos) y luego el padre (usuario) para no romper la integridad referencial.
        
        // 1. Buscamos y borramos Préstamo 1 del Usuario 5
        Prestamo p5_1 = em.find(Prestamo.class, new PrestamoId(1, 5));
        if (p5_1 != null) em.remove(p5_1);

        // 2. Buscamos y borramos Préstamo 2 del Usuario 5
        Prestamo p5_2 = em.find(Prestamo.class, new PrestamoId(2, 5));
        if (p5_2 != null) em.remove(p5_2);

        // 3. Ahora que no tiene préstamos, borramos al Usuario 5
        Usuario usuario5 = em.find(Usuario.class, 5);
        if (usuario5 != null) {
            em.remove(usuario5); // Borra la fila de la BD
            System.out.println("Usuario 5 eliminado con sus préstamos.");
        }

        // Apartado G: Elimina préstamos de un usuario (Usuario 4) [cite: 27]
        // Similar al anterior, pero NO borramos al usuario al final.
        Prestamo p4_1 = em.find(Prestamo.class, new PrestamoId(5, 4));
        if (p4_1 != null) em.remove(p4_1);

        Prestamo p4_2 = em.find(Prestamo.class, new PrestamoId(1, 4));
        if (p4_2 != null) em.remove(p4_2);
        
        System.out.println("Préstamos del Usuario 4 eliminados.");

        em.getTransaction().commit();

        // Cierra recursos
        em.close();
        emf.close();
    }
}