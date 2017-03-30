package fr.projet.repository;

import fr.projet.domain.PersonnageLevelStage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonnageLevelStage entity.
 */
@SuppressWarnings("unused")
public interface PersonnageLevelStageRepository extends JpaRepository<PersonnageLevelStage,Long> {

}
