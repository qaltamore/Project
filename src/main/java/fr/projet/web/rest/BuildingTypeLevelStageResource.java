package fr.projet.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.projet.domain.BuildingTypeLevelStage;

import fr.projet.repository.BuildingTypeLevelStageRepository;
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
 * REST controller for managing BuildingTypeLevelStage.
 */
@RestController
@RequestMapping("/api")
public class BuildingTypeLevelStageResource {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeLevelStageResource.class);
        
    @Inject
    private BuildingTypeLevelStageRepository buildingTypeLevelStageRepository;

    /**
     * POST  /building-type-level-stages : Create a new buildingTypeLevelStage.
     *
     * @param buildingTypeLevelStage the buildingTypeLevelStage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingTypeLevelStage, or with status 400 (Bad Request) if the buildingTypeLevelStage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-type-level-stages")
    @Timed
    public ResponseEntity<BuildingTypeLevelStage> createBuildingTypeLevelStage(@RequestBody BuildingTypeLevelStage buildingTypeLevelStage) throws URISyntaxException {
        log.debug("REST request to save BuildingTypeLevelStage : {}", buildingTypeLevelStage);
        if (buildingTypeLevelStage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("buildingTypeLevelStage", "idexists", "A new buildingTypeLevelStage cannot already have an ID")).body(null);
        }
        BuildingTypeLevelStage result = buildingTypeLevelStageRepository.save(buildingTypeLevelStage);
        return ResponseEntity.created(new URI("/api/building-type-level-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("buildingTypeLevelStage", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-type-level-stages : Updates an existing buildingTypeLevelStage.
     *
     * @param buildingTypeLevelStage the buildingTypeLevelStage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingTypeLevelStage,
     * or with status 400 (Bad Request) if the buildingTypeLevelStage is not valid,
     * or with status 500 (Internal Server Error) if the buildingTypeLevelStage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-type-level-stages")
    @Timed
    public ResponseEntity<BuildingTypeLevelStage> updateBuildingTypeLevelStage(@RequestBody BuildingTypeLevelStage buildingTypeLevelStage) throws URISyntaxException {
        log.debug("REST request to update BuildingTypeLevelStage : {}", buildingTypeLevelStage);
        if (buildingTypeLevelStage.getId() == null) {
            return createBuildingTypeLevelStage(buildingTypeLevelStage);
        }
        BuildingTypeLevelStage result = buildingTypeLevelStageRepository.save(buildingTypeLevelStage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("buildingTypeLevelStage", buildingTypeLevelStage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-type-level-stages : get all the buildingTypeLevelStages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingTypeLevelStages in body
     */
    @GetMapping("/building-type-level-stages")
    @Timed
    public List<BuildingTypeLevelStage> getAllBuildingTypeLevelStages() {
        log.debug("REST request to get all BuildingTypeLevelStages");
        List<BuildingTypeLevelStage> buildingTypeLevelStages = buildingTypeLevelStageRepository.findAll();
        return buildingTypeLevelStages;
    }

    /**
     * GET  /building-type-level-stages/:id : get the "id" buildingTypeLevelStage.
     *
     * @param id the id of the buildingTypeLevelStage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingTypeLevelStage, or with status 404 (Not Found)
     */
    @GetMapping("/building-type-level-stages/{id}")
    @Timed
    public ResponseEntity<BuildingTypeLevelStage> getBuildingTypeLevelStage(@PathVariable Long id) {
        log.debug("REST request to get BuildingTypeLevelStage : {}", id);
        BuildingTypeLevelStage buildingTypeLevelStage = buildingTypeLevelStageRepository.findOne(id);
        return Optional.ofNullable(buildingTypeLevelStage)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /building-type-level-stages/:id : delete the "id" buildingTypeLevelStage.
     *
     * @param id the id of the buildingTypeLevelStage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-type-level-stages/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuildingTypeLevelStage(@PathVariable Long id) {
        log.debug("REST request to delete BuildingTypeLevelStage : {}", id);
        buildingTypeLevelStageRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("buildingTypeLevelStage", id.toString())).build();
    }

}
