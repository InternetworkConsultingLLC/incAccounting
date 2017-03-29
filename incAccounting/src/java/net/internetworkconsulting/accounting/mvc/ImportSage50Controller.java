package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.ImportSage50;
import net.internetworkconsulting.data.pervasive.Adapter;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class ImportSage50Controller extends Controller {
	private ImportSage50 objModel = null;
	
	public ImportSage50Controller(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setModel(model);		
		if(!getIsPostback())
			setModel(new ImportSage50());
		objModel = (ImportSage50) getModel();

		setDocument(new Template(readTemplate("~/templates/ImportSage50.html"), new HtmlSyntax()));

		DateTag tagStarting = new DateTag(this, ImportSage50.START_DATE, objModel);
		DateTag tagEnding = new DateTag(this, ImportSage50.END_DATE, objModel);
		TextTag tagDbServer = new TextTag(this, ImportSage50.DB_SERVER, objModel);
		TextTag tagDbDatabase = new TextTag(this, ImportSage50.DB_DATABASE, objModel);
		TextTag tagDbUser = new TextTag(this, ImportSage50.DB_USER, objModel);
		TextTag tagDbPassword = new TextTag(this, ImportSage50.DB_PASSWORD, objModel);
		
		ButtonTag tagDbTest = new ButtonTag(this, "Test DB");
		tagDbTest.addOnClickEvent(new Event() { public void handle() throws Exception { tagDbTest_Clicked(); } });

		ButtonTag tagAccounts = new ButtonTag(this, "1) Accounts");
		tagAccounts.addOnClickEvent(new Event() { public void handle() throws Exception { tagAccounts_Clicked(); } });
	}
	public History createHistory() throws Exception {
		return new History("Import Sage 50", getRequest(), getUser());
	}
	
	private void tagDbTest_Clicked() {
		objModel.testDatabase();
	}
	private void tagAccounts_Clicked() {
		objModel.testDatabase();
	}
}
