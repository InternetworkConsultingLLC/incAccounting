package net.internetworkconsulting.accounting.mvc;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import net.internetworkconsulting.accounting.data.TimeEntriesRow;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.TimeEntry;
import net.internetworkconsulting.accounting.entities.TimeEntryType;
import net.internetworkconsulting.accounting.entities.TimeSheet;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.Tag;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class TimeEntryController extends EditController {
	private TimeEntriesRow objModel;
	private List<Option> lstJobOptions;
	private List<Option> lstEmployeeOptions;
	private List<Option> lstContactOptions;
	private List<Option> lstTimeEntryTypeOptions;
	private List<Option> lstTimeSheetOptions;
	private List<Option> lstDepartmntOptions;
	private List<Option> lstDocumentOptions;
	
	public TimeEntryController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	
	public void handleDeleteRow(String guid) throws Exception {
		objModel = TimeEntry.loadByGuid(getUser().login(), TimeEntry.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(TimeEntry.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		objModel = TimeEntry.loadByGuid(getUser().login(), TimeEntry.class, guid);
		return objModel;
	}
	public Object handleNewRow() throws Exception {
		objModel = new TimeEntry();
		objModel.initialize();
		return objModel;
	}
	
	public boolean getEnforceSecurity() { return true; }
	public History createHistory() throws Exception { return new History("Time Entry", getRequest(), getUser()); }
	
	public void createControls(Template document, Object model) throws Exception {
		objModel = (TimeEntry) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/TimeEntry.html"), new HtmlSyntax()));

		lstContactOptions = Contact.loadOptions(getUser().login());
		lstEmployeeOptions = Employee.loadOptions(getUser().login());
		lstJobOptions = Job.loadOptions(getUser().login());
		lstDepartmntOptions = Department.loadOptions(getUser().login());
		lstTimeSheetOptions = TimeSheet.loadOptions(getUser().login());
		lstTimeEntryTypeOptions = TimeEntryType.loadOptions(getUser().login());
		lstDocumentOptions = Document.loadOptions(getUser().login());
		
		Tag tagGuid = new TextTag(this, TimeEntry.GUID, objModel);

		TextTag tagStarted = new TextTag(this, TimeEntry.STARTED, objModel);
		tagStarted.setPlaceHolder("yyyy-mm-dd HH:mm:ss");

		TextTag tagEnded = new TextTag(this, TimeEntry.ENDED, objModel);
		tagEnded.setPlaceHolder("yyyy-mm-dd HH:mm:ss");

		Tag tagDescription = new TextAreaTag(this, TimeEntry.DESCRIPTION, objModel);
		
		ComboTag tagContact = new ComboTag(this, TimeEntry.CONTACTS_GUID, objModel);
		tagContact.setOptions(lstContactOptions);
		
		ComboTag tagEmploye = new ComboTag(this, TimeEntry.EMPLOYEES_GUID, objModel);
		tagEmploye.setOptions(lstEmployeeOptions);
		
		ComboTag tagJob = new ComboTag(this, TimeEntry.JOBS_GUID, objModel);
		tagJob.setOptions(lstJobOptions);
		
		ComboTag tagDepartment = new ComboTag(this, TimeEntry.DEPARTMENTS_GUID, objModel);
		tagDepartment.setOptions(lstDepartmntOptions);
		
		ComboTag tagTimeSheet = new ComboTag(this, TimeEntry.TIME_SHEETS_GUID, objModel);
		tagTimeSheet.setOptions(lstTimeSheetOptions);
		
		ComboTag tagDocument = new ComboTag(this, TimeEntry.DOCUMENTS_GUID, objModel);
		tagDocument.setOptions(lstDocumentOptions);
		
		ComboTag tagType = new ComboTag(this, TimeEntry.ENTRY_TYPES_GUID, objModel);
		tagType.setOptions(lstTimeEntryTypeOptions);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClicked(); } });
		
		if(objModel.getEnded() == null) {
			ButtonTag btnEnd = new ButtonTag(this, "End");
			btnEnd.addOnClickEvent(new Event() { public void handle() throws Exception { btnEnd_OnClicked(); } });
		}
	}

	private void btnSave_OnClicked() throws Exception {
		try {
			if(objModel.getContactsGuid() == null) {
				addError("Save", "'Bill To' cannot be empty!");
				return;
			}
			getUser().login().begin(true);
			getUser().login().save(TimeEntry.TABLE_NAME, objModel);
			getUser().login().commit(true);			
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "dbe463ec959f4cdb8810985b9c4a85f1");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=TimeEntry&GUID=" + objModel.getGuid() + "&Error=Saved!");	
	}
	private void btnEnd_OnClicked() throws Exception { 
		if(objModel.getEntryTypesGuid() == null || "d99313c888db4f71bd45c43cd09b492a".equals(objModel.getEntryTypesGuid())) {
			addError("Save", "You must set the type to something other than incomplete!");
			return;	
		}
		if(objModel.getContactsGuid() == null) {
			addError("Save", "'Bill To' cannot be empty!");
			return;
		}

		TimeEntry objNext = new TimeEntry();
		try {
			getUser().login().begin(true);
			objModel.setEnded(new Date(Calendar.getInstance().getTimeInMillis()));			
			getUser().login().save(TimeEntry.TABLE_NAME, objModel);
			
			objNext.initialize();
			objNext.setEmployeesGuid(objModel.getEmployeesGuid());
			getUser().login().save(TimeEntry.TABLE_NAME, objNext);
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "c8e2c8639da14947aefc496f14ea14b9");
			addError("Save", ex.getMessage());
			return;
		}

		redirect("~/incAccounting?App=TimeEntry&GUID=" + objNext.getGuid() + "&Error=Saved and created new entry.");	
	}
}
