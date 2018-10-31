package com.ofss.digx.sites.abl.app.email.service;

import java.io.ByteArrayOutputStream;
import java.util.Locale;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import com.ofss.digx.app.AbstractApplication;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityRequestDTO;
import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityResponseDTO;
import com.ofss.digx.appx.writer.content.PDFContentPublisher;
import com.ofss.digx.sites.abl.app.email.dto.EmailSenderRequestDTO;
import com.ofss.digx.sites.abl.app.email.dto.EmailSenderResponseDTO;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;
import com.ofss.digx.infra.exceptions.Exception;

public class EmailSender 
extends AbstractApplication
implements IEmailSender{
	public EmailSenderResponseDTO sendPdf(SessionContext sessionContext,EmailSenderRequestDTO emailSenderRequestDTO) throws Exception
		  {
		EmailSenderResponseDTO emailSenderResponse = null;
		DemandDepositAccountActivityResponseDTO accountActivityResponse = null;
		DemandDepositAccountActivityRequestDTO accountActivityRequestDTO = emailSenderRequestDTO.getAccountActivityDetails();
		com.ofss.digx.app.dda.service.core.DemandDeposit demandDepositService = new com.ofss.digx.app.dda.service.core.DemandDeposit();
        accountActivityResponse = demandDepositService.fetchTransactions(sessionContext, accountActivityRequestDTO);
        PDFContentPublisher pc = new PDFContentPublisher();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();              
        pc.publish((DataTransferObject)accountActivityResponse,new MediaType(), new MultivaluedHashMap<String, Object>(),baos,new Locale("en"),"pdf");
        EmailHelper emailHelper = new EmailHelper();
        emailHelper.to("mansoor.naseer@hotmail.com");
        emailHelper.from("dummy@sad.com");
        emailHelper.setBaos(baos);
        emailHelper.send();
        
		return emailSenderResponse;
		  }
}