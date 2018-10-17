package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.fc.app.dto.validation.Mandatory;

public class MerchantTransferDTO
  extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;
  @Mandatory(errorCode="DIGX_PY_0146")
  private String billerId;
  @Mandatory(errorCode="DIGX_PY_0146")
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
}
