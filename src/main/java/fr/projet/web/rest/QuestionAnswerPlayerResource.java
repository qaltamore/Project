package fr.projet.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.projet.domain.QuestionAnswerPlayer;

import fr.projet.repository.QuestionAnswerPlayerRepository;
import fr.projet.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing QuestionAnswerPlayer.
 */
@RestController
@RequestMapping("/api")
public class QuestionAnswerPlayerResource {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswerPlayerResource.class);
        
    @Inject
    private QuestionAnswerPlayerRepository questionAnswerPlayerRepository;

    /**
     * POST  /question-answer-players : Create a new questionAnswerPlayer.
     *
     * @param questionAnswerPlayer the questionAnswerPlayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionAnswerPlayer, or with status 400 (Bad Request) if the questionAnswerPlayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-answer-players")
    @Timed
    public ResponseEntity<QuestionAnswerPlayer> createQuestionAnswerPlayer(@RequestBody QuestionAnswerPlayer questionAnswerPlayer) throws URISyntaxException {
    	log.debug("REST request to save QuestionAnswerPlayer : {}", questionAnswerPlayer);
        if (questionAnswerPlayer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionAnswerPlayer", "idexists", "A new questionAnswerPlayer cannot already have an ID")).body(null);
        }
        QuestionAnswerPlayer result = questionAnswerPlayerRepository.save(questionAnswerPlayer);
        return ResponseEntity.created(new URI("/api/question-answer-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionAnswerPlayer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-answer-players : Updates an existing questionAnswerPlayer.
     *
     * @param questionAnswerPlayer the questionAnswerPlayer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionAnswerPlayer,
     * or with status 400 (Bad Request) if the questionAnswerPlayer is not valid,
     * or with status 500 (Internal Server Error) if the questionAnswerPlayer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-answer-players")
    @Timed
    public ResponseEntity<QuestionAnswerPlayer> updateQuestionAnswerPlayer(@RequestBody QuestionAnswerPlayer questionAnswerPlayer) throws URISyntaxException {
        log.debug("REST request to update QuestionAnswerPlayer : {}", questionAnswerPlayer);
        if (questionAnswerPlayer.getId() == null) {
            return createQuestionAnswerPlayer(questionAnswerPlayer);
        }
        QuestionAnswerPlayer result = questionAnswerPlayerRepository.save(questionAnswerPlayer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionAnswerPlayer", questionAnswerPlayer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-answer-players : get all the questionAnswerPlayers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionAnswerPlayers in body
     */
    @GetMapping("/question-answer-players")
    @Timed
    public List<QuestionAnswerPlayer> getAllQuestionAnswerPlayers() {
        log.debug("REST request to get all QuestionAnswerPlayers");
        List<QuestionAnswerPlayer> questionAnswerPlayers = questionAnswerPlayerRepository.findAll();
        return questionAnswerPlayers;
    }

    /**
     * GET  /question-answer-players/:id : get the "id" questionAnswerPlayer.
     *
     * @param id the id of the questionAnswerPlayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionAnswerPlayer, or with status 404 (Not Found)
     */
    @GetMapping("/question-answer-players/{id}")
    @Timed
    public ResponseEntity<QuestionAnswerPlayer> getQuestionAnswerPlayer(@PathVariable Long id) {
        log.debug("REST request to get QuestionAnswerPlayer : {}", id);
        QuestionAnswerPlayer questionAnswerPlayer = questionAnswerPlayerRepository.findOne(id);
        return Optional.ofNullable(questionAnswerPlayer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-answer-players/:id : delete the "id" questionAnswerPlayer.
     *
     * @param id the id of the questionAnswerPlayer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-answer-players/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionAnswerPlayer(@PathVariable Long id) {
        log.debug("REST request to delete QuestionAnswerPlayer : {}", id);
        questionAnswerPlayerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionAnswerPlayer", id.toString())).build();
    }

}
