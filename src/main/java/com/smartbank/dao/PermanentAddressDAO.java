package com.smartbank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartbank.domain.PermanentAddress;

@Repository
public interface PermanentAddressDAO extends JpaRepository<PermanentAddress, Long>  {

}
