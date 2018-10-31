package com.ofss.digx.sites.abl.domain.payment.entity.transfer.policy;

import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationListRequestDTO;
import com.ofss.fc.framework.domain.policy.IBusinessPolicyDTO;

public class ListZakatDonationBusinessPolicyData
  implements IBusinessPolicyDTO
{
  ZakatDonationListRequestDTO ZakatDonationListRequestDTO;
  
  public ZakatDonationListRequestDTO getZakatDonationListRequestDTO()
  {
    return this.ZakatDonationListRequestDTO;
  }
  
  public void setZakatDonationListRequestDTO(ZakatDonationListRequestDTO ZakatDonationListRequestDTO)
  {
    this.ZakatDonationListRequestDTO = ZakatDonationListRequestDTO;
  }
}
