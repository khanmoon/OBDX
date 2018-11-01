package com.ofss.digx.sites.abl.app.payment.dto.transfer;

import java.util.ArrayList;

import com.ofss.digx.app.payment.dto.PaymentDTO;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonationCompanyDetails;
import com.ofss.fc.app.dto.validation.Mandatory;

public class ZakatDonationDTO
  extends PaymentDTO
{
  private static final long serialVersionUID = -5025245192478218954L;
  @Mandatory(errorCode="DIGX_PY_0132")
  private ArrayList<ZakatDonationCompanyDetails> companiesList;
  
  public void setCompaniesList(ArrayList<ZakatDonationCompanyDetails> companiesList)
  {
	 this.companiesList = companiesList;
  }
  
  public ArrayList<ZakatDonationCompanyDetails> getCompaniesList()
  {
	  return this.companiesList;
  }
  
  
  public String toString()
  {
    return "ZakatDonationDTO [credit Account Details=" + "CompaniesList" + " ]";
  }
}
