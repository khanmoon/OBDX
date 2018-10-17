package com.ofss.digx.domain.user.entity.poilcy;

import com.ofss.fc.framework.domain.policy.AbstractBusinessPolicy;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Logger;

public class UpdatePartyAddressBusinessPolicy
  extends AbstractBusinessPolicy
{
  private static final String THIS_COMPONENT_NAME = UpdatePartyAddressBusinessPolicy.class.getName();
  private static final MultiEntityLogger FORMATTER = MultiEntityLogger.getUniqueInstance();
  private static final Logger logger = FORMATTER.getLogger(THIS_COMPONENT_NAME);
  private static final String DAY_ONE_CONFIG = "DayOneConfig";
  private static final String ADDRESS_CHANGE = "ADDRESS_CHANGE_ALLOWED";
  private UserCustBusinessPolicyDTO userBusinessPolicyDTO;
  
  public UpdatePartyAddressBusinessPolicy() {}
  
  public UpdatePartyAddressBusinessPolicy(UserCustBusinessPolicyDTO userBusinessPolicyDTO)
  {
    this.userBusinessPolicyDTO = userBusinessPolicyDTO;
  }
  
  public void validatePolicy()
  {
  }
}
