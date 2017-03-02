package fr.hack47.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import fr.hack47.domain.enumeration.Type;

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

    @Column(name = "life_point")
    private Integer lifePoint;

    @Column(name = "movement_point")
    private Integer movementPoint;

    @Column(name = "attack_point")
    private Integer attackPoint;

    @Column(name = "defense_point")
    private Integer defensePoint;

    @Column(name = "magic_point")
    private Integer magicPoint;

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

    public Integer getLifePoint() {
        return lifePoint;
    }

    public Personnage lifePoint(Integer lifePoint) {
        this.lifePoint = lifePoint;
        return this;
    }

    public void setLifePoint(Integer lifePoint) {
        this.lifePoint = lifePoint;
    }

    public Integer getMovementPoint() {
        return movementPoint;
    }

    public Personnage movementPoint(Integer movementPoint) {
        this.movementPoint = movementPoint;
        return this;
    }

    public void setMovementPoint(Integer movementPoint) {
        this.movementPoint = movementPoint;
    }

    public Integer getAttackPoint() {
        return attackPoint;
    }

    public Personnage attackPoint(Integer attackPoint) {
        this.attackPoint = attackPoint;
        return this;
    }

    public void setAttackPoint(Integer attackPoint) {
        this.attackPoint = attackPoint;
    }

    public Integer getDefensePoint() {
        return defensePoint;
    }

    public Personnage defensePoint(Integer defensePoint) {
        this.defensePoint = defensePoint;
        return this;
    }

    public void setDefensePoint(Integer defensePoint) {
        this.defensePoint = defensePoint;
    }

    public Integer getMagicPoint() {
        return magicPoint;
    }

    public Personnage magicPoint(Integer magicPoint) {
        this.magicPoint = magicPoint;
        return this;
    }

    public void setMagicPoint(Integer magicPoint) {
        this.magicPoint = magicPoint;
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
            ", lifePoint='" + lifePoint + "'" +
            ", movementPoint='" + movementPoint + "'" +
            ", attackPoint='" + attackPoint + "'" +
            ", defensePoint='" + defensePoint + "'" +
            ", magicPoint='" + magicPoint + "'" +
            ", inLive='" + inLive + "'" +
            '}';
    }
}
