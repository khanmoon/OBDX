package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDTO;
import javax.ws.rs.core.Response;

public abstract interface IPayAnyoneTransfer
{
  public abstract Response read(String paramString);
  
  public abstract Response create(PayAnyoneTransferDTO paramPayAnyoneTransferDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract PayAnyoneTransferAuthentication PayAnyoneTransferAuthentication(String paramString);
}