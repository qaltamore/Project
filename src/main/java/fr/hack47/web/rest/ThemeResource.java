package fr.hack47.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.hack47.domain.Theme;

import fr.hack47.repository.ThemeRepository;
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
 * REST controller for managing Theme.
 */
@RestController
@RequestMapping("/api")
public class ThemeResource {

    private final Logger log = LoggerFactory.getLogger(ThemeResource.class);
        
    @Inject
    private ThemeRepository themeRepository;

    /**
     * POST  /themes : Create a new theme.
     *
     * @param theme the theme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new theme, or with status 400 (Bad Request) if the theme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/themes")
    @Timed
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to save Theme : {}", theme);
        if (theme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("theme", "idexists", "A new theme cannot already have an ID")).body(null);
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.created(new URI("/api/themes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("theme", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /themes : Updates an existing theme.
     *
     * @param theme the theme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated theme,
     * or with status 400 (Bad Request) if the theme is not valid,
     * or with status 500 (Internal Server Error) if the theme couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/themes")
    @Timed
    public ResponseEntity<Theme> updateTheme(@RequestBody Theme theme) throws URISyntaxException {
        log.debug("REST request to update Theme : {}", theme);
        if (theme.getId() == null) {
            return createTheme(theme);
        }
        Theme result = themeRepository.save(theme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("theme", theme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /themes : get all the themes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of themes in body
     */
    @GetMapping("/themes")
    @Timed
    public List<Theme> getAllThemes() {
        log.debug("REST request to get all Themes");
        List<Theme> themes = themeRepository.findAll();
        return themes;
    }

    /**
     * GET  /themes/:id : get the "id" theme.
     *
     * @param id the id of the theme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the theme, or with status 404 (Not Found)
     */
    @GetMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Theme> getTheme(@PathVariable Long id) {
        log.debug("REST request to get Theme : {}", id);
        Theme theme = themeRepository.findOne(id);
        return Optional.ofNullable(theme)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /themes/:id : delete the "id" theme.
     *
     * @param id the id of the theme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/themes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        log.debug("REST request to delete Theme : {}", id);
        themeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("theme", id.toString())).build();
    }

}
