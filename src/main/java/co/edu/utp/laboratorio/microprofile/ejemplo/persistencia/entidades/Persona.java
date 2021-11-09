package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = Persona.QUERY_FIND_ALL,query = "select persona from Persona persona")
@NamedQuery(name = Persona.QUERY_FIND_BY_NAME,query = "select persona from Persona persona where persona.nombre like :nombre")
@NamedQuery(name = Persona.QUERY_FIND_BY_CIUDAD,query = "select persona from Persona persona where persona.ciudadNacimiento.id = :id")

public class Persona implements Serializable {

    public static final String QUERY_FIND_ALL = "Persona.findAll";
    public static final String QUERY_FIND_BY_NAME = "Persona.findName";
    public static final String QUERY_FIND_BY_CIUDAD = "Persona.findCiudad";


    @Id
    @Column(length = 20)
    @NotBlank
    private String dni;
    @Column(length = 70)
    @NotBlank
    private String nombre;
    @ElementCollection
    @CollectionTable(name="PERSONA_TELEFONOS")
    @Column(name = "telefono",length = 20)
    private List<String> telefonos;

    @ManyToMany
    private List<Empresa> trabajos;

    @ManyToOne
    private Ciudad ciudadNacimiento;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<String> telefonos) {
        this.telefonos = telefonos;
    }

    public Ciudad getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(Ciudad ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return Objects.equals(getDni(), persona.getDni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDni());
    }
}
