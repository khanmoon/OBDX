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
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferValidateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.infra.error.ErrorManager;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PayAnyoneTransferAuthentication extends AbstractApplication implements IPayAnyoneTransferAuthentication
{
  private static final String THIS_COMPONENT_NAME = PayAnyoneTransferAuthentication.class.getName();
  private MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = formatter.getLogger(THIS_COMPONENT_NAME);
  


  public PayAnyoneTransferAuthentication() {}
  


  public PayAnyoneTransferResponse validateToken(SessionContext sessionContext, PayAnyoneTransferValidateRequestDTO payAnyoneTransferValidateRequestDTO)
    throws Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
      
        .formatMessage("Entered into validate method of Payanyone Transfer service  Input: PayanyoneTransferValidateRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferValidateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.PayanyoneTransferAuthentication.validateToken", new Object[] { sessionContext, payAnyoneTransferValidateRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferValidateRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    payAnyoneTransferValidateRequestDTO.validate(sessionContext);
    boolean isValid = false;
    PayAnyoneTransferResponse payAnyoneTransferResponse = null;
    try
    {
      PayAnyoneTransfer payAnyoneTransferDomain = new PayAnyoneTransfer();
      payAnyoneTransferResponse = new PayAnyoneTransferResponse();
      PaymentKey key = new PaymentKey();
      
      key.setId(payAnyoneTransferValidateRequestDTO.getPaymentId());
      //key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue());
      payAnyoneTransferDomain = payAnyoneTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new ValidateTokenPaymentSystemConstraint(payAnyoneTransferDomain.getStatus());
      constraint.isSatisfiedBy();
      AuthenticationAdapterFactory adapterFactory = AuthenticationAdapterFactory.getInstance();
      IAuthenticationAdapter adpter = adapterFactory.getGenerator(THIS_COMPONENT_NAME.replaceAll("Authentication", ""));
      
      isValid = adpter.validateToken(payAnyoneTransferDomain.getTokenId(), 
        ThreadAttribute.get("TOKEN_ID")
        .toString(), TokenDestinationType.SESSION);
      payAnyoneTransferResponse.setTokenAvailable(true);
      if (isValid)
      {
        payAnyoneTransferDomain.setStatus(PaymentStatusType.VERIFIED);
        payAnyoneTransferDomain.update(payAnyoneTransferDomain);
        payAnyoneTransferResponse.setTokenValid(isValid);
        payAnyoneTransferDomain = payAnyoneTransferDomain.process(payAnyoneTransferDomain);
        payAnyoneTransferResponse.setExternalReferenceId(payAnyoneTransferDomain.getTransactionReference()
          .getExternalReferenceId());
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generatePayAnyonePaymentInitiationAlert(payAnyoneTransferDomain);
      }
      

      payAnyoneTransferResponse.setStatus(buildStatus(transactionStatus));
    }
    catch (Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the service %s while validating payanyone transfer payment", new Object[] {PayAnyoneTransferAuthentication.class
      
        .getName() }), e1);
      String errorCode = transactionStatus.getErrorCode();
      if ((errorCode != null) && (
        (errorCode.equals("DIGX_CMN_0014")) || 
        (errorCode.equals("DIGX_CMN_0015")) || 
        (errorCode.equals("DIGX_CMN_0016"))))
      {
        payAnyoneTransferResponse.setStatus(buildStatus(transactionStatus));
        payAnyoneTransferResponse.getStatus().getMessage().setCode(transactionStatus.getErrorCode());
        payAnyoneTransferResponse.getStatus().getMessage().setType(MessageType.ERROR);
        payAnyoneTransferResponse
          .getStatus()
          .getMessage()
          .setDetail(ErrorManager.buildErrorMessage(errorCode, null, null));
      }
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
      
        .formatMessage("Exiting validateToken of Payanyone Transfer, SessionContext: %s, PayanyoneTransferResponse: %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferResponse;
  }
}