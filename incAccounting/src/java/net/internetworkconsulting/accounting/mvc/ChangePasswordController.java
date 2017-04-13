package net.internetworkconsulting.accounting.mvc;

import java.util.Date;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ChangePasswordController extends Controller {
	private TextTag txtPassword;
	private TextTag txtConfirm;

	public ChangePasswordController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(readTemplate("~/templates/ChangePassword.html"), new HtmlSyntax()));
		
		txtPassword = new TextTag(this, "Password");
		txtPassword.setInputType(TextTag.TYPE_PASSWORD);

		txtConfirm = new TextTag(this, "Confirm");
		txtConfirm.setInputType(TextTag.TYPE_PASSWORD);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception { 
		return new History("Change Password", getRequest(), getUser()); 
	}
	
	private void btnSave_OnClick() throws Exception {
		User currentUser = getUser();
		currentUser.changePassword(getUser().login(), txtPassword.getValue(), txtConfirm.getValue());

		SessionInterface si = getUser().login().getSession();
		//getUser().login().setSession(null);
		getUser().login().save(net.internetworkconsulting.accounting.entities.User.TABLE_NAME, currentUser);
		//getUser().login().setSession(si);
		
		

		addError("Notice", "Password changed!");
	}
}
