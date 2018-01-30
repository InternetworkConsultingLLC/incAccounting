package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.data.Row;

public class Option extends Row {
	public Option() { 
		super(); 
		setSqlSecurableGuid("c8276ec6738a4fc0bb0fe7af7815f045");
	}
	public Option(String display, String value) throws Exception {
		super();
		setDisplay(display);
		setValue(value);
	}
	
	public static String DISPLAY = "Display";
	public java.lang.String getDisplay() { return (java.lang.String) get(DISPLAY); }
	public void setDisplay(java.lang.String value) throws Exception { set(DISPLAY, value); }
	
	public static String VALUE = "Value";
	public java.lang.String getValue() { return (String) get(VALUE); }
	public void setValue(java.lang.String value) throws Exception { set(VALUE, value); }	
}
