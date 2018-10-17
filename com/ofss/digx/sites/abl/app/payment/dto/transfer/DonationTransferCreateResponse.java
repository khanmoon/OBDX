package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class DonationTransferCreateResponse extends BaseResponseObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private String paymentId;
  
  public DonationTransferCreateResponse() {}
  
  public String getPaymentId() {
    return paymentId;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public String toString()
  {
    return "DonationTransferCreateResponse [paymentId=" + paymentId + " ]";
  }
}
