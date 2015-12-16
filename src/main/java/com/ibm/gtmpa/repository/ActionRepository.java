package com.ibm.gtmpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.gtmpa.domain.Action;

/**
 * Spring Data JPA repository for the Action entity.
 */
public interface ActionRepository extends JpaRepository<Action, Long> {

}
