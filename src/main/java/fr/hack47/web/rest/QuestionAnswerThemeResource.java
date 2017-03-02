package fr.hack47.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hack47.domain.QuestionAnswerTheme;

import fr.hack47.repository.QuestionAnswerThemeRepository;
import fr.hack47.web.rest.util.HeaderUtil;

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
 * REST controller for managing QuestionAnswerTheme.
 */
@RestController
@RequestMapping("/api")
public class QuestionAnswerThemeResource {

    private final Logger log = LoggerFactory.getLogger(QuestionAnswerThemeResource.class);
        
    @Inject
    private QuestionAnswerThemeRepository questionAnswerThemeRepository;

    /**
     * POST  /question-answer-themes : Create a new questionAnswerTheme.
     *
     * @param questionAnswerTheme the questionAnswerTheme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionAnswerTheme, or with status 400 (Bad Request) if the questionAnswerTheme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-answer-themes")
    @Timed
    public ResponseEntity<QuestionAnswerTheme> createQuestionAnswerTheme(@RequestBody QuestionAnswerTheme questionAnswerTheme) throws URISyntaxException {
        log.debug("REST request to save QuestionAnswerTheme : {}", questionAnswerTheme);
        if (questionAnswerTheme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionAnswerTheme", "idexists", "A new questionAnswerTheme cannot already have an ID")).body(null);
        }
        QuestionAnswerTheme result = questionAnswerThemeRepository.save(questionAnswerTheme);
        return ResponseEntity.created(new URI("/api/question-answer-themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionAnswerTheme", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-answer-themes : Updates an existing questionAnswerTheme.
     *
     * @param questionAnswerTheme the questionAnswerTheme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionAnswerTheme,
     * or with status 400 (Bad Request) if the questionAnswerTheme is not valid,
     * or with status 500 (Internal Server Error) if the questionAnswerTheme couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-answer-themes")
    @Timed
    public ResponseEntity<QuestionAnswerTheme> updateQuestionAnswerTheme(@RequestBody QuestionAnswerTheme questionAnswerTheme) throws URISyntaxException {
        log.debug("REST request to update QuestionAnswerTheme : {}", questionAnswerTheme);
        if (questionAnswerTheme.getId() == null) {
            return createQuestionAnswerTheme(questionAnswerTheme);
        }
        QuestionAnswerTheme result = questionAnswerThemeRepository.save(questionAnswerTheme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionAnswerTheme", questionAnswerTheme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-answer-themes : get all the questionAnswerThemes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionAnswerThemes in body
     */
    @GetMapping("/question-answer-themes")
    @Timed
    public List<QuestionAnswerTheme> getAllQuestionAnswerThemes() {
        log.debug("REST request to get all QuestionAnswerThemes");
        List<QuestionAnswerTheme> questionAnswerThemes = questionAnswerThemeRepository.findAll();
        return questionAnswerThemes;
    }

    /**
     * GET  /question-answer-themes/:id : get the "id" questionAnswerTheme.
     *
     * @param id the id of the questionAnswerTheme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionAnswerTheme, or with status 404 (Not Found)
     */
    @GetMapping("/question-answer-themes/{id}")
    @Timed
    public ResponseEntity<QuestionAnswerTheme> getQuestionAnswerTheme(@PathVariable Long id) {
        log.debug("REST request to get QuestionAnswerTheme : {}", id);
        QuestionAnswerTheme questionAnswerTheme = questionAnswerThemeRepository.findOne(id);
        return Optional.ofNullable(questionAnswerTheme)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-answer-themes/:id : delete the "id" questionAnswerTheme.
     *
     * @param id the id of the questionAnswerTheme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-answer-themes/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionAnswerTheme(@PathVariable Long id) {
        log.debug("REST request to delete QuestionAnswerTheme : {}", id);
        questionAnswerThemeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionAnswerTheme", id.toString())).build();
    }

}
