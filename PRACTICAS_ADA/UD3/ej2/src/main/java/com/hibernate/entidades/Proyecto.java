package com.hibernate.entidades;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROYECTO")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Hibernate genera este ID automáticamente (autoincrement)

    private String nombre;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio; // Usa LocalDate para fechas sin hora (Año-Mes-Día)

    // Configura la relación Uno a Muchos con Investigador
    // mappedBy: Indica que la clave foránea está en la clase 'Investigador' (campo
    // 'proyecto')
    // cascade = ALL: Si guardas/borras/actualizas el proyecto, haz lo mismo con sus
    // investigadores
    // orphanRemoval = true: Si quitas un investigador de esta lista, bórralo de la
    // BD
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Investigador> investigadores = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(String nombre, LocalDate fechaInicio) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
    }

    // Añade un investigador a la lista y asigna este proyecto al investigador
    // Es vital para mantener la coherencia en ambos lados de la relación
    // bidireccional
    public void addInvestigador(Investigador investigador) {
        investigadores.add(investigador);
        investigador.setProyecto(this);
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public List<Investigador> getInvestigadores() {
        return investigadores;
    }

    @Override
    public String toString() {
        return nombre;
    }
}