package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.ContactType;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ContactTypeListController extends ListController {
	public ContactTypeListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=ContactTypeEdit"; }
	public String getListController() { return "~/incAccounting?App=ContactTypeList"; }
	public String getTitle() { return "Contact Type List"; }
	public List<String> getColumns() { return ContactType.getSearchColumns(); }
	public Class getRowClass() { return ContactType.class; }
	public void deleteRow(String guid) throws Exception {
		ContactType objModel = ContactType.loadByGuid(getUser().login(), ContactType.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(ContactType.TABLE_NAME, objModel);
	}
}
