package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.digx.domain.payment.entity.Payment;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.PayAnyonePayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.PayAnyoneTransferRepository;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IPersistenceObject;
import java.util.List;

public class PayAnyoneTransfer
  extends Payment
  implements IPersistenceObject
{
  private static final long serialVersionUID = -4960596856991625778L;
  private PayAnyonePayeeDetails payee;
  
  public PayAnyonePayeeDetails getPayee()
  {
    return this.payee;
  }
  
  public void setPayee(PayAnyonePayeeDetails payee)
  {
    this.payee = payee;
  }
  
  public void create(PayAnyoneTransfer transfer)
    throws Exception
  {
    PayAnyoneTransferRepository.getInstance().create(transfer);
  }
  
  public PayAnyoneTransfer read(PaymentKey key)
    throws Exception
  {
    return PayAnyoneTransferRepository.getInstance().read(key);
  }
  
  public void delete(PaymentKey key)
    throws Exception
  {
    PayAnyoneTransfer payAnyoneTransferObject = PayAnyoneTransferRepository.getInstance().read(key);
    PayAnyoneTransferRepository.getInstance().delete(payAnyoneTransferObject);
  }
  
  public void update(PayAnyoneTransfer transfer)
    throws Exception
  {
    PayAnyoneTransferRepository.getInstance().update(transfer);
  }
  
  public PayAnyoneTransfer process(PayAnyoneTransfer transfer)
    throws Exception
  {
    transfer.setStatus(PaymentStatusType.SENT);
    transfer.update(transfer);
    transfer = PayAnyoneTransferRepository.getInstance().process(transfer);
    if ((transfer.getTransactionReference() != null) && 
      (transfer.getTransactionReference().getExternalReferenceId() != null)) {
      transfer.setStatus(PaymentStatusType.COMPLETED);
    } else {
      transfer.setStatus(PaymentStatusType.ERROR);
    }
    transfer.update(transfer);
    return transfer;
  }
  
  public List<PayAnyoneTransfer> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    List<PayAnyoneTransfer> payAnyoneTransferList = PayAnyoneTransferRepository.getInstance().list(partyId, fromDate, toDate, status);
    
    return payAnyoneTransferList;
  }
  
  public List<PayAnyoneTransfer> lastPaymentList(Date filterDate)
    throws Exception
  {
    return PayAnyoneTransferRepository.getInstance().lastPaymentList(filterDate);
  }
}
