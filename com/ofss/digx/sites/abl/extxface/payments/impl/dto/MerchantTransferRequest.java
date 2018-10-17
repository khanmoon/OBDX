package com.ofss.digx.sites.abl.extxface.payments.impl.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ofss.fc.datatype.Date;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.extxface.impl.dto.ExternalSystemRequest;

@XmlType(name="", propOrder={"srcAccount","userReferenceNo","billerId","consumerRefNo","amount"})
@XmlRootElement(name="MerchantTransferRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MerchantTransferRequest extends ExternalSystemRequest {

	public MerchantTransferRequest(){}
	
	public MerchantTransferRequest(String interfaceId)
	{
	    super(interfaceId);
	}
		
  @XmlElement(name="srcAccount", required=true)
  public String srcAccount;
  @XmlElement(name="userReferenceNo", required=true)
  public String userReferenceNo;
  @XmlElement(name="billerId", required=true)
  public String billerId;
  @XmlElement(name="consumerRefNo", required=true)
  public String consumerRefNo;
  @XmlElement(name="amount", required=true)
  public CurrencyAmount amount;
  
  public String getSrcAccount() {
	return srcAccount;
}
public void setSrcAccount(String srcAccount) {
	this.srcAccount = srcAccount;
}
public String getUserReferenceNo() {
	return userReferenceNo;
}
public void setUserReferenceNo(String userReferenceNo) {
	this.userReferenceNo = userReferenceNo;
}

public String getBillerId() {
	return billerId;
}

public void setBillerId(String billerId) {
	this.billerId = billerId;
}

public String getConsumerRefNo() {
	return consumerRefNo;
}

public void setConsumerRefNo(String consumerRefNo) {
	this.consumerRefNo = consumerRefNo;
}
 
}
