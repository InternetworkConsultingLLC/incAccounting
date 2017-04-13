package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.template.Template;

public class TimeSheetController extends EditController {
	public TimeSheetController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	
	public void handleDeleteRow(String guid) throws Exception { }
	public Object handleLoadRow(String guid) throws Exception { return null; }
	public Object handleNewRow() throws Exception { return null; }
	
	public boolean getEnforceSecurity() { return true; }
	public History createHistory() throws Exception { return new History("Time Sheet", getRequest(), getUser()); }
	
	public void createControls(Template document, Object model) throws Exception { }
}
