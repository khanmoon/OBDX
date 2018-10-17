package com.ofss.digx.sites.abl.app.payment.service.core;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.app.alerts.adapter.eventgen.IModuleToAlertAdapter;
import com.ofss.digx.app.alerts.dto.eventgen.ActivityLog;
import com.ofss.digx.app.alerts.dto.maintenance.ActivityEventKeyDTO;
import com.ofss.digx.app.enumeration.dto.EnumerationRepresentationResponse;
import com.ofss.digx.app.payment.adapter.party.IPaymentPartyAdapter;
import com.ofss.digx.app.payment.adapter.payee.IPaymentEnumerationAdapter;
import com.ofss.digx.app.payment.dto.PaymentInitiationActivityLog;
import com.ofss.digx.app.payment.dto.payee.PayeeActivityLog;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.domain.payment.entity.payee.Payee;
import com.ofss.digx.enumeration.payment.InstructionFrequency;
import com.ofss.digx.infra.enumeration.representation.EnumerationRepresentation;
import com.ofss.digx.infra.enumeration.representation.ValueRepresentation;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.config.ConfigurationFactory;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.thread.ThreadAttribute;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;




public class PaymentAlertHelper
{
 /*
  * Add Following:
  * import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransfer;
  * */	
  private static final String THIS_COMPONENT_NAME = PaymentAlertHelper.class.getName();
  private MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private transient Logger LOGGER = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private static final String CLAIM_PAYMENT = "CLAIM_PAYMENT";
  private static final String LINK_CONFIG = "DayOneConfig";
  private static Preferences link = null;
  
  public PaymentAlertHelper() {}
  
  public static PaymentAlertHelper getInstance() { link = ConfigurationFactory.getInstance().getConfigurations("DayOneConfig");
    return new PaymentAlertHelper();
  }
  
  private void registerActivityAndGenerateEvent(SessionContext sessionContext, ActivityEventKeyDTO activityEventKeyDTO, Date date, ActivityLog activityLog)
    throws Exception
  {
    IAdapterFactory adapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("MODULE_ALERT_ADAPTER_FACTORY");
    
    IModuleToAlertAdapter adapter = (IModuleToAlertAdapter)adapterFactory.getAdapter("MODULE_ALERT_ADAPTER");
    adapter.registerActivityAndGenerateEvent(sessionContext, activityEventKeyDTO, date, activityLog);
  }
  
  private ActivityEventKeyDTO setActivityEventKeyDTO(String activityId, String eventId)
  {
    ActivityEventKeyDTO activityEventKeyDTO = new ActivityEventKeyDTO();
    activityEventKeyDTO.setActivityId(activityId);
    activityEventKeyDTO.setEventId(eventId);
    return activityEventKeyDTO;
  }
  
  private String getFrequencyName(InstructionFrequency instructionFrequency)
  {
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    IAdapterFactory adapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("PAYMENT_ENUMERATION_ADAPTER_FACTORY");
    
    IPaymentEnumerationAdapter adapter = (IPaymentEnumerationAdapter)adapterFactory.getAdapter("PAYMENT_ENUMERATION_ADAPTER");
    try
    {
      EnumerationRepresentationResponse enumResponse = adapter.getPaymentFrequency(sessionContext);
      EnumerationRepresentation[] enumRepresentations = enumResponse.getEnumRepresentations();
      if (instructionFrequency != null)
      {
        if ((enumRepresentations != null) && (enumRepresentations.length > 0)) {
          for (int i = 0; i < enumRepresentations.length; i++) {
            if ((enumRepresentations[i] != null) && (enumRepresentations[i].getData() != null) && 
              (enumRepresentations[i].getData().length > 0)) {
              for (int j = 0; j < enumRepresentations[i].getData().length; j++) {
                if (enumRepresentations[i].getData()[j] != null)
                {
                  if (instructionFrequency.toString().equals(enumRepresentations[i].getData()[j].getCode()))
                    return enumRepresentations[i].getData()[j].getDescription();
                }
              }
            }
          }
        }
        return instructionFrequency.name();
      }
      return null;
    }
    catch (Exception localException) {}
    return null;
  }
  
