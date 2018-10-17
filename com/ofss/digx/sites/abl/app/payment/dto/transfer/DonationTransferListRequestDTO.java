package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.fc.datatype.Date;

public class DonationTransferListRequestDTO extends com.ofss.fc.framework.domain.common.dto.DataTransferObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private Date fromDate;
  private Date toDate;
  private PaymentStatusType status;
  
  public DonationTransferListRequestDTO() {}
  
  public Date getFromDate()
  {
    return fromDate;
  }
  
  public void setFromDate(Date fromDate)
  {
    this.fromDate = fromDate;
  }
  
  public Date getToDate()
  {
    return toDate;
  }
  
  public void setToDate(Date toDate)
  {
    this.toDate = toDate;
  }
  
  public PaymentStatusType getStatus()
  {
    return status;
  }
  
  public void setStatus(PaymentStatusType status)
  {
    this.status = status;
  }
  
  public String toString()
  {
    return "DonationTransferListRequestDTO [fromDate" + fromDate + "toDate" + toDate + "PaymentStatusType" + status + "]";
  }
}
