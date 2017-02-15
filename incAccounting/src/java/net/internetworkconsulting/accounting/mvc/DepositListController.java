package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class DepositListController extends ListController {
	public DepositListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=DepositEdit"; }
	public String getListController() { return "~/incAccounting?App=DepositList"; }
	public String getTitle() { return "Deposit List"; }
	public List<String> getColumns() { return Deposit.getSearchColumns(); }
	public Class getRowClass() { return Deposit.class; }
	public void deleteRow(String guid) throws Exception {
		Deposit objModel = Deposit.loadByGuid(getUser().login(), Deposit.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Deposit.TABLE_NAME, objModel);
	}
	
}
