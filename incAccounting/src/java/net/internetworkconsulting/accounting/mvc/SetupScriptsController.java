package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.template.Template;

public class SetupScriptsController extends Controller {
	private LabelTag lblPrompt;
	private CheckTag chkTag;
	private String objModel;
		
	public SetupScriptsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		objModel = (String) getModel();
		
		lblPrompt = new LabelTag(this, "Script Name");
		lblPrompt.setValue(objModel);
				
		chkTag = new CheckTag(this, "Script Check");
		chkTag.setName("ScriptCheck" + objModel);
		chkTag.setIsChecked(true);

		if(!getIsPostback()) {
			String sGetValue = getRequest().getParameter("ScriptCheck" + objModel);
			if(sGetValue != null) {
				chkTag.setValue(sGetValue);
			}
		}
	}
	public History createHistory() throws Exception { return null; }
	
	public CheckTag getCheckTag() throws Exception {
		return chkTag;
	}
}