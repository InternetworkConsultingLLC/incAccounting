package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class DocumentListController extends ListController {
	public DocumentListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=DocumentEdit"; }
	public String getListController() { return "~/incAccounting?App=DocumentList"; }
	public String getTitle() { return "Document List"; }
	public List<String> getColumns() { return Document.getSearchColumns(); }
	public Class getRowClass() { return Document.class; }
	public void deleteRow(String guid) throws Exception {
		Document objModel = Document.loadByGuid(getUser().login(), Document.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Document.TABLE_NAME, objModel);
	}
}
