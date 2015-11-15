package com.ibm.gtmpa.web.rest;

import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Rule;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
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

        restRuleMockMvc.perform(post("/api/rules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rule)))
                .andExpect(status().isCreated());

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

        restRuleMockMvc.perform(post("/api/rules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rule)))
                .andExpect(status().isBadRequest());

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

        restRuleMockMvc.perform(post("/api/rules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rule)))
                .andExpect(status().isBadRequest());

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

        restRuleMockMvc.perform(post("/api/rules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rule)))
                .andExpect(status().isBadRequest());

        List<Rule> rules = ruleRepository.findAll();
        assertThat(rules).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRules() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

        // Get all the rules
        restRuleMockMvc.perform(get("/api/rules"))
                .andExpect(status().isOk())
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
        restRuleMockMvc.perform(get("/api/rules/{id}", rule.getId()))
            .andExpect(status().isOk())
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
        restRuleMockMvc.perform(get("/api/rules/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
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

        restRuleMockMvc.perform(put("/api/rules")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(rule)))
                .andExpect(status().isOk());

        // Validate the Rule in the database
        List<Rule> rules = ruleRepository.findAll();
        assertThat(rules).hasSize(databaseSizeBeforeUpdate);
        Rule testRule = rules.get(rules.size() - 1);
        assertThat(testRule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRule.getFieldSpec()).isEqualTo(UPDATED_FIELD_SPEC);
        assertThat(testRule.getRule()).isEqualTo(UPDATED_RULE);
    }

    @Test
    @Transactional
    public void deleteRule() throws Exception {
        // Initialize the database
        ruleRepository.saveAndFlush(rule);

		int databaseSizeBeforeDelete = ruleRepository.findAll().size();

        // Get the rule
        restRuleMockMvc.perform(delete("/api/rules/{id}", rule.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Rule> rules = ruleRepository.findAll();
        assertThat(rules).hasSize(databaseSizeBeforeDelete - 1);
    }
}
