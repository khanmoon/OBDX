package com.ofss.digx.sites.abl.domain.payment.entity.payee;

import com.ofss.fc.framework.domain.AbstractDomainObject;
import com.ofss.fc.framework.domain.IPersistenceObject;



public class DonationPayeeDetails
  extends AbstractDomainObject
  implements IPersistenceObject
{
  private static final long serialVersionUID = 8862660842206749937L;
  private String billerId;
  
  public DonationPayeeDetails() {}
  
  public String getbillerId()
  {
    return billerId;
  }
  
  public void setbillerId(String billerId)
  {
    this.billerId = billerId;
  }
  
  protected void validate() {}
}
