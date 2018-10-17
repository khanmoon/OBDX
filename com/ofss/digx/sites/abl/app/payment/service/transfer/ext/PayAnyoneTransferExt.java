package com.ofss.digx.sites.abl.app.payment.service.transfer.ext;

import javax.ws.rs.core.Response;

import com.ofss.digx.appx.payment.service.transfer.IPayAnyoneTransfer;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferCreateResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadRequestDTO;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferReadResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferResponse;
import com.ofss.digx.sites.abl.app.payment.dto.transfer.PayAnyoneTransferUpdateRequestDTO;
import com.ofss.fc.app.context.SessionContext;

public class PayAnyoneTransferExt implements IPayAnyoneTransferExtExecutor, IPayAnyoneTransfer {

	@Override
	public Response read(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response create(PayAnyoneTransferDTO paramPayAnyoneTransferDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateStatus(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response delete(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public com.ofss.digx.appx.payment.service.transfer.PayAnyoneTransferAuthentication PayAnyoneTransferAuthentication(
			String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void preCreate(SessionContext paramSessionContext,
			PayAnyoneTransferCreateRequestDTO paramPayAnyoneTransferCreateRequestDTO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postCreate(SessionContext paramSessionContext,
			PayAnyoneTransferCreateRequestDTO paramPayAnyoneTransferCreateRequestDTO,
			PayAnyoneTransferCreateResponse paramPayAnyoneTransferCreateResponse) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void preRead(SessionContext paramSessionContext,
			PayAnyoneTransferReadRequestDTO paramPayAnyoneTransferReadRequestDTO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postRead(SessionContext paramSessionContext,
			PayAnyoneTransferReadRequestDTO paramPayAnyoneTransferReadRequestDTO,
			PayAnyoneTransferReadResponse paramPayAnyoneTransferReadResponse) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void preUpdateStatus(SessionContext paramSessionContext,
			PayAnyoneTransferUpdateRequestDTO paramPayAnyoneTransferUpdateRequestDTO) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postUpdateStatus(SessionContext paramSessionContext,
			PayAnyoneTransferUpdateRequestDTO paramPayAnyoneTransferUpdateRequestDTO,
			PayAnyoneTransferResponse paramPayAnyoneTransferResponse) throws Exception {
		// TODO Auto-generated method stub

	}

}
