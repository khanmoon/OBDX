package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.adapter.AbstractRemoteRepositoryAdapter;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.enumeration.ServiceCallContextType;
import com.ofss.fc.infra.thread.ThreadAttribute;
import java.util.List;

public class RemoteMasterpassTransferRepositoryAdapter
  extends AbstractRemoteRepositoryAdapter<MasterpassTransfer>
  implements IMasterpassTransferRepositoryAdapter
{
  private static RemoteMasterpassTransferRepositoryAdapter singletonInstance;
  
  public static RemoteMasterpassTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (RemoteMasterpassTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new RemoteMasterpassTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public MasterpassTransfer read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public void create(MasterpassTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void delete(MasterpassTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void update(MasterpassTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public List<MasterpassTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public MasterpassTransfer process(MasterpassTransfer donationTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    if ((sessionContext.getServiceCallContextType() != null) && (sessionContext.getServiceCallContextType() == ServiceCallContextType.VALIDATE)) {
      return donationTransfer;
    }
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    
    MasterpassTransferRequestDomainDTO donationTransferReqDTO = null;
    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    donationTransferReqDTO = assembler.fromDomainObjectMasterpassTransfer(donationTransfer);
    try
    {
      MasterpassTransferResponseDomainDTO response = adapter.processMasterpassTransfer(donationTransferReqDTO);
      donationTransfer.getTransactionReference().setExternalReferenceId(response.getHostReference());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return donationTransfer;
  }
  
  public List<MasterpassTransfer> lastPaymentList(Date filterDate)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public Boolean isSimulationSupported()
  {
    return null;
  }
}
