package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class LabelTag extends Tag {
	public LabelTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public LabelTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public LabelTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void createControls(Template document, Object model) throws Exception { }
	public void updateControls() throws Exception { }
	protected void handleChange() throws Exception { }
	protected void handleClick() throws Exception { }

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

//		String html = "<label ";
//		html += generateAttributes(true);
//		
//		html += " >";
//		html += getValue();
//		html += " </label>";
//				
//		getController().getDocument().set(sKeyword, html);

		getController().getDocument().set(sKeyword, getValue());
	}
}
