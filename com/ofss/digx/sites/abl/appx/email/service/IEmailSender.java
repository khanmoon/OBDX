package com.ofss.digx.sites.abl.appx.email.service;

import com.ofss.digx.datatype.complex.Account;
import com.ofss.digx.enumeration.accounts.TransactionType;
import com.ofss.digx.enumeration.accounts.statement.SearchByType;
import com.ofss.fc.datatype.Date;

import javax.ws.rs.core.Response;

public interface IEmailSender {
	  public abstract Response sendPdf(Account paramAccount, Date paramDate1, Date paramDate2, Integer paramInteger, TransactionType paramTransactionType, SearchByType paramSearchByType, String paramString1, String paramString2, String paramString3, String paramString4);

}
