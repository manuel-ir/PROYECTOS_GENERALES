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

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Investigacion1");
        EntityManager em = emf.createEntityManager();

        try {
            // INSERTAR DATOS INICIALES
            em.getTransaction().begin();

            // PROYECTOS
            Proyecto p1 = new Proyecto("Proyecto 1", LocalDate.of(2020, 5, 5));
            Proyecto p2 = new Proyecto("Proyecto 2", LocalDate.of(2020, 6, 12));
            Proyecto p3 = new Proyecto("Proyecto 3", LocalDate.of(2020, 8, 15));
            Proyecto p4 = new Proyecto("Proyecto 4", LocalDate.of(2020, 11, 1));
            Proyecto p5 = new Proyecto("Proyecto 5", LocalDate.of(2020, 12, 12));

            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.persist(p4);
            em.persist(p5);

            // CONFERENCIAS
            Conferencia c1 = new Conferencia("Conferencia 1", LocalDateTime.of(2020, 11, 2, 0, 0), "San Fernando", 2.5);
            Conferencia c2 = new Conferencia("Conferencia 2", LocalDateTime.of(2021, 1, 12, 0, 0), "Sevilla", 4.0);
            Conferencia c3 = new Conferencia("Conferencia 3", LocalDateTime.of(2021, 7, 1, 0, 0), "San Fernando", 1.5);
            Conferencia c4 = new Conferencia("Conferencia 4", LocalDateTime.of(2021, 11, 2, 0, 0), "Berlín", 3.0);
            Conferencia c5 = new Conferencia("Conferencia 5", LocalDateTime.of(2022, 1, 1, 0, 0), "Madrid", 5.0);

            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);
            em.persist(c5);

            // INVESTIGADORES
            Investigador i1 = new Investigador("30487452M",
                    new NombreCompletoInvestigador("Juan", "Pérez Martínez"),
                    "C./ Desengaño 21", "623423523", "Cádiz");

            p1.addInvestigador(i1);
            i1.addConferencia(c2);
            em.persist(i1);

            Investigador i2 = new Investigador("45768434R",
                    new NombreCompletoInvestigador("Luisa", "Puertas Soto"),
                    "C./ Falsa 123", "693543252", "Cádiz");

            p2.addInvestigador(i2);
            i2.addConferencia(c1);
            i2.addConferencia(c3);
            i2.addConferencia(c5);
            em.persist(i2);

            Investigador i3 = new Investigador("45642323B",
                    new NombreCompletoInvestigador("María", "Ruiz Sánchez"),
                    "C./ Almiel 12", "623234523", "Cádiz");

            p3.addInvestigador(i3);
            i3.addConferencia(c1);
            i3.addConferencia(c2);
            i3.addConferencia(c3);
            i3.addConferencia(c4);
            i3.addConferencia(c5);
            em.persist(i3);

            Investigador i4 = new Investigador("67534312A",
                    new NombreCompletoInvestigador("Pablo", "Fernández Feria"),
                    "Avd. Inventada 15", "613442323", "Cádiz");

            p2.addInvestigador(i4);
            i4.addConferencia(c1);
            i4.addConferencia(c5);
            em.persist(i4);

            Investigador i5 = new Investigador("65342316R",
                    new NombreCompletoInvestigador("Sofía", "Luque Conde"),
                    "C/ La Virtud 1", "664123623", "Cádiz");

            p1.addInvestigador(i5);
            i5.addConferencia(c1);
            i5.addConferencia(c2);
            i5.addConferencia(c3);
            i5.addConferencia(c4);
            em.persist(i5);

            Investigador i6 = new Investigador("67323452B",
                    new NombreCompletoInvestigador("José", "López"),
                    "C./ Almiel 15", "723234523", "Cádiz");

            p4.addInvestigador(i6);
            em.persist(i6);

            Investigador i7 = new Investigador("78953321A",
                    new NombreCompletoInvestigador("Andrés", "Fernán Noria"),
                    "Avd. Inventada 11", "713442323", "Cádiz");

            p3.addInvestigador(i7);
            em.persist(i7);

            Investigador i8 = new Investigador("98634571R",
                    new NombreCompletoInvestigador("Sofía", "Martín Luz"),
                    "C/ La Virtud 4", "764123623", "Cádiz");

            p4.addInvestigador(i8);
            em.persist(i8);

            em.getTransaction().commit();


            // Muestra los datos de los siguientes elementos de la base de datos
            System.out.println("CONSULTAS");

            // Los proyectos en los que trabaja cada investigador
            System.out.println("Los proyectos en los que trabaja cada investigador");
            List<Investigador> todosInvestigadores = em
                    .createQuery("SELECT i FROM Investigador i", Investigador.class)
                    .getResultList();

            for (Investigador inv : todosInvestigadores) {
                System.out.println(inv.getNombreCompleto() +
                        " trabaja en: " +
                        inv.getProyecto().getNombre());
            }

            // Los investigadores que trabajan en cada proyecto
            System.out.println("Los investigadores que trabajan en cada proyecto");
            List<Proyecto> todosProyectos = em
                    .createQuery("SELECT p FROM Proyecto p", Proyecto.class)
                    .getResultList();

            for (Proyecto pro : todosProyectos) {
                System.out.println(pro.getNombre() + " tiene a: " + pro.getInvestigadores());
            }

            // Los investigadores que han ido a cada conferencia
            System.out.println("Los investigadores que han ido a cada conferencia");
            List<Conferencia> todasConferencias = em
                    .createQuery("SELECT c FROM Conferencia c", Conferencia.class)
                    .getResultList();
            
            for(Conferencia conf : todasConferencias) {
                System.out.println("A la " + conf.getNombre() + " han ido: " + conf.getInvestigadores());
            }

            // Las conferencias en las que ha estado cada investigador
            System.out.println("Las conferencias en las que ha estado cada investigador");
            for(Investigador inv : todosInvestigadores) {
                System.out.println(inv.getNombreCompleto() + " ha estado en: " + inv.getConferencias());
            }


            // Realiza las siguientes actualizaciones
            System.out.println("ACTUALIZACIONES");
            em.getTransaction().begin();

            // Haz que el investigador 2 haya asistido sólo a la conferencia 2
            System.out.println("Haz que el investigador 2 haya asistido sólo a la conferencia 2");
            Investigador inv2 = em.find(Investigador.class, i2.getId());
            Conferencia conf2 = em.find(Conferencia.class, c2.getId());

            if (inv2 != null && conf2 != null) {
                inv2.getConferencias().clear();
                inv2.addConferencia(conf2);
            }

            // Actualiza la fecha de la conferencia 4 poniendo la fecha actual
            System.out.println("Actualiza la fecha de la conferencia 4 poniendo la fecha actual");
            Conferencia conf4 = em.find(Conferencia.class, c4.getId());
            if (conf4 != null) {
                conf4.setFechaHoraInicio(LocalDateTime.now());
            }

            // Haz que todos los investigadores trabajen en el proyecto 3
            System.out.println("Haz que todos los investigadores trabajen en el proyecto 3");
            Proyecto pro3 = em.find(Proyecto.class, p3.getId());

            if (pro3 != null) {
                for (Investigador inv : todosInvestigadores) {
                    Investigador managed = em.merge(inv);
                    managed.setProyecto(pro3);
                }
            }

            em.getTransaction().commit();


            // Elimina los siguientes registros de la base de datos
            System.out.println("ELIMINACIONES");
            em.getTransaction().begin();

            // Investigador 2
            System.out.println("Investigador 2");
            Investigador invToDelete = em.find(Investigador.class, i2.getId());
            if (invToDelete != null) {
                em.remove(invToDelete);
            }

            // Proyecto 1
            System.out.println("Proyecto 1");
            Proyecto proToDelete = em.find(Proyecto.class, p1.getId());
            if (proToDelete != null) {
                em.remove(proToDelete);
            }

            // Conferencia 4
            System.out.println("Conferencia 4");
            Conferencia confToDelete = em.find(Conferencia.class, c4.getId());
            if (confToDelete != null) {
                em.remove(confToDelete);
            }

            em.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
        }
    }
}