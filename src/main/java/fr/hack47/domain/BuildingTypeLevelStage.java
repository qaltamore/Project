package fr.hack47.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BuildingTypeLevelStage.
 */
@Entity
@Table(name = "building_type_level_stage")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BuildingTypeLevelStage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "buildings_quantity")
    private Integer buildingsQuantity;

    @ManyToOne
    private LevelStage levelStage;

    @ManyToOne
    private BuildingType buildingType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBuildingsQuantity() {
        return buildingsQuantity;
    }

    public BuildingTypeLevelStage buildingsQuantity(Integer buildingsQuantity) {
        this.buildingsQuantity = buildingsQuantity;
        return this;
    }

    public void setBuildingsQuantity(Integer buildingsQuantity) {
        this.buildingsQuantity = buildingsQuantity;
    }

    public LevelStage getLevelStage() {
        return levelStage;
    }

    public BuildingTypeLevelStage levelStage(LevelStage levelStage) {
        this.levelStage = levelStage;
        return this;
    }

    public void setLevelStage(LevelStage levelStage) {
        this.levelStage = levelStage;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public BuildingTypeLevelStage buildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
        return this;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BuildingTypeLevelStage buildingTypeLevelStage = (BuildingTypeLevelStage) o;
        if (buildingTypeLevelStage.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, buildingTypeLevelStage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BuildingTypeLevelStage{" +
            "id=" + id +
            ", buildingsQuantity='" + buildingsQuantity + "'" +
            '}';
    }
}
