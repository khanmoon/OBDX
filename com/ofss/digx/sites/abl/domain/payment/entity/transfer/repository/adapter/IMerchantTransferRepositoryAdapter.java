package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface IMerchantTransferRepositoryAdapter
  extends IRepositoryAdapter<MerchantTransferDomain, PaymentKey>
{
  public abstract MerchantTransferDomain read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(MerchantTransferDomain paramMerchantTransfer)
    throws Exception;
  
  public abstract void delete(MerchantTransferDomain paramMerchantTransfer)
    throws Exception;
  
  public abstract void update(MerchantTransferDomain paramMerchantTransfer)
    throws Exception;
  
  public abstract List<MerchantTransferDomain> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract MerchantTransferDomain process(MerchantTransferDomain paramMerchantTransfer)
    throws Exception;
  
  public abstract List<MerchantTransferDomain> lastPaymentList(Date paramDate)
    throws Exception;
}
