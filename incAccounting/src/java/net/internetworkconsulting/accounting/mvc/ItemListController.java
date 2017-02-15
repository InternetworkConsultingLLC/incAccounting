package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ItemListController extends ListController {
	public ItemListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=ItemEdit"; }
	public String getListController() { return "~/incAccounting?App=ItemList"; }
	public String getTitle() { return "Item List"; }
	public List<String> getColumns() { return Item.getSearchColumns(); }
	public Class getRowClass() { return Item.class; }
	public void deleteRow(String guid) throws Exception {
		Item objModel = Item.loadByGuid(getUser().login(), Item.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Item.TABLE_NAME, objModel);
	}
}
