package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
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

public class MerchantTransfer
  extends AbstractRESTApplication
  implements IMerchantTransfer
{
  private static final String THIS_COMPONENT_NAME = MerchantTransfer.class.getName();
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
    MerchantTransferReadResponse readResponse = null;
    MerchantTransferReadRequestDTO merchantTransferReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      merchantTransferReadRequestDTO = new MerchantTransferReadRequestDTO();
      merchantTransferReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService merchantTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService();
      try
      {
        readResponse = merchantTransferService.read(channelContext.getSessionContext(), merchantTransferReadRequestDTO);
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
  public Response create(MerchantTransferDTO merchantTransferDTO)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of merchant Transfer REST Service  Input: merchantTransferDTO: %s", new Object[] { merchantTransferDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    MerchantTransferCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      MerchantTransferCreateRequestDTO merchantTransferCreateRequestDTO = new MerchantTransferCreateRequestDTO();
      merchantTransferCreateRequestDTO.setTransferDetails(merchantTransferDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService merchantTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService();
      createResponse = merchantTransferService.create(channelContext.getSessionContext(), merchantTransferCreateRequestDTO);
      
      response = buildResponse(createResponse, Response.Status.CREATED);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the create service for merchantTransferDTO=%s", new Object[] { merchantTransferDTO }), e);
      
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
    
    logger.log(Level.FINE, formatter.formatMessage("Exiting from create() : merchantTransferCreateResponse=%s", new Object[] { createResponse }));
    
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
    MerchantTransferUpdateRequestDTO merchantTransferUpdateRequestDTO = null;
    MerchantTransferResponse merchantTransferResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      merchantTransferUpdateRequestDTO = new MerchantTransferUpdateRequestDTO();
      merchantTransferUpdateRequestDTO.setPaymentId(paymentId);
      merchantTransferResponse = new MerchantTransferResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService merchantTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MerchantTransferService();
      try
      {
        merchantTransferResponse = merchantTransferService.updateStatus(channelContext.getSessionContext(), merchantTransferUpdateRequestDTO);
        response = buildResponse(merchantTransferResponse, Response.Status.OK);
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
    
    logger.log(Level.FINE, formatter.formatMessage("Exiting from updateStatus() : merchantTransferResponse=%s", new Object[] { merchantTransferResponse }));
    
    return response;
  }
  
  @Path("{paymentId}/authentication")
  public MerchantTransferAuthentication MerchantTransferAuthentication(@PathParam("paymentId") String paymentId)
  {
    MerchantTransferAuthentication merchantTransferAuthentication = new MerchantTransferAuthentication(paymentId);
    merchantTransferAuthentication.setHttpResponse(getHttpResponse());
    merchantTransferAuthentication.setHttpRequest(getHttpRequest());
    merchantTransferAuthentication.setUriInfo(getUriInfo());
    merchantTransferAuthentication.setSecurityContext(getSecurityContext());
    return merchantTransferAuthentication;
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
