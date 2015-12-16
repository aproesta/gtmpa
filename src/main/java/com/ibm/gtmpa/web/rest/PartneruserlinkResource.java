package com.ibm.gtmpa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.ibm.gtmpa.domain.Partneruserlink;
import com.ibm.gtmpa.repository.PartneruserlinkRepository;
import com.ibm.gtmpa.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Partneruserlink.
 */
@RestController
@RequestMapping("/api")
public class PartneruserlinkResource {

    private final Logger log = LoggerFactory.getLogger(PartneruserlinkResource.class);

    @Inject
    private PartneruserlinkRepository partneruserlinkRepository;

    /**
     * POST  /partneruserlinks -> Create a new partneruserlink.
     */
    @RequestMapping(value = "/partneruserlinks",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partneruserlink> createPartneruserlink(@RequestBody Partneruserlink partneruserlink) throws URISyntaxException {
        log.debug("REST request to save Partneruserlink : {}", partneruserlink);
        if (partneruserlink.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partneruserlink cannot already have an ID").body(null);
        }
        Partneruserlink result = partneruserlinkRepository.save(partneruserlink);
        return ResponseEntity.created(new URI("/api/partneruserlinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("partneruserlink", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /partneruserlinks -> Updates an existing partneruserlink.
     */
    @RequestMapping(value = "/partneruserlinks",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partneruserlink> updatePartneruserlink(@RequestBody Partneruserlink partneruserlink) throws URISyntaxException {
        log.debug("REST request to update Partneruserlink : {}", partneruserlink);
        if (partneruserlink.getId() == null) {
            return createPartneruserlink(partneruserlink);
        }
        Partneruserlink result = partneruserlinkRepository.save(partneruserlink);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("partneruserlink", partneruserlink.getId().toString()))
            .body(result);
    }

    /**
     * GET  /partneruserlinks -> get all the partneruserlinks.
     */
    @RequestMapping(value = "/partneruserlinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Partneruserlink> getAllPartneruserlinks() {
        log.debug("REST request to get all Partneruserlinks");
        return partneruserlinkRepository.findAll();
    }

    /**
     * GET  /partneruserlinks/:id -> get the "id" partneruserlink.
     */
    @RequestMapping(value = "/partneruserlinks/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Partneruserlink> getPartneruserlink(@PathVariable Long id) {
        log.debug("REST request to get Partneruserlink : {}", id);
        return Optional.ofNullable(partneruserlinkRepository.findOne(id))
            .map(partneruserlink -> new ResponseEntity<>(
                partneruserlink,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partneruserlinks/:id -> delete the "id" partneruserlink.
     */
    @RequestMapping(value = "/partneruserlinks/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePartneruserlink(@PathVariable Long id) {
        log.debug("REST request to delete Partneruserlink : {}", id);
        partneruserlinkRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("partneruserlink", id.toString())).build();
    }
    

    /**
     * GET  /partneruserlinks -> get all the partneruserlinks.
     */
    @RequestMapping(value = "/filterpartneruserlinks",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Partneruserlink> getAllPartnerUsers(@PathVariable Long id) {
        log.debug("REST request to get all PartnerUser");
        List<Partneruserlink> allLinks = partneruserlinkRepository.findAll();
        List<Partneruserlink> returnLinks = new ArrayList<Partneruserlink> ();
        for(Partneruserlink usrlink : allLinks){
        	
        	if(usrlink.getPartner().getId() == id){
        		returnLinks.add(usrlink);
        	}
        }
        return returnLinks;
    }
}
