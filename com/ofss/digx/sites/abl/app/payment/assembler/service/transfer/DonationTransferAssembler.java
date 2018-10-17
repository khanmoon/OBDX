package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.extxface.impl.assembler.ExtxfaceAssembler;
import com.ofss.digx.extxface.payments.impl.dto.Beneficiary;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferRequest;
import com.ofss.digx.sites.abl.extxface.payments.impl.dto.DonationTransferResponse;
import com.ofss.fc.enumeration.ModuleType;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.enumeration.payment.PaymentType;
import com.ofss.digx.sites.abl.app.payment.dto.payee.DonationPayeeDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.DonationPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;

import java.util.ArrayList;
import java.util.List;
public class DonationTransferAssembler  extends ExtxfaceAssembler<DonationTransferRequest, DonationTransferResponseDomainDTO>
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
		  
		  public DonationTransfer toDonationDomainObjectCreate(DonationTransferDTO donationTransferDTO)
		    throws Exception
		  {
		    DonationTransfer donationTransfer = null;
		    if (donationTransferDTO != null)
		    {
		      donationTransfer = new DonationTransfer();
		      donationTransfer.setAmount(donationTransferDTO.getAmount());
		      donationTransfer.setDebitAccountId(donationTransferDTO.getDebitAccountId().getValue());
		      donationTransfer.setPartyId(donationTransferDTO.getPartyId().getValue());
		      if ((donationTransferDTO.getPurpose() != null) && (donationTransferDTO.getPurpose().trim().length() > 0)) {
		        if (donationTransferDTO.getPurpose().equals("OTH")) {
		          donationTransfer.setPurpose(donationTransferDTO.getPurpose() + "~" + donationTransferDTO
		            .getPurposeText());
		        } else {
		          donationTransfer.setPurpose(donationTransferDTO.getPurpose());
		        }
		      }
		      DonationPayeeDetails donationPayeeDetails = new DonationPayeeDetails();
		      donationPayeeDetails.setbillerId(donationTransferDTO.getbillerId());
		      donationTransfer.setPayee(donationPayeeDetails);
		      
		      donationTransfer.setRemarks(donationTransferDTO.getRemarks());
		      donationTransfer.setStatus(donationTransferDTO.getStatus());
		      donationTransfer.setValueDate(donationTransferDTO.getValueDate());
		    }
		    return donationTransfer;
		  }
		  
		  public DonationTransferDTO fromdonationDomainObjectRead(DonationTransfer donationTransfer)
		    throws Exception
		  {
		    DonationTransferDTO donationTransferDTO = null;
		    if (donationTransfer != null)
		    {
		      donationTransferDTO = new DonationTransferDTO();
		      donationTransferDTO.setAmount(donationTransfer.getAmount());
		      donationTransferDTO.setDebitAccountId(new Account(donationTransfer
		        .getDebitAccountId()));
		      donationTransferDTO.setPartyId(new Party(donationTransfer.getPartyId()));
		      
		      String purpose = donationTransfer.getPurpose();
		      if ((purpose != null) && (purpose.trim().length() > 0)) {
		        if (!purpose.contains("~"))
		        {
		          donationTransferDTO.setPurpose(purpose);
		        }
		        else
		        {
		          String[] purposeToken = purpose.split("~");
		          donationTransferDTO.setPurpose(purposeToken[0]);
		          donationTransferDTO.setPurposeText(purposeToken[1]);
		        }
		      }
		      donationTransferDTO.setbillerId(donationTransfer.getPayee().getbillerId());
		      donationTransferDTO.setRemarks(donationTransfer.getRemarks());
		      donationTransferDTO.setStatus(donationTransfer.getStatus());
		      if (donationTransfer.getTransactionReference() != null) {
		        donationTransferDTO.setUserReferenceNo(donationTransfer.getTransactionReference().getUserReferenceNo());
		      }
		      donationTransferDTO.setValueDate(donationTransfer.getValueDate());
		    }
		    return donationTransferDTO;
		  }
		  
		  public List<DonationTransferDetails> fromDonationDomainObjectList(List<DonationTransfer> donationTransferList)
		    throws Exception
		  {
		    List<DonationTransferDetails> donationTransferDetailsListDTO = null;
		    if (donationTransferList != null)
		    {
		      donationTransferDetailsListDTO = new ArrayList();
		      for (DonationTransfer donationTransfer : donationTransferList)
		      {
		        DonationTransferDetails donationTransferDetails = new DonationTransferDetails();
		        
		        PaymentDTO paymentDTO = new PaymentDTO();
		        DonationPayeeDTO donationPayeeDTO = new DonationPayeeDTO();
		        
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

@Override
public DonationTransferResponseDomainDTO fromResponse(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
	// TODO Auto-generated method stub
	  DonationTransferResponseDomainDTO donationTransferResponseDomainDTO = null;
	    DonationTransferResponse extSystemResponse = (DonationTransferResponse)arg0[0];
	    if (extSystemResponse != null)
	    {
	      donationTransferResponseDomainDTO = new DonationTransferResponseDomainDTO();
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

@Override
public DonationTransferRequest toRequest(Object... arg0)
		throws com.ofss.digx.infra.exceptions.Exception {
    DonationTransferRequest donationTransferRequest = null;
    DonationTransferRequestDomainDTO request = (DonationTransferRequestDomainDTO)arg0[0];
    Beneficiary beneficiary = new Beneficiary();
    if (request != null)
    {
      donationTransferRequest = new DonationTransferRequest("Donation");
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
