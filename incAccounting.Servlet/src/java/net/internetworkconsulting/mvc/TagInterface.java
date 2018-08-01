package net.internetworkconsulting.mvc;

import java.util.HashSet;

public interface TagInterface extends ControllerInterface {
	HashSet<String> getClasses();
	void setClasses(HashSet<String> value);

	String getName();
	void setName(String value);

//	boolean getIsReadOnly();
//	void setIsReadOnly(boolean value);
	
	boolean getIsReadOnly();
	void setIsReadOnly(boolean value);
		
	String getValue() throws Exception;
	boolean setValue(String value) throws Exception;
	
	String getModelProperty();
	void setModelProperty(String value);
	
	String getFormat();
	void setFormat(String value);

	int getTabOrder();
	void setTabOrder(int value);
	
	public String getID() throws Exception;
	
	HashSet<Event> getOnChangeEvents();
	void setOnChangeEvents(HashSet<Event> value);
	void addOnChangeEvent(Event value);
	
	HashSet<Event> getOnClickEvents();
	void setOnClickEvents(HashSet<Event> value);
	void addOnClickEvent(Event value);
	
	void bind(Object model, String property);
}
