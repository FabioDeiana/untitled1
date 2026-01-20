package fabio;

import fabio.dao.EventoDAO;
import fabio.entities.Evento;
import fabio.entities.TipoEvento;
import fabio.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("gestioneeventipu");

    public static void main(String[] args) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EventoDAO ed = new EventoDAO(entityManager);

        // Creo alcuni eventi
        Evento evento1 = new Evento("Concerto Rock", LocalDate.of(2025, 2, 15), "Concerto di musica rock", TipoEvento.PUBBLICO, 500);
        Evento evento2 = new Evento("Conferenza Tech", LocalDate.of(2025, 3, 10), "Conferenza sulla tecnologia", TipoEvento.PUBBLICO, 200);
        Evento evento3 = new Evento("Festa Privata", LocalDate.of(2025, 4, 5), "Festa di compleanno", TipoEvento.PRIVATO, 50);

        // SAVE
    	// ed.save(evento1);
        // ed.save(evento2);
    	// ed.save(evento3);

        // GET BY ID
        try {
            Evento eventoFromDB = ed.getById(1);
            System.out.println(eventoFromDB);
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        // DELETE
        try {
            ed.delete(2);
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        entityManager.close();
        entityManagerFactory.close();
    }
}