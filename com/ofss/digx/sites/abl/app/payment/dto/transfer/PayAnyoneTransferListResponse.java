package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;
import java.util.List;

public class PayAnyoneTransferListResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<PayAnyoneTransferDetails> transfers;
  
  public List<PayAnyoneTransferDetails> getTransfers()
  {
    return this.transfers;
  }
  
  public void setTransfers(List<PayAnyoneTransferDetails> payAnyoneTransfers)
  {
    this.transfers = payAnyoneTransfers;
  }
  
  public String toString()
  {
    return "PayAnyoneTransferListResponse [payAnyoneTransfers=" + this.transfers + "]";
  }
}