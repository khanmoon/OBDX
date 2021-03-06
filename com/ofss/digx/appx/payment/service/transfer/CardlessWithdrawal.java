package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalUpdateRequestDTO;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class CardlessWithdrawal
  extends AbstractRESTApplication
  implements ICardlessWithdrawal
{
  private static final String THIS_COMPONENT_NAME = CardlessWithdrawal.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  
  @GET
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response read(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering read of merchantTransfer Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    CardlessWithdrawalReadResponse readResponse = null;
    CardlessWithdrawalReadRequestDTO cardlessWithdrawalReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      cardlessWithdrawalReadRequestDTO = new CardlessWithdrawalReadRequestDTO();
      cardlessWithdrawalReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal cardlessWithdrawalService = new com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal();
      try
      {
        readResponse = cardlessWithdrawalService.read(channelContext.getSessionContext(), cardlessWithdrawalReadRequestDTO);
        response = buildResponse(readResponse, Response.Status.OK);
      }
      catch (Exception e1)
      {
        e1.printStackTrace();
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the read service for payment id=%s", new Object[] { paymentId }), e);
      
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
    
    logger.log(Level.FINE, formatter.formatMessage("Exiting from read() : paymentId=%s", new Object[] { paymentId }));
    
    return response;
  }
  
  @POST
  @Produces({"application/json"})
  @Consumes({"application/json"})
  public Response create(CardlessWithdrawalDTO cardlessWithdrawalDTO)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of merchant Transfer REST Service  Input: merchantTransferDTO: %s", new Object[] { cardlessWithdrawalDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    CardlessWithdrawalCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      CardlessWithdrawalCreateRequestDTO cardlessWithdrawalCreateRequestDTO = new CardlessWithdrawalCreateRequestDTO();
      cardlessWithdrawalCreateRequestDTO.setTransferDetails(cardlessWithdrawalDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal cardlessWithdrawalService = new com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal();
      createResponse = cardlessWithdrawalService.create(channelContext.getSessionContext(), cardlessWithdrawalCreateRequestDTO);
      
      response = buildResponse(createResponse, Response.Status.CREATED);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the create service for merchantTransferDTO=%s", new Object[] { cardlessWithdrawalDTO }), e);
      
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
    
    logger.log(Level.FINE, formatter.formatMessage("Exiting from create() : cardlessWithdrawalCreateResponse=%s", new Object[] { createResponse }));
    
    return response;
  }
  
  @PATCH
  @Path("/{paymentId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateStatus(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering updateStatus of merchantTransfer rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    CardlessWithdrawalUpdateRequestDTO cardlessWithdrawalUpdateRequestDTO = null;
    CardlessWithdrawalResponse cardlessWithdrawalResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      cardlessWithdrawalUpdateRequestDTO = new CardlessWithdrawalUpdateRequestDTO();
      cardlessWithdrawalUpdateRequestDTO.setPaymentId(paymentId);
      cardlessWithdrawalResponse = new CardlessWithdrawalResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal cardlessWithdrawalService = new com.ofss.digx.sites.abl.app.payment.service.transfer.CardlessWithdrawal();
      try
      {
        cardlessWithdrawalResponse = cardlessWithdrawalService.updateStatus(channelContext.getSessionContext(), cardlessWithdrawalUpdateRequestDTO);
        response = buildResponse(cardlessWithdrawalResponse, Response.Status.OK);
      }
      catch (com.ofss.digx.infra.exceptions.Exception exp)
      {
        exp.printStackTrace();
        response = buildResponse(exp, Response.Status.BAD_REQUEST);
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the updateStatus service for payment Id: %s", new Object[] { paymentId }), e);
      
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
    
    logger.log(Level.FINE, formatter.formatMessage("Exiting from updateStatus() : cardlessWithdrawalResponse=%s", new Object[] { cardlessWithdrawalResponse }));
    
    return response;
  }
  
  @Path("{paymentId}/authentication")
  public CardlessWithdrawalAuthentication cardlessWithdrawalAuthentication(@PathParam("paymentId") String paymentId)
  {
	CardlessWithdrawalAuthentication cardlessWithdrawalAuthentication = new CardlessWithdrawalAuthentication(paymentId);
    cardlessWithdrawalAuthentication.setHttpResponse(getHttpResponse());
    cardlessWithdrawalAuthentication.setHttpRequest(getHttpRequest());
    cardlessWithdrawalAuthentication.setUriInfo(getUriInfo());
    cardlessWithdrawalAuthentication.setSecurityContext(getSecurityContext());
    return cardlessWithdrawalAuthentication;
  }
  
  @DELETE
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response delete(@PathParam("paymentId") String paymentId)
  {
    Response response = null;
    return response;
  }
}
