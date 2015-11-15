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

    @NotNull
    @Size(min = 5)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "action_items")
    private String actionItems;

    @Column(name = "next_action_date", nullable = false)
    private LocalDate nextActionDate;

    @ManyToOne
    private Plan plan;

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