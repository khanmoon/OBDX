package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.service.response.BaseResponseObject;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Logger;

public class ZakatDonationReadResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private static final String THIS_COMPONENT_NAME = ZakatDonationReadResponse.class.getName();
  private transient MultiEntityLogger formatter;
  
  public ZakatDonationReadResponse()
  {
    this.formatter = MultiEntityLogger.getUniqueInstance();
  }
  
  private transient Logger logger = MultiEntityLogger.getUniqueInstance()
    .getLogger(THIS_COMPONENT_NAME);
  private String paymentId;
  private ZakatDonationDTO transferDetails;
  private PaymentType paymentType;
  
  public String getPaymentId()
  {
    return this.paymentId;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public ZakatDonationDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(ZakatDonationDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public PaymentType getPaymentType()
  {
    return this.paymentType;
  }
  
  public void setPaymentType(PaymentType paymentType)
  {
    this.paymentType = paymentType;
  }
  
  public String toString()
  {
    return "ZakatDonationReadResponse [paymentId=" + this.paymentId + "transfer Details=" + this.transferDetails + " ]";
  }
}
