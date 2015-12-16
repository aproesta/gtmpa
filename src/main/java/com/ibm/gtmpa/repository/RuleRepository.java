package com.ibm.gtmpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.gtmpa.domain.Rule;

/**
 * Spring Data JPA repository for the Rule entity.
 */
public interface RuleRepository extends JpaRepository<Rule, Long> {

}
