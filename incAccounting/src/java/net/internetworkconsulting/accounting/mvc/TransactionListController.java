package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class TransactionListController extends ListController {
	public TransactionListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=TransactionEdit"; }
	public String getListController() { return "~/incAccounting?App=TransactionList"; }
	public String getTitle() { return "Transaction List"; }
	public List<String> getColumns() { return Transaction.getSearchColumns(); }
	public Class getRowClass() { return Transaction.class; }
	public void deleteRow(String guid) throws Exception {  
		Transaction tran = Transaction.loadByGuid(getUser().login(), Transaction.class, guid);
		tran.setIsDeleted(true);
		getUser().login().save(Transaction.TABLE_NAME, tran);
	}
}
