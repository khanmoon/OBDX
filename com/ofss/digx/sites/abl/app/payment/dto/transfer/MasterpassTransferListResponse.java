package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;
import java.util.List;

public class MasterpassTransferListResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<MasterpassTransferDetails> transfers;
  
  public List<MasterpassTransferDetails> getTransfers()
  {
    return this.transfers;
  }
  
  public void setTransfers(List<MasterpassTransferDetails> donationTransfers)
  {
    this.transfers = donationTransfers;
  }
  
  public String toString()
  {
    return "DonationTransferListResponse [donationTransfers=" + this.transfers + "]";
  }
}
