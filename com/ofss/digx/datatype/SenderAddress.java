package com.ofss.digx.datatype;

import java.io.Serializable;

public class SenderAddress
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String Line1;
  private String City;
  private String CountrySubdivision;
  private String PostalCode;
  private String Country;

  	public String getLine1() {
		return Line1;
	}
	public void setLine1(String line1) {
		Line1 = line1;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getCountrySubdivision() {
		return CountrySubdivision;
	}
	public void setCountrySubdivision(String countrySubdivision) {
		CountrySubdivision = countrySubdivision;
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
		return "SenderAddress [Line1=" + Line1 + ", City=" + City + ", CountrySubdivision=" + CountrySubdivision
				+ ", PostalCode=" + PostalCode + ", Country=" + Country + "]";
	}
  
  
  
}
