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
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.datatype.Date;

import java.util.List;

public class RemoteCardlessWithdrawalRepositoryAdapter
  extends AbstractRemoteRepositoryAdapter<MerchantTransferDomain>
  implements ICardlessWithdrawalRepositoryAdapter
{
  private static RemoteCardlessWithdrawalRepositoryAdapter singletonInstance;
  
  public static RemoteCardlessWithdrawalRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (RemoteCardlessWithdrawalRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new RemoteCardlessWithdrawalRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public CardlessWithdrawalDomain read(PaymentKey key)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public void create(CardlessWithdrawalDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void delete(CardlessWithdrawalDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public void update(CardlessWithdrawalDomain merchantTransfer)
    throws com.ofss.digx.infra.exceptions.Exception
  {}
  
  public List<CardlessWithdrawalDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public CardlessWithdrawalDomain process(CardlessWithdrawalDomain cardlessWithdrawal)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");  
    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
    //IPaymentAdapter adapter = (IPaymentAdapter)ExtxfaceAdapterFactory.getInstance().getAdapter(IPaymentAdapter.class, "processMerchantTransfer", DeterminantResolver.getInstance().getDeterminantTypeForObject(MerchantTransfer.class.getName()));
   
    CardlessWithdrawalRequestDomainDTO cardlessWithdrawalReqDTO = null;

    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
    cardlessWithdrawalReqDTO = assembler.fromDomainObjectCardlessWithdrawal(cardlessWithdrawal);
    try
    {
      CardlessWithdrawalResponseDomainDTO response = adapter.processCardlessWithdrawal(cardlessWithdrawalReqDTO);
      cardlessWithdrawal.getTransactionReference().setExternalReferenceId(response.getHostReference());
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return cardlessWithdrawal;
  }
  
  public List<CardlessWithdrawalDomain> lastPaymentList(Date filterDate)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    return null;
  }
  
  public Boolean isSimulationSupported()
  {
    return null;
  }
}
