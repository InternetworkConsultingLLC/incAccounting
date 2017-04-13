package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Log;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class LogController extends EditController {
	public LogController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		throw new Exception("You cannot delete log entries.");
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Log.loadByGuid(getUser().login(), Log.class, guid);
	}
	public Object handleNewRow() throws Exception {
		throw new Exception("You cannot create log entries.");
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Log objModel = (Log) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Log.html"), new HtmlSyntax()));		
		
		LiteralTag lblGuid = new LiteralTag(this, Log.GUID);
		lblGuid.bind(objModel, Log.GUID);
		
		LabelTag lblOccured = new LabelTag(this, Log.OCCURED);
		lblOccured.bind(objModel, Log.OCCURED);
		
		LabelTag lblLog = new LabelTag(this, Log.LOG);
		lblLog.bind(objModel, Log.LOG);
		
		LabelTag lblUser = new LabelTag(this, Log.USERS_GUID);
		lblUser.setValue(objModel.loadUser(getUser().login(), User.class, true).getEmailAddress());
		
		LiteralTag lblCodeGuid = new LiteralTag(this, Log.CODE_GUID);
		lblCodeGuid.bind(objModel, Log.CODE_GUID);
		
		LabelTag litDetails = new LabelTag(this, Log.DETAILS);
		litDetails.setValue(objModel.getDetails().replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br/>"));
	}
	public History createHistory() throws Exception {
		Log objModel = (Log) getModel();
		
		String sDisplay = "New Log";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getOccured().toString();

		return new History(sDisplay, getRequest(), getUser());
	}
}
