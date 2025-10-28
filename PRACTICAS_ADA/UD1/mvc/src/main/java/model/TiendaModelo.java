package com.example.model;

import jakarta.xml.bind.*;
import java.io.File;
import java.util.List;

public class TiendaModelo {
    private final String rutaArchivo = "tienda.xml"; 

    // Carga la tienda desde XML
    public Tienda cargarTienda() {
        try {
            JAXBContext context = JAXBContext.newInstance(Tienda.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Tienda) unmarshaller.unmarshal(new File(rutaArchivo));
        } catch (Exception e) {
            e.printStackTrace();
            return new Tienda(); 
        }
    }

    // Guarda la tienda en XML
    public void guardarTienda(Tienda tienda) {
        try {
            JAXBContext context = JAXBContext.newInstance(Tienda.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(tienda, new File(rutaArchivo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Obtiene la lista de videojuegos
    public List<Videojuego> recogerVideojuegos() {
        return cargarTienda().getVideojuegos();
    }

    // Añade un videojuego
    public void anadirVideojuego(Videojuego nuevo) {
        Tienda tienda = cargarTienda();
        tienda.getVideojuegos().add(nuevo);
        guardarTienda(tienda);
    }

    // Obtiene la lista de periféricos
    public List<Periferico> recogerPerifericos() {
        return cargarTienda().getPerifericos();
    }

    // Añade un periférico
    public void anadirPeriferico(Periferico nuevo) {
        Tienda tienda = cargarTienda();
        tienda.getPerifericos().add(nuevo);
        guardarTienda(tienda);
    }

    //  Borra un videojuego por ID
    public void borrarVideojuego(String id) {
        Tienda tienda = cargarTienda();
        tienda.getVideojuegos().removeIf(v -> v.getId().equalsIgnoreCase(id));
        guardarTienda(tienda);
    }

    // Borra un periférico por ID
    public void borrarPeriferico(String id) {
        Tienda tienda = cargarTienda();
        tienda.getPerifericos().removeIf(p -> p.getId().equalsIgnoreCase(id));
        guardarTienda(tienda);
    }
    
}
