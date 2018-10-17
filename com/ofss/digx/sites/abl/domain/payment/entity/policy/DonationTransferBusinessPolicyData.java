package com.ofss.digx.sites.abl.domain.payment.entity.policy;

import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.domain.payment.entity.policy.PaymentBusinessPolicyData;

public class DonationTransferBusinessPolicyData extends PaymentBusinessPolicyData implements com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO
{
  private String payeeId;
  private Account payeeAccountId;
  
  public DonationTransferBusinessPolicyData() {}
  
  public Account getPayeeAccountId()
  {
    return payeeAccountId;
  }
  
  public void setPayeeAccountId(Account payeeAccountId)
  {
    this.payeeAccountId = payeeAccountId;
  }
  
  public String getPayeeId()
  {
    return payeeId;
  }
  
  public void setPayeeId(String payeeId)
  {
    this.payeeId = payeeId;
  }
}
