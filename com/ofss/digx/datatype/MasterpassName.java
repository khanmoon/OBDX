package com.ofss.digx.datatype;

import java.io.Serializable;

public class MasterpassName
  implements Serializable
{
  private static final long serialVersionUID = 8869683895936076272L;
  private String First;
  private String Middle;
  private String Last;

	public String getFirst() {
		return First;
	}
	public void setFirst(String first) {
		First = first;
	}
	public String getMiddle() {
		return Middle;
	}
	public void setMiddle(String middle) {
		Middle = middle;
	}
	public String getLast() {
		return Last;
	}
	public void setLast(String last) {
		Last = last;
	}
	public String toString() {
		return "SenderName [First=" + First + ", Middle=" + Middle + ", Last=" + Last + "]";
	}
}
