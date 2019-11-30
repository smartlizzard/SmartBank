package com.smartbank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbank.domain.PresentAddress;

@Repository
public interface PresentAddressDAO extends JpaRepository<PresentAddress, Long>  {
	

}
