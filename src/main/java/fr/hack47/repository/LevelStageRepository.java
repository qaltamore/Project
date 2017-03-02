package fr.hack47.repository;

import fr.hack47.domain.LevelStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LevelStage entity.
 */
@SuppressWarnings("unused")
public interface LevelStageRepository extends JpaRepository<LevelStage,Long> {

}
