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
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.MerchantTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MerchantPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.IMerchantTransferExtExecutor;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MerchantPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.MerchantTransferBusinessPolicyData;
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






public class MerchantTransferService
  extends AbstractApplication
  implements IMerchantTransfer
{
  private static final String THIS_COMPONENT_NAME = MerchantTransferService.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private ITextMask masker = MaskingFactory.getInstance();
  private IMerchantTransferExtExecutor extensionExecutor = null;
  



  public MerchantTransferService() {}
  


  public MerchantTransferReadResponse read(SessionContext sessionContext, MerchantTransferReadRequestDTO merchantTransferReadRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into read method of  Transfer service  Input: TransferReadRequestDTO: %s in class '%s'", new Object[] { merchantTransferReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransfer.read", new Object[] { sessionContext, merchantTransferReadRequestDTO });
    
    super.canonicalizeInput(merchantTransferReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    MerchantTransferReadResponse merchantTransferReadResponse = new MerchantTransferReadResponse();
    MerchantTransferDTO merchantTransferDTO = new MerchantTransferDTO();
    try
    {
      merchantTransferReadRequestDTO.validate(sessionContext);
      
      PaymentKey key = new PaymentKey();
      key.setId(merchantTransferReadRequestDTO.getPaymentId());
      key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(MerchantTransferService.class.getName()));
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain merchantTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain();
      merchantTransferDomain = merchantTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new PaymentPartyRecordAccessSystemConstraint(sessionContext.getTransactingPartyCode(), merchantTransferDomain.getPartyId());
      constraint.isSatisfiedBy();
      merchantTransferDTO = new MerchantTransferAssembler().frommerchantDomainObjectRead(merchantTransferDomain);
      MerchantPayeeDTO merchantPayeeDTO = new MerchantPayeeDTO();
      
      if ((merchantTransferDomain != null) && (merchantTransferDomain.getPayee() != null)) {
        merchantPayeeDTO.setbillerId(masker.mask(merchantTransferDomain.getPayee().getbillerId(), "ExternalAccountNumberMasking", "external_account_id"));
      }
      merchantTransferReadResponse.setPaymentId(merchantTransferDomain.getKey().getId());
      merchantTransferReadResponse.setPayeeDetails(merchantPayeeDTO);
      merchantTransferReadResponse.setTransferDetails(merchantTransferDTO);
      merchantTransferReadResponse.setStatus(buildStatus(status));
      merchantTransferReadResponse.setPaymentType("MERCHANT");
      
      super.encodeOutput(merchantTransferReadResponse);
      super.indirectResponse(merchantTransferReadResponse);
      super.checkResponsePolicy(sessionContext, merchantTransferReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while reading transaction details of  transfer", new Object[] {MerchantTransferService.class
        
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from read for TransferReadRequestDTO '%s'", new Object[] { merchantTransferReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting read of Transfer, SessionContext: %s, TransferReadResponse: %s in class '%s' ", new Object[] { sessionContext, merchantTransferReadResponse, THIS_COMPONENT_NAME }));
    }
    return merchantTransferReadResponse;
  }
  



  public MerchantTransferCreateResponse create(SessionContext sessionContext, MerchantTransferCreateRequestDTO merchantTransferCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into create method of Merchant Transfer service  Input: MerchantTransferReadRequestDTO: %s in class '%s'", new Object[] { merchantTransferCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransfer.create", new Object[] { sessionContext, merchantTransferCreateRequestDTO });
    
    super.canonicalizeInput(merchantTransferCreateRequestDTO);
    super.indirectRequest(merchantTransferCreateRequestDTO);
    Interaction.begin(sessionContext);
    
    MerchantTransferCreateResponse merchantTransferCreateResponse = new MerchantTransferCreateResponse();
    TransactionStatus status = fetchTransactionStatus();
    TransactionReference transactionReference = new TransactionReference();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    
    MerchantTransferBusinessPolicyData merchantTransferPaymentBusinessPolicyData = null;
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicyFactory businessPolicyFactory = null;
    
    try
    {
      merchantTransferCreateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper paymentValueDateHelper = PaymentTokenGenerationHelper.getInstance();
      PaymentValueDateHelperInputDTO input = new PaymentValueDateHelperInputDTO();
      input.setAccountId(merchantTransferCreateRequestDTO.getTransferDetails().getDebitAccountId().getValue());
      input.setValueDate(merchantTransferCreateRequestDTO.getTransferDetails().getValueDate());
      input.setPaymentModeType(PaymentModeType.IMMEDIATE);
      
      //PaymentValueDateDTO paymentValueDateDTO = paymentValueDateHelper.getInternalPaymentValueDate(input, status);
      merchantTransferCreateRequestDTO.getTransferDetails().setValueDate(input.getValueDate());
      
      MerchantTransferDTO merchantTransferDTO = new MerchantTransferDTO();
      merchantTransferDTO = merchantTransferCreateRequestDTO.getTransferDetails();
      merchantTransferDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));
      
      merchantTransferDTO.setbillerId(merchantTransferCreateRequestDTO.getTransferDetails().getbillerId());
      


      merchantTransferPaymentBusinessPolicyData = new MerchantTransferBusinessPolicyData();
      

      merchantTransferPaymentBusinessPolicyData.setRemarks(merchantTransferCreateRequestDTO.getTransferDetails()
        .getRemarks());
      







      merchantTransferPaymentBusinessPolicyData.setDebitAccount(merchantTransferCreateRequestDTO
        .getTransferDetails().getDebitAccountId());
      merchantTransferPaymentBusinessPolicyData.setCreditAmount(merchantTransferCreateRequestDTO
        .getTransferDetails().getAmount());
      merchantTransferPaymentBusinessPolicyData.setPartyId(sessionContext.getTransactingPartyCode());
      

      merchantTransferPaymentBusinessPolicyData.setUserRefNo(merchantTransferCreateRequestDTO.getTransferDetails().getbillerId());
      
      businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("CUSTOMPAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("PAYMENT_MERCHANT_BUSINESS_POLICY", merchantTransferPaymentBusinessPolicyData);
      
      abstractBusinessPolicy.validate("DIGX_PY_0131");
      


      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain merchantTransferDomain = null;
      merchantTransferDomain = new MerchantTransferAssembler().toMerchantDomainObjectCreate(merchantTransferDTO);
      











      PaymentKey paymentKey = new PaymentKeyAssembler().generatePaymentKey();
      merchantTransferDomain.setKey(paymentKey);
      



      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(merchantTransferDTO.getUserReferenceNo());
      merchantTransferDomain.setTransactionReference(transactionReference);
      
      merchantTransferDomain.setStatus(PaymentStatusType.INITIATED);
      merchantTransferDomain.create(merchantTransferDomain);
      
      merchantTransferCreateResponse.setPaymentId(merchantTransferDomain.getKey().getId());
      merchantTransferCreateResponse.setStatus(buildStatus(status));
      //paymentValueDateHelper.setWarning(paymentValueDateDTO, merchantTransferCreateResponse.getStatus());
      

      super.encodeOutput(merchantTransferCreateResponse);
      super.checkResponsePolicy(sessionContext, merchantTransferCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Fatal Exception from create for Merchant Transfer Create Request '%s'", new Object[] { merchantTransferCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RunTimeException from create for Merchant Transfer Create Request '%s'", new Object[] { merchantTransferCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {MerchantTransferService.class
      
        .getName() }), e);
      fillTransactionStatus(status, e);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting create of MerchantTransfer Service, SessionContext: %s, MerchantTransferCreateResponse: %s ", new Object[] { sessionContext, merchantTransferCreateResponse, THIS_COMPONENT_NAME }));
    }
    return merchantTransferCreateResponse;
  }
  

  public MerchantTransferResponse updateStatus(SessionContext sessionContext, MerchantTransferUpdateRequestDTO merchantTransferUpdateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into update Status method of  Transfer service  Input: TransferUpdateRequestDTO: %s in class '%s'", new Object[] { merchantTransferUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransfer.updateStatus", new Object[] { sessionContext, merchantTransferUpdateRequestDTO });
    
    super.canonicalizeInput(merchantTransferUpdateRequestDTO);
    Interaction.begin(sessionContext);
    MerchantTransferResponse merchantTransferResponse = new MerchantTransferResponse();
    merchantTransferResponse.setStatus(fetchStatus());
    TransactionStatus transactionStatus = fetchTransactionStatus();
    try
    {
      merchantTransferUpdateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), merchantTransferUpdateRequestDTO
        .getPaymentId(), "Merchant Transfer Payment", true, null);
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain merchantTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain();
      PaymentKey key = new PaymentKey();
      key.setId(merchantTransferUpdateRequestDTO.getPaymentId());
      key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain.class.getName()));
      merchantTransferDomain = merchantTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new UpdateStatusPaymentSystemConstraint(merchantTransferDomain.getStatus(), merchantTransferDomain.getDebitAccountId());
      
      constraint.isSatisfiedBy();
      if ((tokenGenerationDetails != null) && (tokenGenerationDetails.isTokenAllowed()))
      {
        merchantTransferResponse.setTokenAvailable(tokenGenerationDetails.isTokenAllowed());
        merchantTransferDomain.setStatus(PaymentStatusType.PENDINGVERIFICATION);
        merchantTransferDomain.setTokenId(tokenGenerationDetails.getUid());
      }
      else
      {
        merchantTransferResponse.setTokenAvailable(false);
        merchantTransferDomain.setStatus(PaymentStatusType.VERIFIED);
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generateMerchantPaymentInitiationAlert(merchantTransferDomain);
      }
      merchantTransferDomain.update(merchantTransferDomain);
      if (!tokenGenerationDetails.isTokenAllowed())
      {
        merchantTransferDomain = merchantTransferDomain.process(merchantTransferDomain);
        merchantTransferResponse.setExternalReferenceId(merchantTransferDomain.getTransactionReference()
          .getExternalReferenceId());
      }
      merchantTransferResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(merchantTransferResponse);
      super.checkResponsePolicy(sessionContext, merchantTransferResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);      
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating merchant transfer payment", new Object[] {MerchantTransferService.class
      
        .getName() }), e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from upadte for merchantTransferUpdateRequestDTO '%s'", new Object[] { merchantTransferUpdateRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting updateStatus of merchant Transfer, SessionContext: %s, merchantTransferResponse: %s in class '%s' ", new Object[] { sessionContext, merchantTransferResponse, THIS_COMPONENT_NAME }));
    }
    return merchantTransferResponse;
  }
  
  public MerchantTransferListResponse list(SessionContext sessionContext, MerchantTransferListRequestDTO merchantTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER.formatMessage("Entered into list of Merchant Transfer service: MerchantTransferListRequestDTO: %s in class '%s'", new Object[] { merchantTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MerchantTransfer.list", new Object[] { sessionContext, merchantTransferListRequestDTO });
    
    super.canonicalizeInput(merchantTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    MerchantTransferListResponse merchantTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain merchantTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain> merchantTransferListDomain = new ArrayList();
    
    try
    {
      merchantTransferListDomain = merchantTransferDomain.list(sessionContext.getTransactingPartyCode(), merchantTransferListRequestDTO
        .getFromDate(), merchantTransferListRequestDTO.getToDate(), merchantTransferListRequestDTO
        .getStatus());
      merchantTransferListResponse = new MerchantTransferListResponse();
      merchantTransferListResponse.setTransfers(new MerchantTransferAssembler().fromMerchantDomainObjectList(merchantTransferListDomain));
      merchantTransferListResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(merchantTransferListResponse);
      super.checkResponsePolicy(sessionContext, merchantTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing merchant transfer payments", new Object[] {MerchantTransferService.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from list for MerchantTransferListRequestDTO '%s'", new Object[] { merchantTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting list of Merchant Transfer, SessionContext: %s, MerchantTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return merchantTransferListResponse;
  }
  
  public MerchantTransferListResponse lastPaymentList(SessionContext sessionContext, MerchantTransferListRequestDTO merchantTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into lastPaymentList of Merchant Transfer service: MerchantTransferListRequestDTO: %s in class '%s'", new Object[] { merchantTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MerchantTransfer.lastPaymentList", new Object[] { sessionContext, merchantTransferListRequestDTO });
    
    super.canonicalizeInput(merchantTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    MerchantTransferListResponse merchantTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain merchantTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain> merchantTransferListDomain = new ArrayList();
    
    try
    {
      merchantTransferListDomain = merchantTransferDomain.lastPaymentList(merchantTransferListRequestDTO
        .getFromDate());
      merchantTransferListResponse = new MerchantTransferListResponse();
      merchantTransferListResponse.setTransfers(new MerchantTransferAssembler().fromMerchantDomainObjectList(merchantTransferListDomain));
      merchantTransferListResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(merchantTransferListResponse);
      super.checkResponsePolicy(sessionContext, merchantTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while listing merchant transfer lastPaymentList", new Object[] {MerchantTransferService.class
        
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from lastPaymentList for MerchantTransferListRequestDTO '%s'", new Object[] { merchantTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting lastPaymentList of Merchant Transfer, SessionContext: %s, MerchantTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return merchantTransferListResponse;
  }
}