package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.TimeEntry;
import net.internetworkconsulting.accounting.entities.TimeSheet;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.Tag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class TimeSheetController extends EditController {
	private TimeSheet objModel;
	private List<TimeSheetLineController> lstLineControllers = new LinkedList<>();
	private List<TimeEntry> lstLines;
	private String sQtyFormat;
	
	public TimeSheetController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	
	public void handleDeleteRow(String guid) throws Exception {
		objModel = TimeSheet.loadByGuid(getUser().login(), TimeSheet.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(TimeSheet.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		objModel = TimeSheet.loadByGuid(getUser().login(), TimeSheet.class, guid);
		return objModel;
	}
	public Object handleNewRow() throws Exception {
		objModel = new TimeSheet();
		objModel.initialize();
		return objModel;
	}
	
	public boolean getEnforceSecurity() { return true; }
	public History createHistory() throws Exception { return new History("Time Sheet", getRequest(), getUser()); }
	
	public void createControls(Template document, Object model) throws Exception {
		objModel = (TimeSheet) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/TimeSheet.html"), new HtmlSyntax()));

		sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";

		LiteralTag litDecimals = new LiteralTag(this, "Quantity Decimals");
		litDecimals.setValue(getUser().getSetting(Document.SETTING_QUANITY_DECIMALS));		
		
		Tag tagGuid = new TextTag(this, TimeSheet.GUID, objModel);

		ComboTag cboEmployee = new ComboTag(this, TimeSheet.EMPLOYEES_GUID, objModel);
		cboEmployee.setOptions(Employee.loadOptions(getUser().login(), false));
		cboEmployee.addOnChangeEvent(new Event() { public void handle() throws Exception { cboEmployee_Changed(); } });
		
		DateTag tagEnding = new DateTag(this, TimeSheet.PERIOD_ENDING, objModel);
		tagEnding.addOnChangeEvent(new Event() { public void handle() throws Exception { cboEmployee_Changed(); } });
		
		DateTag tagStarting = new DateTag(this, TimeSheet.PERIOD_STARTING, objModel);
		tagStarting.addOnChangeEvent(new Event() { public void handle() throws Exception { cboEmployee_Changed(); } });
		
		TextTag txtNumber = new TextTag(this, TimeSheet.NUMBER, objModel);

		TextTag tagTotalHours = new TextTag(this, TimeSheet.TOTAL_HOURS, objModel);
		tagTotalHours.setIsReadOnly(true);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClicked(); } });

		ButtonTag btnAuto = new ButtonTag(this, "Auto");
		btnAuto.addOnClickEvent(new Event() { public void handle() throws Exception { btnAuto_OnClicked(); } });
		
		lstLines = objModel.loadTimeEntries(getUser().login(), TimeEntry.class, false);		
		for(TimeEntry line: lstLines)
			createController(line);	
	}
	private TimeSheetLineController createController(TimeEntry line) {
		TimeSheetLineController cont = new TimeSheetLineController(this, "Line");
		cont.setModel(line);
		cont.setIsDocumentBlock(true);
		
		lstLineControllers.add(cont);
		
		return cont;
	}

	private void cboEmployee_Changed() throws Exception {
		if(objModel.getPeriodStarting() == null || objModel.getPeriodEnding() == null || objModel.getEmployeesGuid() == null)
			return;
		
		objModel.clearTimEntries();
		
		for(TimeSheetLineController tlc : lstLineControllers)
			getControls().remove(tlc);
		lstLineControllers.clear();
		
		lstLines = objModel.loadTimeEntries(getUser().login(), TimeEntry.class, true);
		for(TimeEntry line: lstLines) {
			TimeSheetLineController cont = createController(line);	
		
			doCreateControls(cont, false);
			doBeforeUpdate(cont);
			doBeforeHandle(cont);
			doHandleEvents(cont);
		}
	}
	private void btnSave_OnClicked() throws Exception { 
		try {
			getUser().login().begin(true);
			
			List<TimeEntry> lstTimeEntries = new LinkedList<>();
			for(TimeSheetLineController telc : lstLineControllers) {
				TimeEntry te = (TimeEntry) telc.getModel();

				if(telc.getIsIncluded())
					te.setTimeSheetsGuid(objModel.getGuid());
				else
					te.setTimeSheetsGuid(null);

				lstTimeEntries.add(te);
			}
			
			objModel.calculate(lstTimeEntries);
			
			getUser().login().save(TimeSheet.TABLE_NAME, objModel);
			getUser().login().save(TimeEntry.TABLE_NAME, lstTimeEntries);
			
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "5a78ca0ea5a040598ab898bde190dad1");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=TimeSheet&GUID=" + objModel.getGuid() + "&Error=Saved!");	
	}
	
	private void btnAuto_OnClicked() throws Exception {
		for(TimeSheetLineController telc : lstLineControllers) {
			TimeEntry te = (TimeEntry) telc.getModel();
			
			if(te.loadTimeEntryType(getUser().login(), TimeEntryTypeController.class, false).getIsPaid())
				telc.setIsIncluded(true);
			else
				telc.setIsIncluded(false);				
		}
	}

	public void beforePopulate() throws Exception {
		LiteralTag litTimeEntries = new LiteralTag(this, "TimeEntries");
		litTimeEntries.setValue(Controller.toJson(lstLines));
	}
}