  private String getPartyName(String partyId)
  {
    String partyName = "";
    IAdapterFactory paymentPartyAdapterFactory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("PAYMENT_PARTY_ADAPTER_FACTORY");
    
    IPaymentPartyAdapter paymentPartyAdapter = (IPaymentPartyAdapter)paymentPartyAdapterFactory.getAdapter("PAYMENT_PARTY_ADAPTER");
    try
    {
      partyName = paymentPartyAdapter.fetchPartyName(partyId);
    }
    catch (Exception e1)
    {
      LOGGER.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while fetching the party name", new Object[0]), e1);
    }
    return partyName;
  }
  
  private PayeeActivityLog setPayeeAlertDetails(Payee payee)
  {
    PayeeActivityLog alertDetails = new PayeeActivityLog();
    alertDetails.setPayeeNickname(payee.getNickName());
    return alertDetails;
  }
  

  private PaymentInitiationActivityLog setAlertDetailsPayAnyoneTransfer(PayAnyoneTransfer payAnyoneTransfer)
  {
    PaymentInitiationActivityLog alertDetails = new PaymentInitiationActivityLog();
    CurrencyAmount currencyAmount = new CurrencyAmount();
    currencyAmount.setAmount(payAnyoneTransfer.getAmount().getAmount());
    currencyAmount.setCurrency(payAnyoneTransfer.getAmount().getCurrency());
    
    alertDetails.setAccountId(payAnyoneTransfer.getDebitAccountId());
    alertDetails.setSourceAccountNo(payAnyoneTransfer.getDebitAccountId());
    alertDetails.setPayeeNickname(payAnyoneTransfer.getPayeeNickName());
    
    alertDetails.setCurrencyTransferAmount(currencyAmount);
    alertDetails.setValueDate(payAnyoneTransfer.getValueDate());
    return alertDetails;
  }
  
  public void generatePayAnyonePaymentInitiationAlert(PayAnyoneTransfer donationTransferDomain)
  {
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into generatePayAnyonePaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
    ActivityEventKeyDTO activityEventKeyDTO = setActivityEventKeyDTO("com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer.updateStatus", "PC_PAYANYONE_TRANSFER_INITIATION");
    
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    try
    {
      registerActivityAndGenerateEvent(sessionContext, activityEventKeyDTO, new Date(), setAlertDetailsPayAnyoneTransfer(donationTransferDomain));
    }
    catch (Exception e)
    {
      LOGGER.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while communicating the alert", new Object[0]), e);
    }
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Exit from generatePayAnyonePaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
  }
  

  private PaymentInitiationActivityLog setAlertDetailsDonationTransfer(DonationTransfer donationTransfer)
  {
    PaymentInitiationActivityLog alertDetails = new PaymentInitiationActivityLog();
    CurrencyAmount currencyAmount = new CurrencyAmount();
    currencyAmount.setAmount(donationTransfer.getAmount().getAmount());
    currencyAmount.setCurrency(donationTransfer.getAmount().getCurrency());
    
    alertDetails.setAccountId(donationTransfer.getDebitAccountId());
    alertDetails.setSourceAccountNo(donationTransfer.getDebitAccountId());
    alertDetails.setPayeeNickname(donationTransfer.getPayeeNickName());
    
    alertDetails.setCurrencyTransferAmount(currencyAmount);
    alertDetails.setValueDate(donationTransfer.getValueDate());
    return alertDetails;
  }
  
