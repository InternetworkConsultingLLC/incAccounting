package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class UnitMeasureListController extends ListController {
	public UnitMeasureListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=UnitMeasureEdit"; }
	public String getListController() { return "~/incAccounting?App=UnitMeasureList"; }
	public String getTitle() { return "Unit Measure List"; }
	public List<String> getColumns() { return UnitMeasure.getSearchColumns(); }
	public Class getRowClass() { return UnitMeasure.class; }
	public void deleteRow(String guid) throws Exception {  
		UnitMeasure objModel = UnitMeasure.loadByGuid(getUser().login(), UnitMeasure.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(UnitMeasure.TABLE_NAME, objModel);
	}
}
