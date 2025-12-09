package com.hibernate.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime; // Usamos LocalDateTime para fecha Y hora
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONFERENCIA")
public class Conferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio; // Sin @Temporal

    private String lugar;

    @Column(name = "numero_horas")
    private double numeroHoras;

    @ManyToMany(mappedBy = "conferencias")
    private List<Investigador> investigadores = new ArrayList<>();

    public Conferencia() {
    }

    public Conferencia(String nombre, LocalDateTime fechaHoraInicio, String lugar, double numeroHoras) {
        this.nombre = nombre;
        this.fechaHoraInicio = fechaHoraInicio;
        this.lugar = lugar;
        this.numeroHoras = numeroHoras;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fecha) {
        this.fechaHoraInicio = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public double getNumeroHoras() {
        return numeroHoras;
    }

    public List<Investigador> getInvestigadores() {
        return investigadores;
    }

    @Override
    public String toString() {
        return nombre + " (" + lugar + ")";
    }
}