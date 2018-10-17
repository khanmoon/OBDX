package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.framework.IIndirection;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.framework.security.indirection.handlers.IndirectionHandler;
import com.ofss.fc.app.dto.validation.Mandatory;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonationTransferCreateRequestDTO
  extends DataTransferObject
  implements IIndirection
{
  private static final long serialVersionUID = -5025245192478218954L;
  transient IndirectionHandler indirectionHandler = new IndirectionHandler();
  private static final String THIS_COMPONENT_NAME = DonationTransferCreateRequestDTO.class.getName();
  private transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  @Mandatory(errorCode="DIGX_PY_0155")
  private DonationTransferDTO transferDetails;
  
  public DonationTransferCreateRequestDTO() {}
  
  public DonationTransferDTO getTransferDetails() { return transferDetails; }
  

  public void setTransferDetails(DonationTransferDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public String toString()
  {
    return "DonationTransferCreateRequestDTO [transfer Details=" + transferDetails + " ]";
  }
  
  public void indirectResponse(String sessionId) {}
  
  public void indirectRequest(String sessionId)
  {
    if ((transferDetails != null) && (transferDetails.getDebitAccountId() != null) && (transferDetails.getDebitAccountId().getValue() != null))
    {
      Account account = new Account();
      try
      {
        account.setValue(indirectionHandler.retrieve(sessionId, transferDetails.getDebitAccountId().getValue()));
        transferDetails.setDebitAccountId(account);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, formatter.formatMessage("Exception occurred in converting indirected debit account values to actual debit account values", new Object[0]), e);
      }
    }
  }
}
