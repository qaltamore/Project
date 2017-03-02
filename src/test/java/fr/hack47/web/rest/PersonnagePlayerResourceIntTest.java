package fr.hack47.web.rest;

import fr.hack47.Hack47App;

import fr.hack47.domain.PersonnagePlayer;
import fr.hack47.repository.PersonnagePlayerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonnagePlayerResource REST controller.
 *
 * @see PersonnagePlayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hack47App.class)
public class PersonnagePlayerResourceIntTest {

    @Inject
    private PersonnagePlayerRepository personnagePlayerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonnagePlayerMockMvc;

    private PersonnagePlayer personnagePlayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonnagePlayerResource personnagePlayerResource = new PersonnagePlayerResource();
        ReflectionTestUtils.setField(personnagePlayerResource, "personnagePlayerRepository", personnagePlayerRepository);
        this.restPersonnagePlayerMockMvc = MockMvcBuilders.standaloneSetup(personnagePlayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonnagePlayer createEntity(EntityManager em) {
        PersonnagePlayer personnagePlayer = new PersonnagePlayer();
        return personnagePlayer;
    }

    @Before
    public void initTest() {
        personnagePlayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnagePlayer() throws Exception {
        int databaseSizeBeforeCreate = personnagePlayerRepository.findAll().size();

        // Create the PersonnagePlayer

        restPersonnagePlayerMockMvc.perform(post("/api/personnage-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnagePlayer)))
            .andExpect(status().isCreated());

        // Validate the PersonnagePlayer in the database
        List<PersonnagePlayer> personnagePlayerList = personnagePlayerRepository.findAll();
        assertThat(personnagePlayerList).hasSize(databaseSizeBeforeCreate + 1);
        PersonnagePlayer testPersonnagePlayer = personnagePlayerList.get(personnagePlayerList.size() - 1);
    }

    @Test
    @Transactional
    public void createPersonnagePlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnagePlayerRepository.findAll().size();

        // Create the PersonnagePlayer with an existing ID
        PersonnagePlayer existingPersonnagePlayer = new PersonnagePlayer();
        existingPersonnagePlayer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnagePlayerMockMvc.perform(post("/api/personnage-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonnagePlayer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonnagePlayer> personnagePlayerList = personnagePlayerRepository.findAll();
        assertThat(personnagePlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonnagePlayers() throws Exception {
        // Initialize the database
        personnagePlayerRepository.saveAndFlush(personnagePlayer);

        // Get all the personnagePlayerList
        restPersonnagePlayerMockMvc.perform(get("/api/personnage-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnagePlayer.getId().intValue())));
    }

    @Test
    @Transactional
    public void getPersonnagePlayer() throws Exception {
        // Initialize the database
        personnagePlayerRepository.saveAndFlush(personnagePlayer);

        // Get the personnagePlayer
        restPersonnagePlayerMockMvc.perform(get("/api/personnage-players/{id}", personnagePlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personnagePlayer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonnagePlayer() throws Exception {
        // Get the personnagePlayer
        restPersonnagePlayerMockMvc.perform(get("/api/personnage-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnagePlayer() throws Exception {
        // Initialize the database
        personnagePlayerRepository.saveAndFlush(personnagePlayer);
        int databaseSizeBeforeUpdate = personnagePlayerRepository.findAll().size();

        // Update the personnagePlayer
        PersonnagePlayer updatedPersonnagePlayer = personnagePlayerRepository.findOne(personnagePlayer.getId());

        restPersonnagePlayerMockMvc.perform(put("/api/personnage-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonnagePlayer)))
            .andExpect(status().isOk());

        // Validate the PersonnagePlayer in the database
        List<PersonnagePlayer> personnagePlayerList = personnagePlayerRepository.findAll();
        assertThat(personnagePlayerList).hasSize(databaseSizeBeforeUpdate);
        PersonnagePlayer testPersonnagePlayer = personnagePlayerList.get(personnagePlayerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnagePlayer() throws Exception {
        int databaseSizeBeforeUpdate = personnagePlayerRepository.findAll().size();

        // Create the PersonnagePlayer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonnagePlayerMockMvc.perform(put("/api/personnage-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnagePlayer)))
            .andExpect(status().isCreated());

        // Validate the PersonnagePlayer in the database
        List<PersonnagePlayer> personnagePlayerList = personnagePlayerRepository.findAll();
        assertThat(personnagePlayerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonnagePlayer() throws Exception {
        // Initialize the database
        personnagePlayerRepository.saveAndFlush(personnagePlayer);
        int databaseSizeBeforeDelete = personnagePlayerRepository.findAll().size();

        // Get the personnagePlayer
        restPersonnagePlayerMockMvc.perform(delete("/api/personnage-players/{id}", personnagePlayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonnagePlayer> personnagePlayerList = personnagePlayerRepository.findAll();
        assertThat(personnagePlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
