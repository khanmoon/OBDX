package com.ofss.digx.sites.abl.app.payment.dto.payee;

import com.ofss.digx.app.payment.dto.payee.PayeeDTO;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;

public class PayAnyonePayeeDTO
  extends PayeeDTO
{
  private static final long serialVersionUID = 5840045050101028786L;
  private transient ITextMask masker = MaskingFactory.getInstance();
  private String payeeName;
  private String payeeCNIC;
  private String payeeAddress;
  private String payeeCity;
  private String payeeEmail;
  private String payeeMobile;
  private String paymentType;
  private String remitterName;
  
  public String getpayeeName()
  {
    return this.payeeName;
  }
  
  public void setpayeeName(String payeeName)
  {
    this.payeeName = payeeName;
  }
  
  public String getpayeeCNIC()
  {
    return this.payeeCNIC;
  }
  
  public void setpayeeCNIC(String payeeCNIC)
  {
    this.payeeCNIC = payeeCNIC;
  }
  
  public String getpayeeAddress()
  {
    return this.payeeAddress;
  }
  
  public void setpayeeAddress(String payeeAddress)
  {
    this.payeeAddress = payeeAddress;
  }
  
  public String getpayeeCity()
  {
    return this.payeeCity;
  }
  
  public void setpayeeCity(String payeeCity)
  {
    this.payeeCity = payeeCity;
  }
  
  public void setRemitterName(String remitterName)
  {
    this.remitterName = remitterName;
  }
  
  public String getRemitterName()
  {
    return this.remitterName;
  }
  
  public String getpayeeEmail()
  {
    return this.payeeEmail;
  }
  
  public void setpayeeEmail(String payeeEmail)
  {
    this.payeeEmail = payeeEmail;
  }
  
  public String getpayeeMobile()
  {
    return this.payeeMobile;
  }
  
  public void setpayeeMobile(String payeeMobile)
  {
    this.payeeMobile = payeeMobile;
  }
  
  public String getpaymentType()
  {
    return this.paymentType;
  }
  
  public void setpaymentType(String paymentType)
  {
    this.paymentType = paymentType;
  }
  
  public String toString()
  {
    return "PayAnyoneTransferDTO [payee Name=" + this.payeeName + " ]";
  }
}
