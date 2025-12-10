package com.hibernate.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONFERENCIA")
public class Conferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONFERENCIA")
    private int id;

    @Column(name = "NOMBRE_CONF", length = 50, nullable = false)
    private String nombre;

    @Column(name = "FECHA_HORA_INICIO", nullable = false)
    private LocalDateTime fechaHoraInicio; 

    @Column(name = "LUGAR", length = 50, nullable = false)
    private String lugar;

    @Column(name = "NUMERO_HORAS", nullable = false)
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