package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class DateTag extends TextTag {
	// reference - http://www.html5rocks.com/en/tutorials/forms/html5forms/#toc-introduction
	
	public static String TYPE_DATE = "date";
	public static String TYPE_DATETIME = "datetime";
	public static String TYPE_DATETIME_LOCAL = "datetime-local";
	
	public DateTag(ControllerInterface controller, String property, Object model) throws Exception { 
		super(controller, property, model); 
		setInputType(TYPE_DATE);
	}
	public DateTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { 
		super(controller, prefix, property, unique_key, model);
		setInputType(TYPE_DATE);
	}
	public DateTag(ControllerInterface controller, String document_keyword) {
		super(controller, document_keyword);
		setInputType(TYPE_DATE);
	}
	
	public void createControls(Template document, Object model) throws Exception { }
	
	public String getValue() throws Exception {
		String sValue = super.getValue();
		
		java.util.Date dtValue = (java.util.Date) net.internetworkconsulting.data.pervasive.Statement.parseStringToValue(java.util.Date.class, sValue);
		return net.internetworkconsulting.data.mysql.Statement.convertObjectToString(dtValue, "yyyy-MM-dd");
	}
	public boolean setValue(String value) throws Exception {
		return super.setValue(value);
	}

	// "disabled" - did not impliment as it does not get sent back.  use "readonly"
	
//	public void populateDocument() throws Exception { 
//		if(getName() == null || getName().length() < 1)
//			throw new Exception("You must provie a 'name' for your tags!");
//		if(getController() == null)
//			throw new Exception("Tag must be a subcrontoller, they are not standalone pages.");
//		if(getDocument() == null)
//			throw new Exception("The tag controller is not associated with a document!");
//
//		String html = "<input  type=\"" + getInputType() + "\" ";
//	
//		html += generateAttributes(true);
//
//		if(getValue() != null)
//			html +=  " value=\"" + getValue().replace("\"", "&quot;") + "\" ";
//		else
//			html +=  " value=\"\" ";
//		
//		if(getPlaceHolder()!= null)
//			html += " placeholder=\"" + getPlaceHolder() + "\" ";
//		if(getPattern() != null)
//			html += " pattern=\"" + getPattern() + "\" ";
//		if(getMaxLength() != null)
//			html += " maxlength=\"" + getMaxLength() + "\" ";		
//		if(getMax() != null)
//			html += " max=\"" + getMax() + "\" ";		
//		if(getMin() != null)
//			html += " min=\"" + getMin() + "\" ";		
//		if(getStep() != null)
//			html += " step=\"" + getStep() + "\" ";		
//		if(!getIsAutoCompleted())
//			html += " autocomplete=\"off\" ";
//		
//		html += " >";
//				
//		getController().getDocument().set(getDocumentKeyword(), html);
//	}	
}
