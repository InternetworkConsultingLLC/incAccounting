package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.SalesTax;
import net.internetworkconsulting.mvc.*;

public class SalesTaxListController extends ListController {
	public SalesTaxListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=SalesTaxEdit"; }
	public String getListController() { return "~/incAccounting?App=SalesTaxList"; }
	public String getTitle() { return "Sales Tax  List"; }
	public List<String> getColumns() { return SalesTax.getSearchColumns(); }
	public Class getRowClass() { return SalesTax.class; }
	public void deleteRow(String guid) throws Exception {  
		SalesTax objModel = SalesTax.loadByGuid(getUser().login(), SalesTax.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(SalesTax.TABLE_NAME, objModel);
	}
}
