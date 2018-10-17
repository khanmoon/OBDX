package com.ofss.digx.sites.abl.domain.payment.entity.payee;

import com.ofss.fc.framework.domain.AbstractDomainObject;
import com.ofss.fc.framework.domain.IPersistenceObject;

public class PayAnyonePayeeDetails
  extends AbstractDomainObject
  implements IPersistenceObject
{
  private static final long serialVersionUID = 8862660842206749937L;
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
  
  public void setrRemitterName(String remitterName)
  {
    this.remitterName = remitterName;
  }
  
  public String getRemitterName()
  {
    return this.remitterName;
  }
  
  public String getpayeeCity()
  {
    return this.payeeCity;
  }
  
  public void setpayeeCity(String payeeCity)
  {
    this.payeeCity = payeeCity;
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
  
  protected void validate() {}
}
