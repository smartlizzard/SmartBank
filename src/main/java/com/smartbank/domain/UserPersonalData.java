package com.smartbank.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class UserPersonalData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private Long id;
	private String fathersName;
	private String mothersName;
	@DateTimeFormat(pattern = "DD/MM/YYYY")
	private Date dob;
	private String occupation;
	private String gender;
	private String nominee;
	private String maritialStatus;
	private String userType; //BB-CB
	//private Lob photo;
	private String adharNumber;
	private String panNumber;
	
	@OneToOne
	@JoinColumn(name = "user_id",unique = true,nullable = false)
	@JsonIgnore///without this we are getting all the details of the user including id password and account.Adding the anotation we are dending only the personal data
	private User user;
	
	public UserPersonalData() {
		
	}




	public UserPersonalData(String fathersName, String mothersName, Date dob, String occupation, String gender,
			String nominee, String maritialStatus, String userType, String adharNumber, String panNumber, User user) {
		super();
		this.fathersName = fathersName;
		this.mothersName = mothersName;
		this.dob = dob;
		this.occupation = occupation;
		this.gender = gender;
		this.nominee = nominee;
		this.maritialStatus = maritialStatus;
		this.userType = userType;
		this.adharNumber = adharNumber;
		this.panNumber = panNumber;
		this.user = user;
	}










	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	
	
	public String getFathersName() {
		return fathersName;
	}
	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}
	public String getMothersName() {
		return mothersName;
	}
	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNominee() {
		return nominee;
	}
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}
	public String getMaritialStatus() {
		return maritialStatus;
	}
	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
	public String getAdharNumber() {
		return adharNumber;
	}

	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/*
	 * public Lob getPhoto() { return photo; }
	 * 
	 * 
	 * public void setPhoto(Lob photo) { this.photo = photo; }
	 */

	

}
