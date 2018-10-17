package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferDTO;
import javax.ws.rs.core.Response;

public abstract interface IMerchantTransfer
{
  public abstract Response read(String paramString);
  
  public abstract Response create(MerchantTransferDTO paramMerchantTransferDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract MerchantTransferAuthentication MerchantTransferAuthentication(String paramString);
}
