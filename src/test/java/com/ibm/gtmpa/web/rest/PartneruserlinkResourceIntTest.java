package com.ibm.gtmpa.web.rest;

import com.ibm.gtmpa.Application;
import com.ibm.gtmpa.domain.Partneruserlink;
import com.ibm.gtmpa.repository.PartneruserlinkRepository;

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
 * Test class for the PartneruserlinkResource REST controller.
 *
 * @see PartneruserlinkResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PartneruserlinkResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private PartneruserlinkRepository partneruserlinkRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPartneruserlinkMockMvc;

    private Partneruserlink partneruserlink;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PartneruserlinkResource partneruserlinkResource = new PartneruserlinkResource();
        ReflectionTestUtils.setField(partneruserlinkResource, "partneruserlinkRepository", partneruserlinkRepository);
        this.restPartneruserlinkMockMvc = MockMvcBuilders.standaloneSetup(partneruserlinkResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        partneruserlink = new Partneruserlink();
        partneruserlink.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPartneruserlink() throws Exception {
        int databaseSizeBeforeCreate = partneruserlinkRepository.findAll().size();

        // Create the Partneruserlink

        restPartneruserlinkMockMvc.perform(post("/api/partneruserlinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partneruserlink)))
                .andExpect(status().isCreated());

        // Validate the Partneruserlink in the database
        List<Partneruserlink> partneruserlinks = partneruserlinkRepository.findAll();
        assertThat(partneruserlinks).hasSize(databaseSizeBeforeCreate + 1);
        Partneruserlink testPartneruserlink = partneruserlinks.get(partneruserlinks.size() - 1);
        assertThat(testPartneruserlink.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPartneruserlinks() throws Exception {
        // Initialize the database
        partneruserlinkRepository.saveAndFlush(partneruserlink);

        // Get all the partneruserlinks
        restPartneruserlinkMockMvc.perform(get("/api/partneruserlinks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(partneruserlink.getId().intValue())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPartneruserlink() throws Exception {
        // Initialize the database
        partneruserlinkRepository.saveAndFlush(partneruserlink);

        // Get the partneruserlink
        restPartneruserlinkMockMvc.perform(get("/api/partneruserlinks/{id}", partneruserlink.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(partneruserlink.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPartneruserlink() throws Exception {
        // Get the partneruserlink
        restPartneruserlinkMockMvc.perform(get("/api/partneruserlinks/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartneruserlink() throws Exception {
        // Initialize the database
        partneruserlinkRepository.saveAndFlush(partneruserlink);

		int databaseSizeBeforeUpdate = partneruserlinkRepository.findAll().size();

        // Update the partneruserlink
        partneruserlink.setDescription(UPDATED_DESCRIPTION);

        restPartneruserlinkMockMvc.perform(put("/api/partneruserlinks")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(partneruserlink)))
                .andExpect(status().isOk());

        // Validate the Partneruserlink in the database
        List<Partneruserlink> partneruserlinks = partneruserlinkRepository.findAll();
        assertThat(partneruserlinks).hasSize(databaseSizeBeforeUpdate);
        Partneruserlink testPartneruserlink = partneruserlinks.get(partneruserlinks.size() - 1);
        assertThat(testPartneruserlink.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePartneruserlink() throws Exception {
        // Initialize the database
        partneruserlinkRepository.saveAndFlush(partneruserlink);

		int databaseSizeBeforeDelete = partneruserlinkRepository.findAll().size();

        // Get the partneruserlink
        restPartneruserlinkMockMvc.perform(delete("/api/partneruserlinks/{id}", partneruserlink.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Partneruserlink> partneruserlinks = partneruserlinkRepository.findAll();
        assertThat(partneruserlinks).hasSize(databaseSizeBeforeDelete - 1);
    }
}
