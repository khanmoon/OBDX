package com.ofss.digx.appx.payment.service.transfer;

public abstract interface ITransfer
{
  public abstract SelfTransfer selfTransfer();
  
  public abstract InternalTransfer internalTransfer();
  
  public abstract PeerToPeerTransfer PeerToPeerTransfer();
  
  public abstract ExternalTransfer externalTransfer();
  
  public abstract MerchantTransferData merchantTransferData();
  
  public abstract CreditCardPayment creditCardPayment();
  
  public abstract BillPayment billPayment();
  
  public abstract PayAnyoneTransfer payAnyoneTransfer();
  
  public abstract DonationTransfer donationTransfer();
  
  public abstract MerchantTransfer merchantTransfer();
  
  //public abstract OTPTransfer oTPTransfer();
}
