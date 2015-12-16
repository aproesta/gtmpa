package com.ibm.gtmpa.domain;

import java.io.Serializable;
import java.time.LocalDate;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ibm.gtmpa.domain.enumeration.MilestoneTypeEnum;

/**
 * A Planmilestone.
 */
@Entity
@Table(name = "planmilestone")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Planmilestone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "milestone_date")
    private LocalDate milestoneDate;

    @ManyToOne
    private Plan plan;

    @Enumerated(EnumType.STRING)
    @Column(name = "milestone_type", nullable = true)
    private MilestoneTypeEnum milestoneType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getMilestoneDate() {
        return milestoneDate;
    }

    public void setMilestoneType(MilestoneTypeEnum milestoneType) {
        this.milestoneType = milestoneType;
    }
    public MilestoneTypeEnum getMilestoneType() {
        return milestoneType;
    }

    public void setMilestoneDate(LocalDate milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

   

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Planmilestone planmilestone = (Planmilestone) o;

        if ( ! Objects.equals(id, planmilestone.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Planmilestone{" +
            "id=" + id +
            ", milestoneDate='" + milestoneDate + "'" +
            '}';
    }
}
