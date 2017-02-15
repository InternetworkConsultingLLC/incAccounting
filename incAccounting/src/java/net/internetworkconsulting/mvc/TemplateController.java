package net.internetworkconsulting.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public abstract class TemplateController extends Controller {
	public static String BLOCK_BODY = "Body";
	
	public TemplateController(HttpServletRequest request, HttpServletResponse response, boolean is_postback) { super(request, response, is_postback); }
	public boolean getEnforceSecurity() { return false; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/Template.html"), new HtmlSyntax()));		
	}
	public History createHistory() throws Exception { return null; }
	
	public void populateDocument() throws Exception {
		boolean bSetModel = false;
		for(ControllerInterface ci: getControls())
			if(ci.getDocumentKeyword().equals(TemplateController.BLOCK_BODY)) {
				this.setModel(ci.getModel());
				bSetModel = true;
				continue;
			}
		
		if(!bSetModel)
			throw new Exception("The template requires a document keyword of " + TemplateController.BLOCK_BODY + "!");

		super.populateDocument();
	}

	public void execute() {
		loadChildControls();
		super.execute();
	}
	public abstract void loadChildControls();
}
