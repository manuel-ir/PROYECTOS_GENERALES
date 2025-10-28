package com.example;

import java.io.IOException;

import com.example.controller.TiendaControlador;
import com.example.model.TiendaModelo;
import com.example.view.TiendaVista;

public class Main {
    public static void main(String[] args) throws IOException {
        TiendaModelo modelo = new TiendaModelo();
        TiendaVista vista = new TiendaVista();
        TiendaControlador controlador = new TiendaControlador(modelo, vista);

        controlador.start();
    }
}
