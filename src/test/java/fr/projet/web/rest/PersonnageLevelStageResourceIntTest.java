package fr.projet.web.rest;

import fr.projet.JHipsterAppliApp;

import fr.projet.domain.PersonnageLevelStage;
import fr.projet.repository.PersonnageLevelStageRepository;

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
 * Test class for the PersonnageLevelStageResource REST controller.
 *
 * @see PersonnageLevelStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterAppliApp.class)
public class PersonnageLevelStageResourceIntTest {

    private static final Integer DEFAULT_PERSONNAGES_QUANTITY = 1;
    private static final Integer UPDATED_PERSONNAGES_QUANTITY = 2;

    @Inject
    private PersonnageLevelStageRepository personnageLevelStageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonnageLevelStageMockMvc;

    private PersonnageLevelStage personnageLevelStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonnageLevelStageResource personnageLevelStageResource = new PersonnageLevelStageResource();
        ReflectionTestUtils.setField(personnageLevelStageResource, "personnageLevelStageRepository", personnageLevelStageRepository);
        this.restPersonnageLevelStageMockMvc = MockMvcBuilders.standaloneSetup(personnageLevelStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonnageLevelStage createEntity(EntityManager em) {
        PersonnageLevelStage personnageLevelStage = new PersonnageLevelStage()
                .personnagesQuantity(DEFAULT_PERSONNAGES_QUANTITY);
        return personnageLevelStage;
    }

    @Before
    public void initTest() {
        personnageLevelStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnageLevelStage() throws Exception {
        int databaseSizeBeforeCreate = personnageLevelStageRepository.findAll().size();

        // Create the PersonnageLevelStage

        restPersonnageLevelStageMockMvc.perform(post("/api/personnage-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnageLevelStage)))
            .andExpect(status().isCreated());

        // Validate the PersonnageLevelStage in the database
        List<PersonnageLevelStage> personnageLevelStageList = personnageLevelStageRepository.findAll();
        assertThat(personnageLevelStageList).hasSize(databaseSizeBeforeCreate + 1);
        PersonnageLevelStage testPersonnageLevelStage = personnageLevelStageList.get(personnageLevelStageList.size() - 1);
        assertThat(testPersonnageLevelStage.getPersonnagesQuantity()).isEqualTo(DEFAULT_PERSONNAGES_QUANTITY);
    }

    @Test
    @Transactional
    public void createPersonnageLevelStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnageLevelStageRepository.findAll().size();

        // Create the PersonnageLevelStage with an existing ID
        PersonnageLevelStage existingPersonnageLevelStage = new PersonnageLevelStage();
        existingPersonnageLevelStage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnageLevelStageMockMvc.perform(post("/api/personnage-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonnageLevelStage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<PersonnageLevelStage> personnageLevelStageList = personnageLevelStageRepository.findAll();
        assertThat(personnageLevelStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonnageLevelStages() throws Exception {
        // Initialize the database
        personnageLevelStageRepository.saveAndFlush(personnageLevelStage);

        // Get all the personnageLevelStageList
        restPersonnageLevelStageMockMvc.perform(get("/api/personnage-level-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnageLevelStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].personnagesQuantity").value(hasItem(DEFAULT_PERSONNAGES_QUANTITY)));
    }

    @Test
    @Transactional
    public void getPersonnageLevelStage() throws Exception {
        // Initialize the database
        personnageLevelStageRepository.saveAndFlush(personnageLevelStage);

        // Get the personnageLevelStage
        restPersonnageLevelStageMockMvc.perform(get("/api/personnage-level-stages/{id}", personnageLevelStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personnageLevelStage.getId().intValue()))
            .andExpect(jsonPath("$.personnagesQuantity").value(DEFAULT_PERSONNAGES_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingPersonnageLevelStage() throws Exception {
        // Get the personnageLevelStage
        restPersonnageLevelStageMockMvc.perform(get("/api/personnage-level-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnageLevelStage() throws Exception {
        // Initialize the database
        personnageLevelStageRepository.saveAndFlush(personnageLevelStage);
        int databaseSizeBeforeUpdate = personnageLevelStageRepository.findAll().size();

        // Update the personnageLevelStage
        PersonnageLevelStage updatedPersonnageLevelStage = personnageLevelStageRepository.findOne(personnageLevelStage.getId());
        updatedPersonnageLevelStage
                .personnagesQuantity(UPDATED_PERSONNAGES_QUANTITY);

        restPersonnageLevelStageMockMvc.perform(put("/api/personnage-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonnageLevelStage)))
            .andExpect(status().isOk());

        // Validate the PersonnageLevelStage in the database
        List<PersonnageLevelStage> personnageLevelStageList = personnageLevelStageRepository.findAll();
        assertThat(personnageLevelStageList).hasSize(databaseSizeBeforeUpdate);
        PersonnageLevelStage testPersonnageLevelStage = personnageLevelStageList.get(personnageLevelStageList.size() - 1);
        assertThat(testPersonnageLevelStage.getPersonnagesQuantity()).isEqualTo(UPDATED_PERSONNAGES_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnageLevelStage() throws Exception {
        int databaseSizeBeforeUpdate = personnageLevelStageRepository.findAll().size();

        // Create the PersonnageLevelStage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonnageLevelStageMockMvc.perform(put("/api/personnage-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnageLevelStage)))
            .andExpect(status().isCreated());

        // Validate the PersonnageLevelStage in the database
        List<PersonnageLevelStage> personnageLevelStageList = personnageLevelStageRepository.findAll();
        assertThat(personnageLevelStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonnageLevelStage() throws Exception {
        // Initialize the database
        personnageLevelStageRepository.saveAndFlush(personnageLevelStage);
        int databaseSizeBeforeDelete = personnageLevelStageRepository.findAll().size();

        // Get the personnageLevelStage
        restPersonnageLevelStageMockMvc.perform(delete("/api/personnage-level-stages/{id}", personnageLevelStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PersonnageLevelStage> personnageLevelStageList = personnageLevelStageRepository.findAll();
        assertThat(personnageLevelStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
