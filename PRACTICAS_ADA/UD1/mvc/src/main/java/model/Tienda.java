package com.example.model;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Tienda")
public class Tienda {
    private String nombreTienda;
    private String ubicacion;
    private List<Videojuego> videojuegos = new ArrayList<>();
    private List<Periferico> perifericos = new ArrayList<>();

    // Mapeado de los atributos y elementos XML

    @XmlElement(name = "NombreTienda")
    public String getNombreTienda() { return nombreTienda; }
    public void setNombreTienda(String nombreTienda) { this.nombreTienda = nombreTienda; }

    @XmlElement(name = "Ubicacion")
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    @XmlElementWrapper(name = "Videojuegos")
    @XmlElement(name = "Videojuego")
    public List<Videojuego> getVideojuegos() { return videojuegos; }
    public void setVideojuegos(List<Videojuego> videojuegos) { this.videojuegos = videojuegos; }

    @XmlElementWrapper(name = "Perifericos")
    @XmlElement(name = "Periferico")
    public List<Periferico> getPerifericos() { return perifericos; }
    public void setPerifericos(List<Periferico> perifericos) { this.perifericos = perifericos; }
}
