package com.ibm.gtmpa.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ibm.gtmpa.domain.enumeration.IndustrySegmentEnum;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plan implements Serializable {

	private static final long serialVersionUID = 69752347758587250L;

	public static String START_STATUS = "New";
	public static String END_STATUS = "Complete";
	public static String INVALID_STATUS = "Invalid";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Size(min = 3, max = 30)
	@Column(name = "solution_name", length = 30, nullable = false)
	private String solutionName;

	@NotNull
	@Column(name = "status", length = 100, nullable = false)
	private String status = START_STATUS;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "comments", columnDefinition = "LONGTEXT", nullable = true)
	private String comments;

	@Column(name = "history", columnDefinition = "LONGTEXT", nullable = true)
	private String history;

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	@NotNull
	@Column(name = "partner_type", length = 100, nullable = false)
	private String partnerType;

	@NotNull
	@Column(name = "region", length = 100, nullable = false)
	private String region;

	public String getPartnerType() {
		return partnerType;
	}

	public void setPartnerType(String partnerType) {
		this.partnerType = partnerType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Float getRevenueBooked() {
		return revenueBooked;
	}

	public void setRevenueBooked(Float revenueBooked) {
		this.revenueBooked = revenueBooked;
	}

	@NotNull
	@Column(name = "last_modified_by", nullable = false)
	private String lastModifiedBy;

	@NotNull
	@Column(name = "last_modified", nullable = false)
	private String lastModified;

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	@NotNull
	@Column(name = "brand", length = 100, nullable = false)
	private String brand;

	@Column(name = "revenue_booked", nullable = true)
	private Float revenueBooked;

	@NotNull
	@Column(name = "agreed_gtmdate", nullable = false)
	private LocalDate agreedGTMDate;

	@NotNull
	@Column(name = "creation_date", nullable = false)

	private LocalDate creationDate;

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	@NotNull
	@Column(name = "revenue_commitment", nullable = false)
	private Float revenueCommitment;

	@NotNull
	@Column(name = "deals_required", nullable = false)
	private Integer dealsRequired;

	@NotNull
	@Column(name = "proposal_date", nullable = false)
	private LocalDate proposalDate;

	@Column(name = "initialdiscussiondate", nullable = true)
	private LocalDate initialDiscussionDate;

	public LocalDate getInitialDiscussionDate() {
		return initialDiscussionDate;
	}

	public void setInitialDiscussionDate(LocalDate initialDiscussionDate) {
		this.initialDiscussionDate = initialDiscussionDate;
	}

	public LocalDate getSalesCompetencyDate() {
		return salesCompetencyDate;
	}

	public void setSalesCompetencyDate(LocalDate salesCompetencyDate) {
		this.salesCompetencyDate = salesCompetencyDate;
	}

	public LocalDate getPreSalesCompetencyDate() {
		return preSalesCompetencyDate;
	}

	public void setPreSalesCompetencyDate(LocalDate preSalesCompetencyDate) {
		this.preSalesCompetencyDate = preSalesCompetencyDate;
	}

	public LocalDate getTechnicalCompetencyDate() {
		return technicalCompetencyDate;
	}

	public void setTechnicalCompetencyDate(LocalDate technicalCompetencyDate) {
		this.technicalCompetencyDate = technicalCompetencyDate;
	}

	public LocalDate getSolutionArchitectedDate() {
		return solutionArchitectedDate;
	}

	public void setSolutionArchitectedDate(LocalDate solutionArchitectedDate) {
		this.solutionArchitectedDate = solutionArchitectedDate;
	}

	public LocalDate getSolutionCostedDate() {
		return solutionCostedDate;
	}

	public void setSolutionCostedDate(LocalDate solutionCostedDate) {
		this.solutionCostedDate = solutionCostedDate;
	}

	public LocalDate getSolutionCollateralDate() {
		return solutionCollateralDate;
	}

	public void setSolutionCollateralDate(LocalDate solutionCollateralDate) {
		this.solutionCollateralDate = solutionCollateralDate;
	}

	public LocalDate getMarketingCollateralDate() {
		return marketingCollateralDate;
	}

	public void setMarketingCollateralDate(LocalDate marketingCollateralDate) {
		this.marketingCollateralDate = marketingCollateralDate;
	}

	public LocalDate getMarketingPlanDate() {
		return marketingPlanDate;
	}

	public void setMarketingPlanDate(LocalDate marketingPlanDate) {
		this.marketingPlanDate = marketingPlanDate;
	}

	public LocalDate getCampaignPlanDate() {
		return campaignPlanDate;
	}

	public void setCampaignPlanDate(LocalDate campaignPlanDate) {
		this.campaignPlanDate = campaignPlanDate;
	}

	public LocalDate getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(LocalDate completeDate) {
		this.completeDate = completeDate;
	}

	@Column(name = "salescompetencydate", nullable = true)
	private LocalDate salesCompetencyDate;

	@Column(name = "presalescompetencydate", nullable = true)
	private LocalDate preSalesCompetencyDate;

	@Column(name = "technicalcompetencydate", nullable = true)
	private LocalDate technicalCompetencyDate;

	@Column(name = "solutionarchitecteddate", nullable = true)
	private LocalDate solutionArchitectedDate;

	@Column(name = "solutioncosteddate", nullable = true)
	private LocalDate solutionCostedDate;

	@Column(name = "solutioncollateraldate", nullable = true)
	private LocalDate solutionCollateralDate;

	@Column(name = "marketingcollateraldate", nullable = true)
	private LocalDate marketingCollateralDate;

	@Column(name = "marketingplandate", nullable = true)
	private LocalDate marketingPlanDate;

	@Column(name = "campaignplandate", nullable = true)
	private LocalDate campaignPlanDate;

	@Column(name = "completedate", nullable = true)
	private LocalDate completeDate;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "industry_segment", nullable = false)
	private IndustrySegmentEnum industrySegment;

	@ManyToOne
	private Partner partner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSolutionName() {
		return solutionName;
	}

	public void setSolutionName(String solutionName) {
		this.solutionName = solutionName;
	}

	public LocalDate getAgreedGTMDate() {
		return agreedGTMDate;
	}

	public void setAgreedGTMDate(LocalDate agreedGTMDate) {
		this.agreedGTMDate = agreedGTMDate;
	}

	public Float getRevenueCommitment() {
		return revenueCommitment;
	}

	public void setRevenueCommitment(Float revenueCommitment) {
		this.revenueCommitment = revenueCommitment;
	}

	public Integer getDealsRequired() {
		return dealsRequired;
	}

	public void setDealsRequired(Integer dealsRequired) {
		this.dealsRequired = dealsRequired;
	}

	public LocalDate getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(LocalDate proposalDate) {
		this.proposalDate = proposalDate;
	}

	public IndustrySegmentEnum getIndustrySegment() {
		return industrySegment;
	}

	public void setIndustrySegment(IndustrySegmentEnum industrySegment) {
		this.industrySegment = industrySegment;
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Plan plan = (Plan) o;

		if (!Objects.equals(id, plan.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
