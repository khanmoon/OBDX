package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MerchantPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferDetails;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.MerchantPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;

import java.util.ArrayList;
import java.util.List;

import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.MerchantTransferResponse;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponseDomainDTO;
import com.ofss.digx.extxface.impl.assembler.ExtxfaceAssembler;
import com.ofss.digx.extxface.payments.impl.dto.Beneficiary;

public class MerchantTransferAssembler
  extends ExtxfaceAssembler<MerchantTransferRequest, MerchantTransferResponseDomainDTO>
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
  
  public MerchantTransferDomain toMerchantDomainObjectCreate(MerchantTransferDTO merchantTransferDTO)
    throws Exception
  {
    MerchantTransferDomain merchantTransfer = null;
    if (merchantTransferDTO != null)
    {
      merchantTransfer = new MerchantTransferDomain();
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
      MerchantPayeeDetails merchantPayeeDetails = new MerchantPayeeDetails();
      merchantPayeeDetails.setbillerId(merchantTransferDTO.getbillerId());
      merchantPayeeDetails.setconsumerRefNo(merchantTransferDTO.getconsumerRefNo());
      merchantTransfer.setPayee(merchantPayeeDetails);
      
      merchantTransfer.setRemarks(merchantTransferDTO.getRemarks());
      merchantTransfer.setStatus(merchantTransferDTO.getStatus());
      merchantTransfer.setValueDate(merchantTransferDTO.getValueDate());
    }
    return merchantTransfer;
  }
  
  public MerchantTransferDTO frommerchantDomainObjectRead(MerchantTransferDomain merchantTransfer)
    throws Exception
  {
    MerchantTransferDTO merchantTransferDTO = null;
    if (merchantTransfer != null)
    {
      merchantTransferDTO = new MerchantTransferDTO();
      merchantTransferDTO.setAmount(merchantTransfer.getAmount());
      merchantTransferDTO.setDebitAccountId(new Account(merchantTransfer
        .getDebitAccountId()));
      merchantTransferDTO.setPartyId(new Party(merchantTransfer.getPartyId()));
      
      String purpose = merchantTransfer.getPurpose();
      if ((purpose != null) && (purpose.trim().length() > 0)) {
        if (!purpose.contains("~"))
        {
          merchantTransferDTO.setPurpose(purpose);
        }
        else
        {
          String[] purposeToken = purpose.split("~");
          merchantTransferDTO.setPurpose(purposeToken[0]);
          merchantTransferDTO.setPurposeText(purposeToken[1]);
        }
      }
      merchantTransferDTO.setbillerId(merchantTransfer.getPayee().getbillerId());
      merchantTransferDTO.setconsumerRefNo(merchantTransfer.getPayee().getconsumerRefNo());
      merchantTransferDTO.setRemarks(merchantTransfer.getRemarks());
      merchantTransferDTO.setStatus(merchantTransfer.getStatus());
      if (merchantTransfer.getTransactionReference() != null) {
        merchantTransferDTO.setUserReferenceNo(merchantTransfer.getTransactionReference().getUserReferenceNo());
      }
      merchantTransferDTO.setValueDate(merchantTransfer.getValueDate());
    }
    return merchantTransferDTO;
  }
  
  public List<MerchantTransferDetails> fromMerchantDomainObjectList(List<MerchantTransferDomain> merchantTransferList)
    throws Exception
  {
    List<MerchantTransferDetails> merchantTransferDetailsListDTO = null;
    if (merchantTransferList != null)
    {
      merchantTransferDetailsListDTO = new ArrayList();
      for (MerchantTransferDomain merchantTransfer : merchantTransferList)
      {
        MerchantTransferDetails merchantTransferDetails = new MerchantTransferDetails();
        
        PaymentDTO paymentDTO = new PaymentDTO();
        MerchantPayeeDTO merchantPayeeDTO = new MerchantPayeeDTO();
        
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
public MerchantTransferResponseDomainDTO fromResponse(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
	MerchantTransferResponseDomainDTO merchantTransferResponseDomainDTO = null;	
	    //PayAnyoneTransferResponse extSystemResponse = (PayAnyoneTransferResponse)arg0[0];
	    MerchantTransferResponse extSystemResponse = (MerchantTransferResponse)arg0[0];
	    if (extSystemResponse != null)
	    {
	      merchantTransferResponseDomainDTO = new MerchantTransferResponseDomainDTO();
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
public MerchantTransferRequest toRequest(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
	MerchantTransferRequest merchantTransferRequest = null;
	  MerchantTransferRequestDomainDTO request = (MerchantTransferRequestDomainDTO)arg0[0];
	  Beneficiary beneficiary = new Beneficiary();
	  if (request != null)
	  {   
		  merchantTransferRequest = new MerchantTransferRequest("MerchantPayment"); //Setting Interface ID here - This should be same as the query 
	      if (request.getDictionaryArray() != null) {
	    	  merchantTransferRequest.setDictionaryArray(toDictionary(request.getDictionaryArray()));
	      }
	
	      merchantTransferRequest.srcAccount = request.getDebitAccountId();
	      merchantTransferRequest.billerId = request.getBillerId();
	      merchantTransferRequest.consumerRefNo = request.getConsumerRefNo();
	      merchantTransferRequest.amount = request.getPmtAmount();
	      
	      merchantTransferRequest.setReferenceNo(request.getSystemReferenceNumber());
	      	      
	      System.out.println("**Ending toRequest method of MerchantPaymentAssembler");
	      System.out.println("srcAccount : " + merchantTransferRequest.srcAccount + "\n" +
	    		  "billerId : " + merchantTransferRequest.billerId + "\n" +
	    		  "consumerRefNo : " + merchantTransferRequest.consumerRefNo + "\n" +
	    		  "amount + currency : " + merchantTransferRequest.amount.getAmount() + "+" + merchantTransferRequest.amount.getCurrency()
	    		  );
	    }
	    return merchantTransferRequest;
}
}
