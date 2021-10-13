package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Ciudad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 50)
    private String nombre;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ciudad)) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(id, ciudad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
