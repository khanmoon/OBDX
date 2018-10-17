package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.adapter.AbstractLocalRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.thread.ThreadAttribute;
import com.ofss.digx.framework.determinant.DeterminantResolver;

import java.util.HashMap;
import java.util.List;

public class LocalPayAnyoneTransferRepositoryAdapter
  extends AbstractLocalRepositoryAdapter<PayAnyoneTransfer>
  implements IPayAnyoneTransferRepositoryAdapter
{
  private static LocalPayAnyoneTransferRepositoryAdapter singletonInstance;
  
  public static LocalPayAnyoneTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (LocalPayAnyoneTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new LocalPayAnyoneTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public PayAnyoneTransfer read(PaymentKey key)
    throws Exception
  {
	System.out.println("**Inside read method of LocalPayAnyoneTransferRepositoryAdapter");
	System.out.println("**About to set Determinant Value in read method of LocalPayAnyoneTransferRepositoryAdapter");
	
	key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(PayAnyoneTransfer.class.getName()));
	
	System.out.println("**Did set Determinant Value in read method of LocalPayAnyoneTransferRepositoryAdapter");
	System.out.println("**About to call super.load inside read method of LocalPayAnyoneTransferRepositoryAdapter");
	
    PayAnyoneTransfer payAnyoneTransfer = (PayAnyoneTransfer)super.load(PayAnyoneTransfer.class, key);
    
    System.out.println("**Called super.load inside read method of LocalPayAnyoneTransferRepositoryAdapter");
    return payAnyoneTransfer;
  }
  
  public void create(PayAnyoneTransfer object)
    throws Exception
  {
    if (object.getKey() != null) {
      System.out.println("**About to set Determinant Value in create method of LocalPayAnyoneTransferRepositoryAdapter");
      
      object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(PayAnyoneTransfer.class.getName()));
      
      System.out.println("**Did set Determinant Value in create method of LocalPayAnyoneTransferRepositoryAdapter");
    }
	System.out.println("**About to call super.insert in create method of LocalPayAnyoneTransferRepositoryAdapter");
	
    super.insert(object);
    
    System.out.println("**Called super.insert in create method of LocalPayAnyoneTransferRepositoryAdapter");
  }
  
  public void update(PayAnyoneTransfer object)
    throws Exception
  {
    if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
      object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(PayAnyoneTransfer.class.getName()));
    }  
    super.update(object);
  }
  
  public void delete(PayAnyoneTransfer object)
    throws Exception
  {
    super.delete(object);
  }
  
  public List<PayAnyoneTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", fromDate);
    parameters.put("status", status);
    return executeNamedQuery("fetchChecksumPayAnyoneTransfer", parameters);
  }
  
  public PayAnyoneTransfer process(PayAnyoneTransfer payAnyoneTransfer)
    throws Exception
  {
    return null;
  }
  
  public List<PayAnyoneTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", filterDate);
    parameters.put("status", PaymentStatusType.COMPLETED);
    return executeNamedQuery("fetchChecksumPayAnyoneTransfer", parameters);
  }
}
