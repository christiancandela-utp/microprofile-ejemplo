package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Empresa implements Serializable {
    @Id
    @Column(length = 20)
    private String nit;
    @Column(length = 50)
    private String nombre;
    @ManyToMany(mappedBy = "trabajos")
    private List<Persona> empleados;

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
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
        if (!(o instanceof Empresa)) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(getNit(), empresa.getNit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNit());
    }
}
