package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.fc.app.dto.validation.Mandatory;

public class CardlessWithdrawalDTO
  extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;
  @Mandatory(errorCode="DIGX_PY_0146")
  private String Tpin;
  @Mandatory(errorCode="DIGX_PY_0146")
  public String WithdrawalRef;
public String getTpin() {
	return Tpin;
}
public void setTpin(String tpin) {
	Tpin = tpin;
}
public String getWithdrawalRef() {
	return WithdrawalRef;
}
public void setWithdrawalRef(String referenceNo) {
	WithdrawalRef = referenceNo;
}
  
}
