package fr.hack47.repository;

import fr.hack47.domain.Personnage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personnage entity.
 */
@SuppressWarnings("unused")
public interface PersonnageRepository extends JpaRepository<Personnage,Long> {

}
