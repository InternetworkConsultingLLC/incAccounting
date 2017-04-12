package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.data.TimeEntriesRow;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.TimeEntry;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.Tag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class TimeEntryController extends EditController {
	private TimeEntriesRow objModel;
	
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

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		Tag tagGuid = new TextTag(this, TimeEntry.GUID, objModel);
		Tag tagContact = new ComboTag(this, TimeEntry.CONTACTS_GUID, objModel);
		Tag tagEmploye = new ComboTag(this, TimeEntry.EMPLOYEES_GUID, objModel);
		Tag tgUser = new ComboTag(this, TimeEntry.USERS_GUID, objModel);

		Tag tagJob = new ComboTag(this, TimeEntry.JOBS_GUID, objModel);
		Tag tagDepartment = new ComboTag(this, TimeEntry.DEPARTMENTS_GUID, objModel);

		Tag tagTimeSheet = new ComboTag(this, TimeEntry.TIME_SHEETS_GUID, objModel);
		Tag tagDocument = new ComboTag(this, TimeEntry.DOCUMENTS_GUID, objModel);

		Tag tagType = new ComboTag(this, TimeEntry.ENTRY_TYPES_GUID, objModel);
		Tag tagStarted = new TextTag(this, TimeEntry.STARTED, objModel);
		Tag tagEnded = new TextTag(this, TimeEntry.ENDED, objModel);
		Tag tagDescription = new TextTag(this, TimeEntry.DESCRIPTION, objModel);
		
		ButtonTag tagSave = new ButtonTag(this, "Save");
		ButtonTag tagEnd = new ButtonTag(this, "End");
	}
}
