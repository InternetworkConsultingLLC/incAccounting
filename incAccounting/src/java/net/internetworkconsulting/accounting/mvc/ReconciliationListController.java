package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Reconciliation;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ReconciliationListController extends ListController {
	public ReconciliationListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=ReconciliationEdit"; }
	public String getListController() { return "~/incAccounting?App=ReconciliationList"; }
	public String getTitle() { return "Reconciliation List"; }
	public List<String> getColumns() { return Reconciliation.getSearchColumns(); }
	public Class getRowClass() { return Reconciliation.class; }
	public void deleteRow(String guid) throws Exception {
		Reconciliation objModel = Reconciliation.loadByGuid(getUser().login(), Reconciliation.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Reconciliation.TABLE_NAME, objModel);
	}
}
