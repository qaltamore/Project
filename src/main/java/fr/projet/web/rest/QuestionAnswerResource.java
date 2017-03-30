package fr.projet.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.projet.domain.QuestionAnswer;

import fr.projet.repository.QuestionAnswerRepository;
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
 * REST controller for managing QuestionAnswer.
 */
@RestController
@RequestMapping("/api")
public class QuestionAnswerResource {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswerResource.class);
        
    @Inject
    private QuestionAnswerRepository questionAnswerRepository;

    /**
     * POST  /question-answers : Create a new questionAnswer.
     *
     * @param questionAnswer the questionAnswer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionAnswer, or with status 400 (Bad Request) if the questionAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-answers")
    @Timed
    public ResponseEntity<QuestionAnswer> createQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) throws URISyntaxException {
        log.debug("REST request to save QuestionAnswer : {}", questionAnswer);
        if (questionAnswer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionAnswer", "idexists", "A new questionAnswer cannot already have an ID")).body(null);
        }
        QuestionAnswer result = questionAnswerRepository.save(questionAnswer);
        return ResponseEntity.created(new URI("/api/question-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-answers : Updates an existing questionAnswer.
     *
     * @param questionAnswer the questionAnswer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionAnswer,
     * or with status 400 (Bad Request) if the questionAnswer is not valid,
     * or with status 500 (Internal Server Error) if the questionAnswer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-answers")
    @Timed
    public ResponseEntity<QuestionAnswer> updateQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) throws URISyntaxException {
        log.debug("REST request to update QuestionAnswer : {}", questionAnswer);
        if (questionAnswer.getId() == null) {
            return createQuestionAnswer(questionAnswer);
        }
        QuestionAnswer result = questionAnswerRepository.save(questionAnswer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionAnswer", questionAnswer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-answers : get all the questionAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionAnswers in body
     */
    @GetMapping("/question-answers")
    @Timed
    public List<QuestionAnswer> getAllQuestionAnswers() {
        log.debug("REST request to get all QuestionAnswers");
        List<QuestionAnswer> questionAnswers = questionAnswerRepository.findAll();
        return questionAnswers;
    }

    /**
     * GET  /question-answers/:id : get the "id" questionAnswer.
     *
     * @param id the id of the questionAnswer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionAnswer, or with status 404 (Not Found)
     */
    @GetMapping("/question-answers/{id}")
    @Timed
    public ResponseEntity<QuestionAnswer> getQuestionAnswer(@PathVariable Long id) {
        log.debug("REST request to get QuestionAnswer : {}", id);
        QuestionAnswer questionAnswer = questionAnswerRepository.findOne(id);
        return Optional.ofNullable(questionAnswer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-answers/:id : delete the "id" questionAnswer.
     *
     * @param id the id of the questionAnswer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionAnswer(@PathVariable Long id) {
        log.debug("REST request to delete QuestionAnswer : {}", id);
        questionAnswerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionAnswer", id.toString())).build();
    }

}
