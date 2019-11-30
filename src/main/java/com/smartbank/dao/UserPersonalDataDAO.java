package com.smartbank.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartbank.domain.UserPersonalData;

@Repository
public interface UserPersonalDataDAO extends JpaRepository<UserPersonalData, Long> {
	
	public  final static String FIND_USERDATA="SELECT user.`email` , user.`phone` , user_personal_data.`adhar_number`,user_personal_data.`pan_number`\r\n" + 
			"FROM onlinebanking.user_personal_data ,onlinebanking.user\r\n" + 
			"WHERE user_personal_data.`user_id` = user.`user_id`\r\n" + 
			"AND username=:username";
	//UserPersonalData findByuserId(Long userId);

	@Query(value=FIND_USERDATA,nativeQuery = true)
	List<Object> findUserDetails(@Param("username") String username);
}
