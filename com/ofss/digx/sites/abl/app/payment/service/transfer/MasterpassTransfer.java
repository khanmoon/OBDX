package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.app.AbstractApplication;
import com.ofss.digx.app.Interaction;
import com.ofss.digx.app.adapter.BusinessPolicyFactoryConfigurator;
import com.ofss.digx.app.exception.RunTimeException;
import com.ofss.digx.app.payment.assembler.service.PaymentKeyAssembler;
import com.ofss.digx.app.payment.dto.PaymentValueDateHelperInputDTO;
import com.ofss.digx.app.payment.service.core.PaymentPartyRecordAccessSystemConstraint;
import com.ofss.digx.app.payment.service.core.PaymentTokenGenerationHelper;
import com.ofss.digx.app.payment.service.core.UpdateStatusPaymentSystemConstraint;
import com.ofss.digx.common.annotations.Role;
import com.ofss.digx.common.generator.ISystemReferenceValueGenerator;
import com.ofss.digx.common.generator.SystemReferenceValueGeneratorFactory;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.ModuleType;
import com.ofss.digx.enumeration.payment.PaymentModeType;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.security.authentication.entity.TokenGenerationDetails;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.MasterpassTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MasterpassPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.IMasterpassTransferExtExecutor;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MasterpassPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.MasterpassTransferBusinessPolicyData;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicy;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicyFactory;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;
import com.ofss.fc.service.response.TransactionStatus;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterpassTransfer
  extends AbstractApplication
  implements IMasterpassTransfer
{
  private static final String THIS_COMPONENT_NAME = MasterpassTransfer.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private ITextMask masker = MaskingFactory.getInstance();
  private IMasterpassTransferExtExecutor extensionExecutor = null;
  

  private Object input;
  


  public MasterpassTransfer() {}
  

  public MasterpassTransferReadResponse read(SessionContext sessionContext, MasterpassTransferReadRequestDTO donationTransferReadRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into read method of  Transfer service  Input: TransferReadRequestDTO: %s in class '%s'", new Object[] { donationTransferReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer.read", new Object[] { sessionContext, donationTransferReadRequestDTO });
    
    super.canonicalizeInput(donationTransferReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    MasterpassTransferReadResponse donationTransferReadResponse = new MasterpassTransferReadResponse();
    MasterpassDTO donationTransferDTO = new MasterpassDTO();
    try
    {
      donationTransferReadRequestDTO.validate(sessionContext);
      
      PaymentKey key = new PaymentKey();
      key.setId(donationTransferReadRequestDTO.getPaymentId());
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer();
      donationTransferDomain = donationTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new PaymentPartyRecordAccessSystemConstraint(sessionContext.getTransactingPartyCode(), donationTransferDomain.getPartyId());
      constraint.isSatisfiedBy();
      donationTransferDTO = new MasterpassTransferAssembler().fromMasterpassDomainObjectRead(donationTransferDomain);
      MasterpassPayeeDTO donationPayeeDTO = new MasterpassPayeeDTO();
      
//      if ((donationTransferDomain != null) && (donationTransferDomain.getPayee() != null)) {
//        donationPayeeDTO.setbillerId(masker.mask(donationTransferDomain.getPayee().getbillerId(), "ExternalAccountNumberMasking", "external_account_id"));
//      }
      donationTransferReadResponse.setPaymentId(donationTransferDomain.getKey().getId());
      donationTransferReadResponse.setPayeeDetails(donationPayeeDTO);
      donationTransferReadResponse.setTransferDetails(donationTransferDTO);
      donationTransferReadResponse.setStatus(buildStatus(status));
      donationTransferReadResponse.setPaymentType("DONATION");
      
      super.encodeOutput(donationTransferReadResponse);
      super.indirectResponse(donationTransferReadResponse);
      super.checkResponsePolicy(sessionContext, donationTransferReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while reading transaction details of  transfer", new Object[] {MasterpassTransfer.class
        
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from read for TransferReadRequestDTO '%s'", new Object[] { donationTransferReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting read of Transfer, SessionContext: %s, TransferReadResponse: %s in class '%s' ", new Object[] { sessionContext, donationTransferReadResponse, THIS_COMPONENT_NAME }));
    }
    return donationTransferReadResponse;
  }

  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public MasterpassTransferCreateResponse create(SessionContext sessionContext, MasterpassTransferCreateRequestDTO masterpassTransferCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    System.out.println("**In create of masterpassTransfer at payment.service.transfer ");
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into create method of Masterpass Transfer service  Input: MasterpassTransferReadRequestDTO: %s in class '%s'", new Object[] { masterpassTransferCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer.create", new Object[] { sessionContext, masterpassTransferCreateRequestDTO });
    
    super.canonicalizeInput(masterpassTransferCreateRequestDTO);
    super.indirectRequest(masterpassTransferCreateRequestDTO);
    Interaction.begin(sessionContext);
    
    MasterpassTransferCreateResponse masterpassTransferCreateResponse = new MasterpassTransferCreateResponse();
    TransactionStatus status = fetchTransactionStatus();
    TransactionReference transactionReference = new TransactionReference();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    
    MasterpassTransferBusinessPolicyData masterpassTransferPaymentBusinessPolicyData = null;
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicyFactory businessPolicyFactory = null;
    
    try
    {
      masterpassTransferCreateRequestDTO.validate(sessionContext);
      
//      PaymentValueDateHelperInputDTO input = new PaymentValueDateHelperInputDTO();
//      input.setAccountId(masterpassTransferCreateRequestDTO.getTransferDetails().getDebitAccountId().getValue());
//      input.setValueDate(masterpassTransferCreateRequestDTO.getTransferDetails().getValueDate());
//      input.setPaymentModeType(PaymentModeType.IMMEDIATE);
//      
//
//      masterpassTransferCreateRequestDTO.getTransferDetails().setValueDate(input.getValueDate());
      
      MasterpassDTO masterpassDTO = new MasterpassDTO();
      masterpassDTO = masterpassTransferCreateRequestDTO.getTransferDetails();
      masterpassDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));
      //masterpassDTO.setbillerId(masterpassTransferCreateRequestDTO.getTransferDetails().getbillerId());
      
