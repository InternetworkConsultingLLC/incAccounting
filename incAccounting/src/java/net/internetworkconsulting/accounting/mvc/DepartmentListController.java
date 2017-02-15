package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class DepartmentListController extends ListController {
	public DepartmentListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=DepartmentEdit"; }
	public String getListController() { return "~/incAccounting?App=DepartmentList"; }
	public String getTitle() { return "Department List"; }
	public List<String> getColumns() { return Department.getSearchColumns(); }
	public Class getRowClass() { return Department.class; }
	public void deleteRow(String guid) throws Exception {
		Department objModel = Department.loadByGuid(getUser().login(), Department.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Department.TABLE_NAME, objModel);
	}
}
