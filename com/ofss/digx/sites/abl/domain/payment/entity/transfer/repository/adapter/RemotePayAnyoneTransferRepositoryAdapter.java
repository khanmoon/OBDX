package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.adapter.AbstractRemoteRepositoryAdapter;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.datatype.Date;
import java.util.List;

public class RemotePayAnyoneTransferRepositoryAdapter
  extends AbstractRemoteRepositoryAdapter<PayAnyoneTransfer>
  implements IPayAnyoneTransferRepositoryAdapter
{
  private static RemotePayAnyoneTransferRepositoryAdapter singletonInstance;
  
  public static RemotePayAnyoneTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (RemotePayAnyoneTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new RemotePayAnyoneTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public PayAnyoneTransfer read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public void create(PayAnyoneTransfer payAnyoneTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void delete(PayAnyoneTransfer payAnyoneTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void update(PayAnyoneTransfer payAnyoneTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public List<PayAnyoneTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public PayAnyoneTransfer process(PayAnyoneTransfer payAnyoneTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    PayAnyoneTransferRequestDomainDTO payAnyoneTransferReqDTO = null;
    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    payAnyoneTransferReqDTO = assembler.fromDomainObjectPayAnyoneTransfer(payAnyoneTransfer);
    try
    {
      PayAnyoneTransferResponseDomainDTO response = adapter.processPayAnyoneTransfer(payAnyoneTransferReqDTO);
      payAnyoneTransfer.getTransactionReference().setExternalReferenceId(response.getHostReference());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return payAnyoneTransfer;
  }
  
  public List<PayAnyoneTransfer> lastPaymentList(Date filterDate)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public Boolean isSimulationSupported()
  {
    return null;
  }
}