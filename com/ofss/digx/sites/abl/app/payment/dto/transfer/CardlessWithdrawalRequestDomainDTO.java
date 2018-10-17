package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import javax.xml.bind.annotation.XmlElement;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.fc.datatype.Date;

public class CardlessWithdrawalRequestDomainDTO
  extends PaymentDomainDTO
{
  private static final long serialVersionUID = -3178301363444959222L;
  public String srcAccount;
  public String userReferenceNo;
  public String Tpin;
  public String WithdrawalRef;
  public String destCurrency;
  //public double amount;
  public String flgTransfer;
  public String narrative;
  public String templateName;
  public Date valueDate;
  public String txnTypeFlag;
  public double creditAmount;
  public double debitAmount;
  public String crediDebitFlag;
  
  public String getWithdrawalRef() {
	return WithdrawalRef;
}
public void setWithdrawalRef(String withdrawalRef) {
	WithdrawalRef = withdrawalRef;
}
  
public String getTpin() {
	return Tpin;
}
public void setTpin(String tpin) {
	Tpin = tpin;
}
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
public String getDestCurrency() {
	return destCurrency;
}
public void setDestCurrency(String destCurrency) {
	this.destCurrency = destCurrency;
}
/*
public double getAmount() {
	return amount;
}
public void setAmount(double amount) {
	this.amount = amount;
}
*/
public String getFlgTransfer() {
	return flgTransfer;
}
public void setFlgTransfer(String flgTransfer) {
	this.flgTransfer = flgTransfer;
}
public String getNarrative() {
	return narrative;
}
public void setNarrative(String narrative) {
	this.narrative = narrative;
}
public String getTemplateName() {
	return templateName;
}
public void setTemplateName(String templateName) {
	this.templateName = templateName;
}
public Date getValueDate() {
	return valueDate;
}
public void setValueDate(Date valueDate) {
	this.valueDate = valueDate;
}
public String getTxnTypeFlag() {
	return txnTypeFlag;
}
public void setTxnTypeFlag(String txnTypeFlag) {
	this.txnTypeFlag = txnTypeFlag;
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
public String getCrediDebitFlag() {
	return crediDebitFlag;
}
public void setCrediDebitFlag(String crediDebitFlag) {
	this.crediDebitFlag = crediDebitFlag;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
  
}
