package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public abstract interface IMerchantTransfer
{
  public abstract MerchantTransferReadResponse read(SessionContext paramSessionContext, MerchantTransferReadRequestDTO paramMerchantTransferReadRequestDTO)
    throws Exception;
  
  public abstract MerchantTransferCreateResponse create(SessionContext paramSessionContext, MerchantTransferCreateRequestDTO paramMerchantTransferCreateRequestDTO)
    throws Exception;
  
  public abstract MerchantTransferResponse updateStatus(SessionContext paramSessionContext, MerchantTransferUpdateRequestDTO paramMerchantTransferUpdateRequestDTO)
    throws Exception;
  
  public abstract MerchantTransferListResponse list(SessionContext paramSessionContext, MerchantTransferListRequestDTO paramMerchantTransferListRequestDTO)
    throws Exception;
  
  public abstract MerchantTransferListResponse lastPaymentList(SessionContext paramSessionContext, MerchantTransferListRequestDTO paramMerchantTransferListRequestDTO)
    throws Exception;
}
