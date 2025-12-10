package com.hibernate.entidades;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVESTIGADOR")
public class Investigador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVESTIGADOR")
    private int id;

    @Column(name = "DNI", length = 9, nullable = false, unique = true)
    private String dni;

    @Embedded
    private NombreCompletoInvestigador nombreCompleto;

    @Column(name = "DIRECCION", length = 50)
    private String direccion;

    @Column(name = "TELEFONO", length = 15)
    private String telefono;

    @Column(name = "LOCALIDAD", length = 50)
    private String localidad;

    @ManyToOne
    @JoinColumn(name = "ID_PROYECTO", nullable = false) 
    private Proyecto proyecto;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "ASISTENCIA_CONF", joinColumns = @JoinColumn(name = "ID_INVESTIGADOR"), inverseJoinColumns = @JoinColumn(name = "ID_CONFERENCIA"))
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

    public void addConferencia(Conferencia conferencia) {
        this.conferencias.add(conferencia);
        conferencia.getInvestigadores().add(this);
    }

    public void removeConferencia(Conferencia conferencia) {
        this.conferencias.remove(conferencia);
        conferencia.getInvestigadores().remove(this);
    }

    // Getters y Setters

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

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombreCompleto(NombreCompletoInvestigador nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return nombreCompleto.toString();
    }
}