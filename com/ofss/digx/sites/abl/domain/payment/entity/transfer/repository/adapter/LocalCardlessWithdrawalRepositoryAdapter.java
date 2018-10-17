package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractLocalRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.HashMap;
import java.util.List;

public class LocalCardlessWithdrawalRepositoryAdapter
  extends AbstractLocalRepositoryAdapter<CardlessWithdrawalDomain>
  implements ICardlessWithdrawalRepositoryAdapter
{
  private static LocalCardlessWithdrawalRepositoryAdapter singletonInstance;
  
  public static LocalCardlessWithdrawalRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (LocalCardlessWithdrawalRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new LocalCardlessWithdrawalRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public CardlessWithdrawalDomain read(PaymentKey key)
    throws Exception
  {
	key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(CardlessWithdrawalDomain.class.getName()));
    return (CardlessWithdrawalDomain)super.load(CardlessWithdrawalDomain.class, key);
  }
  
  public void create(CardlessWithdrawalDomain object)
    throws Exception
  {
	  if (object.getKey() != null) {			 
		  object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(CardlessWithdrawalDomain.class.getName()));
	  }
	  System.out.println("Determinant Value :" + object.getKey().getDeterminantValue().toString());
	  super.insert(object);
  }
  
  public void update(CardlessWithdrawalDomain object)
    throws Exception
  {
	  /*if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
	      object.getKey().setDeterminantValue(DeterminantResolver.getInstance()
	        .fetchDeterminantValue(MerchantTransfer.class.getName()));
	  }*/
	      super.update(object);
  }
  
  public void delete(CardlessWithdrawalDomain object)
    throws Exception
  {
	  if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
	      object.getKey().setDeterminantValue(DeterminantResolver.getInstance()
	        .fetchDeterminantValue(MerchantTransferDomain.class.getName()));
	    }
    super.delete(object);
  }
  
  public List<CardlessWithdrawalDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", fromDate);
    parameters.put("status", status);
    return executeNamedQuery("fetchChecksumCardlessWithdrawal", parameters);
  }
  
  public CardlessWithdrawalDomain process(CardlessWithdrawalDomain merchantTransfer)
    throws Exception
  {
    return null;
  }
  
  public List<CardlessWithdrawalDomain> lastPaymentList(Date filterDate)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", filterDate);
    parameters.put("status", PaymentStatusType.COMPLETED);
    return executeNamedQuery("fetchChecksumCardlessWithdrawal", parameters);
  }
}
