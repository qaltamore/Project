package fr.hack47.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hack47.domain.BuildingType;

import fr.hack47.repository.BuildingTypeRepository;
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
 * REST controller for managing BuildingType.
 */
@RestController
@RequestMapping("/api")
public class BuildingTypeResource {

    private final Logger log = LoggerFactory.getLogger(BuildingTypeResource.class);
        
    @Inject
    private BuildingTypeRepository buildingTypeRepository;

    /**
     * POST  /building-types : Create a new buildingType.
     *
     * @param buildingType the buildingType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new buildingType, or with status 400 (Bad Request) if the buildingType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/building-types")
    @Timed
    public ResponseEntity<BuildingType> createBuildingType(@RequestBody BuildingType buildingType) throws URISyntaxException {
        log.debug("REST request to save BuildingType : {}", buildingType);
        if (buildingType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("buildingType", "idexists", "A new buildingType cannot already have an ID")).body(null);
        }
        BuildingType result = buildingTypeRepository.save(buildingType);
        return ResponseEntity.created(new URI("/api/building-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("buildingType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /building-types : Updates an existing buildingType.
     *
     * @param buildingType the buildingType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated buildingType,
     * or with status 400 (Bad Request) if the buildingType is not valid,
     * or with status 500 (Internal Server Error) if the buildingType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/building-types")
    @Timed
    public ResponseEntity<BuildingType> updateBuildingType(@RequestBody BuildingType buildingType) throws URISyntaxException {
        log.debug("REST request to update BuildingType : {}", buildingType);
        if (buildingType.getId() == null) {
            return createBuildingType(buildingType);
        }
        BuildingType result = buildingTypeRepository.save(buildingType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("buildingType", buildingType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /building-types : get all the buildingTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of buildingTypes in body
     */
    @GetMapping("/building-types")
    @Timed
    public List<BuildingType> getAllBuildingTypes() {
        log.debug("REST request to get all BuildingTypes");
        List<BuildingType> buildingTypes = buildingTypeRepository.findAll();
        return buildingTypes;
    }

    /**
     * GET  /building-types/:id : get the "id" buildingType.
     *
     * @param id the id of the buildingType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the buildingType, or with status 404 (Not Found)
     */
    @GetMapping("/building-types/{id}")
    @Timed
    public ResponseEntity<BuildingType> getBuildingType(@PathVariable Long id) {
        log.debug("REST request to get BuildingType : {}", id);
        BuildingType buildingType = buildingTypeRepository.findOne(id);
        return Optional.ofNullable(buildingType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /building-types/:id : delete the "id" buildingType.
     *
     * @param id the id of the buildingType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/building-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteBuildingType(@PathVariable Long id) {
        log.debug("REST request to delete BuildingType : {}", id);
        buildingTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("buildingType", id.toString())).build();
    }

}
