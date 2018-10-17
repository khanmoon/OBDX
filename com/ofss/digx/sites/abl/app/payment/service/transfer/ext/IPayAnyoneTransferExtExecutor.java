package com.ofss.digx.sites.abl.app.payment.service.transfer.ext;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IPayAnyoneTransferExtExecutor
{
  public abstract void preCreate(SessionContext paramSessionContext, PayAnyoneTransferCreateRequestDTO paramPayAnyoneTransferCreateRequestDTO)
    throws Exception;
  
  public abstract void postCreate(SessionContext paramSessionContext, PayAnyoneTransferCreateRequestDTO paramPayAnyoneTransferCreateRequestDTO, PayAnyoneTransferCreateResponse paramPayAnyoneTransferCreateResponse)
    throws Exception;  
  
  public abstract void preRead(SessionContext paramSessionContext, PayAnyoneTransferReadRequestDTO paramPayAnyoneTransferReadRequestDTO)
    throws Exception;
  
  public abstract void postRead(SessionContext paramSessionContext, PayAnyoneTransferReadRequestDTO paramPayAnyoneTransferReadRequestDTO, PayAnyoneTransferReadResponse paramPayAnyoneTransferReadResponse)
    throws Exception;
  
  public abstract void preUpdateStatus(SessionContext paramSessionContext, PayAnyoneTransferUpdateRequestDTO paramPayAnyoneTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract void postUpdateStatus(SessionContext paramSessionContext, PayAnyoneTransferUpdateRequestDTO paramPayAnyoneTransferUpdateRequestDTO, PayAnyoneTransferResponse paramPayAnyoneTransferResponse)
    throws Exception;
}

