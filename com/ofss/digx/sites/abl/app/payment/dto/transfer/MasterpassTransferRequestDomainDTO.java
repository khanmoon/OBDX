package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.fc.datatype.Date;
import java.io.PrintStream;

public class MasterpassTransferRequestDomainDTO
  extends PaymentDomainDTO
{
  private static final long serialVersionUID = -3178301363444959222L;
  private String creditAccountId;
  private String userReferenceNo;
  private String flgTransfer;
  private String narrative;
  private String templateName;
  private Date valueDate;
  private String txnTypeFlag;
  private String crediDebitFlag;
  private String transfer_reference;
  private String sender_first_name;
  private String sender_last_name;
  private String sender_address_city;
  private String sender_address_postalcode;
  private String sender_address_country;
  private String sender_address_line1;
  private String sender_address_state;
  private String recipient_first_name;
  private String recipient_last_name;
  private String recipient_address_line1;
  private String recipient_address_city;
  private String recipient_address_country;
  private String recipient_address_state;
  private String recipient_address_postalcode;  
  private String merchant_category_code;
  private String transaction_local_date_time;
  private String card_acceptor_name;
  private String sender_middle_name;
  
  
  public String getSender_middle_name() {
	return sender_middle_name;
}

public void setSender_middle_name(String sender_middle_name) {
	this.sender_middle_name = sender_middle_name;
}

public String getUserReferenceNo()
  {
    return this.userReferenceNo;
  }
  
  public void setUserReferenceNo(String userReferenceNo)
  {
    this.userReferenceNo = userReferenceNo;
  }
    
  public String getFlgTransfer()
  {
    return this.flgTransfer;
  }
  
  public void setFlgTransfer(String flgTransfer)
  {
    this.flgTransfer = flgTransfer;
  }
  
  public String getNarrative()
  {
    return this.narrative;
  }
  
  public void setNarrative(String narrative)
  {
    this.narrative = narrative;
  }
  
  public String getTemplateName()
  {
    return this.templateName;
  }
  
  public void setTemplateName(String templateName)
  {
    this.templateName = templateName;
  }
  
  public Date getValueDate()
  {
    return this.valueDate;
  }
  
  public void setValueDate(Date valueDate)
  {
    this.valueDate = valueDate;
  }
  
  public String getTxnTypeFlag()
  {
    return this.txnTypeFlag;
  }
  
  public void setTxnTypeFlag(String txnTypeFlag)
  {
    this.txnTypeFlag = txnTypeFlag;
  }
    
  public String getCrediDebitFlag()
  {
    return this.crediDebitFlag;
  }
  
  public void setCrediDebitFlag(String crediDebitFlag)
  {
    this.crediDebitFlag = crediDebitFlag;
  }
  
  public static long getSerialversionuid()
  {
    return -3178301363444959222L;
  }
  
  
  public String getCreditAccountId() {
	return creditAccountId;
}

public void setCreditAccountId(String creditAccountId) {
	this.creditAccountId = creditAccountId;
}

public String getTransfer_reference() {
	return transfer_reference;
}

public void setTransfer_reference(String transfer_reference) {
	this.transfer_reference = transfer_reference;
}

public String getSender_first_name() {
	return sender_first_name;
}

public void setSender_first_name(String sender_first_name) {
	this.sender_first_name = sender_first_name;
}

public String getSender_last_name() {
	return sender_last_name;
}

public void setSender_last_name(String sender_last_name) {
	this.sender_last_name = sender_last_name;
}

public String getSender_address_city() {
	return sender_address_city;
}

public void setSender_address_city(String sender_address_city) {
	this.sender_address_city = sender_address_city;
}

public String getSender_address_postalcode() {
	return sender_address_postalcode;
}

public void setSender_address_postalcode(String sender_address_postalcode) {
	this.sender_address_postalcode = sender_address_postalcode;
}

public String getSender_address_country() {
	return sender_address_country;
}

public void setSender_address_country(String sender_address_country) {
	this.sender_address_country = sender_address_country;
}

public String getSender_address_line1() {
	return sender_address_line1;
}

public void setSender_address_line1(String sender_address_line1) {
	this.sender_address_line1 = sender_address_line1;
}

public String getSender_address_state() {
	return sender_address_state;
}

public void setSender_address_state(String sender_address_state) {
	this.sender_address_state = sender_address_state;
}

public String getRecipient_first_name() {
	return recipient_first_name;
}

public void setRecipient_first_name(String recipient_first_name) {
	this.recipient_first_name = recipient_first_name;
}

public String getRecipient_last_name() {
	return recipient_last_name;
}

public void setRecipient_last_name(String recipient_last_name) {
	this.recipient_last_name = recipient_last_name;
}

public String getRecipient_address_line1() {
	return recipient_address_line1;
}

public void setRecipient_address_line1(String recipient_address_line1) {
	this.recipient_address_line1 = recipient_address_line1;
}

public String getRecipient_address_city() {
	return recipient_address_city;
}

public void setRecipient_address_city(String recipient_address_city) {
	this.recipient_address_city = recipient_address_city;
}

public String getRecipient_address_country() {
	return recipient_address_country;
}

public void setRecipient_address_country(String recipient_address_country) {
	this.recipient_address_country = recipient_address_country;
}

public String getRecipient_address_state() {
	return recipient_address_state;
}

public void setRecipient_address_state(String recipient_address_state) {
	this.recipient_address_state = recipient_address_state;
}

public String getRecipient_address_postalcode() {
	return recipient_address_postalcode;
}

public void setRecipient_address_postalcode(String recipient_address_postalcode) {
	this.recipient_address_postalcode = recipient_address_postalcode;
}

public String getMerchant_category_code() {
	return merchant_category_code;
}

public void setMerchant_category_code(String merchant_category_code) {
	this.merchant_category_code = merchant_category_code;
}

public String getTransaction_local_date_time() {
	return transaction_local_date_time;
}

public void setTransaction_local_date_time(String transaction_local_date_time) {
	this.transaction_local_date_time = transaction_local_date_time;
}

public String getCard_acceptor_name() {
	return card_acceptor_name;
}

public void setCard_acceptor_name(String card_acceptor_name) {
	this.card_acceptor_name = card_acceptor_name;
}

public String toString()
  {
    String output = "SrcAccount=" + this.creditAccountId  + "Amount=" + getPmtAmount();
    System.out.println(output);
    return output;
  }
}
