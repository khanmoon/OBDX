package com.ofss.digx.sites.abl.appx.email.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityRequestDTO;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityResponseDTO;
import com.ofss.digx.app.dda.service.core.DemandDeposit;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.payment.service.transfer.CardlessWithdrawalAuthentication;
import com.ofss.digx.datatype.CurrencyAmount;
import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.enumeration.accounts.TransactionType;
import com.ofss.digx.enumeration.accounts.statement.SearchByType;
import com.ofss.digx.sites.abl.app.email.dto.EmailSenderRequestDTO;
import com.ofss.digx.sites.abl.app.email.dto.EmailSenderResponseDTO;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;

@Path("/sendStatement")
public class EmailSender   
extends AbstractRESTApplication
implements IEmailSender{
	private static final String THIS_COMPONENT_NAME = CardlessWithdrawalAuthentication.class.getName();
	private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
	private static transient MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
	
	@GET
	@Produces({"application/json"})
	@Path("/{accountId}/transactions")
	public Response sendPdf(@PathParam("accountId") Account accountId, @QueryParam("fromDate") Date fromDate, @QueryParam("toDate") Date toDate, @QueryParam("noOfTransactions") Integer lastNoOfTransactions, @QueryParam("transactionType") TransactionType transactionType, @QueryParam("searchBy") SearchByType searchCriteria, @QueryParam("fromAmount") String fromAmount, @QueryParam("toAmount") String toAmount, @QueryParam("referenceNo") String referenceNo, @QueryParam("narration") String narration)
	  {
      if (this.logger.isLoggable(Level.FINE)) {
          this.logger.log(Level.FINE, this.formatter.formatMessage("Entered into fetchTransactions in REST %s: accountId=%s, fromDate=%s, toDate=%s, lastNoOfTransactions=%s, searchCriteria=%s", new Object[] { THIS_COMPONENT_NAME, accountId, fromDate, toDate, lastNoOfTransactions, searchCriteria }));
        }
        ChannelContext channelContext = null;
        DemandDepositAccountActivityRequestDTO accountActivityRequestDTO = new DemandDepositAccountActivityRequestDTO();
        Response response = null;
        EmailSenderResponseDTO emailSenderResponse = null;
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
          com.ofss.digx.sites.abl.app.email.service.EmailSender emailSender = new com.ofss.digx.sites.abl.app.email.service.EmailSender();
          EmailSenderRequestDTO emailSenderRequestDTO = new EmailSenderRequestDTO();
          emailSenderRequestDTO.setAccountActivityDetails(accountActivityRequestDTO);
          emailSenderResponse = emailSender.sendPdf(channelContext.getSessionContext(), emailSenderRequestDTO);
          
          
//          com.ofss.digx.app.dda.service.core.DemandDeposit demandDepositService = new com.ofss.digx.app.dda.service.core.DemandDeposit();
//          accountActivityResponse = demandDepositService.fetchTransactions(channelContext.getSessionContext(), accountActivityRequestDTO);
//          PDFContentPublisher pc = new PDFContentPublisher();
//          ByteArrayOutputStream baos = new ByteArrayOutputStream();              
//          pc.publish((DataTransferObject)accountActivityResponse,new MediaType(), new MultivaluedHashMap(),baos,new Locale("en"),"pdf");
          
          response = buildResponse(emailSenderResponse, Response.Status.OK);
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
          .formatMessage("Exiting fetchTransactions of Demand Deposit REST service, accountActivityResponse: %s", new Object[] { emailSenderResponse }));
        return response;
    }

}
