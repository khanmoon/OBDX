package com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.adapter;

import java.util.List;

import com.ofss.digx.app.adapter.AdapterFactoryConfigurator;
import com.ofss.digx.app.adapter.IAdapterFactory;
import com.ofss.digx.domain.payment.entity.PaymentKey;
import com.ofss.digx.enumeration.payment.PaymentStatusType;
import com.ofss.digx.framework.domain.repository.adapter.AbstractRemoteRepositoryAdapter;
import com.ofss.digx.infra.exceptions.Exception;
import com.ofss.digx.sites.abl.app.payment.adapter.IPaymentAdapter;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.MasterpassTransferResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationResponseDomainDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.ZakatDonationTransferRequestDomainDTO;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.ZakatDonation;
import com.ofss.digx.sites.abl.domain.payment.entity.transfer.repository.assembler.PaymentTransferAssembler;
import com.ofss.fc.app.context.SessionContext;
import com.ofss.fc.datatype.Date;
import com.ofss.fc.enumeration.ServiceCallContextType;
import com.ofss.fc.infra.thread.ThreadAttribute;

public class RemoteZakatDonationTransferRepositoryAdapter 
extends AbstractRemoteRepositoryAdapter<ZakatDonation>
implements IZakatDonationRepositoryAdapter
{
	private static RemoteZakatDonationTransferRepositoryAdapter singletonInstance;
	  
	  public static RemoteZakatDonationTransferRepositoryAdapter getInstance()
	  {
	    if (singletonInstance == null) {
	      synchronized (RemoteMasterpassTransferRepositoryAdapter.class)
	      {
	        if (singletonInstance == null) {
	          singletonInstance = new RemoteZakatDonationTransferRepositoryAdapter();
	        }
	      }
	    }
	    return singletonInstance;
	  }
	

	@Override
	public ZakatDonation process(ZakatDonation paramZakatDonation) throws Exception {

/*		 SessionContext sessionContext = (SessionContext)ThreadAttribute.get("CTX");
		    if ((sessionContext.getServiceCallContextType() != null) && (sessionContext.getServiceCallContextType() == ServiceCallContextType.VALIDATE)) {
		      return paramZakatDonation;
		    }
		    IAdapterFactory factory = AdapterFactoryConfigurator.getInstance().getAdapterFactory("CUSTOM_PAYMENT_ADAPTER_FACTORY");
		    IPaymentAdapter adapter = (IPaymentAdapter)factory.getAdapter("PaymentAdapter");
		    
		    ZakatDonationTransferRequestDomainDTO zakatDonationTransferReqDTO = null;
		    PaymentTransferAssembler assembler = new PaymentTransferAssembler();
		    zakatDonationTransferReqDTO = assembler.fromDomainObjectZakatDonationTransfer(paramZakatDonation);
		   
		      ZakatDonationResponseDomainDTO response = adapter.processZakatDonationTransfer(zakatDonationTransferReqDTO);
		      paramZakatDonation.getTransactionReference().setExternalReferenceId(response.getHostReference());
	
		    return paramZakatDonation;*/
		    
		    return null;
	}
	
	@Override
	public ZakatDonation read(PaymentKey paramPaymentKey) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ZakatDonation paramZakatDonation) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(ZakatDonation paramZakatDonation) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ZakatDonation paramZakatDonation) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ZakatDonation> list(String paramString, Date paramDate1, Date paramDate2,
			PaymentStatusType paramPaymentStatusType) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZakatDonation> lastPaymentList(Date paramDate) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZakatDonation listCompanyDetails() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}


