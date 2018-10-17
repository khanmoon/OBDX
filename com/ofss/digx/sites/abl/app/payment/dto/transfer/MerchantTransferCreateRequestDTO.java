package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.framework.IIndirection;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.framework.security.indirection.handlers.IndirectionHandler;
import com.ofss.fc.app.dto.validation.Mandatory;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MerchantTransferCreateRequestDTO
  extends DataTransferObject
  implements IIndirection
{
  private static final long serialVersionUID = -5025245192478218954L;
  transient IndirectionHandler indirectionHandler = new IndirectionHandler();
  private static final String THIS_COMPONENT_NAME = MerchantTransferCreateRequestDTO.class.getName();
  private transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  @Mandatory(errorCode="DIGX_PY_0155")
  private MerchantTransferDTO transferDetails;
  
  public MerchantTransferDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(MerchantTransferDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public String toString()
  {
    return "MerchantTransferCreateRequestDTO [transfer Details=" + this.transferDetails + " ]";
  }
  
  public void indirectResponse(String sessionId) {}
  
  public void indirectRequest(String sessionId)
  {
    if ((this.transferDetails != null) && (this.transferDetails.getDebitAccountId() != null) && (this.transferDetails.getDebitAccountId().getValue() != null))
    {
      Account account = new Account();
      try
      {
        account.setValue(this.indirectionHandler.retrieve(sessionId, this.transferDetails.getDebitAccountId().getValue()));
        this.transferDetails.setDebitAccountId(account);
      }
      catch (Exception e)
      {
        this.logger.log(Level.SEVERE, this.formatter.formatMessage("Exception occurred in converting indirected debit account values to actual debit account values", new Object[0]), e);
      }
    }
  }
}
