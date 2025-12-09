package com.hibernate_ej1.entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Prestamo")
public class Prestamo {

    // Inyecta la clave primaria compuesta definida en PrestamoId
    @EmbeddedId
    private PrestamoId id;

    // Configura fecha_inicio como obligatoria y ÚNICA [cite: 20]
    // Usamos TIMESTAMP para guardar hora precisa
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio", nullable = false, unique = true)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    public Prestamo() {}

    public Prestamo(PrestamoId id, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public PrestamoId getId() { return id; }
    public void setId(PrestamoId id) { this.id = id; }
    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }
    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }
}