package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DepartmentsController extends EditController {
	private List<Option> lstDepartmentOptions;
	
	public DepartmentsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Department objModel = Department.loadByGuid(getUser().login(), Department.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Department.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Department.loadByGuid(getUser().login(), Department.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Department objModel = new Department();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Department objModel = (Department) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Department.html"), new HtmlSyntax()));
		
		lstDepartmentOptions = Department.loadOptions(getUser().login());

		TextTag txtGuid = new TextTag(this, Department.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtNumber = new TextTag(this, Department.NUMBER, objModel);
		txtNumber.setIsReadOnly(true);
		
		TextTag txtNestedName = new TextTag(this, Department.NESTED_NAME, objModel);
		txtNestedName.setIsReadOnly(true);

		TextTag txtSegment = new TextTag(this, Department.SEGMENT, objModel);		
		TextTag txtName = new TextTag(this, Department.NAME, objModel);
		CheckTag chkIsAllowed = new CheckTag(this, Department.IS_ALLOWED, objModel);

		ComboTag cboParent = new ComboTag(this, Department.PARENT_DEPARTMENTS_GUID, objModel);
		cboParent.setOptions(lstDepartmentOptions);		

		ButtonTag btnOpen = new ButtonTag(this, "", "Open", "", "Open Parent");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

		ButtonTag btnAdd = new ButtonTag(this, "Add");
		if(objModel.getRowState() == RowState.Insert)
			btnAdd.setIsReadOnly(true);
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		List<Department> lstChildren = objModel.loadChildren(getUser().login(), Department.class, !getIsPostback());
		for(Department child: lstChildren)
			createController(child);				
	}
	public DepartmentsChildrenController createController(Department child) {
		DepartmentsChildrenController decc = new DepartmentsChildrenController(this, "Child");
		decc.setModel(child);
		decc.setIsDocumentBlock(true);
		return decc;
	}
	public History createHistory() throws Exception {
		Department objModel = (Department) getModel();

		String sDisplay = "New Department";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnOpen_OnClick() throws Exception {
		Department objModel = (Department) getModel();
		
		if(objModel.getParentDepartmentsGuid() == null)
			redirect("~/incAccounting?App=DepartmentList");
		else
			redirect("~/incAccounting?App=Department&GUID=" + objModel.getParentDepartmentsGuid());
	}
	private void btnSave_OnClick() throws Exception {
		Department objModel = (Department) getModel();
		
		try {
			getUser().login().begin(true);
			getUser().login().save(Department.TABLE_NAME, objModel);
			getUser().login().save(Department.TABLE_NAME, objModel.loadChildren(getUser().login(), Department.class, false));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Department&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAdd_OnClick() throws Exception {
		Department objModel = (Department) getModel();

		Department dept = new Department();
		dept.initialize();
		dept.setParentDepartmentsGuid(objModel.getGuid());
		
		objModel.loadChildren(getUser().login(), Department.class, !getIsPostback()).add(dept);
		
		DepartmentsChildrenController controller = createController(dept);
		doCreateControls(controller, false);
	}
}
