package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class PaymentTypeListController extends ListController {
	public PaymentTypeListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=PaymentTypeEdit"; }
	public String getListController() { return "~/incAccounting?App=PaymentTypeList"; }
	public String getTitle() { return "Payment Type List"; }
	public List<String> getColumns() { return PaymentType.getSearchColumns(); }
	public Class getRowClass() { return PaymentType.class; }
	public void deleteRow(String guid) throws Exception {
		addError("Delete", "You cannot delete a payment type!");
	}
}
