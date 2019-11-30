package com.smartbank.service;



import java.security.Principal;
import java.util.Map;

import org.json.JSONObject;

import com.smartbank.domain.PermanentAddress;
import com.smartbank.domain.PresentAddress;
import com.smartbank.domain.UserPersonalData;




public interface UserInfoService {
	
	PresentAddress createPresentAddress(PresentAddress presentAddress,Principal principal);
	PermanentAddress createPermanentAddress(PermanentAddress permanentAddress,Principal principal);
	
	UserPersonalData createuserPersonalData(UserPersonalData userPersonalData,Principal principal);
	
	UserPersonalData getUserPersonalData(Principal principal);
	
	PresentAddress getPresentAddress(Principal principal);
	
	PermanentAddress getPermanentAddress(Principal principal);
	
	PresentAddress updatePresentAddress(Principal principal);
	
	Map<String, String> getUserDetails(Principal principal);
	

}
