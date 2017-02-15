package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class SystemController extends Controller {
	public SystemController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/System.html"), new HtmlSyntax()));
	}
	public History createHistory() throws Exception { 
		return new History("Documentation", getRequest(), getUser()); 
	}

	public boolean getEnforceSecurity() { return true; }
}
