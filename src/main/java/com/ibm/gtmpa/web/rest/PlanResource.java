package com.ibm.gtmpa.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.domain.PlanMilestoneManager;
import com.ibm.gtmpa.domain.Rule;
import com.ibm.gtmpa.repository.PlanRepository;
import com.ibm.gtmpa.repository.RuleRepository;
import com.ibm.gtmpa.service.util.ObjectDiffer;
import com.ibm.gtmpa.web.rest.util.HeaderUtil;
import com.ibm.gtmpa.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Plan.
 */
@RestController
@RequestMapping("/api")
public class PlanResource {

	private final Logger log = LoggerFactory.getLogger(PlanResource.class);

	@Inject
	private PlanRepository planRepository;

	@Inject
	private RuleRepository ruleRepository;

	/**
	 * POST /plans -> Create a new plan.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> createPlan(@Valid @RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to save Plan : {}", plan);

		if (plan.getId() != null) {
			return ResponseEntity.badRequest().header("Failure", "A new plan cannot already have an ID").body(null);
		}

		// Set the milestone dates
		if (ruleRepository != null) {
			List<Rule> rules = ruleRepository.findAll();
			PlanMilestoneManager pmm = new PlanMilestoneManager(rules);
			pmm.initialisePlanDates(plan);
		}

		plan.setHistory(plan.getLastModified() + " " + plan.getLastModifiedBy() + " Created\n\n");

		Plan result = planRepository.save(plan);
		return ResponseEntity.created(new URI("/api/plans/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("plan", result.getId().toString())).body(result);
	}

	/**
	 * PUT /plans -> Updates an existing plan.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> updatePlan(@Valid @RequestBody Plan plan) throws URISyntaxException {
		log.debug("REST request to update Plan : {}", plan);
		if (plan.getId() == null) {
			return createPlan(plan);
		}

		// Update the history
		String diff = getDiff(plan);
		if (diff != null) {
			String history = plan.getHistory();
			plan.setHistory(
					plan.getLastModified() + " " + plan.getLastModifiedBy() + "\n" + "\t" + diff + "\n\n" + history);
		}

		Plan result = planRepository.save(plan);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("plan", plan.getId().toString()))
				.body(result);
	}

	/**
	 * GET /plans -> get all the plans.
	 */
	@RequestMapping(value = "/plans", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<List<Plan>> getAllPlans(Pageable pageable) throws URISyntaxException {
		Page<Plan> page = planRepository.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/plans");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/**
	 * GET /plans/:id -> get the "id" plan.
	 */
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Plan> getPlan(@PathVariable Long id) {
		log.debug("REST request to get Plan : {}", id);
		return Optional.ofNullable(planRepository.findOne(id)).map(plan -> new ResponseEntity<>(plan, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * DELETE /plans/:id -> delete the "id" plan.
	 */
	@RequestMapping(value = "/plans/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
		log.debug("REST request to delete Plan : {}", id);
		planRepository.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("plan", id.toString())).build();
	}

	private String getDiff(Plan newPlan) {
		String diff = null;

		Plan oldPlan = planRepository.findOne(newPlan.getId());

		if (oldPlan != null) {

			ArrayList<String> ignoreOnly = new ArrayList<String>();
			ignoreOnly.add("lastModified");
			ignoreOnly.add("lastModifiedBy");

			ArrayList<String> markOnly = new ArrayList<String>();
			markOnly.add("comments");
			markOnly.add("history");

			ObjectDiffer differ = new ObjectDiffer(ignoreOnly, markOnly);
			diff = differ.diff(oldPlan, newPlan);
		}
		return diff;

	}

}
