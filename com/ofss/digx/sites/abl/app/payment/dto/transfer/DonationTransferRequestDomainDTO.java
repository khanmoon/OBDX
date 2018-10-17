package com.ofss.digx.sites.abl.app.payment.dto.transfer;

//import javax.xml.bind.annotation.XmlElement;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.extsystem.dto.AccountNoInputDTO;
import com.ofss.fc.datatype.Date;


public class DonationTransferRequestDomainDTO
  extends PaymentDomainDTO
{
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
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
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

    public String toString(){
    	String output= "SrcAccount="+srcAccount+"billerId="+billerId+"Amount="+getPmtAmount();
    	System.out.println(output);
    	return output;
    }	
    
private static final long serialVersionUID = -3178301363444959222L;
  private String srcAccount;	
  private String userReferenceNo;
  private String destCurrency;
  private String billerId;
  private double amount;
  private String flgTransfer;
  private String narrative;
  private String templateName;
  private Date valueDate;
  private String txnTypeFlag;
  private double creditAmount;
  private double debitAmount;
  private String crediDebitFlag;
  

}
