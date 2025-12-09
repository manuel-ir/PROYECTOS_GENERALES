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

    @Embedded
    private NombreCompletoInvestigador nombreCompleto;

    private String direccion;
    private String telefono;
    private String localidad;

    @ManyToOne
    @JoinColumn(name = "id_proyecto")
    private Proyecto proyecto;

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

    // Métodos auxiliares para gestionar la relación N:M
    public void addConferencia(Conferencia conferencia) {
        this.conferencias.add(conferencia);
        conferencia.getInvestigadores().add(this);
    }

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