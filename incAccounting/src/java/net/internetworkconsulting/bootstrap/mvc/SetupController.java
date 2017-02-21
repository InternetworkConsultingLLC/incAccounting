package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.entities.Setup;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class SetupController extends Controller {
	public SetupController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return !getIsSetupAllowed(); }

	public Setup newModel() { return new Setup(); }
	public History createHistory() throws Exception { 
		return new History("Setup", getRequest(), getUser()); 
	}
	
	public void createControls(Template document, Object model) throws Exception {
		checkAllowed();
		setDocument(new Template(read_url("~/templates/Setup.html"), new HtmlSyntax()));
		
		Setup objModel = (Setup) model;
		if(!getIsPostback()) {
			objModel = newModel();
			objModel.setSqlUser("root");
			objModel.setDatabase(getSqlDatabase());
			objModel.setSqlServer(getSqlServer());
		}
		setModel(objModel);			
		
		TextTag txtSqlUser = new TextTag(this, Setup.SQL_USER);
		txtSqlUser.bind(getModel(), Setup.SQL_USER);

		TextTag txtPassword = new TextTag(this, Setup.PASSWORD);
		txtPassword.bind(getModel(), Setup.PASSWORD);

		TextTag txtSqlServer = new TextTag(this, Setup.SQL_SERVER);
		txtSqlServer.bind(getModel(), Setup.SQL_SERVER);

		TextTag txtDatabase = new TextTag(this, Setup.DATABASE);
		txtDatabase.bind(getModel(), Setup.DATABASE);

		TextTag txtAdministratorPassword = new TextTag(this, Setup.ADMINISTRATOR_PASSWORD);
		txtAdministratorPassword.bind(getModel(), Setup.ADMINISTRATOR_PASSWORD);

		ButtonTag btnTestConnection = new ButtonTag(this, "Test Connection");
		btnTestConnection.setValue("Test Connection");
		btnTestConnection.addOnClickEvent(new Event() { public void handle() throws Exception { btnTestConnection_OnClick(); } });

		ButtonTag btnDropDatabase = new ButtonTag(this, "Drop Database");
		btnDropDatabase.setValue("Drop Database");
		btnDropDatabase.addOnClickEvent(new Event() { public void handle() throws Exception { btnDropDatabase_OnClick(); } });

		ButtonTag btnCreateDatabase = new ButtonTag(this, "Create Database");
		btnCreateDatabase.setValue("Create Database");
		btnCreateDatabase.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateDatabase_OnClick(); } });

		ButtonTag btnCreateAdministrator = new ButtonTag(this, "Create Administrator");
		btnCreateAdministrator.setValue("Create Administrator");	
		btnCreateAdministrator.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateAdministrator_OnClick(); } });
	}
	private void checkAllowed() throws Exception {
		if(getUser() != null && getUser().getGuid() != null && getUser().getGuid().equals(User.ADMINISTRATOR_GUID))
			return;

		if(!getIsSetupAllowed())
			throw new Exception("'Setup Allowed' is turned off or not defined in 'web.xml'!");
	}
	private void btnTestConnection_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.testConnection();
		addError("Connection", "Your connection was successful!");
	}
	private void btnDropDatabase_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.dropDatabase();
		addError("Droped Database", "Your database has been deleted!");
	}
	private void btnCreateDatabase_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.createDatabase();
		addError("Created Database", "You database has been created.");
	}
	private void btnCreateAdministrator_OnClick() throws Exception {
		Setup model = (Setup) getModel();
		model.createAdministrator();
		addError("Administrator Created", "The administrator account has been removed and recreated.");
	}
}
