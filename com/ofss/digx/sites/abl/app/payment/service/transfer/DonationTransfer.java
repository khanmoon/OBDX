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
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.DonationTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.payee.DonationPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.IDonationTransferExtExecutor;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.DonationPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.DonationTransferBusinessPolicyData;
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




public class DonationTransfer
  extends AbstractApplication
  implements IDonationTransfer
{
  private static final String THIS_COMPONENT_NAME = DonationTransfer.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private ITextMask masker = MaskingFactory.getInstance();
  private IDonationTransferExtExecutor extensionExecutor = null;
private Object input;
  



  public DonationTransfer() {}
  


  public DonationTransferReadResponse read(SessionContext sessionContext, DonationTransferReadRequestDTO donationTransferReadRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into read method of  Transfer service  Input: TransferReadRequestDTO: %s in class '%s'", new Object[] { donationTransferReadRequestDTO, THIS_COMPONENT_NAME }));
    }
//    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer.read", new Object[] { sessionContext, donationTransferReadRequestDTO });
    
    super.canonicalizeInput(donationTransferReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    DonationTransferReadResponse donationTransferReadResponse = new DonationTransferReadResponse();
    DonationTransferDTO donationTransferDTO = new DonationTransferDTO();
    try
    {
      donationTransferReadRequestDTO.validate(sessionContext);
      
      PaymentKey key = new PaymentKey();
      key.setId(donationTransferReadRequestDTO.getPaymentId());
      //key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(DonationTransfer.class.getName()));
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer();
      donationTransferDomain = donationTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new PaymentPartyRecordAccessSystemConstraint(sessionContext.getTransactingPartyCode(), donationTransferDomain.getPartyId());
      constraint.isSatisfiedBy();
      donationTransferDTO = new DonationTransferAssembler().fromdonationDomainObjectRead(donationTransferDomain);
      DonationPayeeDTO donationPayeeDTO = new DonationPayeeDTO();
      
      if ((donationTransferDomain != null) && (donationTransferDomain.getPayee() != null)) {
        donationPayeeDTO.setbillerId(masker.mask(donationTransferDomain.getPayee().getbillerId(), "ExternalAccountNumberMasking", "external_account_id"));
      }
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
      
        .formatMessage("Exception encountered while invoking the service %s while reading transaction details of  transfer", new Object[] {DonationTransfer.class
        
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
  



  public DonationTransferCreateResponse create(SessionContext sessionContext, DonationTransferCreateRequestDTO donationTransferCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
	System.out.println("**In create of donationTransfer at payment.service.transfer "); 
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into create method of Donation Transfer service  Input: DonationTransferReadRequestDTO: %s in class '%s'", new Object[] { donationTransferCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
   //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer.create", new Object[] { sessionContext, donationTransferCreateRequestDTO });
    
    super.canonicalizeInput(donationTransferCreateRequestDTO);
    super.indirectRequest(donationTransferCreateRequestDTO);
    Interaction.begin(sessionContext);
    
    DonationTransferCreateResponse donationTransferCreateResponse = new DonationTransferCreateResponse();
    TransactionStatus status = fetchTransactionStatus();
    TransactionReference transactionReference = new TransactionReference();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    
    DonationTransferBusinessPolicyData donationTransferPaymentBusinessPolicyData = null;
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicyFactory businessPolicyFactory = null;
    
    try
    {
      donationTransferCreateRequestDTO.validate(sessionContext);
      
      PaymentValueDateHelperInputDTO input = new PaymentValueDateHelperInputDTO();
      input.setAccountId(donationTransferCreateRequestDTO.getTransferDetails().getDebitAccountId().getValue());
      input.setValueDate(donationTransferCreateRequestDTO.getTransferDetails().getValueDate());
      input.setPaymentModeType(PaymentModeType.IMMEDIATE);
      
      //PaymentValueDateDTO paymentValueDateDTO = paymentValueDateHelper.getInternalPaymentValueDate(input, status);
      donationTransferCreateRequestDTO.getTransferDetails().setValueDate(input.getValueDate());
      
      DonationTransferDTO donationTransferDTO = new DonationTransferDTO();
      donationTransferDTO = donationTransferCreateRequestDTO.getTransferDetails();
      donationTransferDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));      
      donationTransferDTO.setbillerId(donationTransferCreateRequestDTO.getTransferDetails().getbillerId());

      donationTransferPaymentBusinessPolicyData = new DonationTransferBusinessPolicyData();
      donationTransferPaymentBusinessPolicyData.setRemarks(donationTransferCreateRequestDTO.getTransferDetails()
        .getRemarks());
      donationTransferPaymentBusinessPolicyData.setDebitAccount(donationTransferCreateRequestDTO
        .getTransferDetails().getDebitAccountId());
      donationTransferPaymentBusinessPolicyData.setCreditAmount(donationTransferCreateRequestDTO
        .getTransferDetails().getAmount());
      donationTransferPaymentBusinessPolicyData.setPartyId(sessionContext.getTransactingPartyCode());
      donationTransferPaymentBusinessPolicyData.setUserRefNo(donationTransferCreateRequestDTO.getTransferDetails().getbillerId());
      

      System.out.println("** Going to fetch businessPolicyFactoryConfigurator from DB");
      businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("CUSTOMPAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("PAYMENT_DONATION_BUSINESS_POLICY", donationTransferPaymentBusinessPolicyData);
      
      abstractBusinessPolicy.validate("DIGX_PY_0131");
      


      com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer donationTransferDomain = null;
      donationTransferDomain = new DonationTransferAssembler().toDonationDomainObjectCreate(donationTransferDTO);

      PaymentKey paymentKey = new PaymentKeyAssembler().generatePaymentKey();
      donationTransferDomain.setKey(paymentKey);

 
      System.out.println("Khalid : " + donationTransferDomain.getDebitAccountId());
      System.out.println("Khalid : " + donationTransferDomain.getPayee().getbillerId());
      
      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(donationTransferDTO.getUserReferenceNo());
      donationTransferDomain.setTransactionReference(transactionReference);
      
      donationTransferDomain.setStatus(PaymentStatusType.INITIATED);
      System.out.println("**about to call create at payment.service.transfer ");
      donationTransferDomain.create(donationTransferDomain);
      System.out.println("** create called at payment.service.transfer ");
      
      donationTransferCreateResponse.setPaymentId(donationTransferDomain.getKey().getId());
      donationTransferCreateResponse.setStatus(buildStatus(status));
     // paymentValueDateHelper.setWarning(paymentValueDateDTO, donationTransferCreateResponse.getStatus());
      

      super.encodeOutput(donationTransferCreateResponse);
      super.checkResponsePolicy(sessionContext, donationTransferCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Fatal Exception from create for Donation Transfer Create Request '%s'", new Object[] { donationTransferCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RunTimeException from create for Donation Transfer Create Request '%s'", new Object[] { donationTransferCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {DonationTransfer.class
      
        .getName() }), e);
      fillTransactionStatus(status, e);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting create of DonationTransfer Service, SessionContext: %s, DonationTransferCreateResponse: %s ", new Object[] { sessionContext, donationTransferCreateResponse, THIS_COMPONENT_NAME }));
    }
    return donationTransferCreateResponse;
  }
  

  public DonationTransferResponse updateStatus(SessionContext sessionContext, DonationTransferUpdateRequestDTO donationTransferUpdateRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into update Status method of  Transfer service  Input: TransferUpdateRequestDTO: %s in class '%s'", new Object[] { donationTransferUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer.updateStatus", new Object[] { sessionContext, donationTransferUpdateRequestDTO });
    
    super.canonicalizeInput(donationTransferUpdateRequestDTO);
    Interaction.begin(sessionContext);
    DonationTransferResponse donationTransferResponse = new DonationTransferResponse();
    donationTransferResponse.setStatus(fetchStatus());
    TransactionStatus transactionStatus = fetchTransactionStatus();
    try
    {
      donationTransferUpdateRequestDTO.validate(sessionContext);
      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), donationTransferUpdateRequestDTO
        .getPaymentId(), "Donation Transfer Payment", true, null);
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer();
      PaymentKey key = new PaymentKey();
      key.setId(donationTransferUpdateRequestDTO.getPaymentId());
     // key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue());
      donationTransferDomain = donationTransferDomain.read(key);
      
      SystemConstraint<DataTransferObject> constraint = new UpdateStatusPaymentSystemConstraint(donationTransferDomain.getStatus(), donationTransferDomain.getDebitAccountId());
      
      constraint.isSatisfiedBy();
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
        alertHelper.generateDonationPaymentInitiationAlert(donationTransferDomain);
      }
      donationTransferDomain.update(donationTransferDomain);
      if (!tokenGenerationDetails.isTokenAllowed())
      {
        donationTransferDomain = donationTransferDomain.process(donationTransferDomain);
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
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while updating donation transfer payment", new Object[] {DonationTransfer.class
      
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
  

  public DonationTransferListResponse list(SessionContext sessionContext, DonationTransferListRequestDTO donationTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER.formatMessage("Entered into list of Donation Transfer service: DonationTransferListRequestDTO: %s in class '%s'", new Object[] { donationTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.DonationTransfer.list", new Object[] { sessionContext, donationTransferListRequestDTO });
    
    super.canonicalizeInput(donationTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    DonationTransferListResponse donationTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer> donationTransferListDomain = new ArrayList();
    
    try
    {
      donationTransferListDomain = donationTransferDomain.list(sessionContext.getTransactingPartyCode(), donationTransferListRequestDTO
        .getFromDate(), donationTransferListRequestDTO.getToDate(), donationTransferListRequestDTO
        .getStatus());
      donationTransferListResponse = new DonationTransferListResponse();
      donationTransferListResponse.setTransfers(new DonationTransferAssembler().fromDonationDomainObjectList(donationTransferListDomain));
      donationTransferListResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(donationTransferListResponse);
      super.checkResponsePolicy(sessionContext, donationTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing donation transfer payments", new Object[] {DonationTransfer.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from list for DonationTransferListRequestDTO '%s'", new Object[] { donationTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting list of Donation Transfer, SessionContext: %s, DonationTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return donationTransferListResponse;
  }
  
  public DonationTransferListResponse lastPaymentList(SessionContext sessionContext, DonationTransferListRequestDTO donationTransferListRequestDTO)
    throws java.lang.Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into lastPaymentList of Donation Transfer service: DonationTransferListRequestDTO: %s in class '%s'", new Object[] { donationTransferListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.app.payment.service.transfer.DonationTransfer.lastPaymentList", new Object[] { sessionContext, donationTransferListRequestDTO });
    
    super.canonicalizeInput(donationTransferListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    DonationTransferListResponse donationTransferListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer donationTransferDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer> donationTransferListDomain = new ArrayList();
    
    try
    {
      donationTransferListDomain = donationTransferDomain.lastPaymentList(donationTransferListRequestDTO
        .getFromDate());
      donationTransferListResponse = new DonationTransferListResponse();
      donationTransferListResponse.setTransfers(new DonationTransferAssembler().fromDonationDomainObjectList(donationTransferListDomain));
      donationTransferListResponse.setStatus(buildStatus(transactionStatus));
      

      super.encodeOutput(donationTransferListResponse);
      super.checkResponsePolicy(sessionContext, donationTransferListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      logger.log(Level.SEVERE, FORMATTER
      
        .formatMessage("Exception encountered while invoking the service %s while listing donation transfer lastPaymentList", new Object[] {DonationTransfer.class
        
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      logger.log(Level.SEVERE, FORMATTER.formatMessage("RuntimeException from lastPaymentList for DonationTransferListRequestDTO '%s'", new Object[] { donationTransferListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, FORMATTER
      
        .formatMessage("Exiting lastPaymentList of Donation Transfer, SessionContext: %s, DonationTransferListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return donationTransferListResponse;
  }
}
