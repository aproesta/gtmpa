package com.ibm.gtmpa.repository;

import com.ibm.gtmpa.domain.Rule;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Rule entity.
 */
public interface RuleRepository extends JpaRepository<Rule,Long> {

}
