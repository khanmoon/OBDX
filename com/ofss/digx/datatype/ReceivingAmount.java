package com.ofss.digx.datatype;

import java.io.Serializable;

public class ReceivingAmount
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String Value;
  private String Currency;

	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	public String toString() {
		return "ReceivingAmount [Value=" + Value + ", Currency=" + Currency + "]";
	}
	
}
