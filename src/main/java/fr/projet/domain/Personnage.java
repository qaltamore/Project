package fr.projet.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import fr.projet.domain.enumeration.Type;

/**
 * A Personnage.
 */
@Entity
@Table(name = "personnage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Personnage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "life_points")
    private Integer lifePoints;

    @Column(name = "movement_points")
    private Integer movementPoints;

    @Column(name = "attack_points")
    private Integer attackPoints;

    @Column(name = "defense_points")
    private Integer defensePoints;

    @Column(name = "magic_points")
    private Integer magicPoints;

    @Column(name = "capacity")
    private String capacity;

    @Column(name = "in_live")
    private Boolean inLive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Personnage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public Personnage type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Integer getLifePoints() {
        return lifePoints;
    }

    public Personnage lifePoints(Integer lifePoints) {
        this.lifePoints = lifePoints;
        return this;
    }

    public void setLifePoints(Integer lifePoints) {
        this.lifePoints = lifePoints;
    }

    public Integer getMovementPoints() {
        return movementPoints;
    }

    public Personnage movementPoints(Integer movementPoints) {
        this.movementPoints = movementPoints;
        return this;
    }

    public void setMovementPoints(Integer movementPoints) {
        this.movementPoints = movementPoints;
    }

    public Integer getAttackPoints() {
        return attackPoints;
    }

    public Personnage attackPoints(Integer attackPoints) {
        this.attackPoints = attackPoints;
        return this;
    }

    public void setAttackPoints(Integer attackPoints) {
        this.attackPoints = attackPoints;
    }

    public Integer getDefensePoints() {
        return defensePoints;
    }

    public Personnage defensePoints(Integer defensePoints) {
        this.defensePoints = defensePoints;
        return this;
    }

    public void setDefensePoints(Integer defensePoints) {
        this.defensePoints = defensePoints;
    }

    public Integer getMagicPoints() {
        return magicPoints;
    }

    public Personnage magicPoints(Integer magicPoints) {
        this.magicPoints = magicPoints;
        return this;
    }

    public void setMagicPoints(Integer magicPoints) {
        this.magicPoints = magicPoints;
    }

    public String getCapacity() {
        return capacity;
    }

    public Personnage capacity(String capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public Boolean isInLive() {
        return inLive;
    }

    public Personnage inLive(Boolean inLive) {
        this.inLive = inLive;
        return this;
    }

    public void setInLive(Boolean inLive) {
        this.inLive = inLive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Personnage personnage = (Personnage) o;
        if (personnage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, personnage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Personnage{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", lifePoints='" + lifePoints + "'" +
            ", movementPoints='" + movementPoints + "'" +
            ", attackPoints='" + attackPoints + "'" +
            ", defensePoints='" + defensePoints + "'" +
            ", magicPoints='" + magicPoints + "'" +
            ", capacity='" + capacity + "'" +
            ", inLive='" + inLive + "'" +
            '}';
    }
}
