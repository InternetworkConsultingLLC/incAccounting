package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class TextAreaTag extends Tag {
	public static String WRAP_HARD = "hard";
	public static String WRAP_SOFT = "soft";
	
	public TextAreaTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public TextAreaTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public TextAreaTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public void createControls(Template document, Object model) throws Exception { }

	private String sPlaceHolder = null;
	public String getPlaceHolder() { return sPlaceHolder; }
	public void setPlaceHolder(String value) { sPlaceHolder = value; }
				
	private String sMaxLength = null; 
	public String getMaxLength() { return sMaxLength; }
	public void setMaxLength(String value) { sMaxLength = value; }
	
	private String sCols = null; 
	public String getCols() { return sCols; }
	public void setCols(String value) { sCols = value; }
	
	private String sRows = null; 
	public String getRows() { return sRows; }
	public void setRows(String value) { sRows = value; }
	
	private String sWrap = null; 
	public String getWrap() { return sWrap; }
	public void setWrap(String value) { sWrap = value; }
	
	public void populateDocument() throws Exception { 
		if(getName() == null || getName().length() < 1)
			throw new Exception("You must provie a 'name' for your tags!");
		if(getController() == null)
			throw new Exception("Tag must be a subcrontoller, they are not standalone pages.");

		String html = "<textarea ";
	
		html += generateAttributes(true);
		
		if(getPlaceHolder() != null)
			html += " placeholder=\"" + getPlaceHolder() + "\" ";
		if(getMaxLength() != null)
			html += " maxlength=\"" + getMaxLength() + "\" ";		
		if(getCols() != null)
			html += " cols=\"" + getCols() + "\" ";		
		if(getRows() != null)
			html += " rows=\"" + getRows() + "\" ";		
		if(getWrap() != null)
			html += " wrap=\"" + getWrap() + "\" ";				

		html += " >";

		if(getValue() != null)
			html +=  getValue().replace("<", "&lt;").replace(">", "&gt;");
		
		html += "</textarea> ";
				
		getController().getDocument().set(getDocumentKeyword(), html);
	}	
}
