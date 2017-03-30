package fr.projet.web.rest;

import fr.projet.JHipsterAppliApp;

import fr.projet.domain.BuildingType;
import fr.projet.repository.BuildingTypeRepository;

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
 * Test class for the BuildingTypeResource REST controller.
 *
 * @see BuildingTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterAppliApp.class)
public class BuildingTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Inject
    private BuildingTypeRepository buildingTypeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restBuildingTypeMockMvc;

    private BuildingType buildingType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BuildingTypeResource buildingTypeResource = new BuildingTypeResource();
        ReflectionTestUtils.setField(buildingTypeResource, "buildingTypeRepository", buildingTypeRepository);
        this.restBuildingTypeMockMvc = MockMvcBuilders.standaloneSetup(buildingTypeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuildingType createEntity(EntityManager em) {
        BuildingType buildingType = new BuildingType()
                .name(DEFAULT_NAME);
        return buildingType;
    }

    @Before
    public void initTest() {
        buildingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuildingType() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeRepository.findAll().size();

        // Create the BuildingType

        restBuildingTypeMockMvc.perform(post("/api/building-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingType)))
            .andExpect(status().isCreated());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBuildingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buildingTypeRepository.findAll().size();

        // Create the BuildingType with an existing ID
        BuildingType existingBuildingType = new BuildingType();
        existingBuildingType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuildingTypeMockMvc.perform(post("/api/building-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingBuildingType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuildingTypes() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        // Get all the buildingTypeList
        restBuildingTypeMockMvc.perform(get("/api/building-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buildingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);

        // Get the buildingType
        restBuildingTypeMockMvc.perform(get("/api/building-types/{id}", buildingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buildingType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuildingType() throws Exception {
        // Get the buildingType
        restBuildingTypeMockMvc.perform(get("/api/building-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();

        // Update the buildingType
        BuildingType updatedBuildingType = buildingTypeRepository.findOne(buildingType.getId());
        updatedBuildingType
                .name(UPDATED_NAME);

        restBuildingTypeMockMvc.perform(put("/api/building-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBuildingType)))
            .andExpect(status().isOk());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate);
        BuildingType testBuildingType = buildingTypeList.get(buildingTypeList.size() - 1);
        assertThat(testBuildingType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBuildingType() throws Exception {
        int databaseSizeBeforeUpdate = buildingTypeRepository.findAll().size();

        // Create the BuildingType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuildingTypeMockMvc.perform(put("/api/building-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buildingType)))
            .andExpect(status().isCreated());

        // Validate the BuildingType in the database
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuildingType() throws Exception {
        // Initialize the database
        buildingTypeRepository.saveAndFlush(buildingType);
        int databaseSizeBeforeDelete = buildingTypeRepository.findAll().size();

        // Get the buildingType
        restBuildingTypeMockMvc.perform(delete("/api/building-types/{id}", buildingType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BuildingType> buildingTypeList = buildingTypeRepository.findAll();
        assertThat(buildingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
