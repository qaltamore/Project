package fr.hack47.repository;

import fr.hack47.domain.BuildingTypeLevelStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BuildingTypeLevelStage entity.
 */
@SuppressWarnings("unused")
public interface BuildingTypeLevelStageRepository extends JpaRepository<BuildingTypeLevelStage,Long> {

}
