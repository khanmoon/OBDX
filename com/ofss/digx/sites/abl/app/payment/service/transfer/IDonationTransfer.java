package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IDonationTransfer
{
  public abstract DonationTransferReadResponse read(SessionContext paramSessionContext, DonationTransferReadRequestDTO paramDonationTransferReadRequestDTO)
    throws Exception;
  
  public abstract DonationTransferCreateResponse create(SessionContext paramSessionContext, DonationTransferCreateRequestDTO paramDonationTransferCreateRequestDTO)
    throws Exception;
  
  public abstract DonationTransferResponse updateStatus(SessionContext paramSessionContext, DonationTransferUpdateRequestDTO paramDonationTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract DonationTransferListResponse list(SessionContext paramSessionContext, DonationTransferListRequestDTO paramDonationTransferListRequestDTO)
    throws Exception;
  
  public abstract DonationTransferListResponse lastPaymentList(SessionContext paramSessionContext, DonationTransferListRequestDTO paramDonationTransferListRequestDTO)
    throws Exception;
}
