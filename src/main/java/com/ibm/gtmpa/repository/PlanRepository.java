package com.ibm.gtmpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ibm.gtmpa.domain.Plan;

/**
 * Spring Data JPA repository for the Plan entity.
 */
public interface PlanRepository extends JpaRepository<Plan,Long> {
	
	
	//@Query("SELECT t FROM Plan t" )	
	/*@Query("select plan0_.id as id1_9_, plan0_.agreedGTMDate as agreed_g2_9_, plan0_.campaignPlanDate as campaign3_9_, plan0_.completeDate as complete4_9_, " + 
	"plan0_.dealsRequired as deals_re5_9_, plan0_.industrySegment as industry6_9_, plan0_.initialDiscussionDate as initiald7_9_," 
			+ " plan0_.marketingCollateralDate as marketin8_9_, plan0_.marketingPlanDate as marketin9_9_, plan0_.partner_id as partner20_9_," +
	" plan0_.preSalesCompetencyDate as presale10_9_, plan0_.proposalDate as proposa11_9_, plan0_.revenueCommitment as revenue12_9_," + 
			" plan0_.salescompetencydate as salesco13_9_, plan0_.solutionarchitecteddate as solutio14_9_," +
	" plan0_.solutioncollateraldate as solutio15_9_, plan0_.solutioncosteddate as solutio16_9_," + 
			" plan0_.solution_name as solutio17_9_, plan0_.status as status18_9_, plan0_.technicalcompetencydate as technic19_9_ from Plan plan0_")*/
	//@Query("select plan0_.id as id1_9_ from Plan plan0_")
	@Query("select plan0_ from Plan plan0_")
    List<Plan> findBySearchTerm();
	
	

}
