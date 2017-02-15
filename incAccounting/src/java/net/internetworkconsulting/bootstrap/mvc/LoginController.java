/*
 * incBootstrap Servlet MVC Controllers
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.entities.User;
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
		setDocument(new Template(read("templates/Login.html"), new HtmlSyntax()));
		setModel(new User());

		TextTag txtUser = new TextTag(this, User.SQL_USER);
		txtUser.bind(getModel(), User.SQL_USER);

		TextTag txtPassword = new TextTag(this, User.PASSWORD);
		txtPassword.setInputType(TextTag.TYPE_PASSWORD);
		txtPassword.bind(getModel(), User.PASSWORD);
		
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
		objModel.setSqlServer(getSqlServer());
		objModel.setDatabase(getSqlDatabase());

		AdapterInterface adapter = null;
		try { adapter = objModel.login(); }
		catch(Exception ex) { 
			addError("Login Failed", ex.getMessage()); 
			return;
		}

		setUser(objModel);

		if(objModel.isPasswordExpired(adapter))
			redirect("~/incBootstrap?App=ChangePassword");
		else
			redirect("~/incBootstrap?App=System");
	}
}
