package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class LiteralTag extends Tag {
	public LiteralTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public LiteralTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public LiteralTag(ControllerInterface controller, String prefix, String property, String unique_key) throws Exception { super(controller, prefix, property, unique_key); }
	public LiteralTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void createControls(Template document, Object model) throws Exception { }
	public void updateControls() throws Exception { }
	protected void handleChange() throws Exception { }
	protected void handleClick() throws Exception { }

	public void populateDocument() throws Exception {
		if(getName() == null)
			throw new Exception("Litral tags requires the 'name' to be set.");
		
		getDocument().set(getDocumentKeyword(), getValue());
	}
}
