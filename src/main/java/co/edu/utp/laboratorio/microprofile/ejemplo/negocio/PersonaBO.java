package co.edu.utp.laboratorio.microprofile.ejemplo.negocio;

import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Persona;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PersonaBO {
    @PersistenceContext
    private EntityManager entityManager;

    public void insertar(Persona persona){
        entityManager.persist(persona);
    }

    public Persona find(String dni){
        return entityManager.find(Persona.class,dni);
    }
}
