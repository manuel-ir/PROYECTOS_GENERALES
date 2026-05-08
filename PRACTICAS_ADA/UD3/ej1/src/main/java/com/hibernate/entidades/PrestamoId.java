package com.hibernate.entidades;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;

@Embeddable
public class PrestamoId implements Serializable {

    @Column(name = "id_libro")
    private int idLibro;

    @Column(name = "id_usuario")
    private int idUsuario;

    public PrestamoId() {}

    public PrestamoId(int idLibro, int idUsuario) {
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
    }

    public int getIdLibro() { return idLibro; }
    public void setIdLibro(int idLibro) { this.idLibro = idLibro; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrestamoId that = (PrestamoId) o;
        return idLibro == that.idLibro && idUsuario == that.idUsuario;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLibro, idUsuario);
    }
}