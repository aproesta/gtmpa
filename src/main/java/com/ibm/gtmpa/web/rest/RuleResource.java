package com.ibm.gtmpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.gtmpa.domain.Rule;
import com.ibm.gtmpa.repository.RuleRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Rule.
 */
@RestController
@RequestMapping("/api")
public class RuleResource {

    private final Logger log = LoggerFactory.getLogger(RuleResource.class);

    @Inject
    private RuleRepository ruleRepository;

    /**
     * POST  /rules -> Create a new rule.
     */
    @RequestMapping(value = "/rules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rule> createRule(@Valid @RequestBody Rule rule) throws URISyntaxException {
        log.debug("REST request to save Rule : {}", rule);
        if (rule.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new rule cannot already have an ID").body(null);
        }
        Rule result = ruleRepository.save(rule);
        return ResponseEntity.created(new URI("/api/rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("rule", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rules -> Updates an existing rule.
     */
    @RequestMapping(value = "/rules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rule> updateRule(@Valid @RequestBody Rule rule) throws URISyntaxException {
        log.debug("REST request to update Rule : {}", rule);
        if (rule.getId() == null) {
            return createRule(rule);
        }
        Rule result = ruleRepository.save(rule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("rule", rule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rules -> get all the rules.
     */
    @RequestMapping(value = "/rules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Rule>> getAllRules(Pageable pageable)
        throws URISyntaxException {
        Page<Rule> page = ruleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /rules/:id -> get the "id" rule.
     */
    @RequestMapping(value = "/rules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Rule> getRule(@PathVariable Long id) {
        log.debug("REST request to get Rule : {}", id);
        return Optional.ofNullable(ruleRepository.findOne(id))
            .map(rule -> new ResponseEntity<>(
                rule,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rules/:id -> delete the "id" rule.
     */
    @RequestMapping(value = "/rules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        log.debug("REST request to delete Rule : {}", id);
        ruleRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("rule", id.toString())).build();
    }
}
