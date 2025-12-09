package com.hibernate.entidades;

import jakarta.persistence.Embeddable;

// Marca esta clase para ser incrustada dentro de otra entidad
// No genera una tabla propia, sus campos se suman a la tabla de Investigador
@Embeddable
public class NombreCompletoInvestigador {

    private String nombre;
    private String apellidos;

    public NombreCompletoInvestigador() {
    }

    public NombreCompletoInvestigador(String nombre, String apellidos) {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Sobrescribe toString para facilitar la impresión por consola en los
    // ejercicios
    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}