package com.ofss.digx.datatype;

import java.io.Serializable;

public class ReceiverIdentification
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String CountryCode;
  private String ExpirationDate;
  private String Number;
  private String Type;

  	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getExpirationDate() {
		return ExpirationDate;
	}
	public void setExpirationDate(String expirationDate) {
		ExpirationDate = expirationDate;
	}
	public String getNumber() {
		return Number;
	}
	public void setNumber(String number) {
		Number = number;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String toString() {
		return "ReceiverIdentification [CountryCode=" + CountryCode + ", ExpirationDate=" + ExpirationDate + ", Number="
				+ Number + ", Type=" + Type + "]";
	}
}
