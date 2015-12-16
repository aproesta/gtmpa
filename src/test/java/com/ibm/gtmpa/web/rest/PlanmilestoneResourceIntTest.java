package com.ibm.gtmpa.web.rest;

import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Planmilestone;
import com.ibm.gtmpa.repository.PlanmilestoneRepository;

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
 * Test class for the PlanmilestoneResource REST controller.
 *
 * @see PlanmilestoneResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlanmilestoneResourceIntTest {


    private static final LocalDate DEFAULT_MILESTONE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MILESTONE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private PlanmilestoneRepository planmilestoneRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlanmilestoneMockMvc;

    private Planmilestone planmilestone;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanmilestoneResource planmilestoneResource = new PlanmilestoneResource();
        ReflectionTestUtils.setField(planmilestoneResource, "planmilestoneRepository", planmilestoneRepository);
        this.restPlanmilestoneMockMvc = MockMvcBuilders.standaloneSetup(planmilestoneResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        planmilestone = new Planmilestone();
        planmilestone.setMilestoneDate(DEFAULT_MILESTONE_DATE);
    }

    @Test
    @Transactional
    public void createPlanmilestone() throws Exception {
        int databaseSizeBeforeCreate = planmilestoneRepository.findAll().size();

        // Create the Planmilestone

        restPlanmilestoneMockMvc.perform(post("/api/planmilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(planmilestone)))
                .andExpect(status().isCreated());

        // Validate the Planmilestone in the database
        List<Planmilestone> planmilestones = planmilestoneRepository.findAll();
        assertThat(planmilestones).hasSize(databaseSizeBeforeCreate + 1);
        Planmilestone testPlanmilestone = planmilestones.get(planmilestones.size() - 1);
        assertThat(testPlanmilestone.getMilestoneDate()).isEqualTo(DEFAULT_MILESTONE_DATE);
    }

    @Test
    @Transactional
    public void getAllPlanmilestones() throws Exception {
        // Initialize the database
        planmilestoneRepository.saveAndFlush(planmilestone);

        // Get all the planmilestones
        restPlanmilestoneMockMvc.perform(get("/api/planmilestones"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(planmilestone.getId().intValue())))
                .andExpect(jsonPath("$.[*].milestoneDate").value(hasItem(DEFAULT_MILESTONE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getPlanmilestone() throws Exception {
        // Initialize the database
        planmilestoneRepository.saveAndFlush(planmilestone);

        // Get the planmilestone
        restPlanmilestoneMockMvc.perform(get("/api/planmilestones/{id}", planmilestone.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(planmilestone.getId().intValue()))
            .andExpect(jsonPath("$.milestoneDate").value(DEFAULT_MILESTONE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlanmilestone() throws Exception {
        // Get the planmilestone
        restPlanmilestoneMockMvc.perform(get("/api/planmilestones/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlanmilestone() throws Exception {
        // Initialize the database
        planmilestoneRepository.saveAndFlush(planmilestone);

		int databaseSizeBeforeUpdate = planmilestoneRepository.findAll().size();

        // Update the planmilestone
        planmilestone.setMilestoneDate(UPDATED_MILESTONE_DATE);

        restPlanmilestoneMockMvc.perform(put("/api/planmilestones")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(planmilestone)))
                .andExpect(status().isOk());

        // Validate the Planmilestone in the database
        List<Planmilestone> planmilestones = planmilestoneRepository.findAll();
        assertThat(planmilestones).hasSize(databaseSizeBeforeUpdate);
        Planmilestone testPlanmilestone = planmilestones.get(planmilestones.size() - 1);
        assertThat(testPlanmilestone.getMilestoneDate()).isEqualTo(UPDATED_MILESTONE_DATE);
    }

    @Test
    @Transactional
    public void deletePlanmilestone() throws Exception {
        // Initialize the database
        planmilestoneRepository.saveAndFlush(planmilestone);

		int databaseSizeBeforeDelete = planmilestoneRepository.findAll().size();

        // Get the planmilestone
        restPlanmilestoneMockMvc.perform(delete("/api/planmilestones/{id}", planmilestone.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Planmilestone> planmilestones = planmilestoneRepository.findAll();
        assertThat(planmilestones).hasSize(databaseSizeBeforeDelete - 1);
    }
}
