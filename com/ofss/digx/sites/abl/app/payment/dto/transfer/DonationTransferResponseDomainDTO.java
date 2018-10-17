package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class DonationTransferResponseDomainDTO extends BaseResponseObject {
  private static final long serialVersionUID = 3677078328255886928L;
  private String hostReference;
  
  public DonationTransferResponseDomainDTO() {}
  
  public String getHostReference() {
    return hostReference;
  }
  
  public void setHostReference(String hostReference)
  {
    this.hostReference = hostReference;
  }
}
