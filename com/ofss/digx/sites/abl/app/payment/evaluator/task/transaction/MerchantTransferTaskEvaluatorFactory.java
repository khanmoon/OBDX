package com.ofss.digx.sites.abl.app.payment.evaluator.task.transaction;

import com.ofss.digx.app.payment.evaluator.task.transaction.PaymentsTFAEvaluator;
import com.ofss.digx.enumeration.task.TaskAspect;
import com.ofss.digx.framework.task.evaluator.DefaultEvaluator;
import com.ofss.digx.framework.task.evaluator.ITaskEvaluator;
import com.ofss.digx.framework.task.evaluator.ITaskEvaluatorFactory;

public class MerchantTransferTaskEvaluatorFactory
	  implements ITaskEvaluatorFactory
	{
	  private static MerchantTransferTaskEvaluatorFactory uniqueInstance = null;
	  
	  public ITaskEvaluator getEvaluator(TaskAspect taskAspect)
	  {
		  ITaskEvaluator evaluator = null;
		    switch (taskAspect)
		    {
		    case LIMITS: 
		      evaluator = new MerchantTransferLimitTaskEvaluator();
		      break;
		    case WORKING_WINDOW: 
		      //evaluator = new BillPaymentWorkingWindowTaskEvaluator();
		      break;
		    case TWO_FACTOR_AUTHENTICATION: 
		      evaluator = new PaymentsTFAEvaluator();
		      break;
		    default: 
		      evaluator = new DefaultEvaluator(taskAspect);
		    }
		    return evaluator;
	  }
	  
	  public static ITaskEvaluatorFactory getInstance()
	  {
	    if (uniqueInstance == null) {
	      synchronized (MerchantTransferTaskEvaluatorFactory.class)
	      {
	        if (uniqueInstance == null) {
	          uniqueInstance = new MerchantTransferTaskEvaluatorFactory();
	        }
	      }
	    }
	    return uniqueInstance;
	  }
	}

