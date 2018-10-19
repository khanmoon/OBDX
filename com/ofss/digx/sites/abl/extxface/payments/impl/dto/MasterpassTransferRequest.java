package com.ofss.digx.sites.abl.extxface.payments.impl.dto;

import com.ofss.digx.datatype.SenderName;
import com.ofss.digx.datatype.SenderAddress;
import com.ofss.digx.datatype.SenderIdentification;
import com.ofss.digx.datatype.FundingCard;
import com.ofss.digx.datatype.ReceiverName;
import com.ofss.digx.datatype.ReceiverAddress;
import com.ofss.digx.datatype.ReceiverIdentification;
import com.ofss.digx.datatype.ReceivingCard;
import com.ofss.digx.datatype.ReceivingAmount;
import com.ofss.digx.datatype.CardAcceptor;
import com.ofss.digx.extxface.impl.dto.ExternalSystemRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="", propOrder={"LocalDate", "LocalTime", "TransactionReference", "SenderName", "SenderPhone", "SenderDateOfBirth", "SenderAddress", "SenderIdentification", "SenderNationality", "SenderCountryOfBirth", "FundingCard", "FundingSource", "AdditionalMessage", "ParticipationID", "LanguageIdentification", "LanguageData", "ReceiverName", "ReceiverAddress", "ReceiverPhone", "ReceiverDateOfBirth", "ReceiverIdentification", "ReceiverNationality", "ReceiverCountryOfBirth", "ReceivingCard", "ReceivingAmount", "Channel", "ICA", "ProcessorId", "RoutingAndTransitNumber", "CardAcceptor", "TransactionDesc", "MerchantId", "TransactionPurpose"})
@XmlRootElement(name="PaymentRequestV3")
@XmlAccessorType(XmlAccessType.FIELD)
public class MasterpassTransferRequest
  extends ExternalSystemRequest
{
  @XmlElement(name="LocalDate", required=true)
  public String LocalDate;
  @XmlElement(name="LocalTime", required=true)
  public String LocalTime;
  @XmlElement(name="TransactionReference", required=true)
  private String TransactionReference;
  @XmlElement(name="SenderName", required=true)
  private SenderName SenderName;
  @XmlElement(name="SenderPhone", required=true)
  private String SenderPhone;
  @XmlElement(name="SenderDateOfBirth", required=true)
  private String SenderDateOfBirth;
  @XmlElement(name="SenderAddress", required=true)
  private SenderAddress SenderAddress;
  @XmlElement(name="SenderIdentification", required=true)
  private SenderIdentification SenderIdentification;
  @XmlElement(name="SenderNationality", required=true)
  private String SenderNationality;
  @XmlElement(name="SenderCountryOfBirth", required=true)
  private String SenderCountryOfBirth;
  @XmlElement(name="FundingCard", required=true)
  private FundingCard FundingCard;
  @XmlElement(name="FundingSource", required=true)
  private String FundingSource;
  @XmlElement(name="AdditionalMessage", required=true)
  private String AdditionalMessage;
  @XmlElement(name="ParticipationID", required=true)
  private String ParticipationID;
  @XmlElement(name="LanguageIdentification", required=true)
  private String LanguageIdentification;
  @XmlElement(name="LanguageData", required=true)
  private String LanguageData;
  @XmlElement(name="ReceiverName", required=true)
  private ReceiverName ReceiverName;
  @XmlElement(name="ReceiverAddress", required=true)
  private ReceiverAddress ReceiverAddress;
  @XmlElement(name="ReceiverPhone", required=true)
  private String ReceiverPhone;
  @XmlElement(name="ReceiverDateOfBirth", required=true)
  private String ReceiverDateOfBirth;
  @XmlElement(name="ReceiverIdentification", required=true)
  private ReceiverIdentification ReceiverIdentification;
  @XmlElement(name="ReceiverNationality", required=true)
  private String ReceiverNationality;
  @XmlElement(name="ReceiverCountryOfBirth", required=true)
  private String ReceiverCountryOfBirth;
  @XmlElement(name="ReceivingCard", required=true)
  private ReceivingCard ReceivingCard;
  @XmlElement(name="ReceivingAmount", required=true)
  private ReceivingAmount ReceivingAmount;
  @XmlElement(name="Channel", required=true)
  private String Channel;
  @XmlElement(name="ICA", required=true)
  private String ICA;
  @XmlElement(name="ProcessorId", required=true)
  private String ProcessorId;
  @XmlElement(name="RoutingAndTransitNumber", required=true)
  private String RoutingAndTransitNumber;
  @XmlElement(name="CardAcceptor", required=true)
  private CardAcceptor CardAcceptor;
  @XmlElement(name="TransactionDesc", required=true)
  private String TransactionDesc;
  @XmlElement(name="MerchantId", required=true)
  private String MerchantId;
  @XmlElement(name="TransactionPurpose", required=true)
  private String TransactionPurpose;
  
  public MasterpassTransferRequest(String interfaceId)
  {
    super(interfaceId);
  }
  
  public MasterpassTransferRequest() {}

	public String getLocalDate() {
		return LocalDate;
	}
	
	public void setLocalDate(String localDate) {
		LocalDate = localDate;
	}
	
	public String getLocalTime() {
		return LocalTime;
	}
	
	public void setLocalTime(String localTime) {
		LocalTime = localTime;
	}
	
	public String getTransactionReference() {
		return TransactionReference;
	}
	
	public void setTransactionReference(String transactionReference) {
		TransactionReference = transactionReference;
	}
	
	public SenderName getSenderName() {
		return SenderName;
	}
	
	public void setSenderName(SenderName senderName) {
		SenderName = senderName;
	}
	
	public String getSenderPhone() {
		return SenderPhone;
	}
	
	public void setSenderPhone(String senderPhone) {
		SenderPhone = senderPhone;
	}
	
	public String getSenderDateOfBirth() {
		return SenderDateOfBirth;
	}
	
	public void setSenderDateOfBirth(String senderDateOfBirth) {
		SenderDateOfBirth = senderDateOfBirth;
	}
	
	public SenderAddress getSenderAddress() {
		return SenderAddress;
	}
	
	public void setSenderAddress(SenderAddress senderAddress) {
		SenderAddress = senderAddress;
	}
	
	public SenderIdentification getSenderIdentification() {
		return SenderIdentification;
	}
	
	public void setSenderIdentification(SenderIdentification senderIdentification) {
		SenderIdentification = senderIdentification;
	}
	
	public String getSenderNationality() {
		return SenderNationality;
	}
	
	public void setSenderNationality(String senderNationality) {
		SenderNationality = senderNationality;
	}
	
	public String getSenderCountryOfBirth() {
		return SenderCountryOfBirth;
	}
	
	public void setSenderCountryOfBirth(String senderCountryOfBirth) {
		SenderCountryOfBirth = senderCountryOfBirth;
	}
	
	public FundingCard getFundingCard() {
		return FundingCard;
	}
	
	public void setFundingCard(FundingCard fundingCard) {
		FundingCard = fundingCard;
	}
	
	public String getFundingSource() {
		return FundingSource;
	}
	
	public void setFundingSource(String fundingSource) {
		FundingSource = fundingSource;
	}
	
	public String getAdditionalMessage() {
		return AdditionalMessage;
	}
	
	public void setAdditionalMessage(String additionalMessage) {
		AdditionalMessage = additionalMessage;
	}
	
	public String getParticipationID() {
		return ParticipationID;
	}
	
	public void setParticipationID(String participationID) {
		ParticipationID = participationID;
	}
	
	public String getLanguageIdentification() {
		return LanguageIdentification;
	}
	
	public void setLanguageIdentification(String languageIdentification) {
		LanguageIdentification = languageIdentification;
	}
	
	public String getLanguageData() {
		return LanguageData;
	}
	
	public void setLanguageData(String languageData) {
		LanguageData = languageData;
	}
	
	public ReceiverName getReceiverName() {
		return ReceiverName;
	}
	
	public void setReceiverName(ReceiverName receiverName) {
		ReceiverName = receiverName;
	}
	
	public ReceiverAddress getReceiverAddress() {
		return ReceiverAddress;
	}
	
	public void setReceiverAddress(ReceiverAddress receiverAddress) {
		ReceiverAddress = receiverAddress;
	}
	
	public String getReceiverPhone() {
		return ReceiverPhone;
	}
	
	public void setReceiverPhone(String receiverPhone) {
		ReceiverPhone = receiverPhone;
	}
	
	public String getReceiverDateOfBirth() {
		return ReceiverDateOfBirth;
	}
	
	public void setReceiverDateOfBirth(String receiverDateOfBirth) {
		ReceiverDateOfBirth = receiverDateOfBirth;
	}
	
	public ReceiverIdentification getReceiverIdentification() {
		return ReceiverIdentification;
	}
	
	public void setReceiverIdentification(ReceiverIdentification receiverIdentification) {
		ReceiverIdentification = receiverIdentification;
	}
	
	public String getReceiverNationality() {
		return ReceiverNationality;
	}
	
	public void setReceiverNationality(String receiverNationality) {
		ReceiverNationality = receiverNationality;
	}
	
	public String getReceiverCountryOfBirth() {
		return ReceiverCountryOfBirth;
	}
	
	public void setReceiverCountryOfBirth(String receiverCountryOfBirth) {
		ReceiverCountryOfBirth = receiverCountryOfBirth;
	}
	
	public ReceivingCard getReceivingCard() {
		return ReceivingCard;
	}
	
	public void setReceivingCard(ReceivingCard receivingCard) {
		ReceivingCard = receivingCard;
	}
	
	public ReceivingAmount getReceivingAmount() {
		return ReceivingAmount;
	}
	
	public void setReceivingAmount(ReceivingAmount receivingAmount) {
		ReceivingAmount = receivingAmount;
	}
	
	public String getChannel() {
		return Channel;
	}
	
	public void setChannel(String channel) {
		Channel = channel;
	}
	
	public String getICA() {
		return ICA;
	}
	
	public void setICA(String iCA) {
		ICA = iCA;
	}
	
	public String getProcessorId() {
		return ProcessorId;
	}
	
	public void setProcessorId(String processorId) {
		ProcessorId = processorId;
	}
	
	public String getRoutingAndTransitNumber() {
		return RoutingAndTransitNumber;
	}
	
	public void setRoutingAndTransitNumber(String routingAndTransitNumber) {
		RoutingAndTransitNumber = routingAndTransitNumber;
	}
	
	public CardAcceptor getCardAcceptor() {
		return CardAcceptor;
	}
	
	public void setCardAcceptor(CardAcceptor cardAcceptor) {
		CardAcceptor = cardAcceptor;
	}
	
	public String getTransactionDesc() {
		return TransactionDesc;
	}
	
	public void setTransactionDesc(String transactionDesc) {
		TransactionDesc = transactionDesc;
	}
	
	public String getMerchantId() {
		return MerchantId;
	}
	
	public void setMerchantId(String merchantId) {
		MerchantId = merchantId;
	}
	
	public String getTransactionPurpose() {
		return TransactionPurpose;
	}
	
	public void setTransactionPurpose(String transactionPurpose) {
		TransactionPurpose = transactionPurpose;
	}
}
