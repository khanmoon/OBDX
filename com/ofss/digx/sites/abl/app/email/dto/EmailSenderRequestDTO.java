package com.ofss.digx.sites.abl.app.email.dto;

import com.ofss.digx.app.dda.dto.statement.DemandDepositAccountActivityRequestDTO;
import com.ofss.fc.framework.domain.common.dto.DataTransferObject;

public class EmailSenderRequestDTO extends DataTransferObject {
	private DemandDepositAccountActivityRequestDTO AccountActivityDetails;

	public DemandDepositAccountActivityRequestDTO getAccountActivityDetails() {
		return AccountActivityDetails;
	}

	public void setAccountActivityDetails(DemandDepositAccountActivityRequestDTO accountActivityDetails) {
		AccountActivityDetails = accountActivityDetails;
	}
	
	
}
