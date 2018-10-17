package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class MerchantTransferCreateResponse
  extends BaseResponseObject
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
    return "MerchantTransferCreateResponse [paymentId=" + this.paymentId + " ]";
  }
}
