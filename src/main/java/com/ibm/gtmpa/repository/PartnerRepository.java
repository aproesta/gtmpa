package com.ibm.gtmpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.gtmpa.domain.Partner;

/**
 * Spring Data JPA repository for the Partner entity.
 */
public interface PartnerRepository extends JpaRepository<Partner, Long> {

}
