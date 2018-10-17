package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.ICardlessWithdrawalRepositoryAdapter;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IMerchantTransferRepositoryAdapter;
import com.ofss.fc.datatype.Date;
import java.util.List;

public class CardlessWithdrawalRepository
  extends AbstractDomainObjectRepository<CardlessWithdrawalDomain, PaymentKey>
{
  private static CardlessWithdrawalRepository singletonInstance;
  
  public static CardlessWithdrawalRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (CardlessWithdrawalRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new CardlessWithdrawalRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public CardlessWithdrawalDomain read(PaymentKey key)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.read(key);
  }
  
  public void create(CardlessWithdrawalDomain object)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_REPOSITORY_ADAPTER");
    
    repositoryAdapter.create(object);
  }
  
  public void update(CardlessWithdrawalDomain object)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_REPOSITORY_ADAPTER");
    
    repositoryAdapter.update(object);
  }
  
  public void delete(CardlessWithdrawalDomain object)
    throws Exception
  {}
  
  public List<CardlessWithdrawalDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public CardlessWithdrawalDomain process(CardlessWithdrawalDomain transfer)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_HOST_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.process(transfer);
  }
  
  public List<CardlessWithdrawalDomain> lastPaymentList(Date filterDate)
    throws Exception
  {
    ICardlessWithdrawalRepositoryAdapter repositoryAdapter = (ICardlessWithdrawalRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("CARDLESS_WITHDRAWAL_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}
