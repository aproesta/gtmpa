package com.ibm.gtmpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.gtmpa.domain.Planmilestone;
import com.ibm.gtmpa.repository.PlanmilestoneRepository;
import com.ibm.gtmpa.web.rest.util.HeaderUtil;
import com.ibm.gtmpa.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Planmilestone.
 */
@RestController
@RequestMapping("/api")
public class PlanmilestoneResource {

    private final Logger log = LoggerFactory.getLogger(PlanmilestoneResource.class);

    @Inject
    private PlanmilestoneRepository planmilestoneRepository;

    /**
     * POST  /planmilestones -> Create a new planmilestone.
     */
    @RequestMapping(value = "/planmilestones",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Planmilestone> createPlanmilestone(@RequestBody Planmilestone planmilestone) throws URISyntaxException {
        log.debug("REST request to save Planmilestone : {}", planmilestone);
        if (planmilestone.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new planmilestone cannot already have an ID").body(null);
        }
        Planmilestone result = planmilestoneRepository.save(planmilestone);
        return ResponseEntity.created(new URI("/api/planmilestones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("planmilestone", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /planmilestones -> Updates an existing planmilestone.
     */
    @RequestMapping(value = "/planmilestones",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Planmilestone> updatePlanmilestone(@RequestBody Planmilestone planmilestone) throws URISyntaxException {
        log.debug("REST request to update Planmilestone : {}", planmilestone);
        if (planmilestone.getId() == null) {
            return createPlanmilestone(planmilestone);
        }
        Planmilestone result = planmilestoneRepository.save(planmilestone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("planmilestone", planmilestone.getId().toString()))
            .body(result);
    }

    /**
     * GET  /planmilestones -> get all the planmilestones.
     */
    @RequestMapping(value = "/planmilestones",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Planmilestone>> getAllPlanmilestones(Pageable pageable)
        throws URISyntaxException {
        Page<Planmilestone> page = planmilestoneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/planmilestones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /planmilestones/:id -> get the "id" planmilestone.
     */
    @RequestMapping(value = "/planmilestones/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Planmilestone> getPlanmilestone(@PathVariable Long id) {
        log.debug("REST request to get Planmilestone : {}", id);
        return Optional.ofNullable(planmilestoneRepository.findOne(id))
            .map(planmilestone -> new ResponseEntity<>(
                planmilestone,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /planmilestones/:id -> delete the "id" planmilestone.
     */
    @RequestMapping(value = "/planmilestones/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePlanmilestone(@PathVariable Long id) {
        log.debug("REST request to delete Planmilestone : {}", id);
        planmilestoneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("planmilestone", id.toString())).build();
    }
}
