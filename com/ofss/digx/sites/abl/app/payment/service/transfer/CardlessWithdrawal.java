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
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.CardlessWithdrawalAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.MerchantTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.payee.CardlessWithdrawalPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.ICardlessWithdrawalExtExecutor;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.CardlessWithdrawalPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.CardlessWithdrawalBusinessPolicyData;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.app.ops.service.dms.channel.AlertHelper;
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






public class CardlessWithdrawal
  extends AbstractApplication
  implements ICardlessWithdrawal
{
  private static final String THIS_COMPONENT_NAME = CardlessWithdrawal.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private ITextMask masker = MaskingFactory.getInstance();
  private ICardlessWithdrawalExtExecutor extensionExecutor = null;
  



  public CardlessWithdrawal() {}
  


  public CardlessWithdrawalReadResponse read(SessionContext sessionContext,CardlessWithdrawalReadRequestDTO cardlessWithdrawalReadRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into read method of  Transfer service  Input: TransferReadRequestDTO: %s in class '%s'", new Object[] { cardlessWithdrawalReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransfer.read", new Object[] { sessionContext, merchantTransferReadRequestDTO });
    
    super.canonicalizeInput(cardlessWithdrawalReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    CardlessWithdrawalReadResponse cardlessWithdrawalReadResponse = new CardlessWithdrawalReadResponse();
    CardlessWithdrawalDTO cardlessWithdrawalDTO = new CardlessWithdrawalDTO();
    try
    {
      cardlessWithdrawalReadRequestDTO.validate(sessionContext);
      
      PaymentKey key = new PaymentKey();
      key.setId(cardlessWithdrawalReadRequestDTO.getPaymentId());
      key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(CardlessWithdrawal.class.getName()));
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain cardlessWithdrawalDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain();
      cardlessWithdrawalDomain = cardlessWithdrawalDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new PaymentPartyRecordAccessSystemConstraint(sessionContext.getTransactingPartyCode(), cardlessWithdrawalDomain.getPartyId());
      constraint.isSatisfiedBy();
      cardlessWithdrawalDTO = new CardlessWithdrawalAssembler().fromCardlessWithdrawalDomainObjectRead(cardlessWithdrawalDomain);
      CardlessWithdrawalPayeeDTO cardlessWithdrawalPayeeDTO = new CardlessWithdrawalPayeeDTO();
      
      if ((cardlessWithdrawalDomain != null) && (cardlessWithdrawalDomain.getPayee() != null)) {
        cardlessWithdrawalPayeeDTO.setTpin(masker.mask(cardlessWithdrawalDomain.getPayee().getTpin(), "ExternalAccountNumberMasking", "external_account_id"));
      }
      
      cardlessWithdrawalReadResponse.setPaymentId(cardlessWithdrawalDomain.getKey().getId());
      cardlessWithdrawalReadResponse.setPayeeDetails(cardlessWithdrawalPayeeDTO);
      cardlessWithdrawalReadResponse.setTransferDetails(cardlessWithdrawalDTO);
      cardlessWithdrawalReadResponse.setStatus(buildStatus(status));
      cardlessWithdrawalReadResponse.setPaymentType("CARDLESSWITHDRAWAL");
      
      super.encodeOutput(cardlessWithdrawalReadResponse);
      super.indirectResponse(cardlessWithdrawalReadResponse);
      super.checkResponsePolicy(sessionContext, cardlessWithdrawalReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while reading transaction details of  transfer", new Object[] {CardlessWithdrawal.class
        
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from read for TransferReadRequestDTO '%s'", new Object[] { cardlessWithdrawalReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting read of Transfer, SessionContext: %s, TransferReadResponse: %s in class '%s' ", new Object[] { sessionContext, cardlessWithdrawalReadResponse, THIS_COMPONENT_NAME }));
    }
    return cardlessWithdrawalReadResponse;
  }
  



  public CardlessWithdrawalCreateResponse create(SessionContext sessionContext, CardlessWithdrawalCreateRequestDTO cardlessWithdrawalCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into create method of Merchant Transfer service  Input: CardlessWithdrawalReadRequestDTO: %s in class '%s'", new Object[] { cardlessWithdrawalCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal.create", new Object[] { sessionContext, cardlessWithdrawalCreateRequestDTO });
    
    super.canonicalizeInput(cardlessWithdrawalCreateRequestDTO);
    super.indirectRequest(cardlessWithdrawalCreateRequestDTO);
    Interaction.begin(sessionContext);
    
    CardlessWithdrawalCreateResponse cardlessWithdrawalCreateResponse = new CardlessWithdrawalCreateResponse();
    TransactionStatus status = fetchTransactionStatus();
    TransactionReference transactionReference = new TransactionReference();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    
    CardlessWithdrawalBusinessPolicyData cardlessWithdrawalPaymentBusinessPolicyData = null;
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicyFactory businessPolicyFactory = null;
    
    try
    {
      cardlessWithdrawalCreateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper paymentValueDateHelper = PaymentTokenGenerationHelper.getInstance();
      PaymentValueDateHelperInputDTO input = new PaymentValueDateHelperInputDTO();
      input.setAccountId(cardlessWithdrawalCreateRequestDTO.getTransferDetails().getDebitAccountId().getValue());
      input.setValueDate(cardlessWithdrawalCreateRequestDTO.getTransferDetails().getValueDate());
      input.setPaymentModeType(PaymentModeType.IMMEDIATE);
      
      //PaymentValueDateDTO paymentValueDateDTO = paymentValueDateHelper.getInternalPaymentValueDate(input, status);
      cardlessWithdrawalCreateRequestDTO.getTransferDetails().setValueDate(input.getValueDate());
      
      CardlessWithdrawalDTO cardlessWithdrawalDTO = new CardlessWithdrawalDTO();
      cardlessWithdrawalDTO = cardlessWithdrawalCreateRequestDTO.getTransferDetails();
      cardlessWithdrawalDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));
      
      cardlessWithdrawalDTO.setTpin(cardlessWithdrawalCreateRequestDTO.getTransferDetails().getTpin());
      


      cardlessWithdrawalPaymentBusinessPolicyData = new CardlessWithdrawalBusinessPolicyData();
      

      cardlessWithdrawalPaymentBusinessPolicyData.setRemarks(cardlessWithdrawalCreateRequestDTO.getTransferDetails()
        .getRemarks());
      







      cardlessWithdrawalPaymentBusinessPolicyData.setDebitAccount(cardlessWithdrawalCreateRequestDTO
        .getTransferDetails().getDebitAccountId());
      cardlessWithdrawalPaymentBusinessPolicyData.setCreditAmount(cardlessWithdrawalCreateRequestDTO
        .getTransferDetails().getAmount());
      cardlessWithdrawalPaymentBusinessPolicyData.setPartyId(sessionContext.getTransactingPartyCode());
      

      cardlessWithdrawalPaymentBusinessPolicyData.setUserRefNo(cardlessWithdrawalCreateRequestDTO.getTransferDetails().getWithdrawalRef());
      
      businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("CUSTOMPAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("PAYMENT_CARDLESSWITHDRAWAL_BUSINESS_POLICY", cardlessWithdrawalPaymentBusinessPolicyData);
      
      abstractBusinessPolicy.validate("DIGX_PY_0131");
      


      com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain cardlessWithdrawalDomain = null;
      cardlessWithdrawalDomain = new CardlessWithdrawalAssembler().toCardlessWithdrawalDomainObjectCreate(cardlessWithdrawalDTO);
      











      PaymentKey paymentKey = new PaymentKeyAssembler().generatePaymentKey();
      cardlessWithdrawalDomain.setKey(paymentKey);
      



      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(cardlessWithdrawalDTO.getUserReferenceNo());
      cardlessWithdrawalDomain.setTransactionReference(transactionReference);
      
      cardlessWithdrawalDomain.setStatus(PaymentStatusType.INITIATED);
      cardlessWithdrawalDomain.create(cardlessWithdrawalDomain);
      
      cardlessWithdrawalCreateResponse.setPaymentId(cardlessWithdrawalDomain.getKey().getId());
      cardlessWithdrawalCreateResponse.setStatus(buildStatus(status));
      //paymentValueDateHelper.setWarning(paymentValueDateDTO, merchantTransferCreateResponse.getStatus());
      

      super.encodeOutput(cardlessWithdrawalCreateResponse);
      super.checkResponsePolicy(sessionContext, cardlessWithdrawalCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Fatal Exception from create for CardlessWithdrawal Create Request '%s'", new Object[] { cardlessWithdrawalCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RunTimeException from create for CardlessWithdrawal Create Request '%s'", new Object[] { cardlessWithdrawalCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {CardlessWithdrawal.class
      
        .getName() }), e);
      fillTransactionStatus(status, e);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting create of CardlessWithdrawal Service, SessionContext: %s, MerchantTransferCreateResponse: %s ", new Object[] { sessionContext, cardlessWithdrawalCreateResponse, THIS_COMPONENT_NAME }));
    }
    return cardlessWithdrawalCreateResponse;
  }
  

  public CardlessWithdrawalResponse updateStatus(SessionContext sessionContext, CardlessWithdrawalUpdateRequestDTO cardlessWithdrawalUpdateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into update Status method of  Transfer service  Input: TransferUpdateRequestDTO: %s in class '%s'", new Object[] { cardlessWithdrawalUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal.updateStatus", new Object[] { sessionContext, cardlessWithdrawalUpdateRequestDTO });
    
    super.canonicalizeInput(cardlessWithdrawalUpdateRequestDTO);
    Interaction.begin(sessionContext);
    CardlessWithdrawalResponse cardlessWithdrawalResponse = new CardlessWithdrawalResponse();
    cardlessWithdrawalResponse.setStatus(fetchStatus());
    TransactionStatus transactionStatus = fetchTransactionStatus();
    try
    {
      cardlessWithdrawalUpdateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), cardlessWithdrawalUpdateRequestDTO
        .getPaymentId(), "Merchant Transfer Payment", true, null);
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain cardlessWithdrawalDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain();
      PaymentKey key = new PaymentKey();
      key.setId(cardlessWithdrawalUpdateRequestDTO.getPaymentId());
      key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain.class.getName()));
      cardlessWithdrawalDomain = cardlessWithdrawalDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new UpdateStatusPaymentSystemConstraint(cardlessWithdrawalDomain.getStatus(), cardlessWithdrawalDomain.getDebitAccountId());
      
      constraint.isSatisfiedBy();
      if ((tokenGenerationDetails != null) && (tokenGenerationDetails.isTokenAllowed()))
      {
        cardlessWithdrawalResponse.setTokenAvailable(tokenGenerationDetails.isTokenAllowed());
        cardlessWithdrawalDomain.setStatus(PaymentStatusType.PENDINGVERIFICATION);
        cardlessWithdrawalDomain.setTokenId(tokenGenerationDetails.getUid());
      }
      else
      {
        cardlessWithdrawalResponse.setTokenAvailable(false);
        cardlessWithdrawalDomain.setStatus(PaymentStatusType.VERIFIED);
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generateCardlessWithdrawalPaymentInitiationAlert(cardlessWithdrawalDomain);
      }
      cardlessWithdrawalDomain.update(cardlessWithdrawalDomain);
      if (!tokenGenerationDetails.isTokenAllowed())
      {
        cardlessWithdrawalDomain = cardlessWithdrawalDomain.process(cardlessWithdrawalDomain);
        cardlessWithdrawalResponse.setExternalReferenceId(cardlessWithdrawalDomain.getTransactionReference()
          .getExternalReferenceId());
      }
      cardlessWithdrawalResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(cardlessWithdrawalResponse);
      super.checkResponsePolicy(sessionContext, cardlessWithdrawalResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);      
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating cash withdrawal payment", new Object[] {CardlessWithdrawal.class
      
        .getName() }), e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from upadte for cardlessWithdrawalUpdateRequestDTO '%s'", new Object[] { cardlessWithdrawalUpdateRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting updateStatus of merchant Transfer, SessionContext: %s, cardlessWithdrawalResponse: %s in class '%s' ", new Object[] { sessionContext, cardlessWithdrawalResponse, THIS_COMPONENT_NAME }));
    }
    return cardlessWithdrawalResponse;
  }
  
  public CardlessWithdrawalListResponse list(SessionContext sessionContext, CardlessWithdrawalListRequestDTO cardlessWithdrawalListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER.formatMessage("Entered into list of Merchant Transfer service: MerchantTransferListRequestDTO: %s in class '%s'", new Object[] { cardlessWithdrawalListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MerchantTransfer.list", new Object[] { sessionContext, merchantTransferListRequestDTO });
    
    super.canonicalizeInput(cardlessWithdrawalListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    CardlessWithdrawalListResponse cardlessWithdrawalListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain cardlessWithdrawalDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain> cardlessWithdrawalListDomain = new ArrayList();
    
    try
    {
      cardlessWithdrawalListDomain = cardlessWithdrawalDomain.list(sessionContext.getTransactingPartyCode(), cardlessWithdrawalListRequestDTO
        .getFromDate(), cardlessWithdrawalListRequestDTO.getToDate(), cardlessWithdrawalListRequestDTO
        .getStatus());
      cardlessWithdrawalListResponse = new CardlessWithdrawalListResponse();
      cardlessWithdrawalListResponse.setTransfers(new CardlessWithdrawalAssembler().fromCardlessWithdrawalDomainObjectList(cardlessWithdrawalListDomain));
      cardlessWithdrawalListResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(cardlessWithdrawalListResponse);
      super.checkResponsePolicy(sessionContext, cardlessWithdrawalListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing Cardless Withdrawal payments", new Object[] {CardlessWithdrawal.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from list for CardlessWithdrawalListRequestDTO '%s'", new Object[] { cardlessWithdrawalListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting list of Merchant Transfer, SessionContext: %s, MerchantTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return cardlessWithdrawalListResponse;
  }
  
  public CardlessWithdrawalListResponse lastPaymentList(SessionContext sessionContext, CardlessWithdrawalListRequestDTO cardlessWithdrawalListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into lastPaymentList of Merchant Transfer service: MerchantTransferListRequestDTO: %s in class '%s'", new Object[] { cardlessWithdrawalListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MerchantTransfer.lastPaymentList", new Object[] { sessionContext, merchantTransferListRequestDTO });
    
    super.canonicalizeInput(cardlessWithdrawalListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    CardlessWithdrawalListResponse cardlessWithdrawalListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain cardlessWithdrawalDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain> cardlessWithdrawalListDomain = new ArrayList();
    
    try
    {
      cardlessWithdrawalListDomain = cardlessWithdrawalDomain.lastPaymentList(cardlessWithdrawalListRequestDTO
        .getFromDate());
      cardlessWithdrawalListResponse = new CardlessWithdrawalListResponse();
      cardlessWithdrawalListResponse.setTransfers(new CardlessWithdrawalAssembler().fromCardlessWithdrawalDomainObjectList(cardlessWithdrawalListDomain));
      cardlessWithdrawalListResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(cardlessWithdrawalListResponse);
      super.checkResponsePolicy(sessionContext, cardlessWithdrawalListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while listing Cardless withdrawal lastPaymentList", new Object[] {CardlessWithdrawal.class
        
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from lastPaymentList for CardlessWithdrawalListRequestDTO '%s'", new Object[] { cardlessWithdrawalListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting lastPaymentList of Cardless withdrawal, SessionContext: %s, CardlessWithdrawalListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return cardlessWithdrawalListResponse;
  }
}