package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.accounting.entities.ContactNote;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.template.Template;

public class ContactEditNoteController extends Controller {
	public ContactEditNoteController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		ContactNote objModel = (ContactNote) getModel();
		
		CheckTag chkDelete = new CheckTag(this, "Note", ContactNote.IS_DELETED, objModel.getGuid(), objModel);

		TextTag txtDate = new TextTag(this, "Note", ContactNote.DATED, objModel.getGuid(), objModel);
		txtDate.setIsReadOnly(true);

		ComboTag cboUser = new ComboTag(this, "Note", ContactNote.USERS_GUID, objModel.getGuid(), objModel);
		cboUser.setOptions(User.loadOptions(getUser().login(), false));
		
		TextTag txtSubject = new TextTag(this, "Note", ContactNote.SUBJECT, objModel.getGuid(), objModel);

		TextAreaTag txtMemo = new TextAreaTag(this, "Note", ContactNote.MEMORANDUM, objModel.getGuid(), objModel);
		txtMemo.setRows("5");
	}
	public History createHistory() throws Exception { return null; }
	
}
