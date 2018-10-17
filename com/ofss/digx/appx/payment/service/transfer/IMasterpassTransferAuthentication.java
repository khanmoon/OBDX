package com.ofss.digx.appx.payment.service.transfer;

import javax.ws.rs.core.Response;

public abstract interface IMasterpassTransferAuthentication
{
  public abstract Response validateToken();
}
