package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.digx.domain.payment.entity.Payment;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.DonationPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.DonationTransferRepository;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IPersistenceObject;

import java.util.List;

public class DonationTransfer extends Payment implements IPersistenceObject
{
  private static final long serialVersionUID = -4960596856991625778L;
  private DonationPayeeDetails payee;
  
  public DonationTransfer() {}
  
  public DonationPayeeDetails getPayee()
  {
    return payee;
  }
  
  public void setPayee(DonationPayeeDetails payee)
  {
    this.payee = payee;
  }
  
  public void create(DonationTransfer transfer)
    throws Exception
  {
	System.out.println("**About to call create of donationTransfer at entity.transfer ");
    DonationTransferRepository.getInstance().create(transfer);
    System.out.println("**Called create of donationTransfer at entity.transfer ");
  }
  
  public DonationTransfer read(PaymentKey key)
    throws Exception
  {
    return DonationTransferRepository.getInstance().read(key);
  }
  
  public void delete(PaymentKey key)
    throws Exception
  {
    DonationTransfer donationTransferObject = DonationTransferRepository.getInstance().read(key);
    DonationTransferRepository.getInstance().delete(donationTransferObject);
  }
  
  public void update(DonationTransfer transfer)
    throws Exception
  {
    DonationTransferRepository.getInstance().update(transfer);
  }
  

  public DonationTransfer process(DonationTransfer transfer)
    throws Exception
  {
    transfer.setStatus(PaymentStatusType.SENT);
    transfer.update(transfer);
    transfer = DonationTransferRepository.getInstance().process(transfer);
    if ((transfer.getTransactionReference() != null) && 
      (transfer.getTransactionReference().getExternalReferenceId() != null)) {
      transfer.setStatus(PaymentStatusType.COMPLETED);
    } else {
      transfer.setStatus(PaymentStatusType.ERROR);
    }
    transfer.update(transfer);
    return transfer;
  }
  
  public List<DonationTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    List<DonationTransfer> donationTransferList = DonationTransferRepository.getInstance().list(partyId, fromDate, toDate, status);
    
    return donationTransferList;
  }
  
  public List<DonationTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    return DonationTransferRepository.getInstance().lastPaymentList(filterDate);
  }
}
