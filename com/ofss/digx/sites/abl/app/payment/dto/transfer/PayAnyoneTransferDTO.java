package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.fc.app.dto.validation.Length;
import com.ofss.fc.app.dto.validation.Mandatory;

public class PayAnyoneTransferDTO
  extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;
  @Mandatory(errorCode="DIGX_PY_0146")
  @Length(min=1, max=80, errorCode="DIGX_PY_0153")
  private String payeeName;
  @Mandatory(errorCode="DIGX_PY_0146")
  @Length(min=13, max=13, errorCode="DIGX_PY_0153")
  private String payeeCNIC;
  @Mandatory(errorCode="DIGX_PY_0146")
  private String payeeAddress;
  private String payeeCity;
  private String payeeEmail;
  @Length(min=11, max=11, errorCode="DIGX_PY_0153")
  private String payeeMobile;
  @Mandatory(errorCode="DIGX_PY_0146")
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
