package com.hibernate_ej1.entidades;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "PRESTAMO")
public class Prestamo {
    
    @Id
    @Column(name = "id")
    private int id_libro;

    @Column(name = "id_usuario")
    private int id_usuario;

    @Column(nullable = false, unique = true)
    private Date fecha_inicio;

    @Column(name = "fecha_fin")
    private Date fecha_fin;


    public Prestamo() {
    }

    public Prestamo(int id_libro, int id_usuario, Date fecha_inicio, Date fecha_fin) {
        this.id_libro = id_libro;
        this.id_usuario = id_usuario;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
    }

    public int getId_libro() {
        return id_libro;
    }

    public void setId_libro(int id_libro) {
        this.id_libro = id_libro;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    
}
