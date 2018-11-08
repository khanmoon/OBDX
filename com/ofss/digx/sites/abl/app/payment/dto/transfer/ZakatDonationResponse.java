package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class ZakatDonationResponse
  extends BaseResponseObject
{
  private static final long serialVersionUID = 2695633089263589147L;
  private String externalReferenceId;
  private boolean tokenAvailable;
  private boolean isTokenValid;
  
  public String getExternalReferenceId()
  {
    return this.externalReferenceId;
  }
  
  public void setExternalReferenceId(String externalReferenceId)
  {
    this.externalReferenceId = externalReferenceId;
  }
  
  public boolean isTokenAvailable()
  {
    return this.tokenAvailable;
  }
  
  public void setTokenAvailable(boolean tokenAvailable)
  {
    this.tokenAvailable = tokenAvailable;
  }
  
  public boolean isTokenValid()
  {
    return this.isTokenValid;
  }
  
  public void setTokenValid(boolean isTokenValid)
  {
    this.isTokenValid = isTokenValid;
  }
  
  public String toString()
  {
    return "ZakatDonationResponse [external Reference Id=" + this.externalReferenceId + "is token Available" + this.tokenAvailable + "isTokenValid" + this.isTokenValid + " ]";
  }
}
