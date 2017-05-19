package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.TimeEntry;
import net.internetworkconsulting.accounting.entities.TimeEntryType;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;

public class TimeSheetLineController extends Controller {
	private TimeEntry objModel;
	private CheckTag chkIsIncluded;
	private String sQtyFormat;
	
	public boolean getIsIncluded() throws Exception { return chkIsIncluded.getIsChecked(); }
	
	public TimeSheetLineController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public History createHistory() throws Exception { return null; }

	public void createControls(Template document, Object model) throws Exception { 
		setDocument(document);
		objModel = (TimeEntry) getModel();

		sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		chkIsIncluded = new CheckTag(this, "Line", "Is Included", objModel.getGuid());		
		if(!getIsPostback())
			chkIsIncluded.setIsChecked(objModel.getTimeSheetsGuid() != null);

		LiteralTag tagDescription = new LiteralTag(this, "Line", TimeEntry.DESCRIPTION, objModel.getGuid(), objModel);
		LiteralTag tagGuid = new LiteralTag(this, "Line", TimeEntry.GUID, objModel.getGuid(), objModel);
		LiteralTag tagStarted = new LiteralTag(this, "Line", TimeEntry.STARTED, objModel.getGuid(), objModel);
		LiteralTag tagEnded = new LiteralTag(this, "Line", TimeEntry.ENDED, objModel.getGuid(), objModel);
		LiteralTag tagContact = new LiteralTag(this, "Line", TimeEntry.CONTACTS_GUID, objModel.getGuid());		
		
		LiteralTag tagDeparment = new LiteralTag(this, "Line", TimeEntry.DEPARTMENTS_GUID, objModel.getGuid());
		if(objModel.getDepartmentsGuid() != null) {
			Department obj = objModel.loadDepartment(getUser().login(), Department.class, false);
			tagDeparment.setValue(obj.getNumber() + " " + obj.getNestedName());
		}
		
		LiteralTag tagDocument = new LiteralTag(this, "Line", TimeEntry.DOCUMENTS_GUID, objModel.getGuid());
		if(objModel.getDocumentsGuid() != null) {
			Department obj = objModel.loadDepartment(getUser().login(), Document.class, false);
			tagDocument.setValue(obj.getNumber() + " " + obj.getNestedName());
		}

		LiteralTag tagType = new LiteralTag(this, "Line", TimeEntry.ENTRY_TYPES_GUID, objModel.getGuid());
		if(objModel.getEntryTypesGuid()!= null)
			tagType.setValue(objModel.loadTimeEntryType(getUser().login(), TimeEntryType.class, false).getDescription());

		LiteralTag tagJob = new LiteralTag(this, "Line", TimeEntry.JOBS_GUID, objModel.getGuid());
		if(objModel.getJobsGuid() != null) {
			Job obj = objModel.loadJob(getUser().login(), Job.class, false);
			tagDocument.setValue(obj.getNumber() + " " + obj.getNestedName());
		}

		LiteralTag tagHours = new LiteralTag(this, "Line", "Hours", objModel.getGuid());
		tagHours.setValue(Statement.convertObjectToString(objModel.getHours(), sQtyFormat));
	}
}
