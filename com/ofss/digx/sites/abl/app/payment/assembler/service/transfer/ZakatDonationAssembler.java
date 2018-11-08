package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDetails;
import com.ofss.digx.sites.abl.datatype.CardAcceptor;
import com.ofss.digx.sites.abl.datatype.MasterpassCard;
import com.ofss.digx.sites.abl.datatype.MasterpassIdentification;
import com.ofss.digx.datatype.Address;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.Name;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonationCompanyDetails;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.ZakatDonationTransferRequest;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;
import java.util.ArrayList;
import java.util.List;

public class ZakatDonationAssembler
  extends AbstractAssembler
{
  public DomainObjectDTO fromDomainObject(IAbstractDomainObject arg0)
    throws FatalException
  {
    return null;
  }
  
  public IAbstractDomainObject toDomainObject(DomainObjectDTO arg0)
    throws FatalException
  {
    return null;
  }
  
  public ZakatDonation toSelfDomainObjectCreate(ZakatDonationDTO ZakatDonationDTO)
    throws Exception
  {
    ZakatDonation ZakatDonationDomain = null;
    if (ZakatDonationDTO != null)
    {
      populateDataTransferObjectDTOMap("com.ofss.digx.app.payment.dto.transfer.ZakatDonationDTO", ZakatDonationDTO);
      
      ZakatDonationDomain = new ZakatDonation();
      ZakatDonationDomain.setAmount(ZakatDonationDTO.getAmount());
      ZakatDonationDomain.setDebitAccountId(ZakatDonationDTO.getDebitAccountId().getValue());
      //ZakatDonationDomain.setCreditAccountId(ZakatDonationDTO.getCreditAccountId().getValue());
      
      ZakatDonationDomain.setCompanyID(ZakatDonationDTO.getCompanyId());
      
      ZakatDonationDomain.setPartyId(ZakatDonationDTO.getPartyId().getValue());
      if (ZakatDonationDTO.getPurpose() != null) {
        if (ZakatDonationDTO.getPurpose().equals("OTH")) {
          ZakatDonationDomain.setPurpose(ZakatDonationDTO.getPurpose() + "~" + ZakatDonationDTO.getPurposeText());
        } else {
          ZakatDonationDomain.setPurpose(ZakatDonationDTO.getPurpose());
        }
      }
      ZakatDonationDomain.setRemarks(ZakatDonationDTO.getRemarks());
      ZakatDonationDomain.setStatus(ZakatDonationDTO.getStatus());
      ZakatDonationDomain.setValueDate(ZakatDonationDTO.getValueDate());
    }
    return ZakatDonationDomain;
  }
  
  public ZakatDonationDTO fromSelfDomainObjectRead(ZakatDonation ZakatDonationDomain)
   
  {
    ZakatDonationDTO ZakatDonationDTO = null;
    if (ZakatDonationDomain != null)
    {
      ZakatDonationDTO = new ZakatDonationDTO();
      
      ZakatDonationDTO.setCompanyID(ZakatDonationDomain.getCompanyId());
      
      ZakatDonationDTO.setPartyId(new Party(ZakatDonationDomain.getPartyId()));
      ZakatDonationDTO.setAmount(ZakatDonationDomain.getAmount());
      ZakatDonationDTO.setDebitAccountId(new Account(ZakatDonationDomain.getDebitAccountId()));
      ZakatDonationDTO.setRemarks(ZakatDonationDomain.getRemarks());
      ZakatDonationDTO.setStatus(ZakatDonationDomain.getStatus());
      if (ZakatDonationDomain.getTransactionReference() != null) {
        ZakatDonationDTO.setUserReferenceNo(ZakatDonationDomain.getTransactionReference().getUserReferenceNo());
      }
      ZakatDonationDTO.setValueDate(ZakatDonationDomain.getValueDate());
      ZakatDonationDTO.setDictionaryArray(getDictionaryArray(ZakatDonationDomain));
  
      ArrayList<ZakatDonationCompanyDetails> companiesList = new ArrayList<ZakatDonationCompanyDetails>();
      
      if(ZakatDonationDomain.getCompaniesList().size() > 0)
      {
          companiesList = (ArrayList<ZakatDonationCompanyDetails>) ZakatDonationDomain.getCompaniesList();
      }
      
      ZakatDonationDTO.setCompaniesList(companiesList); 
      
    }
    return ZakatDonationDTO;
  }
  
  
  public ZakatDonationDTO fromSelfDomainObjectCompanyList(ZakatDonation ZakatDonationDomain)
  
  {
    ZakatDonationDTO ZakatDonationDTO = null;
    if (ZakatDonationDomain != null)
    {
      ZakatDonationDTO = new ZakatDonationDTO();
      ZakatDonationDTO.setAmount(ZakatDonationDomain.getAmount());
      ZakatDonationDTO.setDebitAccountId(new Account(ZakatDonationDomain.getDebitAccountId()));
      
      ArrayList<ZakatDonationCompanyDetails> companiesList = new ArrayList<ZakatDonationCompanyDetails>();
      
      if(ZakatDonationDomain.getCompaniesList().size() > 0)
      {
          companiesList = (ArrayList<ZakatDonationCompanyDetails>) ZakatDonationDomain.getCompaniesList();
      }
      
      ZakatDonationDTO.setCompaniesList(companiesList); 
      
      ZakatDonationDTO.setPartyId(new Party(ZakatDonationDomain.getPartyId()));
      
      String purpose = ZakatDonationDomain.getPurpose();
      if ((purpose != null) && (purpose.trim().length() > 0)) {
        if (!purpose.contains("~"))
        {
          ZakatDonationDTO.setPurpose(purpose);
        }
        else
        {
          String[] purposeToken = purpose.split("~");
          ZakatDonationDTO.setPurpose(purposeToken[0]);
          ZakatDonationDTO.setPurposeText(purposeToken[1]);
        }
      }
      ZakatDonationDTO.setRemarks(ZakatDonationDomain.getRemarks());
      ZakatDonationDTO.setStatus(ZakatDonationDomain.getStatus());
      if (ZakatDonationDomain.getTransactionReference() != null) {
        ZakatDonationDTO.setUserReferenceNo(ZakatDonationDomain.getTransactionReference().getUserReferenceNo());
      }
      ZakatDonationDTO.setValueDate(ZakatDonationDomain.getValueDate());
      ZakatDonationDTO.setDictionaryArray(getDictionaryArray(ZakatDonationDomain));
    }
    return ZakatDonationDTO;
  }
  
  public List<ZakatDonationDetails> fromSelfDomainObjectList(List<ZakatDonation> ZakatDonationList)
    throws Exception
  {
    List<ZakatDonationDetails> ZakatDonationDetailsListDTO = null;
    if (ZakatDonationList != null)
    {
      ZakatDonationDetailsListDTO = new ArrayList();
      for (ZakatDonation ZakatDonation : ZakatDonationList)
      {
        ZakatDonationDetails ZakatDonationDetails = new ZakatDonationDetails();
        
        PaymentDTO paymentDTO = new PaymentDTO();
        
        paymentDTO.setAmount(ZakatDonation.getAmount());
        paymentDTO.setDebitAccountId(new Account(ZakatDonation.getDebitAccountId()));
        paymentDTO.setPartyId(new Party(ZakatDonation.getPartyId()));
        paymentDTO.setPurpose(ZakatDonation.getPurpose());
        paymentDTO.setRemarks(ZakatDonation.getRemarks());
        paymentDTO.setStatus(ZakatDonation.getStatus());
        if (ZakatDonation.getTransactionReference() != null) {
          paymentDTO.setUserReferenceNo(ZakatDonation.getTransactionReference().getUserReferenceNo());
        }
        paymentDTO.setValueDate(ZakatDonation.getValueDate());
        
        ZakatDonationDetails.setTransferDetails(paymentDTO);
        
        ZakatDonationDetails.setPaymentType(PaymentType.SELFFT);
        ZakatDonationDetails.setDictionaryArray(getDictionaryArray(ZakatDonation));
        ZakatDonationDetailsListDTO.add(ZakatDonationDetails);
      }
    }
    return ZakatDonationDetailsListDTO;
  }
  
  /*public ZakatDonationTransferRequest toRequest(Object... arg0)
		    throws com.ofss.digx.infra.exceptions.Exception
		  {
	  	    ZakatDonationTransferRequest donationTransferRequest = null;
		   
		    MasterpassTransferRequestDomainDTO request = (MasterpassTransferRequestDomainDTO)arg0[0];
		    Address masterpassAddress = new Address();
		    CurrencyAmount amount = new CurrencyAmount();
		    Name masterpassName = new Name();
		    if (request != null)
		    {
		      donationTransferRequest = new ZakatDonationTransferRequest("MasterpassPayment");
		      if (request.getDictionaryArray() != null) {
		        donationTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
		      }
//		      donationTransferRequest.setSrcAccount(request.getSrcAccount());
//		      donationTransferRequest.setBillerId(request.getBillerId());
//		      donationTransferRequest.setAmount(request.getPmtAmount());
		      donationTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
		      donationTransferRequest.setTransactionReference(request.getSystemReferenceNumber());
		      cardAcceptor.setCountry(request.getRecipient_address_country());
		      cardAcceptor.setName(request.getCard_acceptor_name());
		      cardAcceptor.setPostalCode(request.getRecipient_address_postalcode());
		      cardAcceptor.setState(request.getRecipient_address_state());
		      cardAcceptor.setCity(request.getRecipient_address_city());
		      FundingmasterpassCard.setAccountNumber(request.getDebitAccountId());
		      donationTransferRequest.setFundingCard(FundingmasterpassCard);
		      RecevingmasterpassCard.setAccountNumber(request.getCreditAccountId());
		      donationTransferRequest.setReceivingCard(RecevingmasterpassCard);
		      donationTransferRequest.setLanguageIdentification("ENG");
		      masterpassAddress.setLine1(request.getSender_address_line1());
		      masterpassAddress.setCity(request.getSender_address_city());
		      masterpassAddress.setState(request.getSender_address_state());
		      masterpassAddress.setCountry(request.getSender_address_country());
		      masterpassAddress.setZipCode(request.getSender_address_postalcode());
		      donationTransferRequest.setSenderAddress(masterpassAddress);
		      masterpassName.setFirstName(request.getSender_first_name());
		      masterpassName.setMiddleName(request.getSender_middle_name());
		      masterpassName.setLastName(request.getSender_last_name());
		      donationTransferRequest.setSenderName(masterpassName);
		      amount.setAmount(request.getPmtAmount().getAmount());
		      amount.setCurrency(request.getPmtAmount().getCurrency());
		      donationTransferRequest.setReceivingAmount(amount);
		      donationTransferRequest.setCardAcceptor(cardAcceptor);
		      
		    }
		    return donationTransferRequest;
		  }
  */
  
}
