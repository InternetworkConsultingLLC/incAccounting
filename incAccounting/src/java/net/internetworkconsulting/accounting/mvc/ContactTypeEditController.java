package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.ContactType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ContactTypeEditController extends Controller {
	public ContactTypeEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/ContactTypeEdit.html"), new HtmlSyntax()));
		
		ContactType objModel = (ContactType) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = ContactType.loadByGuid(getUser().login(), ContactType.class, sGuid);
			else {
				objModel = new ContactType();
				objModel.initialize();
			}
		}
		setModel(objModel);
		
		TextTag txtGuid = new TextTag(this, ContactType.GUID, objModel);
		txtGuid.setIsReadOnly(true);
		
		
		TextTag txtName = new TextTag(this, ContactType.DISPLAY_NAME, objModel);
		CheckTag chkAllowed = new CheckTag(this, ContactType.IS_ALLOWED, objModel);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		ContactType objModel = (ContactType) getModel();

		String sDisplay = "New Contact Type";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName() + " Type";

		return new History(sDisplay, getRequest(), getUser());
	}
	public void btnSave_OnClick() throws Exception {
		ContactType objModel = (ContactType) getModel();

		try {
			getUser().login().begin(true);
			getUser().login().save(ContactType.TABLE_NAME, objModel);
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "3621f2278d7e40038161b6e2054d2df8");
			addError("Save", ex.getMessage());
			return;
		}

		redirect("~/incAccounting?App=ContactTypeEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
