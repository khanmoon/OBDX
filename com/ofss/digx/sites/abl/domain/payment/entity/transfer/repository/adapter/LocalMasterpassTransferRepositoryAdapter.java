package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractLocalRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.HashMap;
import java.util.List;

public class LocalMasterpassTransferRepositoryAdapter
  extends AbstractLocalRepositoryAdapter<MasterpassTransfer>
  implements IMasterpassTransferRepositoryAdapter
{
  private static LocalMasterpassTransferRepositoryAdapter singletonInstance;
  
  public static LocalMasterpassTransferRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (LocalMasterpassTransferRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new LocalMasterpassTransferRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public MasterpassTransfer read(PaymentKey key)
    throws Exception
  {
	  key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(MasterpassTransfer.class.getName()));  
    return (MasterpassTransfer)super.load(MasterpassTransfer.class, key);
  }
  
  public void create(MasterpassTransfer object)
    throws Exception
  {
	System.out.println("**About to call insert at entity.transfer.repository.adapter ");
	 if (object.getKey() != null) {
		 object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(MasterpassTransfer.class.getName()));
	 }
    super.insert(object);
    System.out.println("**Called insert at entity.transfer.repository.adapter ");
  }
  
  public void update(MasterpassTransfer object)
    throws Exception
  {
	  
  if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
      object.getKey().setDeterminantValue(
        DeterminantResolver.getInstance().fetchDeterminantValue(MasterpassTransfer.class.getName()));
    }  
    super.update(object);
  }
  
  public void delete(MasterpassTransfer object)
    throws Exception
  {
    super.delete(object);
  }
  
  public List<MasterpassTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", fromDate);
    parameters.put("status", status);
    return executeNamedQuery("fetchChecksumMasterpassTransfer", parameters);
  }
  
  public MasterpassTransfer process(MasterpassTransfer donationTransfer)
    throws Exception
  {
    return null;
  }
  
  public List<MasterpassTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", filterDate);
    parameters.put("status", PaymentStatusType.COMPLETED);
    return executeNamedQuery("fetchChecksumMasterpassTransfer", parameters);
  }
}

