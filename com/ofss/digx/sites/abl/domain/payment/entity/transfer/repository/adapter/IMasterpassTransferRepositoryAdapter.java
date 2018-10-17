package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface IMasterpassTransferRepositoryAdapter
  extends IRepositoryAdapter<MasterpassTransfer, PaymentKey>
{
  public abstract MasterpassTransfer read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(MasterpassTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract void delete(MasterpassTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract void update(MasterpassTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract List<MasterpassTransfer> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract MasterpassTransfer process(MasterpassTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract List<MasterpassTransfer> lastPaymentList(Date paramDate)
    throws Exception;
}
