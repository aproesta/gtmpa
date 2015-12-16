package com.ibm.gtmpa.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.domain.PlanMilestoneManager;
import com.ibm.gtmpa.domain.Rule;
import com.ibm.gtmpa.domain.util.JSR310DateConverters;
import com.ibm.gtmpa.repository.RuleRepository;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RuleResource REST controller.
 *
 * @see RuleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class RuleResourceIntTest {

	private static final String DEFAULT_NAME = "AAAAA";
	private static final String UPDATED_NAME = "BBBBB";
	private static final String DEFAULT_FIELD_SPEC = "AAAAA";
	private static final String UPDATED_FIELD_SPEC = "BBBBB";
	private static final String DEFAULT_RULE = "AAAAA";
	private static final String UPDATED_RULE = "BBBBB";

	@Inject
	private RuleRepository ruleRepository;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restRuleMockMvc;

	private Rule rule;

	@PostConstruct
	public void setup() {
		MockitoAnnotations.initMocks(this);
		RuleResource ruleResource = new RuleResource();
		ReflectionTestUtils.setField(ruleResource, "ruleRepository", ruleRepository);
		this.restRuleMockMvc = MockMvcBuilders.standaloneSetup(ruleResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	@Before
	public void initTest() {
		rule = new Rule();
		rule.setName(DEFAULT_NAME);
		rule.setFieldSpec(DEFAULT_FIELD_SPEC);
		rule.setRule(DEFAULT_RULE);
	}

	@Test
	@Transactional
	public void createRule() throws Exception {
		int databaseSizeBeforeCreate = ruleRepository.findAll().size();

		// Create the Rule

		restRuleMockMvc.perform(post("/api/rules").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(rule))).andExpect(status().isCreated());

		// Validate the Rule in the database
		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeCreate + 1);
		Rule testRule = rules.get(rules.size() - 1);
		assertThat(testRule.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testRule.getFieldSpec()).isEqualTo(DEFAULT_FIELD_SPEC);
		assertThat(testRule.getRule()).isEqualTo(DEFAULT_RULE);
	}

	@Test
	@Transactional
	public void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = ruleRepository.findAll().size();
		// set the field null
		rule.setName(null);

		// Create the Rule, which fails.

		restRuleMockMvc.perform(post("/api/rules").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(rule))).andExpect(status().isBadRequest());

		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkFieldSpecIsRequired() throws Exception {
		int databaseSizeBeforeTest = ruleRepository.findAll().size();
		// set the field null
		rule.setFieldSpec(null);

		// Create the Rule, which fails.

		restRuleMockMvc.perform(post("/api/rules").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(rule))).andExpect(status().isBadRequest());

		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void checkRuleIsRequired() throws Exception {
		int databaseSizeBeforeTest = ruleRepository.findAll().size();
		// set the field null
		rule.setRule(null);

		// Create the Rule, which fails.

		restRuleMockMvc.perform(post("/api/rules").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(rule))).andExpect(status().isBadRequest());

		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeTest);
	}

	@Test
	@Transactional
	public void getAllRules() throws Exception {
		// Initialize the database
		ruleRepository.saveAndFlush(rule);

		// Get all the rules
		restRuleMockMvc.perform(get("/api/rules")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.[*].id").value(hasItem(rule.getId().intValue())))
				.andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
				.andExpect(jsonPath("$.[*].fieldSpec").value(hasItem(DEFAULT_FIELD_SPEC.toString())))
				.andExpect(jsonPath("$.[*].rule").value(hasItem(DEFAULT_RULE.toString())));
	}

	@Test
	@Transactional
	public void getRule() throws Exception {
		// Initialize the database
		ruleRepository.saveAndFlush(rule);

		// Get the rule
		restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(rule.getId().intValue()))
				.andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
				.andExpect(jsonPath("$.fieldSpec").value(DEFAULT_FIELD_SPEC.toString()))
				.andExpect(jsonPath("$.rule").value(DEFAULT_RULE.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingRule() throws Exception {
		// Get the rule
		restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateRule() throws Exception {
		// Initialize the database
		ruleRepository.saveAndFlush(rule);

		int databaseSizeBeforeUpdate = ruleRepository.findAll().size();

		// Update the rule
		rule.setName(UPDATED_NAME);
		rule.setFieldSpec(UPDATED_FIELD_SPEC);
		rule.setRule(UPDATED_RULE);

		restRuleMockMvc.perform(put("/api/rules").contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(rule))).andExpect(status().isOk());

		// Validate the Rule in the database
		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeUpdate);
		Rule testRule = rules.get(rules.size() - 1);
		assertThat(testRule.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testRule.getFieldSpec()).isEqualTo(UPDATED_FIELD_SPEC);
		assertThat(testRule.getRule()).isEqualTo(UPDATED_RULE);
	}

	@Test
	public void testBusinessDayAdjustment() throws Exception {
		// Test Business Day Adjustments
		LocalDate aSat = LocalDate.of(2015,11,21);
		LocalDate aSun = LocalDate.of(2015, 11, 22);
		LocalDate nextMonday = LocalDate.of(2015, 11, 23);

		LocalDate theMonday = PlanMilestoneManager.nextBusinessDay(aSat);
		assertThat(nextMonday).isEqualTo(theMonday);

		theMonday = PlanMilestoneManager.nextBusinessDay(aSun);
		assertThat(nextMonday).isEqualTo(theMonday);

	}

	@Test
	@Transactional
	public void testMilestoneManager() throws Exception {
		// Initialize the database
		ruleRepository.saveAndFlush(rule);

		MvcResult result = restRuleMockMvc.perform(get("/api/rules")).andReturn();
		String rulesStr = result.getResponse().getContentAsString();

		ObjectMapper mapper = new ObjectMapper();
		List<Rule> rules = mapper.readValue(rulesStr, new TypeReference<List<Rule>>() {
		});

		// test counting e2e days
		PlanMilestoneManager mpp = new PlanMilestoneManager(rules);
		int totalDays = mpp.getEndToEndDayCount();
		assertThat(totalDays).isEqualTo(110);

		// search by name
		Rule complete = mpp.getRuleByName(Plan.END_STATUS);
		assertThat(complete.getName()).isEqualTo(Plan.END_STATUS);

		// search by id
		Rule completeID = mpp.getRuleByID(complete.getId());
		assertThat(completeID.getName()).isEqualTo(complete.getName());
		
		// check next state
		Rule initialDiscussionRule = mpp.getNextState(Plan.START_STATUS);
		assertThat(initialDiscussionRule.getName()).isEqualTo("Initial Discussion");


		// Setting a Plan date field
		Plan plan = new Plan();
		LocalDate todaysDate = JSR310DateConverters.DateToLocalDateConverter.INSTANCE.convert(new Date());
		boolean success = mpp.setDate(plan, initialDiscussionRule, todaysDate);
		assertThat(success).isTrue();
		assertThat(plan.getInitialDiscussionDate()).isEqualTo(todaysDate);

		// test that the agreedGTMDate allows all the other milestone dates to
		// be acheived
		boolean isValid = mpp.isAgreedGTMDateValid(todaysDate);
		assertThat(isValid).isFalse();

		isValid = mpp.isAgreedGTMDateValid(todaysDate.plusDays(totalDays));
		assertThat(isValid).isTrue();

	}
	
	List<Rule> getSimpleRules() {
		List<Rule> simpleRules = new ArrayList<Rule>();
		Rule firstRule = new Rule();
		firstRule.setId(1L);
		firstRule.setName(Plan.START_STATUS);
		firstRule.setRule("10");
		firstRule.setForwardState("2");
		firstRule.setBackState("5");

		Rule secondRule = new Rule();
		secondRule.setId(2L);
		secondRule.setName("Initial Discussion");
		secondRule.setFieldSpec("initialDiscussionDate");
		secondRule.setRule("10");
		secondRule.setForwardState("3");
		secondRule.setBackState("1");

		Rule thirdRule = new Rule();
		thirdRule.setId(3L);
		thirdRule.setName("Sales Competency");
		thirdRule.setFieldSpec("salesCompetencyDate");
		thirdRule.setRule("10");
		thirdRule.setForwardState("4");
		thirdRule.setBackState("2");

		Rule lastRule = new Rule();
		lastRule.setId(4L);
		lastRule.setName(Plan.END_STATUS);
		lastRule.setRule("0");
		lastRule.setBackState("3");
		lastRule.setFieldSpec("completeDate");

		Rule defRule = new Rule();
		defRule.setName(Plan.INVALID_STATUS);
		defRule.setId(5L);
		defRule.setForwardState("1");
		defRule.setBackState("1");

		simpleRules.add(firstRule);
		simpleRules.add(secondRule);
		simpleRules.add(thirdRule);
		simpleRules.add(lastRule);
		simpleRules.add(defRule);
		
		return simpleRules;
	}

	@Test
	@Transactional
	public void testValidStates() throws Exception {
		List<Rule> simpleRules = getSimpleRules();

		PlanMilestoneManager pmm = new PlanMilestoneManager(simpleRules);

		List<Rule> validStates = pmm.getValidStates(Plan.START_STATUS);
		String nextState = "Initial Discussion";
		String subsequentState = "Sales Competency";

		// should contain the next state
		assertThat(validStates).contains(pmm.getRuleByName(nextState));

		// should contain the current state
		assertThat(validStates).contains(pmm.getRuleByName(Plan.START_STATUS));

		// should contain invalid state
		assertThat(validStates).contains(pmm.getRuleByName(Plan.INVALID_STATUS));

		// there should be no other states
		assertThat(validStates).hasSize(3);

		// should be able to go forward and backward and stay on the current state
		validStates = pmm.getValidStates(nextState);
		assertThat(validStates).contains(pmm.getRuleByName(Plan.START_STATUS));
		assertThat(validStates).contains(pmm.getRuleByName(nextState));
		assertThat(validStates).contains(pmm.getRuleByName(subsequentState));

		// there should be no other states
		assertThat(validStates).hasSize(4);

		
	}
	
	
	@Test
	@Transactional
	public void testInitialisingPlan() throws Exception {

		// Set a target date and initialise the milestones
		// Start with a simple set of rules first
		List<Rule> simpleRules = getSimpleRules();

		LocalDate todaysDate = LocalDate.now(ZoneId.systemDefault());

		PlanMilestoneManager simplePMM = new PlanMilestoneManager(simpleRules);
		int simpleE2Edays = simplePMM.getEndToEndDayCount();
		assertThat(simpleE2Edays).isEqualTo(30);

		LocalDate agDate = todaysDate.plusDays(simpleE2Edays);
		Plan simplePlan = new Plan();
		simplePlan.setAgreedGTMDate(agDate);
		assertThat(simplePMM.isAgreedGTMDateValid(agDate)).isTrue();
		
		// What do we expect Next??
		simplePMM.initialisePlanDates(simplePlan);

		// The Plan.initialDiscussionDate should be set to agDate - 20 days
		// Need to check for businessDay adjustments too
		LocalDate targetInitialDiscussionDate = PlanMilestoneManager.nextBusinessDay(agDate.minusDays(20));
		assertThat(simplePlan.getInitialDiscussionDate()).isEqualTo(targetInitialDiscussionDate);

		// The Plan.salesCompetencyDate should be set to agDate - 10 days
		LocalDate salesCompetencyDate = PlanMilestoneManager.nextBusinessDay(agDate.minusDays(10));
		assertThat(simplePlan.getSalesCompetencyDate()).isEqualTo(salesCompetencyDate);

	}

	@Test
	@Transactional
	public void deleteRule() throws Exception {
		// Initialize the database
		ruleRepository.saveAndFlush(rule);

		int databaseSizeBeforeDelete = ruleRepository.findAll().size();

		// Get the rule
		restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<Rule> rules = ruleRepository.findAll();
		assertThat(rules).hasSize(databaseSizeBeforeDelete - 1);
	}
}
