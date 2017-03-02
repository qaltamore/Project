package fr.hack47.repository;

import fr.hack47.domain.PersonnagePlayer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PersonnagePlayer entity.
 */
@SuppressWarnings("unused")
public interface PersonnagePlayerRepository extends JpaRepository<PersonnagePlayer,Long> {

}
