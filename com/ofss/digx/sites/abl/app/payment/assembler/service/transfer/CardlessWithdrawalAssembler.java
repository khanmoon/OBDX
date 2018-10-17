package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.sites.abl.app.payment.dto.payee.CardlessWithdrawalPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDetails;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.CardlessWithdrawalPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;

import java.util.ArrayList;
import java.util.List;

import com.ofss.digx.sites.abl.extxface.payments.impl.dto.CardlessWithdrawalRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;
import com.ofss.digx.extxface.impl.assembler.ExtxfaceAssembler;
import com.ofss.digx.extxface.payments.impl.dto.Beneficiary;

public class CardlessWithdrawalAssembler
  extends ExtxfaceAssembler<CardlessWithdrawalRequest, CardlessWithdrawalResponseDomainDTO>
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
  
  public CardlessWithdrawalDomain toCardlessWithdrawalDomainObjectCreate(CardlessWithdrawalDTO merchantTransferDTO)
    throws Exception
  {
	  CardlessWithdrawalDomain merchantTransfer = null;
    if (merchantTransferDTO != null)
    {
      merchantTransfer = new CardlessWithdrawalDomain();
      merchantTransfer.setAmount(merchantTransferDTO.getAmount());
      merchantTransfer.setDebitAccountId(merchantTransferDTO.getDebitAccountId().getValue());
      merchantTransfer.setPartyId(merchantTransferDTO.getPartyId().getValue());
      if ((merchantTransferDTO.getPurpose() != null) && (merchantTransferDTO.getPurpose().trim().length() > 0)) {
        if (merchantTransferDTO.getPurpose().equals("OTH")) {
          merchantTransfer.setPurpose(merchantTransferDTO.getPurpose() + "~" + merchantTransferDTO
            .getPurposeText());
        } else {
          merchantTransfer.setPurpose(merchantTransferDTO.getPurpose());
        }
      }
      CardlessWithdrawalPayeeDetails merchantPayeeDetails = new CardlessWithdrawalPayeeDetails();
      merchantPayeeDetails.setTpin(merchantTransferDTO.getTpin());
      merchantTransfer.setPayee(merchantPayeeDetails);
      
      merchantTransfer.setRemarks(merchantTransferDTO.getRemarks());
      merchantTransfer.setStatus(merchantTransferDTO.getStatus());
      merchantTransfer.setValueDate(merchantTransferDTO.getValueDate());
    }
    return merchantTransfer;
  }
  
  public CardlessWithdrawalDTO fromCardlessWithdrawalDomainObjectRead(CardlessWithdrawalDomain cardlessWithdrawal)
    throws Exception
  {
	  CardlessWithdrawalDTO cardlessWithdrawalDTO = null;
    if (cardlessWithdrawal != null)
    {
      cardlessWithdrawalDTO = new CardlessWithdrawalDTO();
      cardlessWithdrawalDTO.setAmount(cardlessWithdrawal.getAmount());
      cardlessWithdrawalDTO.setDebitAccountId(new Account(cardlessWithdrawal
        .getDebitAccountId()));
      cardlessWithdrawalDTO.setPartyId(new Party(cardlessWithdrawal.getPartyId()));
      
      String purpose = cardlessWithdrawal.getPurpose();
      if ((purpose != null) && (purpose.trim().length() > 0)) {
        if (!purpose.contains("~"))
        {
          cardlessWithdrawalDTO.setPurpose(purpose);
        }
        else
        {
          String[] purposeToken = purpose.split("~");
          cardlessWithdrawalDTO.setPurpose(purposeToken[0]);
          cardlessWithdrawalDTO.setPurposeText(purposeToken[1]);
        }
      }
      cardlessWithdrawalDTO.setTpin(cardlessWithdrawal.getPayee().getTpin());
      cardlessWithdrawalDTO.setRemarks(cardlessWithdrawal.getRemarks());
      cardlessWithdrawalDTO.setStatus(cardlessWithdrawal.getStatus());
      if (cardlessWithdrawal.getTransactionReference() != null) {
        cardlessWithdrawalDTO.setUserReferenceNo(cardlessWithdrawal.getTransactionReference().getUserReferenceNo());
      }
      cardlessWithdrawalDTO.setValueDate(cardlessWithdrawal.getValueDate());
    }
    return cardlessWithdrawalDTO;
  }
  
  public List<CardlessWithdrawalDetails> fromCardlessWithdrawalDomainObjectList(List<CardlessWithdrawalDomain> merchantTransferList)
    throws Exception
  {
    List<CardlessWithdrawalDetails> merchantTransferDetailsListDTO = null;
    if (merchantTransferList != null)
    {
      merchantTransferDetailsListDTO = new ArrayList();
      for (CardlessWithdrawalDomain merchantTransfer : merchantTransferList)
      {
    	  CardlessWithdrawalDetails merchantTransferDetails = new CardlessWithdrawalDetails();
        
        PaymentDTO paymentDTO = new PaymentDTO();
        CardlessWithdrawalPayeeDTO merchantPayeeDTO = new CardlessWithdrawalPayeeDTO();
        
        paymentDTO.setAmount(merchantTransfer.getAmount());
        paymentDTO.setDebitAccountId(new Account(merchantTransfer.getDebitAccountId()));
        paymentDTO.setPartyId(new Party(merchantTransfer.getPartyId()));
        paymentDTO.setPurpose(merchantTransfer.getPurpose());
        paymentDTO.setRemarks(merchantTransfer.getRemarks());
        paymentDTO.setStatus(merchantTransfer.getStatus());
        if (merchantTransfer.getTransactionReference() != null) {
          paymentDTO.setUserReferenceNo(merchantTransfer.getTransactionReference().getUserReferenceNo());
        }
        paymentDTO.setValueDate(merchantTransfer.getValueDate());
        
        merchantTransferDetails.setTransferDetails(paymentDTO);
        
        merchantTransferDetails.setPayeeDetails(merchantPayeeDTO);
        merchantTransferDetails.setTransferDetails(paymentDTO);
        
        merchantTransferDetails.setPaymentType(PaymentType.INTERNATIONALFT_PAYLATER);
        merchantTransferDetailsListDTO.add(merchantTransferDetails);
      }
    }
    return merchantTransferDetailsListDTO;
  }

