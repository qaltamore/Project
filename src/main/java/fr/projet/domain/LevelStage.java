package fr.projet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A LevelStage.
 */
@Entity
@Table(name = "level_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LevelStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "id_level_stage")
    private Integer idLevelStage;

    @Column(name = "numero")
    private Integer numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdLevelStage() {
        return idLevelStage;
    }

    public LevelStage idLevelStage(Integer idLevelStage) {
        this.idLevelStage = idLevelStage;
        return this;
    }

    public void setIdLevelStage(Integer idLevelStage) {
        this.idLevelStage = idLevelStage;
    }

    public Integer getNumero() {
        return numero;
    }

    public LevelStage numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LevelStage levelStage = (LevelStage) o;
        if (levelStage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, levelStage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LevelStage{" +
            "id=" + id +
            ", idLevelStage='" + idLevelStage + "'" +
            ", numero='" + numero + "'" +
            '}';
    }
}
