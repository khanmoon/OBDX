package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IDonationTransferRepositoryAdapter;
import com.ofss.fc.datatype.Date;

import java.util.List;

public class DonationTransferRepository extends AbstractDomainObjectRepository<DonationTransfer, PaymentKey>
{
  private static DonationTransferRepository singletonInstance;
  
  public DonationTransferRepository() {}
  
  public static DonationTransferRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (DonationTransferRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new DonationTransferRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public DonationTransfer read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.read(key);
  }
  
  public void create(DonationTransfer object)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    System.out.println("**About to call create of adaptor fetched from DB ");
    repositoryAdapter.create(object);
  }
  
  public void update(DonationTransfer object)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.update(object);
  }
  


  public void delete(DonationTransfer object)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  


  public List<DonationTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws java.lang.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public DonationTransfer process(DonationTransfer transfer)
    throws java.lang.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_HOST_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.process(transfer);
  }
  
  public List<DonationTransfer> lastPaymentList(Date filterDate)
    throws java.lang.Exception
  {
    IDonationTransferRepositoryAdapter repositoryAdapter = (IDonationTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}
