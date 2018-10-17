package com.ofss.digx.appx.party.service;

import com.ofss.digx.app.context.ChannelContext;
import com.ofss.digx.app.core.ChannelInteraction;
import com.ofss.digx.app.party.dto.AddressListResponse;
import com.ofss.digx.app.party.dto.ContactDetailsRequestDTO;
import com.ofss.digx.app.party.dto.ContactListResponse;
import com.ofss.digx.app.party.dto.ContactRequestDTO;
import com.ofss.digx.app.party.dto.ListPartiesRequestDTO;
import com.ofss.digx.app.party.dto.PartyAddressDTO;
import com.ofss.digx.app.party.dto.PartyAddressDetailsRequestDTO;
import com.ofss.digx.app.party.dto.PartyAddressRequestDTO;
import com.ofss.digx.app.party.dto.PartyCreateAddressDetailsRequestDTO;
import com.ofss.digx.app.party.dto.PartyInquiryRequestDTO;
import com.ofss.digx.app.party.dto.PartyListResponse;
import com.ofss.digx.app.party.dto.PartyPersonalInformationRequestDTO;
import com.ofss.digx.app.party.dto.PartyRelationshipDTO;
import com.ofss.digx.app.party.dto.PartyResponse;
import com.ofss.digx.app.party.dto.PartySearchRequestDTO;
import com.ofss.digx.appx.AbstractRESTApplication;
import com.ofss.digx.appx.PATCH;
import com.ofss.digx.appx.party.service.linelimit.LineLimit;
import com.ofss.digx.appx.party.service.profile.PartyPreferences;
import com.ofss.digx.appx.party.service.relation.PartyToPartyRelationship;
import com.ofss.digx.appx.party.service.relation.account.PartyToAccountRelationship;
import com.ofss.digx.enumeration.AddressType;
import com.ofss.digx.enumeration.party.PartyType;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MerchantTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.infra.log.impl.MultiEntityLogger;
import com.ofss.fc.infra.text.mask.ITextMask;
import com.ofss.fc.infra.text.mask.MaskingFactory;
import com.ofss.fc.service.response.TransactionStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/parties")
public class Party
  extends AbstractRESTApplication
  implements IParty
{
  private static final String THIS_COMPONENT_NAME = Party.class.getName();
  private MultiEntityLogger formatter = MultiEntityLogger.getUniqueInstance();
  private static transient Logger logger = MultiEntityLogger.getUniqueInstance().getLogger(THIS_COMPONENT_NAME);
  private String extRefId;
  
  public String getExtRefId()
  {
    return this.extRefId;
  }
  
  public void setExtRefId(String extRefId)
  {
    this.extRefId = extRefId;
  }
  
  public Party()
  {
    super(new Class[] { com.ofss.digx.datatype.complex.Party.class });
  }
  
  @GET
  @Produces({"application/json"})
  public Response searchParty(@QueryParam("email") String email, @QueryParam("partyId") String partyId, @QueryParam("partyType") PartyType partyType, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("middleName") String middleName, @QueryParam("fullName") String fullName, @QueryParam("birthDate") Date birthDate)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter.formatMessage("Entered into searchParty() : email=%s", new Object[] { email }));
    }
    Response response = null;
    PartyListResponse partyListResponse = null;
    PartyResponse partyResponse = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    com.ofss.digx.app.party.service.core.Party party = new com.ofss.digx.app.party.service.core.Party();
    try
    {
      channelContext = getChannelContext();
      channelInteraction.begin(channelContext);
      if ((email != null) && (!email.isEmpty()))
      {
        PartySearchRequestDTO partySearchRequestDTO = new PartySearchRequestDTO();
        partySearchRequestDTO.setEmailId(email);
        partyResponse = party.findPartyWithEmailId(channelContext.getSessionContext(), partySearchRequestDTO);
        partyListResponse = new PartyListResponse();
        partyListResponse.setStatus(partyResponse.getStatus());
        partyListResponse.setParties(new ArrayList());
        partyListResponse.getParties().add(partyResponse.getParty());
      }
      else
      {
        ListPartiesRequestDTO listPartiesRequestDTO = new ListPartiesRequestDTO();
        listPartiesRequestDTO.setPartyId(partyId);
        listPartiesRequestDTO.setPartyName(fullName);
        listPartiesRequestDTO.setPartyType(partyType);
        listPartiesRequestDTO.setFirstName(firstName);
        listPartiesRequestDTO.setMiddleName(middleName);
        listPartiesRequestDTO.setLastName(lastName);
        listPartiesRequestDTO.setBirthDate(birthDate);
        partyListResponse = party.searchParties(channelContext.getSessionContext(), listPartiesRequestDTO);
      }
      response = buildResponse(partyListResponse);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter
        .formatMessage("FatalException encountered while invoking the service %s for email=%s ", new Object[] {Party.class
        .getName(), email }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter
      .formatMessage("Exiting from searchParty() : partyResponse=%s", new Object[] { partyResponse }));
    label500:
    return response;
  }
  
  @POST
  @Consumes({"application/json"})
  @Produces({"application/json"})
  @Path("{submissionId}/relationship")
  public Response addPartyRelationship(@PathParam("submissionId") String submissionId, PartyRelationshipDTO partyRelationshipDTO)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter.formatMessage("Entered into addPartyRelationship() : Party", new Object[0]));
    }
    ChannelContext channelContext = null;
    Response response = null;
    partyRelationshipDTO.setExtRefId(submissionId);
    com.ofss.digx.app.party.service.core.Party party = new com.ofss.digx.app.party.service.core.Party();
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    TransactionStatus transactionStatus = null;
    try
    {
      channelContext = super.getChannelContext();
      channelInteraction.begin(channelContext);
      transactionStatus = party.addPartyRelationship(channelContext.getSessionContext(), partyRelationshipDTO);
      response = buildResponse(transactionStatus);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
    	  return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter
        .formatMessage("FatalException encountered while invoking the service %s for submissionId=%s", new Object[] {Party.class
        
        .getName(), submissionId }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter
      .formatMessage("Exiting from addPartyRelationship() : TransactionStatus:%s ", new Object[] { transactionStatus }));
    label341:
    return response;
  }
  
  @Path("{partyId}/accounts")
  public PartyToAccountRelationship account(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, "Invoking sub-resource Account for processing actions on party's account details.");
    }
    PartyToAccountRelationship accountSubResource = null;
    accountSubResource = new PartyToAccountRelationship(partyId);
    accountSubResource.setHttpResponse(getHttpResponse());
    accountSubResource.setHttpRequest(getHttpRequest());
    accountSubResource.setUriInfo(getUriInfo());
    accountSubResource.setSecurityContext(getSecurityContext());
    return accountSubResource;
  }
  
  @Path("{partyId}/relations")
  public PartyToPartyRelationship partyToPartyRelationship(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId)
  {
    PartyToPartyRelationship partyToPartyRelationshipSubResource = new PartyToPartyRelationship(partyId);
    partyToPartyRelationshipSubResource.setHttpResponse(getHttpResponse());
    partyToPartyRelationshipSubResource.setHttpRequest(getHttpRequest());
    partyToPartyRelationshipSubResource.setUriInfo(getUriInfo());
    partyToPartyRelationshipSubResource.setSecurityContext(getSecurityContext());
    return partyToPartyRelationshipSubResource;
  }
  
  @GET
  @Produces({"application/json"})
  @Path("{partyId}")
  public Response readPersonalInformation(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId, @QueryParam("expand") String expandAttribute)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter
        .formatMessage("Entered into readPersonalInformation() : partyId=%s, extRefId=%s", new Object[] { partyId, 
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }));
    }
    PartyResponse partyResponse = null;
    PartyPersonalInformationRequestDTO partyPersonalInformationRequestDTO = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    Response response = null;
    try
    {
      channelContext = getChannelContext();
      channelInteraction.begin(channelContext);
      partyPersonalInformationRequestDTO = new PartyPersonalInformationRequestDTO();
      partyPersonalInformationRequestDTO.setPartyId(partyId);
      partyPersonalInformationRequestDTO.setExtRefId(getExtRefId());
      
      partyResponse = new com.ofss.digx.app.party.service.core.Party(expandAttribute).readPersonalInformation(channelContext.getSessionContext(), partyPersonalInformationRequestDTO);
      response = buildResponse(partyResponse, Response.Status.OK);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
    	  return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter
        .formatMessage("Exception encountered while invoking the core service readPersonalInformation for partyId=%s, extRefId=%s", new Object[] { partyId, 
        
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from readPersonalInformation() : partyResponse=%s", new Object[] { partyResponse }));
    label404:
    return response;
  }
  
  @GET
  @Path("{partyId}/contactPoints")
  @Produces({"application/json"})
  public Response listContact(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter
        .formatMessage("Entered into listContact() : partyId=%s, extRefId=%s", new Object[] { partyId, 
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }));
    }
    ContactListResponse contactListResponse = null;
    Response response = null;
    PartyInquiryRequestDTO partyInquiryRequestDTO = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    try
    {
      channelContext = getChannelContext();
      channelInteraction.begin(channelContext);
      partyInquiryRequestDTO = new PartyInquiryRequestDTO();
      partyInquiryRequestDTO.setPartyId(partyId);
      partyInquiryRequestDTO.setExtRefId(getExtRefId());
      
      contactListResponse = new com.ofss.digx.app.party.service.core.Party().listContact(channelContext.getSessionContext(), partyInquiryRequestDTO);
      response = buildResponse(contactListResponse, Response.Status.OK);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter.formatMessage("Exception encountered while invoking the core service %s for partyId=%s, extRefId=%s", new Object[] {Party.class
      
        .getName(), partyId, MaskingFactory.getInstance()
        .mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from listContact() : contactListResponse=%s", new Object[] { contactListResponse }));
    
    return response;
  }
  
  @GET
  @Path("{partyId}/addresses")
  @Produces({"application/json"})
  public Response listAddress(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId, @QueryParam("type") List<AddressType> addressTypesFilter)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter
        .formatMessage("Entered into listAddress() : partyId=%s, extRefId=%s", new Object[] { partyId, 
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }));
    }
    AddressListResponse addressListResponse = null;
    Response response = null;
    PartyAddressRequestDTO partyAddressRequestDTO = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    try
    {
      channelContext = getChannelContext();
      channelInteraction.begin(channelContext);
      partyAddressRequestDTO = new PartyAddressRequestDTO();
      partyAddressRequestDTO.setPartyId(partyId);
      partyAddressRequestDTO.setExtRefId(getExtRefId());
      partyAddressRequestDTO.setAddressTypesFilter(addressTypesFilter);
      
      addressListResponse = new com.ofss.digx.app.party.service.core.Party().listAddress(channelContext.getSessionContext(), partyAddressRequestDTO);
      response = buildResponse(addressListResponse, Response.Status.OK);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
    	  return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter.formatMessage("Exception encountered while invoking the core service %s for partyId=%s, extRefId=%s", new Object[] {Party.class
      
        .getName(), partyId, MaskingFactory.getInstance()
        .mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern") }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from listAddress() : addressListResponse=%s", new Object[] { addressListResponse }));
    label417:
    return response;
  }
  
  @POST
  @Path("{partyId}/address")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response createAddress(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId, PartyAddressDTO partyAddressDTO)
  {
    Response response = null;
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter
        .formatMessage("Entered into createAddress() : extRefId=%s,partyId=%s,partyAddressDTO=%s", new Object[] {
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyAddressDTO }));
    }
    if ((getExtRefId() == null) || (getExtRefId().trim().length() == 0)) {
      return buildMethodNotAllowResponse();
    }
    AddressListResponse addressListResponse = null;
    PartyAddressDetailsRequestDTO partyAddressDetailsRequestDTO = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    try
    {
      channelContext = getChannelContext();
      channelContext.getSessionContext().setTransactingPartyCode(partyId.getValue());
      channelInteraction.begin(channelContext);
      partyAddressDetailsRequestDTO = new PartyAddressDetailsRequestDTO();
      partyAddressDetailsRequestDTO.setExtRefId(getExtRefId());
      partyAddressDetailsRequestDTO.setPartyId(partyId);
      partyAddressDetailsRequestDTO.setPartyAddress(partyAddressDTO);
      
      addressListResponse = new com.ofss.digx.app.party.service.core.Party().createAddress(channelContext.getSessionContext(), partyAddressDetailsRequestDTO);
      response = buildResponse(addressListResponse, Response.Status.CREATED);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
        return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter
        .formatMessage("Exception encountered while invoking the core service %s for extRefId=%s,partyId=%s,partyAddressDTO=%s", new Object[] {Party.class
        
        .getName(), MaskingFactory.getInstance()
        .mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyAddressDTO }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from createAddress() : addressListResponse=%s", new Object[] { addressListResponse }));
    
    return response;
  }
  
  @PATCH
  @Path("{partyId}/address")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response updateAddress(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId, PartyAddressDTO[] partyAddressDTO)
  {
	  Response response = null;
	    if (logger.isLoggable(Level.FINE)) {
	      logger.log(Level.FINE, this.formatter
	        .formatMessage("Entered into createAddress() : extRefId=%s,partyId=%s,partyAddressDTO=%s", new Object[] {
	        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyAddressDTO }));
	    }
	    if ((getExtRefId() == null) || (getExtRefId().trim().length() == 0)) {
	      return buildMethodNotAllowResponse();
	    }
	    AddressListResponse addressListResponse = null;
	    PartyCreateAddressDetailsRequestDTO partyAddressDetailsRequestDTO = null;
	    ChannelContext channelContext = null;
	    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
	    try
	    {
	      channelContext = getChannelContext();
	      channelContext.getSessionContext().setTransactingPartyCode(partyId.getValue());
	      channelInteraction.begin(channelContext);
	      partyAddressDetailsRequestDTO = new PartyCreateAddressDetailsRequestDTO();
	      partyAddressDetailsRequestDTO.setExtRefId(getExtRefId());
	      partyAddressDetailsRequestDTO.setPartyId(partyId);
	      partyAddressDetailsRequestDTO.setPartyAddresses(partyAddressDTO);
	      
	      addressListResponse = new com.ofss.digx.app.party.service.core.Party().updateAddress(channelContext.getSessionContext(), partyAddressDetailsRequestDTO);
	      response = buildResponse(addressListResponse, Response.Status.OK);
	      try
	      {
	        channelInteraction.close(channelContext);
	      }
	      catch (Exception e)
	      {
	        logger.log(Level.SEVERE, this.formatter
	          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
	        
	        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
	      }
	      if (!logger.isLoggable(Level.FINE)) {
	        return response;
	      }
	    }
	    catch (Exception e)
	    {
	      logger.log(Level.SEVERE, this.formatter
	        .formatMessage("Exception encountered while invoking the core service %s for extRefId=%s,partyId=%s,partyAddressDTO=%s", new Object[] {Party.class
	        
	        .getName(), MaskingFactory.getInstance()
	        .mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyAddressDTO }), e);
	      
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
	        logger.log(Level.SEVERE, this.formatter
	          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
	        
	        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
	      }
	    }
	    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from createAddress() : addressListResponse=%s", new Object[] { addressListResponse }));
	    
	    return response;
  }
  
  @POST
  @Path("{partyId}/contactPoints")
  @Consumes({"application/json"})
  @Produces({"application/json"})
  public Response createContact(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId, ContactRequestDTO partyContactDTO)
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.log(Level.FINE, this.formatter
        .formatMessage("Entered into createContact() : extRefId=%s, partyId=%s, partyContactDTO=%s", new Object[] {
        
        MaskingFactory.getInstance().mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyContactDTO }));
    }
    if ((getExtRefId() == null) || (getExtRefId().trim().length() == 0)) {
      return buildMethodNotAllowResponse();
    }
    ContactListResponse contactListResponse = null;
    Response response = null;
    ContactDetailsRequestDTO partyContactDetailsRequestDTO = null;
    ChannelContext channelContext = null;
    ChannelInteraction channelInteraction = ChannelInteraction.getInstance();
    try
    {
      channelContext = getChannelContext();
      channelContext.getSessionContext().setTransactingPartyCode(partyId.getValue());
      channelInteraction.begin(channelContext);
      partyContactDetailsRequestDTO = new ContactDetailsRequestDTO();
      partyContactDetailsRequestDTO.setPartyId(partyId);
      partyContactDetailsRequestDTO.setExtRefId(getExtRefId());
      partyContactDetailsRequestDTO.setPartyContacts(partyContactDTO.getContactDTOs());
      
      contactListResponse = new com.ofss.digx.app.party.service.core.Party().createContact(channelContext.getSessionContext(), partyContactDetailsRequestDTO);
      response = buildResponse(contactListResponse, Response.Status.CREATED);
      try
      {
        channelInteraction.close(channelContext);
      }
      catch (Exception e)
      {
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
      if (!logger.isLoggable(Level.FINE)) {
    	  return response;
      }
    }
    catch (Exception e)
    {
      logger.log(Level.SEVERE, this.formatter
        .formatMessage("Exception encountered while invoking the core service %s for extRefId=%s, partyId=%s, partyContactDTO=%s", new Object[] {Party.class
        
        .getName(), MaskingFactory.getInstance()
        .mask(getExtRefId(), "SubmissionIdMaskingPattern", "submissionIdMaskingPattern"), partyId, partyContactDTO }), e);
      
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
        logger.log(Level.SEVERE, this.formatter
          .formatMessage("Error encountered while closing channelContext %s", new Object[] { channelContext }), e);
        
        response = buildResponse(e, Response.Status.INTERNAL_SERVER_ERROR);
      }
    }
    logger.log(Level.FINE, this.formatter.formatMessage("Exiting from createContact() : contactListResponse=%s", new Object[] { contactListResponse }));
    label465:
    return response;
  }
  
  @Path("{partyId}/preferences")
  public PartyPreferences partyPreferences(@PathParam("partyId") com.ofss.digx.datatype.complex.Party partyId)
  {
    PartyPreferences partyPreferencesSubResource = new PartyPreferences(partyId);
    partyPreferencesSubResource.setHttpResponse(getHttpResponse());
    partyPreferencesSubResource.setHttpRequest(getHttpRequest());
    partyPreferencesSubResource.setUriInfo(getUriInfo());
    partyPreferencesSubResource.setSecurityContext(getSecurityContext());
    return partyPreferencesSubResource;
  }
  
  @Path("/lineLimit")
  public LineLimit lineLimits()
  {
    LineLimit lineLimitSubResource = new LineLimit();
    lineLimitSubResource.setHttpResponse(getHttpResponse());
    lineLimitSubResource.setHttpRequest(getHttpRequest());
    lineLimitSubResource.setUriInfo(getUriInfo());
    lineLimitSubResource.setSecurityContext(getSecurityContext());
    return lineLimitSubResource;
  }
}
