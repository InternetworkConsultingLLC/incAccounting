package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class LoginController extends Controller {
	public LoginController(ControllerInterface controller, String document_keyword ) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(readTemplate("~/templates/Login.html"), new HtmlSyntax()));
		setModel(new User());

		//TextTag txDatabase = new TextTag(this, User.DATABASE, getModel());
		TextTag txtUser = new TextTag(this, User.EMAIL_ADDRESS, getModel());
		TextTag txtPassword = new TextTag(this, User.PASSWORD, getModel());
		txtPassword.setInputType(TextTag.TYPE_PASSWORD);
		
		ButtonTag btnLogin = new ButtonTag(this, "Login");
		btnLogin.setValue("Login");
		btnLogin.addOnClickEvent(new Event() {
			public void handle() throws Exception { btnLogin_OnClick(); }
		});
	}	
	public History createHistory() throws Exception { 
		return new History("Login", getRequest(), getUser()); 
	}
	
	public void btnLogin_OnClick() throws Exception {
		User objModel = (User) getModel();

		AdapterInterface adapter = null;
		try { adapter = objModel.login(); }
		catch(Exception ex) { 
			addError("Login Failed!", ex.getMessage()); 
			return;
		}

		setUser(objModel);

		if(objModel.isPasswordExpired(adapter))
			redirect("~/incBootstrap?App=ChangePassword");
		else
			redirect("~/incBootstrap?App=ReportView&GUID=18afbfdf422246b1a549f34dad94f8b7");
	}
}
