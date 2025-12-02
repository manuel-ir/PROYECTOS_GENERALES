package com.hibernate_ej1.entidades;

import java.util.Date; 
import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")
public class Usuario {

    @Id
    @Column(name = "id")
    private int id;

    @Embedded
    private NombreCompleto nombreCompleto;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "categoria")
    private double categoria;

    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaIngreso; 

    public Usuario() {
    }

    // Constructor
    public Usuario(int id, NombreCompleto nombreCompleto, String ciudad, double categoria, Date fechaIngreso) {
        this.id = id;
        this.nombreCompleto = nombreCompleto; 
        this.ciudad = ciudad;
        this.categoria = categoria;
        this.fechaIngreso = fechaIngreso; 
    }

    // Getters y Setters Actualizados 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y Setter 
    public NombreCompleto getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(NombreCompleto nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public double getCategoria() {
        return categoria;
    }

    public void setCategoria(double categoria) {
        this.categoria = categoria;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}