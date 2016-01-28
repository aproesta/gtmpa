package com.ibm.gtmpa.service.util;

import static org.assertj.core.api.StrictAssertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Test;

import com.ibm.gtmpa.domain.Plan;

public class ObjectDifferTest {

	@Test
	public void testStringProp() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();
		p1.setBrand("banana");
		p2.setBrand("banana");

		String diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isEmpty();

		p2.setBrand("bananaX");

		diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).contains("brand");
		assertThat(diff).contains("banana");
		assertThat(diff).contains("bananaX");
	}

	@Test
	public void testNoDiff() {
		Plan p1 = new Plan();
		Plan p2 = new Plan();
		String diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isEmpty();
	}

	@Test
	public void testDateProp() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setCreationDate(LocalDate.now());
		p2.setCreationDate(LocalDate.now());

		String diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isEmpty();

		p2.setCreationDate(LocalDate.now().plusDays(2));

		diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("creationDate");

	}

	@Test
	public void testNumberProp() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setDealsRequired(1);
		p2.setDealsRequired(1);

		String diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isEmpty();

		p1.setDealsRequired(2);

		diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("dealsRequired");
		assertThat(diff).contains("1");
		assertThat(diff).contains("2");

	}

	@Test
	public void testMultipleProps() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setDealsRequired(1);
		p2.setDealsRequired(1);

		p1.setBrand("banana");
		p2.setBrand("banana");

		String diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isEmpty();

		p1.setDealsRequired(2);

		diff = new ObjectDiffer().diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("dealsRequired");
		assertThat(diff).contains("1");
		assertThat(diff).contains("2");

		assertThat(diff).doesNotContain("brand");

		p2.setBrand("bananaX");
		diff = new ObjectDiffer().diff(p1, p2);

		assertThat(diff).contains("dealsRequired");
		assertThat(diff).contains("1");
		assertThat(diff).contains("2");
		assertThat(diff).contains("brand");
		assertThat(diff).contains("banana");
		assertThat(diff).contains("bananaX");

	}

	@Test
	public void testIgnoreProps() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setDealsRequired(1);
		p2.setDealsRequired(1);

		ArrayList<String> ignoreFields = new ArrayList<String>();
		ignoreFields.add("dealsRequired");

		ObjectDiffer differ = new ObjectDiffer(ignoreFields, null);

		String diff = differ.diff(p1, p2);
		assertThat(diff).isEmpty();

		p1.setBrand("banana");
		p2.setBrand("bananaX");

		differ = new ObjectDiffer(ignoreFields, null);

		diff = differ.diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("brand");
		assertThat(diff).contains("banana");
		assertThat(diff).contains("bananaX");

	}

	@Test
	public void testMarkOnlyProps() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setDealsRequired(1);
		p2.setDealsRequired(2);

		ArrayList<String> markOnly = new ArrayList<String>();
		markOnly.add("dealsRequired");

		ObjectDiffer differ = new ObjectDiffer(null, markOnly);

		String diff = differ.diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("dealsRequired");
		assertThat(diff).contains("changed");
		assertThat(diff).doesNotContain("1");
		assertThat(diff).doesNotContain("2");
	}

	@Test
	public void testComplexProps() {

		Plan p1 = new Plan();
		Plan p2 = new Plan();

		p1.setDealsRequired(1);
		p2.setDealsRequired(2);
		
		p1.setBrand("banana");
		p2.setBrand("bananaX");

		p1.setComments("comments");
		p2.setComments("commentsX");

		p1.setCreationDate(LocalDate.now());
		p2.setCreationDate(LocalDate.now().plusDays(2));

		ArrayList<String> ignoreOnly = new ArrayList<String>();
		ignoreOnly.add("creationDate");

		ArrayList<String> markOnly = new ArrayList<String>();
		markOnly.add("comments");

		ObjectDiffer differ = new ObjectDiffer(ignoreOnly, markOnly);
		String diff = differ.diff(p1, p2);
		assertThat(diff).isNotEmpty();

		assertThat(diff).contains("dealsRequired");
		assertThat(diff).contains("1");
		assertThat(diff).contains("2");
		
		assertThat(diff).contains("brand");
		assertThat(diff).contains("banana");
		assertThat(diff).contains("bananaX");

		assertThat(diff).contains("comments");
		assertThat(diff).contains("changed");

		assertThat(diff).doesNotContain("creationDate");
		
	}

}
