package com.examenud3.operaciones;

import java.time.LocalDate;
import java.util.List;

import com.examenud3.entidades.Alumno;
import com.examenud3.entidades.Asignatura;
import com.examenud3.entidades.Profesor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class Main {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ExamenUD3");
        EntityManager em = emf.createEntityManager();
    
    
        System.out.println(" INSERTAMOS LOS DATOS ");
        em.getTransaction().begin();

        // Profesores
        Profesor p1 = new Profesor(1, "Celso Espada Ferrando", "mañana");
        Profesor p2 = new Profesor(2, "Valeria Peiró Marquez", "mañana");
        Profesor p3 = new Profesor(3, "Primitivo Pizarro Luján", "tarde");
        Profesor p4 = new Profesor(4, "Nazario Anaya Aznar", "mañana");
        Profesor p5 = new Profesor(5, "Vilma Hervia Yáñez", "tarde");
        Profesor p6 = new Profesor(6, "Gertrudis Baeza Borrego", "tarde");

        // Asignaturas
         Asignatura a1 = new Asignatura(1, "Sistemas Informáticos", "primero");
         Asignatura a2 = new Asignatura(2, "Bases de Datos", "primero");
         Asignatura a3 = new Asignatura(3, "Programación", "primero");
         Asignatura a4 = new Asignatura(4, "Lenguajes de Marcas", "primero");
         Asignatura a5 = new Asignatura(5, "Entornos de Desarrollo", "primero");
         Asignatura a6 = new Asignatura(6, "Formación y Orientación Laboral", "primero");
         Asignatura a7 = new Asignatura(7, "Sistemas de gestión empresarial", "segundo");
         Asignatura a8 = new Asignatura(8, "Programación de servicios y procesos", "segundo");
         Asignatura a9 = new Asignatura(9, "Acceso a datos", "segundo");
         Asignatura a10 = new Asignatura(10, "Desarrollo de Interfaces", "segundo");
         Asignatura a11 = new Asignatura(11, "Programación Multimedia y dispositivos móviles", "segundo");
         Asignatura a12 = new Asignatura(12, "Empresa e iniciativa emprendedora", "segundo");

        // Relaciones Profesor-Asignatura
        p1.addAsignatura(a1); p1.addAsignatura(a5);
        p2.addAsignatura(a4); p2.addAsignatura(a8);
        p3.addAsignatura(a2); p3.addAsignatura(a3);
        p4.addAsignatura(a7); p4.addAsignatura(a11);
        p5.addAsignatura(a9); p5.addAsignatura(a10);
        p6.addAsignatura(a6); p6.addAsignatura(a12);

        // Profesores (y asignaturas por cascada)
        em.persist(p1); em.persist(p2); em.persist(p3); 
        em.persist(p4); em.persist(p5); em.persist(p6);

        // Alumnos y Matrículas
        // Alumno 1 (Todas las de segundo)
        Alumno al1 = new Alumno(1, "Silvio Núñez Silvestre", LocalDate.of(2019, 7, 1), "Cádiz", "N");
        al1.addAsignatura(a7); al1.addAsignatura(a8); al1.addAsignatura(a9); 
        al1.addAsignatura(a10); al1.addAsignatura(a11); al1.addAsignatura(a12);
        em.persist(al1);

        // Alumno 2 (Todas las de segundo)
        Alumno al2 = new Alumno(2, "Alexandra del Rosell", LocalDate.of(2019, 7, 2), "Sevilla", "N");
        al2.addAsignatura(a7); al2.addAsignatura(a8); al2.addAsignatura(a9); 
        al2.addAsignatura(a10); al2.addAsignatura(a11); al2.addAsignatura(a12);
        em.persist(al2);

        // Alumno 3 (Segundo + Asignatura 2)
        Alumno al3 = new Alumno(3, "María Pilar Robledo", LocalDate.of(2019, 8, 12), "Cádiz", "S");
        al3.addAsignatura(a7); al3.addAsignatura(a8); al3.addAsignatura(a9); 
        al3.addAsignatura(a10); al3.addAsignatura(a11); al3.addAsignatura(a12);
        al3.addAsignatura(a2);
        em.persist(al3);

        // Alumno 4 (2, 3, 8, 9, 10)
        Alumno al4 = new Alumno(4, "Severino Egea Tenorio", LocalDate.of(2019, 9, 1), "Málaga", "S");
        al4.addAsignatura(a2); al4.addAsignatura(a3); al4.addAsignatura(a8); 
        al4.addAsignatura(a9); al4.addAsignatura(a10);
        em.persist(al4);

        // Alumno 5 (1, 2, 3, 10, 11)
        Alumno al5 = new Alumno(5, "Rosa Serra Bárcena", LocalDate.of(2019, 9, 1), "Cádiz", "N");
        al5.addAsignatura(a1); al5.addAsignatura(a2); al5.addAsignatura(a3); 
        al5.addAsignatura(a10); al5.addAsignatura(a11);
        em.persist(al5);

        // Alumno 6 (3, 7, 8, 9, 12)
        Alumno al6 = new Alumno(6, "Ernesto Sedano Alonso", LocalDate.of(2019, 10, 5), "Córdoba", "N");
        al6.addAsignatura(a3); al6.addAsignatura(a7); al6.addAsignatura(a8); 
        al6.addAsignatura(a9); al6.addAsignatura(a12);
        em.persist(al6);

        // Alumno 7 (Todas las de primero)
        Alumno al7 = new Alumno(7, "Encarnación Nebot Heredia", LocalDate.of(2020, 1, 10), "Granada", "S");
        al7.addAsignatura(a1); al7.addAsignatura(a2); al7.addAsignatura(a3); 
        al7.addAsignatura(a4); al7.addAsignatura(a5); al7.addAsignatura(a6);
        em.persist(al7);

        // Alumno 8 (Todas las de primero)
        Alumno al8 = new Alumno(8, "Fernando Riera Perera", LocalDate.of(2020, 5, 6), "Granada", "N");
        al8.addAsignatura(a1); al8.addAsignatura(a2); al8.addAsignatura(a3); 
        al8.addAsignatura(a4); al8.addAsignatura(a5); al8.addAsignatura(a6);
        em.persist(al8);

        em.getTransaction().commit();

       // APARTADO C: MOSTRAMOS LOS DATOS
         
        System.out.println("\n APARTADO C: MOSTRAR LOS DATOS ");

        // 1. Asignaturas del alumno 7
        System.out.println("   Asignaturas Alumno 7   ");
        Alumno a7_db = em.find(Alumno.class, 7);
        if (a7_db != null) {
            for (Asignatura as : a7_db.getAsignaturas()) System.out.println(as.getDasig());
        }

        // 2. Alumnos en Acceso a Datos
        System.out.println("\n   Alumnos en Acceso a Datos ");
        try {
            Asignatura acc = em.createQuery("SELECT a FROM Asignatura a WHERE a.dasig='Acceso a datos'", Asignatura.class).getSingleResult();
            for (Alumno al : acc.getAlumnos()) System.out.println(al.getNoma());
        } catch (Exception e) { System.out.println("Asignatura no encontrada"); }

        // 3. Asignaturas de cada profesor
        System.out.println("\n   Asignaturas por Profesor   ");
        List<Profesor> profes = em.createQuery("SELECT p FROM Profesor p", Profesor.class).getResultList();
        for (Profesor p : profes) {
            System.out.println("Prof: " + p.getNomp());
            for (Asignatura as : p.getAsignaturas()) System.out.println("  - " + as.getDasig());
        }

        // APARTADO D: CONSULTAS
         
        System.out.println("\n APARTADO D: CONSULTAS ");

        // 1. Nombres de alumnos en Bases de Datos
        System.out.println("   Alumnos en Bases de Datos ");
        TypedQuery<String> q1 = em.createQuery(
            "SELECT al.noma FROM Alumno al JOIN al.asignaturas asig WHERE asig.dasig = 'Bases de Datos'", String.class);
        for (String s : q1.getResultList()) System.out.println(s);

        
        // 3. Num Asignaturas Profesor ID 1
        System.out.println("\n   Num Asignaturas Profe 1 ");
        Long count = em.createQuery("SELECT COUNT(a) FROM Profesor p JOIN p.asignaturas a WHERE p.cprof=1", Long.class).getSingleResult();
        System.out.println("Total: " + count);

       
        // APARTADO E: ACTUALIZACIONES
         
        System.out.println("\n  APARTADO E: ACTUALIZACIONES");
        em.getTransaction().begin();

        // 1. Modifica el alumno 5 y se matricula en 12
        Alumno al5_upd = em.find(Alumno.class, 5);
        Asignatura asig12 = em.find(Asignatura.class, 12);
        if (al5_upd != null && asig12 != null) {
            if (!al5_upd.getAsignaturas().contains(asig12)) {
                al5_upd.addAsignatura(asig12);
                System.out.println("Alumno 5 matriculado en asig 12");
            }
        }

        // 2. Modificar nombre profesor 4
        Profesor p4_upd = em.find(Profesor.class, 4);
        if (p4_upd != null) {
            p4_upd.setNomp("Nazario Modificado");
            System.out.println("Profesor 4 renombrado");
        }

        em.getTransaction().commit();
        em.close();
        emf.close();

    }
}
