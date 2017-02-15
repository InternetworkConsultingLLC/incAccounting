package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class DocumentTypeListController extends ListController {
	public DocumentTypeListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=DocumentTypeEdit"; }
	public String getListController() { return "~/incAccounting?App=DocumentTypeList"; }
	public String getTitle() { return "Document Type List"; }
	public List<String> getColumns() { return DocumentType.getSearchColumns(); }
	public Class getRowClass() { return DocumentType.class; }
	public void deleteRow(String guid) throws Exception {
		addError("Delete", "You cannot delete a document type!");
	}	
}
