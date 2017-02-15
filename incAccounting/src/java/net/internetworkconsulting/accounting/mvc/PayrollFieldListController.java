package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.PayrollField;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class PayrollFieldListController extends ListController {
	public PayrollFieldListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=PayrollFieldEdit"; }
	public String getListController() { return "~/incAccounting?App=PayrollFieldList"; }
	public String getTitle() { return "Payroll Field List"; }
	public List<String> getColumns() { return PayrollField.getSearchColumns(); }
	public Class getRowClass() { return PayrollField.class; }
	public void deleteRow(String guid) throws Exception {
		PayrollField objModel = PayrollField.loadByGuid(getUser().login(), PayrollField.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(PayrollField.TABLE_NAME, objModel);
	}
	
}
