package com.examenud3.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Alumno")
public class Alumno {

    // Matrícula
    @Id
    @Column(name = "NMAT") // PK
    private int nmat;

    // Nombre alumno
    @Column(name = "NOMA", length = 50, nullable = false)
    private String noma;

    // Fecha nacimiento
    @Column(name = "FN", nullable = false)
    private LocalDate fn;

    // Provincia
    @Column(name = "PROV", length = 50)
    private String prov;

    // Beca: S/N 
    @Column(name = "BECA", length = 1, nullable = false)
    private String beca;

    // Relación N:M
    // Tabla intermedia MATRICULA con las columnas NMAT y CASIG
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "MATRICULA", joinColumns = @JoinColumn(name = "NMAT"), inverseJoinColumns = @JoinColumn(name = "CASIG"))
    private List<Asignatura> asignaturas = new ArrayList<>();

    // Constructor sin parámetros
    public Alumno() {
    }

    // Constructor con parámetros
    public Alumno(int nmat, String noma, LocalDate fn, String prov, String beca) {
        this.nmat = nmat;
        this.noma = noma;
        this.fn = fn;
        this.prov = prov;
        this.beca = beca;
    }

    public void addAsignatura(Asignatura a) {
        asignaturas.add(a);
        a.getAlumnos().add(this);
    }

    // Getters y Setters
    public int getNmat() {
        return nmat;
    }

    public void setNmat(int nmat) {
        this.nmat = nmat;
    }

    public String getNoma() {
        return noma;
    }

    public void setNoma(String noma) {
        this.noma = noma;
    }

    public LocalDate getFn() {
        return fn;
    }

    public void setFn(LocalDate fn) {
        this.fn = fn;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getBeca() {
        return beca;
    }

    public void setBeca(String beca) {
        this.beca = beca;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
        return noma;
    }
}