package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.extxface.impl.assembler.ExtxfaceAssembler;
import com.ofss.digx.extxface.impl.dto.Result;
import com.ofss.digx.extxface.payments.impl.dto.Beneficiary;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MasterpassPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferDetails;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MasterpassPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MasterpassTransferResponse;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;
import java.util.ArrayList;
import java.util.List;

public class MasterpassTransferAssembler
  extends ExtxfaceAssembler<MasterpassTransferRequest, MasterpassTransferResponseDomainDTO>
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
  
  public MasterpassTransfer toMasterpassDomainObjectCreate(MasterpassDTO masterpassTransferDTO)
    throws java.lang.Exception
  {
    MasterpassTransfer masterpassTransfer = null;
    if (masterpassTransferDTO != null)
    {
      masterpassTransfer = new MasterpassTransfer();
      masterpassTransfer.setAmount(masterpassTransferDTO.getAmount());
      masterpassTransfer.setDebitAccountId(masterpassTransferDTO.getDebitAccountId().getValue());
      masterpassTransfer.setPartyId(masterpassTransferDTO.getPartyId().getValue());
      if ((masterpassTransferDTO.getPurpose() != null) && (masterpassTransferDTO.getPurpose().trim().length() > 0)) {
        if (masterpassTransferDTO.getPurpose().equals("OTH")) {
        	masterpassTransfer.setPurpose(masterpassTransferDTO.getPurpose() + "~" + masterpassTransferDTO
            .getPurposeText());
        } else {
        	masterpassTransfer.setPurpose(masterpassTransferDTO.getPurpose());
        }
      }
//      MasterpassPayeeDetails donationPayeeDetails = new MasterpassPayeeDetails();
//      donationPayeeDetails.setbillerId(masterpassTransferDTO.getbillerId());
//      masterpassTransfer.setPayee(donationPayeeDetails);
      masterpassTransfer.setPartnerId(masterpassTransferDTO.getPartnerId());
      masterpassTransfer.setTransfer_reference(masterpassTransferDTO.getTransfer_reference());
      masterpassTransfer.setSender_first_name(masterpassTransferDTO.getSender_first_name());
      masterpassTransfer.setSender_last_name(masterpassTransferDTO.getSender_last_name());
      masterpassTransfer.setSender_address_city(masterpassTransferDTO.getSender_address_city());
      masterpassTransfer.setSender_address_country(masterpassTransferDTO.getSender_address_country());
      masterpassTransfer.setSender_address_postalcode(masterpassTransferDTO.getSender_address_postalcode());
      masterpassTransfer.setSender_address_line1(masterpassTransferDTO.getSender_address_line1());
      masterpassTransfer.setSender_address_state(masterpassTransferDTO.getSender_address_state());
      masterpassTransfer.setCreditAccountId(masterpassTransferDTO.getCreditAccountId());
      masterpassTransfer.setRecipient_first_name(masterpassTransferDTO.getRecipient_first_name());
      masterpassTransfer.setRecipient_last_name(masterpassTransferDTO.getRecipient_last_name());
      masterpassTransfer.setRecipient_address_city(masterpassTransferDTO.getRecipient_address_city());
      masterpassTransfer.setRecipient_address_country(masterpassTransferDTO.getRecipient_address_country());
      masterpassTransfer.setRecipient_address_postalcode(masterpassTransferDTO.getRecipient_address_postalcode());
      masterpassTransfer.setRecipient_address_line1(masterpassTransferDTO.getRecipient_address_line1());
      masterpassTransfer.setRecipient_address_state(masterpassTransferDTO.getRecipient_address_state());
      masterpassTransfer.setMerchant_category_code(masterpassTransferDTO.getMerchant_category_code());
      masterpassTransfer.setTransaction_local_date_time(masterpassTransferDTO.getTransaction_local_date_time());
      masterpassTransfer.setCard_acceptor_name(masterpassTransferDTO.getCard_acceptor_name());
      
      
      masterpassTransfer.setRemarks(masterpassTransferDTO.getRemarks());
      masterpassTransfer.setStatus(masterpassTransferDTO.getStatus());
      masterpassTransfer.setValueDate(masterpassTransferDTO.getValueDate());
    }
    return masterpassTransfer;
  }
  
  public MasterpassDTO fromMasterpassDomainObjectRead(MasterpassTransfer donationTransfer)
    throws java.lang.Exception
  {
	  MasterpassDTO masterpassTransferDTO = null;
    if (donationTransfer != null)
    {
    	masterpassTransferDTO = new MasterpassDTO();
    	masterpassTransferDTO.setAmount(donationTransfer.getAmount());
    	masterpassTransferDTO.setDebitAccountId(new Account(donationTransfer
        .getDebitAccountId()));
    	masterpassTransferDTO.setPartyId(new Party(donationTransfer.getPartyId()));
      
      String purpose = donationTransfer.getPurpose();
      if ((purpose != null) && (purpose.trim().length() > 0)) {
        if (!purpose.contains("~"))
        {
        	masterpassTransferDTO.setPurpose(purpose);
        }
        else
        {
          String[] purposeToken = purpose.split("~");
          masterpassTransferDTO.setPurpose(purposeToken[0]);
          masterpassTransferDTO.setPurposeText(purposeToken[1]);
        }
      }
//      masterpassTransferDTO.setbillerId(donationTransfer.getPayee().getbillerId());
      masterpassTransferDTO.setRemarks(donationTransfer.getRemarks());
      masterpassTransferDTO.setStatus(donationTransfer.getStatus());
      if (donationTransfer.getTransactionReference() != null) {
    	  masterpassTransferDTO.setUserReferenceNo(donationTransfer.getTransactionReference().getUserReferenceNo());
      }
      masterpassTransferDTO.setValueDate(donationTransfer.getValueDate());
    }
    return masterpassTransferDTO;
  }
  
  public List<MasterpassTransferDetails> fromMasterpassDomainObjectList(List<MasterpassTransfer> donationTransferList)
    throws java.lang.Exception
  {
    List<MasterpassTransferDetails> donationTransferDetailsListDTO = null;
    if (donationTransferList != null)
    {
      donationTransferDetailsListDTO = new ArrayList();
      for (MasterpassTransfer donationTransfer : donationTransferList)
      {
    	  MasterpassTransferDetails donationTransferDetails = new MasterpassTransferDetails();
        
        PaymentDTO paymentDTO = new PaymentDTO();
        MasterpassPayeeDTO donationPayeeDTO = new MasterpassPayeeDTO();
        
        paymentDTO.setAmount(donationTransfer.getAmount());
        paymentDTO.setDebitAccountId(new Account(donationTransfer.getDebitAccountId()));
        paymentDTO.setPartyId(new Party(donationTransfer.getPartyId()));
        paymentDTO.setPurpose(donationTransfer.getPurpose());
        paymentDTO.setRemarks(donationTransfer.getRemarks());
        paymentDTO.setStatus(donationTransfer.getStatus());
        if (donationTransfer.getTransactionReference() != null) {
          paymentDTO.setUserReferenceNo(donationTransfer.getTransactionReference().getUserReferenceNo());
        }
        paymentDTO.setValueDate(donationTransfer.getValueDate());
        
        donationTransferDetails.setTransferDetails(paymentDTO);
        
        donationTransferDetails.setPayeeDetails(donationPayeeDTO);
        donationTransferDetails.setTransferDetails(paymentDTO);
        
        donationTransferDetails.setPaymentType(PaymentType.INTERNATIONALDRAFT_PAYLATER);
        donationTransferDetailsListDTO.add(donationTransferDetails);
      }
    }
    return donationTransferDetailsListDTO;
  }
  
  public MasterpassTransferResponseDomainDTO fromResponse(Object... arg0)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    MasterpassTransferResponseDomainDTO donationTransferResponseDomainDTO = null;
    MasterpassTransferResponse extSystemResponse = (MasterpassTransferResponse)arg0[0];
    if (extSystemResponse != null)
    {
      donationTransferResponseDomainDTO = new MasterpassTransferResponseDomainDTO();
      if ((extSystemResponse.getResult() != null) && 
        (extSystemResponse.getResult().getExternalReferenceId() != null)) {
        donationTransferResponseDomainDTO.setHostReference(extSystemResponse.getResult().getExternalReferenceId());
      }
      if (extSystemResponse.getDictionaryArray() != null) {
        donationTransferResponseDomainDTO.setDictionaryArray(fromDictionary(extSystemResponse.getDictionaryArray()));
      }
    }
    return donationTransferResponseDomainDTO;
  }
  
  public MasterpassTransferRequest toRequest(Object... arg0)
    throws com.ofss.digx.infra.exceptions.Exception
  {
    MasterpassTransferRequest donationTransferRequest = null;
    MasterpassTransferRequestDomainDTO request = (MasterpassTransferRequestDomainDTO)arg0[0];
    Beneficiary beneficiary = new Beneficiary();
    if (request != null)
    {
      donationTransferRequest = new MasterpassTransferRequest("MasterpassPayment");
      if (request.getDictionaryArray() != null) {
        donationTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
      }
      donationTransferRequest.setSrcAccount(request.getSrcAccount());
      donationTransferRequest.setBillerId(request.getBillerId());
      donationTransferRequest.setAmount(request.getPmtAmount());
      
      donationTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
    }
    return donationTransferRequest;
  }
}
