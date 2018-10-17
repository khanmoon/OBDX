package com.ofss.digx.sites.abl.app.payment.service.transfer.ext;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IDonationTransferExtExecutor
{
  public abstract void preCreate(SessionContext paramSessionContext, DonationTransferCreateRequestDTO paramDonationTransferCreateRequestDTO)
    throws Exception;
  
  public abstract void postCreate(SessionContext paramSessionContext, DonationTransferCreateRequestDTO paramDonationTransferCreateRequestDTO, DonationTransferCreateResponse paramDonationTransferCreateResponse)
    throws Exception;
  
  public abstract void preRead(SessionContext paramSessionContext, DonationTransferReadRequestDTO paramDonationTransferReadRequestDTO)
    throws Exception;
  
  public abstract void postRead(SessionContext paramSessionContext, DonationTransferReadRequestDTO paramDonationTransferReadRequestDTO, DonationTransferReadResponse paramDonationTransferReadResponse)
    throws Exception;
  
  public abstract void preUpdateStatus(SessionContext paramSessionContext, DonationTransferUpdateRequestDTO paramDonationTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract void postUpdateStatus(SessionContext paramSessionContext, DonationTransferUpdateRequestDTO paramDonationTransferUpdateRequestDTO, DonationTransferResponse paramDonationTransferResponse)
    throws Exception;
}
