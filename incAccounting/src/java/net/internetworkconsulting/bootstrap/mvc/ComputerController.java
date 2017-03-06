package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.data.ComputersRow;
import net.internetworkconsulting.bootstrap.entities.Computer;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ComputerController extends EditController {
	public ComputerController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Computer objModel = Computer.loadByGuid(getUser().login(), Computer.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Computer.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Computer.loadByGuid(getUser().login(), Computer.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Computer objModel = new Computer();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Computer objModel = (Computer) handleNonPostbackActions(model);
		setDocument(new Template(read_url("~/templates/Computer.html"), new HtmlSyntax()));		

		TextTag txtGuid = new TextTag(this, Computer.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel, Computer.GUID);
		
		TextTag txtDescription = new TextTag(this, Computer.DESCRIPTION);
		txtDescription.bind(objModel, Computer.DESCRIPTION);
		
		TextTag txtMac = new TextTag(this, Computer.MAC_ADDRESS);
		txtMac.bind(objModel, Computer.MAC_ADDRESS);
		
		CheckTag chkIsAllowed = new CheckTag(this, Computer.IS_ALLOWED);
		chkIsAllowed.bind(objModel, Computer.IS_ALLOWED);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		Computer objModel = (Computer) getModel();
		
		String sDisplay = "New Computer";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDescription();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnSave_OnClick() throws Exception {
		Computer objModel = (Computer) getModel();
		try { 
			getUser().login().begin(true);
			getUser().login().save(Computer.TABLE_NAME, objModel); 
			getUser().login().commit(true);
		} 
		catch (Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}
		redirect("~/incBootstrap?App=Computer&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
