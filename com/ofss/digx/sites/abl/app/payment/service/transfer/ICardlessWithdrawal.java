package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface ICardlessWithdrawal
{
  public abstract CardlessWithdrawalReadResponse read(SessionContext paramSessionContext, CardlessWithdrawalReadRequestDTO paramMerchantTransferReadRequestDTO)
    throws Exception;
  
  public abstract CardlessWithdrawalCreateResponse create(SessionContext paramSessionContext, CardlessWithdrawalCreateRequestDTO paramMerchantTransferCreateRequestDTO)
    throws Exception;
  
  public abstract CardlessWithdrawalResponse updateStatus(SessionContext paramSessionContext, CardlessWithdrawalUpdateRequestDTO paramMerchantTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract CardlessWithdrawalListResponse list(SessionContext paramSessionContext, CardlessWithdrawalListRequestDTO paramMerchantTransferListRequestDTO)
    throws Exception;
  
  public abstract CardlessWithdrawalListResponse lastPaymentList(SessionContext paramSessionContext, CardlessWithdrawalListRequestDTO paramMerchantTransferListRequestDTO)
    throws Exception;
}
