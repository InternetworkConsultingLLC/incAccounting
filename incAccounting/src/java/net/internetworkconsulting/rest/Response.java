package net.internetworkconsulting.rest;

import javax.xml.bind.annotation.*;

public class Response {
	private Object objModel = null;
	public Object getModel() { return objModel; }
	public void setModel(Object value) { objModel = value; }

	private Object objValue = null;
	public Object getValue() { return objValue; }
	public void setValue(Object value) { objValue = value; }
}
