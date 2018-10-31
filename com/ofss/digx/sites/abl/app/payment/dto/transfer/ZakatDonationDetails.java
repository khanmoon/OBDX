package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.app.payment.dto.PaymentDetails;
import com.ofss.digx.datatype.complex.Account;

public class ZakatDonationDetails
  extends PaymentDetails
{
  private static final long serialVersionUID = -5025245192478218954L;
  private PaymentDTO transferDetails;
  private Account creditAccountId;
  
  public PaymentDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(PaymentDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public Account getCreditAccountId()
  {
    return this.creditAccountId;
  }
  
  public void setCreditAccountId(Account creditAccountId)
  {
    this.creditAccountId = creditAccountId;
  }
}
