package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class EmployeeListController  extends ListController {
	public EmployeeListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=EmployeeEdit"; }
	public String getListController() { return "~/incAccounting?App=EmployeeList"; }
	public String getTitle() { return "Employee List"; }
	public List<String> getColumns() { return Employee.getSearchColumns(); }
	public Class getRowClass() { return Employee.class; }
	public void deleteRow(String guid) throws Exception {
		Employee objModel = Employee.loadByGuid(getUser().login(), Employee.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Employee.TABLE_NAME, objModel);
	}
}
