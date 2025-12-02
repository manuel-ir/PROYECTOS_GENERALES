package com.hibernate_ej1.entidades;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class NombreCompleto {

    @Column(length = 30)
    private String nombre;

    @Column(length = 20)
    private String apellido1;

    @Column(length = 20)
    private String apellido2;

    // Constructor vacío
    public NombreCompleto() {
    }

    // Constructor con parámetros
    public NombreCompleto(String nombre, String apellido1, String apellido2) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }

    // Getters y Setters 
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
}