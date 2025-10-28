package com.example.controller;

import java.io.IOException;
import java.util.List;

import com.example.model.TiendaModelo;
import com.example.view.TiendaVista;
import com.example.model.Videojuego;
import com.example.model.Periferico;

public class TiendaControlador {


    private TiendaModelo modelo;
    private TiendaVista vista;

    // Constructor: recibe el modelo y la vista

    public TiendaControlador(TiendaModelo modelo, TiendaVista vista) {
        this.modelo = modelo;
        this.vista = vista;
    }

    // Método principal que inicia el programa

    public void start() throws IOException {
        int opcion = 0;

        while (opcion != 7) {
            vista.mostrarOpciones();
            opcion = vista.leerOpcion();

            switch (opcion) {
                // Ver videojuegos

                case 1:
                    List<Videojuego> lista = modelo.recogerVideojuegos();
                    vista.mostrarVideojuegos(lista);
                    break;

                // Añadir videojuego

                case 2:
                    Videojuego nuevo = vista.crearVideojuego();
                    modelo.anadirVideojuego(nuevo);
                    vista.mostrarMensaje(" Videojuego añadido.");
                    break;

                // Ver periféricos

                case 3:
                    List<Periferico> perifericos = modelo.recogerPerifericos();
                    vista.mostrarPerifericos(perifericos);
                    break;

                // Añadir periférico

                case 4:
                    Periferico nuevoP = vista.crearPeriferico();
                    modelo.anadirPeriferico(nuevoP);
                    vista.mostrarMensaje(" Periférico añadido.");
                    break;
                
                // Borrar videojuego 

                case 5:
                    String idV = vista.pedirId("videojuego");
                    modelo.borrarVideojuego(idV);
                    vista.mostrarMensaje(" Videojuego borrado.");
                    break;

                // Borrar periférico  

                case 6:
                    String idP = vista.pedirId("periférico");
                    modelo.borrarPeriferico(idP);
                    vista.mostrarMensaje(" Periférico borrado.");
                    break;

                // Salir del programa

                case 7:
                    vista.mostrarMensaje(" Saliendo del programa...");
                    break;
                    
                default:
                    vista.mostrarMensaje(" Opción no válida.");
            }
        }

    }
}
