package com.hibernate.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROYECTO")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROYECTO")
    private int id; 

    @Column(name = "NOMBRE_PROYECTO", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "FECHA_INICIO", nullable = false)
    private LocalDate fechaInicio; 

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investigador> investigadores = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(String nombre, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }

    public void addInvestigador(Investigador investigador) {
        investigadores.add(investigador);
        investigador.setProyecto(this);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<Investigador> getInvestigadores() {
        return investigadores;
    }

    @Override
    public String toString() {
        return nombre;
    }
}