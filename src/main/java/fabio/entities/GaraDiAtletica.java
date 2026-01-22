package fabio.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("GaraDiAtletica")
public class GaraDiAtletica extends Evento {

    @ManyToMany
    @JoinTable(
            name = "gara_atleti",
            joinColumns = @JoinColumn(name = "gara_id"),
            inverseJoinColumns = @JoinColumn(name = "persona_id")
    )
    private Set<Persona> atleti = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "vincitore_id")
    private Persona vincitore;

    public GaraDiAtletica() {
    }

    public GaraDiAtletica(String titolo, java.time.LocalDate dataEvento, String descrizione, TipoEvento tipoEvento, int numeroMassimoPartecipanti) {
        super(titolo, dataEvento, descrizione, tipoEvento, numeroMassimoPartecipanti);
    }

    public Set<Persona> getAtleti() {
        return atleti;
    }

    public void setAtleti(Set<Persona> atleti) {
        this.atleti = atleti;
    }

    public Persona getVincitore() {
        return vincitore;
    }

    public void setVincitore(Persona vincitore) {
        this.vincitore = vincitore;
    }

    @Override
    public String toString() {
        return "GaraDiAtletica{" +
                "numeroAtleti=" + atleti.size() +
                ", vincitore=" + (vincitore != null ? vincitore.getNome() + " " + vincitore.getCognome() : "nessuno") +
                "} " + super.toString();
    }
}