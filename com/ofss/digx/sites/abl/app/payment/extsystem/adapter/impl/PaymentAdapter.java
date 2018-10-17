package com.ofss.digx.sites.abl.app.payment.extsystem.adapter.impl;

import com.ofss.digx.app.adapter.AbstractAdapter;
import com.ofss.digx.app.core.AdapterInteraction;
import com.ofss.digx.extxface.impl.ExternalSystemResponseHandler;
import com.ofss.digx.extxface.impl.endpoint.EndpointFactory;
import com.ofss.digx.extxface.impl.endpoint.IEndpoint;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.CardlessWithdrawalAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.DonationTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.MasterpassTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.MerchantTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.assembler.service.transfer.PayAnyoneTransferAssembler;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferRequestDomainDTO;
//import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;
/*import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;*/
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.CardlessWithdrawalRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferRequest;
import com.ofss.extsystem.business.extsystems.HostAdapterManager;
import com.ofss.extsystem.dto.HostRequestDTO;
import com.ofss.extsystem.dto.HostResponseDTO;
//import com.ofss.extsystem.ubs.business.extsystems.HostAdapterManager;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PaymentAdapter
  extends AbstractAdapter
  implements IPaymentAdapter
{
  private static final String THIS_COMPONENT_NAME = PaymentAdapter.class.getName();
  private static final MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private final ExternalSystemResponseHandler responseHandler = ExternalSystemResponseHandler.getInstance();
  
  //--private final PaymentAdapterResponseHandler responseHandler = new PaymentAdapterResponseHandler();
  
  public PaymentAdapter() {}
  
  public PayAnyoneTransferResponseDomainDTO processPayAnyoneTransfer(PayAnyoneTransferRequestDomainDTO payAnyoneTransferRequestDomainDTO)
    throws Exception
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
      
        .formatMessage("Entered in method processPayanyoneTransfer of %s used for Payanyone transfer, %s", new Object[] { THIS_COMPONENT_NAME, payAnyoneTransferRequestDomainDTO }));
    }
    super.checkRequest("com.ofss.digx.sites.abl.app.payment.extsystem.adapter.impl.PaymentAdapter.processPayAnyoneTransfer", new Object[] { payAnyoneTransferRequestDomainDTO });
    
    AdapterInteraction.begin();
    PayAnyoneTransferResponseDomainDTO payAnyoneTransferResponse = null;
    PayAnyoneTransferResponse extSystemResponse = null;
    PayAnyoneTransferAssembler payAnyoneTransferAssembler = null;
    try
    {
    	payAnyoneTransferAssembler = new PayAnyoneTransferAssembler();
    	PayAnyoneTransferRequest extSystemRequest = payAnyoneTransferAssembler.toRequest(new Object[] { payAnyoneTransferRequestDomainDTO });
        
        IEndpoint endpoint = EndpointFactory.getInstance().getEndpoint(extSystemRequest.getInterfaceId());
        extSystemResponse = (PayAnyoneTransferResponse)endpoint.processRequest(extSystemRequest, PayAnyoneTransferResponse.class);
        
        this.responseHandler.checkExternalSystemResponse(extSystemResponse, "TP_DDA_0001");
        payAnyoneTransferResponse = payAnyoneTransferAssembler.fromResponse(new Object[] { extSystemResponse });
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage(" Exception has occured while getting response object of %s inside the processPayanyoneTransfer method of %s for %s. Exception details are %s", new Object[] {PayAnyoneTransferResponseDomainDTO.class
      
        .getName(), THIS_COMPONENT_NAME }), e);
    }
    finally
    {
      AdapterInteraction.close();
    }
    
    setExternalReferenceNumber(payAnyoneTransferResponse.getHostReference());
    super.checkResponse(payAnyoneTransferResponse);
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
      
        .formatMessage("Exiting from method processPayanyoneTransfer of %s used to process Payanyone transfer, PayanyoneTransferReqDTO  = %s", new Object[] { THIS_COMPONENT_NAME, payAnyoneTransferRequestDomainDTO }));
    }
    return payAnyoneTransferResponse;
  }

  public DonationTransferResponseDomainDTO processDonationTransfer(DonationTransferRequestDomainDTO donationTransferReqDTO)
    throws Exception
  {
 
	  System.out.println(donationTransferReqDTO.toString());
      if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entered in method processDonationTransfer of %s used for donation transfer, %s", new Object[] { THIS_COMPONENT_NAME, donationTransferReqDTO }));
    }
    super.checkRequest("com.ofss.digx.extxface.payments.impl.PaymentAdapter.processDonationTransfer", new Object[] { donationTransferReqDTO });
    
    AdapterInteraction.begin();
    DonationTransferResponseDomainDTO donationTransferResponse = null;
    DonationTransferResponse extSystemResponse = null;
    DonationTransferAssembler donationTransferAssembler = null;
    try
    {
      donationTransferAssembler = new DonationTransferAssembler();
      DonationTransferRequest extSystemRequest = donationTransferAssembler.toRequest(new Object[] { donationTransferReqDTO });
      
      IEndpoint endpoint = EndpointFactory.getInstance().getEndpoint(extSystemRequest.getInterfaceId());
      extSystemResponse = (DonationTransferResponse)endpoint.processRequest(extSystemRequest, DonationTransferResponse.class);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage(" Exception has occured while getting response object of %s inside the processDonationTransfer method of %s for %s. Exception details are %s", new Object[] {DonationTransferRequestDomainDTO.class
      
        .getName(), THIS_COMPONENT_NAME, donationTransferReqDTO, e }));
    }
    finally
    {
      AdapterInteraction.close();
    }
    this.responseHandler.checkExternalSystemResponse(extSystemResponse, "TP_PY_0001");
    donationTransferResponse = donationTransferAssembler.fromResponse(new Object[] { extSystemResponse });
    setExternalReferenceNumber(donationTransferResponse.getHostReference());
    super.checkResponse(donationTransferResponse);
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Exiting from method processDonationTransfer of %s used to process donation transfer, donationTransferReqDTO  = %s", new Object[] { THIS_COMPONENT_NAME, donationTransferReqDTO }));
    }
    return donationTransferResponse;
    
  }

  public MerchantTransferResponseDomainDTO processMerchantTransfer(MerchantTransferRequestDomainDTO merchantTransferReqDTO)
		    throws Exception
		  {
		 
			  System.out.println(merchantTransferReqDTO.toString());
		      if (logger.isLoggable(Level.FINE)) {
		      logger.log(Level.FINE, formatter
		        .formatMessage("Entered in method processMerchantTransfer of %s used for merchant transfer, %s", new Object[] { THIS_COMPONENT_NAME, merchantTransferReqDTO }));
		    }
		    super.checkRequest("com.ofss.digx.extxface.payments.impl.PaymentAdapter.processMerchantTransfer", new Object[] { merchantTransferReqDTO });
		    
		    AdapterInteraction.begin();
		    MerchantTransferResponseDomainDTO merchantTransferResponse = null;
		    MerchantTransferResponse extSystemResponse = null;
		    MerchantTransferAssembler merchantTransferAssembler = null;
		    try
		    {
		      merchantTransferAssembler = new MerchantTransferAssembler();
		      MerchantTransferRequest extSystemRequest = merchantTransferAssembler.toRequest(new Object[] { merchantTransferReqDTO });
		      
		      IEndpoint endpoint = EndpointFactory.getInstance().getEndpoint(extSystemRequest.getInterfaceId());
		      extSystemResponse = (MerchantTransferResponse)endpoint.processRequest(extSystemRequest, MerchantTransferResponse.class);
		    }
		    catch (Exception e)
		    {
		      logger.log(Level.SEVERE, formatter.formatMessage(" Exception has occured while getting response object of %s inside the processMerchantTransfer method of %s for %s. Exception details are %s", new Object[] {MerchantTransferRequestDomainDTO.class
		      
		        .getName(), THIS_COMPONENT_NAME, merchantTransferReqDTO, e }));
		    }
		    finally
		    {
		      AdapterInteraction.close();
		    }
		    this.responseHandler.checkExternalSystemResponse(extSystemResponse, "TP_PY_0001");
		    merchantTransferResponse = merchantTransferAssembler.fromResponse(new Object[] { extSystemResponse });
		    setExternalReferenceNumber(merchantTransferResponse.getHostReference());
		    super.checkResponse(merchantTransferResponse);
		    if (logger.isLoggable(Level.FINE)) {
		      logger.log(Level.FINE, formatter
		        .formatMessage("Exiting from method processMerchantTransfer of %s used to process merchant transfer, merchantTransferReqDTO  = %s", new Object[] { THIS_COMPONENT_NAME, merchantTransferReqDTO }));
		    }
		    return merchantTransferResponse;
		    
		  }
  public CardlessWithdrawalResponseDomainDTO processCardlessWithdrawal(CardlessWithdrawalRequestDomainDTO cardlessWithdrawalReqDTO)
		    throws Exception
		  {
		 
			  System.out.println(cardlessWithdrawalReqDTO.toString());
		      if (logger.isLoggable(Level.FINE)) {
		      logger.log(Level.FINE, formatter
		        .formatMessage("Entered in method processMerchantTransfer of %s used for merchant transfer, %s", new Object[] { THIS_COMPONENT_NAME, cardlessWithdrawalReqDTO }));
		    }
		    super.checkRequest("com.ofss.digx.extxface.payments.impl.PaymentAdapter.processCardlessWithdrawal", new Object[] { cardlessWithdrawalReqDTO });
		    
		    AdapterInteraction.begin();
		    CardlessWithdrawalResponseDomainDTO cardlessWithdrawalResponse = null;
		    CardlessWithdrawalResponse extSystemResponse = null;
		    CardlessWithdrawalAssembler cardlessWithdrawalAssembler = null;
		    try
		    {
		      cardlessWithdrawalAssembler = new CardlessWithdrawalAssembler();
		      CardlessWithdrawalRequest extSystemRequest = cardlessWithdrawalAssembler.toRequest(new Object[] { cardlessWithdrawalReqDTO });
		      
		      IEndpoint endpoint = EndpointFactory.getInstance().getEndpoint(extSystemRequest.getInterfaceId());
		      extSystemResponse = (CardlessWithdrawalResponse)endpoint.processRequest(extSystemRequest, CardlessWithdrawalResponse.class);
		    }
		    catch (Exception e)
		    {
		      logger.log(Level.SEVERE, formatter.formatMessage(" Exception has occured while getting response object of %s inside the processMerchantTransfer method of %s for %s. Exception details are %s", new Object[] {MerchantTransferRequestDomainDTO.class
		      
		        .getName(), THIS_COMPONENT_NAME, cardlessWithdrawalReqDTO, e }));
		    }
		    finally
		    {
		      AdapterInteraction.close();
		    }
		    this.responseHandler.checkExternalSystemResponse(extSystemResponse, "TP_PY_0001");
		    cardlessWithdrawalResponse = cardlessWithdrawalAssembler.fromResponse(new Object[] { extSystemResponse });
		    setExternalReferenceNumber(cardlessWithdrawalResponse.getHostReference());
		    super.checkResponse(cardlessWithdrawalResponse);
		    if (logger.isLoggable(Level.FINE)) {
		      logger.log(Level.FINE, formatter
		        .formatMessage("Exiting from method processCardlessWithdrawal of %s used to process cardless withdrawal, cardlessWithdrawalReqDTO  = %s", new Object[] { THIS_COMPONENT_NAME, cardlessWithdrawalReqDTO }));
		    }
		    return cardlessWithdrawalResponse;
		    
		  }

