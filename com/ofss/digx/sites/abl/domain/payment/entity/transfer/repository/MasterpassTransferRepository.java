package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IMasterpassTransferRepositoryAdapter;
import com.ofss.fc.datatype.Date;
import java.io.PrintStream;
import java.util.List;

public class MasterpassTransferRepository
  extends AbstractDomainObjectRepository<MasterpassTransfer, PaymentKey>
{
  private static MasterpassTransferRepository singletonInstance;
  
  public static MasterpassTransferRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (DonationTransferRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new MasterpassTransferRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public MasterpassTransfer read(PaymentKey key)
    throws Exception
  {
	  IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.read(key);
  }
  
  public void create(MasterpassTransfer object)
    throws Exception
  {
    IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("MASTERPASS_TRANSFER_REPOSITORY_ADAPTER");
    System.out.println("**About to call create of adaptor fetched from DB ");
    repositoryAdapter.create(object);
  }
  
  public void update(MasterpassTransfer object)
    throws Exception
  {
	  IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    repositoryAdapter.update(object);
  }
  
  public void delete(MasterpassTransfer object)
    throws Exception
  {}
  
  public List<MasterpassTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
	  IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public MasterpassTransfer process(MasterpassTransfer transfer)
    throws Exception
  {
	  IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_HOST_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.process(transfer);
  }
  
  public List<MasterpassTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
	  IMasterpassTransferRepositoryAdapter repositoryAdapter = (IMasterpassTransferRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("DONATION_TRANSFER_PAYMENT_REPOSITORY_ADAPTER");
    
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}
