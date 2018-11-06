package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface IZakatDonationRepositoryAdapter
  extends IRepositoryAdapter<ZakatDonation, PaymentKey>
{
  public abstract ZakatDonation read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(ZakatDonation paramZakatDonation)
    throws Exception;
  
  public abstract void delete(ZakatDonation paramZakatDonation)
    throws Exception;
  
  public abstract void update(ZakatDonation paramZakatDonation)
    throws Exception;
  
  public abstract List<ZakatDonation> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract ZakatDonation process(ZakatDonation paramZakatDonation)
    throws Exception;
  
  public abstract List<ZakatDonation> lastPaymentList(Date paramDate)
    throws Exception;
  
  public abstract ZakatDonation listCompanyDetails()
		    throws Exception;
}
