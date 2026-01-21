package fabio.dao;

import fabio.entities.Persona;
import fabio.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PersonaDAO {
    private final EntityManager entityManager;

    public PersonaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Persona newPersona) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newPersona);
        transaction.commit();
        System.out.println("La persona " + newPersona.getNome() + " " + newPersona.getCognome() + " è stata salvata!");
    }

    public Persona getById(long personaId) {
        Persona found = entityManager.find(Persona.class, personaId);
        if (found == null) throw new NotFoundException(personaId);
        return found;
    }

    public void delete(long personaId) {
        Persona found = this.getById(personaId);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println("La persona con id: " + personaId + " è stata eliminata!");
    }
}