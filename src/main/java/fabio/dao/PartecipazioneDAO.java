package fabio.dao;

import fabio.entities.Partecipazione;
import fabio.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PartecipazioneDAO {
    private final EntityManager entityManager;

    public PartecipazioneDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Partecipazione newPartecipazione) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newPartecipazione);
        transaction.commit();
        System.out.println("La partecipazione è stata salvata!");
    }

    public Partecipazione getById(long partecipazioneId) {
        Partecipazione found = entityManager.find(Partecipazione.class, partecipazioneId);
        if (found == null) throw new NotFoundException(partecipazioneId);
        return found;
    }

    public void delete(long partecipazioneId) {
        Partecipazione found = this.getById(partecipazioneId);
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();
        System.out.println("La partecipazione con id: " + partecipazioneId + " è stata eliminata!");
    }
}