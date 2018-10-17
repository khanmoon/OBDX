package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferUpdateRequestDTO;
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

public class MasterPassTransfer
  extends AbstractRESTApplication
  implements IMasterpassTransfer
{
  private static final String THIS_COMPONENT_NAME = MasterPassTransfer.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
    
  @GET
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response read(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering read of donationTransfer Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    MasterpassTransferReadResponse readResponse = null;
    MasterpassTransferReadRequestDTO donationTransferReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      donationTransferReadRequestDTO = new MasterpassTransferReadRequestDTO();
      donationTransferReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer donationTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer();
      try
      {
        readResponse = donationTransferService.read(channelContext.getSessionContext(), donationTransferReadRequestDTO);
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
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response create(MasterpassDTO masterpassDTO)
  {
	System.out.println("**In Create of Masterpass Transfer");  
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of masterpass Transfer REST Service  Input: donationTransferDTO: %s", new Object[] { masterpassDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    MasterpassTransferCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      MasterpassTransferCreateRequestDTO masterpassTransferCreateRequestDTO = new MasterpassTransferCreateRequestDTO();
      masterpassTransferCreateRequestDTO.setTransferDetails(masterpassDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer masterpassTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer();
      System.out.println("**About to call create of masterpassTransferService at service layer ");  
      createResponse = masterpassTransferService.create(channelContext.getSessionContext(), masterpassTransferCreateRequestDTO);
      System.out.println("**called create of masterpassTransferService at service layer "); 
      
      response = buildResponse(createResponse, Response.Status.CREATED);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the create service for donationTransferDTO=%s", new Object[] { masterpassDTO }), e);
      
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from create() : donationTransferCreateResponse=%s", new Object[] { createResponse }));
    
    return response;
  }
  
  @PATCH
  @Path("/{paymentId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateStatus(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entering updateStatus of donationTransfer rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    MasterpassTransferUpdateRequestDTO donationTransferUpdateRequestDTO = null;
    MasterpassTransferResponse donationTransferResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      donationTransferUpdateRequestDTO = new MasterpassTransferUpdateRequestDTO();
      donationTransferUpdateRequestDTO.setPaymentId(paymentId);
      donationTransferResponse = new MasterpassTransferResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer donationTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.MasterpassTransfer();
      try
      { 
        donationTransferResponse = donationTransferService.updateStatus(channelContext.getSessionContext(), donationTransferUpdateRequestDTO);
        response = buildResponse(donationTransferResponse, Response.Status.OK);
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from updateStatus() : donationTransferResponse=%s", new Object[] { donationTransferResponse }));
    
    return response;
  }
  
  @Path("{paymentId}/authentication")
  public MasterpassTransferAuthentication MasterpassTransferAuthentication(@PathParam("paymentId") String paymentId)
  {
	MasterpassTransferAuthentication donationTransferAuthentication = new MasterpassTransferAuthentication(paymentId);
    donationTransferAuthentication.setHttpResponse(getHttpResponse());
    donationTransferAuthentication.setHttpRequest(getHttpRequest());
    donationTransferAuthentication.setUriInfo(getUriInfo());
    donationTransferAuthentication.setSecurityContext(getSecurityContext());
    return donationTransferAuthentication;
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
