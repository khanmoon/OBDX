package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.domain.repository.adapter.AbstractLocalRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonationCompanyDetails;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocalZakatDonationRepositoryAdapter
  extends AbstractLocalRepositoryAdapter<ZakatDonation>
  implements IZakatDonationRepositoryAdapter
{
  private static LocalZakatDonationRepositoryAdapter singletonInstance;
  
  public static LocalZakatDonationRepositoryAdapter getInstance()
  {
    if (singletonInstance == null) {
      synchronized (LocalZakatDonationRepositoryAdapter.class)
      {
        if (singletonInstance == null) {
          singletonInstance = new LocalZakatDonationRepositoryAdapter();
        }
      }
    }
    return singletonInstance;
  }
  
  public ZakatDonation read(PaymentKey key)
    throws Exception
  {
	  
	  HashMap<String, Object> parameters = new HashMap();
	  
	    parameters.put("categgoryID", "zakatDonationCompany");
	    
	    ArrayList<ZakatDonationCompanyDetails> _companiesList = (ArrayList<ZakatDonationCompanyDetails>)executeNamedQuery("fetchCompaniesListZakatDonation", parameters);
	    ZakatDonation _zakatDonation = new ZakatDonation();
	    
	    _zakatDonation.setCompaniesList(_companiesList);
	    _zakatDonation.setKey(key);
	   
	    return _zakatDonation;
	  
	//  key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(ZakatDonation.class.getName()));  
  //  return (ZakatDonation)super.load(ZakatDonation.class, key);
  }
  
  public void create(ZakatDonation object)
    throws Exception
  {
	System.out.println("**About to call insert at entity.transfer.repository.adapter ");
	 if (object.getKey() != null) {
	 
	  object.getKey().setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(ZakatDonation.class.getName()));
	    }
    super.insert(object);
    System.out.println("**Called insert at entity.transfer.repository.adapter ");
  }
  
  public void update(ZakatDonation object)
    throws Exception
  {
	  
  if ((object.getKey() != null) && (object.getKey().getDeterminantValue() == null)) {
      object.getKey().setDeterminantValue(
        DeterminantResolver.getInstance().fetchDeterminantValue(ZakatDonation.class.getName()));
    }  
    super.update(object);
  }
  
  public void delete(ZakatDonation object)
    throws Exception
  {
    super.delete(object);
  }
  
  public List<ZakatDonation> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", fromDate);
    parameters.put("status", status);
    return executeNamedQuery("fetchChecksumZakatDonation", parameters);
  }
  
  public ZakatDonation process(ZakatDonation ZakatDonation)
    throws Exception
  {
    return null;
  }
  
  public List<ZakatDonation> lastPaymentList(Date filterDate)
    throws Exception
  {
    HashMap<String, Object> parameters = new HashMap();
    parameters.put("partyId", 
      ((SessionContext)ThreadAttribute.get("CTX")).getTransactingPartyCode());
    parameters.put("creationDate", filterDate);
    parameters.put("status", PaymentStatusType.COMPLETED);
    return executeNamedQuery("fetchChecksumZakatDonation", parameters);
  }

}
