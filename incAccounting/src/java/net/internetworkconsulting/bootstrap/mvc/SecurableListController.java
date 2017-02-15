package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Securable;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;


public class SecurableListController extends ListController {
	public SecurableListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=SecurableEdit"; }
	public String getListController() { return "~/incBootstrap?App=SecurableList"; }
	public String getTitle() { return "Securable List"; }
	public List<String> getColumns() { return Securable.getSearchColumns(); }
	public Class getRowClass() { return Securable.class; }
	public void deleteRow(String guid) throws Exception {
		Securable sec = Securable.loadByGuid(getUser().login(), Securable.class, guid);
		sec.setIsDeleted(true);
		getUser().login().save(Securable.TABLE_NAME, sec);
	}
}
