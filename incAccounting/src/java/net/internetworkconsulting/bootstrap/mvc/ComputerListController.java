package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Computer;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ComputerListController extends ListController {
	public ComputerListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }	
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=ComputerEdit"; }
	public String getListController() { return "~/incBootstrap?App=ComputerList"; }
	public String getTitle() { return "Computer List"; }
	public List<String> getColumns() { return Computer.getSearchColumns(); }
	public Class getRowClass() { return Computer.class; }
	public void deleteRow(String guid) throws Exception {
		Computer objModel = Computer.loadByGuid(getUser().login(), Computer.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Computer.TABLE_NAME, objModel);
	}
}
