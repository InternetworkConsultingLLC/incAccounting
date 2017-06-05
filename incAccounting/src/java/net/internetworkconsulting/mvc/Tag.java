package net.internetworkconsulting.mvc;

import java.lang.reflect.Method;
import java.util.HashSet;
import net.internetworkconsulting.data.mysql.Statement;

public abstract class Tag extends Controller implements TagInterface {
	public Tag(ControllerInterface controller, String document_keyword) { 
		super(controller, document_keyword); 
		setDocument(controller.getDocument());
		setName(document_keyword);
	}
	public Tag(ControllerInterface controller, String property, Object model) throws Exception {
		super(controller, property);
		setDocument(controller.getDocument());
		setName(property);
		bind(model, property);
	}
	public Tag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception {
		super(controller, prefix + " " + property);
		setDocument(controller.getDocument());
		setName(prefix + property + unique_key);
		bind(model, property);
	}
	public Tag(ControllerInterface controller, String prefix, String property, String unique_key) throws Exception {
		super(controller, prefix + " " + property);
		setDocument(controller.getDocument());
		setName(prefix + property + unique_key);
	}

	public boolean getEnforceSecurity() { return false; }
	
	private HashSet<String> myClasses = new HashSet<>();
	public HashSet<String> getClasses() { return myClasses; }
	public void setClasses(HashSet<String> value) { myClasses = value; }

	private String sName = "";
	public String getName() { return sName; }
	public void setName(String value) { sName = value; }

	// disabled means the user cannot interact with it, but the value
	// can be changed...  as where read only, the value will not change
	private boolean isReadOnly = false;
	public boolean getIsReadOnly() {
		return isReadOnly;
	}
	public void setIsReadOnly(boolean value) {
		isReadOnly = value;
		if(value && !getClasses().contains("readonly"))
			getClasses().add("readonly");
		else if(!value && getClasses().contains("readonly"))
			getClasses().remove("readonly");
	}
	
	private boolean bValueChanged = false;
	public boolean getIsValueChanged() { return bValueChanged; }
	public void setIsValueChanged(boolean value) { bValueChanged = bValueChanged || value; }
	
	private String sValue = null;
	public String getValue() throws Exception { 
		if(getModel() == null || getModelProperty() == null || getModelProperty().length() < 1)
			return sValue; 
		else if(getFormat() != null && getFormat().length() > 0)
			return Statement.convertObjectToString(getModelValue(), getFormat());
		else
			return Statement.convertObjectToString(getModelValue(), null);
	}
	public boolean setValue(String value) throws Exception {
		String sOldValue = getValue();
		if(sOldValue != null && sOldValue.equals(""))
			sOldValue = null;
		
		if(sOldValue == null && value == null)
			return false;
		if(sOldValue != null && value != null && value.equals(getValue()))
			return false;
		
		if(getModel() != null && getModelProperty() != null && getModelProperty().length() > 0)
			setModelValue(value);
		else
			sValue = value;

		return true;
	}
	private Object getModelValue() throws Exception {
		for(Method mthd : getModel().getClass().getMethods())
			if(mthd.getName().equals("get" + getCamelCase(getModelProperty())))
				if(mthd.getParameterCount() == 0)
					return mthd.invoke(getModel());
		
		return null;
	}
	private void setModelValue(String value) throws Exception {
		for(Method mthd : getModel().getClass().getMethods())
			if(mthd.getName().equals("set" + getCamelCase(getModelProperty())))
				if(mthd.getParameterCount() == 1) {
					Object objParsed = Statement.parseStringToValue(mthd.getParameterTypes()[0], value);
					String formatted = Statement.convertObjectToString(objParsed, getFormat());
					mthd.invoke(getModel(), Statement.parseStringToValue(mthd.getParameterTypes()[0], formatted));
					return;
				}

		throw new Exception("Could not locate set" + getCamelCase(getModelProperty()) + " on " + getModel().getClass().getSimpleName() + "!");
	}

	private String sModelProperty = "";
	public String getModelProperty() { return sModelProperty; }
	public void setModelProperty(String value) { sModelProperty = value; }

