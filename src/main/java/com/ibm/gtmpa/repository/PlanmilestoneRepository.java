package com.ibm.gtmpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.domain.Planmilestone;

/**
 * Spring Data JPA repository for the Planmilestone entity.
 */
public interface PlanmilestoneRepository extends JpaRepository<Planmilestone,Long> {

}
