package com.ofss.digx.sites.abl.app.payment.service.transfer.ext;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IMerchantTransferExtExecutor
{
  public abstract void preCreate(SessionContext paramSessionContext, MerchantTransferCreateRequestDTO paramMerchantTransferCreateRequestDTO)
    throws Exception;
  
  public abstract void postCreate(SessionContext paramSessionContext, MerchantTransferCreateRequestDTO paramMerchantTransferCreateRequestDTO, MerchantTransferCreateResponse paramMerchantTransferCreateResponse)
    throws Exception;
  
  public abstract void preRead(SessionContext paramSessionContext, MerchantTransferReadRequestDTO paramMerchantTransferReadRequestDTO)
    throws Exception;
  
  public abstract void postRead(SessionContext paramSessionContext, MerchantTransferReadRequestDTO paramMerchantTransferReadRequestDTO, MerchantTransferReadResponse paramMerchantTransferReadResponse)
    throws Exception;
  
  public abstract void preUpdateStatus(SessionContext paramSessionContext, MerchantTransferUpdateRequestDTO paramMerchantTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract void postUpdateStatus(SessionContext paramSessionContext, MerchantTransferUpdateRequestDTO paramMerchantTransferUpdateRequestDTO, MerchantTransferResponse paramMerchantTransferResponse)
    throws Exception;
}
