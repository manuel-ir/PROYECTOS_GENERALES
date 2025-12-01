package com.hibernate_ej1.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "LIBRO")

public class Libro {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "autor", length = 20, unique = true)
    private String autor;

    @Column(name = "titulo", length = 30)
    private String titulo;

    @Column(name = "estado", length = 20, nullable = false)
    private String estado;

    @Column(name = "n_paginas", nullable = false)
    private int n_paginas;

    public Libro() {
    }

    public Libro(int id, String autor, String titulo, String estado, int n_paginas) {
        this.id = id;
        this.autor = autor;
        this.titulo = titulo;
        this.estado = estado;
        this.n_paginas = n_paginas;
    }

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

    public int getN_paginas() {
        return n_paginas;
    }

    public void setN_paginas(int n_paginas) {
        this.n_paginas = n_paginas;
    }

    

}


