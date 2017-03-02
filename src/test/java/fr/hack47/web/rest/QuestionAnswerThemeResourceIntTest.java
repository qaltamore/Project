package fr.hack47.web.rest;

import fr.hack47.Hack47App;

import fr.hack47.domain.QuestionAnswerTheme;
import fr.hack47.repository.QuestionAnswerThemeRepository;

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
 * Test class for the QuestionAnswerThemeResource REST controller.
 *
 * @see QuestionAnswerThemeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Hack47App.class)
public class QuestionAnswerThemeResourceIntTest {

    @Inject
    private QuestionAnswerThemeRepository questionAnswerThemeRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionAnswerThemeMockMvc;

    private QuestionAnswerTheme questionAnswerTheme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionAnswerThemeResource questionAnswerThemeResource = new QuestionAnswerThemeResource();
        ReflectionTestUtils.setField(questionAnswerThemeResource, "questionAnswerThemeRepository", questionAnswerThemeRepository);
        this.restQuestionAnswerThemeMockMvc = MockMvcBuilders.standaloneSetup(questionAnswerThemeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionAnswerTheme createEntity(EntityManager em) {
        QuestionAnswerTheme questionAnswerTheme = new QuestionAnswerTheme();
        return questionAnswerTheme;
    }

    @Before
    public void initTest() {
        questionAnswerTheme = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionAnswerTheme() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerThemeRepository.findAll().size();

        // Create the QuestionAnswerTheme

        restQuestionAnswerThemeMockMvc.perform(post("/api/question-answer-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswerTheme)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswerTheme in the database
        List<QuestionAnswerTheme> questionAnswerThemeList = questionAnswerThemeRepository.findAll();
        assertThat(questionAnswerThemeList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionAnswerTheme testQuestionAnswerTheme = questionAnswerThemeList.get(questionAnswerThemeList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuestionAnswerThemeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerThemeRepository.findAll().size();

        // Create the QuestionAnswerTheme with an existing ID
        QuestionAnswerTheme existingQuestionAnswerTheme = new QuestionAnswerTheme();
        existingQuestionAnswerTheme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionAnswerThemeMockMvc.perform(post("/api/question-answer-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionAnswerTheme)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionAnswerTheme> questionAnswerThemeList = questionAnswerThemeRepository.findAll();
        assertThat(questionAnswerThemeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionAnswerThemes() throws Exception {
        // Initialize the database
        questionAnswerThemeRepository.saveAndFlush(questionAnswerTheme);

        // Get all the questionAnswerThemeList
        restQuestionAnswerThemeMockMvc.perform(get("/api/question-answer-themes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAnswerTheme.getId().intValue())));
    }

    @Test
    @Transactional
    public void getQuestionAnswerTheme() throws Exception {
        // Initialize the database
        questionAnswerThemeRepository.saveAndFlush(questionAnswerTheme);

        // Get the questionAnswerTheme
        restQuestionAnswerThemeMockMvc.perform(get("/api/question-answer-themes/{id}", questionAnswerTheme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionAnswerTheme.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionAnswerTheme() throws Exception {
        // Get the questionAnswerTheme
        restQuestionAnswerThemeMockMvc.perform(get("/api/question-answer-themes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionAnswerTheme() throws Exception {
        // Initialize the database
        questionAnswerThemeRepository.saveAndFlush(questionAnswerTheme);
        int databaseSizeBeforeUpdate = questionAnswerThemeRepository.findAll().size();

        // Update the questionAnswerTheme
        QuestionAnswerTheme updatedQuestionAnswerTheme = questionAnswerThemeRepository.findOne(questionAnswerTheme.getId());

        restQuestionAnswerThemeMockMvc.perform(put("/api/question-answer-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionAnswerTheme)))
            .andExpect(status().isOk());

        // Validate the QuestionAnswerTheme in the database
        List<QuestionAnswerTheme> questionAnswerThemeList = questionAnswerThemeRepository.findAll();
        assertThat(questionAnswerThemeList).hasSize(databaseSizeBeforeUpdate);
        QuestionAnswerTheme testQuestionAnswerTheme = questionAnswerThemeList.get(questionAnswerThemeList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionAnswerTheme() throws Exception {
        int databaseSizeBeforeUpdate = questionAnswerThemeRepository.findAll().size();

        // Create the QuestionAnswerTheme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionAnswerThemeMockMvc.perform(put("/api/question-answer-themes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswerTheme)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswerTheme in the database
        List<QuestionAnswerTheme> questionAnswerThemeList = questionAnswerThemeRepository.findAll();
        assertThat(questionAnswerThemeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionAnswerTheme() throws Exception {
        // Initialize the database
        questionAnswerThemeRepository.saveAndFlush(questionAnswerTheme);
        int databaseSizeBeforeDelete = questionAnswerThemeRepository.findAll().size();

        // Get the questionAnswerTheme
        restQuestionAnswerThemeMockMvc.perform(delete("/api/question-answer-themes/{id}", questionAnswerTheme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionAnswerTheme> questionAnswerThemeList = questionAnswerThemeRepository.findAll();
        assertThat(questionAnswerThemeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
