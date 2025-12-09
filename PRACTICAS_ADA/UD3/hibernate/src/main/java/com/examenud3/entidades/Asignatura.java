package com.examenud3.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Asignatura")
public class Asignatura {

    @Id
    @Column(name = "CASIG") // PK
    private int casig;

    @Column(name = "DASIG", length = 50, nullable = false)
    private String dasig;

    // Curso: No nulo, longitud 20
    @Column(name = "CUR", length = 20, nullable = false)
    private String cur;

    // Relación N:1 con Profesor
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CPROF", nullable = false)
    private Profesor profesor;

    // Relación N:M con Alumno
    @ManyToMany(mappedBy = "asignaturas", fetch = FetchType.LAZY)
    private List<Alumno> alumnos = new ArrayList<>();

    // Constructor sin parámetros
    public Asignatura() {
    }

    // Constructor con parámetros
    public Asignatura(int casig, String dasig, String cur) {
        this.casig = casig;
        this.dasig = dasig;
        this.cur = cur;
    }

    // Getters y Setters
    public int getCasig() {
        return casig;
    }

    public void setCasig(int casig) {
        this.casig = casig;
    }

    public String getDasig() {
        return dasig;
    }

    public void setDasig(String dasig) {
        this.dasig = dasig;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        return dasig;
    }
}