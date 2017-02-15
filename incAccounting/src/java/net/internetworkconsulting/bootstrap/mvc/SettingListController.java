package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Setting;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class SettingListController extends ListController {
	public SettingListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=SettingEdit"; }
	public String getListController() { return "~/incBootstrap?App=SettingList"; }
	public String getTitle() { return "Setting List"; }
	public List<String> getColumns() { return Setting.getSearchColumns(); }
	public Class getRowClass() { return Setting.class; }
	public void deleteRow(String guid) throws Exception {
		Setting setting = Setting.loadByGuid(getUser().login(), Setting.class, guid);
		setting.setIsDeleted(true);
		getUser().login().save(Setting.TABLE_NAME, setting);
	}
}
