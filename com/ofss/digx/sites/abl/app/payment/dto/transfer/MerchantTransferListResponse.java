package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;
import java.util.List;

public class MerchantTransferListResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<MerchantTransferDetails> transfers;
  
  public List<MerchantTransferDetails> getTransfers()
  {
    return this.transfers;
  }
  
  public void setTransfers(List<MerchantTransferDetails> merchantTransfers)
  {
    this.transfers = merchantTransfers;
  }
  
  public String toString()
  {
    return "MerchantTransferListResponse [merchantTransfers=" + this.transfers + "]";
  }
}
