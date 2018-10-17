package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.app.payment.dto.PaymentDetails;
import com.ofss.digx.sites.abl.app.payment.dto.payee.CardlessWithdrawalPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MerchantPayeeDTO;

public class CardlessWithdrawalDetails
  extends PaymentDetails
{
  private static final long serialVersionUID = -5025245192478218954L;
  private PaymentDTO transferDetails;
  private CardlessWithdrawalPayeeDTO payeeDetails;
  
  public PaymentDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(PaymentDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public CardlessWithdrawalPayeeDTO getPayeeDetails()
  {
    return this.payeeDetails;
  }
  
  public void setPayeeDetails(CardlessWithdrawalPayeeDTO payeeDetails)
  {
    this.payeeDetails = payeeDetails;
  }
}
