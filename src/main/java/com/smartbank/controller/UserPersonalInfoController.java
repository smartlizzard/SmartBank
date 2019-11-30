package com.smartbank.controller;

import java.security.Principal;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbank.domain.PermanentAddress;
import com.smartbank.domain.PresentAddress;
import com.smartbank.domain.UserPersonalData;
import com.smartbank.service.UserInfoService;

@RestController
@RequestMapping("/userInfo")
public class UserPersonalInfoController {

	@Autowired
	private UserInfoService userInfo;

	@PostMapping("/personalData")
	public ResponseEntity<String> insertPersonalData(Principal principal,
			@RequestBody UserPersonalData userPersonalData) {

		UserPersonalData createuserPersonalData = userInfo.createuserPersonalData(userPersonalData, principal);
		if (createuserPersonalData == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Updation Failed");

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("SuccessFully Updated");

		}

	}

	@PostMapping("/presentAddress")
	public ResponseEntity<String> insertPresentAddress(Principal principal,
			@RequestBody PresentAddress presentAddress) {
		PresentAddress createPresentAddress = userInfo.createPresentAddress(presentAddress, principal);

		if (createPresentAddress == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Updation Failed");

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("SuccessFully Updated");

		}
	}

	@PostMapping("/permanentAddress")
	public ResponseEntity<String> insertPermanentAddress(Principal principal,
			@RequestBody PermanentAddress permanentAddress) {
		PermanentAddress createPermanentAddress = userInfo.createPermanentAddress(permanentAddress, principal);

		if (createPermanentAddress == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Updation Failed");

		} else {
			return ResponseEntity.status(HttpStatus.CREATED).body("SuccessFully Updated");

		}
	}

	@GetMapping("/getPersonalData")
	public ResponseEntity<UserPersonalData> getUserPersonalData(Principal principal) {
		UserPersonalData userPersonalData = userInfo.getUserPersonalData(principal);
		return ResponseEntity.status(HttpStatus.FOUND).body(userPersonalData);
	}
	
	
	@GetMapping("/getPermanentAddress")
	public ResponseEntity<PermanentAddress> getUserPermanentAddress(Principal principal){
		PermanentAddress permanentAddress = userInfo.getPermanentAddress(principal);
		return ResponseEntity.status(HttpStatus.FOUND).body(permanentAddress);	
				
	}
	
	
	  @GetMapping("/getPresentAddress") 
	  public ResponseEntity<PresentAddress>getUserPresentAddress(Principal principal)
	  { 
		  PresentAddress presentAddress =userInfo.getPresentAddress(principal); 
		  System.out.println("presentAddress"+presentAddress);
		  return ResponseEntity.status(HttpStatus.ACCEPTED).body(presentAddress);
	  
	  }
	
	/*
	 * @GetMapping("/getPresentAddress") public PresentAddress
	 * getUserPresentAddress(Principal principal){ PresentAddress presentAddress =
	 * userInfo.getPresentAddress(principal); return presentAddress ;
	 * 
	 * }
	 */
	  
	  @GetMapping(value = "/getUserData",produces = MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Map<String, String>> getUserData(Principal principal){
		  Map<String, String> map =userInfo.getUserDetails(principal);
		 // System.out.println(userData);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(map);
		  
	  }
}