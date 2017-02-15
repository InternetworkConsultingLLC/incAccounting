package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Report;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class ReportListController extends ListController {
	public ReportListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incBootstrap?App=ReportEdit"; }
	public String getListController() { return "~/incBootstrap?App=ReportList"; }
	public String getTitle() { return "Report List"; }
	public List<String> getColumns() { return Report.getSearchColumns(); }
	public Class getRowClass() { return Report.class; }
	public void deleteRow(String guid) throws Exception {
		Report rpt = Report.loadByGuid(getUser().login(), Report.class, guid);
		rpt.setIsDeleted(true);
		getUser().login().save(Report.TABLE_NAME, rpt);
	}	
}
