package com.smartbank.service;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartbank.dao.PermanentAddressDAO;
import com.smartbank.dao.PresentAddressDAO;
import com.smartbank.dao.UserPersonalDataDAO;
import com.smartbank.domain.PermanentAddress;
import com.smartbank.domain.PresentAddress;
import com.smartbank.domain.User;
import com.smartbank.domain.UserPersonalData;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	PresentAddressDAO presentAddressDAO;

	@Autowired
	PermanentAddressDAO permanentAddressDAO;

	@Autowired
	UserPersonalDataDAO userPersonalDataDAO;
	
	@Autowired
	UserService userService;
	@Autowired
	UserCacheService cacheService;
	//String name;
	
	
	

	@Override
	public PresentAddress createPresentAddress(PresentAddress presentAddress,Principal principal) {
       User user= userService.findByUsername(principal.getName());
       
      
       
       if (presentAddressDAO.existsById(user.getUserId())) {
    	   
    	   presentAddress.setId(user.getUserId());
    	   presentAddress.setUser(user);
    	   PresentAddress updateAddress = presentAddressDAO.save(presentAddress);
    	 return updateAddress;
		
	}
      else {
    	  presentAddress.setUser(user);
  		return presentAddressDAO.save(presentAddress);
	}

	}

	@Override
	public PermanentAddress createPermanentAddress(PermanentAddress permanentAddress,Principal principal) {
		User user= userService.findByUsername(principal.getName());
		permanentAddress.setUser(user);
		return permanentAddressDAO.save(permanentAddress);

	}

	@Override
	public UserPersonalData createuserPersonalData(UserPersonalData userPersonalData,Principal principal) {
		User user=userService.findByUsername(principal.getName());
		userPersonalData.setUser(user);
		return userPersonalDataDAO.save(userPersonalData);

	}

	@Override
	public UserPersonalData getUserPersonalData(Principal principal) {
		User user=cacheService.findUserDetailsByUsername(principal);
		Optional<UserPersonalData> optionalPersonalData = userPersonalDataDAO.findById(user.getUserId());
		UserPersonalData userPersonalData = optionalPersonalData.get();
		return userPersonalData;
	}
	
	
	@Override
	public PresentAddress getPresentAddress(Principal principal) {
		User user=cacheService.findUserDetailsByUsername(principal);
		Optional<PresentAddress> optionalPresentAddress = presentAddressDAO.findById(user.getUserId());
		PresentAddress presentAddress = optionalPresentAddress.get();
		return presentAddress;
	}
	
	@Override
	public PermanentAddress getPermanentAddress(Principal principal) {
		User user=cacheService.findUserDetailsByUsername(principal);
		Optional<PermanentAddress> optionalPermanentAddress = permanentAddressDAO.findById(user.getUserId());
		PermanentAddress permanentAddress = optionalPermanentAddress.get();
		return permanentAddress;
	
	}

	@Override
	public PresentAddress updatePresentAddress(Principal principal) {
		User user=cacheService.findUserDetailsByUsername(principal);
		return null;
	}

	@Override
	public Map<String, String> getUserDetails(Principal principal) {
		User user=cacheService.findUserDetailsByUsername(principal);
		List<Object> userData =userPersonalDataDAO.findUserDetails(user.getUsername());
		Map<String, String> map=new HashMap<>();
		
		if (userData.size() > 0) {
			Object[] object = (Object[]) userData.get(0);
			String email = (String)object[0];
			String phone = (String)object[1];
			String aadhar=(String)object[2];
			String pan=(String)object[3];
			System.out.println(aadhar);
			//userPersonalData.setAdharNumber();
			map.put("email", email);
			map.put("phone", phone);
			map.put("aadhar", aadhar);
			map.put("pan", pan);
			
			
		}
		return map;
		
		
	}
	
	
		
}
