package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class UserController extends EditController {
	private TextTag txtPassword;
	private TextTag txtConfirm;

	public UserController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }


	public void handleDeleteRow(String guid) throws Exception {
		User objModel = User.loadByGuid(getUser().login(), User.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(User.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return User.loadByGuid(getUser().login(), User.class, guid);
	}
	public Object handleNewRow() throws Exception {
		User objModel = new User();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {
		User objModel = (User) handleNonPostbackActions(model);		
		setDocument(new Template(read_url("~/templates/User.html"), new HtmlSyntax()));		

		CheckTag chkIsAllowed = new CheckTag(this, User.IS_ALLOWED);
		chkIsAllowed.bind(getModel(), User.IS_ALLOWED);

		CheckTag chkAddComputer = new CheckTag(this, User.ADD_COMPUTER);
		chkAddComputer.bind(getModel(), User.ADD_COMPUTER);

		TextTag txtGuid = new TextTag(this, User.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(getModel(), User.GUID);

		TextTag txtDisplayName = new TextTag(this, User.DISPLAY_NAME);
		txtDisplayName.bind(getModel(), User.DISPLAY_NAME);

		TextTag txtSqlUser = new TextTag(this, User.SQL_USER);
		txtSqlUser.setIsReadOnly(((RowInterface) getModel()).getRowState() != RowInterface.RowState.Insert);
		txtSqlUser.bind(getModel(), User.SQL_USER);

		TextTag txtEmailAddress = new TextTag(this, User.EMAIL_ADDRESS);
		txtEmailAddress.bind(getModel(), User.EMAIL_ADDRESS);

		DateTag dtPasswordDate = new DateTag(this, User.PASSWORD_DATE);
		dtPasswordDate.bind(getModel(), User.PASSWORD_DATE);
		
		txtPassword = new TextTag(this, User.PASSWORD);
		txtPassword.setInputType(TextTag.TYPE_PASSWORD);

		txtConfirm = new TextTag(this, "Confirm");
		txtConfirm.setInputType(TextTag.TYPE_PASSWORD);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

		ButtonTag btnReset = new ButtonTag(this, "Reset");
		btnReset.setValue("Reset");
		btnReset.addOnClickEvent(new Event() { public void handle() throws Exception { btnReset_OnClick(); } });
	}
	public History createHistory() throws Exception {
		User objModel = (User) getModel();
		
		String sDisplay = "New User";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnSave_OnClick() throws Exception {
		User objModel = (User) getModel();
		try { 
			getUser().login().begin(true);
			getUser().login().save(User.TABLE_NAME, objModel); 
			getUser().login().commit(true);
		} 
		catch (Exception ex) { 
			getUser().login().rollback(true);
			addError("Error", ex.getMessage()); 
			return;
		}

		redirect("~/incBootstrap?App=User&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnReset_OnClick() throws Exception {
		User objModel = (User) getModel();
		objModel.setDatabase(getSqlDatabase());
		objModel.resetSqlPassword(getUser().login(), txtConfirm.getValue(), txtPassword.getValue());
		addError("Error", "Reset!");
	}

}
