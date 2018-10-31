package com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDeleteRequestDTO;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;

public class DeleteZakatDonationBusinessPolicyData
  implements IBusinessPolicyDTO
{
  ZakatDonationDeleteRequestDTO ZakatDonationDeleteRequestDTO;
  
  public ZakatDonationDeleteRequestDTO getZakatDonationDeleteRequestDTO()
  {
    return this.ZakatDonationDeleteRequestDTO;
  }
  
  public void setZakatDonationDeleteRequestDTO(ZakatDonationDeleteRequestDTO ZakatDonationDeleteRequestDTO)
  {
    this.ZakatDonationDeleteRequestDTO = ZakatDonationDeleteRequestDTO;
  }
}
