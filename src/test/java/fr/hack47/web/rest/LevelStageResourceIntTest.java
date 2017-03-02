package fr.hack47.web.rest;

import fr.hack47.Hack47App;

import fr.hack47.domain.LevelStage;
import fr.hack47.repository.LevelStageRepository;

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
 * Test class for the LevelStageResource REST controller.
 *
 * @see LevelStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hack47App.class)
public class LevelStageResourceIntTest {

    private static final Integer DEFAULT_ID_LEVEL_STAGE = 1;
    private static final Integer UPDATED_ID_LEVEL_STAGE = 2;

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    @Inject
    private LevelStageRepository levelStageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLevelStageMockMvc;

    private LevelStage levelStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LevelStageResource levelStageResource = new LevelStageResource();
        ReflectionTestUtils.setField(levelStageResource, "levelStageRepository", levelStageRepository);
        this.restLevelStageMockMvc = MockMvcBuilders.standaloneSetup(levelStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LevelStage createEntity(EntityManager em) {
        LevelStage levelStage = new LevelStage()
                .idLevelStage(DEFAULT_ID_LEVEL_STAGE)
                .numero(DEFAULT_NUMERO);
        return levelStage;
    }

    @Before
    public void initTest() {
        levelStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createLevelStage() throws Exception {
        int databaseSizeBeforeCreate = levelStageRepository.findAll().size();

        // Create the LevelStage

        restLevelStageMockMvc.perform(post("/api/level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelStage)))
            .andExpect(status().isCreated());

        // Validate the LevelStage in the database
        List<LevelStage> levelStageList = levelStageRepository.findAll();
        assertThat(levelStageList).hasSize(databaseSizeBeforeCreate + 1);
        LevelStage testLevelStage = levelStageList.get(levelStageList.size() - 1);
        assertThat(testLevelStage.getIdLevelStage()).isEqualTo(DEFAULT_ID_LEVEL_STAGE);
        assertThat(testLevelStage.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createLevelStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = levelStageRepository.findAll().size();

        // Create the LevelStage with an existing ID
        LevelStage existingLevelStage = new LevelStage();
        existingLevelStage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLevelStageMockMvc.perform(post("/api/level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLevelStage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LevelStage> levelStageList = levelStageRepository.findAll();
        assertThat(levelStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLevelStages() throws Exception {
        // Initialize the database
        levelStageRepository.saveAndFlush(levelStage);

        // Get all the levelStageList
        restLevelStageMockMvc.perform(get("/api/level-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(levelStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].idLevelStage").value(hasItem(DEFAULT_ID_LEVEL_STAGE)))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }

    @Test
    @Transactional
    public void getLevelStage() throws Exception {
        // Initialize the database
        levelStageRepository.saveAndFlush(levelStage);

        // Get the levelStage
        restLevelStageMockMvc.perform(get("/api/level-stages/{id}", levelStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(levelStage.getId().intValue()))
            .andExpect(jsonPath("$.idLevelStage").value(DEFAULT_ID_LEVEL_STAGE))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingLevelStage() throws Exception {
        // Get the levelStage
        restLevelStageMockMvc.perform(get("/api/level-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLevelStage() throws Exception {
        // Initialize the database
        levelStageRepository.saveAndFlush(levelStage);
        int databaseSizeBeforeUpdate = levelStageRepository.findAll().size();

        // Update the levelStage
        LevelStage updatedLevelStage = levelStageRepository.findOne(levelStage.getId());
        updatedLevelStage
                .idLevelStage(UPDATED_ID_LEVEL_STAGE)
                .numero(UPDATED_NUMERO);

        restLevelStageMockMvc.perform(put("/api/level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLevelStage)))
            .andExpect(status().isOk());

        // Validate the LevelStage in the database
        List<LevelStage> levelStageList = levelStageRepository.findAll();
        assertThat(levelStageList).hasSize(databaseSizeBeforeUpdate);
        LevelStage testLevelStage = levelStageList.get(levelStageList.size() - 1);
        assertThat(testLevelStage.getIdLevelStage()).isEqualTo(UPDATED_ID_LEVEL_STAGE);
        assertThat(testLevelStage.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingLevelStage() throws Exception {
        int databaseSizeBeforeUpdate = levelStageRepository.findAll().size();

        // Create the LevelStage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLevelStageMockMvc.perform(put("/api/level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(levelStage)))
            .andExpect(status().isCreated());

        // Validate the LevelStage in the database
        List<LevelStage> levelStageList = levelStageRepository.findAll();
        assertThat(levelStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLevelStage() throws Exception {
        // Initialize the database
        levelStageRepository.saveAndFlush(levelStage);
        int databaseSizeBeforeDelete = levelStageRepository.findAll().size();

        // Get the levelStage
        restLevelStageMockMvc.perform(delete("/api/level-stages/{id}", levelStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LevelStage> levelStageList = levelStageRepository.findAll();
        assertThat(levelStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
