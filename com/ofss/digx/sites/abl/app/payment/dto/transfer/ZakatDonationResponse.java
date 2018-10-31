package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class ZakatDonationResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 2695633089263589147L;
  private String externalReferenceId;
  
  public String getExternalReferenceId()
  {
    return this.externalReferenceId;
  }
  
  public void setExternalReferenceId(String externalReferenceId)
  {
    this.externalReferenceId = externalReferenceId;
  }
  
  public String toString()
  {
    return "ZakatDonationResponse [external Reference Id=" + this.externalReferenceId + " ]";
  }
}
