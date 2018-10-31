package com.ofss.digx.sites.abl.domain.payment.entity.policy;

import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.domain.fileupload.ft.policy.PaymentBusinessPolicyData;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;

public class ZakatDonationBusinessPolicyData
  extends PaymentBusinessPolicyData
  implements IBusinessPolicyDTO
{
  private Account creditAccount;
  private CurrencyAmount creditAmount;
  
  public Account getCreditAccount()
  {
    return this.creditAccount;
  }
  
  public void setCreditAccount(Account creditAccount)
  {
    this.creditAccount = creditAccount;
  }

public void setCreditAmount(CurrencyAmount amount) {
	this.creditAmount = amount;
	}

public Object getWorkingWindowCheckResponse() {
	// TODO Auto-generated method stub
	return null;
}


}