@Override
public MasterpassTransferResponseDomainDTO processMasterpassTransfer(MasterpassTransferRequestDomainDTO masterpassTransferReqDTO) {
	System.out.println(masterpassTransferReqDTO.toString());
    if (logger.isLoggable(Level.FINE)) {
    logger.log(Level.FINE, formatter
      .formatMessage("Entered in method processDonationTransfer of %s used for donation transfer, %s", new Object[] { THIS_COMPONENT_NAME, masterpassTransferReqDTO }));
  }
  super.checkRequest("com.ofss.digx.extxface.payments.impl.PaymentAdapter.processDonationTransfer", new Object[] { masterpassTransferReqDTO });
  
  AdapterInteraction.begin();
  MasterpassTransferResponseDomainDTO donationTransferResponse = null;
  MasterpassTransferResponse extSystemResponse = null;
  MasterpassTransferAssembler masterpassTransferAssembler = null;
  try
  {
    masterpassTransferAssembler = new MasterpassTransferAssembler();
    MasterpassTransferRequest extSystemRequest = masterpassTransferAssembler.toRequest(new Object[] { masterpassTransferReqDTO });
    
    IEndpoint endpoint = EndpointFactory.getInstance().getEndpoint(extSystemRequest.getInterfaceId());
    extSystemResponse = (MasterpassTransferResponse)endpoint.processRequest(extSystemRequest, MasterpassTransferResponse.class);
  }
  catch (Exception e)
  {
    logger.log(Level.SEVERE, formatter.formatMessage(" Exception has occured while getting response object of %s inside the processDonationTransfer method of %s for %s. Exception details are %s", new Object[] {DonationTransferRequestDomainDTO.class
    
      .getName(), THIS_COMPONENT_NAME, masterpassTransferReqDTO, e }));
  }
  finally
  {
    AdapterInteraction.close();
  }
  try {
	this.responseHandler.checkExternalSystemResponse(extSystemResponse, "TP_PY_0001");
	donationTransferResponse = masterpassTransferAssembler.fromResponse(new Object[] { extSystemResponse });
} catch (com.ofss.digx.infra.exceptions.Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  setExternalReferenceNumber(donationTransferResponse.getHostReference());
  super.checkResponse(donationTransferResponse);
  if (logger.isLoggable(Level.FINE)) {
    logger.log(Level.FINE, formatter
      .formatMessage("Exiting from method processDonationTransfer of %s used to process donation transfer, donationTransferReqDTO  = %s", new Object[] { THIS_COMPONENT_NAME, masterpassTransferReqDTO }));
  }
  return donationTransferResponse;
}
}
