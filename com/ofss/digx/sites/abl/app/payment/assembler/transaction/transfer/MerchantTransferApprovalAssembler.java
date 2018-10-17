package com.ofss.digx.sites.abl.app.payment.assembler.transaction.transfer;

import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.biller.BillerCategoryRelationShip;
import com.ofss.digx.domain.payment.entity.biller.BillerCategoryRelationshipKey;
import com.ofss.digx.domain.payment.entity.transaction.PaymentTransaction;
import com.ofss.digx.enumeration.accounts.AccountType;
import com.ofss.digx.framework.domain.transaction.AmountAccountTransaction;
import com.ofss.digx.framework.domain.transaction.TransactionKey;
import com.ofss.digx.framework.domain.transaction.assembler.AbstractApprovalAssembler;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;
import com.ofss.fc.infra.thread.ThreadAttribute;

public class MerchantTransferApprovalAssembler
  extends AbstractApprovalAssembler<MerchantTransferUpdateRequestDTO, AmountAccountTransaction>
{
  public MerchantTransferUpdateRequestDTO fromDomainObject(AmountAccountTransaction transaction)
    throws Exception
  {
    return null;
  }
  
  public AmountAccountTransaction toDomainObject(MerchantTransferUpdateRequestDTO requestDTO)
    throws Exception
  {
    PaymentTransaction paymentTransaction = null;
    if (requestDTO != null)
    {
      paymentTransaction = new PaymentTransaction();
      paymentTransaction = (PaymentTransaction)super.toDomainObject(requestDTO, paymentTransaction);
      
      SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
      
      MerchantTransferDomain MerchantTransfer = new MerchantTransferDomain();
      
      PaymentKey paymentKey = new PaymentKey();
      paymentKey.setId(requestDTO.getPaymentId());
      
      MerchantTransfer = MerchantTransfer.read(paymentKey);
      
      BillerCategoryRelationshipKey categoryKey = new BillerCategoryRelationshipKey();
      
      BillerCategoryRelationShip categoryRelationship = new BillerCategoryRelationShip();
      categoryRelationship = categoryRelationship.read(categoryKey);
      if (MerchantTransfer != null)
      {
        TransactionKey key = new TransactionKey();
        paymentTransaction.setKey(key);
        paymentTransaction.setAmount(MerchantTransfer.getAmount());
        paymentTransaction.setValueDate(MerchantTransfer.getValueDate());
        
        paymentTransaction.setCreditAccountName(categoryRelationship.getDescription());
        if ((paymentTransaction.getValueDate() == null) && 
          (sessionContext != null)) {
          paymentTransaction.setValueDate(new Date(sessionContext.getPostingDateText()));
        }
        if (MerchantTransfer.getDebitAccountId() != null)
        {
          paymentTransaction.setAccountId(MerchantTransfer.getDebitAccountId());
          paymentTransaction.setAccountType(AccountType.DEMAND_DEPOSIT);
        }
      }
    }
    return paymentTransaction;
  }
  
  public DomainObjectDTO fromDomainObject(IAbstractDomainObject arg0)
    throws FatalException
  {
    return null;
  }
  
  public IAbstractDomainObject toDomainObject(DomainObjectDTO arg0)
    throws FatalException
  {
    return null;
  }
}
