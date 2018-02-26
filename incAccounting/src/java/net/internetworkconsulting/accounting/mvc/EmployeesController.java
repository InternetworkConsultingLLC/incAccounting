package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.ContactNote;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class EmployeesController extends ContactsController {
	protected Employee objEmployee;
	protected Contact objModel;
	
	protected String getTemplateFile() { return "~/templates/Employee.html"; }
	public EmployeesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		super.createControls(document, model);
		
		objModel = (Contact) getModel();
		
		List<Employee> lst = objModel.loadEmployee(getUser().login(), Employee.class, !getIsPostback()); 
		if(lst.size() == 1)
			objEmployee = lst.get(0);
		else {
			objModel.setParentContactsGuid(Contact.OUR_COMPANY_GUID);
			
			objEmployee = new Employee();
			objEmployee.initialize(objModel);
			lst.add(objEmployee);
		}
		
		TextTag txtFirstName = new TextTag(this, Employee.FIRST_NAME, objEmployee);
		TextTag txtLastName = new TextTag(this, Employee.LAST_NAME, objEmployee);
		TextTag txtSuffix = new TextTag(this, Employee.SUFFIX, objEmployee);
		TextTag txtTaxId = new TextTag(this, Employee.TAX_ID, objEmployee);
		DateTag txtDOB = new DateTag(this, Employee.DATE_OF_BIRTH, objEmployee);
		CheckTag chkMale = new CheckTag(this, Employee.IS_MALE, objEmployee);
		TextTag txtEthnicity = new TextTag(this, Employee.ETHNICITY, objEmployee);
		TextTag txtJobTitle = new TextTag(this, Employee.JOB_TITLE, objEmployee);
		
		ComboTag cboSupervisor = new ComboTag(this, Employee.SUPERVISOR_CONTACTS_GUID, objEmployee);
		cboSupervisor.setOptions(((Contact) objModel.loadParentContact(getUser().login(), Contact.class, true)).loadChildOptions(getUser().login(), true));
		
		DateTag dtHirred = new DateTag(this, Employee.DATE_HIRRED, objEmployee);
		DateTag dtTerminated = new DateTag(this, Employee.DATE_TERMINATED, objEmployee);
		DateTag dtVerified = new DateTag(this, Employee.DATE_VERIFIED, objEmployee);
		CheckTag chkInsurance = new CheckTag(this, Employee.INSURANCE, objEmployee);
		CheckTag chkVacation = new CheckTag(this, Employee.VACATION, objEmployee);
		
		cboType.setIsReadOnly(true);
	}
	public History createHistory() throws Exception {
		String sDisplay = "New Employee";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	protected void btnSave_OnClick() throws Exception {
		try {
			getUser().login().begin(true);
			getUser().login().save(Contact.TABLE_NAME, objModel);
			getUser().login().save(Employee.TABLE_NAME, objEmployee);
			getUser().login().save(Contact.TABLE_NAME, objModel.loadChildrenContacts(getUser().login(), Contact.class, !getIsPostback()));
			getUser().login().save(ContactNote.TABLE_NAME, objModel.loadContactNotes(getUser().login(), ContactNote.class, !getIsPostback()));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "b39d408635d541f8ba0e4ef84a36ba42");
			addError("Save Error", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Employee&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
