package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import java.util.List;

public class DonationTransferListResponse extends com.ofss.digx.service.response.BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<DonationTransferDetails> transfers;
  
  public DonationTransferListResponse() {}
  
  public List<DonationTransferDetails> getTransfers()
  {
    return transfers;
  }
  
  public void setTransfers(List<DonationTransferDetails> donationTransfers)
  {
    transfers = donationTransfers;
  }
  
  public String toString()
  {
    return "DonationTransferListResponse [donationTransfers=" + transfers + "]";
  }
}
