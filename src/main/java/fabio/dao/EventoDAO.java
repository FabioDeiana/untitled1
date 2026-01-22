package fabio.dao;

import fabio.entities.Evento;
import fabio.entities.Concerto;
import fabio.entities.Genere;
import fabio.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class EventoDAO {
    private final EntityManager entityManager;

    public EventoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Evento newEvento) {
        // 1. Chiediamo all'EntityManager di creare una nuova transazione
        EntityTransaction transaction = entityManager.getTransaction();
        // 2. Facciamo partire la transazione
        transaction.begin();
        // 3. Aggiungiamo il newEvento al PersistenceContext
        entityManager.persist(newEvento);
        // 4. Facciamo il commit della transazione
        transaction.commit();
        // 5. Log
        System.out.println("L'evento " + newEvento.getTitolo() + " è stato salvato correttamente!");
    }

    public Evento getById(long eventoId) {
        Evento found = entityManager.find(Evento.class, eventoId);
        if (found == null) throw new NotFoundException(eventoId);
        return found;
    }

    public void delete(long eventoId) {
        // 1. Cerco l'evento tramite id
        Evento found = this.getById(eventoId);
        // 2. Chiediamo all'EntityManager di creare una nuova transazione
        EntityTransaction transaction = entityManager.getTransaction();
        // 3. Facciamo partire la transazione
        transaction.begin();
        // 4. Rimuoviamo l'oggetto
        entityManager.remove(found);
        // 5. Facciamo il commit
        transaction.commit();
        // 6. Log
        System.out.println("L'evento con id: " + eventoId + " è stato eliminato!");
    }

    public List<Concerto> getConcertiInStreaming(boolean inStreaming) {
        TypedQuery<Concerto> query = entityManager.createQuery(
                "SELECT c FROM Concerto c WHERE c.inStreaming = :streaming",
                Concerto.class
        );
        query.setParameter("streaming", inStreaming);
        return query.getResultList();
    }

    public List<Concerto> getConcertiPerGenere(Genere genere) {
        TypedQuery<Concerto> query = entityManager.createQuery(
                "SELECT c FROM Concerto c WHERE c.genere = :genere",
                Concerto.class
        );
        query.setParameter("genere", genere);
        return query.getResultList();
    }
}