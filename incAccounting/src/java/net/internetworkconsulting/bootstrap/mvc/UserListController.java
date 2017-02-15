package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class UserListController extends ListController {
	public UserListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
		
	public String getEditController() { return "~/incBootstrap?App=UserEdit"; }
	public String getListController() { return "~/incBootstrap?App=UserList"; }
	public String getTitle() { return "User List"; }
	public List<String> getColumns() { return User.getSearchColumns(); }
	public Class getRowClass() { return User.class; }
	public void deleteRow(String guid) throws Exception {
		User user = User.loadByGuid(getUser().login(), User.class, guid);
		user.setIsDeleted(true);
		getUser().login().save(User.TABLE_NAME, user);
	}
}
