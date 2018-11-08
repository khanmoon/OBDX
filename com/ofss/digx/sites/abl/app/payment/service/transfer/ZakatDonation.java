package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.app.AbstractApplication;
import com.ofss.digx.app.Interaction;
import com.ofss.digx.app.account.dto.AccountDetailsRequestDTO;
import com.ofss.digx.app.account.dto.dda.DemandDepositAccountResponseDTO;
import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.BusinessPolicyFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.app.adapter.core.ICoreServiceAdapter;
import com.ofss.digx.app.commonservice.core.dto.branchdates.BranchDatesDefinitionDTO;
import com.ofss.digx.app.config.dto.workingwindow.WorkingWindowCheckResponse;
import com.ofss.digx.app.dda.adapter.IDemandDepositAccountAdapter;
import com.ofss.digx.app.dda.dto.account.DemandDepositAccountDTO;
import com.ofss.digx.app.exception.RunTimeException;
import com.ofss.digx.app.ext.ServiceExtensionFactory;
import com.ofss.digx.app.messages.Message;
import com.ofss.digx.app.messages.Status;
import com.ofss.digx.app.payment.assembler.service.PaymentKeyAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.ZakatDonationAssembler;
import com.ofss.digx.app.payment.dto.PaymentValueDateDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDeleteRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationUpdateRequestDTO;
import com.ofss.digx.app.payment.service.core.PaymentAlertHelper;
import com.ofss.digx.app.payment.service.core.PaymentTokenGenerationHelper;
import com.ofss.digx.app.payment.service.transfer.InternalTransfer;
//import com.ofss.digx.sites.abl.app.payment.service.transfer.ext.IZakatDonationExtExecutor;
import com.ofss.digx.common.annotations.Role;
import com.ofss.digx.common.generator.ISystemReferenceValueGenerator;
import com.ofss.digx.common.generator.SystemReferenceValueGeneratorFactory;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.sites.abl.domain.payment.entity.policy.ZakatDonationBusinessPolicyData;
import com.ofss.digx.domain.payment.entity.policy.WorkingWindowCheckBusinessPolicyData;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy.DeleteZakatDonationBusinessPolicyData;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy.ListZakatDonationBusinessPolicyData;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy.ReadZakatDonationBusinessPolicyData;
import com.ofss.digx.enumeration.MessageType;
import com.ofss.digx.enumeration.ModuleType;
import com.ofss.digx.enumeration.config.WorkingWindowProcessingType;
import com.ofss.digx.enumeration.config.WorkingWindowStatus;
import com.ofss.digx.enumeration.payment.PaymentModeType;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.framework.determinant.DeterminantResolver;
import com.ofss.digx.framework.security.authentication.entity.TokenGenerationDetails;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.framework.domain.constraint.SystemConstraint;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicy;
import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicyFactory;
import com.ofss.fc.infra.error.ErrorManager;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

