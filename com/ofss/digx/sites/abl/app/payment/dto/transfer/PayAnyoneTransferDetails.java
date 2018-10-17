package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.app.payment.dto.PaymentDetails;
import com.ofss.digx.sites.abl.app.payment.dto.payee.PayAnyonePayeeDTO;

public class PayAnyoneTransferDetails
  extends PaymentDetails
{
  private static final long serialVersionUID = -5025245192478218954L;
  private PaymentDTO transferDetails;
  private PayAnyonePayeeDTO payeeDetails;
  
  public PaymentDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(PaymentDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public PayAnyonePayeeDTO getPayeeDetails()
  {
    return this.payeeDetails;
  }
  
  public void setPayeeDetails(PayAnyonePayeeDTO payeeDetails)
  {
    this.payeeDetails = payeeDetails;
  }
}