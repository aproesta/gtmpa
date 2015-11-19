package com.ibm.gtmpa.web.rest;

import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.repository.PlanRepository;

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

import com.ibm.gtmpa.domain.enumeration.IndustrySegmentEnum;

/**
 * Test class for the PlanResource REST controller.
 *
 * @see PlanResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PlanResourceIntTest {

    private static final String DEFAULT_SOLUTION_NAME = "AAA";
    private static final String UPDATED_SOLUTION_NAME = "BBB";

    private static final LocalDate DEFAULT_AGREED_GTMDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AGREED_GTMDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_REVENUE_COMMITMENT = 1F;
    private static final Float UPDATED_REVENUE_COMMITMENT = 2F;

    private static final Integer DEFAULT_DEALS_REQUIRED = 1;
    private static final Integer UPDATED_DEALS_REQUIRED = 2;

    private static final LocalDate DEFAULT_PROPOSAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROPOSAL_DATE = LocalDate.now(ZoneId.systemDefault());


private static final IndustrySegmentEnum DEFAULT_INDUSTRY_SEGMENT = IndustrySegmentEnum.Banking;
    private static final IndustrySegmentEnum UPDATED_INDUSTRY_SEGMENT = IndustrySegmentEnum.Government;

    @Inject
    private PlanRepository planRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPlanMockMvc;

    private Plan plan;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlanResource planResource = new PlanResource();
        ReflectionTestUtils.setField(planResource, "planRepository", planRepository);
        this.restPlanMockMvc = MockMvcBuilders.standaloneSetup(planResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        plan = new Plan();
        plan.setSolutionName(DEFAULT_SOLUTION_NAME);
        plan.setAgreedGTMDate(DEFAULT_AGREED_GTMDATE);
        plan.setRevenueCommitment(DEFAULT_REVENUE_COMMITMENT);
        plan.setDealsRequired(DEFAULT_DEALS_REQUIRED);
        plan.setProposalDate(DEFAULT_PROPOSAL_DATE);
        plan.setIndustrySegment(DEFAULT_INDUSTRY_SEGMENT);
    }

    @Test
    @Transactional
    public void createPlan() throws Exception {
        int databaseSizeBeforeCreate = planRepository.findAll().size();

        // Create the Plan

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isCreated());

        // Validate the Plan in the database
        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeCreate + 1);
        Plan testPlan = plans.get(plans.size() - 1);
        assertThat(testPlan.getSolutionName()).isEqualTo(DEFAULT_SOLUTION_NAME);
        assertThat(testPlan.getAgreedGTMDate()).isEqualTo(DEFAULT_AGREED_GTMDATE);
        assertThat(testPlan.getRevenueCommitment()).isEqualTo(DEFAULT_REVENUE_COMMITMENT);
        assertThat(testPlan.getDealsRequired()).isEqualTo(DEFAULT_DEALS_REQUIRED);
        assertThat(testPlan.getProposalDate()).isEqualTo(DEFAULT_PROPOSAL_DATE);
        assertThat(testPlan.getIndustrySegment()).isEqualTo(DEFAULT_INDUSTRY_SEGMENT);
        assertThat(testPlan.getStatus()).isEqualTo(Plan.INITIAL_STATUS);
    }

    @Test
    @Transactional
    public void checkSolutionNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setSolutionName(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAgreedGTMDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setAgreedGTMDate(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRevenueCommitmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setRevenueCommitment(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDealsRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setDealsRequired(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProposalDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setProposalDate(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIndustrySegmentIsRequired() throws Exception {
        int databaseSizeBeforeTest = planRepository.findAll().size();
        // set the field null
        plan.setIndustrySegment(null);

        // Create the Plan, which fails.

        restPlanMockMvc.perform(post("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isBadRequest());

        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlans() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get all the plans
        restPlanMockMvc.perform(get("/api/plans"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(plan.getId().intValue())))
                .andExpect(jsonPath("$.[*].solutionName").value(hasItem(DEFAULT_SOLUTION_NAME.toString())))
                .andExpect(jsonPath("$.[*].agreedGTMDate").value(hasItem(DEFAULT_AGREED_GTMDATE.toString())))
                .andExpect(jsonPath("$.[*].revenueCommitment").value(hasItem(DEFAULT_REVENUE_COMMITMENT.doubleValue())))
                .andExpect(jsonPath("$.[*].dealsRequired").value(hasItem(DEFAULT_DEALS_REQUIRED)))
                .andExpect(jsonPath("$.[*].proposalDate").value(hasItem(DEFAULT_PROPOSAL_DATE.toString())))
                .andExpect(jsonPath("$.[*].industrySegment").value(hasItem(DEFAULT_INDUSTRY_SEGMENT.toString())));
    }

    @Test
    @Transactional
    public void getPlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

        // Get the plan
        restPlanMockMvc.perform(get("/api/plans/{id}", plan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(plan.getId().intValue()))
            .andExpect(jsonPath("$.solutionName").value(DEFAULT_SOLUTION_NAME.toString()))
            .andExpect(jsonPath("$.agreedGTMDate").value(DEFAULT_AGREED_GTMDATE.toString()))
            .andExpect(jsonPath("$.revenueCommitment").value(DEFAULT_REVENUE_COMMITMENT.doubleValue()))
            .andExpect(jsonPath("$.dealsRequired").value(DEFAULT_DEALS_REQUIRED))
            .andExpect(jsonPath("$.proposalDate").value(DEFAULT_PROPOSAL_DATE.toString()))
            .andExpect(jsonPath("$.industrySegment").value(DEFAULT_INDUSTRY_SEGMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPlan() throws Exception {
        // Get the plan
        restPlanMockMvc.perform(get("/api/plans/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

		int databaseSizeBeforeUpdate = planRepository.findAll().size();

        // Update the plan
        plan.setSolutionName(UPDATED_SOLUTION_NAME);
        plan.setAgreedGTMDate(UPDATED_AGREED_GTMDATE);
        plan.setRevenueCommitment(UPDATED_REVENUE_COMMITMENT);
        plan.setDealsRequired(UPDATED_DEALS_REQUIRED);
        plan.setProposalDate(UPDATED_PROPOSAL_DATE);
        plan.setIndustrySegment(UPDATED_INDUSTRY_SEGMENT);

        restPlanMockMvc.perform(put("/api/plans")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(plan)))
                .andExpect(status().isOk());

        // Validate the Plan in the database
        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeUpdate);
        Plan testPlan = plans.get(plans.size() - 1);
        assertThat(testPlan.getSolutionName()).isEqualTo(UPDATED_SOLUTION_NAME);
        assertThat(testPlan.getAgreedGTMDate()).isEqualTo(UPDATED_AGREED_GTMDATE);
        assertThat(testPlan.getRevenueCommitment()).isEqualTo(UPDATED_REVENUE_COMMITMENT);
        assertThat(testPlan.getDealsRequired()).isEqualTo(UPDATED_DEALS_REQUIRED);
        assertThat(testPlan.getProposalDate()).isEqualTo(UPDATED_PROPOSAL_DATE);
        assertThat(testPlan.getIndustrySegment()).isEqualTo(UPDATED_INDUSTRY_SEGMENT);
    }

    @Test
    @Transactional
    public void deletePlan() throws Exception {
        // Initialize the database
        planRepository.saveAndFlush(plan);

		int databaseSizeBeforeDelete = planRepository.findAll().size();

        // Get the plan
        restPlanMockMvc.perform(delete("/api/plans/{id}", plan.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Plan> plans = planRepository.findAll();
        assertThat(plans).hasSize(databaseSizeBeforeDelete - 1);
    }
}
