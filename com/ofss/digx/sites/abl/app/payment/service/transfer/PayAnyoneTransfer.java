package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.app.AbstractApplication;
import com.ofss.digx.app.Interaction;
import com.ofss.digx.app.adapter.BusinessPolicyFactoryConfigurator;
import com.ofss.digx.app.exception.RunTimeException;
import com.ofss.digx.app.payment.assembler.service.PaymentKeyAssembler;
import com.ofss.digx.app.payment.dto.PaymentValueDateDTO;
import com.ofss.digx.app.payment.dto.PaymentValueDateHelperInputDTO;
import com.ofss.digx.app.payment.service.core.PaymentPartyRecordAccessSystemConstraint;
import com.ofss.digx.app.payment.service.core.PaymentTokenGenerationHelper;
import com.ofss.digx.app.payment.service.core.UpdateStatusPaymentSystemConstraint;
import com.ofss.digx.common.generator.ISystemReferenceValueGenerator;
import com.ofss.digx.common.generator.SystemReferenceValueGeneratorFactory;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.ModuleType;
import com.ofss.digx.enumeration.payment.PaymentModeType;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.security.authentication.entity.TokenGenerationDetails;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.PayAnyoneTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.payee.PayAnyonePayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.IPayAnyoneTransferExtExecutor;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.PayAnyonePayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.PayAnyoneTransferBusinessPolicyData;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicy;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicyFactory;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;
import com.ofss.fc.service.response.TransactionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class PayAnyoneTransfer
  extends AbstractApplication
  implements IPayAnyoneTransfer
{
  private static final String THIS_COMPONENT_NAME = PayAnyoneTransfer.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private ITextMask masker = MaskingFactory.getInstance();
  private IPayAnyoneTransferExtExecutor extensionExecutor = null;

  public PayAnyoneTransfer() {}
  
  public PayAnyoneTransferReadResponse read(SessionContext sessionContext, PayAnyoneTransferReadRequestDTO payAnyoneTransferReadRequestDTO)
    throws java.lang.Exception
  {
	System.out.println("**Entered into read method of PayAnyoneTransfer SERVICE");
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into read method of  Transfer service  Input: TransferReadRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer.read", new Object[] { sessionContext, payAnyoneTransferReadRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    PayAnyoneTransferReadResponse payAnyoneTransferReadResponse = new PayAnyoneTransferReadResponse();
    PayAnyoneTransferDTO payAnyoneTransferDTO = new PayAnyoneTransferDTO();
    try
    {
      payAnyoneTransferReadRequestDTO.validate(sessionContext);
      
      PaymentKey key = new PaymentKey();
      key.setId(payAnyoneTransferReadRequestDTO.getPaymentId());
      //key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue());
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer payAnyoneTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer();
      System.out.println("**About to call read method of PayAnyoneTransfer DOMAIN");
      payAnyoneTransferDomain = payAnyoneTransferDomain.read(key);
      System.out.println("**Called read method of PayAnyoneTransfer DOMAIN");
      
      SystemConstraint<DataTransferObject> constraint = new PaymentPartyRecordAccessSystemConstraint(sessionContext.getTransactingPartyCode(), payAnyoneTransferDomain.getPartyId());
      constraint.isSatisfiedBy();
      payAnyoneTransferDTO = new PayAnyoneTransferAssembler().frompayAnyoneDomainObjectRead(payAnyoneTransferDomain);
      PayAnyonePayeeDTO payAnyonePayeeDTO = new PayAnyonePayeeDTO();
      payAnyonePayeeDTO.setpayeeName(payAnyoneTransferDomain.getPayee().getpayeeName());
      if ((payAnyoneTransferDomain != null) && (payAnyoneTransferDomain.getPayee() != null)) {
        payAnyonePayeeDTO.setpayeeCNIC(masker.mask(payAnyoneTransferDomain.getPayee().getpayeeCNIC(), "ExternalAccountNumberMasking", "external_account_id"));
      }
      payAnyoneTransferReadResponse.setPaymentId(payAnyoneTransferDomain.getKey().getId());
      payAnyoneTransferReadResponse.setPayeeDetails(payAnyonePayeeDTO);
      payAnyoneTransferReadResponse.setTransferDetails(payAnyoneTransferDTO);
      payAnyoneTransferReadResponse.setStatus(buildStatus(status));
      payAnyoneTransferReadResponse.setPaymentType("PAYANYONE");
      
      super.encodeOutput(payAnyoneTransferReadResponse);
      super.indirectResponse(payAnyoneTransferReadResponse);
      super.checkResponsePolicy(sessionContext, payAnyoneTransferReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while reading transaction details of  transfer", new Object[] {PayAnyoneTransfer.class
        
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from read for TransferReadRequestDTO '%s'", new Object[] { payAnyoneTransferReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting read of Transfer, SessionContext: %s, TransferReadResponse: %s in class '%s' ", new Object[] { sessionContext, payAnyoneTransferReadResponse, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferReadResponse;
  }

  public PayAnyoneTransferCreateResponse create(SessionContext sessionContext, PayAnyoneTransferCreateRequestDTO payAnyoneTransferCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
	System.out.println("**Entered into create method of PayAnyoneTransfer SERVICE");
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into create method of PayAnyone Transfer service  Input: PayAnyoneTransferReadRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer.create", new Object[] { sessionContext, payAnyoneTransferCreateRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferCreateRequestDTO);
    super.indirectRequest(payAnyoneTransferCreateRequestDTO);
    Interaction.begin(sessionContext);
    
    PayAnyoneTransferCreateResponse payAnyoneTransferCreateResponse = new PayAnyoneTransferCreateResponse();
    TransactionStatus status = fetchTransactionStatus();
    TransactionReference transactionReference = new TransactionReference();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    
    PayAnyoneTransferBusinessPolicyData payAnyoneTransferPaymentBusinessPolicyData = null;
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicyFactory businessPolicyFactory = null;
    
    try
    {
      payAnyoneTransferCreateRequestDTO.validate(sessionContext);
      
      //PaymentValueDateHelper paymentValueDateHelper = PaymentValueDateHelper.getInstance();
      PaymentValueDateHelperInputDTO input = new PaymentValueDateHelperInputDTO();
      input.setAccountId(payAnyoneTransferCreateRequestDTO.getTransferDetails().getDebitAccountId().getValue());
      input.setValueDate(payAnyoneTransferCreateRequestDTO.getTransferDetails().getValueDate());
      input.setPaymentModeType(PaymentModeType.IMMEDIATE);
      
      //PaymentValueDateDTO paymentValueDateDTO = paymentValueDateHelper.getInternalPaymentValueDate(input, status);
      payAnyoneTransferCreateRequestDTO.getTransferDetails().setValueDate(input.getValueDate());
      
      PayAnyoneTransferDTO payAnyoneTransferDTO = new PayAnyoneTransferDTO();
      payAnyoneTransferDTO = payAnyoneTransferCreateRequestDTO.getTransferDetails();
      payAnyoneTransferDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));
      
      payAnyoneTransferDTO.setpayeeAddress(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeAddress());
      payAnyoneTransferDTO.setpayeeCity(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeCity());
      payAnyoneTransferDTO.setpayeeCNIC(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeCNIC());
      payAnyoneTransferDTO.setpayeeEmail(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeEmail());
      payAnyoneTransferDTO.setpayeeMobile(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeMobile());
      payAnyoneTransferDTO.setpayeeName(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpayeeName());
      payAnyoneTransferDTO.setpaymentType(payAnyoneTransferCreateRequestDTO.getTransferDetails().getpaymentType());
      payAnyoneTransferDTO.setRemitterName(payAnyoneTransferCreateRequestDTO.getTransferDetails().getRemitterName());
      
      payAnyoneTransferPaymentBusinessPolicyData = new PayAnyoneTransferBusinessPolicyData();
      payAnyoneTransferPaymentBusinessPolicyData.setRemarks(payAnyoneTransferCreateRequestDTO.getTransferDetails()
        .getRemarks());
      payAnyoneTransferPaymentBusinessPolicyData.setDebitAccount(payAnyoneTransferCreateRequestDTO
        .getTransferDetails().getDebitAccountId());
      payAnyoneTransferPaymentBusinessPolicyData.setCreditAmount(payAnyoneTransferCreateRequestDTO
        .getTransferDetails().getAmount());
      payAnyoneTransferPaymentBusinessPolicyData.setPartyId(sessionContext.getTransactingPartyCode());
      
      businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("CUSTOMPAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("PAYMENT_PAYANYONE_TRANSFER_BUSINESS_POLICY", payAnyoneTransferPaymentBusinessPolicyData);   
      abstractBusinessPolicy.validate("DIGX_PY_0131");
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer payAnyoneTransferDomain = null;
      payAnyoneTransferDomain = new PayAnyoneTransferAssembler().toPayAnyoneDomainObjectCreate(payAnyoneTransferDTO);

      PaymentKey paymentKey = new PaymentKeyAssembler().generatePaymentKey();
      payAnyoneTransferDomain.setKey(paymentKey);

      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(payAnyoneTransferDTO.getUserReferenceNo());
      payAnyoneTransferDomain.setTransactionReference(transactionReference);
      
      payAnyoneTransferDomain.setStatus(PaymentStatusType.INITIATED);
      System.out.println("**About to call create method of PayAnyoneTransfer DOMAIN");
      payAnyoneTransferDomain.create(payAnyoneTransferDomain);
      System.out.println("**Called create method of PayAnyoneTransfer DOMAIN");
      
      payAnyoneTransferCreateResponse.setPaymentId(payAnyoneTransferDomain.getKey().getId());
      payAnyoneTransferCreateResponse.setStatus(buildStatus(status));
      //paymentValueDateHelper.setWarning(paymentValueDateDTO, payAnyoneTransferCreateResponse.getStatus());

      super.encodeOutput(payAnyoneTransferCreateResponse);
      super.checkResponsePolicy(sessionContext, payAnyoneTransferCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Fatal Exception from create for PayAnyone Transfer Create Request '%s'", new Object[] { payAnyoneTransferCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RunTimeException from create for PayAnyone Transfer Create Request '%s'", new Object[] { payAnyoneTransferCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {PayAnyoneTransfer.class
      
        .getName() }), e);
      fillTransactionStatus(status, e);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting create of PayAnyoneTransfer Service, SessionContext: %s, PayAnyoneTransferCreateResponse: %s ", new Object[] { sessionContext, payAnyoneTransferCreateResponse, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferCreateResponse;
  }
  

  public PayAnyoneTransferResponse updateStatus(SessionContext sessionContext, PayAnyoneTransferUpdateRequestDTO payAnyoneTransferUpdateRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into update Status method of  Transfer service  Input: TransferUpdateRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer.updateStatus", new Object[] { sessionContext, payAnyoneTransferUpdateRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferUpdateRequestDTO);
    Interaction.begin(sessionContext);
    PayAnyoneTransferResponse payAnyoneTransferResponse = new PayAnyoneTransferResponse();
    payAnyoneTransferResponse.setStatus(fetchStatus());
    TransactionStatus transactionStatus = fetchTransactionStatus();
    try
    {
      payAnyoneTransferUpdateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), payAnyoneTransferUpdateRequestDTO
        .getPaymentId(), "PayAnyone Transfer Payment", true, null);
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer payAnyoneTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer();
      PaymentKey key = new PaymentKey();
      key.setId(payAnyoneTransferUpdateRequestDTO.getPaymentId());
      //key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue());
      payAnyoneTransferDomain = payAnyoneTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new UpdateStatusPaymentSystemConstraint(payAnyoneTransferDomain.getStatus(), payAnyoneTransferDomain.getDebitAccountId());
      
      constraint.isSatisfiedBy();
      if ((tokenGenerationDetails != null) && (tokenGenerationDetails.isTokenAllowed()))
      {
        payAnyoneTransferResponse.setTokenAvailable(tokenGenerationDetails.isTokenAllowed());
        payAnyoneTransferDomain.setStatus(PaymentStatusType.PENDINGVERIFICATION);
        payAnyoneTransferDomain.setTokenId(tokenGenerationDetails.getUid());
      }
      else
      {
        payAnyoneTransferResponse.setTokenAvailable(false);
        payAnyoneTransferDomain.setStatus(PaymentStatusType.VERIFIED);
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generatePayAnyonePaymentInitiationAlert(payAnyoneTransferDomain);
      }
      payAnyoneTransferDomain.update(payAnyoneTransferDomain);
      if (!tokenGenerationDetails.isTokenAllowed())
      {
        payAnyoneTransferDomain = payAnyoneTransferDomain.process(payAnyoneTransferDomain);
        payAnyoneTransferResponse.setExternalReferenceId(payAnyoneTransferDomain.getTransactionReference()
          .getExternalReferenceId());
      }
      payAnyoneTransferResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(payAnyoneTransferResponse);
      super.checkResponsePolicy(sessionContext, payAnyoneTransferResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating payanyone transfer payment", new Object[] {PayAnyoneTransfer.class
      
        .getName() }), e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from upadte for PayanyoneTransferUpdateRequestDTO '%s'", new Object[] { payAnyoneTransferUpdateRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting updateStatus of Payanyone Transfer, SessionContext: %s, PayanyoneTransferResponse: %s in class '%s' ", new Object[] { sessionContext, payAnyoneTransferResponse, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferResponse;
  }
  
  public PayAnyoneTransferListResponse list(SessionContext sessionContext, PayAnyoneTransferListRequestDTO payAnyoneTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER.formatMessage("Entered into list of PayAnyone Transfer service: PayAnyoneTransferListRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.PayAnyoneTransfer.list", new Object[] { sessionContext, payAnyoneTransferListRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    PayAnyoneTransferListResponse payAnyoneTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer payAnyoneTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer> payAnyoneTransferListDomain = new ArrayList();
    
    try
    {
      payAnyoneTransferListDomain = payAnyoneTransferDomain.list(sessionContext.getTransactingPartyCode(), payAnyoneTransferListRequestDTO
        .getFromDate(), payAnyoneTransferListRequestDTO.getToDate(), payAnyoneTransferListRequestDTO
        .getStatus());
      payAnyoneTransferListResponse = new PayAnyoneTransferListResponse();
      payAnyoneTransferListResponse.setTransfers(new PayAnyoneTransferAssembler().fromPayAnyoneDomainObjectList(payAnyoneTransferListDomain));
      payAnyoneTransferListResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(payAnyoneTransferListResponse);
      super.checkResponsePolicy(sessionContext, payAnyoneTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing payAnyone transfer payments", new Object[] {PayAnyoneTransfer.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from list for PayAnyoneTransferListRequestDTO '%s'", new Object[] { payAnyoneTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting list of PayAnyone Transfer, SessionContext: %s, PayAnyoneTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferListResponse;
  }
  
  public PayAnyoneTransferListResponse lastPaymentList(SessionContext sessionContext, PayAnyoneTransferListRequestDTO payAnyoneTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into lastPaymentList of PayAnyone Transfer service: PayAnyoneTransferListRequestDTO: %s in class '%s'", new Object[] { payAnyoneTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.PayAnyoneTransfer.lastPaymentList", new Object[] { sessionContext, payAnyoneTransferListRequestDTO });
    
    super.canonicalizeInput(payAnyoneTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    PayAnyoneTransferListResponse payAnyoneTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer payAnyoneTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer> payAnyoneTransferListDomain = new ArrayList();
    
    try
    {
      payAnyoneTransferListDomain = payAnyoneTransferDomain.lastPaymentList(payAnyoneTransferListRequestDTO
        .getFromDate());
      payAnyoneTransferListResponse = new PayAnyoneTransferListResponse();
      payAnyoneTransferListResponse.setTransfers(new PayAnyoneTransferAssembler().fromPayAnyoneDomainObjectList(payAnyoneTransferListDomain));
      payAnyoneTransferListResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(payAnyoneTransferListResponse);
      super.checkResponsePolicy(sessionContext, payAnyoneTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while listing payAnyone transfer lastPaymentList", new Object[] {PayAnyoneTransfer.class
        
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from lastPaymentList for PayAnyoneTransferListRequestDTO '%s'", new Object[] { payAnyoneTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting lastPaymentList of PayAnyone Transfer, SessionContext: %s, PayAnyoneTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return payAnyoneTransferListResponse;
  }
}
