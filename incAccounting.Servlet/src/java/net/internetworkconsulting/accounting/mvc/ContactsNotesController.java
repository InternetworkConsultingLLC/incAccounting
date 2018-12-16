package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.accounting.entities.ContactNote;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.template.Template;

public class ContactsNotesController extends Controller {
	private final List<Option> lstUserOptions;
	public ContactsNotesController(ControllerInterface controller, String document_keyword, List<Option> user_options) { 
		super(controller, document_keyword); 
		
		lstUserOptions = user_options;
	}
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		ContactNote objModel = (ContactNote) getModel();
		
		CheckTag chkDelete = new CheckTag(this, "Note", ContactNote.IS_DELETED, objModel.getGuid(), objModel);

		TextTag txtDate = new TextTag(this, "Note", ContactNote.DATED, objModel.getGuid(), objModel);
		txtDate.setIsReadOnly(true);

		ComboTag cboUser = new ComboTag(this, "Note", ContactNote.USERS_GUID, objModel.getGuid(), objModel);
		cboUser.setOptions(lstUserOptions);
		
		TextTag txtSubject = new TextTag(this, "Note", ContactNote.SUBJECT, objModel.getGuid(), objModel);

		TextAreaTag txtMemo = new TextAreaTag(this, "Note", ContactNote.MEMORANDUM, objModel.getGuid(), objModel);
		txtMemo.setRows("5");
	}
	public History createHistory() throws Exception { return null; }
	
}
