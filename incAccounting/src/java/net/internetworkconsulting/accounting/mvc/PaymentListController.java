package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class PaymentListController extends ListController {
	public PaymentListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=PaymentEdit"; }
	public String getListController() { return "~/incAccounting?App=PaymentList"; }
	public String getTitle() { return "Payment List"; }
	public List<String> getColumns() { return Payment.getSearchColumns(); }
	public Class getRowClass() { return Payment.class; }
	public void deleteRow(String guid) throws Exception {
		Payment objModel = Payment.loadByGuid(getUser().login(), Payment.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Payment.TABLE_NAME, objModel);
	}
	
}
