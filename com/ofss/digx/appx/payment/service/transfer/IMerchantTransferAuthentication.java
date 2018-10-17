package com.ofss.digx.appx.payment.service.transfer;

import javax.ws.rs.core.Response;

public abstract interface IMerchantTransferAuthentication
{
  public abstract Response validateToken();
}
