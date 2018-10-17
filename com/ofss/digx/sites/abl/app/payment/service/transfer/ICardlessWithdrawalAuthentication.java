package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalValidateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface ICardlessWithdrawalAuthentication
{
  public abstract CardlessWithdrawalResponse validateToken(SessionContext paramSessionContext, CardlessWithdrawalValidateRequestDTO paramMerchantTransferValidateRequestDTO)
    throws Exception;
}
