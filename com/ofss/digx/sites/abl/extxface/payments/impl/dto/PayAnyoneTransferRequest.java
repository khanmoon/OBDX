package com.ofss.digx.sites.abl.extxface.payments.impl.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.extxface.impl.dto.ExternalSystemRequest;
import com.ofss.fc.datatype.Date;

@XmlType(name="", propOrder={"srcAccount", "amount", "valueDate", "payeeName", "payeeCNIC", "payeeAddress", "payeeCity", 
		"payeeEmail", "payeeMobile", "paymentType", "remitterName"})
@XmlRootElement(name="PayAnyoneTransferRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayAnyoneTransferRequest extends ExternalSystemRequest 
{
    @XmlElement(name="srcAccount", required=true)
	public String srcAccount;
    @XmlElement(name="amount", required=true)
    public CurrencyAmount amount;
    @XmlElement(name="valueDate", required=true)
	public Date valueDate;
	@XmlElement(name="payeeName", required=true)
	public String payeeName;
    @XmlElement(name="payeeCNIC", required=true)
	public String payeeCNIC;
    @XmlElement(name="payeeAddress", required=true)
	public String payeeAddress;
    @XmlElement(name="payeeCity", required=true)
	public String payeeCity;
    @XmlElement(name="payeeEmail", required=true)
	public String payeeEmail;
    @XmlElement(name="payeeMobile", required=true)
	public String payeeMobile;
    @XmlElement(name="paymentType", required=true)
	public String paymentType;
    @XmlElement(name="remitterName", required=true)
	public String remitterName;

	public PayAnyoneTransferRequest() 
	{} 
	
	public PayAnyoneTransferRequest(String interfaceId)
	{
	  super(interfaceId);
	}

    public String getSrcAccount() {
    	return srcAccount;
    }
}
