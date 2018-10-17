package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.fc.datatype.Date;

public class PayAnyoneTransferRequestDomainDTO
  extends PaymentDomainDTO
{
    private static final long serialVersionUID = -3178301363444959222L;
    private String userReferenceNo;
    private String payeeName;
    private String payeeCNIC;
    private String payeeAddress;
    private String payeeCity;
    private String payeeEmail;
    private String payeeMobile;
    private String paymentType;
    private Date valueDate;
    private double creditAmount;
    private double debitAmount;
    private String remitterName;

	public String getUserReferenceNo() {
		return userReferenceNo;
	}
	public void setUserReferenceNo(String userReferenceNo) {
		this.userReferenceNo = userReferenceNo;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayeeCNIC() {
		return payeeCNIC;
	}
	public void setPayeeCNIC(String payeeCNIC) {
		this.payeeCNIC = payeeCNIC;
	}
	public String getPayeeAddress() {
		return payeeAddress;
	}
	public void setPayeeAddress(String payeeAddress) {
		this.payeeAddress = payeeAddress;
	}
	public String getPayeeCity() {
		return payeeCity;
	}
	public void setPayeeCity(String payeeCity) {
		this.payeeCity = payeeCity;
	}
	public String getPayeeEmail() {
		return payeeEmail;
	}
	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}
	public String getPayeeMobile() {
		return payeeMobile;
	}
	public void setPayeeMobile(String payeeMobile) {
		this.payeeMobile = payeeMobile;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public double getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(double creditAmount) {
		this.creditAmount = creditAmount;
	}
	public double getDebitAmount() {
		return debitAmount;
	}
	public void setDebitAmount(double debitAmount) {
		this.debitAmount = debitAmount;
	}
	public String getRemitterName() {
		return remitterName;
	}
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}
  
  
}
