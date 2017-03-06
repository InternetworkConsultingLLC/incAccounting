package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.bootstrap.mvc.EditController;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class JobsController extends EditController {
	public JobsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Job objModel = Job.loadByGuid(getUser().login(), Job.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Job.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Job.loadByGuid(getUser().login(), Job.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Job objModel = new Job();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Job objModel = (Job) handleNonPostbackActions(model);		
		setDocument(new Template(read_url("~/templates/Job.html"), new HtmlSyntax()));
	
		TextTag txtGuid = new TextTag(this, Job.GUID, objModel);
		txtGuid.setIsReadOnly(true);
		
		ComboTag cboParent = new ComboTag(this, Job.PARENT_JOBS_GUID, objModel);
		cboParent.setOptions(Job.loadOptions(getUser().login(), true));
		
		TextTag litNestedName = new TextTag(this, Job.NESTED_NAME, objModel);
		litNestedName.setIsReadOnly(true);
		
		TextTag txtSegment = new TextTag(this, Job.SEGMENT, objModel);

		TextTag lblNumber = new TextTag(this, Job.NUMBER, objModel);
		lblNumber.setIsReadOnly(true);

		TextTag txtName = new TextTag(this, Job.NAME, objModel);
		CheckTag chkIsAllowed = new CheckTag(this, Job.IS_ALLOWED, objModel);

		ButtonTag btnOpen = new ButtonTag(this, "Open");
		if(objModel.getParentJobsGuid() == null)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

		ButtonTag btnAdd = new ButtonTag(this, "Add");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnAdd.setIsReadOnly(true);
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		List<Job> lstChildren = objModel.loadChildren(getUser().login(), Job.class, false);
		for(Job child: lstChildren)
			createController(child);				
	}
	public JobsChildrenController createController(Job child) {
		JobsChildrenController decc = new JobsChildrenController(this, "Child");
		decc.setModel(child);
		decc.setIsDocumentBlock(true);
		return decc;
	}
	public History createHistory() throws Exception {
		Job objModel = (Job) getModel();

		String sDisplay = "New Job";
		if(objModel.getRowState() != RowInterface.RowState.Insert)
			sDisplay = objModel.getName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnOpen_OnClick() throws Exception {
		Job objModel = (Job) getModel();
		
		if(objModel.getParentJobsGuid() == null)
			redirect("~/incAccounting?App=JobList");
		else
			redirect("~/incAccounting?App=Job&GUID=" + objModel.getParentJobsGuid());
	}
	private void btnSave_OnClick() throws Exception {
		Job objModel = (Job) getModel();
		
		try {
			getUser().login().begin(true);
			getUser().login().save(Job.TABLE_NAME, objModel);
			getUser().login().save(Job.TABLE_NAME, objModel.loadChildren(getUser().login(), Job.class, false));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Job&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAdd_OnClick() throws Exception {
		Job objModel = (Job) getModel();

		Job dept = new Job();
		dept.initialize();
		dept.setParentJobsGuid(objModel.getGuid());
		
		objModel.loadChildren(getUser().login(), Job.class, false).add(dept);
		
		JobsChildrenController controller = createController(dept);
		doCreateControls(controller, false);
	}
}
