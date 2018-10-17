package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IMerchantTransferRepositoryAdapter;
import com.ofss.fc.datatype.Date;
import java.util.List;

public class MerchantTransferRepository
  extends AbstractDomainObjectRepository<MerchantTransferDomain, PaymentKey>
{
  private static MerchantTransferRepository singletonInstance;
  
  public static MerchantTransferRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (MerchantTransferRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new MerchantTransferRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public MerchantTransferDomain read(PaymentKey key)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.read(key);
  }
  
  public void create(MerchantTransferDomain object)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.create(object);
  }
  
  public void update(MerchantTransferDomain object)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.update(object);
  }
  
  public void delete(MerchantTransferDomain object)
    throws Exception
  {}
  
  public List<MerchantTransferDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public MerchantTransferDomain process(MerchantTransferDomain transfer)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_HOST_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.process(transfer);
  }
  
  public List<MerchantTransferDomain> lastPaymentList(Date filterDate)
    throws Exception
  {
    IMerchantTransferRepositoryAdapter repositoryAdapter = (IMerchantTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MERCHANT_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}
