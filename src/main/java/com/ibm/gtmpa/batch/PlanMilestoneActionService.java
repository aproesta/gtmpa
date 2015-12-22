package com.ibm.gtmpa.batch;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.CaseFormat;
import com.ibm.gtmpa.domain.Action;
import com.ibm.gtmpa.domain.Plan;
import com.ibm.gtmpa.domain.enumeration.ActionStatusEnum;
import com.ibm.gtmpa.domain.enumeration.ActionTypeEnum;
import com.ibm.gtmpa.domain.enumeration.MilestoneTypeEnum;
import com.ibm.gtmpa.repository.ActionRepository;
import com.ibm.gtmpa.repository.PlanRepository;
import com.ibm.gtmpa.repository.PlanmilestoneRepository;
import com.ibm.gtmpa.web.rest.ActionResource;

/**
 * REST controller for managing Plan.
 */
@RestController
@RequestMapping("/api")
public class PlanMilestoneActionService {

	private final Logger log = LoggerFactory.getLogger(PlanMilestoneActionService.class);

	@Inject
	private PlanmilestoneRepository planmilestoneRepository;
	
	@Inject
	private PlanRepository planRepository;
	
	@Inject
	private ActionRepository actionRepository;
	
	/**
	 * PUT /Action/:id -> create the "id" plan.
	 */
	@RequestMapping(value = "/planmilestoneactionservices/{id}/{milestonetype}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	
	public void createActionForPlanMilestone(@RequestParam("id") Long id, @RequestParam("milestonetype") String milestonetype) {
		
		ActionResource actionResource = new ActionResource();
		
		log.debug("REST request to get Plan : {}", id);
		
		 List<Plan> planList2 = planmilestoneRepository.findBySearchTerm1(id);
	      
		 //System.out.println("**************** ");
	    //    System.out.println("*************" + planList2.size());
	        //first step
	     String correspondingActionType = milestonetype.replace("Milestone", "Date");
	        //second step not an ideal logic but will do it anyway.
	     correspondingActionType = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, correspondingActionType);
	        
	        //3rd Step
	     correspondingActionType="For"+correspondingActionType;
	        
	        
	       
		//System.out.println("  *************  "+id + "  milestonetype  "+milestonetype+ " and correspondingActionType "+ActionTypeEnum.valueOf(correspondingActionType)  + "************************I am HERe *********************************");
		
		
		List<Action> actionList = actionRepository.findActionsForMissedMilestoneType(id,ActionTypeEnum.valueOf(correspondingActionType));
		
		if(actionList == null || actionList.size() == 0) {

	    	   Plan plan = planRepository.getOne(id);
	    	   
			//if(plan.getAgreedGTMDate().isBefore(LocalDate.now())){
			
				   Action action = new Action();
				   
				   action.setLogDate(LocalDate.now());
				   action.setDescription("For Overdue Paln :" + plan.getSolutionName());
				   action.setPlan(plan);
				   action.setActionTypeCode(ActionTypeEnum.valueOf(correspondingActionType));
				   action.setStatus(ActionStatusEnum.New1);
				   
				   try {
					actionResource.createAction(action);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("Created an action item for overdue plan : " + plan.getSolutionName() + " and planID :" + plan.getId());
			//}
	       
		}
		
		
		
	}
	/**
	 * PUT /Action/:id -> create the "id" plan.
	 */
	@RequestMapping(value = "/createActionsForMissedMilestoneForAllPlans", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	
	public void createActionsForMissedMilestoneForAllPlans() {
		
		LocalDate missedMilestoneDate = LocalDate.now().plusDays(5);//minusDays(1);
		
		//System.out.println("  *************  " + "  missedMilestoneDate:  "+missedMilestoneDate);
		ActionResource actionResource = new ActionResource();
		
		for(MilestoneTypeEnum milestonetype : MilestoneTypeEnum.values()){
			//System.out.println("  *************  " + "  milestonetype  "+milestonetype);
			
			String milestonetypeStr = milestonetype.toString();
			String correspondingActionType = milestonetypeStr.replace("Milestone", "Date");
	        //second step not an ideal logic but will do it anyway.
	        correspondingActionType = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, correspondingActionType);	        
	        //3rd Step
	        correspondingActionType="For"+correspondingActionType;//
	        
	        List<Plan> planList = planmilestoneRepository.findPlansForMissedMilestoneType(milestonetype, 
	        		ActionTypeEnum.valueOf(correspondingActionType),missedMilestoneDate);
	        
	        //System.out.println("  *************  " + "  for milestonetype the number of plans  "+planList.size());
	        
	        for(Plan plan0 : planList) {
	        
	        	Action action = new Action();
	 			   
	 			action.setLogDate(LocalDate.now());
	 			action.setDescription("For Overdue Paln :" + plan0.getSolutionName());
	 			action.setPlan(plan0);
	 			action.setActionTypeCode(ActionTypeEnum.valueOf(correspondingActionType));
	 			action.setStatus(ActionStatusEnum.New1);
	 				   
	 			try {
	 					
	 					actionResource.createAction(action);
	 					
	 				} catch (URISyntaxException e) {
	 					// TODO Auto-generated catch block
	 					e.printStackTrace();
	 				}
	 				   
	 				log.info("Created an action item for overdue plan : " + plan0.getSolutionName() + " and planID :" + plan0.getId());
	        }
	      
	        
		}
		
	}

	
}


/** For Action Table
 * ForAgreedGTMDate,ForProposalDate,ForInitialDiscussionDate,ForSalesCompetencyDate,ForPreSalesCompetencyDate,
   ForTechnicalCompetencyDate,ForSolutionArchitectedDate,ForSolutionCostedDate,
   ForSolutionCollateralDate,ForMarketingCollateralDate,ForMarketingPlanDate,ForCampaignPlanDate
 */

/**
 * For Milestone Table
 * agreedGTMMilestone,proposalMilestone,initialDiscussionMilestone,salesCompetencyMilestone,preSalesCompetencyMilestone,
	technicalCompetencyMilestone,solutionArchitectedMilestone,solutionCostedMilestone,
	solutionCollateralMilestone,marketingCollateralMilestone,marketingPlanMilestone,campaignPlanMilestone

 */