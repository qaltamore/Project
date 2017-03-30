package fr.projet.web.rest;

import fr.projet.JHipsterAppliApp;

import fr.projet.domain.QuestionAnswerPlayer;
import fr.projet.repository.QuestionAnswerPlayerRepository;

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
 * Test class for the QuestionAnswerPlayerResource REST controller.
 *
 * @see QuestionAnswerPlayerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterAppliApp.class)
public class QuestionAnswerPlayerResourceIntTest {

    @Inject
    private QuestionAnswerPlayerRepository questionAnswerPlayerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionAnswerPlayerMockMvc;

    private QuestionAnswerPlayer questionAnswerPlayer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionAnswerPlayerResource questionAnswerPlayerResource = new QuestionAnswerPlayerResource();
        ReflectionTestUtils.setField(questionAnswerPlayerResource, "questionAnswerPlayerRepository", questionAnswerPlayerRepository);
        this.restQuestionAnswerPlayerMockMvc = MockMvcBuilders.standaloneSetup(questionAnswerPlayerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionAnswerPlayer createEntity(EntityManager em) {
        QuestionAnswerPlayer questionAnswerPlayer = new QuestionAnswerPlayer();
        return questionAnswerPlayer;
    }

    @Before
    public void initTest() {
        questionAnswerPlayer = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionAnswerPlayer() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerPlayerRepository.findAll().size();

        // Create the QuestionAnswerPlayer

        restQuestionAnswerPlayerMockMvc.perform(post("/api/question-answer-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswerPlayer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswerPlayer in the database
        List<QuestionAnswerPlayer> questionAnswerPlayerList = questionAnswerPlayerRepository.findAll();
        assertThat(questionAnswerPlayerList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionAnswerPlayer testQuestionAnswerPlayer = questionAnswerPlayerList.get(questionAnswerPlayerList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuestionAnswerPlayerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerPlayerRepository.findAll().size();

        // Create the QuestionAnswerPlayer with an existing ID
        QuestionAnswerPlayer existingQuestionAnswerPlayer = new QuestionAnswerPlayer();
        existingQuestionAnswerPlayer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionAnswerPlayerMockMvc.perform(post("/api/question-answer-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionAnswerPlayer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionAnswerPlayer> questionAnswerPlayerList = questionAnswerPlayerRepository.findAll();
        assertThat(questionAnswerPlayerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionAnswerPlayers() throws Exception {
        // Initialize the database
        questionAnswerPlayerRepository.saveAndFlush(questionAnswerPlayer);

        // Get all the questionAnswerPlayerList
        restQuestionAnswerPlayerMockMvc.perform(get("/api/question-answer-players?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAnswerPlayer.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQuestionAnswerPlayer() throws Exception {
        // Initialize the database
        questionAnswerPlayerRepository.saveAndFlush(questionAnswerPlayer);

        // Get the questionAnswerPlayer
        restQuestionAnswerPlayerMockMvc.perform(get("/api/question-answer-players/{id}", questionAnswerPlayer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionAnswerPlayer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionAnswerPlayer() throws Exception {
        // Get the questionAnswerPlayer
        restQuestionAnswerPlayerMockMvc.perform(get("/api/question-answer-players/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionAnswerPlayer() throws Exception {
        // Initialize the database
        questionAnswerPlayerRepository.saveAndFlush(questionAnswerPlayer);
        int databaseSizeBeforeUpdate = questionAnswerPlayerRepository.findAll().size();

        // Update the questionAnswerPlayer
        QuestionAnswerPlayer updatedQuestionAnswerPlayer = questionAnswerPlayerRepository.findOne(questionAnswerPlayer.getId());

        restQuestionAnswerPlayerMockMvc.perform(put("/api/question-answer-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionAnswerPlayer)))
            .andExpect(status().isOk());

        // Validate the QuestionAnswerPlayer in the database
        List<QuestionAnswerPlayer> questionAnswerPlayerList = questionAnswerPlayerRepository.findAll();
        assertThat(questionAnswerPlayerList).hasSize(databaseSizeBeforeUpdate);
        QuestionAnswerPlayer testQuestionAnswerPlayer = questionAnswerPlayerList.get(questionAnswerPlayerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionAnswerPlayer() throws Exception {
        int databaseSizeBeforeUpdate = questionAnswerPlayerRepository.findAll().size();

        // Create the QuestionAnswerPlayer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionAnswerPlayerMockMvc.perform(put("/api/question-answer-players")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswerPlayer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswerPlayer in the database
        List<QuestionAnswerPlayer> questionAnswerPlayerList = questionAnswerPlayerRepository.findAll();
        assertThat(questionAnswerPlayerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionAnswerPlayer() throws Exception {
        // Initialize the database
        questionAnswerPlayerRepository.saveAndFlush(questionAnswerPlayer);
        int databaseSizeBeforeDelete = questionAnswerPlayerRepository.findAll().size();

        // Get the questionAnswerPlayer
        restQuestionAnswerPlayerMockMvc.perform(delete("/api/question-answer-players/{id}", questionAnswerPlayer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionAnswerPlayer> questionAnswerPlayerList = questionAnswerPlayerRepository.findAll();
        assertThat(questionAnswerPlayerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
