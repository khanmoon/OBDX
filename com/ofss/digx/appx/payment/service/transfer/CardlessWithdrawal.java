package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityRequestDTO;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityResponseDTO;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.appx.dda.service.DemandDeposit;
import com.ofss.digx.appx.writer.content.PDFContentPublisher;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.enumeration.accounts.TransactionType;
import com.ofss.digx.enumeration.accounts.statement.SearchByType;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalUpdateRequestDTO;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
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

  @GET
  @Produces({"application/json"})
  @Path("/{accountId}/transactions")
  public Response fetchTransactions(@PathParam("accountId") Account accountId, @QueryParam("fromDate") Date fromDate, @QueryParam("toDate") Date toDate, @QueryParam("noOfTransactions") Integer lastNoOfTransactions, @QueryParam("transactionType") TransactionType transactionType, @QueryParam("searchBy") SearchByType searchCriteria, @QueryParam("fromAmount") String fromAmount, @QueryParam("toAmount") String toAmount, @QueryParam("referenceNo") String referenceNo, @QueryParam("narration") String narration)
   {
	  if (this.logger.isLoggable(Level.FINE)) {
	      this.logger.log(Level.FINE, this.formatter.formatMessage("Entered into fetchTransactions in REST %s: accountId=%s, fromDate=%s, toDate=%s, lastNoOfTransactions=%s, searchCriteria=%s", new Object[] { THIS_COMPONENT_NAME, accountId, fromDate, toDate, lastNoOfTransactions, searchCriteria }));
	    }
	    ChannelContext channelContext = null;
	    DemandDepositAccountActivityRequestDTO accountActivityRequestDTO = new DemandDepositAccountActivityRequestDTO();
	    Response response = null;
	    DemandDepositAccountActivityResponseDTO accountActivityResponse = null;
	    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
	    try
	    {
	      channelContext = super.getChannelContext();
	      channelInteraction.begin(channelContext);
	      accountActivityRequestDTO.setAccountId(accountId);
	      accountActivityRequestDTO.setSearchByType(searchCriteria);
	      accountActivityRequestDTO.setTransactionType(transactionType);
	      accountActivityRequestDTO.setNoOfTransactions(lastNoOfTransactions);
	      accountActivityRequestDTO.setFromDate(fromDate);
	      accountActivityRequestDTO.setToDate(toDate);
	      //accountActivityRequestDTO.setNarration(narration);
	      if (fromAmount != null) {
	        accountActivityRequestDTO.setFromAmount(new CurrencyAmount(new BigDecimal(fromAmount), null));
	      }
	      if (toAmount != null) {
	        accountActivityRequestDTO.setToAmount(new CurrencyAmount(new BigDecimal(toAmount), null));
	      }
	      accountActivityRequestDTO.setReferenceNo(referenceNo);
	      com.ofss.digx.app.dda.service.core.DemandDeposit demandDepositService = new com.ofss.digx.app.dda.service.core.DemandDeposit();
	      accountActivityResponse = demandDepositService.fetchTransactions(channelContext.getSessionContext(), accountActivityRequestDTO);
	      PDFContentPublisher pc = new PDFContentPublisher();
	      try {
			pc.publish((DataTransferObject)accountActivityResponse,new MediaType(),(MultivaluedMap<String, Object>)new MultivaluedHashMap(),(OutputStream)new FileOutputStream("resources"+System.currentTimeMillis()+".pdf"),new Locale("en"),"pdf");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      response = buildResponse(accountActivityResponse, Response.Status.OK);
	      try
	      {
	        channelInteraction.close(channelContext);
	      }
	      catch (com.ofss.digx.infra.exceptions.Exception e)
	      {
	        this.logger.log(Level.SEVERE, this.formatter
	          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
	        
	        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
	      }
	      if (!this.logger.isLoggable(Level.FINE)) {
	    	  logger.log(Level.FINE, formatter.formatMessage("Entering send of merchantTransfer rest service, payment Id: %s", new Object[] { 123 }));
	      }
	    }
	    catch (com.ofss.digx.infra.exceptions.Exception e)
	    {
	      this.logger.log(Level.SEVERE, this.formatter
	        .formatMessage("Exception encountered while invoking the service %s for accountActivityRequestDTO=%s", new Object[] {DemandDeposit.class
	        
	        .getName(), accountActivityRequestDTO }), e);
	      
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
	        this.logger.log(Level.SEVERE, this.formatter
	          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
	        
	        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
	      }
	    }
	    this.logger.log(Level.FINE, this.formatter
	      .formatMessage("Exiting fetchTransactions of Demand Deposit REST service, accountActivityResponse: %s", new Object[] { accountActivityResponse }));
	    return response;
	}
}
