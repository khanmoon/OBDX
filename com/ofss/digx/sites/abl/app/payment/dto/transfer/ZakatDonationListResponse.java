package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;
import java.util.List;

public class ZakatDonationListResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 4093991239866712251L;
  private List<ZakatDonationDetails> transfers;
  
  public List<ZakatDonationDetails> getTransfers()
  {
    return this.transfers;
  }
  
  public void setTransfers(List<ZakatDonationDetails> ZakatDonations)
  {
    this.transfers = ZakatDonations;
  }
  
  public String toString()
  {
    return "ZakatDonationListResponse [ZakatDonations=" + this.transfers + "]";
  }
}
