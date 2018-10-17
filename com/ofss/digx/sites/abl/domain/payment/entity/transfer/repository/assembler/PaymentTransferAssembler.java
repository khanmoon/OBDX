package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler;

import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.domain.payment.entity.TransactionReference;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
//import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferRequestDomainDTO;
//import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.DonationPayeeDetails;
//import com.ofss.digx.sites.abl.domain.payment.entity.payee.MerchantPayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.payee.PayAnyonePayeeDetails;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.CardlessWithdrawalDomain;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.DonationTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MasterpassTransfer;
//import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransfer;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.PayAnyoneTransfer;
import com.ofss.fc.framework.domain.IAbstractDomainObject;
import com.ofss.fc.framework.domain.assembler.AbstractAssembler;
import com.ofss.fc.framework.domain.common.dto.DomainObjectDTO;
import com.ofss.fc.infra.exception.FatalException;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.MerchantTransferDomain;
import java.io.PrintStream;
import java.util.logging.Logger;

public class PaymentTransferAssembler
  extends AbstractAssembler
{
  private static final String THIS_COMPONENT_NAME = PaymentTransferAssembler.class.getName();
  private transient Logger logger;
  
  public PaymentTransferAssembler()
  {
    this.logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  }
  
  private MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private static final String SESSION_ID = "SESSION_ID";
  
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
  
  public PayAnyoneTransferRequestDomainDTO fromDomainObjectPayAnyoneTransfer(PayAnyoneTransfer payAnyoneTransfer)
  {
    PayAnyoneTransferRequestDomainDTO payAnyoneTransferRequestDomainDTO = null;
    if (payAnyoneTransfer != null)
    {
      payAnyoneTransferRequestDomainDTO = new PayAnyoneTransferRequestDomainDTO();
      if (payAnyoneTransfer.getAmount() != null)
      {
        CurrencyAmount amount = new CurrencyAmount();
        if (payAnyoneTransfer.getAmount().getAmount() != null)
        { 
          amount.setAmount(payAnyoneTransfer.getAmount().getAmount());
          amount.setCurrency(payAnyoneTransfer.getAmount().getCurrency());
          payAnyoneTransferRequestDomainDTO.setPmtAmount(amount);
        }
      }
      if (payAnyoneTransfer.getTransactionReference() != null)
      {
        payAnyoneTransferRequestDomainDTO.setSystemReferenceNumber(payAnyoneTransfer.getTransactionReference()
          .getSystemReferenceId());
        payAnyoneTransferRequestDomainDTO.setUserReferenceNo(payAnyoneTransfer.getTransactionReference()
          .getUserReferenceNo());
      }
      payAnyoneTransferRequestDomainDTO.setPurpose(payAnyoneTransfer.getPurpose());
      payAnyoneTransferRequestDomainDTO.setPartyId(payAnyoneTransfer.getPartyId());
      payAnyoneTransferRequestDomainDTO.setDebitAccountId(payAnyoneTransfer.getDebitAccountId());
      payAnyoneTransferRequestDomainDTO.setRemarks(payAnyoneTransfer.getRemarks());
      payAnyoneTransferRequestDomainDTO.setPaymentDate(payAnyoneTransfer.getValueDate());
      payAnyoneTransferRequestDomainDTO.setDictionaryArray(getDictionaryArray(payAnyoneTransfer));
      
      payAnyoneTransferRequestDomainDTO.setPayeeName(payAnyoneTransfer.getPayee().getpayeeName());
      payAnyoneTransferRequestDomainDTO.setPayeeCNIC(payAnyoneTransfer.getPayee().getpayeeCNIC());
      payAnyoneTransferRequestDomainDTO.setPayeeAddress(payAnyoneTransfer.getPayee().getpayeeAddress());
      payAnyoneTransferRequestDomainDTO.setPayeeCity(payAnyoneTransfer.getPayee().getpayeeCity());
      payAnyoneTransferRequestDomainDTO.setPayeeEmail(payAnyoneTransfer.getPayee().getpayeeEmail());
      payAnyoneTransferRequestDomainDTO.setPayeeMobile(payAnyoneTransfer.getPayee().getpayeeMobile());
      payAnyoneTransferRequestDomainDTO.setPaymentType(payAnyoneTransfer.getPayee().getpaymentType());
      payAnyoneTransferRequestDomainDTO.setRemitterName(payAnyoneTransfer.getPayee().getRemitterName());
    }
    return payAnyoneTransferRequestDomainDTO;
  }
  
  public MasterpassTransferRequestDomainDTO fromDomainObjectDonationTransfer(DonationTransfer donationTransfer)
  {
    MasterpassTransferRequestDomainDTO donationTransferRequestDomainDTO = null;
    if (donationTransfer != null)
    {
      donationTransferRequestDomainDTO = new MasterpassTransferRequestDomainDTO();
      if (donationTransfer.getAmount() != null)
      {
        CurrencyAmount amount = new CurrencyAmount();
        if (donationTransfer.getAmount().getAmount() != null)
        {
          amount.setAmount(donationTransfer.getAmount().getAmount());
          amount.setCurrency(donationTransfer.getAmount().getCurrency());
          donationTransferRequestDomainDTO.setPmtAmount(amount);
        }
      }
      if (donationTransfer.getTransactionReference() != null)
      {
        donationTransferRequestDomainDTO.setSystemReferenceNumber(donationTransfer.getTransactionReference()
          .getSystemReferenceId());
        donationTransferRequestDomainDTO.setUserReferenceNo(donationTransfer.getTransactionReference()
          .getUserReferenceNo());
      }
      donationTransferRequestDomainDTO.setSrcAccount(donationTransfer.getDebitAccountId());
      donationTransferRequestDomainDTO.setBillerId(donationTransfer.getPayee().getbillerId());
      donationTransferRequestDomainDTO.setPurpose(donationTransfer.getPurpose());
      donationTransferRequestDomainDTO.setPartyId(donationTransfer.getPartyId());
      donationTransferRequestDomainDTO.setDebitAccountId(donationTransfer.getDebitAccountId());
      donationTransferRequestDomainDTO.setRemarks(donationTransfer.getRemarks());
      donationTransferRequestDomainDTO.setPaymentDate(donationTransfer.getValueDate());
      donationTransferRequestDomainDTO.setDictionaryArray(getDictionaryArray(donationTransfer));
      
      
      //donationTransferRequestDomainDTO.setbillerId(donationTransfer.getPayee().getbillerId());
    }
    return donationTransferRequestDomainDTO;
  }
  
  public MerchantTransferRequestDomainDTO fromDomainObjectMerchantTransfer(MerchantTransferDomain merchantTransfer)
  {
    MerchantTransferRequestDomainDTO merchantTransferRequestDomainDTO = null;
    if (merchantTransfer != null)
    {
      merchantTransferRequestDomainDTO = new MerchantTransferRequestDomainDTO();
      if (merchantTransfer.getAmount() != null)
      {
        CurrencyAmount amount = new CurrencyAmount();
        if (merchantTransfer.getAmount().getAmount() != null)
        {
          amount.setAmount(merchantTransfer.getAmount().getAmount());
          amount.setCurrency(merchantTransfer.getAmount().getCurrency());
          merchantTransferRequestDomainDTO.setPmtAmount(amount);
        }
      }
      if (merchantTransfer.getTransactionReference() != null)
      {
        merchantTransferRequestDomainDTO.setSystemReferenceNumber(merchantTransfer.getTransactionReference()
          .getSystemReferenceId());
        merchantTransferRequestDomainDTO.setUserReferenceNo(merchantTransfer.getTransactionReference()
          .getUserReferenceNo());
      }
      merchantTransferRequestDomainDTO.setPurpose(merchantTransfer.getPurpose());
      merchantTransferRequestDomainDTO.setPartyId(merchantTransfer.getPartyId());
      merchantTransferRequestDomainDTO.setDebitAccountId(merchantTransfer.getDebitAccountId());
      merchantTransferRequestDomainDTO.setRemarks(merchantTransfer.getRemarks());
      merchantTransferRequestDomainDTO.setPaymentDate(merchantTransfer.getValueDate());
      merchantTransferRequestDomainDTO.setDictionaryArray(getDictionaryArray(merchantTransfer));
      
      merchantTransferRequestDomainDTO.setBillerId(merchantTransfer.getPayee().getbillerId());
      merchantTransferRequestDomainDTO.setConsumerRefNo(merchantTransfer.getPayee().getconsumerRefNo());
    }
    return merchantTransferRequestDomainDTO;
  }
  public CardlessWithdrawalRequestDomainDTO fromDomainObjectCardlessWithdrawal(CardlessWithdrawalDomain cardlessWithdrawal)
  {
	  CardlessWithdrawalRequestDomainDTO cardlessWithdrawalRequestDomainDTO = null;
    if (cardlessWithdrawal != null)
    {
      cardlessWithdrawalRequestDomainDTO = new CardlessWithdrawalRequestDomainDTO();
      if (cardlessWithdrawal.getAmount() != null)
      {
        CurrencyAmount amount = new CurrencyAmount();
        if (cardlessWithdrawal.getAmount().getAmount() != null)
        {
          amount.setAmount(cardlessWithdrawal.getAmount().getAmount());
          amount.setCurrency(cardlessWithdrawal.getAmount().getCurrency());
          cardlessWithdrawalRequestDomainDTO.setPmtAmount(amount);
        }
      }
      if (cardlessWithdrawal.getTransactionReference() != null)
      {
        cardlessWithdrawalRequestDomainDTO.setSystemReferenceNumber(cardlessWithdrawal.getTransactionReference()
          .getSystemReferenceId());
        cardlessWithdrawalRequestDomainDTO.setUserReferenceNo(cardlessWithdrawal.getTransactionReference()
          .getUserReferenceNo());
      }
      
      cardlessWithdrawalRequestDomainDTO.setPurpose(cardlessWithdrawal.getPurpose());
      cardlessWithdrawalRequestDomainDTO.setPartyId(cardlessWithdrawal.getPartyId());
      cardlessWithdrawalRequestDomainDTO.setDebitAccountId(cardlessWithdrawal.getDebitAccountId());
      cardlessWithdrawalRequestDomainDTO.setRemarks(cardlessWithdrawal.getRemarks());
      cardlessWithdrawalRequestDomainDTO.setPaymentDate(cardlessWithdrawal.getValueDate());
      cardlessWithdrawalRequestDomainDTO.setDictionaryArray(getDictionaryArray(cardlessWithdrawal));

      cardlessWithdrawalRequestDomainDTO.setTpin(cardlessWithdrawal.getPayee().getTpin());
      cardlessWithdrawalRequestDomainDTO.setWithdrawalRef(cardlessWithdrawal.getPayee().getWithdrawalRef());
    }
    return cardlessWithdrawalRequestDomainDTO;
  }

public MasterpassTransferRequestDomainDTO fromDomainObjectMasterpassTransfer(MasterpassTransfer masterpassTransfer) {
	 MasterpassTransferRequestDomainDTO masterpassTransferRequestDomainDTO = null;
	    if (masterpassTransfer != null)
	    {
	      masterpassTransferRequestDomainDTO = new MasterpassTransferRequestDomainDTO();
	      if (masterpassTransfer.getAmount() != null)
	      {
	        CurrencyAmount amount = new CurrencyAmount();
	        if (masterpassTransfer.getAmount().getAmount() != null)
	        {
	          amount.setAmount(masterpassTransfer.getAmount().getAmount());
	          amount.setCurrency(masterpassTransfer.getAmount().getCurrency());
	          masterpassTransferRequestDomainDTO.setPmtAmount(amount);
	        }
	      }
	      if (masterpassTransfer.getTransactionReference() != null)
	      {
	        masterpassTransferRequestDomainDTO.setSystemReferenceNumber(masterpassTransfer.getTransactionReference()
	          .getSystemReferenceId());
	        masterpassTransferRequestDomainDTO.setUserReferenceNo(masterpassTransfer.getTransactionReference()
	          .getUserReferenceNo());
	      }
	      masterpassTransferRequestDomainDTO.setSrcAccount(masterpassTransfer.getDebitAccountId());
//	      donationTransferRequestDomainDTO.setBillerId(donationTransfer.getPayee().getbillerId());
	      masterpassTransferRequestDomainDTO.setPurpose(masterpassTransfer.getPurpose());
	      masterpassTransferRequestDomainDTO.setPartyId(masterpassTransfer.getPartyId());
	      masterpassTransferRequestDomainDTO.setDebitAccountId(masterpassTransfer.getDebitAccountId());
	      masterpassTransferRequestDomainDTO.setRemarks(masterpassTransfer.getRemarks());
	      masterpassTransferRequestDomainDTO.setPaymentDate(masterpassTransfer.getValueDate());
	      masterpassTransferRequestDomainDTO.setDictionaryArray(getDictionaryArray(masterpassTransfer));
	    }
	    return masterpassTransferRequestDomainDTO;
}
}
