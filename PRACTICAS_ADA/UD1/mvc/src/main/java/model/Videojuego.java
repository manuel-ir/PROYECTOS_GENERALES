package com.example.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Videojuego")
public class Videojuego {
    private String id;
    private String nombre;
    private double precio;
    private String duracion;
    private String plataforma;
    private String lanzamiento;
    private String clasificacion;
    private String descripcion;

    // Mapeado de los atributos y elementos XML
    @XmlAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlElement(name="Nombre")
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement(name="Precio")
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @XmlElement(name="Duracion")
    public String getDuracion() { return duracion; }
    public void setDuracion(String duracion) { this.duracion = duracion; }

    @XmlElement(name="Plataforma")
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    @XmlElement(name="Lanzamiento")
    public String getLanzamiento() { return lanzamiento; }
    public void setLanzamiento(String lanzamiento) { this.lanzamiento = lanzamiento; }

    @XmlElement(name="Clasificacion")
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }

    @XmlElement(name="Descripcion")
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
