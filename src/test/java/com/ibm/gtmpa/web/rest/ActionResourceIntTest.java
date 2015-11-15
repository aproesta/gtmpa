package com.ibm.gtmpa.web.rest;

import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Action;
import com.ibm.gtmpa.repository.ActionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ActionResource REST controller.
 *
 * @see ActionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class ActionResourceIntTest {


    private static final LocalDate DEFAULT_LOG_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LOG_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";
    private static final String DEFAULT_ACTION_ITEMS = "AAAAA";
    private static final String UPDATED_ACTION_ITEMS = "BBBBB";

    private static final LocalDate DEFAULT_NEXT_ACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_NEXT_ACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private ActionRepository actionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restActionMockMvc;

    private Action action;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ActionResource actionResource = new ActionResource();
        ReflectionTestUtils.setField(actionResource, "actionRepository", actionRepository);
        this.restActionMockMvc = MockMvcBuilders.standaloneSetup(actionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        action = new Action();
        action.setLogDate(DEFAULT_LOG_DATE);
        action.setDescription(DEFAULT_DESCRIPTION);
        action.setActionItems(DEFAULT_ACTION_ITEMS);
        action.setNextActionDate(DEFAULT_NEXT_ACTION_DATE);
    }

    @Test
    @Transactional
    public void createAction() throws Exception {
        int databaseSizeBeforeCreate = actionRepository.findAll().size();

        // Create the Action

        restActionMockMvc.perform(post("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isCreated());

        // Validate the Action in the database
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeCreate + 1);
        Action testAction = actions.get(actions.size() - 1);
        assertThat(testAction.getLogDate()).isEqualTo(DEFAULT_LOG_DATE);
        assertThat(testAction.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAction.getActionItems()).isEqualTo(DEFAULT_ACTION_ITEMS);
        assertThat(testAction.getNextActionDate()).isEqualTo(DEFAULT_NEXT_ACTION_DATE);
    }

    @Test
    @Transactional
    public void checkLogDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setLogDate(null);

        // Create the Action, which fails.

        restActionMockMvc.perform(post("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isBadRequest());

        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = actionRepository.findAll().size();
        // set the field null
        action.setDescription(null);

        // Create the Action, which fails.

        restActionMockMvc.perform(post("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isBadRequest());

        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllActions() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get all the actions
        restActionMockMvc.perform(get("/api/actions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(action.getId().intValue())))
                .andExpect(jsonPath("$.[*].logDate").value(hasItem(DEFAULT_LOG_DATE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].actionItems").value(hasItem(DEFAULT_ACTION_ITEMS.toString())))
                .andExpect(jsonPath("$.[*].nextActionDate").value(hasItem(DEFAULT_NEXT_ACTION_DATE.toString())));
    }

    @Test
    @Transactional
    public void getAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", action.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(action.getId().intValue()))
            .andExpect(jsonPath("$.logDate").value(DEFAULT_LOG_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.actionItems").value(DEFAULT_ACTION_ITEMS.toString()))
            .andExpect(jsonPath("$.nextActionDate").value(DEFAULT_NEXT_ACTION_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAction() throws Exception {
        // Get the action
        restActionMockMvc.perform(get("/api/actions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

		int databaseSizeBeforeUpdate = actionRepository.findAll().size();

        // Update the action
        action.setLogDate(UPDATED_LOG_DATE);
        action.setDescription(UPDATED_DESCRIPTION);
        action.setActionItems(UPDATED_ACTION_ITEMS);
        action.setNextActionDate(UPDATED_NEXT_ACTION_DATE);

        restActionMockMvc.perform(put("/api/actions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(action)))
                .andExpect(status().isOk());

        // Validate the Action in the database
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeUpdate);
        Action testAction = actions.get(actions.size() - 1);
        assertThat(testAction.getLogDate()).isEqualTo(UPDATED_LOG_DATE);
        assertThat(testAction.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAction.getActionItems()).isEqualTo(UPDATED_ACTION_ITEMS);
        assertThat(testAction.getNextActionDate()).isEqualTo(UPDATED_NEXT_ACTION_DATE);
    }

    @Test
    @Transactional
    public void deleteAction() throws Exception {
        // Initialize the database
        actionRepository.saveAndFlush(action);

		int databaseSizeBeforeDelete = actionRepository.findAll().size();

        // Get the action
        restActionMockMvc.perform(delete("/api/actions/{id}", action.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Action> actions = actionRepository.findAll();
        assertThat(actions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
