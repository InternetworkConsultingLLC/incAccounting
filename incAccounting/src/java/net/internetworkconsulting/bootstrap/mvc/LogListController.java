package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Log;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class LogListController extends ListController {
	public LogListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=LogEdit"; }
	public String getListController() { return "~/incBootstrap?App=LogList"; }
	public String getTitle() { return "Log List"; }
	public List<String> getColumns() { return Log.getSearchColumns(); }
	public Class getRowClass() { return Log.class; }
	public void deleteRow(String guid) throws Exception {
		throw new Exception("You cannot delete log entries!");
	}
}
