package com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationReadRequestDTO;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;

public class ReadZakatDonationBusinessPolicyData
  implements IBusinessPolicyDTO
{
  ZakatDonationReadRequestDTO ZakatDonationReadRequestDTO;
  
  public ZakatDonationReadRequestDTO getZakatDonationReadRequestDTO()
  {
    return this.ZakatDonationReadRequestDTO;
  }
  
  public void setZakatDonationReadRequestDTO(ZakatDonationReadRequestDTO ZakatDonationReadRequestDTO)
  {
    this.ZakatDonationReadRequestDTO = ZakatDonationReadRequestDTO;
  }
}
