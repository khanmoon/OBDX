package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.fc.app.dto.validation.Mandatory;

public class DonationTransferDTO extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;
  @Mandatory(errorCode="DIGX_PY_0146")
  private String billerId;
  
  public DonationTransferDTO() {}
  
  public String getbillerId()
  {
    return billerId;
  }
  
  public void setbillerId(String billerId)
  {
    this.billerId = billerId;
  }
}
