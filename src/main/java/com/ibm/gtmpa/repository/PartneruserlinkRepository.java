package com.ibm.gtmpa.repository;

import com.ibm.gtmpa.domain.Partneruserlink;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partneruserlink entity.
 */
public interface PartneruserlinkRepository extends JpaRepository<Partneruserlink,Long> {

}
