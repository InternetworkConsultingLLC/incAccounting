package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class AccountListController extends ListController {
	public AccountListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=AccountEdit"; }
	public String getListController() { return "~/incAccounting?App=AccountList"; }
	public String getTitle() { return "Account List"; }
	public List<String> getColumns() { return Account.getSearchColumns(); }
	public Class getRowClass() { return Account.class; }
	public void deleteRow(String guid) throws Exception {  
		Account objModel = Account.loadByGuid(getUser().login(), Account.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Account.TABLE_NAME, objModel);
	}
}
