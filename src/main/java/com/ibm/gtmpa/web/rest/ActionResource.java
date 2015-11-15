package com.ibm.gtmpa.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.ibm.gtmpa.domain.Action;
import com.ibm.gtmpa.repository.ActionRepository;
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
 * REST controller for managing Action.
 */
@RestController
@RequestMapping("/api")
public class ActionResource {

    private final Logger log = LoggerFactory.getLogger(ActionResource.class);

    @Inject
    private ActionRepository actionRepository;

    /**
     * POST  /actions -> Create a new action.
     */
    @RequestMapping(value = "/actions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Action> createAction(@Valid @RequestBody Action action) throws URISyntaxException {
        log.debug("REST request to save Action : {}", action);
        if (action.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new action cannot already have an ID").body(null);
        }
        Action result = actionRepository.save(action);
        return ResponseEntity.created(new URI("/api/actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("action", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actions -> Updates an existing action.
     */
    @RequestMapping(value = "/actions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Action> updateAction(@Valid @RequestBody Action action) throws URISyntaxException {
        log.debug("REST request to update Action : {}", action);
        if (action.getId() == null) {
            return createAction(action);
        }
        Action result = actionRepository.save(action);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("action", action.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actions -> get all the actions.
     */
    @RequestMapping(value = "/actions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Action>> getAllActions(Pageable pageable)
        throws URISyntaxException {
        Page<Action> page = actionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /actions/:id -> get the "id" action.
     */
    @RequestMapping(value = "/actions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Action> getAction(@PathVariable Long id) {
        log.debug("REST request to get Action : {}", id);
        return Optional.ofNullable(actionRepository.findOne(id))
            .map(action -> new ResponseEntity<>(
                action,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /actions/:id -> delete the "id" action.
     */
    @RequestMapping(value = "/actions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        log.debug("REST request to delete Action : {}", id);
        actionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("action", id.toString())).build();
    }
}
