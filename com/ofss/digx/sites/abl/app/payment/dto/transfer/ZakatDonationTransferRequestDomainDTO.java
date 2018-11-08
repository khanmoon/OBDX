package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.digx.extxface.goal.product.impl.dto.Amount;
import com.ofss.fc.datatype.Date;

public class ZakatDonationTransferRequestDomainDTO
extends PaymentDomainDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3178301363444959222L;
	private String debitAccountId;	
	private String creditAccountId;
	  private String userReferenceNo;
	  private Date valueDate;
	  private Amount amount;
	  
	  public void seteDebitAccountId(String debitAccountId) {
		  this.debitAccountId = debitAccountId;
	  }
	  
	  public void setCreditAccountId(String creditAccountId) {
		  this.creditAccountId = creditAccountId;
	  }
	  
	  public void setUserReferenceNo(String userReferenceNo) {
		  this.userReferenceNo = userReferenceNo;
	  }
	  
	  public void setValueDate(Date valueDate) {
		  this.valueDate = valueDate;
	  }
	  
	  
	  public void setAmount(Amount amount) {
		  this.amount = amount;
	  }
	  
	  
	  public String getDebitAccountId() {
		  return this.debitAccountId;
	  }
	  
	  public String getCreditAccountId() {
		  return this.creditAccountId;
	  }
	  
	  public String getUserReferenceNo() {
		  return this.userReferenceNo;
	  }
	  
	  public Date getValueDate() {
		  return this.valueDate;
	  }
	  
	  public Amount getAmount() {
		  return this.amount;
	  }
}
