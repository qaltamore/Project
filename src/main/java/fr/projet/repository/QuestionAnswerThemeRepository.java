package fr.projet.repository;

import fr.projet.domain.QuestionAnswerTheme;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionAnswerTheme entity.
 */
@SuppressWarnings("unused")
public interface QuestionAnswerThemeRepository extends JpaRepository<QuestionAnswerTheme,Long> {

}