	private String sFormat = "";
	public String getFormat() { return sFormat; }
	public void setFormat(String value) { sFormat = value; }

	private HashSet<Event> myOnChangeEvents = new HashSet<>();
	public HashSet<Event> getOnChangeEvents() { return myOnChangeEvents; }
	public void setOnChangeEvents(HashSet<Event> value) { myOnChangeEvents = value; }
	public void addOnChangeEvent(Event value) { myOnChangeEvents.add(value); }

	private HashSet<Event> myOnClickEvents = new HashSet<>();
	public HashSet<Event> getOnClickEvents() { return myOnClickEvents; }
	public void setOnClickEvents(HashSet<Event> value) { myOnClickEvents = value; }
	public void addOnClickEvent(Event value) { myOnClickEvents.add(value); }

	private int iTabOrder = -1;
	public int getTabOrder() { return iTabOrder; }
	public void setTabOrder(int value) { iTabOrder = value; }
		
	private boolean isAutoFocused = false;
	public boolean getIsAutoFocused() { return isAutoFocused; }
	public void setIsAutoFocused(boolean value) { isAutoFocused = value; }

	private boolean isRequired = false;
	public boolean getIsRequired() { return isRequired; }
	public void setIsRequired(boolean value) { isRequired = value; }
	
	public String getID() throws Exception { return getCamelCase(getName()); }
	
	public void setFocus() throws Exception { this.setFocus(getID()); }

	protected String generateAttributes(boolean allows_name) throws Exception {
		getClasses().add(getCamelCase(getDocumentKeyword()));
		
		String html = "";
		if(getName() != null && getName().length() > 0) {
			html += " id=\"" + getCamelCase(getName()) + "\" ";
			if(allows_name)
				html += " name=\"" + getName() + "\" ";
		}
		
		String sClasses = "";
		for(String cls : getClasses())
			sClasses += cls + " ";
		if(sClasses.length() > 0)
			html += " class=\"" + sClasses.trim() + "\" ";

		
		if(getIsReadOnly())
			html += " readonly ";
		if(getIsAutoFocused())
			html += " autofocus ";
		if(getIsRequired())
			html += " required ";
		
		if(getOnChangeEvents().size() > 0)
			html += " onchange=\"return sendEvent('" + getName().replace("'", "\\'") + "', 'onchange');\" ";
		if(getOnClickEvents().size() > 0)
			html += " onclick=\"return sendEvent('" + getName().replace("'", "\\'") + "', 'onclick');\" ";

		return html;
	}

	public History createHistory() throws Exception { return null; }
	public void updateControls() throws Exception {
		if(getIsReadOnly())
			return;
		
		if(getName() == null || getName().length() == 0)
			throw new Exception("All controls must have a unique name!");
		
		String value = getRequest().getParameter(getName());
		if(value == null || value.length() < 1)
			setIsValueChanged(setValue(null));
		else
			setIsValueChanged(setValue(value));
	}
	public void handleEvents() throws Exception {
		handleChange();
		handleClick();
	}
	protected void handleChange() throws Exception {
		if(!getIsValueChanged())
			return;
		
		if(!getIsReadOnly())
			for(Event e : getOnChangeEvents())
				e.handle();
	}
	protected void handleClick() throws Exception {
		if(getName() == null || getName().length() < 1)
			return;
		
		String sHiddenControl = getRequest().getParameter(Controller.HIDDEN_CONTROL);
		if (sHiddenControl == null)
			return;
		if (!sHiddenControl.equals(getName()))
			return;

		String sHiddenEvent = getRequest().getParameter(Controller.HIDDEN_EVENT);
		if (sHiddenEvent == null)
			return;
		
		if(sHiddenEvent.toLowerCase().equals("onclick"))
			for(Event e : myOnClickEvents)
				e.handle();
	}
	
	public void bind(Object model) {
		setModel(model);
		setModelProperty(getDocumentKeyword());
	}
	public void bind(Object model, String property) {
		setModel(model);
		setModelProperty(property);
	}
}
