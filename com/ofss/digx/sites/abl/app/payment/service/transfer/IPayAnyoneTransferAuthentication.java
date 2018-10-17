package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferValidateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IPayAnyoneTransferAuthentication
{
  public abstract PayAnyoneTransferResponse validateToken(SessionContext paramSessionContext, PayAnyoneTransferValidateRequestDTO paramPayAnyoneTransferValidateRequestDTO)
    throws Exception;
}
