package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class JobsChildrenController extends Controller{
	private ButtonTag btnOpen;
	public JobsChildrenController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Job objModel = (Job) getModel();
		
		TextTag txtSegment = new TextTag(this, "Child", Job.SEGMENT, objModel.getGuid(), objModel);
		txtSegment.bind(objModel, Job.SEGMENT);
		
		TextTag lblNumber = new TextTag(this, "Child", Job.NUMBER, objModel.getGuid(), objModel);
		lblNumber.setName(Job.NUMBER + objModel.getGuid());
		lblNumber.bind(objModel, Job.NUMBER);
		
		TextTag txtName = new TextTag(this, "Child", Job.NAME, objModel.getGuid(), objModel);
		txtName.setName(Job.NAME + objModel.getGuid());
		txtName.bind(objModel, Job.NAME);
		
		CheckTag chkIsAllowed = new CheckTag(this, "Child", Job.IS_ALLOWED, objModel.getGuid(), objModel);
		chkIsAllowed.setName(Job.IS_ALLOWED + objModel.getGuid());
		chkIsAllowed.bind(objModel, Job.IS_ALLOWED);

		CheckTag chkIsDeleted = new CheckTag(this, "Child", Job.IS_DELETED, objModel.getGuid(), objModel);
		chkIsDeleted.setName(Job.IS_DELETED + objModel.getGuid());
		chkIsDeleted.bind(objModel, Job.IS_DELETED);
		
		btnOpen = new ButtonTag(this, "Child", "Open", objModel.getGuid(), "Open");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });				
	}
	public History createHistory() throws Exception { return null; }

	private void btnOpen_OnClick() throws Exception {
		Job objModel = (Job) getModel();
		
		if(objModel.getParentJobsGuid() != null)
			redirect("~/incAccounting?App=Job&GUID=" + objModel.getGuid());
	}
	public void beforePopulate() throws Exception {
		super.beforePopulate();
		Job objModel = (Job) getModel();
		btnOpen.setValue("Open " + objModel.getNumber());
	}
}