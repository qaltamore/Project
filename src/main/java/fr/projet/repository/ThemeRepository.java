package fr.projet.repository;

import fr.projet.domain.Theme;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Theme entity.
 */
@SuppressWarnings("unused")
public interface ThemeRepository extends JpaRepository<Theme,Long> {

}
