package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferValidateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IMasterpassTransferAuthentication
{
  public abstract MasterpassTransferResponse validateToken(SessionContext paramSessionContext, MasterpassTransferValidateRequestDTO paramDonationTransferValidateRequestDTO)
    throws Exception;
}
