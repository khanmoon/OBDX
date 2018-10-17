package com.ofss.digx.sites.abl.app.payment.extsystem.adapter.impl;

import com.ofss.digx.app.adapter.AdapterFactory;
import com.ofss.digx.datatype.NameValuePair;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;

public class PaymentAdapterFactory
  extends AdapterFactory
{
  private static PaymentAdapterFactory adapterFactory = null;
  
  public static PaymentAdapterFactory getInstance()
  {
    if (adapterFactory == null) {
      synchronized (PaymentAdapterFactory.class)
      {
        adapterFactory = new PaymentAdapterFactory();
      }
    }
    return adapterFactory;
  }
  
  private boolean isPaymentAdapterMocked = false;
  
  public Object getAdapter(String adapterClass)
  {
    IPaymentAdapter adapter = null;
    if (!this.isPaymentAdapterMocked)
    {
      if (adapterClass.equals("PaymentAdapter")) {
        adapter = new PaymentAdapter();
      }
    }
    else {
      adapter = new PaymentAdapter();
    }
    return adapter;
  }
  
  public Object getAdapter(String adapter, NameValuePair[] nameValues)
  {
    return null;
  }
}
