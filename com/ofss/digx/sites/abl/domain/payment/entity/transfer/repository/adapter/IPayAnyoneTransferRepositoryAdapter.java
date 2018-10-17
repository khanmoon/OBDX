package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface IPayAnyoneTransferRepositoryAdapter
  extends IRepositoryAdapter<PayAnyoneTransfer, PaymentKey>
{
  public abstract PayAnyoneTransfer read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(PayAnyoneTransfer paramPayAnyoneTransfer)
    throws Exception;
  
  public abstract void delete(PayAnyoneTransfer paramPayAnyoneTransfer)
    throws Exception;
  
  public abstract void update(PayAnyoneTransfer paramPayAnyoneTransfer)
    throws Exception;
  
  public abstract List<PayAnyoneTransfer> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract PayAnyoneTransfer process(PayAnyoneTransfer paramPayAnyoneTransfer)
    throws Exception;
  
  public abstract List<PayAnyoneTransfer> lastPaymentList(Date paramDate)
    throws Exception;
}
