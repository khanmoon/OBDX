package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import javax.ws.rs.core.Response;

public abstract interface ICardlessWithdrawal
{
  public abstract Response read(String paramString);
  
  public abstract Response create(CardlessWithdrawalDTO paramMerchantTransferDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract CardlessWithdrawalAuthentication cardlessWithdrawalAuthentication(String paramString);
}
