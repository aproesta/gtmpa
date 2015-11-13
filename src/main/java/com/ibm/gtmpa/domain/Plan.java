package com.ibm.gtmpa.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.ibm.gtmpa.domain.enumeration.IndustrySegmentEnum;

/**
 * A Plan.
 */
@Entity
@Table(name = "plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plan implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "solution_name", length = 30, nullable = false)
    private String solutionName;

    @NotNull
    @Column(name = "agreed_gtmdate", nullable = false)
    private LocalDate agreedGTMDate;

    @NotNull
    @Column(name = "revenue_commitment", nullable = false)
    private Float revenueCommitment;

    @NotNull
    @Column(name = "deals_required", nullable = false)
    private Integer dealsRequired;

    @NotNull
    @Column(name = "proposal_date", nullable = false)
    private LocalDate proposalDate;

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

        if ( ! Objects.equals(id, plan.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Plan{" +
            "id=" + id +
            ", solutionName='" + solutionName + "'" +
            ", agreedGTMDate='" + agreedGTMDate + "'" +
            ", revenueCommitment='" + revenueCommitment + "'" +
            ", dealsRequired='" + dealsRequired + "'" +
            ", proposalDate='" + proposalDate + "'" +
            ", industrySegment='" + industrySegment + "'" +
            '}';
    }
}
