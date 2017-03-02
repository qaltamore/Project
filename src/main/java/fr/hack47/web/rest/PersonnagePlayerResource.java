package fr.hack47.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hack47.domain.PersonnagePlayer;

import fr.hack47.repository.PersonnagePlayerRepository;
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
 * REST controller for managing PersonnagePlayer.
 */
@RestController
@RequestMapping("/api")
public class PersonnagePlayerResource {

    private final Logger log = LoggerFactory.getLogger(PersonnagePlayerResource.class);
        
    @Inject
    private PersonnagePlayerRepository personnagePlayerRepository;

    /**
     * POST  /personnage-players : Create a new personnagePlayer.
     *
     * @param personnagePlayer the personnagePlayer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new personnagePlayer, or with status 400 (Bad Request) if the personnagePlayer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/personnage-players")
    @Timed
    public ResponseEntity<PersonnagePlayer> createPersonnagePlayer(@RequestBody PersonnagePlayer personnagePlayer) throws URISyntaxException {
        log.debug("REST request to save PersonnagePlayer : {}", personnagePlayer);
        if (personnagePlayer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("personnagePlayer", "idexists", "A new personnagePlayer cannot already have an ID")).body(null);
        }
        PersonnagePlayer result = personnagePlayerRepository.save(personnagePlayer);
        return ResponseEntity.created(new URI("/api/personnage-players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("personnagePlayer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /personnage-players : Updates an existing personnagePlayer.
     *
     * @param personnagePlayer the personnagePlayer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated personnagePlayer,
     * or with status 400 (Bad Request) if the personnagePlayer is not valid,
     * or with status 500 (Internal Server Error) if the personnagePlayer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/personnage-players")
    @Timed
    public ResponseEntity<PersonnagePlayer> updatePersonnagePlayer(@RequestBody PersonnagePlayer personnagePlayer) throws URISyntaxException {
        log.debug("REST request to update PersonnagePlayer : {}", personnagePlayer);
        if (personnagePlayer.getId() == null) {
            return createPersonnagePlayer(personnagePlayer);
        }
        PersonnagePlayer result = personnagePlayerRepository.save(personnagePlayer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("personnagePlayer", personnagePlayer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /personnage-players : get all the personnagePlayers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of personnagePlayers in body
     */
    @GetMapping("/personnage-players")
    @Timed
    public List<PersonnagePlayer> getAllPersonnagePlayers() {
        log.debug("REST request to get all PersonnagePlayers");
        List<PersonnagePlayer> personnagePlayers = personnagePlayerRepository.findAll();
        return personnagePlayers;
    }

    /**
     * GET  /personnage-players/:id : get the "id" personnagePlayer.
     *
     * @param id the id of the personnagePlayer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the personnagePlayer, or with status 404 (Not Found)
     */
    @GetMapping("/personnage-players/{id}")
    @Timed
    public ResponseEntity<PersonnagePlayer> getPersonnagePlayer(@PathVariable Long id) {
        log.debug("REST request to get PersonnagePlayer : {}", id);
        PersonnagePlayer personnagePlayer = personnagePlayerRepository.findOne(id);
        return Optional.ofNullable(personnagePlayer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /personnage-players/:id : delete the "id" personnagePlayer.
     *
     * @param id the id of the personnagePlayer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/personnage-players/{id}")
    @Timed
    public ResponseEntity<Void> deletePersonnagePlayer(@PathVariable Long id) {
        log.debug("REST request to delete PersonnagePlayer : {}", id);
        personnagePlayerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("personnagePlayer", id.toString())).build();
    }

}
