package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Rol implements Serializable {
    @Id @Column(length = 20)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        Rol rol = (Rol) o;
        return Objects.equals(getName(), rol.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}