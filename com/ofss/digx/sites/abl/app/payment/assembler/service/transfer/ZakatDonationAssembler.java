package com.ofss.digx.sites.abl.app.payment.assembler.service.transfer;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDetails;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonationCompanyDetails;
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
    throws Exception
  {
    ZakatDonationDTO ZakatDonationDTO = null;
    if (ZakatDonationDomain != null)
    {
      ZakatDonationDTO = new ZakatDonationDTO();
      ZakatDonationDTO.setAmount(ZakatDonationDomain.getAmount());
      ZakatDonationDTO.setDebitAccountId(new Account(ZakatDonationDomain
        .getDebitAccountId()));
      
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
}
