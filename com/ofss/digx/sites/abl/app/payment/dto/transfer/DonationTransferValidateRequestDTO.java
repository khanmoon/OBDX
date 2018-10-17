package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.fc.framework.domain.common.dto.DataTransferObject;

public class DonationTransferValidateRequestDTO extends DataTransferObject {
  private static final long serialVersionUID = -3265874471430497210L;
  private String paymentId;
  
  public DonationTransferValidateRequestDTO() {}
  
  public String getPaymentId() {
    return paymentId;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public String toString()
  {
    return "DonationTransferValidateRequestDTO [paymentId=" + paymentId + ", getPaymentId()=" + getPaymentId() + "]";
  }
}
