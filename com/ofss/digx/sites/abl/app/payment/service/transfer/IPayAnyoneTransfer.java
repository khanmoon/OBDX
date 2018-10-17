package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IPayAnyoneTransfer
{
  public abstract PayAnyoneTransferReadResponse read(SessionContext paramSessionContext, PayAnyoneTransferReadRequestDTO paramPayAnyoneTransferReadRequestDTO)
    throws Exception;
  
  public abstract PayAnyoneTransferCreateResponse create(SessionContext paramSessionContext, PayAnyoneTransferCreateRequestDTO paramPayAnyoneTransferCreateRequestDTO)
    throws Exception;
  
  public abstract PayAnyoneTransferResponse updateStatus(SessionContext paramSessionContext, PayAnyoneTransferUpdateRequestDTO paramPayAnyoneTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract PayAnyoneTransferListResponse list(SessionContext paramSessionContext, PayAnyoneTransferListRequestDTO paramPayAnyoneTransferListRequestDTO)
    throws Exception;
  
  public abstract PayAnyoneTransferListResponse lastPaymentList(SessionContext paramSessionContext, PayAnyoneTransferListRequestDTO paramPayAnyoneTransferListRequestDTO)
    throws Exception;
}