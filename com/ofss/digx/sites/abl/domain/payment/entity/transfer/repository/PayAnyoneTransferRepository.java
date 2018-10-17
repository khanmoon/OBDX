package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IPayAnyoneTransferRepositoryAdapter;
import com.ofss.fc.datatype.Date;
import java.util.List;

public class PayAnyoneTransferRepository
  extends AbstractDomainObjectRepository<PayAnyoneTransfer, PaymentKey>
{
  private static PayAnyoneTransferRepository singletonInstance;
  
  public static PayAnyoneTransferRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (PayAnyoneTransferRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new PayAnyoneTransferRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public PayAnyoneTransfer read(PaymentKey key)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.read(key);
  }
  
  public void create(PayAnyoneTransfer object)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.create(object);
  }
  
  public void update(PayAnyoneTransfer object)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.update(object);
  }
  
  public void delete(PayAnyoneTransfer object)
    throws Exception
  {}
  
  public List<PayAnyoneTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public PayAnyoneTransfer process(PayAnyoneTransfer transfer)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_HOST_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.process(transfer);
  }
  
  public List<PayAnyoneTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    IPayAnyoneTransferRepositoryAdapter repositoryAdapter = (IPayAnyoneTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("PAYANYONE_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}

