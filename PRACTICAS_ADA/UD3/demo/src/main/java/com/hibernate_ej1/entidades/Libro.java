package com.hibernate_ej1.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Libro")
public class Libro {

    @Id
    private int id;

    @Column(length = 20, unique = true)
    private String autor;

    @Column(length = 30)
    private String titulo;

    @Column(length = 20, nullable = false)
    private String estado;

    @Column(name = "n_paginas", nullable = false)
    private int nPaginas;

    // Constructor vacio
    public Libro() {
    }

    // Constructor con parámetros
    public Libro(int id, String autor, String titulo, String estado, int nPaginas) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.nPaginas = nPaginas;
    }

    // Getters y Setters 
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getnPaginas() {
        return nPaginas;
    }

    public void setnPaginas(int nPaginas) {
        this.nPaginas = nPaginas;
    }
}


