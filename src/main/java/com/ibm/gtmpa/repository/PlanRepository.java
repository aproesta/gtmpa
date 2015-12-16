package com.ibm.gtmpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.gtmpa.domain.Plan;

/**
 * Spring Data JPA repository for the Plan entity.
 */
public interface PlanRepository extends JpaRepository<Plan, Long> {

}
