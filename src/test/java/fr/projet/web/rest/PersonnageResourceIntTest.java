package fr.projet.web.rest;

import fr.projet.JHipsterAppliApp;

import fr.projet.domain.Personnage;
import fr.projet.repository.PersonnageRepository;

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
import org.springframework.util.Base64Utils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.projet.domain.enumeration.Type;
import fr.projet.domain.enumeration.Role;
/**
 * Test class for the PersonnageResource REST controller.
 *
 * @see PersonnageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterAppliApp.class)
public class PersonnageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Type DEFAULT_TYPE = Type.FOR;
    private static final Type UPDATED_TYPE = Type.VIT;

    private static final Integer DEFAULT_LIFE_POINTS = 1;
    private static final Integer UPDATED_LIFE_POINTS = 2;

    private static final Integer DEFAULT_MOVEMENT_POINTS = 1;
    private static final Integer UPDATED_MOVEMENT_POINTS = 2;

    private static final Integer DEFAULT_ATTACK_POINTS = 1;
    private static final Integer UPDATED_ATTACK_POINTS = 2;

    private static final Integer DEFAULT_DEFENSE_POINTS = 1;
    private static final Integer UPDATED_DEFENSE_POINTS = 2;

    private static final Integer DEFAULT_MAGIC_POINTS = 1;
    private static final Integer UPDATED_MAGIC_POINTS = 2;

    private static final String DEFAULT_CAPACITY = "AAAAAAAAAA";
    private static final String UPDATED_CAPACITY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IN_LIVE = false;
    private static final Boolean UPDATED_IN_LIVE = true;

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

    private static final Role DEFAULT_PLAYING_BY = Role.ATK;
    private static final Role UPDATED_PLAYING_BY = Role.DEF;

    @Inject
    private PersonnageRepository personnageRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonnageMockMvc;

    private Personnage personnage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonnageResource personnageResource = new PersonnageResource();
        ReflectionTestUtils.setField(personnageResource, "personnageRepository", personnageRepository);
        this.restPersonnageMockMvc = MockMvcBuilders.standaloneSetup(personnageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Personnage createEntity(EntityManager em) {
        Personnage personnage = new Personnage()
                .name(DEFAULT_NAME)
                .type(DEFAULT_TYPE)
                .lifePoints(DEFAULT_LIFE_POINTS)
                .movementPoints(DEFAULT_MOVEMENT_POINTS)
                .attackPoints(DEFAULT_ATTACK_POINTS)
                .defensePoints(DEFAULT_DEFENSE_POINTS)
                .magicPoints(DEFAULT_MAGIC_POINTS)
                .capacity(DEFAULT_CAPACITY)
                .inLive(DEFAULT_IN_LIVE)
                .img(DEFAULT_IMG)
                .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
                .playingBy(DEFAULT_PLAYING_BY);
        return personnage;
    }

    @Before
    public void initTest() {
        personnage = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersonnage() throws Exception {
        int databaseSizeBeforeCreate = personnageRepository.findAll().size();

        // Create the Personnage

        restPersonnageMockMvc.perform(post("/api/personnages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnage)))
            .andExpect(status().isCreated());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeCreate + 1);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPersonnage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPersonnage.getLifePoints()).isEqualTo(DEFAULT_LIFE_POINTS);
        assertThat(testPersonnage.getMovementPoints()).isEqualTo(DEFAULT_MOVEMENT_POINTS);
        assertThat(testPersonnage.getAttackPoints()).isEqualTo(DEFAULT_ATTACK_POINTS);
        assertThat(testPersonnage.getDefensePoints()).isEqualTo(DEFAULT_DEFENSE_POINTS);
        assertThat(testPersonnage.getMagicPoints()).isEqualTo(DEFAULT_MAGIC_POINTS);
        assertThat(testPersonnage.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testPersonnage.isInLive()).isEqualTo(DEFAULT_IN_LIVE);
        assertThat(testPersonnage.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testPersonnage.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testPersonnage.getPlayingBy()).isEqualTo(DEFAULT_PLAYING_BY);
    }

    @Test
    @Transactional
    public void createPersonnageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personnageRepository.findAll().size();

        // Create the Personnage with an existing ID
        Personnage existingPersonnage = new Personnage();
        existingPersonnage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonnageMockMvc.perform(post("/api/personnages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingPersonnage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPersonnages() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        // Get all the personnageList
        restPersonnageMockMvc.perform(get("/api/personnages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personnage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lifePoints").value(hasItem(DEFAULT_LIFE_POINTS)))
            .andExpect(jsonPath("$.[*].movementPoints").value(hasItem(DEFAULT_MOVEMENT_POINTS)))
            .andExpect(jsonPath("$.[*].attackPoints").value(hasItem(DEFAULT_ATTACK_POINTS)))
            .andExpect(jsonPath("$.[*].defensePoints").value(hasItem(DEFAULT_DEFENSE_POINTS)))
            .andExpect(jsonPath("$.[*].magicPoints").value(hasItem(DEFAULT_MAGIC_POINTS)))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY.toString())))
            .andExpect(jsonPath("$.[*].inLive").value(hasItem(DEFAULT_IN_LIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].playingBy").value(hasItem(DEFAULT_PLAYING_BY.toString())));
    }

    @Test
    @Transactional
    public void getPersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);

        // Get the personnage
        restPersonnageMockMvc.perform(get("/api/personnages/{id}", personnage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(personnage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.lifePoints").value(DEFAULT_LIFE_POINTS))
            .andExpect(jsonPath("$.movementPoints").value(DEFAULT_MOVEMENT_POINTS))
            .andExpect(jsonPath("$.attackPoints").value(DEFAULT_ATTACK_POINTS))
            .andExpect(jsonPath("$.defensePoints").value(DEFAULT_DEFENSE_POINTS))
            .andExpect(jsonPath("$.magicPoints").value(DEFAULT_MAGIC_POINTS))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY.toString()))
            .andExpect(jsonPath("$.inLive").value(DEFAULT_IN_LIVE.booleanValue()))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.playingBy").value(DEFAULT_PLAYING_BY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPersonnage() throws Exception {
        // Get the personnage
        restPersonnageMockMvc.perform(get("/api/personnages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();

        // Update the personnage
        Personnage updatedPersonnage = personnageRepository.findOne(personnage.getId());
        updatedPersonnage
                .name(UPDATED_NAME)
                .type(UPDATED_TYPE)
                .lifePoints(UPDATED_LIFE_POINTS)
                .movementPoints(UPDATED_MOVEMENT_POINTS)
                .attackPoints(UPDATED_ATTACK_POINTS)
                .defensePoints(UPDATED_DEFENSE_POINTS)
                .magicPoints(UPDATED_MAGIC_POINTS)
                .capacity(UPDATED_CAPACITY)
                .inLive(UPDATED_IN_LIVE)
                .img(UPDATED_IMG)
                .imgContentType(UPDATED_IMG_CONTENT_TYPE)
                .playingBy(UPDATED_PLAYING_BY);

        restPersonnageMockMvc.perform(put("/api/personnages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersonnage)))
            .andExpect(status().isOk());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate);
        Personnage testPersonnage = personnageList.get(personnageList.size() - 1);
        assertThat(testPersonnage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPersonnage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPersonnage.getLifePoints()).isEqualTo(UPDATED_LIFE_POINTS);
        assertThat(testPersonnage.getMovementPoints()).isEqualTo(UPDATED_MOVEMENT_POINTS);
        assertThat(testPersonnage.getAttackPoints()).isEqualTo(UPDATED_ATTACK_POINTS);
        assertThat(testPersonnage.getDefensePoints()).isEqualTo(UPDATED_DEFENSE_POINTS);
        assertThat(testPersonnage.getMagicPoints()).isEqualTo(UPDATED_MAGIC_POINTS);
        assertThat(testPersonnage.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testPersonnage.isInLive()).isEqualTo(UPDATED_IN_LIVE);
        assertThat(testPersonnage.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testPersonnage.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testPersonnage.getPlayingBy()).isEqualTo(UPDATED_PLAYING_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingPersonnage() throws Exception {
        int databaseSizeBeforeUpdate = personnageRepository.findAll().size();

        // Create the Personnage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPersonnageMockMvc.perform(put("/api/personnages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(personnage)))
            .andExpect(status().isCreated());

        // Validate the Personnage in the database
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePersonnage() throws Exception {
        // Initialize the database
        personnageRepository.saveAndFlush(personnage);
        int databaseSizeBeforeDelete = personnageRepository.findAll().size();

        // Get the personnage
        restPersonnageMockMvc.perform(delete("/api/personnages/{id}", personnage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Personnage> personnageList = personnageRepository.findAll();
        assertThat(personnageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
