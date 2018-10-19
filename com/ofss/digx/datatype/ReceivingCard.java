package com.ofss.digx.datatype;

import java.io.Serializable;

public class ReceivingCard
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String AccountNumber;

  	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}
	public String toString() {
		return "FundingCard [AccountNumber=" + AccountNumber + "]";
	}
	
}
