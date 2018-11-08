package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.digx.domain.payment.entity.Payment;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.ZakatDonationRepository;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IPersistenceObject;

import java.util.ArrayList;
import java.util.List;

public class ZakatDonation
  extends Payment
  implements IPersistenceObject
{
  private static final long serialVersionUID = -4960596856991625778L;
  private String companyID;
  private ArrayList<ZakatDonationCompanyDetails> companiesList;
  
  public String getCompanyId()
  {
	  return this.companyID;
  }
  
  public void setCompanyID(String companyID)
  {
	  this.companyID = companyID;
  }
  
  
  public void setCompaniesList(ArrayList<ZakatDonationCompanyDetails> companiesList)
  {
	 this.companiesList = companiesList;
  }
  
  public ArrayList<ZakatDonationCompanyDetails> getCompaniesList()
  {
	  return this.companiesList;
  }
 
  public ZakatDonation read(PaymentKey key)
    throws Exception
  {
	  ZakatDonation _zakatDonation = ZakatDonationRepository.getInstance().read(key);    
	  return _zakatDonation;
  }
  
  
  //Get List of All Zakat Companies From DB
  public ZakatDonation listCompanyDetails()
    throws Exception
  {
	  ZakatDonation _zakatDonation = ZakatDonationRepository.getInstance().listCompanyDetails();    
	  return _zakatDonation;
  }
  
  public void create(ZakatDonation transfer)
		    throws Exception
  {
	ZakatDonationRepository.getInstance().create(transfer);
  }
 
  
  public void update(ZakatDonation transfer)
    throws Exception
  {
    ZakatDonationRepository.getInstance().update(transfer);
  }
  
  public ZakatDonation process(ZakatDonation transfer)
    throws Exception
  {
    transfer.setStatus(PaymentStatusType.SENT);
    transfer.update(transfer);
    transfer = ZakatDonationRepository.getInstance().process(transfer);
    if ((transfer.getTransactionReference() != null) && 
      (transfer.getTransactionReference().getExternalReferenceId() != null)) {
      transfer.setStatus(PaymentStatusType.COMPLETED);
    } else {
      transfer.setStatus(PaymentStatusType.ERROR);
    }
    transfer.update(transfer);
    return transfer;
  }
  
  public void delete(PaymentKey key)
		    throws Exception
  {
		    ZakatDonation ZakatDonationObject = ZakatDonationRepository.getInstance().read(key);
		    ZakatDonationRepository.getInstance().delete(ZakatDonationObject);
  }
  
  public List<ZakatDonation> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    List<ZakatDonation> ZakatDonationList = ZakatDonationRepository.getInstance().list(partyId, fromDate, toDate, status);
    
    return ZakatDonationList;
  }
  
  public List<ZakatDonation> lastPaymentList(Date filterDate)
    throws Exception
  {
    return ZakatDonationRepository.getInstance().lastPaymentList(filterDate);
  }
}
