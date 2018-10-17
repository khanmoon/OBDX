package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.service.response.BaseResponseObject;

public class DonationTransferResponse extends BaseResponseObject {
  private static final long serialVersionUID = 2695633089263589147L;
  private String externalReferenceId;
  private boolean tokenAvailable;
  private boolean isTokenValid;
  
  public DonationTransferResponse() {}
  
  public String getExternalReferenceId() {
    return externalReferenceId;
  }
  
  public void setExternalReferenceId(String externalReferenceId)
  {
    this.externalReferenceId = externalReferenceId;
  }
  
  public boolean isTokenAvailable()
  {
    return tokenAvailable;
  }
  
  public void setTokenAvailable(boolean tokenAvailable)
  {
    this.tokenAvailable = tokenAvailable;
  }
  
  public boolean isTokenValid()
  {
    return isTokenValid;
  }
  
  public void setTokenValid(boolean isTokenValid)
  {
    this.isTokenValid = isTokenValid;
  }
  
  public String toString()
  {
    return "DonationTransferResponse [external Reference Id=" + externalReferenceId + "is token Available" + tokenAvailable + "isTokenValid" + isTokenValid + " ]";
  }
}
