package com.ofss.digx.sites.abl.app.payment.adapter;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;


public abstract interface IPaymentAdapter
{
  public abstract PayAnyoneTransferResponseDomainDTO processPayAnyoneTransfer(PayAnyoneTransferRequestDomainDTO paramPayAnyoneTransferRequestDomainDTO)
    throws Exception;
    
  public abstract DonationTransferResponseDomainDTO processDonationTransfer(DonationTransferRequestDomainDTO paramDonationTransferRequestDomainDTO)
    throws Exception;
  
  public abstract MerchantTransferResponseDomainDTO processMerchantTransfer(MerchantTransferRequestDomainDTO paramMerchantTransferRequestDomainDTO)
    throws Exception;
  
  public abstract CardlessWithdrawalResponseDomainDTO processCardlessWithdrawal(CardlessWithdrawalRequestDomainDTO cardlessWithdrawalReqDTO)
		    throws Exception;

}
