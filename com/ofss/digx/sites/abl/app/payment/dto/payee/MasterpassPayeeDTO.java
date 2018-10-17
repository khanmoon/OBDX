package com.ofss.digx.sites.abl.app.payment.dto.payee;

import com.ofss.digx.app.payment.dto.payee.PayeeDTO;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;

public class MasterpassPayeeDTO
  extends PayeeDTO
{
  private static final long serialVersionUID = 5840045050101028786L;
  private transient ITextMask masker = MaskingFactory.getInstance();
  private String billerId;
  
  public String getbillerId()
  {
    return this.billerId;
  }
  
  public void setbillerId(String billerId)
  {
    this.billerId = billerId;
  }
  
  public String toString()
  {
    return "DonationTransferDTO [billerId=" + this.billerId + " ]";
  }
}
