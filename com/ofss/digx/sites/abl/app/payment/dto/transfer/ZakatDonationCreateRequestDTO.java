package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.app.common.dto.DataTransferObject;
import com.ofss.fc.app.dto.validation.Mandatory;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Logger;

public class ZakatDonationCreateRequestDTO
  extends DataTransferObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private static final String THIS_COMPONENT_NAME = ZakatDonationCreateRequestDTO.class.getName();
  private transient MultiEntityLogger formatter;
  
  public ZakatDonationCreateRequestDTO()
  {
    this.formatter = MultiEntityLogger.getUniqueInstance();
  }
  
  private transient Logger logger = MultiEntityLogger.getUniqueInstance()
    .getLogger(THIS_COMPONENT_NAME);
  @Mandatory(errorCode="DIGX_PY_0155")
  private ZakatDonationDTO transferDetails;
  
  public ZakatDonationDTO getTransferDetails()
  {
    return this.transferDetails;
  }
  
  public void setTransferDetails(ZakatDonationDTO transferDetails)
  {
    this.transferDetails = transferDetails;
  }
  
  public String toString()
  {
    return "ZakatDonationCreateRequestDTO [transfer Details=" + this.transferDetails + " ]";
  }
}