  private PaymentInitiationActivityLog setAlertDetailsMasterpassTransfer(MasterpassTransfer donationTransfer)
  {
    PaymentInitiationActivityLog alertDetails = new PaymentInitiationActivityLog();
    CurrencyAmount currencyAmount = new CurrencyAmount();
    currencyAmount.setAmount(donationTransfer.getAmount().getAmount());
    currencyAmount.setCurrency(donationTransfer.getAmount().getCurrency());
    
    alertDetails.setAccountId(donationTransfer.getDebitAccountId());
    alertDetails.setSourceAccountNo(donationTransfer.getDebitAccountId());
    alertDetails.setPayeeNickname(donationTransfer.getPayeeNickName());
    
    alertDetails.setCurrencyTransferAmount(currencyAmount);
    alertDetails.setValueDate(donationTransfer.getValueDate());
    return alertDetails;
  }
  
  public void generateDonationPaymentInitiationAlert(DonationTransfer donationTransferDomain)
  {
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into generateDonationPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
    ActivityEventKeyDTO activityEventKeyDTO = setActivityEventKeyDTO("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer.updateStatus", "PC_DONATION_TRANSFER_INITIATION");
    
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    try
    {
      registerActivityAndGenerateEvent(sessionContext, activityEventKeyDTO, new Date(), setAlertDetailsDonationTransfer(donationTransferDomain));
    }
    catch (Exception e)
    {
      LOGGER.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while communicating the alert", new Object[0]), e);
    }
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Exit from generateDonationPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
  }
  
  public void generateMasterpassPaymentInitiationAlert(MasterpassTransfer donationTransferDomain)
  {
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into generateDonationPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
    ActivityEventKeyDTO activityEventKeyDTO = setActivityEventKeyDTO("com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer.updateStatus", "PC_DONATION_TRANSFER_INITIATION");
    
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    try
    {
      registerActivityAndGenerateEvent(sessionContext, activityEventKeyDTO, new Date(), setAlertDetailsMasterpassTransfer(donationTransferDomain));
    }
    catch (Exception e)
    {
      LOGGER.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while communicating the alert", new Object[0]), e);
    }
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Exit from generateDonationPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { donationTransferDomain, THIS_COMPONENT_NAME }));
    }
  }
  
  
  private PaymentInitiationActivityLog setAlertDetailsMerchantTransfer(MerchantTransferDomain merchantTransfer)
  {
    PaymentInitiationActivityLog alertDetails = new PaymentInitiationActivityLog();
    CurrencyAmount currencyAmount = new CurrencyAmount();
    currencyAmount.setAmount(merchantTransfer.getAmount().getAmount());
    currencyAmount.setCurrency(merchantTransfer.getAmount().getCurrency());
    
    alertDetails.setAccountId(merchantTransfer.getDebitAccountId());
    alertDetails.setSourceAccountNo(merchantTransfer.getDebitAccountId());
    alertDetails.setPayeeNickname(merchantTransfer.getPayeeNickName());
    
    alertDetails.setCurrencyTransferAmount(currencyAmount);
    alertDetails.setValueDate(merchantTransfer.getValueDate());
    return alertDetails;
  }
  
  public void generateMerchantPaymentInitiationAlert(MerchantTransferDomain merchantTransferDomain)
  {
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Entered into generateMerchantPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { merchantTransferDomain, THIS_COMPONENT_NAME }));
    }
    ActivityEventKeyDTO activityEventKeyDTO = setActivityEventKeyDTO("com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransfer.updateStatus", "PC_MERCHANT_TRANSFER_INITIATION");
    
    SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
    try
    {
      registerActivityAndGenerateEvent(sessionContext, activityEventKeyDTO, new Date(), setAlertDetailsMerchantTransfer(merchantTransferDomain));
    }
    catch (Exception e)
    {
      LOGGER.log(Level.SEVERE, FORMATTER.formatMessage("Exception encountered while communicating the alert", new Object[0]), e);
    }
    if (LOGGER.isLoggable(Level.FINE)) {
      LOGGER.log(Level.FINE, FORMATTER
      
        .formatMessage("Exit from generateMerchantPaymentInitiationAlert method of PaymentAlertHelper class  Input: Transfer: %s in class '%s'", new Object[] { merchantTransferDomain, THIS_COMPONENT_NAME }));
    }
  }

}
