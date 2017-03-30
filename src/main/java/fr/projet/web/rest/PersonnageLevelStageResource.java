package fr.projet.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.projet.domain.PersonnageLevelStage;

import fr.projet.repository.PersonnageLevelStageRepository;
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
 * REST controller for managing PersonnageLevelStage.
 */
@RestController
@RequestMapping("/api")
public class PersonnageLevelStageResource {

    private final Logger log = LoggerFactory.getLogger(PersonnageLevelStageResource.class);
        
    @Inject
    private PersonnageLevelStageRepository personnageLevelStageRepository;

    /**
     * POST  /personnage-level-stages : Create a new personnageLevelStage.
     *
     * @param personnageLevelStage the personnageLevelStage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personnageLevelStage, or with status 400 (Bad Request) if the personnageLevelStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personnage-level-stages")
    @Timed
    public ResponseEntity<PersonnageLevelStage> createPersonnageLevelStage(@RequestBody PersonnageLevelStage personnageLevelStage) throws URISyntaxException {
        log.debug("REST request to save PersonnageLevelStage : {}", personnageLevelStage);
        if (personnageLevelStage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personnageLevelStage", "idexists", "A new personnageLevelStage cannot already have an ID")).body(null);
        }
        PersonnageLevelStage result = personnageLevelStageRepository.save(personnageLevelStage);
        return ResponseEntity.created(new URI("/api/personnage-level-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personnageLevelStage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personnage-level-stages : Updates an existing personnageLevelStage.
     *
     * @param personnageLevelStage the personnageLevelStage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personnageLevelStage,
     * or with status 400 (Bad Request) if the personnageLevelStage is not valid,
     * or with status 500 (Internal Server Error) if the personnageLevelStage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personnage-level-stages")
    @Timed
    public ResponseEntity<PersonnageLevelStage> updatePersonnageLevelStage(@RequestBody PersonnageLevelStage personnageLevelStage) throws URISyntaxException {
        log.debug("REST request to update PersonnageLevelStage : {}", personnageLevelStage);
        if (personnageLevelStage.getId() == null) {
            return createPersonnageLevelStage(personnageLevelStage);
        }
        PersonnageLevelStage result = personnageLevelStageRepository.save(personnageLevelStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personnageLevelStage", personnageLevelStage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personnage-level-stages : get all the personnageLevelStages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personnageLevelStages in body
     */
    @GetMapping("/personnage-level-stages")
    @Timed
    public List<PersonnageLevelStage> getAllPersonnageLevelStages() {
        log.debug("REST request to get all PersonnageLevelStages");
        List<PersonnageLevelStage> personnageLevelStages = personnageLevelStageRepository.findAll();
        return personnageLevelStages;
    }

    /**
     * GET  /personnage-level-stages/:id : get the "id" personnageLevelStage.
     *
     * @param id the id of the personnageLevelStage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personnageLevelStage, or with status 404 (Not Found)
     */
    @GetMapping("/personnage-level-stages/{id}")
    @Timed
    public ResponseEntity<PersonnageLevelStage> getPersonnageLevelStage(@PathVariable Long id) {
        log.debug("REST request to get PersonnageLevelStage : {}", id);
        PersonnageLevelStage personnageLevelStage = personnageLevelStageRepository.findOne(id);
        return Optional.ofNullable(personnageLevelStage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personnage-level-stages/:id : delete the "id" personnageLevelStage.
     *
     * @param id the id of the personnageLevelStage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personnage-level-stages/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonnageLevelStage(@PathVariable Long id) {
        log.debug("REST request to delete PersonnageLevelStage : {}", id);
        personnageLevelStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personnageLevelStage", id.toString())).build();
    }

}
