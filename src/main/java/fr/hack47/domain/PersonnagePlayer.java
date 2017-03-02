package fr.hack47.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonnagePlayer.
 */
@Entity
@Table(name = "personnage_player")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonnagePlayer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Personnage personnage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public PersonnagePlayer player(Player player) {
        this.player = player;
        return this;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public PersonnagePlayer personnage(Personnage personnage) {
        this.personnage = personnage;
        return this;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonnagePlayer personnagePlayer = (PersonnagePlayer) o;
        if (personnagePlayer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personnagePlayer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonnagePlayer{" +
            "id=" + id +
            '}';
    }
}
