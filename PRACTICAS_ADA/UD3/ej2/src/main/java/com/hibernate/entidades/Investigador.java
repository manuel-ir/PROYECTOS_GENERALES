package com.hibernate.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVESTIGADOR")
public class Investigador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dni;

    // Incrusta los campos nombre y apellidos aquí dentro
    @Embedded
    private NombreCompletoInvestigador nombreCompleto;

    private String direccion;
    private String telefono;
    private String localidad;

    // Configura la relación Muchos a Uno con Proyecto
    // Crea una columna 'id_proyecto' en la tabla INVESTIGADOR que es clave foránea
    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

    // Configura la relación Muchos a Muchos
    // Crea una tabla intermedia llamada 'ASISTENCIA'
    // joinColumns: Columna que apunta a este investigador
    // inverseJoinColumns: Columna que apunta a la conferencia
    @ManyToMany
    @JoinTable(name = "ASISTENCIA", joinColumns = @JoinColumn(name = "id_investigador"), inverseJoinColumns = @JoinColumn(name = "id_conferencia"))
    private List<Conferencia> conferencias = new ArrayList<>();

    public Investigador() {
    }

    public Investigador(String dni, NombreCompletoInvestigador nombre, String direccion, String telefono,
            String localidad) {
        this.dni = dni;
        this.nombreCompleto = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.localidad = localidad;
    }

    // Gestiona la relación N:M añadiendo a la lista propia y a la de la conferencia
    public void addConferencia(Conferencia conferencia) {
        this.conferencias.add(conferencia);
        conferencia.getInvestigadores().add(this);
    }

    // Gestiona la eliminación de la relación N:M en memoria
    public void removeConferencia(Conferencia conferencia) {
        this.conferencias.remove(conferencia);
        conferencia.getInvestigadores().remove(this);
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public NombreCompletoInvestigador getNombreCompleto() {
        return nombreCompleto;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<Conferencia> getConferencias() {
        return conferencias;
    }

    @Override
    public String toString() {
        return nombreCompleto.toString();
    }
}