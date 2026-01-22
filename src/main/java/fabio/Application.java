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

        // Creo location
        Location stadio = new Location("Stadio Olimpico", "Roma");
        Location arena = new Location("Arena Civica", "Milano");
         ld.save(stadio);
         ld.save(arena);

        // Creo persone
        Persona mario = new Persona("Mario", "Rossi", "mario@email.com", LocalDate.of(1990, 5, 15), Sesso.M);
        Persona luca = new Persona("Luca", "Bianchi", "luca@email.com", LocalDate.of(1985, 3, 20), Sesso.M);
        Persona giulia = new Persona("Giulia", "Verdi", "giulia@email.com", LocalDate.of(1992, 7, 10), Sesso.F);
        pd.save(mario);
        pd.save(luca);
        pd.save(giulia);

        // Creo una partita di calcio
        PartitaDiCalcio partita = new PartitaDiCalcio(
                "Derby",
                LocalDate.of(2025, 3, 15),
                "Derby cittadino",
                TipoEvento.PUBBLICO,
                50000,
                "Inter",
                "Milan",
                "Inter",
                2,
                1
        );
        partita.setLocation(stadio);
        // ed.save(partita);

        // Creo una gara di atletica
        GaraDiAtletica gara = new GaraDiAtletica(
                "Maratona di Milano",
                LocalDate.of(2025, 4, 20),
                "Maratona cittadina",
                TipoEvento.PUBBLICO,
                1000
        );
        gara.setLocation(arena);
        gara.getAtleti().add(mario);
        gara.getAtleti().add(luca);
        gara.getAtleti().add(giulia);
        gara.setVincitore(mario);
        // ed.save(gara);

        // Creo concerti
        Concerto concerto1 = new Concerto(
                "Concerto Rock",
                LocalDate.of(2025, 5, 10),
                "Serata rock",
                TipoEvento.PUBBLICO,
                5000,
                Genere.ROCK,
                true
        );
        concerto1.setLocation(arena);
        // ed.save(concerto1);

        Concerto concerto2 = new Concerto(
                "Concerto Pop",
                LocalDate.of(2025, 6, 15),
                "Serata pop",
                TipoEvento.PUBBLICO,
                3000,
                Genere.POP,
                false
        );
        concerto2.setLocation(stadio);
        // ed.save(concerto2);

        // TEST QUERY JPQL
        System.out.println("\n--- CONCERTI IN STREAMING ---");
        ed.getConcertiInStreaming(true).forEach(c -> System.out.println(c));

        System.out.println("\n--- CONCERTI GENERE ROCK ---");
        ed.getConcertiPerGenere(Genere.ROCK).forEach(c -> System.out.println(c));

        entityManager.close();
        entityManagerFactory.close();
    }
}