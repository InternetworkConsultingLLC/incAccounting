package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class DepartmentEditChildController extends Controller{
	private ButtonTag btnOpen;
	public DepartmentEditChildController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Department objModel = (Department) getModel();

		TextTag txtNumber = new TextTag(this, "Child", Department.NUMBER, objModel.getGuid(), objModel);
		txtNumber.setIsReadOnly(true);
		
		TextTag txtSegment = new TextTag(this, "Child", Department.SEGMENT, objModel.getGuid(), objModel);
		TextTag txtName = new TextTag(this, "Child", Department.NAME, objModel.getGuid(), objModel);
		CheckTag chkIsAllowed = new CheckTag(this, "Child", Department.IS_ALLOWED, objModel.getGuid(), objModel);
		CheckTag chkIsDeleted = new CheckTag(this, "Child", Department.IS_DELETED, objModel.getGuid(), objModel);

		btnOpen = new ButtonTag(this, "Child", "Open", objModel.getGuid(), "Open");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });				
	}
	public History createHistory() throws Exception { return null; }

	private void btnOpen_OnClick() throws Exception {
		Department objModel = (Department) getModel();
		
		if(objModel.getParentDepartmentsGuid() != null)
			redirect("~/incAccounting?App=DepartmentEdit&GUID=" + objModel.getGuid());
	}
	
		
	public void beforePopulate() throws Exception {
		super.beforePopulate();
		
		Department objModel = (Department) getModel();
		
		btnOpen.setValue("Open " + objModel.getNumber());
	}
}
