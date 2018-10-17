package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferValidateRequestDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class MasterpassTransferAuthentication
  extends AbstractRESTApplication
  implements IMasterpassTransferAuthentication
{
  private static final String THIS_COMPONENT_NAME = MasterpassTransferAuthentication.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private String paymentId;
  
  public MasterpassTransferAuthentication(String paymentId)
  {
    this.paymentId = paymentId;
  }
  
  @PUT
  @Produces({"application/json"})
  public Response validateToken()
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering validateToken of Donation transfer, payment Id: %s", new Object[] { this.paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    MasterpassTransferResponse donationTransferResponse = null;
    MasterpassTransferValidateRequestDTO donationTransferValidateRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      donationTransferValidateRequestDTO = new MasterpassTransferValidateRequestDTO();
      donationTransferValidateRequestDTO.setPaymentId(this.paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransferAuthentication donationTransferAuthenticationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransferAuthentication();
      try
      {
        donationTransferResponse = donationTransferAuthenticationService.validateToken(channelContext
          .getSessionContext(), donationTransferValidateRequestDTO);
        response = buildResponse(donationTransferResponse, Response.Status.OK);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
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
      catch (com.ofss.digx.infra.exceptions.Exception e)
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
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter
        .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
      response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
    }
    logger.log(Level.FINE, formatter.formatMessage("Exiting from validateToken() : paymentId=%s", new Object[] { this.paymentId }));
    
    return response;
  }
}
