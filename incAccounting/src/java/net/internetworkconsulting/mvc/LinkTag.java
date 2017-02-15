package net.internetworkconsulting.mvc;

import net.internetworkconsulting.template.Template;

public class LinkTag extends Tag {
	public LinkTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public LinkTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public LinkTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void createControls(Template document, Object model) throws Exception { }
	public void updateControls() throws Exception { }
	protected void handleChange() throws Exception { }
	protected void handleClick() throws Exception { }

	private String sUrl = null;
	public String getUrl() { return sUrl; }
	public void setUrl(String value) { sUrl = value; }
	
	public void populateDocument() throws Exception {
		String html = "<a href=\"" + getUrl() + "\">" + getValue() + "</a>";		
		getDocument().set(getDocumentKeyword(), html);
	}
}
