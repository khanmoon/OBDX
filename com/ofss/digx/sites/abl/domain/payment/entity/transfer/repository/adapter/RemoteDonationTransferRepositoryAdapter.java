package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.extxface.extxface.ExtxfaceAdapterFactory;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractRemoteRepositoryAdapter;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.enumeration.ServiceCallContextType;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.List;

public class RemoteDonationTransferRepositoryAdapter
  extends AbstractRemoteRepositoryAdapter<DonationTransfer>
  implements IDonationTransferRepositoryAdapter
{
  private static RemoteDonationTransferRepositoryAdapter singletonInstance;
  
  public static RemoteDonationTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (RemoteDonationTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new RemoteDonationTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public DonationTransfer read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public void create(DonationTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void delete(DonationTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void update(DonationTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public List<DonationTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public DonationTransfer process(DonationTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {
	/*//SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");
    
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    
    DonationTransferRequestDomainDTO donationTransferReqDTO = null;
    
    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    donationTransferReqDTO = assembler.fromDomainObjectDonationTransfer(donationTransfer);
    */
    
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    if ((sessionContext.getServiceCallContextType() != null) && (sessionContext.getServiceCallContextType() == ServiceCallContextType.VALIDATE)) {
      return donationTransfer;
    }
    
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    //IPaymentAdapter adapter = (IPaymentAdapter)ExtxfaceAdapterFactory.getInstance().getAdapter(IPaymentAdapter.class, "processDonationTransfer", DeterminantResolver.getInstance().getDeterminantTypeForObject(DonationTransfer.class.getName()));
    DonationTransferRequestDomainDTO donationTransferReqDTO = null;
    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    donationTransferReqDTO = assembler.fromDomainObjectDonationTransfer(donationTransfer);
   
    try
    {
    	DonationTransferResponseDomainDTO response = adapter.processDonationTransfer(donationTransferReqDTO);
      donationTransfer.getTransactionReference().setExternalReferenceId(response.getHostReference());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return donationTransfer;
  }
  
  public List<DonationTransfer> lastPaymentList(Date filterDate)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public Boolean isSimulationSupported()
  {
    return null;
  }
}
