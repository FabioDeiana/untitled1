package fabio;

import fabio.dao.EventoDAO;
import fabio.dao.LocationDAO;
import fabio.dao.PartecipazioneDAO;
import fabio.dao.PersonaDAO;
import fabio.entities.*;
import fabio.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("gestioneeventipu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // Creo i DAO
        EventoDAO ed = new EventoDAO(entityManager);
        PersonaDAO pd = new PersonaDAO(entityManager);
        LocationDAO ld = new LocationDAO(entityManager);
        PartecipazioneDAO partd = new PartecipazioneDAO(entityManager);

        // 1. Creo e salvo una Location
        Location location1 = new Location("Teatro Verdi", "Milano");
        ld.save(location1);

        // 2. Creo e salvo una Persona
        Persona persona1 = new Persona("Mario", "Rossi", "mario.rossi@email.com", LocalDate.of(1990, 5, 15), Sesso.M);
        pd.save(persona1);

        // 3. Creo e salvo un Evento collegato alla Location
        Evento evento1 = new Evento("Concerto Rock", LocalDate.of(2025, 2, 15), "Concerto di musica rock", TipoEvento.PUBBLICO, 500);
        evento1.setLocation(location1);
        ed.save(evento1);

        // 4. Creo e salvo una Partecipazione che collega Persona ed Evento
        Partecipazione partecipazione1 = new Partecipazione(persona1, evento1, StatoPartecipazione.CONFERMATA);
        partd.save(partecipazione1);

        // TEST: Recupero l'evento e vedo se ha la location
        try {
            Evento eventoFromDB = ed.getById(evento1.getId());
            System.out.println("\n--- EVENTO RECUPERATO ---");
            System.out.println(eventoFromDB);
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        // TEST: Recupero la persona e vedo se ha le partecipazioni
        try {
            Persona personaFromDB = pd.getById(persona1.getId());
            System.out.println("\n--- PERSONA RECUPERATA ---");
            System.out.println(personaFromDB);
            System.out.println("Numero partecipazioni: " + personaFromDB.getListaPartecipazioni().size());
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}