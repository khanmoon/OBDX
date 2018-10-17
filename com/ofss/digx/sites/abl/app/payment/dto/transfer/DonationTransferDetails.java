package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.app.payment.dto.PaymentDetails;
import com.ofss.digx.sites.abl.app.payment.dto.payee.DonationPayeeDTO;

public class DonationTransferDetails
  extends PaymentDetails
{
  private static final long serialVersionUID = -5025245192478218954L;
  private PaymentDTO transferDetails;
  private DonationPayeeDTO payeeDetails;
  
  public PaymentDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(PaymentDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public DonationPayeeDTO getPayeeDetails()
  {
    return this.payeeDetails;
  }
  
  public void setPayeeDetails(DonationPayeeDTO payeeDetails)
  {
    this.payeeDetails = payeeDetails;
  }
}
