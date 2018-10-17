package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.DonationTransferUpdateRequestDTO;
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

public class DonationTransfer
  extends AbstractRESTApplication
  implements IDonationTransfer
{
  private static final String THIS_COMPONENT_NAME = DonationTransfer.class.getName();
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
    DonationTransferReadResponse readResponse = null;
    DonationTransferReadRequestDTO donationTransferReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      donationTransferReadRequestDTO = new DonationTransferReadRequestDTO();
      donationTransferReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer donationTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer();
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
  @Produces({"application/json"})
  @Consumes({"application/json"})
  public Response create(DonationTransferDTO donationTransferDTO)
  {
	System.out.println("**In Create of Donation Transfer");  
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of donation Transfer REST Service  Input: donationTransferDTO: %s", new Object[] { donationTransferDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    DonationTransferCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      DonationTransferCreateRequestDTO donationTransferCreateRequestDTO = new DonationTransferCreateRequestDTO();
      donationTransferCreateRequestDTO.setTransferDetails(donationTransferDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer donationTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer();
      System.out.println("**About to call create of donationTransferService at service layer ");  
      createResponse = donationTransferService.create(channelContext.getSessionContext(), donationTransferCreateRequestDTO);
      System.out.println("**called create of donationTransferService at service layer "); 
      
      response = buildResponse(createResponse, Response.Status.CREATED);
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (com.ofss.digx.infra.exceptions.Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the create service for donationTransferDTO=%s", new Object[] { donationTransferDTO }), e);
      
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
    DonationTransferUpdateRequestDTO donationTransferUpdateRequestDTO = null;
    DonationTransferResponse donationTransferResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      donationTransferUpdateRequestDTO = new DonationTransferUpdateRequestDTO();
      donationTransferUpdateRequestDTO.setPaymentId(paymentId);
      donationTransferResponse = new DonationTransferResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer donationTransferService = new com.ofss.digx.sites.abl.app.payment.service.transfer.DonationTransfer();
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
  public DonationTransferAuthentication DonationTransferAuthentication(@PathParam("paymentId") String paymentId)
  {
    DonationTransferAuthentication donationTransferAuthentication = new DonationTransferAuthentication(paymentId);
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
