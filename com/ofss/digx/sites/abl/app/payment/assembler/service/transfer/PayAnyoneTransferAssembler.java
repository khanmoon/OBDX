package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.extxface.impl.assembler.ExtxfaceAssembler;
import com.ofss.digx.extxface.payments.impl.dto.Beneficiary;
import com.ofss.digx.sites.abl.app.payment.dto.payee.PayAnyonePayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDetails;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.PayAnyonePayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferResponse;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;

import java.util.ArrayList;
import java.util.List;

public class PayAnyoneTransferAssembler
  extends ExtxfaceAssembler<PayAnyoneTransferRequest, PayAnyoneTransferResponseDomainDTO>
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
  
  public PayAnyoneTransfer toPayAnyoneDomainObjectCreate(PayAnyoneTransferDTO payAnyoneTransferDTO)
    throws Exception
  {
    PayAnyoneTransfer payAnyoneTransfer = null;
    if (payAnyoneTransferDTO != null)
    {
      payAnyoneTransfer = new PayAnyoneTransfer();
      payAnyoneTransfer.setAmount(payAnyoneTransferDTO.getAmount());
      payAnyoneTransfer.setDebitAccountId(payAnyoneTransferDTO.getDebitAccountId().getValue());
      payAnyoneTransfer.setPartyId(payAnyoneTransferDTO.getPartyId().getValue());
      if ((payAnyoneTransferDTO.getPurpose() != null) && (payAnyoneTransferDTO.getPurpose().trim().length() > 0)) {
        if (payAnyoneTransferDTO.getPurpose().equals("OTH")) {
          payAnyoneTransfer.setPurpose(payAnyoneTransferDTO.getPurpose() + "~" + payAnyoneTransferDTO
            .getPurposeText());
        } else {
          payAnyoneTransfer.setPurpose(payAnyoneTransferDTO.getPurpose());
        }
      }
      PayAnyonePayeeDetails payAnyonePayeeDetails = new PayAnyonePayeeDetails();
      payAnyonePayeeDetails.setpayeeAddress(payAnyoneTransferDTO.getpayeeAddress());
      payAnyonePayeeDetails.setpayeeCity(payAnyoneTransferDTO.getpayeeCity());
      payAnyonePayeeDetails.setpayeeCNIC(payAnyoneTransferDTO.getpayeeCNIC());
      payAnyonePayeeDetails.setpayeeEmail(payAnyoneTransferDTO.getpayeeEmail());
      payAnyonePayeeDetails.setpayeeMobile(payAnyoneTransferDTO.getpayeeMobile());
      payAnyonePayeeDetails.setpayeeName(payAnyoneTransferDTO.getpayeeName());
      payAnyonePayeeDetails.setpaymentType(payAnyoneTransferDTO.getpaymentType());
      payAnyonePayeeDetails.setrRemitterName(payAnyoneTransferDTO.getRemitterName());
      
      payAnyoneTransfer.setPayee(payAnyonePayeeDetails);
      
      payAnyoneTransfer.setRemarks(payAnyoneTransferDTO.getRemarks());
      payAnyoneTransfer.setStatus(payAnyoneTransferDTO.getStatus());
      payAnyoneTransfer.setValueDate(payAnyoneTransferDTO.getValueDate());
    }
    return payAnyoneTransfer;
  }
  
  public PayAnyoneTransferDTO frompayAnyoneDomainObjectRead(PayAnyoneTransfer payAnyoneTransfer)
    throws Exception
  {
    PayAnyoneTransferDTO payAnyoneTransferDTO = null;
    if (payAnyoneTransfer != null)
    {
      payAnyoneTransferDTO = new PayAnyoneTransferDTO();
      payAnyoneTransferDTO.setAmount(payAnyoneTransfer.getAmount());
      payAnyoneTransferDTO.setDebitAccountId(new Account(payAnyoneTransfer
        .getDebitAccountId()));
      payAnyoneTransferDTO.setPartyId(new Party(payAnyoneTransfer.getPartyId()));
      
      String purpose = payAnyoneTransfer.getPurpose();
      if ((purpose != null) && (purpose.trim().length() > 0)) {
        if (!purpose.contains("~"))
        {
          payAnyoneTransferDTO.setPurpose(purpose);
        }
        else
        {
          String[] purposeToken = purpose.split("~");
          payAnyoneTransferDTO.setPurpose(purposeToken[0]);
          payAnyoneTransferDTO.setPurposeText(purposeToken[1]);
        }
      }
      payAnyoneTransferDTO.setpayeeName(payAnyoneTransfer.getPayee().getpayeeName());
      payAnyoneTransferDTO.setpayeeAddress(payAnyoneTransfer.getPayee().getpayeeAddress());
      payAnyoneTransferDTO.setpayeeCity(payAnyoneTransfer.getPayee().getpayeeCity());
      payAnyoneTransferDTO.setpayeeCNIC(payAnyoneTransfer.getPayee().getpayeeCNIC());
      payAnyoneTransferDTO.setpayeeEmail(payAnyoneTransfer.getPayee().getpayeeEmail());
      payAnyoneTransferDTO.setpayeeMobile(payAnyoneTransfer.getPayee().getpayeeMobile());
      payAnyoneTransferDTO.setpaymentType(payAnyoneTransfer.getPayee().getpaymentType());
      payAnyoneTransferDTO.setRemitterName(payAnyoneTransfer.getPayee().getRemitterName());
      payAnyoneTransferDTO.setRemarks(payAnyoneTransfer.getRemarks());
      payAnyoneTransferDTO.setStatus(payAnyoneTransfer.getStatus());
      if (payAnyoneTransfer.getTransactionReference() != null) {
        payAnyoneTransferDTO.setUserReferenceNo(payAnyoneTransfer.getTransactionReference().getUserReferenceNo());
      }
      payAnyoneTransferDTO.setValueDate(payAnyoneTransfer.getValueDate());
    }
    return payAnyoneTransferDTO;
  }
  
  public List<PayAnyoneTransferDetails> fromPayAnyoneDomainObjectList(List<PayAnyoneTransfer> payAnyoneTransferList)
    throws Exception
  {
    List<PayAnyoneTransferDetails> payAnyoneTransferDetailsListDTO = null;
    if (payAnyoneTransferList != null)
    {
      payAnyoneTransferDetailsListDTO = new ArrayList();
      for (PayAnyoneTransfer payAnyoneTransfer : payAnyoneTransferList)
      {
        PayAnyoneTransferDetails payAnyoneTransferDetails = new PayAnyoneTransferDetails();
        
        PaymentDTO paymentDTO = new PaymentDTO();
        PayAnyonePayeeDTO payAnyonePayeeDTO = new PayAnyonePayeeDTO();
        
        paymentDTO.setAmount(payAnyoneTransfer.getAmount());
        paymentDTO.setDebitAccountId(new Account(payAnyoneTransfer.getDebitAccountId()));
        paymentDTO.setPartyId(new Party(payAnyoneTransfer.getPartyId()));
        paymentDTO.setPurpose(payAnyoneTransfer.getPurpose());
        paymentDTO.setRemarks(payAnyoneTransfer.getRemarks());
        paymentDTO.setStatus(payAnyoneTransfer.getStatus());
        if (payAnyoneTransfer.getTransactionReference() != null) {
          paymentDTO.setUserReferenceNo(payAnyoneTransfer.getTransactionReference().getUserReferenceNo());
        }
        paymentDTO.setValueDate(payAnyoneTransfer.getValueDate());
        
        payAnyoneTransferDetails.setTransferDetails(paymentDTO);
        
        payAnyonePayeeDTO.setpayeeAddress(payAnyoneTransfer.getPayee().getpayeeAddress());
        payAnyonePayeeDTO.setpayeeCity(payAnyoneTransfer.getPayee().getpayeeCity());
        payAnyonePayeeDTO.setpayeeCNIC(payAnyoneTransfer.getPayee().getpayeeCNIC());
        payAnyonePayeeDTO.setpayeeEmail(payAnyoneTransfer.getPayee().getpayeeEmail());
        payAnyonePayeeDTO.setpayeeMobile(payAnyoneTransfer.getPayee().getpayeeMobile());
        payAnyonePayeeDTO.setpayeeName(payAnyoneTransfer.getPayee().getpayeeName());
        payAnyonePayeeDTO.setRemitterName(payAnyoneTransfer.getPayee().getRemitterName());
        
        payAnyoneTransferDetails.setPayeeDetails(payAnyonePayeeDTO);
        payAnyoneTransferDetails.setTransferDetails(paymentDTO);
        payAnyoneTransferDetails.setPaymentType(PaymentType.INTERNATIONALDRAFT);
        payAnyoneTransferDetailsListDTO.add(payAnyoneTransferDetails);
      }
    }
    return payAnyoneTransferDetailsListDTO;
  }


  @Override
  public PayAnyoneTransferResponseDomainDTO fromResponse(Object... arg0) throws com.ofss.digx.infra.exceptions.Exception 
  {
    PayAnyoneTransferResponseDomainDTO payAnyoneTransferResponseDomainDTO = null;
    PayAnyoneTransferResponse extSystemResponse = (PayAnyoneTransferResponse)arg0[0];
    if (extSystemResponse != null)
    {
      payAnyoneTransferResponseDomainDTO = new PayAnyoneTransferResponseDomainDTO();
      if ((extSystemResponse.getResult() != null) && (extSystemResponse.getResult().getExternalReferenceId() != null)) {
    	  payAnyoneTransferResponseDomainDTO.setHostReference(extSystemResponse.getResult().getExternalReferenceId());
      }
      if (extSystemResponse.getDictionaryArray() != null) {
    	  payAnyoneTransferResponseDomainDTO.setDictionaryArray(fromDictionary(extSystemResponse.getDictionaryArray()));
      }
    }
    return payAnyoneTransferResponseDomainDTO;
  }
  

  @Override
  public PayAnyoneTransferRequest toRequest(Object... arg0) throws com.ofss.digx.infra.exceptions.Exception {
	  PayAnyoneTransferRequest payAnyoneTransferRequest = null;
	  PayAnyoneTransferRequestDomainDTO request = (PayAnyoneTransferRequestDomainDTO)arg0[0];
	  Beneficiary beneficiary = new Beneficiary();
	  if (request != null)
	  {
		  payAnyoneTransferRequest = new PayAnyoneTransferRequest("PayAnyone");
	      if (request.getDictionaryArray() != null) {
	    	  payAnyoneTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
	      }
	
	      payAnyoneTransferRequest.srcAccount = request.getDebitAccountId();
	      payAnyoneTransferRequest.payeeName = request.getPayeeName();
	      payAnyoneTransferRequest.payeeCNIC = request.getPayeeCNIC();
	      payAnyoneTransferRequest.payeeAddress = request.getPayeeAddress();
	      payAnyoneTransferRequest.payeeCity = request.getPayeeCity();
	      payAnyoneTransferRequest.payeeEmail = request.getPayeeEmail();
	      payAnyoneTransferRequest.payeeMobile = request.getPayeeMobile();
	      payAnyoneTransferRequest.paymentType = request.getPaymentType();
	      payAnyoneTransferRequest.amount = request.getPmtAmount();
	      payAnyoneTransferRequest.valueDate = request.getValueDate();
	      payAnyoneTransferRequest.remitterName = request.getRemitterName();
	      payAnyoneTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
	      
	      System.out.println("**Ending toRequest method of PayAnyoneTransferAssembler");
	    }
	    return payAnyoneTransferRequest;
  }

  
}
