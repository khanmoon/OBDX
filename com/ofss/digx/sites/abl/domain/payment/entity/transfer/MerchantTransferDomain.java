package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.digx.domain.payment.entity.Payment;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MerchantPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.MerchantTransferRepository;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IPersistenceObject;
import java.util.List;

public class MerchantTransferDomain
  extends Payment
  implements IPersistenceObject
{
  private static final long serialVersionUID = -4960596856991625778L;
  private MerchantPayeeDetails payee;
  
  public MerchantPayeeDetails getPayee()
  {
    return this.payee;
  }
  
  public void setPayee(MerchantPayeeDetails payee)
  {
    this.payee = payee;
  }
  
  public void create(MerchantTransferDomain transfer)
    throws Exception
  {
    MerchantTransferRepository.getInstance().create(transfer);
  }
  
  public MerchantTransferDomain read(PaymentKey key)
    throws Exception
  {
    return MerchantTransferRepository.getInstance().read(key);
  }
  
  public void delete(PaymentKey key)
    throws Exception
  {
    MerchantTransferDomain merchantTransferObject = MerchantTransferRepository.getInstance().read(key);
    MerchantTransferRepository.getInstance().delete(merchantTransferObject);
  }
  
  public void update(MerchantTransferDomain transfer)
    throws Exception
  {
    MerchantTransferRepository.getInstance().update(transfer);
  }
  
  public MerchantTransferDomain process(MerchantTransferDomain transfer)
    throws Exception
  {
    transfer.setStatus(PaymentStatusType.SENT);
    transfer.update(transfer);    
    transfer = MerchantTransferRepository.getInstance().process(transfer);
    if ((transfer.getTransactionReference() != null) && 
      (transfer.getTransactionReference().getExternalReferenceId() != null)) {
      transfer.setStatus(PaymentStatusType.COMPLETED);
    } else {
      transfer.setStatus(PaymentStatusType.ERROR);
    }
    transfer.update(transfer);
    return transfer;
  }
  
  public List<MerchantTransferDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    List<MerchantTransferDomain> merchantTransferList = MerchantTransferRepository.getInstance().list(partyId, fromDate, toDate, status);
    
    return merchantTransferList;
  }
  
  public List<MerchantTransferDomain> lastPaymentList(Date filterDate)
    throws Exception
  {
    return MerchantTransferRepository.getInstance().lastPaymentList(filterDate);
  }
}
