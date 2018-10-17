package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;
import java.util.List;

public class CardlessWithdrawalListResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<CardlessWithdrawalDetails> transfers;
  
  public List<CardlessWithdrawalDetails> getTransfers()
  {
    return this.transfers;
  }
  
  public void setTransfers(List<CardlessWithdrawalDetails> merchantTransfers)
  {
    this.transfers = merchantTransfers;
  }
  
  public String toString()
  {
    return "CardlessWithdrawalListResponse [cardlessWithdrawal=" + this.transfers + "]";
  }
}
