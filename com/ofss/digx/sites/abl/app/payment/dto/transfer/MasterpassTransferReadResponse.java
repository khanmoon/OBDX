package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.framework.IIndirection;
import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.datatype.complex.Party;
import com.ofss.digx.framework.security.indirection.entity.IndirectionType;
import com.ofss.digx.framework.security.indirection.handlers.IndirectionHandler;
import com.ofss.digx.service.response.BaseResponseObject;
import com.ofss.digx.sites.abl.app.payment.dto.payee.MasterpassPayeeDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MasterpassTransferReadResponse
  extends BaseResponseObject
  implements IIndirection
{
  private static final long serialVersionUID = -5025245192478218954L;
  transient IndirectionHandler indirectionHandler = new IndirectionHandler();
  private transient ITextMask masker = MaskingFactory.getInstance();
  private static final String THIS_COMPONENT_NAME = DonationTransferReadResponse.class.getName();
  private transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private String paymentId;
  private PaymentDTO transferDetails;
  private String paymentType;
  private MasterpassPayeeDTO payeeDetails;
  
  public String getPaymentId()
  {
    return this.paymentId;
  }
  
  public void setPaymentId(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  public PaymentDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(PaymentDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public MasterpassPayeeDTO getPayeeDetails()
  {
    return this.payeeDetails;
  }
  
  public void setPayeeDetails(MasterpassPayeeDTO payeeDetails)
  {
    this.payeeDetails = payeeDetails;
  }
  
  public String toString()
  {
    return "PayanyoneTransferReadRequestDTO [paymentId=" + this.paymentId + "transfer Details=" + this.transferDetails + "payee Details=" + this.payeeDetails + " ]";
  }
  
  public void indirectResponse(String sessionId)
  {
    PaymentDTO transferDetails = this.transferDetails;
    try
    {
      if ((transferDetails != null) && (transferDetails.getDebitAccountId() != null))
      {
        transferDetails.getDebitAccountId().setValue(this.masker.mask(transferDetails.getDebitAccountId().getValue(), "AccountNumberMasking", "account_id"));
        transferDetails.getDebitAccountId().setValue(this.indirectionHandler.create(sessionId, transferDetails.getDebitAccountId().getValue(), IndirectionType.ACCOUNT_ID));
      }
      if ((transferDetails != null) && (transferDetails.getPartyId() != null))
      {
        transferDetails.getPartyId().setValue(this.masker.mask(this.transferDetails.getPartyId().getValue(), "PartyIdMasking", "party_id"));
        transferDetails.getPartyId().setValue(this.indirectionHandler.create(sessionId, transferDetails.getPartyId().getValue(), IndirectionType.PARTY_ID));
      }
    }
    catch (Exception e)
    {
      this.logger.log(Level.SEVERE, this.formatter.formatMessage("Exception occurred in converting values of response DTO to thier respective Inditected-masked values.", new Object[0]), e);
    }
  }
  
  public void indirectRequest(String sessionId) {}
  
  public String getPaymentType()
  {
    return this.paymentType;
  }
  
  public void setPaymentType(String paymentType)
  {
    this.paymentType = paymentType;
  }
}
