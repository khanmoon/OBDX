package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.DeterminantType;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.extxface.extxface.ExtxfaceAdapterFactory;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractRemoteRepositoryAdapter;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.datatype.Date;

import java.util.List;

public class RemoteMerchantTransferRepositoryAdapter
  extends AbstractRemoteRepositoryAdapter<MerchantTransferDomain>
  implements IMerchantTransferRepositoryAdapter
{
  private static RemoteMerchantTransferRepositoryAdapter singletonInstance;
  
  public static RemoteMerchantTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (RemoteMerchantTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new RemoteMerchantTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public MerchantTransferDomain read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public void create(MerchantTransferDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void delete(MerchantTransferDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void update(MerchantTransferDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public List<MerchantTransferDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public MerchantTransferDomain process(MerchantTransferDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");  
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    //IPaymentAdapter adapter = (IPaymentAdapter)ExtxfaceAdapterFactory.getInstance().getAdapter(IPaymentAdapter.class, "processMerchantTransfer", DeterminantResolver.getInstance().getDeterminantTypeForObject(MerchantTransfer.class.getName()));
   
    MerchantTransferRequestDomainDTO merchantTransferReqDTO = null;

    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    merchantTransferReqDTO = assembler.fromDomainObjectMerchantTransfer(merchantTransfer);
    try
    {
      MerchantTransferResponseDomainDTO response = adapter.processMerchantTransfer(merchantTransferReqDTO);
      merchantTransfer.getTransactionReference().setExternalReferenceId(response.getHostReference());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return merchantTransfer;
  }
  
  public List<MerchantTransferDomain> lastPaymentList(Date filterDate)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public Boolean isSimulationSupported()
  {
    return null;
  }
}
