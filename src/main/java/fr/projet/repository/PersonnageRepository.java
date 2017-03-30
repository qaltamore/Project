package fr.projet.repository;

import fr.projet.domain.Personnage;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Personnage entity.
 */
@SuppressWarnings("unused")
public interface PersonnageRepository extends JpaRepository<Personnage,Long> {

}
