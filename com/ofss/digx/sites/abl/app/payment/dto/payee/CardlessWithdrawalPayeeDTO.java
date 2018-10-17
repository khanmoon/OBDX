package com.ofss.digx.sites.abl.app.payment.dto.payee;

import com.ofss.digx.app.payment.dto.payee.PayeeDTO;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;

public class CardlessWithdrawalPayeeDTO
  extends PayeeDTO
{
  private static final long serialVersionUID = 5840045050101028786L;
  private transient ITextMask masker = MaskingFactory.getInstance();
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



public void setWithdrawalRef(String referenceNo) {
	WithdrawalRef = referenceNo;
}



public String toString()
  {
    return "CardlessWithdrawalDTO [Tpin=" + this.Tpin + " ]";
  }
}
