package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.PayrollCheck;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class PayrollCheckListController extends ListController {
	public PayrollCheckListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=PayrollCheckEdit"; }
	public String getListController() { return "~/incAccounting?App=PayrollCheckList"; }
	public String getTitle() { return "Payroll Check List"; }
	public List<String> getColumns() { return PayrollCheck.getSearchColumns(); }
	public Class getRowClass() { return PayrollCheck.class; }
	public void deleteRow(String guid) throws Exception {
		PayrollCheck objModel = PayrollCheck.loadByGuid(getUser().login(), PayrollCheck.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(PayrollCheck.TABLE_NAME, objModel);
	}
}
