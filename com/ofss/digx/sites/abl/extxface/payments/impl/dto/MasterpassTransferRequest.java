package com.ofss.digx.sites.abl.extxface.payments.impl.dto;

import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.extxface.impl.dto.ExternalSystemRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="", propOrder={"srcAccount", "billerId", "amount"})
@XmlRootElement(name="MasterpassTransferRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class MasterpassTransferRequest
  extends ExternalSystemRequest
{
  @XmlElement(name="srcAccount", required=true)
  public String srcAccount;
  @XmlElement(name="billerId", required=true)
  public String billerId;
  @XmlElement(name="amount", required=true)
  private CurrencyAmount amount;
  
  public MasterpassTransferRequest(String interfaceId)
  {
    super(interfaceId);
  }
  
  public MasterpassTransferRequest() {}
  
  public String getSrcAccount()
  {
    return this.srcAccount;
  }
  
  public void setSrcAccount(String srcAccount)
  {
    this.srcAccount = srcAccount;
  }
  
  public String getBillerId()
  {
    return this.billerId;
  }
  
  public void setBillerId(String billerId)
  {
    this.billerId = billerId;
  }
  
  public CurrencyAmount getAmount()
  {
    return this.amount;
  }
  
  public void setAmount(CurrencyAmount amount)
  {
    this.amount = amount;
  }
}
