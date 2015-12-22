package com.ibm.gtmpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibm.gtmpa.domain.Action;
import com.ibm.gtmpa.domain.enumeration.ActionTypeEnum;

/**
 * Spring Data JPA repository for the Action entity.
 */
public interface ActionRepository extends JpaRepository<Action,Long> {

	

	@Query("select action0_ from Action action0_, Planmilestone planmilestone_  Where " +
			" planmilestone_.plan.id=action0_.plan.id and action0_.plan.id=:planID and action0_.actionTypeCode=:milestonetype")
	List<Action> findActionsForMissedMilestoneType(@Param("planID") Long planID, @Param("milestonetype") ActionTypeEnum milestonetype);
}
