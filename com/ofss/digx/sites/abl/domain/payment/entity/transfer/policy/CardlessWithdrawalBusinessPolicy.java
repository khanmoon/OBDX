
package com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy;

import com.ofss.digx.app.account.dto.AccountCurrencyResponse;
import com.ofss.digx.app.account.dto.AccountVerificationRequestDTO;
import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.app.dda.adapter.IDemandDepositAccountAdapter;
import com.ofss.digx.app.payment.dto.biller.*;
import com.ofss.digx.app.payment.service.biller.Biller;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.domain.payment.entity.policy.AbstractPaymentBusinessPolicy;
import com.ofss.digx.enumeration.accounts.AccountType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.CardlessWithdrawalBusinessPolicyData;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.MerchantTransferBusinessPolicyData;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.thread.ThreadAttribute;
import com.ofss.fc.infra.validation.error.ValidationError;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardlessWithdrawalBusinessPolicy extends AbstractPaymentBusinessPolicy
{

    public CardlessWithdrawalBusinessPolicy()
    {
    }

    public CardlessWithdrawalBusinessPolicy(IBusinessPolicyDTO iBusinessPolicyDTO)
    {
        paymentBusinessPolicyData = (CardlessWithdrawalBusinessPolicyData)iBusinessPolicyDTO;
    }

    /* Commented - 10th April - Muhammad Faheem
    public void validatePolicy()
    {
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Entered into com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        super.validateRegEx(paymentBusinessPolicyData, null);
        try {
			super.validatePayment(paymentBusinessPolicyData);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if(validationErrors != null)
            break;
        if(paymentBusinessPolicyData.getDebitAccount() != null && paymentBusinessPolicyData.getDebitAccount().getValue() != null)
            break;
        addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0133", new String[] {
            ""
        }));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        return;
        if(isPartyAccountRelationship(paymentBusinessPolicyData.getDebitAccount().getValue()))
            break MISSING_BLOCK_LABEL_219;
        addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0164", new String[] {
            ""
        }));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        return;
        SessionContext sessionContext;
        sessionContext = (SessionContext)ThreadAttribute.get("CTX");
        IAdapterFactory adapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("PAYMENT_DEMAND_DEPOSIT_ACCOUNT_ADAPTER_FACTORY");
        IDemandDepositAccountAdapter adapter = (IDemandDepositAccountAdapter)adapterFactory.getAdapter("PAYEMENT_DEMAND_DEPOSIT_ACCOUNT_ADAPTER");
        AccountVerificationRequestDTO debitAccountRequest = new AccountVerificationRequestDTO();
        debitAccountRequest.setAccountId(paymentBusinessPolicyData.getDebitAccount());
        debitAccountRequest.setAccountType(AccountType.DEMAND_DEPOSIT);
        AccountCurrencyResponse debitAccountCurrency = adapter.fetchCurrencyForAccount(debitAccountRequest);
        if(paymentBusinessPolicyData.getCreditAmount() == null || paymentBusinessPolicyData.getCreditAmount().getCurrency().equals(debitAccountCurrency.getCurrencyCode()))
            break MISSING_BLOCK_LABEL_381;
        addValidationError(new ValidationError("paymentBusinessPolicyData", "creditAmount", null, "DIGX_PY_0180", new String[] {
            ""
        }));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        return;
        boolean chk = false;
        if(paymentBusinessPolicyData.getUserRefNo() != null)
        {
            BillerCategoryRelationShipDTO requestDTO = new BillerCategoryRelationShipDTO();
            BillerListResponse billerListResponse = null;
            try
            {
                requestDTO.setCategoryType("ALL");
                billerListResponse = (new Biller()).list(sessionContext, requestDTO);
                for(Iterator iterator = billerListResponse.getBillers().iterator(); iterator.hasNext();)
                {
                    BillerDTO billerDTO = (BillerDTO)iterator.next();
                    if(billerDTO.getId().equalsIgnoreCase(paymentBusinessPolicyData.getUserRefNo()))
                        chk = true;
                }

                if(!chk)
                    addValidationError(new ValidationError(THIS_COMPONENT_NAME, "", "", "DIGX_PY_0155", new String[] {
                        paymentBusinessPolicyData.getUserRefNo(), paymentBusinessPolicyData.getUserRefNo()
                    }));
            }
            catch(Exception e)
            {
                logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating Biller Category relationships", new Object[] {
                    THIS_COMPONENT_NAME
                }), e);
            }
        }
        break MISSING_BLOCK_LABEL_758;
        Exception e;
       
        if(e.getValidationErrors() != null)
            break MISSING_BLOCK_LABEL_657;
        addValidationError(new ValidationError());
        logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception from validatePolicy in '%s'", new Object[] {
            THIS_COMPONENT_NAME, e
        }));
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        return;
        for(int i = 0; i < e.getValidationErrors().length; i++)
            addValidationError(e.getValidationErrors()[i]);

        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        Exception exception;

        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        throw exception;
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, FORMATTER.formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.MerchantTransferBusinessPolicy.validatePolicy() ", new Object[0]));
    }
*/
    
/* Added - 10th April - Muhammad Faheem */
    public void validatePolicy()
    {
      if (logger.isLoggable(Level.FINE)) {
        logger.log(Level.FINE, FORMATTER
        
          .formatMessage("Entered into com.ofss.digx.domain.payment.entity.transfer.policy.DonationTransferBusinessPolicy.validatePolicy() ", new Object[0]));
      }
      try
      {
        super.validateRegEx(paymentBusinessPolicyData, null);
        
        super.validatePayment(paymentBusinessPolicyData);
        if (validationErrors == null)
        {
          if ((paymentBusinessPolicyData.getDebitAccount() == null) || 
            (paymentBusinessPolicyData.getDebitAccount().getValue() == null))
          {
            addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0133", new String[] { "" }));
            
            return;
          }
          if (!isPartyAccountRelationship(paymentBusinessPolicyData.getDebitAccount().getValue()))
          {
            addValidationError(new ValidationError("paymentBusinessPolicy", "debitAccount", null, "DIGX_PY_0164", new String[] { "" }));
            
            return;
          }
          SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
          IAdapterFactory adapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("DEMAND_DEPOSIT_ACCOUNT_ADAPTER_FACTORY");
          
          IDemandDepositAccountAdapter adapter = (IDemandDepositAccountAdapter)adapterFactory.getAdapter("DemandDepositAccountAdapter");
          System.out.println("** Adaptor picked");
          AccountVerificationRequestDTO debitAccountRequest = new AccountVerificationRequestDTO();
          debitAccountRequest.setAccountId(paymentBusinessPolicyData.getDebitAccount());
          debitAccountRequest.setAccountType(AccountType.DEMAND_DEPOSIT);
          AccountCurrencyResponse debitAccountCurrency = adapter.fetchCurrencyForAccount(debitAccountRequest);
          if ((paymentBusinessPolicyData.getCreditAmount() != null) && 
            (!paymentBusinessPolicyData.getCreditAmount().getCurrency().equals(debitAccountCurrency.getCurrencyCode())))
          {
            addValidationError(new ValidationError("paymentBusinessPolicyData", "creditAmount", null, "DIGX_PY_0180", new String[] { "" })); 
            return;
          }
          

          boolean chk = false;
          if (paymentBusinessPolicyData.getUserRefNo() != null) {
            BillerCategoryRelationShipDTO requestDTO = new BillerCategoryRelationShipDTO();
            BillerListResponse billerListResponse;
            Iterator localIterator;
            BillerDTO billerDTO;

            int i;
            return;
          }
        }
      }
      catch (Exception e)
      {
      	if (e.getValidationErrors() == null)
          {
            addValidationError(new ValidationError());
            logger.log(Level.SEVERE, FORMATTER
              .formatMessage("Exception from validatePolicy in '%s'", new Object[] { THIS_COMPONENT_NAME, e }));
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
          
            .formatMessage("Exit from com.ofss.digx.domain.payment.entity.transfer.policy.DonationTransferBusinessPolicy.validatePolicy() ", new Object[0]));
        }
      }
    }
    
    public CardlessWithdrawalBusinessPolicyData getPaymentBusinessPolicyData()
    {
        return paymentBusinessPolicyData;
    }

    public void setPaymentBusinessPolicyData(CardlessWithdrawalBusinessPolicyData paymentBusinessPolicyData)
    {
        this.paymentBusinessPolicyData = paymentBusinessPolicyData;
    }

    private static final String THIS_COMPONENT_NAME = CardlessWithdrawalBusinessPolicy.class.getName();
    private static final MultiEntityLogger FORMATTER;
    private static final Logger logger;
    private CardlessWithdrawalBusinessPolicyData paymentBusinessPolicyData;

    static 
    {
        FORMATTER = MultiEntityLogger.getUniqueInstance();
        logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
    }
}