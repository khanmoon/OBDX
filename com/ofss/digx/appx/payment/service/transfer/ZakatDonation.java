package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationDeleteRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationListRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationListResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationUpdateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.service.transfer.IZakatDonation;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ZakatDonation
  extends AbstractRESTApplication
  implements IZakatDonation
{
  private static final String THIS_COMPONENT_NAME = ZakatDonation.class.getName();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  
  @GET
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response read(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering read of Zakat Donation Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    ZakatDonationReadResponse readResponse = null;
    ZakatDonationReadRequestDTO ZakatDonationReadRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      ZakatDonationReadRequestDTO = new ZakatDonationReadRequestDTO();
      ZakatDonationReadRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation ZakatDonationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation();
      readResponse = ZakatDonationService.read(channelContext.getSessionContext(), ZakatDonationReadRequestDTO);
      response = buildResponse(readResponse, Response.Status.OK);
/*      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }*/

    }
    catch (Exception e)
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
      catch (Exception e)
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
  public Response create(ZakatDonationDTO ZakatDonationDTO)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into create of Self Transfer REST Service  Input: ZakatDonationDTO: %s", new Object[] { ZakatDonationDTO }));
    }
    Response response = null;
    ChannelInteraction channelInteraction = null;
    ChannelContext channelContext = null;
    ZakatDonationCreateResponse createResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      ZakatDonationCreateRequestDTO ZakatDonationCreateRequestDTO = new ZakatDonationCreateRequestDTO();
      ZakatDonationCreateRequestDTO.setTransferDetails(ZakatDonationDTO);
      com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation ZakatDonationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation();
      createResponse = ZakatDonationService.create(channelContext.getSessionContext(), ZakatDonationCreateRequestDTO);
      
      response = buildResponse(createResponse, Response.Status.CREATED);
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
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter
        .formatMessage("Exception encountered while invoking the create service for ZakatDonationDTO=%s", new Object[] { ZakatDonationDTO }), e);
      
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
    logger.log(Level.FINE, formatter
      .formatMessage("Exiting from create() : ZakatDonationCreateResponse=%s", new Object[] { createResponse }));
    
    return response;
  }
  
  @PATCH
  @Path("/{paymentId}")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateStatus(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering updateStatus of ZakatDonation Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    Response response = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    ZakatDonationUpdateRequestDTO ZakatDonationUpdateRequestDTO = null;
    ZakatDonationResponse ZakatDonationResponse = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      ZakatDonationUpdateRequestDTO = new ZakatDonationUpdateRequestDTO();
      ZakatDonationUpdateRequestDTO.setPaymentId(paymentId);
      ZakatDonationResponse = new ZakatDonationResponse();
      com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation ZakatDonationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation();
      ZakatDonationResponse = ZakatDonationService.updateStatus(channelContext.getSessionContext(), ZakatDonationUpdateRequestDTO);
      
      response = buildResponse(ZakatDonationResponse, Response.Status.OK);
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
    catch (Exception e)
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
      catch (Exception e)
      {
        logger.log(Level.SEVERE, formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, formatter.formatMessage("Exiting from updateStatus() : ZakatDonationResponse=%s", new Object[] { ZakatDonationResponse }));
    
    return response;
  }
  
  @DELETE
  @Produces({"application/json"})
  @Path("/{paymentId}")
  public Response delete(@PathParam("paymentId") String paymentId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter
        .formatMessage("Entering delete of ZakatDonation Rest service, payment Id: %s", new Object[] { paymentId }));
    }
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    TransactionStatus transactionStatus = null;
    Response response = null;
    ZakatDonationDeleteRequestDTO ZakatDonationDeleteRequestDTO = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      ZakatDonationDeleteRequestDTO = new ZakatDonationDeleteRequestDTO();
      ZakatDonationDeleteRequestDTO.setPaymentId(paymentId);
      com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation ZakatDonationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation();
      transactionStatus = ZakatDonationService.delete(channelContext.getSessionContext(), ZakatDonationDeleteRequestDTO);
      
      response = buildResponse(transactionStatus, Response.Status.OK);
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
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the delete service for ZakatDonationDeleteRequestDTO : %s", new Object[] { ZakatDonationDeleteRequestDTO, e }));
      
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
    logger.log(Level.FINE, formatter.formatMessage("Exiting from delete of Self Transfer REST Service. TransactionStatus : %s", new Object[] { transactionStatus }));
    label349:
    return response;
  }
  
  @GET
  @Produces({"application/json"})
  public Response list(@QueryParam("fromDate") Date fromDate, @QueryParam("toDate") Date toDate, @QueryParam("PaymentStatusType") PaymentStatusType status)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, formatter.formatMessage("Entered into list of Self transfer REST Service.", new Object[0]));
    }
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = null;
    Response response = null;
    ZakatDonationListResponse responseDTO = null;
    ZakatDonationListRequestDTO requestDTO = null;
    com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation ZakatDonationService = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction = ChannelInteraction.getInstance();
      channelInteraction.begin(channelContext);
      requestDTO = new ZakatDonationListRequestDTO();
      requestDTO.setFromDate(fromDate);
      requestDTO.setToDate(toDate);
      requestDTO.setStatus(status);
      ZakatDonationService = new com.ofss.digx.sites.abl.app.payment.service.transfer.ZakatDonation();
      responseDTO = new ZakatDonationListResponse();
      responseDTO = ZakatDonationService.list(channelContext.getSessionContext(), requestDTO);
      response = buildResponse(responseDTO, Response.Status.OK);
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
    catch (Exception e)
    {
      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the list service  %s, ", new Object[] { THIS_COMPONENT_NAME, e }));
      
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
    logger.log(Level.FINE, formatter
      .formatMessage("Exiting from list of Self trasnfer REST Service. Output: ZakatDonationListResponse %s", new Object[] { responseDTO }));
    label386:
    return response;
  }

@Override
public Response readCompanyDetails() {
	// TODO Auto-generated method stub
	return null;
}
}
