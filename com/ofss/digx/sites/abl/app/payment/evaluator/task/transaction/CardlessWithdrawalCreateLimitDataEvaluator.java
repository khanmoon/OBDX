package com.ofss.digx.sites.abl.app.payment.evaluator.task.transaction;

import com.ofss.digx.enumeration.finlimit.LimitType;
import com.ofss.digx.finlimit.core.evaluator.limitdata.ILimitDataEvaluator;
import com.ofss.digx.finlimit.core.evaluator.limitdata.LimitData;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

import java.util.ArrayList;
import java.util.List;

public class CardlessWithdrawalCreateLimitDataEvaluator
  implements ILimitDataEvaluator
{
  public static final String PARAMETERS = "Parameters";
  
  public LimitData evaluate(List<Object> serviceParameters)
    throws Exception
  {
    SessionContext sessionContext = null;
    CardlessWithdrawalCreateRequestDTO cardlessWithdrawalCreateRequestDT = null;
    LimitData limitData = new LimitData();
    for (Object serviceParameter : serviceParameters) {
      if ((serviceParameter instanceof SessionContext)) {
        sessionContext = (SessionContext)serviceParameter;
      } else if ((serviceParameter instanceof CardlessWithdrawalCreateRequestDTO)) {
    	  cardlessWithdrawalCreateRequestDT = (CardlessWithdrawalCreateRequestDTO)serviceParameter;
      }
    }
    if ((cardlessWithdrawalCreateRequestDT != null) && (sessionContext != null))
    {
      limitData.setCurrencyAmount(cardlessWithdrawalCreateRequestDT.getTransferDetails().getAmount());
      Object limitTypesToBeValidated = new ArrayList();
      ((List)limitTypesToBeValidated).add(LimitType.TRANSACTION);
      limitData.setLimitTypesToBeValidated((List)limitTypesToBeValidated);
    }
    return limitData;
  }
}
