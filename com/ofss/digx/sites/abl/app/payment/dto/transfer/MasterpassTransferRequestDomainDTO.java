package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDomainDTO;
import com.ofss.fc.datatype.Date;
import java.io.PrintStream;

public class MasterpassTransferRequestDomainDTO
  extends PaymentDomainDTO
{
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
  
  public String getSrcAccount()
  {
    return this.srcAccount;
  }
  
  public void setSrcAccount(String srcAccount)
  {
    this.srcAccount = srcAccount;
  }
  
  public String getUserReferenceNo()
  {
    return this.userReferenceNo;
  }
  
  public void setUserReferenceNo(String userReferenceNo)
  {
    this.userReferenceNo = userReferenceNo;
  }
  
  public String getDestCurrency()
  {
    return this.destCurrency;
  }
  
  public void setDestCurrency(String destCurrency)
  {
    this.destCurrency = destCurrency;
  }
  
  public String getBillerId()
  {
    return this.billerId;
  }
  
  public void setBillerId(String billerId)
  {
    this.billerId = billerId;
  }
  
  public double getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(double amount)
  {
    this.amount = amount;
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
  
  public double getCreditAmount()
  {
    return this.creditAmount;
  }
  
  public void setCreditAmount(double creditAmount)
  {
    this.creditAmount = creditAmount;
  }
  
  public double getDebitAmount()
  {
    return this.debitAmount;
  }
  
  public void setDebitAmount(double debitAmount)
  {
    this.debitAmount = debitAmount;
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
  
  public String toString()
  {
    String output = "SrcAccount=" + this.srcAccount + "billerId=" + this.billerId + "Amount=" + getPmtAmount();
    System.out.println(output);
    return output;
  }
}
