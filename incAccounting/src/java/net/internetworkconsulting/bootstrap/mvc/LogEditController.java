package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.entities.Computer;
import net.internetworkconsulting.bootstrap.entities.Log;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class LogEditController extends Controller {
	public LogEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/LogEdit.html"), new HtmlSyntax()));
		
		Log objModel = (Log) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter(Log.GUID);
			if(sGuid != null)
				objModel = Log.loadByGuid(getUser().login(), Log.class, sGuid);
			else {
				throw new Exception("You cannot create new log entries!");
			}
		}
		setModel(objModel);
		
		LiteralTag lblGuid = new LiteralTag(this, Log.GUID);
		lblGuid.bind(objModel, Log.GUID);
		
		LabelTag lblOccured = new LabelTag(this, Log.OCCURED);
		lblOccured.bind(objModel, Log.OCCURED);
		
		LabelTag lblLog = new LabelTag(this, Log.LOG);
		lblLog.bind(objModel, Log.LOG);
		
		LabelTag lblUser = new LabelTag(this, Log.USERS_GUID);
		lblUser.setValue(objModel.loadUser(getUser().login(), User.class, true).getSqlUser());
		
		LabelTag lblComputer = new LabelTag(this, Log.COMPUTERS_GUID);
		lblComputer.setValue(objModel.loadComputer(getUser().login(), Computer.class, true).getDescription());
		
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
