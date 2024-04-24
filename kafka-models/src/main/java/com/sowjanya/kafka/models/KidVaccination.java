package com.sowjanya.kafka.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter 
@Setter 
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@Jacksonized
public class KidVaccination {
	
	private String vaccineId;
	private String vaccineName;
	private String kidName;
	private String dateOfBirth;
	private String dateGiven;
	
//	
//	public String getVaccineId() {
//		return vaccineId;
//	}
//	public void setVaccineId(String vaccineId) {
//		this.vaccineId = vaccineId;
//	}
//	public String getVaccineName() {
//		return vaccineName;
//	}
//	public void setVaccineName(String vaccineName) {
//		this.vaccineName = vaccineName;
//	}
//	public String getKidName() {
//		return kidName;
//	}
//	public void setKidName(String kidName) {
//		this.kidName = kidName;
//	}
//	public String getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(String dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}
//	public String getDateGiven() {
//		return dateGiven;
//	}
//	public void setDateGiven(String dateGiven) {
//		this.dateGiven = dateGiven;
//	}

}
