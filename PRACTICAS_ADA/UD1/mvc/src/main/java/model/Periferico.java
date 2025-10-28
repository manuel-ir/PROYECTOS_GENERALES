package com.example.model;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "Periferico")
public class Periferico {
    private String id;
    private String nombre;
    private String tipo;
    private String compatibilidad;
    private double precio;
    private int stock;
    private String descripcion;

    // Mapeado de los atributos y elementos XML
    @XmlAttribute
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @XmlElement(name="Nombre")
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @XmlElement(name="Tipo")
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    @XmlElement(name="Compatibilidad")
    public String getCompatibilidad() { return compatibilidad; }
    public void setCompatibilidad(String compatibilidad) { this.compatibilidad = compatibilidad; }

    @XmlElement(name="Precio")
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @XmlElement(name="Stock")
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @XmlElement(name="Descripcion")
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
