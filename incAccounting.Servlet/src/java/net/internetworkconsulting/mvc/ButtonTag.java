package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class ButtonTag extends Tag {
	public ButtonTag(ControllerInterface controller, String document_keyword) { 
		super(controller, document_keyword); 		
		try { setValue(document_keyword); }
		catch(Exception ex) { }
	}
	public ButtonTag(ControllerInterface controller, String prefix, String document_keyword, String unique_key, String display) { 
		super(controller, document_keyword);
		
		if(prefix != null && prefix.length() > 0)
			setDocumentKeyword(prefix + " " + document_keyword);
		else
			setDocumentKeyword(document_keyword);			
		
		try { setValue(display); }
		catch(Exception ex) { }
		
		setName(prefix + document_keyword + unique_key);		
	}

	public void createControls(Template document, Object model) throws Exception { }
	public void updateControls() throws Exception { }

	protected void handleChange() throws Exception { }
//	protected void handleClick() throws Exception {
//		if(getIsReadOnly())
//			return;
//		
//		if(getName() == null || getName().length() < 1)
//			return;
//		
//		String sValue = getRequest().getParameter(getName());
//		if (sValue == null)
//			return;
//		if (!sValue.equals(getName()))
//			return;
//		
//		for(Event e : getOnClickEvents())
//			e.handle();
//	}

	public void populateDocument() throws Exception {
		if(getName() == null || getName().length() < 1)
			throw new Exception("You must provie a 'name' for your tags!");
		if(getController() == null)
			throw new Exception("Tag must be a subcrontoller, they are not standalone pages.");

		String sKeyword;
		if(getDocumentKeyword() != null)
			sKeyword = getDocumentKeyword();
		else
			sKeyword = getName();

		getClasses().add(getCamelCase(sKeyword));

		String html = "<button ";
		html += " value=\"" + getName() + "\" ";
		html += generateAttributes(true);
		
		if(getIsReadOnly())
			html += " disabled ";
		
		html += " >";
		html += getValue();
		html += " </button>";
				
		getController().getDocument().set(sKeyword, html);
	}

}
