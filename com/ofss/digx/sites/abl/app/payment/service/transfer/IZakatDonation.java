package com.ofss.digx.sites.abl.app.payment.service.transfer;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDTO;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.fc.datatype.Date;
import javax.ws.rs.core.Response;

public abstract interface IZakatDonation
{
  public abstract Response read(String paramString);
  
  public abstract Response create(ZakatDonationDTO paramZakatDonationDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract Response list(Date paramDate1, Date paramDate2, PaymentStatusType paramPaymentStatusType);
}
