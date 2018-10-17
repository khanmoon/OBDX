package com.ofss.digx.sites.abl.domain.payment.entity.policy;

import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.domain.payment.entity.policy.PaymentBusinessPolicyData;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;

public class MerchantTransferBusinessPolicyData
  extends PaymentBusinessPolicyData
  implements IBusinessPolicyDTO
{
  private String payeeId;
  private Account payeeAccountId;
  
  public Account getPayeeAccountId()
  {
    return this.payeeAccountId;
  }
  
  public void setPayeeAccountId(Account payeeAccountId)
  {
    this.payeeAccountId = payeeAccountId;
  }
  
  public String getPayeeId()
  {
    return this.payeeId;
  }
  
  public void setPayeeId(String payeeId)
  {
    this.payeeId = payeeId;
  }
}
