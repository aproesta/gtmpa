package com.ibm.gtmpa.repository;

import com.ibm.gtmpa.domain.Partner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partner entity.
 */
public interface PartnerRepository extends JpaRepository<Partner,Long> {

}
