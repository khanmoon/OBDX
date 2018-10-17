// Decompiled by DJ v3.12.12.98 Copyright 2014 Atanas Neshkov  Date: 10-Apr-18 2:55:20 PM
// Home Page: http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   MerchantTransferAuthentication.java

package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.app.AbstractApplication;
import com.ofss.digx.app.Interaction;
import com.ofss.digx.app.messages.Message;
import com.ofss.digx.app.messages.Status;
import com.ofss.digx.app.payment.service.core.ValidateTokenPaymentSystemConstraint;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.MessageType;
import com.ofss.digx.enumeration.authentication.TokenDestinationType;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.security.authentication.AuthenticationAdapterFactory;
import com.ofss.digx.framework.security.authentication.IAuthenticationAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.infra.thread.ThreadAttribute;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalValidateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.infra.error.ErrorManager;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.logging.Level;
import java.util.logging.Logger;

// Referenced classes of package com.ofss.digx.sites.abl.app.payment.service.transfer:
//            IMerchantTransferAuthentication

public class CardlessWithdrawalAuthentication extends AbstractApplication
    implements ICardlessWithdrawalAuthentication
{
	private static final String THIS_COMPONENT_NAME = CardlessWithdrawalAuthentication.class.getName();
	private MultiEntityLogger formatter;
	private transient Logger logger;
	
    public CardlessWithdrawalAuthentication()
    {
        formatter = MultiEntityLogger.getUniqueInstance();
        logger = formatter.getLogger(THIS_COMPONENT_NAME);
    }

    public CardlessWithdrawalResponse validateToken(SessionContext sessionContext, CardlessWithdrawalValidateRequestDTO cashlessWithdrawalValidateRequestDTO)
        throws Exception
    {
        TransactionStatus transactionStatus;
        CardlessWithdrawalResponse cashlessWithdrawalResponse;
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, formatter.formatMessage("Entered into validate method of merchant Transfer service  Input: merchantTransferValidateRequestDTO: %s in class '%s'", new Object[] {
                cashlessWithdrawalValidateRequestDTO, THIS_COMPONENT_NAME
            }));
        //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.merchantTransferAuthentication.validateToken", new Object[] {sessionContext, merchantTransferValidateRequestDTO});
        super.canonicalizeInput(cashlessWithdrawalValidateRequestDTO);
        Interaction.begin(sessionContext);
        transactionStatus = fetchTransactionStatus();
        cashlessWithdrawalValidateRequestDTO.validate(sessionContext);
        boolean isValid = false;
        cashlessWithdrawalResponse = null;
        try
        {
        	CardlessWithdrawalDomain cashlessWithdrawalDomain = new CardlessWithdrawalDomain();
            cashlessWithdrawalResponse = new CardlessWithdrawalResponse();
            PaymentKey key = new PaymentKey();
            key.setId(cashlessWithdrawalValidateRequestDTO.getPaymentId());
            key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(CardlessWithdrawalDomain.class.getName()));
            cashlessWithdrawalDomain = cashlessWithdrawalDomain.read(key);
            SystemConstraint constraint = new ValidateTokenPaymentSystemConstraint(cashlessWithdrawalDomain.getStatus());
            constraint.isSatisfiedBy();
            AuthenticationAdapterFactory adapterFactory = AuthenticationAdapterFactory.getInstance();
            IAuthenticationAdapter adpter = adapterFactory.getGenerator(THIS_COMPONENT_NAME.replaceAll("Authentication", ""));
            isValid = adpter.validateToken(cashlessWithdrawalDomain.getTokenId(), ThreadAttribute.get("TOKEN_ID").toString(), TokenDestinationType.SESSION);
            cashlessWithdrawalResponse.setTokenAvailable(true);
            if(isValid)
            {
                cashlessWithdrawalDomain.setStatus(PaymentStatusType.VERIFIED);
                cashlessWithdrawalDomain.update(cashlessWithdrawalDomain);
                cashlessWithdrawalResponse.setTokenValid(isValid);
                cashlessWithdrawalDomain = cashlessWithdrawalDomain.process(cashlessWithdrawalDomain);
                cashlessWithdrawalResponse.setExternalReferenceId(cashlessWithdrawalDomain.getTransactionReference().getExternalReferenceId());
                PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
                alertHelper.generateCardlessWithdrawalPaymentInitiationAlert(cashlessWithdrawalDomain);
            }
            cashlessWithdrawalResponse.setStatus(buildStatus(transactionStatus));
        }
        catch(Exception e1)
        {
            fillTransactionStatus(transactionStatus, e1);
            logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the service %s while validating merchant transfer payment", new Object[] {
                CardlessWithdrawalAuthentication.class.getName()
            }), e1);
            String errorCode = transactionStatus.getErrorCode();
            if(errorCode != null && (errorCode.equals("DIGX_CMN_0014") || errorCode.equals("DIGX_CMN_0015") || errorCode.equals("DIGX_CMN_0016")))
            {
                cashlessWithdrawalResponse.setStatus(buildStatus(transactionStatus));
                cashlessWithdrawalResponse.getStatus().getMessage().setCode(transactionStatus.getErrorCode());
                cashlessWithdrawalResponse.getStatus().getMessage().setType(MessageType.ERROR);
                cashlessWithdrawalResponse.getStatus().getMessage().setDetail(ErrorManager.buildErrorMessage(errorCode, null, null));
            }
        }
        finally{
        Interaction.close();
        }
        if(logger.isLoggable(Level.FINE))
            logger.log(Level.FINE, formatter.formatMessage("Exiting validateToken of merchant Transfer, SessionContext: %s, merchantTransferResponse: %s in class '%s' ", new Object[] {
                sessionContext, transactionStatus, THIS_COMPONENT_NAME
            }));
        return cashlessWithdrawalResponse;
    }

 

}