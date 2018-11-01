package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter.IZakatDonationRepositoryAdapter;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.AbstractDomainObjectRepository;
import com.ofss.digx.framework.domain.repository.RepositoryAdapterFactory;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.fc.datatype.Date;
import java.util.List;

public class ZakatDonationRepository
  extends AbstractDomainObjectRepository<ZakatDonation, PaymentKey>
{
  private static ZakatDonationRepository singletonInstance;
  
  public static ZakatDonationRepository getInstance()
  {
    if (singletonInstance == null) {
      synchronized (ZakatDonationRepository.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new ZakatDonationRepository();
        }
      }
    }
    return singletonInstance;
  }
  
  public ZakatDonation read(PaymentKey key)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    return repositoryAdapter.read(key);
  }
  
  public void create(ZakatDonation object)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    repositoryAdapter.create(object);
  }
  
  public void update(ZakatDonation object)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    repositoryAdapter.update(object);
  }
  
  public void delete(ZakatDonation object)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    repositoryAdapter.delete(object);
  }
  
  public List<ZakatDonation> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    return repositoryAdapter.list(partyId, fromDate, toDate, status);
  }
  
  public ZakatDonation process(ZakatDonation transfer)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    return repositoryAdapter.process(transfer);
  }
  
  public List<ZakatDonation> lastPaymentList(Date filterDate)
    throws Exception
  {
    IZakatDonationRepositoryAdapter repositoryAdapter = (IZakatDonationRepositoryAdapter)RepositoryAdapterFactory.getInstance().getRepositoryAdapter("ZAKAT_DONATION_REPOSITORY_ADAPTER");
    return repositoryAdapter.lastPaymentList(filterDate);
  }
}
