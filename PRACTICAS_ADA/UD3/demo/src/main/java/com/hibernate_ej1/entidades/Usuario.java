package com.hibernate_ej1.entidades;

import java.sql.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "USUARIO")

public class Usuario {
    
    
    @Id 
    @Column(name = "id")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido1")
    private String apellido1;

    @Column(name = "apellido2")
    private String apellido2;

    @Column(name = "ciudad")
    private String ciudad;

    @Column(name = "categoria")
    private double categoria;

    @Column(name = "fecha_ingreso")
    private Date fecha_ingreso;


    
}
