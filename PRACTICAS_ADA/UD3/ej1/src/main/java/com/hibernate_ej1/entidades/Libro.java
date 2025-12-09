package com.hibernate_ej1.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "Libro")
public class Libro {

    // Define la clave primaria sin autoincremento (lo pondremos manual en el Main)
    @Id
    private int id;

    // Configura columna 'autor': máximo 20 caracteres y valor único en toda la tabla
    @Column(length = 20, unique = true)
    private String autor;

    // Configura columna 'titulo': máximo 30 caracteres
    @Column(length = 30)
    private String titulo;

    // Configura columna 'estado': obligatorio (NOT NULL)
    @Column(length = 20, nullable = false)
    private String estado;

    // Configura columna 'n_paginas': obligatorio
    @Column(name = "n_paginas", nullable = false)
    private int nPaginas;

    public Libro() {}

    public Libro(int id, String autor, String titulo, String estado, int nPaginas) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.nPaginas = nPaginas;
    }

    // Getters y Setters necesarios para que Hibernate lea y escriba los datos
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getnPaginas() { return nPaginas; }
    public void setnPaginas(int nPaginas) { this.nPaginas = nPaginas; }
}


