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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ibm.gtmpa.domain.enumeration.ActionStatusEnum;
import com.ibm.gtmpa.domain.enumeration.IndustrySegmentEnum;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Action implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "log_date", nullable = false)
    private LocalDate logDate;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "action_items")
    private String actionItems;

    @Column(name = "next_action_date", nullable = false)
    private LocalDate nextActionDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "staus", nullable = true)
    private ActionStatusEnum status;
    
    @ManyToOne
    private Plan plan;
	//ndimari
	@ManyToOne
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLogDate() {
        return logDate;
    }

    public void setLogDate(LocalDate logDate) {
        this.logDate = logDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionItems() {
        return actionItems;
    }

    public void setActionItems(String actionItems) {
        this.actionItems = actionItems;
    }

    public LocalDate getNextActionDate() {
        return nextActionDate;
    }

    public void setNextActionDate(LocalDate nextActionDate) {
        this.nextActionDate = nextActionDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
	
	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public ActionStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ActionStatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Action action = (Action) o;

        if ( ! Objects.equals(id, action.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + id +
            ", logDate='" + logDate + "'" +
            ", description='" + description + "'" +
            ", actionItems='" + actionItems + "'" +
            ", nextActionDate='" + nextActionDate + "'" +
            '}';
    }
}
