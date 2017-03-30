package fr.projet.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.projet.domain.Personnage;

import fr.projet.repository.PersonnageRepository;
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
 * REST controller for managing Personnage.
 */
@RestController
@RequestMapping("/api")
public class PersonnageResource {

    private final Logger log = LoggerFactory.getLogger(PersonnageResource.class);
        
    @Inject
    private PersonnageRepository personnageRepository;

    /**
     * POST  /personnages : Create a new personnage.
     *
     * @param personnage the personnage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personnage, or with status 400 (Bad Request) if the personnage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personnages")
    @Timed
    public ResponseEntity<Personnage> createPersonnage(@RequestBody Personnage personnage) throws URISyntaxException {
        log.debug("REST request to save Personnage : {}", personnage);
        if (personnage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personnage", "idexists", "A new personnage cannot already have an ID")).body(null);
        }
        Personnage result = personnageRepository.save(personnage);
        return ResponseEntity.created(new URI("/api/personnages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personnage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personnages : Updates an existing personnage.
     *
     * @param personnage the personnage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personnage,
     * or with status 400 (Bad Request) if the personnage is not valid,
     * or with status 500 (Internal Server Error) if the personnage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personnages")
    @Timed
    public ResponseEntity<Personnage> updatePersonnage(@RequestBody Personnage personnage) throws URISyntaxException {
        log.debug("REST request to update Personnage : {}", personnage);
        if (personnage.getId() == null) {
            return createPersonnage(personnage);
        }
        Personnage result = personnageRepository.save(personnage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personnage", personnage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personnages : get all the personnages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personnages in body
     */
    @GetMapping("/personnages")
    @Timed
    public List<Personnage> getAllPersonnages() {
        log.debug("REST request to get all Personnages");
        List<Personnage> personnages = personnageRepository.findAll();
        return personnages;
    }

    /**
     * GET  /personnages/:id : get the "id" personnage.
     *
     * @param id the id of the personnage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personnage, or with status 404 (Not Found)
     */
    @GetMapping("/personnages/{id}")
    @Timed
    public ResponseEntity<Personnage> getPersonnage(@PathVariable Long id) {
        log.debug("REST request to get Personnage : {}", id);
        Personnage personnage = personnageRepository.findOne(id);
        return Optional.ofNullable(personnage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personnages/:id : delete the "id" personnage.
     *
     * @param id the id of the personnage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personnages/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonnage(@PathVariable Long id) {
        log.debug("REST request to delete Personnage : {}", id);
        personnageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personnage", id.toString())).build();
    }

}
