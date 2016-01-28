package com.ibm.gtmpa.domain;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class PlanMilestoneManager {

	private final Logger log = LoggerFactory.getLogger(PlanMilestoneManager.class);

	private HashMap<Long, Rule> map = new HashMap<Long, Rule>();

	public PlanMilestoneManager(List<Rule> rules) {
		for (Rule rule : rules) {
			map.put(rule.getId(), rule);
		}
	}

	public Rule getRuleByName(String name) {
		Rule rule = null;
		for (Rule ruleCandidate : map.values()) {
			if (ruleCandidate.getName().equals(name)) {
				rule = ruleCandidate;
				break;
			}
		}
		return rule;
	}

	public Rule getRuleByID(Long id) {
		return map.get(id);
	}

	public List<Rule> getValidStates(String currentState) {
		assert(currentState != null);
		
		List<Rule> rules = new ArrayList<Rule>();

		Rule currentStateRule = getRuleByName(currentState);
		Rule backRule = getBackState(currentState);
		Rule forwardRule = getNextState(currentState);
		Rule invalidRule = getRuleByName(Plan.INVALID_STATUS);

		if (backRule != null && !rules.contains(backRule)) {
			rules.add(backRule);
		}
		
		if (currentStateRule != null && !rules.contains(currentStateRule)) {
			rules.add(currentStateRule);
		}

		if (forwardRule != null && !rules.contains(forwardRule)) {
			rules.add(forwardRule);
		}

		// Add Invalid
		if (invalidRule != null && !rules.contains(invalidRule)) {
			rules.add(invalidRule);
		}

		return rules;
	}

	public Rule getBackState(String currentState) {
		Rule backRule = null;
		Rule currentStateRule = getRuleByName(currentState);
		if (currentStateRule != null) {
			String backState = currentStateRule.getBackState();
			if (NumberUtils.isNumber(backState)) {
				backRule = map.get(new Long(backState));
			}
		}
		return backRule;
	}

	public Rule getNextState(String currentState) {
		Rule nextRule = null;
		Rule currentStateRule = getRuleByName(currentState);
		if (currentStateRule != null) {
			nextRule = map.get(new Long(currentStateRule.getForwardState()));
		}
		return nextRule;
	}

	public boolean setDate(Plan plan, Rule rule, LocalDate lDate) {
		boolean success = true;
		String methodName = rule.getFieldSpec();

		try {
			methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
			Method setter = BeanUtils.findMethod(Plan.class, methodName, LocalDate.class);
			setter.invoke(plan, lDate);
		} catch (Exception e) {
			success = false;
			log.warn("Setting " + methodName + " to " + lDate + " failed.", e);
		}
		return success;
	}

	public static LocalDate nextBusinessDay(LocalDate currentDate) {
		LocalDate businessDay = currentDate;

		// Ensure date is not a weekend
		DayOfWeek dow = currentDate.getDayOfWeek();
		if (dow.equals(DayOfWeek.SATURDAY)) {
			businessDay = currentDate.plusDays(2);
		}
		if (dow.equals(DayOfWeek.SUNDAY)) {
			businessDay = currentDate.plusDays(1);
		}
		return businessDay;
	}

	// Given anAgreedDate, set all the milestone dates working backwards
	public void initialisePlanDates(Plan plan) {

		// Start at the Complete Record
		Rule rule = getRuleByName(Plan.END_STATUS);
		assert (rule != null);

		// The Complete date should be the agreedGTMDate
		LocalDate currentDate = plan.getAgreedGTMDate();

		while (!rule.getName().equals(Plan.START_STATUS)) {
			int days = new Integer(rule.getRule()).intValue();
			currentDate = currentDate.minusDays(days);

			currentDate = nextBusinessDay(currentDate);

			setDate(plan, rule, currentDate);

			rule = map.get(new Long(rule.getBackState()));
		}
	}

	// Return the number of days from New to Complete
	public int getEndToEndDayCount() {
		int days = 0;

		// Start at the New Record
		Rule rule = getRuleByName(Plan.START_STATUS);
		assert (rule != null);

		while (!rule.getName().equals(Plan.END_STATUS)) {
			days += new Integer(rule.getRule()).intValue();
			rule = map.get(new Long(rule.getForwardState()));
		}
		days += new Integer(rule.getRule()).intValue();
		return days;
	}

	public boolean isAgreedGTMDateValid(LocalDate agLDate) {
		int e2eDays = getEndToEndDayCount();
		LocalDate startDate = agLDate.minusDays(e2eDays);
		LocalDate todaysDate = LocalDate.now();

		return startDate.isAfter(todaysDate) || startDate.isEqual(todaysDate);
	}

}