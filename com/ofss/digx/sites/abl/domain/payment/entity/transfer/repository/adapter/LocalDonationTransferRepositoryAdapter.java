package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractLocalRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.HashMap;
import java.util.List;

public class LocalDonationTransferRepositoryAdapter
  extends AbstractLocalRepositoryAdapter<DonationTransfer>
  implements IDonationTransferRepositoryAdapter
{
  private static LocalDonationTransferRepositoryAdapter singletonInstance;
  
  public static LocalDonationTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (LocalDonationTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new LocalDonationTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public DonationTransfer read(PaymentKey key)
    throws Exception
  {
	  key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(DonationTransfer.class.getName()));  
    return (DonationTransfer)super.load(DonationTransfer.class, key);
  }
  
  public void create(DonationTransfer object)
    throws Exception
  {
	System.out.println("**About to call insert at entity.transfer.repository.adapter ");
	 if (object.getKey() != null) {
	 
	  object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(DonationTransfer.class.getName()));
	    }
    super.insert(object);
    System.out.println("**Called insert at entity.transfer.repository.adapter ");
  }
  
  public void update(DonationTransfer object)
    throws Exception
  {
	  
  if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
      object.getKey().setDeterminantValue(
        DeterminantResolver.getInstance().fetchDeterminantValue(DonationTransfer.class.getName()));
    }  
    super.update(object);
  }
  
  public void delete(DonationTransfer object)
    throws Exception
  {
    super.delete(object);
  }
  
  public List<DonationTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", fromDate);
    parameters.put("status", status);
    return executeNamedQuery("fetchChecksumDonationTransfer", parameters);
  }
  
  public DonationTransfer process(DonationTransfer donationTransfer)
    throws Exception
  {
    return null;
  }
  
  public List<DonationTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", filterDate);
    parameters.put("status", PaymentStatusType.COMPLETED);
    return executeNamedQuery("fetchChecksumDonationTransfer", parameters);
  }
}
