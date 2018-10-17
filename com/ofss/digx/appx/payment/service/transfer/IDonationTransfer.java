package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferDTO;
import javax.ws.rs.core.Response;

public abstract interface IDonationTransfer
{
  public abstract Response read(String paramString);
  
  public abstract Response create(DonationTransferDTO paramDonationTransferDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract DonationTransferAuthentication DonationTransferAuthentication(String paramString);
}
