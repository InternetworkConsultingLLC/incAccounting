package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.ContactNote;
import net.internetworkconsulting.accounting.entities.ContactType;
import net.internetworkconsulting.accounting.entities.SalesTax;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.*;

public class ContactEditController extends Controller {
	protected ButtonTag btnSave;
	protected ButtonTag btnAddNote;
	protected ButtonTag btnAddContact;
	protected ComboTag cboSalesTax;
	protected TextTag txtCountry;
	protected TextTag txtZip;
	protected TextTag txtState;
	protected TextTag txtCity;
	protected TextTag txtLine2;
	protected TextTag txtLine1;
	protected TextTag txtFax;
	protected TextTag txtWebsite;
	protected TextTag txtEmail;
	protected TextTag txtOffice;
	protected TextTag txtMobile;
	protected TextTag txtHome;
	protected ComboTag cboShippingContact;
	protected ComboTag cboType;
	protected DateTag dtSince;
	protected CheckTag chkAllowed;
	protected TextTag txtDisplay;
	protected TextTag txtGuid;

	protected String getTemplateFile() { return "~/templates/ContactEdit.html"; }
	
	public ContactEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		User.loadOptions(getUser().login(), true);
		Contact.loadOptions(getUser().login(), true);

		setDocument(new Template(read_url(getTemplateFile()), new HtmlSyntax()));
		
		Contact objModel = (Contact) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Contact.loadByGuid(getUser().login(), Contact.class, sGuid);
			else {
				objModel = new Contact();
				objModel.initialize();
			}
		}
		setModel(objModel);
				
		txtGuid = new TextTag(this, Contact.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		txtDisplay = new TextTag(this, Contact.DISPLAY_NAME, objModel);
		chkAllowed = new CheckTag(this, Contact.IS_ALLOWED, objModel);
		dtSince = new DateTag(this, Contact.CONTACT_SINCE, objModel);

		cboType = new ComboTag(this, Contact.CONTACT_TYPES_GUID, objModel);
		cboType.setOptions(ContactType.loadOptions(getUser().login(), true));
		
		cboShippingContact = new ComboTag(this, Contact.DEFAULT_SHIPPING_CONTACTS_GUID, objModel);
		cboShippingContact.setOptions(objModel.loadChildOptions(getUser().login(), true));
		
		txtWebsite = new TextTag(this, Contact.WEBSITE, objModel);
		txtEmail = new TextTag(this, Contact.EMAIL_ADDRESS, objModel);
		txtOffice = new TextTag(this, Contact.OFFICE_PHONE, objModel);
		txtMobile = new TextTag(this, Contact.MOBILE_PHONE, objModel);
		txtHome = new TextTag(this, Contact.HOME_PHONE, objModel);
		txtFax = new TextTag(this, Contact.FAX_NUMBER, objModel);
		txtLine1 = new TextTag(this, Contact.LINE_1, objModel);
		txtLine2 = new TextTag(this, Contact.LINE_2, objModel);
		txtCity = new TextTag(this, Contact.CITY, objModel);
		txtState = new TextTag(this, Contact.STATE, objModel);
		txtZip = new TextTag(this, Contact.POSTAL_CODE, objModel);
		txtCountry = new TextTag(this, Contact.COUNTRY, objModel);

		cboSalesTax = new ComboTag(this, Contact.TAX_GROUP_GUID, objModel);
		cboSalesTax.setOptions(SalesTax.loadOptions(getUser().login(), true));
		
		List<Contact> lstChildren = objModel.loadChildrenContacts(getUser().login(), Contact.class, false);
		for(Contact child: lstChildren)
			createChildController(child);

		List<ContactNote> lstNotes = objModel.loadContactNotes(getUser().login(), ContactNote.class, false);
		for(ContactNote note: lstNotes)
			createNoteController(note);
		
		btnAddContact = new ButtonTag(this, "Add Contact");
		if(objModel.getRowState() == RowState.Insert)
			btnAddContact.setIsReadOnly(true);
		btnAddContact.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddContact_OnClick(); } });
		
		btnAddNote = new ButtonTag(this, "Add Note");
		if(objModel.getRowState() == RowState.Insert)
			btnAddNote.setIsReadOnly(true);
		btnAddNote.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddNote_OnClick(); } });
		
		btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });		
	}
	public History createHistory() throws Exception {
		Contact objModel = (Contact) getModel();

		String sDisplay = "New Contact";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	protected ContactEditChildController createChildController(Contact child) throws Exception {
		ContactEditChildController controller = new ContactEditChildController(this, "Child");
		controller.setModel(child);
		controller.setIsDocumentBlock(true);
		return controller;
	}
	protected ContactEditNoteController createNoteController(ContactNote note) {
		ContactEditNoteController controller = new ContactEditNoteController(this, "Note");
		controller.setModel(note);
		controller.setIsDocumentBlock(true);
		return controller;
	}

	protected void btnAddNote_OnClick() throws Exception {
		try {			
			Contact objModel = (Contact) getModel();

			ContactNote note = new ContactNote();
			note.initialize();
			note.setContactsGuid(objModel.getGuid());
			note.setUsersGuid(getUser().getGuid());

			objModel.loadContactNotes(getUser().login(), ContactNote.class, false).add(note);

			ContactEditNoteController controller = createNoteController(note);
			doCreateControls(controller, true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "a9778d3624324e85bde25b945b381281");
			addError("Add Note Error", ex.getMessage());
		}
	}
	protected void btnAddContact_OnClick() throws Exception {
		try {
			Contact objModel = (Contact) getModel();

			Contact child = new Contact();
			child.initialize(objModel);

			objModel.loadChildrenContacts(getUser().login(), Contact.class, false).add(child);

			ContactEditChildController controller = createChildController(child);			
			doCreateControls(controller, false);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "52da5d6c9c774e9aab6abb36e9a7c87f");
			addError("Add Contact Error", ex.getMessage());
		}		
	}
	protected void btnSave_OnClick() throws Exception {
		Contact objModel = (Contact) getModel();

		try {
			getUser().login().begin(true);
			getUser().login().save(Contact.TABLE_NAME, objModel);
			getUser().login().save(Contact.TABLE_NAME, objModel.loadChildrenContacts(getUser().login(), Contact.class, false));
			getUser().login().save(ContactNote.TABLE_NAME, objModel.loadContactNotes(getUser().login(), ContactNote.class, false));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "b39d408635d541f8ba0e4ef84a36ba42");
			addError("Save Error", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=ContactEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
