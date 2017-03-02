package fr.hack47.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hack47.domain.LevelStage;

import fr.hack47.repository.LevelStageRepository;
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
 * REST controller for managing LevelStage.
 */
@RestController
@RequestMapping("/api")
public class LevelStageResource {

    private final Logger log = LoggerFactory.getLogger(LevelStageResource.class);
        
    @Inject
    private LevelStageRepository levelStageRepository;

    /**
     * POST  /level-stages : Create a new levelStage.
     *
     * @param levelStage the levelStage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new levelStage, or with status 400 (Bad Request) if the levelStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/level-stages")
    @Timed
    public ResponseEntity<LevelStage> createLevelStage(@RequestBody LevelStage levelStage) throws URISyntaxException {
        log.debug("REST request to save LevelStage : {}", levelStage);
        if (levelStage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("levelStage", "idexists", "A new levelStage cannot already have an ID")).body(null);
        }
        LevelStage result = levelStageRepository.save(levelStage);
        return ResponseEntity.created(new URI("/api/level-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("levelStage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /level-stages : Updates an existing levelStage.
     *
     * @param levelStage the levelStage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated levelStage,
     * or with status 400 (Bad Request) if the levelStage is not valid,
     * or with status 500 (Internal Server Error) if the levelStage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/level-stages")
    @Timed
    public ResponseEntity<LevelStage> updateLevelStage(@RequestBody LevelStage levelStage) throws URISyntaxException {
        log.debug("REST request to update LevelStage : {}", levelStage);
        if (levelStage.getId() == null) {
            return createLevelStage(levelStage);
        }
        LevelStage result = levelStageRepository.save(levelStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("levelStage", levelStage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /level-stages : get all the levelStages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of levelStages in body
     */
    @GetMapping("/level-stages")
    @Timed
    public List<LevelStage> getAllLevelStages() {
        log.debug("REST request to get all LevelStages");
        List<LevelStage> levelStages = levelStageRepository.findAll();
        return levelStages;
    }

    /**
     * GET  /level-stages/:id : get the "id" levelStage.
     *
     * @param id the id of the levelStage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the levelStage, or with status 404 (Not Found)
     */
    @GetMapping("/level-stages/{id}")
    @Timed
    public ResponseEntity<LevelStage> getLevelStage(@PathVariable Long id) {
        log.debug("REST request to get LevelStage : {}", id);
        LevelStage levelStage = levelStageRepository.findOne(id);
        return Optional.ofNullable(levelStage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /level-stages/:id : delete the "id" levelStage.
     *
     * @param id the id of the levelStage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/level-stages/{id}")
    @Timed
    public ResponseEntity<Void> deleteLevelStage(@PathVariable Long id) {
        log.debug("REST request to delete LevelStage : {}", id);
        levelStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("levelStage", id.toString())).build();
    }

}
