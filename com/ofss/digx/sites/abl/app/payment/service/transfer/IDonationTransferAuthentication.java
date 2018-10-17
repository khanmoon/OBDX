package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferValidateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IDonationTransferAuthentication
{
  public abstract DonationTransferResponse validateToken(SessionContext paramSessionContext, DonationTransferValidateRequestDTO paramDonationTransferValidateRequestDTO)
    throws Exception;
}
