package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferValidateRequestDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class MerchantTransferAuthentication
  extends AbstractRESTApplication
  implements IMerchantTransferAuthentication
{
  private static final String THIS_COMPONENT_NAME = MerchantTransferAuthentication.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private String paymentId;
  
  public MerchantTransferAuthentication(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  @PUT
  @Produces({"application/json"})
  public Response validateToken()
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering validateToken of Merchant transfer, payment Id: %s", new Object[] { this.paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    MerchantTransferResponse merchantTransferResponse = null;
    MerchantTransferValidateRequestDTO merchantTransferValidateRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      merchantTransferValidateRequestDTO = new MerchantTransferValidateRequestDTO();
      merchantTransferValidateRequestDTO.setPaymentId(this.paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferAuthentication merchantTransferAuthenticationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferAuthentication();
      
      merchantTransferResponse = merchantTransferAuthenticationService.validateToken(channelContext
        .getSessionContext(), merchantTransferValidateRequestDTO);
      response = buildResponse(merchantTransferResponse, Response.Status.OK);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the core service validateToken for paymentId=%s", new Object[] { this.paymentId }), e);
      
      response = buildResponse(e, Response.Status.BAD_REQUEST);
    }
    finally
    {
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    try
    {
      channelInteraction.close(channelContext);
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter
        .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
      response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
    }
    logger.log(Level.FINE, formatter.formatMessage("Exiting from validateToken() : paymentId=%s", new Object[] { this.paymentId }));
    
    return response;
  }
}
