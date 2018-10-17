package com.ofss.digx.sites.abl.domain.payment.entity.payee;

import com.ofss.fc.framework.domain.AbstractDomainObject;
import com.ofss.fc.framework.domain.IPersistenceObject;

public class CardlessWithdrawalPayeeDetails
  extends AbstractDomainObject
  implements IPersistenceObject
{
  private static final long serialVersionUID = 8862660842206749937L;
  private String Tpin;
  private String WithdrawalRef;

  
  public String getTpin() {
	return Tpin;
}



public void setTpin(String tpin) {
	Tpin = tpin;
}



public String getWithdrawalRef() {
	return WithdrawalRef;
}



public void setWithdrawalRef(String withdrawalRef) {
	WithdrawalRef = withdrawalRef;
}



protected void validate() {}
}
