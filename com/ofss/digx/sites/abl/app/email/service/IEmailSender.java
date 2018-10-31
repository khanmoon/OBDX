package com.ofss.digx.sites.abl.app.email.service;

import com.ofss.digx.sites.abl.app.email.dto.EmailSenderRequestDTO;
import com.ofss.digx.sites.abl.app.email.dto.EmailSenderResponseDTO;
import com.ofss.fc.app.context.SessionContext; 

public interface IEmailSender {
	public EmailSenderResponseDTO sendPdf(SessionContext sessionContext,EmailSenderRequestDTO emailSenderRequestDTO) throws Exception;
}
