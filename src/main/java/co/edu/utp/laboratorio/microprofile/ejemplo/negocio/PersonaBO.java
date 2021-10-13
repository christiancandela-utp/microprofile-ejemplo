package co.edu.utp.laboratorio.microprofile.ejemplo.negocio;

import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Persona;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PersonaBO {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(Persona persona){
        entityManager.persist(persona);
    }

    public Persona find(String dni){
        return entityManager.find(Persona.class,dni);
    }

    public void delete(Persona persona ){
        persona = find( persona.getDni() );
        if( persona != null ) {
            entityManager.remove(persona);
        } else {
            //ERROR
        }
    }

    public void update( Persona persona ){
        entityManager.merge(persona);
    }

    public List<Persona> findAll(){
        return entityManager.createNamedQuery(Persona.QUERY_FIND_ALL,Persona.class).getResultList();
    }

    public List<Persona> finByName(String nombre){
        return entityManager.createNamedQuery(Persona.QUERY_FIND_BY_NAME,Persona.class)
                .setParameter("nombre","%"+nombre+"%").getResultList();
    }

    public List<Persona> finByCiudad(Integer id){
        return entityManager.createNamedQuery(Persona.QUERY_FIND_BY_CIUDAD,Persona.class)
                .setParameter("id",id).getResultList();
    }
}