//      masterpassTransferPaymentBusinessPolicyData = new MasterpassTransferBusinessPolicyData();
//      masterpassTransferPaymentBusinessPolicyData.setRemarks(masterpassTransferCreateRequestDTO.getTransferDetails()
//        .getRemarks());
//      masterpassTransferPaymentBusinessPolicyData.setDebitAccount(masterpassTransferCreateRequestDTO
//        .getTransferDetails().getDebitAccountId());
//      masterpassTransferPaymentBusinessPolicyData.setCreditAmount(masterpassTransferCreateRequestDTO
//        .getTransferDetails().getAmount());
//      masterpassTransferPaymentBusinessPolicyData.setPartyId(sessionContext.getTransactingPartyCode());
//      masterpassTransferPaymentBusinessPolicyData.setUserRefNo(masterpassTransferCreateRequestDTO.getTransferDetails().getbillerId());
//      
//
//      System.out.println("** Going to fetch businessPolicyFactoryConfigurator from DB");
//      businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("CUSTOMPAYMENTS_POLICY_FACTORY");
//      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("PAYMENT_MASTERPASS_BUSINESS_POLICY", masterpassTransferPaymentBusinessPolicyData);
      
      //abstractBusinessPolicy.validate("DIGX_PY_0131");
      


      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer masterpassTransferDomain = null;
      masterpassTransferDomain = new MasterpassTransferAssembler().toMasterpassDomainObjectCreate(masterpassDTO);
      
      PaymentKey paymentKey = new PaymentKeyAssembler().generatePaymentKey();
      masterpassTransferDomain.setKey(paymentKey);
      

      System.out.println("Khalid : " + masterpassTransferDomain.getDebitAccountId());
      
      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(masterpassDTO.getUserReferenceNo());
      masterpassTransferDomain.setTransactionReference(transactionReference);
      
      masterpassTransferDomain.setStatus(PaymentStatusType.INITIATED);
      System.out.println("**about to call create at payment.service.transfer ");
      masterpassTransferDomain.create(masterpassTransferDomain);
      System.out.println("** create called at payment.service.transfer ");
      
      masterpassTransferCreateResponse.setPaymentId(masterpassTransferDomain.getKey().getId());
      masterpassTransferCreateResponse.setStatus(buildStatus(status));
      


      super.encodeOutput(masterpassTransferCreateResponse);
      super.checkResponsePolicy(sessionContext, masterpassTransferCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Fatal Exception from create for Masterpass Transfer Create Request '%s'", new Object[] { masterpassTransferCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RunTimeException from create for Masterpass Transfer Create Request '%s'", new Object[] { masterpassTransferCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {MasterpassTransfer.class
      
        .getName() }), e);
      fillTransactionStatus(status, e);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting create of MasterpassTransfer Service, SessionContext: %s, MasterpassTransferCreateResponse: %s ", new Object[] { sessionContext, masterpassTransferCreateResponse, THIS_COMPONENT_NAME }));
    }
    return masterpassTransferCreateResponse;
  }
  

  public MasterpassTransferResponse updateStatus(SessionContext sessionContext, MasterpassTransferUpdateRequestDTO donationTransferUpdateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into update Status method of  Transfer service  Input: TransferUpdateRequestDTO: %s in class '%s'", new Object[] { donationTransferUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer.updateStatus", new Object[] { sessionContext, donationTransferUpdateRequestDTO });
    
    super.canonicalizeInput(donationTransferUpdateRequestDTO);
    Interaction.begin(sessionContext);
    MasterpassTransferResponse donationTransferResponse = new MasterpassTransferResponse();
    donationTransferResponse.setStatus(fetchStatus());
    TransactionStatus transactionStatus = fetchTransactionStatus();
    try
    {
      donationTransferUpdateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), donationTransferUpdateRequestDTO
        .getPaymentId(), "Masterpass Transfer Payment", true, null);
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer();
      PaymentKey key = new PaymentKey();
      key.setId(donationTransferUpdateRequestDTO.getPaymentId());
      
      System.out.println("**testing error");
      System.out.println("**Payment Id =" + donationTransferUpdateRequestDTO.getPaymentId());
      System.out.println("**To String  =" + donationTransferUpdateRequestDTO.toString());
      System.out.println("**About to call read");
      donationTransferDomain = donationTransferDomain.read(key);
      System.out.println("**Debit Account Id =" + donationTransferDomain.getDebitAccountId());
      System.out.println("**Status           =" + donationTransferDomain.getStatus());
      

      System.out.println("**About to call system constraint");
      System.out.println("**Debit Account Id =" + donationTransferDomain.getDebitAccountId());
      System.out.println("**Status           =" + donationTransferDomain.getStatus());
      
      SystemConstraint<DataTransferObject> constraint = new UpdateStatusPaymentSystemConstraint(donationTransferDomain.getStatus(), donationTransferDomain.getDebitAccountId());
      

      if ((tokenGenerationDetails != null) && (tokenGenerationDetails.isTokenAllowed()))
      {
        donationTransferResponse.setTokenAvailable(tokenGenerationDetails.isTokenAllowed());
        donationTransferDomain.setStatus(PaymentStatusType.PENDINGVERIFICATION);
        donationTransferDomain.setTokenId(tokenGenerationDetails.getUid());
      }
      else
      {
        donationTransferResponse.setTokenAvailable(false);
        donationTransferDomain.setStatus(PaymentStatusType.VERIFIED);
        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
        alertHelper.generateMasterpassPaymentInitiationAlert(donationTransferDomain);
      }
      try {
        donationTransferDomain.update(donationTransferDomain);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
      if (!tokenGenerationDetails.isTokenAllowed())
      {
        try {
          donationTransferDomain = donationTransferDomain.process(donationTransferDomain);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
        donationTransferResponse.setExternalReferenceId(donationTransferDomain.getTransactionReference()
          .getExternalReferenceId());
      }
      donationTransferResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(donationTransferResponse);
      super.checkResponsePolicy(sessionContext, donationTransferResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      fillTransactionStatus(transactionStatus, e1);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating donation transfer payment", new Object[] {MasterpassTransfer.class
      
        .getName() }), e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from upadte for donationTransferUpdateRequestDTO '%s'", new Object[] { donationTransferUpdateRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting updateStatus of donation Transfer, SessionContext: %s, donationTransferResponse: %s in class '%s' ", new Object[] { sessionContext, donationTransferResponse, THIS_COMPONENT_NAME }));
    }
    return donationTransferResponse;
  }
  

  public MasterpassTransferListResponse list(SessionContext sessionContext, MasterpassTransferListRequestDTO donationTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER.formatMessage("Entered into list of Masterpass Transfer service: MasterpassTransferListRequestDTO: %s in class '%s'", new Object[] { donationTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MasterpassTransfer.list", new Object[] { sessionContext, donationTransferListRequestDTO });
    
    super.canonicalizeInput(donationTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    MasterpassTransferListResponse donationTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer> donationTransferListDomain = new ArrayList();
    
    try
    {
      donationTransferListDomain = donationTransferDomain.list(sessionContext.getTransactingPartyCode(), donationTransferListRequestDTO
        .getFromDate(), donationTransferListRequestDTO.getToDate(), donationTransferListRequestDTO
        .getStatus());
      donationTransferListResponse = new MasterpassTransferListResponse();
      donationTransferListResponse.setTransfers(new MasterpassTransferAssembler().fromMasterpassDomainObjectList(donationTransferListDomain));
      donationTransferListResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(donationTransferListResponse);
      super.checkResponsePolicy(sessionContext, donationTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing donation transfer payments", new Object[] {MasterpassTransfer.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from list for MasterpassTransferListRequestDTO '%s'", new Object[] { donationTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting list of Masterpass Transfer, SessionContext: %s, MasterpassTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return donationTransferListResponse;
  }
  
  public MasterpassTransferListResponse lastPaymentList(SessionContext sessionContext, MasterpassTransferListRequestDTO donationTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into lastPaymentList of Masterpass Transfer service: MasterpassTransferListRequestDTO: %s in class '%s'", new Object[] { donationTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.MasterpassTransfer.lastPaymentList", new Object[] { sessionContext, donationTransferListRequestDTO });
    
    super.canonicalizeInput(donationTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    MasterpassTransferListResponse donationTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer> donationTransferListDomain = new ArrayList();
    
    try
    {
      donationTransferListDomain = donationTransferDomain.lastPaymentList(donationTransferListRequestDTO
        .getFromDate());
      donationTransferListResponse = new MasterpassTransferListResponse();
      donationTransferListResponse.setTransfers(new MasterpassTransferAssembler().fromMasterpassDomainObjectList(donationTransferListDomain));
      donationTransferListResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(donationTransferListResponse);
      super.checkResponsePolicy(sessionContext, donationTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while listing donation transfer lastPaymentList", new Object[] {MasterpassTransfer.class
        
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from lastPaymentList for MasterpassTransferListRequestDTO '%s'", new Object[] { donationTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting lastPaymentList of Masterpass Transfer, SessionContext: %s, MasterpassTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return donationTransferListResponse;
  }
}