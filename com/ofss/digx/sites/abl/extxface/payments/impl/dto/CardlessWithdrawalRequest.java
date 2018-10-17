package com.ofss.digx.sites.abl.extxface.payments.impl.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ofss.fc.datatype.Date;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.extxface.impl.dto.ExternalSystemRequest;

@XmlType(name="", propOrder={"srcAccount","Tpin","amount","WithdrawalRef"})
@XmlRootElement(name="CardlessWithdrawalRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardlessWithdrawalRequest extends ExternalSystemRequest {

	public CardlessWithdrawalRequest(){}
	
	public CardlessWithdrawalRequest(String interfaceId)
	{
	    super(interfaceId);
	}
		
  @XmlElement(name="srcAccount", required=true)
  public String srcAccount;
  @XmlElement(name="Tpin", required=true)
  public String Tpin;
  @XmlElement(name="amount", required=true)
  public CurrencyAmount amount;
  @XmlElement(name="WithdrawalRef", required=true)
  public String WithdrawalRef;
public String getSrcAccount() {
	return srcAccount;
}

public void setSrcAccount(String srcAccount) {
	this.srcAccount = srcAccount;
}

public String getTpin() {
	return Tpin;
}

public void setTpin(String tpin) {
	Tpin = tpin;
}

public CurrencyAmount getAmount() {
	return amount;
}

public void setAmount(CurrencyAmount amount) {
	this.amount = amount;
}

public String getWithdrawalRef() {
	return WithdrawalRef;
}

public void setWithdrawalRef(String withdrawalRef) {
	WithdrawalRef = withdrawalRef;
}
  
}
