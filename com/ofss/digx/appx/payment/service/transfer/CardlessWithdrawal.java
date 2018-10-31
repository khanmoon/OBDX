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
import com.ofss.fc.domain.ep.entity.dispatch.EmailDispatchDetail;
import com.ofss.fc.domain.ep.service.dispatch.EmailDispatcher;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;

import oracle.apps.xdo.XDOException;
import oracle.apps.xdo.template.FOProcessor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.MimetypesFileTypeMap;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

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
	  if (logger.isLoggable(Level.FINE)) {
	      logger.log(Level.FINE, formatter.formatMessage("Entering read of merchantTransfer Rest service, payment Id: %s", new Object[] { accountId }));
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
	        if (fromAmount != null) {
	          accountActivityRequestDTO.setFromAmount(new CurrencyAmount(new BigDecimal(fromAmount), null));
	        }
	        if (toAmount != null) {
	          accountActivityRequestDTO.setToAmount(new CurrencyAmount(new BigDecimal(toAmount), null));
	        }
	      try
	      {
	    	  accountActivityRequestDTO.setReferenceNo(referenceNo);
	          com.ofss.digx.app.dda.service.core.DemandDeposit demandDepositService = new com.ofss.digx.app.dda.service.core.DemandDeposit();
	          accountActivityResponse = demandDepositService.fetchTransactions(channelContext.getSessionContext(), accountActivityRequestDTO);
	          
	          Lock lock = new ReentrantLock();
	          DataTransferObject entity = accountActivityResponse;
	          Writer writer = new StringWriter();
	          JAXBContext jaxbContext = null;
	          Marshaller jaxbMarhsaller = null;
	          String entityXml = null;
	          String entityClassName = entity.getClass().getName();          
	          jaxbContext = JAXBContext.newInstance(new Class[] { entity.getClass() });
	          jaxbMarhsaller = jaxbContext.createMarshaller();
	          jaxbMarhsaller.setProperty("jaxb.formatted.output", Boolean.valueOf(true));
	          jaxbMarhsaller.marshal(entity, writer);
	          entityXml = writer.toString();
	          String entityTransformationXsl = "resources/" + entityClassName.replaceAll("\\.", "/");
	          lock.lock();
	          FOProcessor processor = new FOProcessor();
//	          processor.setOutputFormat((byte)1);
	          processor.setOutputFormat(FOProcessor.FORMAT_PDF);
	          processor.setData(new StringReader(entityXml));
	          processor.setTemplate(entity
	            .getClass().getClassLoader().getResourceAsStream(entityTransformationXsl.concat(".xsl")));
	          ByteArrayOutputStream baos = new ByteArrayOutputStream();	          
	          processor.setOutput(baos);
	          processor.generate();
	          
	          Properties props = new Properties();
		        props.put("mail.transport.protocol", "smtp");
		        props.put("mail.smtp.host", "smtp.sendgrid.net");
		        props.put("mail.smtp.port", 587);
		        props.put("mail.smtp.auth", "true");
		        
		        Authenticator auth = new SMTPAuthenticator();
		        Session mailSession = Session.getInstance(props, auth);
		        Transport transport = mailSession.getTransport();
		 
		        MimeMessage message = new MimeMessage(mailSession);
		 
		        Multipart multipart = new MimeMultipart("alternative");
		        BodyPart part1 = new MimeBodyPart();
		        part1.setText("Dummy");
		        multipart.addBodyPart(part1);
		        MimeBodyPart attachmentPart = new MimeBodyPart();
		        InputStream targetStream = new ByteArrayInputStream(baos.toByteArray());
		        DataSource source = new InputStreamDataSource(targetStream,"test");
		        attachmentPart.setDataHandler(new DataHandler(source));
		        attachmentPart.setFileName("dummy.pdf");
		        multipart.addBodyPart(attachmentPart);

		        message.setContent(multipart);
		        message.setFrom(new InternetAddress("mansoor.naseer@techlogix.com"));
		        message.setSubject("dummy");
		        message.addRecipient(Message.RecipientType.TO, new InternetAddress("mansoor.naseer@hotmail.com"));
		 
		        transport.connect();
		        transport.sendMessage(message,
		           message.getRecipients(Message.RecipientType.TO));
		        transport.close();
	          response = buildResponse(accountActivityResponse, Response.Status.OK);
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
	      logger.log(Level.SEVERE, formatter.formatMessage("Exception encountered while invoking the read service for payment id=%s", new Object[] { accountId }), e);
	      
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
	    
	    logger.log(Level.FINE, formatter.formatMessage("Exiting from read() : paymentId=%s", new Object[] { accountId }));
	    
	    return response;
    }
  public class InputStreamDataSource implements DataSource {

      ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      private final String name;

      public InputStreamDataSource(InputStream inputStream, String name) {
          this.name = name;
          try {
              int nRead;
              byte[] data = new byte[16384];
              while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
              }
              inputStream.close();
              buffer.flush();
          } catch (IOException e) {
              e.printStackTrace();
          }

      }

      @Override
      public String getContentType() {            
          return new MimetypesFileTypeMap().getContentType(name);
      }

      @Override
      public InputStream getInputStream() throws IOException {
              return new ByteArrayInputStream(buffer.toByteArray());
      }

      @Override
      public String getName() {
         return name;
      }

      @Override
      public OutputStream getOutputStream() throws IOException {
          throw new IOException("Read-only data");
      }

  }
  	private class SMTPAuthenticator extends javax.mail.Authenticator {
      public PasswordAuthentication getPasswordAuthentication() {
         String username = "mansoor.naseer";
         String password = "Reach@5042281";
         return new PasswordAuthentication(username, password);
      }
  }
}
