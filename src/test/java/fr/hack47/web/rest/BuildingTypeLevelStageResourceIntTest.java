package fr.hack47.web.rest;

import fr.hack47.Hack47App;

import fr.hack47.domain.BuildingTypeLevelStage;
import fr.hack47.repository.BuildingTypeLevelStageRepository;

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
 * Test class for the BuildingTypeLevelStageResource REST controller.
 *
 * @see BuildingTypeLevelStageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hack47App.class)
public class BuildingTypeLevelStageResourceIntTest {

    private static final Integer DEFAULT_BUILDINGS_QUANTITY = 1;
    private static final Integer UPDATED_BUILDINGS_QUANTITY = 2;

    @Inject
    private BuildingTypeLevelStageRepository buildingTypeLevelStageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBuildingTypeLevelStageMockMvc;

    private BuildingTypeLevelStage buildingTypeLevelStage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BuildingTypeLevelStageResource buildingTypeLevelStageResource = new BuildingTypeLevelStageResource();
        ReflectionTestUtils.setField(buildingTypeLevelStageResource, "buildingTypeLevelStageRepository", buildingTypeLevelStageRepository);
        this.restBuildingTypeLevelStageMockMvc = MockMvcBuilders.standaloneSetup(buildingTypeLevelStageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingTypeLevelStage createEntity(EntityManager em) {
        BuildingTypeLevelStage buildingTypeLevelStage = new BuildingTypeLevelStage()
                .buildingsQuantity(DEFAULT_BUILDINGS_QUANTITY);
        return buildingTypeLevelStage;
    }

    @Before
    public void initTest() {
        buildingTypeLevelStage = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuildingTypeLevelStage() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeLevelStageRepository.findAll().size();

        // Create the BuildingTypeLevelStage

        restBuildingTypeLevelStageMockMvc.perform(post("/api/building-type-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingTypeLevelStage)))
            .andExpect(status().isCreated());

        // Validate the BuildingTypeLevelStage in the database
        List<BuildingTypeLevelStage> buildingTypeLevelStageList = buildingTypeLevelStageRepository.findAll();
        assertThat(buildingTypeLevelStageList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingTypeLevelStage testBuildingTypeLevelStage = buildingTypeLevelStageList.get(buildingTypeLevelStageList.size() - 1);
        assertThat(testBuildingTypeLevelStage.getBuildingsQuantity()).isEqualTo(DEFAULT_BUILDINGS_QUANTITY);
    }

    @Test
    @Transactional
    public void createBuildingTypeLevelStageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeLevelStageRepository.findAll().size();

        // Create the BuildingTypeLevelStage with an existing ID
        BuildingTypeLevelStage existingBuildingTypeLevelStage = new BuildingTypeLevelStage();
        existingBuildingTypeLevelStage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingTypeLevelStageMockMvc.perform(post("/api/building-type-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBuildingTypeLevelStage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BuildingTypeLevelStage> buildingTypeLevelStageList = buildingTypeLevelStageRepository.findAll();
        assertThat(buildingTypeLevelStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuildingTypeLevelStages() throws Exception {
        // Initialize the database
        buildingTypeLevelStageRepository.saveAndFlush(buildingTypeLevelStage);

        // Get all the buildingTypeLevelStageList
        restBuildingTypeLevelStageMockMvc.perform(get("/api/building-type-level-stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingTypeLevelStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].buildingsQuantity").value(hasItem(DEFAULT_BUILDINGS_QUANTITY)));
    }

    @Test
    @Transactional
    public void getBuildingTypeLevelStage() throws Exception {
        // Initialize the database
        buildingTypeLevelStageRepository.saveAndFlush(buildingTypeLevelStage);

        // Get the buildingTypeLevelStage
        restBuildingTypeLevelStageMockMvc.perform(get("/api/building-type-level-stages/{id}", buildingTypeLevelStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buildingTypeLevelStage.getId().intValue()))
            .andExpect(jsonPath("$.buildingsQuantity").value(DEFAULT_BUILDINGS_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingTypeLevelStage() throws Exception {
        // Get the buildingTypeLevelStage
        restBuildingTypeLevelStageMockMvc.perform(get("/api/building-type-level-stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingTypeLevelStage() throws Exception {
        // Initialize the database
        buildingTypeLevelStageRepository.saveAndFlush(buildingTypeLevelStage);
        int databaseSizeBeforeUpdate = buildingTypeLevelStageRepository.findAll().size();

        // Update the buildingTypeLevelStage
        BuildingTypeLevelStage updatedBuildingTypeLevelStage = buildingTypeLevelStageRepository.findOne(buildingTypeLevelStage.getId());
        updatedBuildingTypeLevelStage
                .buildingsQuantity(UPDATED_BUILDINGS_QUANTITY);

        restBuildingTypeLevelStageMockMvc.perform(put("/api/building-type-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuildingTypeLevelStage)))
            .andExpect(status().isOk());

        // Validate the BuildingTypeLevelStage in the database
        List<BuildingTypeLevelStage> buildingTypeLevelStageList = buildingTypeLevelStageRepository.findAll();
        assertThat(buildingTypeLevelStageList).hasSize(databaseSizeBeforeUpdate);
        BuildingTypeLevelStage testBuildingTypeLevelStage = buildingTypeLevelStageList.get(buildingTypeLevelStageList.size() - 1);
        assertThat(testBuildingTypeLevelStage.getBuildingsQuantity()).isEqualTo(UPDATED_BUILDINGS_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingBuildingTypeLevelStage() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeLevelStageRepository.findAll().size();

        // Create the BuildingTypeLevelStage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingTypeLevelStageMockMvc.perform(put("/api/building-type-level-stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingTypeLevelStage)))
            .andExpect(status().isCreated());

        // Validate the BuildingTypeLevelStage in the database
        List<BuildingTypeLevelStage> buildingTypeLevelStageList = buildingTypeLevelStageRepository.findAll();
        assertThat(buildingTypeLevelStageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuildingTypeLevelStage() throws Exception {
        // Initialize the database
        buildingTypeLevelStageRepository.saveAndFlush(buildingTypeLevelStage);
        int databaseSizeBeforeDelete = buildingTypeLevelStageRepository.findAll().size();

        // Get the buildingTypeLevelStage
        restBuildingTypeLevelStageMockMvc.perform(delete("/api/building-type-level-stages/{id}", buildingTypeLevelStage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingTypeLevelStage> buildingTypeLevelStageList = buildingTypeLevelStageRepository.findAll();
        assertThat(buildingTypeLevelStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
