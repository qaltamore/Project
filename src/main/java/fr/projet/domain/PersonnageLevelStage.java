package fr.projet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A PersonnageLevelStage.
 */
@Entity
@Table(name = "personnage_level_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonnageLevelStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "personnages_quantity")
    private Integer personnagesQuantity;

    @ManyToOne
    private Personnage personnage;

    @ManyToOne
    private LevelStage levelStage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPersonnagesQuantity() {
        return personnagesQuantity;
    }

    public PersonnageLevelStage personnagesQuantity(Integer personnagesQuantity) {
        this.personnagesQuantity = personnagesQuantity;
        return this;
    }

    public void setPersonnagesQuantity(Integer personnagesQuantity) {
        this.personnagesQuantity = personnagesQuantity;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public PersonnageLevelStage personnage(Personnage personnage) {
        this.personnage = personnage;
        return this;
    }

    public void setPersonnage(Personnage personnage) {
        this.personnage = personnage;
    }

    public LevelStage getLevelStage() {
        return levelStage;
    }

    public PersonnageLevelStage levelStage(LevelStage levelStage) {
        this.levelStage = levelStage;
        return this;
    }

    public void setLevelStage(LevelStage levelStage) {
        this.levelStage = levelStage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonnageLevelStage personnageLevelStage = (PersonnageLevelStage) o;
        if (personnageLevelStage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personnageLevelStage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PersonnageLevelStage{" +
            "id=" + id +
            ", personnagesQuantity='" + personnagesQuantity + "'" +
            '}';
    }
}
