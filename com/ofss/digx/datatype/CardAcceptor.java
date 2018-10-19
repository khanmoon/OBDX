package com.ofss.digx.datatype;

import java.io.Serializable;

public class CardAcceptor
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String Name;
  private String City;
  private String State;
  private String PostalCode;
  private String Country;

  	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getPostalCode() {
		return PostalCode;
	}
	public void setPostalCode(String postalCode) {
		PostalCode = postalCode;
	}
	public String getCountry() {
		return Country;
	}
	public void setCountry(String country) {
		Country = country;
	}
	public String toString() {
		return "CardAcceptor [Name=" + Name + ", City=" + City + ", State=" + State + ", PostalCode=" + PostalCode
				+ ", Country=" + Country + "]";
	}

}
