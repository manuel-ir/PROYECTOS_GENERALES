package com.examenud3.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Profesor")
public class Profesor {

    @Id
    @Column(name = "CPROF") // PK 
    private int cprof;

    @Column(name = "NOMP", length = 50, nullable = false)
    private String nomp;

    // Turno (Mañana/Tarde)
    @Column(name = "TURNO", length = 20, nullable = false)
    private String turno;

    // Relación 1:N con Asignatura
    // CascadeType.ALL: Si borras al profesor, borras sus asignaturas 
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asignatura> asignaturas = new ArrayList<>();

    // Constructor sin parámetros
    public Profesor() {
    }

    // Constructor con parámetros
    public Profesor(int cprof, String nomp, String turno) {
        this.cprof = cprof;
        this.nomp = nomp;
        this.turno = turno;
    }

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.setProfesor(this);
    }

    // Getters y Setters
    public int getCprof() {
        return cprof;
    }

    public void setCprof(int cprof) {
        this.cprof = cprof;
    }

    public String getNomp() {
        return nomp;
    }

    public void setNomp(String nomp) {
        this.nomp = nomp;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
        return nomp;
    }
}