public class ZakatDonation
  extends AbstractApplication
  implements IZakatDonation
{
  private static final String THIS_COMPONENT_NAME = ZakatDonation.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = this.FORMATTER.getLogger(THIS_COMPONENT_NAME);
  //private IZakatDonationExtExecutor extensionExecutor = null;
  
  public ZakatDonation()
  {
   // this.extensionExecutor = ((IZakatDonationExtExecutor)ServiceExtensionFactory.getServiceExtensionExecutor(THIS_COMPONENT_NAME));
  }
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER, com.ofss.digx.enumeration.RoleType.CHECKER, com.ofss.digx.enumeration.RoleType.VIEWER})
  public ZakatDonationReadResponse read(SessionContext sessionContext, ZakatDonationReadRequestDTO ZakatDonationReadRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Entered into read method of Self Transfer service  Input: ZakatDonationReadRequestDTO: %s in class '%s'", new Object[] { ZakatDonationReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation.read", new Object[] { sessionContext, ZakatDonationReadRequestDTO });
    
    super.canonicalizeInput(ZakatDonationReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    ZakatDonationReadResponse ZakatDonationReadResponse = new ZakatDonationReadResponse();
    ZakatDonationDTO ZakatDonationDTO = new ZakatDonationDTO();
    //AbstractBusinessPolicyFactory businessPolicyFactory = null;
    //AbstractBusinessPolicy abstractBusinessPolicy = null;
    //ReadZakatDonationBusinessPolicyData businessPolicyData = new ReadZakatDonationBusinessPolicyData();
    try
    {
      ZakatDonationReadRequestDTO.validate(sessionContext);
      //this.extensionExecutor.preRead(sessionContext, ZakatDonationReadRequestDTO);
      //businessPolicyData.setZakatDonationReadRequestDTO(ZakatDonationReadRequestDTO);
      
     // BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
      
      //businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("PAYMENTS_POLICY_FACTORY");
      //abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("READ_SELF_TRANSFER_BUSINESS_POLICY", businessPolicyData);
      
      //abstractBusinessPolicy.validate();
      PaymentKey key = new PaymentKey();
      key.setId(ZakatDonationReadRequestDTO.getPaymentId());
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
      ZakatDonationDomain = ZakatDonationDomain.read(key);
      
      //ReadZakatDonationSystemConstraint constraint = new ReadZakatDonationSystemConstraint(ZakatDonationDomain.getDebitAccountId());
      //constraint.isSatisfiedBy();
      ZakatDonationDTO = new ZakatDonationAssembler().fromSelfDomainObjectRead(ZakatDonationDomain);
      ZakatDonationReadResponse.setPaymentId(ZakatDonationDomain.getKey().getId());
      ZakatDonationReadResponse.setTransferDetails(ZakatDonationDTO);
      ZakatDonationReadResponse.setStatus(buildStatus(status));
      ZakatDonationReadResponse.setPaymentType(PaymentType.SELFFT);
      //this.extensionExecutor.postRead(sessionContext, ZakatDonationReadRequestDTO, ZakatDonationReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("Exception encountered while invoking the service %s while reading transaction details of Self transfer", new Object[] {ZakatDonation.class
      
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("RuntimeException from read for ZakatDonationReadRequestDTO '%s'", new Object[] { ZakatDonationReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationReadResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationReadResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting read of ZakatDonation, SessionContext: %s, ZakatDonationReadResponse: %s in class '%s' ", new Object[] { sessionContext, ZakatDonationReadResponse, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationReadResponse;
  }
  
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public ZakatDonationReadResponse listCompanyDetails(SessionContext sessionContext, ZakatDonationReadRequestDTO ZakatDonationReadRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Entered into read method of Self Transfer service  Input: ZakatDonationReadRequestDTO: %s in class '%s'", new Object[] { ZakatDonationReadRequestDTO, THIS_COMPONENT_NAME }));
    }
    
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation.read", new Object[] { sessionContext, ZakatDonationReadRequestDTO });
    
    super.canonicalizeInput(ZakatDonationReadRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    ZakatDonationReadResponse ZakatDonationReadResponse = new ZakatDonationReadResponse();
    ZakatDonationDTO ZakatDonationDTO = new ZakatDonationDTO();
    //AbstractBusinessPolicyFactory businessPolicyFactory = null;
    //AbstractBusinessPolicy abstractBusinessPolicy = null;
    //ReadZakatDonationBusinessPolicyData businessPolicyData = new ReadZakatDonationBusinessPolicyData();
    try
    {
      ZakatDonationReadRequestDTO.validate(sessionContext);
      //this.extensionExecutor.preRead(sessionContext, ZakatDonationReadRequestDTO);
      //businessPolicyData.setZakatDonationReadRequestDTO(ZakatDonationReadRequestDTO);
      
     // BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
      
      //businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("PAYMENTS_POLICY_FACTORY");
      //abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("READ_SELF_TRANSFER_BUSINESS_POLICY", businessPolicyData);
      
      //abstractBusinessPolicy.validate();
      //PaymentKey key = new PaymentKey();
      //key.setId(ZakatDonationReadRequestDTO.getPaymentId());
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
      ZakatDonationDomain = ZakatDonationDomain.listCompanyDetails();
      
      //ReadZakatDonationSystemConstraint constraint = new ReadZakatDonationSystemConstraint(ZakatDonationDomain.getDebitAccountId());
      //constraint.isSatisfiedBy();
      ZakatDonationDTO = new ZakatDonationAssembler().fromSelfDomainObjectCompanyList(ZakatDonationDomain);
      //ZakatDonationReadResponse.setPaymentId(ZakatDonationDomain.getKey().getId());
      ZakatDonationReadResponse.setTransferDetails(ZakatDonationDTO);
      ZakatDonationReadResponse.setStatus(buildStatus(status));
      ZakatDonationReadResponse.setPaymentType(PaymentType.SELFFT);
      //this.extensionExecutor.postRead(sessionContext, ZakatDonationReadRequestDTO, ZakatDonationReadResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("Exception encountered while invoking the service %s while reading transaction details of Self transfer", new Object[] {ZakatDonation.class
      
        .getName() }), e1);
      fillTransactionStatus(status, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(status, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("RuntimeException from read for ZakatDonationReadRequestDTO '%s'", new Object[] { ZakatDonationReadRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationReadResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationReadResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting read of ZakatDonation, SessionContext: %s, ZakatDonationReadResponse: %s in class '%s' ", new Object[] { sessionContext, ZakatDonationReadResponse, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationReadResponse;
  }
  
  
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public ZakatDonationCreateResponse create(SessionContext sessionContext, ZakatDonationCreateRequestDTO ZakatDonationCreateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Entered into create method of Self Transfer service  Input: ZakatDonationReadRequestDTO: %s in class '%s'", new Object[] { ZakatDonationCreateRequestDTO, THIS_COMPONENT_NAME }));
    }
    
    //Disabled Business oilcy
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.create", new Object[] { sessionContext, ZakatDonationCreateRequestDTO });
    
    super.canonicalizeInput(ZakatDonationCreateRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus status = fetchTransactionStatus();
    ZakatDonationCreateResponse ZakatDonationCreateResponse = new ZakatDonationCreateResponse();
    TransactionReference transactionReference = new TransactionReference();

    try
    {

      ZakatDonationCreateRequestDTO.validate(sessionContext);
      

     // PaymentValueDateDTO paymentValueDateDTO = new PaymentValueDateDTO();
      
      ZakatDonationCreateRequestDTO.getTransferDetails().setValueDate(new Date());
 
      ZakatDonationDTO ZakatDonationDTO = new ZakatDonationDTO();
      ZakatDonationDTO = ZakatDonationCreateRequestDTO.getTransferDetails();
      ZakatDonationDTO.setPartyId(new Party(sessionContext.getTransactingPartyCode()));
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new ZakatDonationAssembler().toSelfDomainObjectCreate(ZakatDonationDTO);
      PaymentKey key = new PaymentKeyAssembler().generatePaymentKey();
      ZakatDonationDomain.setKey(key);
      
      ISystemReferenceValueGenerator sysRefNumGenerator = SystemReferenceValueGeneratorFactory.getInstance().getGenerator();
      String sysRefNum = sysRefNumGenerator.generateRandomValue(ModuleType.PAYMENTS.getValue());
      transactionReference.setSystemReferenceId(sysRefNum);
      transactionReference.setUserReferenceNo(ZakatDonationDTO.getUserReferenceNo());
      ZakatDonationDomain.setTransactionReference(transactionReference);
      ZakatDonationDomain.setStatus(PaymentStatusType.INITIATED);
      
      ZakatDonationDomain.create(ZakatDonationDomain);
      
      
      ZakatDonationCreateResponse.setPaymentId(ZakatDonationDomain.getKey().getId());
      ZakatDonationCreateResponse.setStatus(buildStatus(status));

      //setWarning(paymentValueDateDTO, ZakatDonationCreateResponse.getStatus());
      //this.extensionExecutor.postCreate(sessionContext, ZakatDonationCreateRequestDTO, ZakatDonationCreateResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      fillTransactionStatus(status, e);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("Fatal Exception from create for Self Transfer Create Request '%s'", new Object[] { ZakatDonationCreateRequestDTO }), e);
    }
    catch (RunTimeException rte)
    {
      fillTransactionStatus(status, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("RunTimeException from create for Self Transfer Create Request '%s'", new Object[] { ZakatDonationCreateRequestDTO }), rte);
    }
    catch (Exception e)
    {
      fillTransactionStatus(status, e);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("Exception encountered while generating the systemReferenceId in create of service  %s", new Object[] {ZakatDonation.class
        
        .getName() }), e);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationCreateResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationCreateResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting create of Self Transfer Service, SessionContext: %s, ZakatDonationCreateResponse: %s ", new Object[] { sessionContext, ZakatDonationCreateResponse, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationCreateResponse;
  }
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER, com.ofss.digx.enumeration.RoleType.CHECKER, com.ofss.digx.enumeration.RoleType.VIEWER})
  public ZakatDonationResponse updateStatus(SessionContext sessionContext, ZakatDonationUpdateRequestDTO ZakatDonationUpdateRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Entered into update method of Self Transfer service  Input: ZakatDonationUpdateRequestDTO: %s in class '%s'", new Object[] { ZakatDonationUpdateRequestDTO, THIS_COMPONENT_NAME }));
    }
    //super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.updateStatus", new Object[] { sessionContext, ZakatDonationUpdateRequestDTO });
    
    super.canonicalizeInput(ZakatDonationUpdateRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    ZakatDonationResponse ZakatDonationResponse = new ZakatDonationResponse();
    ZakatDonationResponse.setStatus(fetchStatus());
    try
    {
      ZakatDonationUpdateRequestDTO.validate(sessionContext);

      
      PaymentTokenGenerationHelper tokenGenerationHelper = PaymentTokenGenerationHelper.getInstance();
      TokenGenerationDetails tokenGenerationDetails = tokenGenerationHelper.tokenGenerationHelper(THIS_COMPONENT_NAME, sessionContext
        .getTransactingPartyCode(), ZakatDonationUpdateRequestDTO
        .getPaymentId(), "Zakat Donation Transfer Payment", true, null);
      
      
      
      com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
      PaymentKey key = new PaymentKey();
      key.setId(ZakatDonationUpdateRequestDTO.getPaymentId());
      key.setDeterminantValue(DeterminantResolver.getInstance().fetchDeterminantValue(com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation.class.getName()));
      ZakatDonationDomain = ZakatDonationDomain.read(key);
      ZakatDonationDomain.setValueDate(new Date());
      
      //ZakatDonationDomain.setStatus(PaymentStatusType.VERIFIED);
     
      if ((tokenGenerationDetails != null) && (tokenGenerationDetails.isTokenAllowed()))
      {
    	  ZakatDonationResponse.setTokenAvailable(tokenGenerationDetails.isTokenAllowed());
    	  ZakatDonationDomain.setStatus(PaymentStatusType.PENDINGVERIFICATION);
    	  ZakatDonationDomain.setTokenId(tokenGenerationDetails.getUid());
      }
      else
      {
    	  ZakatDonationResponse.setTokenAvailable(false);
    	  ZakatDonationDomain.setStatus(PaymentStatusType.VERIFIED);
//        PaymentAlertHelper alertHelper = PaymentAlertHelper.getInstance();
//        alertHelper.generateCardlessWithdrawalPaymentInitiationAlert(cardlessWithdrawalDomain);
      }
      ZakatDonationDomain.update(ZakatDonationDomain);
     
      
//      if (!tokenGenerationDetails.isTokenAllowed())
//      {
//    	  ZakatDonationDomain = ZakatDonationDomain.process(ZakatDonationDomain);
//    	  ZakatDonationDomain.setExternalReferenceId(cardlessWithdrawalDomain.getTransactionReference().getExternalReferenceId());
//      }
      
      ZakatDonationResponse.setStatus(buildStatus(transactionStatus));
      
      super.encodeOutput(ZakatDonationDomain);
  
      
      //ZakatDonationDomain.update(ZakatDonationDomain);
      //ZakatDonationDomain = ZakatDonationDomain.process(ZakatDonationDomain);
     //ZakatDonationResponse.setExternalReferenceId(ZakatDonationDomain.getTransactionReference().getExternalReferenceId());
  
    
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("Exception encountered while invoking the service %s while updating self transfer payment", new Object[] {InternalTransfer.class
        
        .getName() }), e1);
      
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("RuntimeException from upadte for ZakatDonationUpdateRequestDTO '%s'", new Object[] { ZakatDonationUpdateRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting updateStatus of Self Transfer, SessionContext: %s, ZakatDonationResponse: %s in class '%s' ", new Object[] { sessionContext, ZakatDonationResponse, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationResponse;
  }
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public TransactionStatus delete(SessionContext sessionContext, ZakatDonationDeleteRequestDTO ZakatDonationDeleteRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Entered into delete method of Self Transfer service  Input: ZakatDonationDeleteRequestDTO: %s in class '%s'", new Object[] { ZakatDonationDeleteRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.delete", new Object[] { sessionContext, ZakatDonationDeleteRequestDTO });
    
    super.canonicalizeInput(ZakatDonationDeleteRequestDTO);
    Interaction.begin(sessionContext);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    try
    {
      ZakatDonationDeleteRequestDTO.validate(sessionContext);
      //this.extensionExecutor.preDelete(sessionContext, ZakatDonationDeleteRequestDTO);
      DeleteZakatDonationBusinessPolicyData businessPolicyData = new DeleteZakatDonationBusinessPolicyData();
      businessPolicyData.setZakatDonationDeleteRequestDTO(ZakatDonationDeleteRequestDTO);
      
      AbstractBusinessPolicyFactory businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("PAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("DELETE_SELF_TRANSFER_BUSINESS_POLICY", businessPolicyData);
      
      abstractBusinessPolicy.validate();
      PaymentKey key = new PaymentKey();
      key.setId(ZakatDonationDeleteRequestDTO.getPaymentId());
      ZakatDonationDomain.delete(key);
      //this.extensionExecutor.postDelete(sessionContext, ZakatDonationDeleteRequestDTO, transactionStatus);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("Exception encountered while invoking the delete service %s while deleting self transfer details from local database.", new Object[] {ZakatDonation.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("RuntimeException from delete for ZakatDonationDeleteRequestDTO '%s'", new Object[] { ZakatDonationDeleteRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(transactionStatus);
    super.checkResponsePolicy(sessionContext, transactionStatus);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER
        .formatMessage("Exiting delete of ZakatDonation, SessionContext: %s, TransactionStatus: %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return transactionStatus;
  }
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public ZakatDonationListResponse list(SessionContext sessionContext, ZakatDonationListRequestDTO ZakatDonationListRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER
        .formatMessage("Entered into list of Self Transfer service: ZakatDonationListRequestDTO: %s in class '%s'", new Object[] { ZakatDonationListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.list", new Object[] { sessionContext, ZakatDonationListRequestDTO });
    
    super.canonicalizeInput(ZakatDonationListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    ZakatDonationListResponse ZakatDonationListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation> ZakatDonationDomainList = new ArrayList();
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    try
    {
      //this.extensionExecutor.preList(sessionContext, ZakatDonationListRequestDTO);
      ListZakatDonationBusinessPolicyData businessPolicyData = new ListZakatDonationBusinessPolicyData();
      businessPolicyData.setZakatDonationListRequestDTO(ZakatDonationListRequestDTO);
      
      AbstractBusinessPolicyFactory businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("PAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("LIST_SELF_TRANSFER_BUSINESS_POLICY", businessPolicyData);
      
      abstractBusinessPolicy.validate();
      ZakatDonationDomainList = ZakatDonationDomain.list(sessionContext.getTransactingPartyCode(), ZakatDonationListRequestDTO
        .getFromDate(), ZakatDonationListRequestDTO.getToDate(), ZakatDonationListRequestDTO
        .getStatus());
      ZakatDonationListResponse = new ZakatDonationListResponse();
      ZakatDonationListResponse
        .setTransfers(new ZakatDonationAssembler().fromSelfDomainObjectList(ZakatDonationDomainList));
      ZakatDonationListResponse.setStatus(buildStatus(transactionStatus));
      //this.extensionExecutor.postList(sessionContext, ZakatDonationListRequestDTO, ZakatDonationListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("Exception encountered while invoking the service %s while listing self transfer payments", new Object[] {ZakatDonation.class
        
        .getName() }), e1);
      
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("RuntimeException from list for ZakatDonationListRequestDTO '%s'", new Object[] { ZakatDonationListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationListResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationListResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting list of Self Transfer, SessionContext: %s, ZakatDonationListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationListResponse;
  }
  
  @Role(roles={com.ofss.digx.enumeration.RoleType.CUSTOMER, com.ofss.digx.enumeration.RoleType.MAKER})
  public ZakatDonationListResponse lastPaymentList(SessionContext sessionContext, ZakatDonationListRequestDTO ZakatDonationListRequestDTO)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER
        .formatMessage("Entered into list of Self Transfer service: ZakatDonationListRequestDTO: %s in class '%s'", new Object[] { ZakatDonationListRequestDTO, THIS_COMPONENT_NAME }));
    }
    super.checkAccessPolicy("com.ofss.digx.sites.abl.app.payment.service.transfer.lastPaymentList", new Object[] { sessionContext, ZakatDonationListRequestDTO });
    
    super.canonicalizeInput(ZakatDonationListRequestDTO);
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    ZakatDonationListResponse ZakatDonationListResponse = null;
    com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation ZakatDonationDomain = new com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation();
    List<com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation> ZakatDonationDomainList = new ArrayList();
    
    BusinessPolicyFactoryConfigurator businessPolicyFactoryConfigurator = BusinessPolicyFactoryConfigurator.getInstance();
    AbstractBusinessPolicy abstractBusinessPolicy = null;
    try
    {
      //this.extensionExecutor.preLastPaymentList(sessionContext, ZakatDonationListRequestDTO);
      ListZakatDonationBusinessPolicyData businessPolicyData = new ListZakatDonationBusinessPolicyData();
      businessPolicyData.setZakatDonationListRequestDTO(ZakatDonationListRequestDTO);
      
      AbstractBusinessPolicyFactory businessPolicyFactory = businessPolicyFactoryConfigurator.getBusinessPolicyFactory("PAYMENTS_POLICY_FACTORY");
      abstractBusinessPolicy = businessPolicyFactory.getBusinesPolicyInstance("LIST_SELF_TRANSFER_BUSINESS_POLICY", businessPolicyData);
      
      abstractBusinessPolicy.validate();
      ZakatDonationDomainList = ZakatDonationDomain.lastPaymentList(ZakatDonationListRequestDTO.getFromDate());
      ZakatDonationListResponse = new ZakatDonationListResponse();
      ZakatDonationListResponse
        .setTransfers(new ZakatDonationAssembler().fromSelfDomainObjectList(ZakatDonationDomainList));
      ZakatDonationListResponse.setStatus(buildStatus(transactionStatus));
      //this.extensionExecutor.postLastPaymentList(sessionContext, ZakatDonationListRequestDTO, ZakatDonationListResponse);
    }
    catch (com.ofss.digx.infra.exceptions.Exception e1)
    {
      this.logger.log(Level.SEVERE, this.FORMATTER.formatMessage("Exception encountered while invoking the service %s while listing lastPaymentList self transfer payments", new Object[] {ZakatDonation.class
      
        .getName() }), e1);
      fillTransactionStatus(transactionStatus, e1);
    }
    catch (RuntimeException rte)
    {
      fillTransactionStatus(transactionStatus, rte);
      this.logger.log(Level.SEVERE, this.FORMATTER
        .formatMessage("RuntimeException from lastPaymentList for ZakatDonationListRequestDTO '%s'", new Object[] { ZakatDonationListRequestDTO }), rte);
    }
    finally
    {
      Interaction.close();
    }
    super.encodeOutput(ZakatDonationListResponse);
    super.checkResponsePolicy(sessionContext, ZakatDonationListResponse);
    if (this.logger.isLoggable(Level.FINE)) {
      this.logger.log(Level.FINE, this.FORMATTER.formatMessage("Exiting lastPaymentList of Self Transfer, SessionContext: %s, ZakatDonationListResponse : %s in class '%s' ", new Object[] { sessionContext, transactionStatus, THIS_COMPONENT_NAME }));
    }
    return ZakatDonationListResponse;
  }
  
  private String fetchAccountBranch(String accountId)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    DemandDepositAccountResponseDTO accountResponseDTO = null;
    
    IAdapterFactory demandDepositAdapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("DEMAND_DEPOSIT_ACCOUNT_ADAPTER_FACTORY");
    
    IDemandDepositAccountAdapter adapter = (IDemandDepositAccountAdapter)demandDepositAdapterFactory.getAdapter("DemandDepositAccountAdapter");
    AccountDetailsRequestDTO accountRequestDTO = new AccountDetailsRequestDTO();
    accountRequestDTO.setAccountId(new Account(accountId));
    accountResponseDTO = adapter.read(accountRequestDTO);
    return accountResponseDTO.getDemandDepositAccountDTO() != null ? accountResponseDTO
      .getDemandDepositAccountDTO().getBranchCode() : null;
  }
  
  private void setWarning(Status status, String warningCode, String... msgParams)
  {
    if ((warningCode != null) && (warningCode.length() > 0))
    {
      status.getMessage().setCode(warningCode);
      status.getMessage().setType(MessageType.INFO);
      status.getMessage().setDetail(ErrorManager.buildErrorMessage(warningCode, msgParams, null));
    }
  }
  
  private void setWarning(PaymentValueDateDTO paymentValueDateDTO, Status status)
  {
    String warningCode = null;
    if (PaymentModeType.IMMEDIATE.equals(paymentValueDateDTO.getPaymentModeType()))
    {
      if (paymentValueDateDTO.getnWCutOffDone()) {
        warningCode = "DIGX_PY_0171";
      }
    }
    else if (paymentValueDateDTO.getnWCutOffDone()) {
      warningCode = "DIGX_PY_0171";
    } else if (!paymentValueDateDTO.getPaymentDate().equals(paymentValueDateDTO.getValueDate())) {
      warningCode = "DIGX_PY_0170";
    }
    if ((warningCode != null) && (warningCode.length() > 0))
    {
      status.getMessage().setCode(warningCode);
      status.getMessage().setType(MessageType.INFO);
      status.getMessage().setDetail(ErrorManager.buildErrorMessage(warningCode, new String[] {paymentValueDateDTO
        .getValueDate().toFormattedString() }, null));
    }
  }

@Override
public Response read(String paramString) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Response create(ZakatDonationDTO paramZakatDonationDTO) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Response updateStatus(String paramString) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Response delete(String paramString) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Response list(Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Response listCompanyDetails()
{
	// TODO Auto-generated method stub
	return null;
}


}
