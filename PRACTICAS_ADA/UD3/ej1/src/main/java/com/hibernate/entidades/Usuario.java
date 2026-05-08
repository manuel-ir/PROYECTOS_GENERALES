package com.hibernate.entidades;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Usuario")
public class Usuario {

    @Id
    private int id;

    
    @Embedded
    private NombreCompleto nombreCompleto;

    @Column(length = 20)
    private String ciudad;

   
    private double categoria;

   
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    public Usuario() {}

    public Usuario(int id, NombreCompleto nombreCompleto, String ciudad, double categoria, Date fechaIngreso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.fechaIngreso = fechaIngreso;
    }

    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public NombreCompleto getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(NombreCompleto nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public double getCategoria() { return categoria; }
    public void setCategoria(double categoria) { this.categoria = categoria; }
    public Date getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(Date fechaIngreso) { this.fechaIngreso = fechaIngreso; }
}