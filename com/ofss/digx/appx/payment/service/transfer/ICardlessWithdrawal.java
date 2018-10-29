package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.datatype.complex.Account;
import com.ofss.fc.datatype.Date;
import com.ofss.digx.enumeration.accounts.TransactionType;
import com.ofss.digx.enumeration.accounts.statement.SearchByType;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.CardlessWithdrawalDTO;
import javax.ws.rs.core.Response;

public abstract interface ICardlessWithdrawal
{
  public abstract Response read(String paramString);
  
  public abstract Response create(CardlessWithdrawalDTO paramMerchantTransferDTO);
  
  public abstract Response updateStatus(String paramString);
  
  public abstract Response delete(String paramString);
  
  public abstract Response fetchTransactions(Account paramAccount, Date paramDate1, Date paramDate2, Integer paramInteger, TransactionType paramTransactionType, SearchByType paramSearchByType, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract CardlessWithdrawalAuthentication cardlessWithdrawalAuthentication(String paramString);
}
