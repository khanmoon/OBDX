package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferValidateRequestDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class PayAnyoneTransferAuthentication
  extends AbstractRESTApplication
  implements IPayAnyoneTransferAuthentication
{
  private static final String THIS_COMPONENT_NAME = PayAnyoneTransferAuthentication.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private String paymentId;
  
  public PayAnyoneTransferAuthentication(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  @PUT
  @Produces({"application/json"})
  public Response validateToken()
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering validateToken of PayAnyone transfer, payment Id: %s", new Object[] { this.paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    PayAnyoneTransferResponse payAnyoneTransferResponse = null;
    PayAnyoneTransferValidateRequestDTO payAnyoneTransferValidateRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      payAnyoneTransferValidateRequestDTO = new PayAnyoneTransferValidateRequestDTO();
      payAnyoneTransferValidateRequestDTO.setPaymentId(this.paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransferAuthentication payAnyoneTransferAuthenticationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransferAuthentication();
      
      payAnyoneTransferResponse = payAnyoneTransferAuthenticationService.validateToken(channelContext
        .getSessionContext(), payAnyoneTransferValidateRequestDTO);
      response = buildResponse(payAnyoneTransferResponse, Response.Status.OK);
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
