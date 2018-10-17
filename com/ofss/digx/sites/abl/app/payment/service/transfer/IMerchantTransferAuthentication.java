package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferValidateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IMerchantTransferAuthentication
{
  public abstract MerchantTransferResponse validateToken(SessionContext paramSessionContext, MerchantTransferValidateRequestDTO paramMerchantTransferValidateRequestDTO)
    throws Exception;
}
