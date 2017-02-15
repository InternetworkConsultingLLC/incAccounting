package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Group;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class GroupListController  extends ListController {
	public GroupListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=GroupEdit"; }
	public String getListController()  { return "~/incBootstrap?App=GroupList"; }
	public String getTitle() { return "Group List"; }
	public List<String> getColumns() { return Group.getSearchColumns(); }
	public Class getRowClass() { return Group.class; }
	public void deleteRow(String guid) throws Exception {
		Group grp = Group.loadByGuid(getUser().login(), Group.class, guid);
		grp.setIsDeleted(true);
		getUser().login().save(Group.TABLE_NAME, grp);
	}
}
