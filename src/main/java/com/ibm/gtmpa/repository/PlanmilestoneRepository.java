package com.ibm.gtmpa.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.domain.Planmilestone;
import com.ibm.gtmpa.domain.enumeration.ActionTypeEnum;
import com.ibm.gtmpa.domain.enumeration.MilestoneTypeEnum;

/**
 * Spring Data JPA repository for the Planmilestone entity.
 */
public interface PlanmilestoneRepository extends JpaRepository<Planmilestone,Long> {

	@Query("select plan0_ from Plan plan0_ Where " +
		" plan0_.id=:planID")
	    List<Plan> findBySearchTerm1(@Param("planID") Long planID);
		
	
	 @Query("select  planmilestone_.plan from Planmilestone planmilestone_ Where " +
				" planmilestone_.milestoneType=:milestoneType "
				+ "and planmilestone_.milestoneDate<=:missedMilestoneDate "
				+ " and planmilestone_.plan "
				+ " NOT in (Select action0_.plan from Action action0_ where action0_.actionTypeCode=:actionTypeCode)")
		List<Plan> findPlansForMissedMilestoneType(@Param("milestoneType") MilestoneTypeEnum milestoneType, @Param("actionTypeCode") ActionTypeEnum actionTypeCode,@Param("missedMilestoneDate") LocalDate missedMilestoneDate);
		
		/**
		 * ("select action0_ from Action action0_, Planmilestone planmilestone_  Where " +
			" planmilestone_.plan.id=action0_.plan.id and action0_.plan.id=:planID and action0_.actionTypeCode=:milestonetype")
			
		 * @param missedDate
		 * @return
		 * 
		 */
		
		@Query("select planmilestone0_ from Planmilestone planmilestone0_  Where " +
				" planmilestone0_.milestoneDate=:missedMilestoneDate ")		
		List<Planmilestone> findPlansForMissedMilestoneDate(@Param("missedMilestoneDate") LocalDate missedMilestoneDate);
		
		/**
		 * @Query("SELECT t FROM Todo t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%',:searchTerm, '%')) " +
            "ORDER BY t.title ASC")
			    List<Todo> findBySearchTerm(@Param("searchTerm") String searchTerm);
			    @Query("select plan0_ from Planmilestone planmilestone_, Plan plan0_,  Where " +
							" planmilestone_.milestone_type=:milestoneType ")
				@Query("select plan0_ from Plan plan0_, Planmilestone planmilestone_  Where " +
				" planmilestone_.plan.id=:planID ")
				
				@Query("select action0_ from Action action0_, Planmilestone planmilestone_  Where " +
			" planmilestone_.plan.id=action0_.plan.id and action0_.plan.id=:planID and action0_.actionTypeCode=:milestonetype") ActionTypeEnum actionTypeCode
		 */

		//@Query("SELECT t FROM Plan t" )	
			/*@Query("select plan0_.id as id1_9_, plan0_.agreedGTMDate as agreed_g2_9_, plan0_.campaignPlanDate as campaign3_9_, plan0_.completeDate as complete4_9_, " + 
			"plan0_.dealsRequired as deals_re5_9_, plan0_.industrySegment as industry6_9_, plan0_.initialDiscussionDate as initiald7_9_," 
					+ " plan0_.marketingCollateralDate as marketin8_9_, plan0_.marketingPlanDate as marketin9_9_, plan0_.partner_id as partner20_9_," +
			" plan0_.preSalesCompetencyDate as presale10_9_, plan0_.proposalDate as proposa11_9_, plan0_.revenueCommitment as revenue12_9_," + 
					" plan0_.salescompetencydate as salesco13_9_, plan0_.solutionarchitecteddate as solutio14_9_," +
			" plan0_.solutioncollateraldate as solutio15_9_, plan0_.solutioncosteddate as solutio16_9_," + 
					" plan0_.solution_name as solutio17_9_, plan0_.status as status18_9_, plan0_.technicalcompetencydate as technic19_9_ from Plan plan0_")*/
			//@Query("select plan0_.id as id1_9_ from Plan plan0_")
			
		
}
