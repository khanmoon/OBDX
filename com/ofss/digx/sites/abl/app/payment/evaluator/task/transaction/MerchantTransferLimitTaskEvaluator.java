package com.ofss.digx.sites.abl.app.payment.evaluator.task.transaction;

import com.ofss.digx.framework.task.evaluator.ITaskEvaluator;
import com.ofss.digx.infra.exceptions.Exception;
import java.util.List;

public class MerchantTransferLimitTaskEvaluator
  implements ITaskEvaluator
{
  private static final String TASK_CODE_BILL_PAYMENT = "PC_F_MERCHANTT";
  private static final String TASK_CODE_BILL_PAYMENT_CREATE = "PC_F_MERCHANTT";
  private static final String TASK_CODE_BILL_PAYMENT_UPDATE = "PC_F_MERCHANTTU";
  
  public String evaluateTaskCode(String taskCode, List<Object> serviceInputs)
    throws Exception
  {
    String evaluatedTaskCode = null;
    switch (taskCode)
    { 
    case "PC_F_MERCHANTT": 
      evaluatedTaskCode = "PC_F_MERCHANTT";
      break;
    case "PC_F_MERCHANTTU": 
        evaluatedTaskCode = "PC_F_MERCHANTTU";
        break;  
    default: 
      evaluatedTaskCode = taskCode;
    }
    return evaluatedTaskCode;
  }
}
