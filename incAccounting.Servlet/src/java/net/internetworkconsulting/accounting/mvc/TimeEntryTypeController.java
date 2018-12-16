package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.TimeEntryType;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.Tag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class TimeEntryTypeController extends EditController {
	private TimeEntryType objModel;
	private List<Option> lstItemOptions;
	
	public TimeEntryTypeController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	
	public void handleDeleteRow(String guid) throws Exception {
		objModel = TimeEntryType.loadByGuid(getUser().login(), TimeEntryType.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(TimeEntryType.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		objModel = TimeEntryType.loadByGuid(getUser().login(), TimeEntryType.class, guid);
		return objModel;
	}
	public Object handleNewRow() throws Exception {
		objModel = new TimeEntryType();
		objModel.initialize();
		return objModel;
	}
	
	public boolean getEnforceSecurity() { return true; }
	public History createHistory() throws Exception { return new History("Time Entry", getRequest(), getUser()); }
	
	public void createControls(Template document, Object model) throws Exception {
		objModel = (TimeEntryType) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/TimeEntryType.html"), new HtmlSyntax()));
		
		lstItemOptions = Item.loadOptions(getUser().login());

		CheckTag chkIsPaid = new CheckTag(this, TimeEntryType.IS_PAID, objModel);		
		Tag tagGuid = new TextTag(this, TimeEntryType.GUID, objModel);
		Tag tagDescription = new TextTag(this, TimeEntryType.DESCRIPTION, objModel);

		ComboTag tagContact = new ComboTag(this, TimeEntryType.ITEMS_GUID, objModel);
		tagContact.setOptions(lstItemOptions);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClicked(); } });
	}

	private void btnSave_OnClicked() throws Exception { 
		try {
			getUser().login().begin(true);
			getUser().login().save(TimeEntryType.TABLE_NAME, objModel);
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4fa4cee1e3aa470da1fe5d4e894ab61e");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=TimeEntryType&GUID=" + objModel.getGuid() + "&Error=Saved!");	
	}
}
