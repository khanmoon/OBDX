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
import com.ofss.digx.infra.thread.ThreadAttribute;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferValidateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.infra.error.ErrorManager;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonationTransferAuthentication extends AbstractApplication implements IDonationTransferAuthentication
{
  private static final String THIS_COMPONENT_NAME = DonationTransferAuthentication.class.getName();
  private MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = formatter.getLogger(THIS_COMPONENT_NAME);
  


  public DonationTransferAuthentication() {}
  


  public DonationTransferResponse validateToken(SessionContext sessionContext, DonationTransferValidateRequestDTO donationTransferValidateRequestDTO)
    throws Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
      
        .formatMessage("Entered into validate method of donation Transfer service  Input: donationTransferValidateRequestDTO: %s in class '%s'", new Object[] { donationTransferValidateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransferAuthentication.validateToken", new Object[] { sessionContext, donationTransferValidateRequestDTO });
    
    super.canonicalizeInput(donationTransferValidateRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    donationTransferValidateRequestDTO.validate(sessionContext);
    boolean isValid = false;
    DonationTransferResponse donationTransferResponse = null;
    try
    {
      DonationTransfer donationTransferDomain = new DonationTransfer();
      donationTransferResponse = new DonationTransferResponse();
      PaymentKey key = new PaymentKey();
      
      key.setId(donationTransferValidateRequestDTO.getPaymentId());
      //key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue());
      donationTransferDomain = donationTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new ValidateTokenPaymentSystemConstraint(donationTransferDomain.getStatus());
      constraint.isSatisfiedBy();
      AuthenticationAdapterFactory adapterFactory = AuthenticationAdapterFactory.getInstance();
      IAuthenticationAdapter adpter = adapterFactory.getGenerator(THIS_COMPONENT_NAME.replaceAll("Authentication", ""));
      
      isValid = adpter.validateToken(donationTransferDomain.getTokenId(), 
        ThreadAttribute.get("TOKEN_ID")
        .toString(), TokenDestinationType.SESSION);
      donationTransferResponse.setTokenAvailable(true);
      if (isValid)
      {
        donationTransferDomain.setStatus(PaymentStatusType.VERIFIED);
        donationTransferDomain.update(donationTransferDomain);
        donationTransferResponse.setTokenValid(isValid);
        donationTransferDomain = donationTransferDomain.process(donationTransferDomain);
        donationTransferResponse.setExternalReferenceId(donationTransferDomain.getTransactionReference()
          .getExternalReferenceId());
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generateDonationPaymentInitiationAlert(donationTransferDomain);
      }
      

      donationTransferResponse.setStatus(buildStatus(transactionStatus));
    }
    catch (Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the service %s while validating donation transfer payment", new Object[] {DonationTransferAuthentication.class
      
        .getName() }), e1);
      String errorCode = transactionStatus.getErrorCode();
      if ((errorCode != null) && (
        (errorCode.equals("DIGX_CMN_0014")) || 
        (errorCode.equals("DIGX_CMN_0015")) || 
        (errorCode.equals("DIGX_CMN_0016"))))
      {
        donationTransferResponse.setStatus(buildStatus(transactionStatus));
        donationTransferResponse.getStatus().getMessage().setCode(transactionStatus.getErrorCode());
        donationTransferResponse.getStatus().getMessage().setType(MessageType.ERROR);
        donationTransferResponse
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
      
        .formatMessage("Exiting validateToken of donation Transfer, SessionContext: %s, donationTransferResponse: %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return donationTransferResponse;
  }
}
