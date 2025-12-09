package com.hibernate.operaciones;

import com.hibernate.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainEjercicio2 {
    public static void main(String[] args) {
        // Carga la configuración del archivo persistence.xml
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Conferencia");
        // Crea el gestor que nos permite guardar y buscar objetos
        EntityManager em = emf.createEntityManager();

        // ----------------------------------------------------
        // APARTADO B: INSERTAR DATOS
        // ----------------------------------------------------
        // Inicia una transacción para asegurar que todo se guarde o nada
        em.getTransaction().begin();

        // Crea los objetos Proyecto usando LocalDate (Año, Mes, Día)
        Proyecto p1 = new Proyecto("Proyecto 1", LocalDate.of(2020, 5, 5));
        Proyecto p2 = new Proyecto("Proyecto 2", LocalDate.of(2020, 6, 12));
        Proyecto p3 = new Proyecto("Proyecto 3", LocalDate.of(2020, 8, 15));
        Proyecto p4 = new Proyecto("Proyecto 4", LocalDate.of(2020, 11, 1));
        Proyecto p5 = new Proyecto("Proyecto 5", LocalDate.of(2020, 12, 12));

        // Guarda los proyectos en el contexto de persistencia
        em.persist(p1); em.persist(p2); em.persist(p3); em.persist(p4); em.persist(p5);

        // Crea las Conferencias usando LocalDateTime (Año, Mes, Día, Hora, Minuto)
        Conferencia c1 = new Conferencia("Conferencia 1", LocalDateTime.of(2020, 11, 2, 0, 0), "San Fernando", 2.5);
        Conferencia c2 = new Conferencia("Conferencia 2", LocalDateTime.of(2021, 1, 12, 0, 0), "Sevilla", 4.0);
        Conferencia c3 = new Conferencia("Conferencia 3", LocalDateTime.of(2021, 7, 1, 0, 0), "San Fernando", 1.5);
        Conferencia c4 = new Conferencia("Conferencia 4", LocalDateTime.of(2021, 11, 2, 0, 0), "Berlín", 3.0);
        Conferencia c5 = new Conferencia("Conferencia 5", LocalDateTime.of(2022, 1, 1, 0, 0), "Madrid", 5.0);

        // Guarda las conferencias
        em.persist(c1); em.persist(c2); em.persist(c3); em.persist(c4); em.persist(c5);

        // Crea Investigadores y establece relaciones
        // Nota: Al usar p1.addInvestigador(i1), Hibernate sabe que debe guardar la FK automáticamente
        
        Investigador i1 = new Investigador("30487452M", new NombreCompletoInvestigador("Juan", "Pérez Martínez"), "C./ Desengaño 21", "623423523", "Cádiz");
        p1.addInvestigador(i1); // Vincula con proyecto 1
        i1.addConferencia(c2);  // Vincula con conferencia 2
        em.persist(i1); // Guarda al investigador y sus relaciones

        Investigador i2 = new Investigador("45768434R", new NombreCompletoInvestigador("Luisa", "Puertas Soto"), "C./ Falsa 123", "693543252", "Cádiz");
        p2.addInvestigador(i2);
        i2.addConferencia(c1); i2.addConferencia(c3); i2.addConferencia(c5);
        em.persist(i2);

        Investigador i3 = new Investigador("45642323B", new NombreCompletoInvestigador("María", "Ruiz Sánchez"), "C./ Almiel 12", "623234523", "Cádiz");
        p3.addInvestigador(i3);
        // Este va a todas las conferencias
        i3.addConferencia(c1); i3.addConferencia(c2); i3.addConferencia(c3); i3.addConferencia(c4); i3.addConferencia(c5);
        em.persist(i3);

        Investigador i4 = new Investigador("67534312A", new NombreCompletoInvestigador("Pablo", "Fernández Feria"), "Avd. Inventada 15", "613442323", "Cádiz");
        p2.addInvestigador(i4);
        i4.addConferencia(c1); i4.addConferencia(c5);
        em.persist(i4);

        Investigador i5 = new Investigador("65342316R", new NombreCompletoInvestigador("Sofía", "Luque Conde"), "C/ La Virtud 1", "664123623", "Cádiz");
        p1.addInvestigador(i5);
        i5.addConferencia(c1); i5.addConferencia(c2); i5.addConferencia(c3); i5.addConferencia(c4);
        em.persist(i5);

        // Investigadores sin conferencias (solo proyecto)
        Investigador i6 = new Investigador("67323452B", new NombreCompletoInvestigador("José", "López"), "C./ Almiel 15", "723234523", "Cádiz");
        p4.addInvestigador(i6);
        em.persist(i6);

        Investigador i7 = new Investigador("78953321A", new NombreCompletoInvestigador("Andrés", "Fernán Noria"), "Avd. Inventada 11", "713442323", "Cádiz");
        p3.addInvestigador(i7);
        em.persist(i7);

        Investigador i8 = new Investigador("98634571R", new NombreCompletoInvestigador("Sofía", "Martín Luz"), "C/ La Virtud 4", "764123623", "Cádiz");
        p4.addInvestigador(i8);
        em.persist(i8);

        // Confirma los cambios en la BD
        em.getTransaction().commit();


        // ----------------------------------------------------
        // APARTADO C: MOSTRAR DATOS (Listar)
        // ----------------------------------------------------
        System.out.println("\n=== CONSULTAS ===");
        
        // i) Proyectos de cada investigador
        // Recupera todos los investigadores y navega por su objeto 'proyecto'
        List<Investigador> todosInvestigadores = em.createQuery("SELECT i FROM Investigador i", Investigador.class).getResultList();
        for (Investigador inv : todosInvestigadores) {
            System.out.println(inv.getNombreCompleto() + " trabaja en: " + inv.getProyecto().getNombre());
        }

        // ii) Investigadores en cada proyecto
        // Recupera todos los proyectos y recorre su lista 'investigadores'
        List<Proyecto> todosProyectos = em.createQuery("SELECT p FROM Proyecto p", Proyecto.class).getResultList();
        for (Proyecto pro : todosProyectos) {
            System.out.println(pro.getNombre() + " tiene a: " + pro.getInvestigadores());
        }


        // ----------------------------------------------------
        // APARTADO D: ACTUALIZACIONES
        // ----------------------------------------------------
        em.getTransaction().begin();

        // i) Haz que el investigador 2 haya asistido sólo a la conferencia 2
        Investigador inv2 = em.find(Investigador.class, 2);
        Conferencia conf2 = em.find(Conferencia.class, 2);
        if (inv2 != null && conf2 != null) {
            // Limpia la lista actual (borra las filas de la tabla intermedia)
            inv2.getConferencias().clear(); 
            // Añade la nueva relación
            inv2.addConferencia(conf2);     
        }

        // ii) Haz que todos los investigadores trabajen en el proyecto 3
        Proyecto pro3 = em.find(Proyecto.class, 3);
        if (pro3 != null) {
            // Recorre la lista que cargamos antes y cambia el proyecto uno a uno
            for (Investigador inv : todosInvestigadores) {
                // merge asegura que el objeto inv sigue conectado a Hibernate
                Investigador managedInv = em.merge(inv); 
                managedInv.setProyecto(pro3);
            }
        }

        // iii) Actualiza la fecha de la conferencia 4 a la actual
        Conferencia conf4 = em.find(Conferencia.class, 4);
        if (conf4 != null) {
            // Usa LocalDateTime.now() para obtener la hora del sistema
            conf4.setFechaHoraInicio(LocalDateTime.now());
        }

        em.getTransaction().commit();


        // ----------------------------------------------------
        // APARTADO E: ELIMINACIONES
        // ----------------------------------------------------
        em.getTransaction().begin();

        // i) Elimina Investigador 2
        Investigador invToDelete = em.find(Investigador.class, 2);
        if (invToDelete != null) {
            // Al borrarlo, Hibernate borra sus referencias en la tabla ASISTENCIA
            em.remove(invToDelete);
            System.out.println("\nInvestigador 2 eliminado.");
        }

        // ii) Elimina Proyecto 1 (CASCADA)
        // Debido a CascadeType.ALL en Proyecto.java, esto borrará también a los investigadores 1 y 5
        Proyecto proToDelete = em.find(Proyecto.class, 1);
        if (proToDelete != null) {
            em.remove(proToDelete);
            System.out.println("Proyecto 1 y sus investigadores asociados eliminados.");
        }

        // iii) Elimina Conferencia 4
        Conferencia confToDelete = em.find(Conferencia.class, 4);
        if (confToDelete != null) {
            // Solo borra la conferencia y los enlaces de asistencia, pero NO a los investigadores
            em.remove(confToDelete);
            System.out.println("Conferencia 4 eliminada.");
        }

        em.getTransaction().commit();

        // Cierra conexiones
        em.close();
        emf.close();
    }
}