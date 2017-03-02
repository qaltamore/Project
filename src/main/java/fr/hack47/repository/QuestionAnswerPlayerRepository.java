package fr.hack47.repository;

import fr.hack47.domain.QuestionAnswerPlayer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the QuestionAnswerPlayer entity.
 */
@SuppressWarnings("unused")
public interface QuestionAnswerPlayerRepository extends JpaRepository<QuestionAnswerPlayer,Long> {

}
