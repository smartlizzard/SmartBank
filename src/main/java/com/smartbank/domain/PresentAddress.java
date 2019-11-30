package com.smartbank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PresentAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false)
	private Long id;
	private String houseNo;
	private String plotNo;
	private String villageName;
	private String city;
	private String dist;
	private String state;
	private String country;
	private int pinNumber;
	
	@OneToOne
	@JoinColumn(name = "user_id",unique = true,nullable = false)
	@JsonIgnore
	private User user;
	

	public PresentAddress() {
		
	}
	



	

	public PresentAddress(String houseNo, String plotNo, String villageName, String city, String dist, String state,
			String country, int pinNumber, User user) {
		super();
		this.houseNo = houseNo;
		this.plotNo = plotNo;
		this.villageName = villageName;
		this.city = city;
		this.dist = dist;
		this.state = state;
		this.country = country;
		this.pinNumber = pinNumber;
		this.user = user;
	}






	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}




	public String getHouseNo() {
		return houseNo;
	}


	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}


	public String getPlotNo() {
		return plotNo;
	}


	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}


	public String getVillageName() {
		return villageName;
	}


	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getDist() {
		return dist;
	}


	public void setDist(String dist) {
		this.dist = dist;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getPinNumber() {
		return pinNumber;
	}


	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
