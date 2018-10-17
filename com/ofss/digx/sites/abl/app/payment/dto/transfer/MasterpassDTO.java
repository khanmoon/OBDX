//package com.ofss.digx.sites.abl.app.payment.dto.transfer;
//
//import com.ofss.digx.app.payment.dto.PaymentDTO;
//import com.ofss.fc.app.dto.validation.Mandatory;
//
//public class MasterpassDTO extends PaymentDTO
//{
//  private static final long serialVersionUID = -5025245192478218954L;
//  @Mandatory(errorCode="DIGX_PY_0146")
//  private String partner_id;
//  private String transfer_reference;
//  private String payment_type;
//  private String transfer_amount_value;
//  private String transfer_amount_currency;
//  private String payment_origination_country;
//  private String sender_account_uri;
//  private String digital_account_reference_number;
//  private String sender_first_name;
//  private String sender_middle_name;
//  private String sender_last_name;
//  private String sender_address_line1;
//  private String sender_address_line2;
//  private String sender_address_city;
//  private String sender_address_country_subdivision;
//  private String sender_address_postal_code;
//  private String sender_address_country;
//  private String sender_phone;
//  private String sender_email;
//  private String recipient_account_uri;
//  private String recipient_first_name;
//  private String recipient_middle_name;
//  private String recipient_last_name;
//  private String recipient_address_line1;
//  private String recipient_address_line2;
//  private String recipient_address_city;
//  private String recipient_address_country_subdivision;
//  private String recipient_address_postal_code;
//  private String recipient_address_country;
//  private String recipient_phone;
//  private String recipient_email;
//  private String recipient_merchant_category_code;
//  private String reconciliation_data_custom_field_0_name;
//  private String reconciliation_data_custom_field_0_value;
//  private String reconciliation_data_custom_field_1_name;
//  private String reconciliation_data_custom_field_1_value;
//  private String reconciliation_data_custom_field_2_name;
//  private String reconciliation_data_custom_field_2_value;
//  private String transaction_local_date_time;
//  private String participant_card_acceptor_id;
//  private String participant_card_acceptor_name;
//  private String participation_id;
//  private String additional_message;
//  private String mastercard_assigned_id;
//  
//  public MasterpassDTO() {}
//  
//  public String getbillerId()
//  {
//    return mastercard_assigned_id;
//  }
//  
//  public void setbillerId(String mastercard_assigned_id)
//  {
//    this.mastercard_assigned_id = mastercard_assigned_id;
//  }
//}

package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.fc.app.dto.validation.Mandatory;
import com.ofss.fc.datatype.PostalAddress;

public class MasterpassDTO extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;

  private String partnerId;
  private String transfer_reference;
  private String sender_first_name;
  private String sender_last_name;
  private String sender_address_city;
  private String sender_address_postalcode;
  private String sender_address_country;
  private String sender_address_line1;
  private String sender_address_state;
  private String creditAccountId;
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
  
  public MasterpassDTO() {}

public String getPartnerId() {
	return partnerId;
}

public void setPartnerId(String partnerId) {
	this.partnerId = partnerId;
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


public String getCreditAccountId() {
	return creditAccountId;
}

public void setCreditAccountId(String creditAccountId) {
	this.creditAccountId = creditAccountId;
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


  
}

