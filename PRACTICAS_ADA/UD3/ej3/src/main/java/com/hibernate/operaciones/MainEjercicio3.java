package com.hibernate.operaciones;

import com.hibernate.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class MainEjercicio3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Conferencia");
        EntityManager em = emf.createEntityManager();

        System.out.println("=== EJERCICIO 3: CONSULTAS JPQL ===");

        // Apartado A: Conferencias con duración > 2
        // SELECT c.nombre: Solo queremos el String del nombre
        // FROM Conferencia c: Alias 'c' para la clase Conferencia
        System.out.println("\n--- A) Conferencias > 2 horas ---");
        TypedQuery<String> queryA = em.createQuery(
            "SELECT c.nombre FROM Conferencia c WHERE c.numeroHoras > 2", String.class);
        
        // getResultList devuelve una lista de los resultados encontrados
        List<String> listaA = queryA.getResultList();
        for (String nombre : listaA) System.out.println(nombre);


        // Apartado B: Investigadores en la conferencia más larga
        // Subconsulta (SELECT MAX...): Busca primero cuál es el número máximo de horas
        // JOIN i.conferencias c: Une el investigador con sus conferencias para filtrar
        System.out.println("\n--- B) Investigadores en la conferencia más larga ---");
        TypedQuery<String> queryB = em.createQuery(
            "SELECT DISTINCT i.nombreCompleto.nombre FROM Investigador i " +
            "JOIN i.conferencias c " +
            "WHERE c.numeroHoras = (SELECT MAX(c2.numeroHoras) FROM Conferencia c2)", String.class);
        
        for (String nombre : queryB.getResultList()) System.out.println(nombre);


        // Apartado C: Info de la conferencia más corta
        // Devuelve el objeto completo Conferencia, no solo un campo
        System.out.println("\n--- C) Info conferencia más corta ---");
        TypedQuery<Conferencia> queryC = em.createQuery(
            "SELECT c FROM Conferencia c " +
            "WHERE c.numeroHoras = (SELECT MIN(c2.numeroHoras) FROM Conferencia c2)", Conferencia.class);
        
        List<Conferencia> listaC = queryC.getResultList();
        for (Conferencia c : listaC) {
            System.out.println(c.getNombre() + " (" + c.getNumeroHoras() + "h) en " + c.getLugar());
        }


        // Apartado D: Proyecto de un investigador (Parámetros)
        // :idInvestigador es un parámetro que rellenaremos con setParameter
        System.out.println("\n--- D) Proyecto del Investigador 1 ---");
        TypedQuery<Proyecto> queryD = em.createQuery(
            "SELECT i.proyecto FROM Investigador i WHERE i.id = :idInvestigador", Proyecto.class);
        queryD.setParameter("idInvestigador", 1);
        
        List<Proyecto> listaD = queryD.getResultList();
        for (Proyecto p : listaD) System.out.println(p.getNombre());


        // Apartado E: Número de conferencias (Función SIZE)
        // SIZE(colección) devuelve cuántos elementos tiene la lista de conferencias
        System.out.println("\n--- E) Número de conferencias del Investigador 3 ---");
        TypedQuery<Integer> queryE = em.createQuery(
            "SELECT SIZE(i.conferencias) FROM Investigador i WHERE i.id = :idInvestigador", Integer.class);
        queryE.setParameter("idInvestigador", 3);
        
        try {
            // getSingleResult se usa cuando sabemos que solo devolverá un número
            Integer numConferencias = queryE.getSingleResult();
            System.out.println("Conferencias: " + numConferencias);
        } catch (Exception e) {
            System.out.println("Sin resultados.");
        }


        // Apartado F: Datos específicos (Array de Objetos)
        // Como pedimos campos mezclados (dni, nombre, apellidos), el resultado es Object[]
        // i.nombreCompleto.nombre: Navegamos dentro del objeto embebido
        System.out.println("\n--- F) Datos investigadores del Proyecto 4 ---");
        TypedQuery<Object[]> queryF = em.createQuery(
            "SELECT i.dni, i.nombreCompleto.nombre, i.nombreCompleto.apellidos " +
            "FROM Investigador i WHERE i.proyecto.nombre = 'Proyecto 4'", Object[].class);
        
        List<Object[]> listaF = queryF.getResultList();
        for (Object[] fila : listaF) {
            // fila[0] es dni, fila[1] es nombre...
            System.out.println("DNI: " + fila[0] + " | Nombre: " + fila[1] + " " + fila[2]);
        }


        // Apartado G: JOIN anidados
        // Une Proyecto -> Investigadores -> Conferencias
        // DISTINCT evita que salga el proyecto repetido si van varios investigadores
        System.out.println("\n--- G) Proyectos de asistentes a Conferencia 5 ---");
        TypedQuery<Proyecto> queryG = em.createQuery(
            "SELECT DISTINCT p FROM Proyecto p " +
            "JOIN p.investigadores i " +
            "JOIN i.conferencias c " +
            "WHERE c.nombre = 'Conferencia 5'", Proyecto.class);
        
        for (Proyecto p : queryG.getResultList()) System.out.println(p.getNombre());


        // Apartado H: Igual que el G pero con parámetro
        System.out.println("\n--- H) Proyectos de asistentes a 'Conferencia 1' ---");
        TypedQuery<Proyecto> queryH = em.createQuery(
            "SELECT DISTINCT p FROM Proyecto p " +
            "JOIN p.investigadores i " +
            "JOIN i.conferencias c " +
            "WHERE c.nombre = :nombreConf", Proyecto.class);
        queryH.setParameter("nombreConf", "Conferencia 1");
        
        for (Proyecto p : queryH.getResultList()) System.out.println(p.getNombre());


        // Apartado I: Comparación de Fechas con LocalDate
        // Hibernate entiende automáticamente la comparación de fechas
        System.out.println("\n--- I) DNI inv. en proyectos anteriores a Sep 2020 ---");
        TypedQuery<String> queryI = em.createQuery(
            "SELECT i.dni FROM Investigador i " +
            "JOIN i.proyecto p " +
            "WHERE p.fechaInicio < :fechaLimite", String.class);
        
        // Pasamos un objeto LocalDate como parámetro
        queryI.setParameter("fechaLimite", LocalDate.of(2020, 9, 1));
        
        for (String dni : queryI.getResultList()) System.out.println(dni);


        // Apartado J: Búsqueda de texto parcial (LIKE)
        // %Sánchez% busca cualquier apellido que contenga la palabra Sánchez
        System.out.println("\n--- J) Conferencias de investigadores apellidados 'Sánchez' ---");
        TypedQuery<Conferencia> queryJ = em.createQuery(
            "SELECT DISTINCT c FROM Conferencia c " +
            "JOIN c.investigadores i " +
            "WHERE i.nombreCompleto.apellidos LIKE :apellido", Conferencia.class);
        
        queryJ.setParameter("apellido", "%Sánchez%");
        
        for (Conferencia c : queryJ.getResultList()) System.out.println(c.getNombre());

        em.close();
        emf.close();
    }
}