package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferUpdateRequestDTO;
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

public class PayAnyoneTransfer
  extends AbstractRESTApplication
  implements IPayAnyoneTransfer
{
  private static final String THIS_COMPONENT_NAME = PayAnyoneTransfer.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  
  @GET
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response read(@PathParam("paymentId") String paymentId)
  {
	System.out.println("**Entered into read method of PayAnyoneTransfer REST");
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering read of PayanyoneTransfer Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    PayAnyoneTransferReadResponse readResponse = null;
    PayAnyoneTransferReadRequestDTO payAnyoneTransferReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      payAnyoneTransferReadRequestDTO = new PayAnyoneTransferReadRequestDTO();
      payAnyoneTransferReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer payAnyoneTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer();
      try
      {
    	System.out.println("**About to call read method of PayAnyoneTransfer SERVICE");
        readResponse = payAnyoneTransferService.read(channelContext.getSessionContext(), payAnyoneTransferReadRequestDTO);
        System.out.println("**Called read method of PayAnyoneTransfer SERVICE");
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from read() : paymentId=%s", new Object[] { paymentId }));
    
    return response;
  }
  
  @POST
  @Produces({"application/json"})
  @Consumes({"application/json"})
  public Response create(PayAnyoneTransferDTO payAnyoneTransferDTO)
  {
	System.out.println("**Entered into create method of PayAnyoneTransfer REST");
	System.out.println("**Received Account: "+payAnyoneTransferDTO.getDebitAccountId());
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of Payanyone Transfer REST Service  Input: PayanyoneTransferDTO: %s", new Object[] { payAnyoneTransferDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    PayAnyoneTransferCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      PayAnyoneTransferCreateRequestDTO payAnyoneTransferCreateRequestDTO = new PayAnyoneTransferCreateRequestDTO();
      payAnyoneTransferCreateRequestDTO.setTransferDetails(payAnyoneTransferDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer payAnyoneTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer();
      System.out.println("**About to call create method of PayAnyoneTransfer SERVICE");
      createResponse = payAnyoneTransferService.create(channelContext.getSessionContext(), payAnyoneTransferCreateRequestDTO);
      System.out.println("**Called create method of PayAnyoneTransfer SERVICE");
      
      response = buildResponse(createResponse, Response.Status.CREATED);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the create service for PayanyoneTransferDTO=%s", new Object[] { payAnyoneTransferDTO }), e);
      
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from create() : PayanyoneTransferCreateResponse=%s", new Object[] { createResponse }));
    
    return response;
  }
  
  @PATCH
  @Path("/{paymentId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateStatus(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering updateStatus of PayanyoneTransfer rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    PayAnyoneTransferUpdateRequestDTO payAnyoneTransferUpdateRequestDTO = null;
    PayAnyoneTransferResponse payAnyoneTransferResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      payAnyoneTransferUpdateRequestDTO = new PayAnyoneTransferUpdateRequestDTO();
      payAnyoneTransferUpdateRequestDTO.setPaymentId(paymentId);
      payAnyoneTransferResponse = new PayAnyoneTransferResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer payAnyoneTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.PayAnyoneTransfer();
      try
      {
        payAnyoneTransferResponse = payAnyoneTransferService.updateStatus(channelContext.getSessionContext(), payAnyoneTransferUpdateRequestDTO);
        response = buildResponse(payAnyoneTransferResponse, Response.Status.OK);
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from updateStatus() : PayanyoneTransferResponse=%s", new Object[] { payAnyoneTransferResponse }));
    
    return response;
  }
  
  @Path("{paymentId}/authentication")
  public PayAnyoneTransferAuthentication PayAnyoneTransferAuthentication(@PathParam("paymentId") String paymentId)
  {
    PayAnyoneTransferAuthentication payAnyoneTransferAuthentication = new PayAnyoneTransferAuthentication(paymentId);
    payAnyoneTransferAuthentication.setHttpResponse(getHttpResponse());
    payAnyoneTransferAuthentication.setHttpRequest(getHttpRequest());
    payAnyoneTransferAuthentication.setUriInfo(getUriInfo());
    payAnyoneTransferAuthentication.setSecurityContext(getSecurityContext());
    return payAnyoneTransferAuthentication;
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
