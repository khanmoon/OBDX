package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface IDonationTransferRepositoryAdapter
  extends IRepositoryAdapter<DonationTransfer, PaymentKey>
{
  public abstract DonationTransfer read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(DonationTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract void delete(DonationTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract void update(DonationTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract List<DonationTransfer> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract DonationTransfer process(DonationTransfer paramDonationTransfer)
    throws Exception;
  
  public abstract List<DonationTransfer> lastPaymentList(Date paramDate)
    throws Exception;
}
