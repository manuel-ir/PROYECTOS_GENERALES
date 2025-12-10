package com.hibernate.operaciones;

import com.hibernate.entidades.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;


public class MainEjercicio3 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Investigacion1");
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("EJERCICIO 3 CONSULTAS JPQL");

            // El nombre de las conferencias cuya duración sea superior a las dos horas
            System.out.println("El nombre de las conferencias cuya duración sea superior a las dos horas");
            
            TypedQuery<String> queryA = em.createQuery(
                "SELECT c.nombre FROM Conferencia c WHERE c.numeroHoras > 2", String.class);
            
            for (String nombre : queryA.getResultList()) {
                System.out.println(nombre);
            }


            // El nombre de los investigadores que participaron en la conferencia de la mayor duración
            System.out.println("El nombre de los investigadores que participaron en la conferencia de la mayor duración");
            
            TypedQuery<String> queryB = em.createQuery(
                "SELECT DISTINCT i.nombreCompleto.nombre FROM Investigador i " +
                "JOIN i.conferencias c " +
                "WHERE c.numeroHoras = (SELECT MAX(c2.numeroHoras) FROM Conferencia c2)", String.class);
            
            for (String nombre : queryB.getResultList()) {
                System.out.println(nombre);
            }


            // Toda la información sobre la conferencia de menor duración
            System.out.println("Toda la información sobre la conferencia de menor duración");
            
            TypedQuery<Conferencia> queryC = em.createQuery(
                "SELECT c FROM Conferencia c " +
                "WHERE c.numeroHoras = (SELECT MIN(c2.numeroHoras) FROM Conferencia c2)", Conferencia.class);
            
            for (Conferencia c : queryC.getResultList()) {
                System.out.println(c.getNombre() + " Duracion " + c.getNumeroHoras() + "h Lugar " + c.getLugar());
            }


            // Los proyectos llevados a cabo por un determinado investigador
            System.out.println("Los proyectos llevados a cabo por un determinado investigador");
            
            TypedQuery<Proyecto> queryD = em.createQuery(
                "SELECT i.proyecto FROM Investigador i WHERE i.id = :idInvestigador", Proyecto.class);
            
            // Usamos el ID 1 como ejemplo
            queryD.setParameter("idInvestigador", 1);
            
            for (Proyecto p : queryD.getResultList()) {
                System.out.println(p.getNombre());
            }


            // El número de conferencias en las que ha participado un determinado investigador
            System.out.println("El número de conferencias en las que ha participado un determinado investigador");
            
            TypedQuery<Integer> queryE = em.createQuery(
                "SELECT SIZE(i.conferencias) FROM Investigador i WHERE i.id = :idInvestigador", Integer.class);
            
            // Usamos el ID 3 como ejemplo
            queryE.setParameter("idInvestigador", 3);
            
            try {
                Integer numConferencias = queryE.getSingleResult();
                System.out.println("Total conferencias " + numConferencias);
            } catch (Exception e) {
                System.out.println("Sin resultados");
            }


            // Dni, nombre y apellidos de los investigadores que trabajan en el proyecto 4
            System.out.println("Dni, nombre y apellidos de los investigadores que trabajan en el proyecto 4");
            
            TypedQuery<Object[]> queryF = em.createQuery(
                "SELECT i.dni, i.nombreCompleto.nombre, i.nombreCompleto.apellidos " +
                "FROM Investigador i WHERE i.proyecto.nombre = 'Proyecto 4'", Object[].class);
            
            for (Object[] fila : queryF.getResultList()) {
                System.out.println("DNI " + fila[0] + " Nombre " + fila[1] + " " + fila[2]);
            }


            // Toda la información sobre los proyectos en los que trabajan los investigadores que participaron en la conferencia 5
            System.out.println("Toda la información sobre los proyectos en los que trabajan los investigadores que participaron en la conferencia 5");
            
            TypedQuery<Proyecto> queryG = em.createQuery(
                "SELECT DISTINCT p FROM Proyecto p " +
                "JOIN p.investigadores i " +
                "JOIN i.conferencias c " +
                "WHERE c.nombre = 'Conferencia 5'", Proyecto.class);
            
            for (Proyecto p : queryG.getResultList()) {
                System.out.println(p.getNombre());
            }


            // La misma información que el anterior, pero para una conferencia determinada
            System.out.println("La misma información que el anterior, pero para una conferencia determinada");
            
            TypedQuery<Proyecto> queryH = em.createQuery(
                "SELECT DISTINCT p FROM Proyecto p " +
                "JOIN p.investigadores i " +
                "JOIN i.conferencias c " +
                "WHERE c.nombre = :nombreConf", Proyecto.class);
            
            // Usamos Conferencia 1 como ejemplo
            queryH.setParameter("nombreConf", "Conferencia 1");
            
            for (Proyecto p : queryH.getResultList()) {
                System.out.println(p.getNombre());
            }


            // El dni de los investigadores que trabajen en un proyecto cuya fecha de inicio sea menor a una fecha dada
            System.out.println("El dni de los investigadores que trabajen en un proyecto cuya fecha de inicio sea menor a una fecha dada");
            
            TypedQuery<String> queryI = em.createQuery(
                "SELECT i.dni FROM Investigador i " +
                "JOIN i.proyecto p " +
                "WHERE p.fechaInicio < :fechaLimite", String.class);
            
            // Usamos 1 de Septiembre de 2020 como fecha limite
            queryI.setParameter("fechaLimite", LocalDate.of(2020, 9, 1));
            
            for (String dni : queryI.getResultList()) {
                System.out.println(dni);
            }


            // Toda la información sobre las conferencias en las que haya participado un investigador con un apellido concreto
            System.out.println("Toda la información sobre las conferencias en las que haya participado un investigador con un apellido concreto");
            
            TypedQuery<Conferencia> queryJ = em.createQuery(
                "SELECT DISTINCT c FROM Conferencia c " +
                "JOIN c.investigadores i " +
                "WHERE i.nombreCompleto.apellidos LIKE :apellido", Conferencia.class);
            
            // Usamos el apellido Sánchez como ejemplo
            queryJ.setParameter("apellido", "%Sánchez%");
            
            for (Conferencia c : queryJ.getResultList()) {
                System.out.println(c.getNombre() + " en " + c.getLugar());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
