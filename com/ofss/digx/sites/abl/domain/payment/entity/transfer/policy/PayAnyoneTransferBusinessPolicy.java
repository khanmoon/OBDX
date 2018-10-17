package com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy;

import com.ofss.digx.app.account.dto.AccountCurrencyResponse;
import com.ofss.digx.app.account.dto.AccountVerificationRequestDTO;
import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.app.dda.adapter.IDemandDepositAccountAdapter;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.domain.payment.entity.policy.AbstractPaymentBusinessPolicy;
import com.ofss.digx.enumeration.accounts.AccountType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.PayAnyoneTransferBusinessPolicyData;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.thread.ThreadAttribute;
import com.ofss.fc.infra.validation.error.ValidationError;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PayAnyoneTransferBusinessPolicy
  extends AbstractPaymentBusinessPolicy
{
  private static final String THIS_COMPONENT_NAME = PayAnyoneTransferBusinessPolicy.class.getName();
  private static final MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private static final Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private PayAnyoneTransferBusinessPolicyData paymentBusinessPolicyData;
  
  public PayAnyoneTransferBusinessPolicy() {}
  
  public PayAnyoneTransferBusinessPolicy(IBusinessPolicyDTO iBusinessPolicyDTO)
  {
    this.paymentBusinessPolicyData = ((PayAnyoneTransferBusinessPolicyData)iBusinessPolicyDTO);
  }
  
  public void validatePolicy()
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into com.ofss.digx.domain.payment.entity.transfer.policy.PayAnyoneTransferBusinessPolicy.validatePolicy() ", new Object[0]));
    }
    try
    {
      super.validateRegEx(this.paymentBusinessPolicyData, null);
      
      super.validatePayment(this.paymentBusinessPolicyData);
      if (this.validationErrors == null)
      {
        if ((this.paymentBusinessPolicyData.getDebitAccount() == null) || 
          (this.paymentBusinessPolicyData.getDebitAccount().getValue() == null))
        {
          addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0133", new String[] { "" }));
          
          return;
        }
        if (!isPartyAccountRelationship(this.paymentBusinessPolicyData.getDebitAccount().getValue()))
        {
          addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0164", new String[] { "" }));
          
          return;
        }
        SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
        IAdapterFactory adapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("DEMAND_DEPOSIT_ACCOUNT_ADAPTER_FACTORY");
        
        IDemandDepositAccountAdapter adapter = (IDemandDepositAccountAdapter)adapterFactory.getAdapter("DemandDepositAccountAdapter");
        
        AccountVerificationRequestDTO debitAccountRequest = new AccountVerificationRequestDTO();
        debitAccountRequest.setAccountId(this.paymentBusinessPolicyData.getDebitAccount());
        debitAccountRequest.setAccountType(AccountType.DEMAND_DEPOSIT);
        AccountCurrencyResponse debitAccountCurrency = adapter.fetchCurrencyForAccount(debitAccountRequest);
        if ((this.paymentBusinessPolicyData.getCreditAmount() != null) && 
          (!this.paymentBusinessPolicyData.getCreditAmount().getCurrency().equals(debitAccountCurrency.getCurrencyCode())))
        {
          addValidationError(new ValidationError("paymentBusinessPolicyData", "creditAmount", null, "DIGX_PY_0180", new String[] { "" }));
          
          return;
        }
      }
    }
    catch (Exception e)
    {
      if (e.getValidationErrors() == null)
      {
        addValidationError(new ValidationError());
        logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception from validatePolicy in '%s'", new Object[] { THIS_COMPONENT_NAME, e }));
        return;
      }
      for (int i = 0; i < e.getValidationErrors().length; i++) {
        addValidationError(e.getValidationErrors()[i]);
      }
    }
    finally
    {
      if (logger.isLoggable(Level.FINE)) {
        logger.log(Level.FINE, FORMATTER
        
          .formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.PayAnyoneTransferBusinessPolicy.validatePolicy() ", new Object[0]));
      }
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.PayAnyoneTransferBusinessPolicy.validatePolicy() ", new Object[0]));
    }
  }
  
  public PayAnyoneTransferBusinessPolicyData getPaymentBusinessPolicyData()
  {
    return this.paymentBusinessPolicyData;
  }
  
  public void setPaymentBusinessPolicyData(PayAnyoneTransferBusinessPolicyData paymentBusinessPolicyData)
  {
    this.paymentBusinessPolicyData = paymentBusinessPolicyData;
  }
}