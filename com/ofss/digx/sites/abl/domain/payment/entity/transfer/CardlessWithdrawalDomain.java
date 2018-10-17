package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.digx.domain.payment.entity.Payment;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.CardlessWithdrawalPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MerchantPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.CardlessWithdrawalRepository;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.CardlessWithdrawalRepository;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IPersistenceObject;
import java.util.List;

public class CardlessWithdrawalDomain
  extends Payment
  implements IPersistenceObject
{
  private static final long serialVersionUID = -4960596856991625778L;
  private CardlessWithdrawalPayeeDetails payee;
  
  public CardlessWithdrawalPayeeDetails getPayee()
  {
    return this.payee;
  }
  
  public void setPayee(CardlessWithdrawalPayeeDetails payee)
  {
    this.payee = payee;
  }
  
  public void create(CardlessWithdrawalDomain transfer)
    throws Exception
  {
    CardlessWithdrawalRepository.getInstance().create(transfer);
  }
  
  public CardlessWithdrawalDomain read(PaymentKey key)
    throws Exception
  {
    return CardlessWithdrawalRepository.getInstance().read(key);
  }
  
  public void delete(PaymentKey key)
    throws Exception
  {
    CardlessWithdrawalDomain merchantTransferObject = CardlessWithdrawalRepository.getInstance().read(key);
    CardlessWithdrawalRepository.getInstance().delete(merchantTransferObject);
  }
  
  public void update(CardlessWithdrawalDomain transfer)
    throws Exception
  {
    CardlessWithdrawalRepository.getInstance().update(transfer);
  }
  
  public CardlessWithdrawalDomain process(CardlessWithdrawalDomain transfer)
    throws Exception
  {
    transfer.setStatus(PaymentStatusType.SENT);
    transfer.update(transfer);    
    transfer = CardlessWithdrawalRepository.getInstance().process(transfer);
    if ((transfer.getTransactionReference() != null) && 
      (transfer.getTransactionReference().getExternalReferenceId() != null)) {
      transfer.setStatus(PaymentStatusType.COMPLETED);
    } else {
      transfer.setStatus(PaymentStatusType.ERROR);
    }
    transfer.update(transfer);
    return transfer;
  }
  
  public List<CardlessWithdrawalDomain> list(String partyId, Date fromDate, Date toDate, PaymentStatusType status)
    throws Exception
  {
    List<CardlessWithdrawalDomain> merchantTransferList = CardlessWithdrawalRepository.getInstance().list(partyId, fromDate, toDate, status);
    
    return merchantTransferList;
  }
  
  public List<CardlessWithdrawalDomain> lastPaymentList(Date filterDate)
    throws Exception
  {
    return CardlessWithdrawalRepository.getInstance().lastPaymentList(filterDate);
  }
}