@Override
public CardlessWithdrawalResponseDomainDTO fromResponse(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
	CardlessWithdrawalResponseDomainDTO merchantTransferResponseDomainDTO = null;	
	    //PayAnyoneTransferResponse extSystemResponse = (PayAnyoneTransferResponse)arg0[0];
	    MerchantTransferResponse extSystemResponse = (MerchantTransferResponse)arg0[0];
	    if (extSystemResponse != null)
	    {
	      merchantTransferResponseDomainDTO = new CardlessWithdrawalResponseDomainDTO();
	      if ((extSystemResponse.getResult() != null) && (extSystemResponse.getResult().getExternalReferenceId() != null)) {
	    	  merchantTransferResponseDomainDTO.setHostReference(extSystemResponse.getResult().getExternalReferenceId());
	      }
	      if (extSystemResponse.getDictionaryArray() != null) {
	    	  merchantTransferResponseDomainDTO.setDictionaryArray(fromDictionary(extSystemResponse.getDictionaryArray()));
	      }
	    }
	    return merchantTransferResponseDomainDTO;
}

@Override
public CardlessWithdrawalRequest toRequest(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
	CardlessWithdrawalRequest merchantTransferRequest = null;
	CardlessWithdrawalRequestDomainDTO request = (CardlessWithdrawalRequestDomainDTO)arg0[0];
	  Beneficiary beneficiary = new Beneficiary();
	  if (request != null)
	  {   
		  merchantTransferRequest = new CardlessWithdrawalRequest("CardlessWithdrawal"); //Setting Interface ID here - This should be same as the query 
	      if (request.getDictionaryArray() != null) {
	    	  merchantTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
	      }
	
	      merchantTransferRequest.srcAccount = request.getDebitAccountId();
	      merchantTransferRequest.Tpin = request.getTpin();
	      merchantTransferRequest.amount = request.getPmtAmount();
	      
	      merchantTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
	      	      
	      System.out.println("**Ending toRequest method of MerchantPaymentAssembler");
	      System.out.println("srcAccount : " + merchantTransferRequest.srcAccount + "\n" +
	    		  "Tpin : " + merchantTransferRequest.Tpin + "\n" +
	    		  "amount + currency : " + merchantTransferRequest.amount.getAmount() + "+" + merchantTransferRequest.amount.getCurrency()
	    		  );
	    }
	    return merchantTransferRequest;
}
}
