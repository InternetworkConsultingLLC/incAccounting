package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class TextTag extends Tag {
	// reference - http://www.html5rocks.com/en/tutorials/forms/html5forms/#toc-introduction
	
	public static String TYPE_TEXT = "text";
	public static String TYPE_PASSWORD = "password";
	public static String TYPE_HIDDEN = "hidden";
	public static String TYPE_COLOR = "color";
	public static String TYPE_DATE = "date";
	public static String TYPE_DATETIME = "datetime";
	public static String TYPE_DATETIME_LOCAL = "datetime-local";
	public static String TYPE_EMAIL = "email";
	public static String TYPE_MONTH = "month";
	public static String TYPE_NUMBER = "number";
	public static String TYPE_RANGE = "range";
	public static String TYPE_SEARCH = "search";
	public static String TYPE_TEL = "tel";
	public static String TYPE_TIME = "time";
	public static String TYPE_URL = "url";
	public static String TYPE_WEEK = "week";
	
	public TextTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public TextTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public TextTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public void createControls(Template document, Object model) throws Exception { }

	// "disabled" - did not impliment as it does not get sent back.  use "readonly"
	
	private String sType = TextTag.TYPE_TEXT;
	public String getInputType() { return sType; }
	public void setInputType(String value) { sType = value; }
	
	private String sPlaceHolder = null;
	public String getPlaceHolder() { return sPlaceHolder; }
	public void setPlaceHolder(String value) { sPlaceHolder = value; }
	
	private String sPattern = null;
	public String getPattern() { return sPattern; }
	public void setPattern(String value) { sPattern = value; }
		
	private String sMax = null; // Specifies the maximum value for an input field
	public String getMax() { return sMax; }
	public void setMax(String value) { sMax = value; }

	private String sMin = null; // Specifies the minimum value for an input field
	public String getMin() { return sMin; }
	public void setMin(String value) { sMin = value; }

	private String sSize = null; // Specifies the legal number intervals for an input field
	public String getSize() { return sSize; }
	public void setSize(String value) { sSize = value; }

	private String sStep = null; // Specifies the width (in characters) of an input field
	public String getStep() { return sStep; }
	public void setStep(String value) { sStep = value; }

	private String sMaxLength = null; // Specifies the maximum number of character for an input field
	public String getMaxLength() { return sMaxLength; }
	public void setMaxLength(String value) { sMaxLength = value; }
	
	private boolean isAutoCompleted = true;
	public boolean getIsAutoCompleted() { return isAutoCompleted; }
	public void setIsAutoCompleted(boolean value) { isAutoCompleted = value; }
	
	private boolean isAutoFocused = false;
	public boolean getIsAutoFocused() { return isAutoFocused; }
	public void setIsAutoFocused(boolean value) { isAutoFocused = value; }
		
	private boolean isRequired = false;
	public boolean getIsRequired() { return isRequired; }
	public void setIsRequired(boolean value) { isRequired = value; }

	public void populateDocument() throws Exception { 
		if(getName() == null || getName().length() < 1)
			throw new Exception("You must provie a 'name' for your tags!");
		if(getController() == null)
			throw new Exception("Tag must be a subcrontoller, they are not standalone pages.");
		if(getDocument() == null)
			throw new Exception("The tag controller is not associated with a document!");

		String html = "<input  type=\"" + getInputType() + "\" ";
	
		html += generateAttributes(true);

		if(getValue() != null)
			html +=  " value=\"" + getValue().replace("\"", "&quot;") + "\" ";
		else
			html +=  " value=\"\" ";
		
		if(getPlaceHolder()!= null)
			html += " placeholder=\"" + getPlaceHolder() + "\" ";
		if(getPattern() != null)
			html += " pattern=\"" + getPattern() + "\" ";
		if(getMaxLength() != null)
			html += " maxlength=\"" + getMaxLength() + "\" ";		
		if(getMax() != null)
			html += " max=\"" + getMax() + "\" ";		
		if(getMin() != null)
			html += " min=\"" + getMin() + "\" ";		
		if(getStep() != null)
			html += " step=\"" + getStep() + "\" ";		
		if(!getIsAutoCompleted())
			html += " autocomplete=\"off\" ";
		
		html += " >";
				
		getController().getDocument().set(getDocumentKeyword(), html);
	}	
}
