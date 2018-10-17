package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;

public class MerchantTransferListRequestDTO
  extends DataTransferObject
{
  private static final long serialVersionUID = -5025245192478218954L;
  private Date fromDate;
  private Date toDate;
  private PaymentStatusType status;
  
  public Date getFromDate()
  {
    return this.fromDate;
  }
  
  public void setFromDate(Date fromDate)
  {
    this.fromDate = fromDate;
  }
  
  public Date getToDate()
  {
    return this.toDate;
  }
  
  public void setToDate(Date toDate)
  {
    this.toDate = toDate;
  }
  
  public PaymentStatusType getStatus()
  {
    return this.status;
  }
  
  public void setStatus(PaymentStatusType status)
  {
    this.status = status;
  }
  
  public String toString()
  {
    return "MerchantTransferListRequestDTO [fromDate" + this.fromDate + "toDate" + this.toDate + "PaymentStatusType" + this.status + "]";
  }
}
