package fr.projet.repository;

import fr.projet.domain.BuildingType;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BuildingType entity.
 */
@SuppressWarnings("unused")
public interface BuildingTypeRepository extends JpaRepository<BuildingType,Long> {

}
