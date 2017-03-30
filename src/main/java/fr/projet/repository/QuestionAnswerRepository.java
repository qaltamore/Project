package fr.projet.repository;

import fr.projet.domain.QuestionAnswer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionAnswer entity.
 */
@SuppressWarnings("unused")
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer,Long> {

}
