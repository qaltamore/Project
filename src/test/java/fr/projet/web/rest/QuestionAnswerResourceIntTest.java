package fr.projet.web.rest;

import fr.projet.JHipsterAppliApp;

import fr.projet.domain.QuestionAnswer;
import fr.projet.repository.QuestionAnswerRepository;

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
 * Test class for the QuestionAnswerResource REST controller.
 *
 * @see QuestionAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JHipsterAppliApp.class)
public class QuestionAnswerResourceIntTest {

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEVEL = 1;
    private static final Integer UPDATED_LEVEL = 2;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSITION_1 = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSITION_1 = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSITION_2 = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSITION_2 = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSITION_3 = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSITION_3 = "BBBBBBBBBB";

    private static final String DEFAULT_PROPOSITION_4 = "AAAAAAAAAA";
    private static final String UPDATED_PROPOSITION_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Inject
    private QuestionAnswerRepository questionAnswerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restQuestionAnswerMockMvc;

    private QuestionAnswer questionAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionAnswerResource questionAnswerResource = new QuestionAnswerResource();
        ReflectionTestUtils.setField(questionAnswerResource, "questionAnswerRepository", questionAnswerRepository);
        this.restQuestionAnswerMockMvc = MockMvcBuilders.standaloneSetup(questionAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionAnswer createEntity(EntityManager em) {
        QuestionAnswer questionAnswer = new QuestionAnswer()
                .titre(DEFAULT_TITRE)
                .level(DEFAULT_LEVEL)
                .question(DEFAULT_QUESTION)
                .proposition1(DEFAULT_PROPOSITION_1)
                .proposition2(DEFAULT_PROPOSITION_2)
                .proposition3(DEFAULT_PROPOSITION_3)
                .proposition4(DEFAULT_PROPOSITION_4)
                .answer(DEFAULT_ANSWER);
        return questionAnswer;
    }

    @Before
    public void initTest() {
        questionAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionAnswer() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer

        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionAnswer testQuestionAnswer = questionAnswerList.get(questionAnswerList.size() - 1);
        assertThat(testQuestionAnswer.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testQuestionAnswer.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testQuestionAnswer.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testQuestionAnswer.getProposition1()).isEqualTo(DEFAULT_PROPOSITION_1);
        assertThat(testQuestionAnswer.getProposition2()).isEqualTo(DEFAULT_PROPOSITION_2);
        assertThat(testQuestionAnswer.getProposition3()).isEqualTo(DEFAULT_PROPOSITION_3);
        assertThat(testQuestionAnswer.getProposition4()).isEqualTo(DEFAULT_PROPOSITION_4);
        assertThat(testQuestionAnswer.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createQuestionAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer with an existing ID
        QuestionAnswer existingQuestionAnswer = new QuestionAnswer();
        existingQuestionAnswer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionAnswerMockMvc.perform(post("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingQuestionAnswer)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionAnswers() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);

        // Get all the questionAnswerList
        restQuestionAnswerMockMvc.perform(get("/api/question-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE.toString())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION.toString())))
            .andExpect(jsonPath("$.[*].proposition1").value(hasItem(DEFAULT_PROPOSITION_1.toString())))
            .andExpect(jsonPath("$.[*].proposition2").value(hasItem(DEFAULT_PROPOSITION_2.toString())))
            .andExpect(jsonPath("$.[*].proposition3").value(hasItem(DEFAULT_PROPOSITION_3.toString())))
            .andExpect(jsonPath("$.[*].proposition4").value(hasItem(DEFAULT_PROPOSITION_4.toString())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void getQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);

        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(get("/api/question-answers/{id}", questionAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionAnswer.getId().intValue()))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE.toString()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION.toString()))
            .andExpect(jsonPath("$.proposition1").value(DEFAULT_PROPOSITION_1.toString()))
            .andExpect(jsonPath("$.proposition2").value(DEFAULT_PROPOSITION_2.toString()))
            .andExpect(jsonPath("$.proposition3").value(DEFAULT_PROPOSITION_3.toString()))
            .andExpect(jsonPath("$.proposition4").value(DEFAULT_PROPOSITION_4.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionAnswer() throws Exception {
        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(get("/api/question-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);
        int databaseSizeBeforeUpdate = questionAnswerRepository.findAll().size();

        // Update the questionAnswer
        QuestionAnswer updatedQuestionAnswer = questionAnswerRepository.findOne(questionAnswer.getId());
        updatedQuestionAnswer
                .titre(UPDATED_TITRE)
                .level(UPDATED_LEVEL)
                .question(UPDATED_QUESTION)
                .proposition1(UPDATED_PROPOSITION_1)
                .proposition2(UPDATED_PROPOSITION_2)
                .proposition3(UPDATED_PROPOSITION_3)
                .proposition4(UPDATED_PROPOSITION_4)
                .answer(UPDATED_ANSWER);

        restQuestionAnswerMockMvc.perform(put("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionAnswer)))
            .andExpect(status().isOk());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeUpdate);
        QuestionAnswer testQuestionAnswer = questionAnswerList.get(questionAnswerList.size() - 1);
        assertThat(testQuestionAnswer.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testQuestionAnswer.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testQuestionAnswer.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testQuestionAnswer.getProposition1()).isEqualTo(UPDATED_PROPOSITION_1);
        assertThat(testQuestionAnswer.getProposition2()).isEqualTo(UPDATED_PROPOSITION_2);
        assertThat(testQuestionAnswer.getProposition3()).isEqualTo(UPDATED_PROPOSITION_3);
        assertThat(testQuestionAnswer.getProposition4()).isEqualTo(UPDATED_PROPOSITION_4);
        assertThat(testQuestionAnswer.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionAnswer() throws Exception {
        int databaseSizeBeforeUpdate = questionAnswerRepository.findAll().size();

        // Create the QuestionAnswer

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQuestionAnswerMockMvc.perform(put("/api/question-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionAnswer)))
            .andExpect(status().isCreated());

        // Validate the QuestionAnswer in the database
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQuestionAnswer() throws Exception {
        // Initialize the database
        questionAnswerRepository.saveAndFlush(questionAnswer);
        int databaseSizeBeforeDelete = questionAnswerRepository.findAll().size();

        // Get the questionAnswer
        restQuestionAnswerMockMvc.perform(delete("/api/question-answers/{id}", questionAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<QuestionAnswer> questionAnswerList = questionAnswerRepository.findAll();
        assertThat(questionAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
