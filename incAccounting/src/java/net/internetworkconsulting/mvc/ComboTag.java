package net.internetworkconsulting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.template.Template;

public class ComboTag  extends Tag {
	public ComboTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public ComboTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public ComboTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void createControls(Template document, Object model) throws Exception { }
	private List<Option> lstOptions = new LinkedList<>();
	public List<Option> getOptions() { return lstOptions; }
	public void setOptions(List<Option> value) { lstOptions = value; }
	
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

		String html = "<select ";
	
		html += generateAttributes(true);
		
		if(getIsReadOnly())
			html += " readonly ";

		html += " value=\"";
		if(getValue() != null)
			html +=  getValue().replace("\"", "&quot;");
		html += "\" ";
		html += " >";
		
		String sValue = getValue();
		if(getOptions() != null)
			for(Option opt : getOptions()) {
				html += " <option value=\"" + opt.getValue() + "\" ";
				String sOptionValue = opt.getValue();
				if(sValue != null && sValue.equals(sOptionValue))
					html += " selected ";
				html += ">" + opt.getDisplay() + "</option>";
			}
		
		html += " </select> ";
				
		getController().getDocument().set(sKeyword, html);
	}	
}
