package com.ofss.digx.sites.abl.domain.payment.entity.payee;

import com.ofss.fc.framework.domain.AbstractDomainObject;
import com.ofss.fc.framework.domain.IPersistenceObject;

public class MerchantPayeeDetails
  extends AbstractDomainObject
  implements IPersistenceObject
{
  private static final long serialVersionUID = 8862660842206749937L;
  private String billerId;
  private String consumerRefNo;
  
  public String getbillerId()
  {
    return this.billerId;
  }
  
  public void setbillerId(String billerId)
  {
    this.billerId = billerId;
  }
  
  public String getconsumerRefNo()
  {
    return this.consumerRefNo;
  }
  
  public void setconsumerRefNo(String consumerRefNo)
  {
    this.consumerRefNo = consumerRefNo;
  }
  
  protected void validate() {}
}
