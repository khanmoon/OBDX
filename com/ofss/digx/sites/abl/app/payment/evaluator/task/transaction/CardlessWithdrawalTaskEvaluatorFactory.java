package com.ofss.digx.sites.abl.app.payment.evaluator.task.transaction;

import com.ofss.digx.app.payment.evaluator.task.transaction.PaymentsTFAEvaluator;
import com.ofss.digx.enumeration.task.TaskAspect;
import com.ofss.digx.framework.task.evaluator.DefaultEvaluator;
import com.ofss.digx.framework.task.evaluator.ITaskEvaluator;
import com.ofss.digx.framework.task.evaluator.ITaskEvaluatorFactory;

public class CardlessWithdrawalTaskEvaluatorFactory
	  implements ITaskEvaluatorFactory
	{
	  private static CardlessWithdrawalTaskEvaluatorFactory uniqueInstance = null;
	  
	  public ITaskEvaluator getEvaluator(TaskAspect taskAspect)
	  {
		  ITaskEvaluator evaluator = null;
		    switch (taskAspect)
		    {
		    case LIMITS: 
		      evaluator = new CardlessWithdrawalLimitTaskEvaluator();
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
	      synchronized (CardlessWithdrawalTaskEvaluatorFactory.class)
	      {
	        if (uniqueInstance == null) {
	          uniqueInstance = new CardlessWithdrawalTaskEvaluatorFactory();
	        }
	      }
	    }
	    return uniqueInstance;
	  }
	}

