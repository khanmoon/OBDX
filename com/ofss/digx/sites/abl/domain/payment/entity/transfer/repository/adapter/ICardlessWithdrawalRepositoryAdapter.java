package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.IRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.fc.datatype.Date;
import java.util.List;

public abstract interface ICardlessWithdrawalRepositoryAdapter
  extends IRepositoryAdapter<CardlessWithdrawalDomain, PaymentKey>
{
  public abstract CardlessWithdrawalDomain read(PaymentKey paramPaymentKey)
    throws Exception;
  
  public abstract void create(CardlessWithdrawalDomain paramCardlessWithdrawal)
    throws Exception;
  
  public abstract void delete(CardlessWithdrawalDomain paramCardlessWithdrawal)
    throws Exception;
  
  public abstract void update(CardlessWithdrawalDomain paramCardlessWithdrawal)
    throws Exception;
  
  public abstract List<CardlessWithdrawalDomain> list(String paramString, Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType)
    throws Exception;
  
  public abstract CardlessWithdrawalDomain process(CardlessWithdrawalDomain paramCardlessWithdrawal)
    throws Exception;
  
  public abstract List<CardlessWithdrawalDomain> lastPaymentList(Date paramDate)
    throws Exception;
}
