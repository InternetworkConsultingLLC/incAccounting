package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ContactListController extends ListController {
	public ContactListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=ContactEdit"; }
	public String getListController() { return "~/incAccounting?App=ContactList"; }
	public String getTitle() { return "Contact List"; }
	public List<String> getColumns() { return Contact.getSearchColumns(); }
	public Class getRowClass() { return Contact.class; }
	public void deleteRow(String guid) throws Exception {
		Contact objModel = Contact.loadByGuid(getUser().login(), Contact.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Contact.TABLE_NAME, objModel);
	}
}
