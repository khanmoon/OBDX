package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.common.dto.DataTransferObject;

public class ZakatDonationUpdateRequestDTO
  extends DataTransferObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private String paymentId;
  
  public String getPaymentId()
  {
    return this.paymentId;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public String toString()
  {
    return "ZakatDonationUpdateRequestDTO [paymentId=" + this.paymentId + " ]";
  }
}
