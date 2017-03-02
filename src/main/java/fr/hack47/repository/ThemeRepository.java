package fr.hack47.repository;

import fr.hack47.domain.Theme;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Theme entity.
 */
@SuppressWarnings("unused")
public interface ThemeRepository extends JpaRepository<Theme,Long> {

}
