package com.ofss.digx.sites.abl.domain.payment.entity.transfer;

import com.ofss.fc.framework.domain.AbstractDomainObjectKey;

public class ZakatDonationCompanyDetails 
extends AbstractDomainObjectKey
{
	private static final long serialVersionUID = -4960596856991625778L;
	private String companyID;
	private String companyName;
	private String companyCode;
	private String categgoryID;
	
	public String getCompanyID ()
	{
		return this.companyID;
	}
	
	public void setCompanyId (String companyID)
	{
		this.companyID = companyID;
	}
	
	
	public String getCompanyName ()
	{
		return this.companyName;
	}
	
	public void setCompanyName (String companyName)
	{
		this.companyName = companyName;
	}
	
	public String getCompanyCode ()
	{
		return this.companyCode;
	}
	
	public void setCompanyCode (String companyCode)
	{
		this.companyCode = companyCode;
	}
	
	public String getCateggoryID ()
	{
		return this.categgoryID;
	}
	
	public void setCateggoryID (String categgoryID)
	{
		this.categgoryID = categgoryID;
	}
	
	
	public String keyAsString() {
		return null;
	}

}
