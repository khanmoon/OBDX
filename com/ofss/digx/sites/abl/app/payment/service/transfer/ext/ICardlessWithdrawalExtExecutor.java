package com.ofss.digx.sites.abl.app.payment.service.transfer.ext;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface ICardlessWithdrawalExtExecutor
{
  public abstract void preCreate(SessionContext paramSessionContext, CardlessWithdrawalCreateRequestDTO paramMerchantTransferCreateRequestDTO)
    throws Exception;
  
  public abstract void postCreate(SessionContext paramSessionContext, CardlessWithdrawalCreateRequestDTO paramMerchantTransferCreateRequestDTO, CardlessWithdrawalCreateResponse paramMerchantTransferCreateResponse)
    throws Exception;
  
  public abstract void preRead(SessionContext paramSessionContext, CardlessWithdrawalReadRequestDTO paramMerchantTransferReadRequestDTO)
    throws Exception;
  
  public abstract void postRead(SessionContext paramSessionContext, CardlessWithdrawalReadRequestDTO paramMerchantTransferReadRequestDTO, CardlessWithdrawalReadResponse paramMerchantTransferReadResponse)
    throws Exception;
  
  public abstract void preUpdateStatus(SessionContext paramSessionContext, CardlessWithdrawalUpdateRequestDTO paramMerchantTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract void postUpdateStatus(SessionContext paramSessionContext, CardlessWithdrawalUpdateRequestDTO paramMerchantTransferUpdateRequestDTO, CardlessWithdrawalResponse paramMerchantTransferResponse)
    throws Exception;
}
