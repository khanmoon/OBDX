package com.ofss.digx.appx.payment.service.transfer;

import com.ofss.digx.appx.AbstractRESTApplication;
import javax.ws.rs.Path;

public class Transfer
  extends AbstractRESTApplication
  implements ITransfer
{
  @Path("/self")
  public SelfTransfer selfTransfer()
  {
    SelfTransfer selfTransfer = new SelfTransfer();
    selfTransfer.setHttpResponse(getHttpResponse());
    selfTransfer.setHttpRequest(getHttpRequest());
    selfTransfer.setUriInfo(getUriInfo());
    selfTransfer.setSecurityContext(getSecurityContext());
    return selfTransfer;
  }
  
  @Path("/internal")
  public InternalTransfer internalTransfer()
  {
    InternalTransfer internalTransfer = new InternalTransfer();
    internalTransfer.setHttpResponse(getHttpResponse());
    internalTransfer.setHttpRequest(getHttpRequest());
    internalTransfer.setUriInfo(getUriInfo());
    internalTransfer.setSecurityContext(getSecurityContext());
    return internalTransfer;
  }
  
  @Path("/peerToPeer")
  public PeerToPeerTransfer PeerToPeerTransfer()
  {
    PeerToPeerTransfer PeerToPeerTransfer = new PeerToPeerTransfer();
    PeerToPeerTransfer.setHttpResponse(getHttpResponse());
    PeerToPeerTransfer.setHttpRequest(getHttpRequest());
    PeerToPeerTransfer.setUriInfo(getUriInfo());
    PeerToPeerTransfer.setSecurityContext(getSecurityContext());
    return PeerToPeerTransfer;
  }
  
  @Path("/external")
  public ExternalTransfer externalTransfer()
  {
    ExternalTransfer externalTransfer = new ExternalTransfer();
    externalTransfer.setHttpResponse(getHttpResponse());
    externalTransfer.setHttpRequest(getHttpRequest());
    externalTransfer.setUriInfo(getUriInfo());
    externalTransfer.setSecurityContext(getSecurityContext());
    return externalTransfer;
  }
  
  @Path("/merchantTransferData")
  public MerchantTransferData merchantTransferData()
  {
    MerchantTransferData merchantTransferData = new MerchantTransferData();
    merchantTransferData.setHttpResponse(getHttpResponse());
    merchantTransferData.setHttpRequest(getHttpRequest());
    merchantTransferData.setUriInfo(getUriInfo());
    merchantTransferData.setSecurityContext(getSecurityContext());
    return merchantTransferData;
  }
  
  @Path("/creditCard")
  public CreditCardPayment creditCardPayment()
  {
    CreditCardPayment creditCardPayment = new CreditCardPayment();
    creditCardPayment.setHttpResponse(getHttpResponse());
    creditCardPayment.setHttpRequest(getHttpRequest());
    creditCardPayment.setUriInfo(getUriInfo());
    creditCardPayment.setSecurityContext(getSecurityContext());
    return creditCardPayment;
  }
  
  @Path("/bill")
  public BillPayment billPayment()
  {
    BillPayment billPayment = new BillPayment();
    billPayment.setHttpResponse(getHttpResponse());
    billPayment.setHttpRequest(getHttpRequest());
    billPayment.setUriInfo(getUriInfo());
    billPayment.setSecurityContext(getSecurityContext());
    return billPayment;
  }
  
  @Path("/donation")
  public DonationTransfer donationTransfer()
  {
    DonationTransfer donationTransfer = new DonationTransfer();
    donationTransfer.setHttpResponse(getHttpResponse());
    donationTransfer.setHttpRequest(getHttpRequest());
    donationTransfer.setUriInfo(getUriInfo());
    donationTransfer.setSecurityContext(getSecurityContext());
    return donationTransfer;
  }
  
  @Path("/payanyone")
  public PayAnyoneTransfer payAnyoneTransfer()
  {
    PayAnyoneTransfer payAnyoneTransfer = new PayAnyoneTransfer();
    payAnyoneTransfer.setHttpResponse(getHttpResponse());
    payAnyoneTransfer.setHttpRequest(getHttpRequest());
    payAnyoneTransfer.setUriInfo(getUriInfo());
    payAnyoneTransfer.setSecurityContext(getSecurityContext());
    return payAnyoneTransfer;
  }
  
  @Path("/merchant")
  public MerchantTransfer merchantTransfer()
  {
    MerchantTransfer merchantTransfer = new MerchantTransfer();
    merchantTransfer.setHttpResponse(getHttpResponse());
    merchantTransfer.setHttpRequest(getHttpRequest());
    merchantTransfer.setUriInfo(getUriInfo());
    merchantTransfer.setSecurityContext(getSecurityContext());
    return merchantTransfer;
  }
  
  @Path("/masterpass")
  public MasterPassTransfer masterpassTransfer()
  {
    MasterPassTransfer masterpassTransfer = new MasterPassTransfer();
    masterpassTransfer.setHttpResponse(getHttpResponse());
    masterpassTransfer.setHttpRequest(getHttpRequest());
    masterpassTransfer.setUriInfo(getUriInfo());
    masterpassTransfer.setSecurityContext(getSecurityContext());
    return masterpassTransfer;
  }
  
  @Path("/cardless")
  public CardlessWithdrawal cardlessWithdrawal()
  {
	CardlessWithdrawal cardlessWithdrawal = new CardlessWithdrawal();
	cardlessWithdrawal.setHttpResponse(getHttpResponse());
	cardlessWithdrawal.setHttpRequest(getHttpRequest());
	cardlessWithdrawal.setUriInfo(getUriInfo());
	cardlessWithdrawal.setSecurityContext(getSecurityContext());
    return cardlessWithdrawal;
  }


  @Path("/zakatdonation")
  public ZakatDonation ZakatDonationTransfer()
  {
	  ZakatDonation zakatDonation = new ZakatDonation();
	  zakatDonation.setHttpResponse(getHttpResponse());
	  zakatDonation.setHttpRequest(getHttpRequest());
	  zakatDonation.setUriInfo(getUriInfo());
	  zakatDonation.setSecurityContext(getSecurityContext());
    return zakatDonation;
  }
  

  /*
  @Path("/otp")
  public OTPTransfer oTPTransfer()
  {
    OTPTransfer oTPTransfer = new OTPTransfer();
    oTPTransfer.setHttpResponse(getHttpResponse());
    oTPTransfer.setHttpRequest(getHttpRequest());
    oTPTransfer.setUriInfo(getUriInfo());
    oTPTransfer.setSecurityContext(getSecurityContext());
    return oTPTransfer;
  }
*/

}
