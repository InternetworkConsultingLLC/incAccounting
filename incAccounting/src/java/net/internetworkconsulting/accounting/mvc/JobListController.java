package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.ListController;

public class JobListController extends ListController {
	public JobListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public String getEditController() { return "~/incAccounting?App=JobEdit"; }
	public String getListController() { return "~/incAccounting?App=JobList"; }
	public String getTitle() { return "Job List"; }
	public List<String> getColumns() { return Job.getSearchColumns(); }
	public Class getRowClass() { return Job.class; }
	public void deleteRow(String guid) throws Exception {
		Job objModel = Job.loadByGuid(getUser().login(), Job.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Job.TABLE_NAME, objModel);
	}
}
