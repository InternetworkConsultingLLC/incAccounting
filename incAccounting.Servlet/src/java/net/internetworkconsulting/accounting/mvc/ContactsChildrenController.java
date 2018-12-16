package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class ContactsChildrenController extends Controller {
	private List<Option> lstContactTypeOptions = null;
	private List<Option> lstSalesTaxOptions;
	
	public ContactsChildrenController(ControllerInterface controller, String document_keyword, List<Option> contact_type_options, List<Option> sales_tax_options) { 
		super(controller, document_keyword); 
		lstContactTypeOptions = contact_type_options;
		lstSalesTaxOptions = sales_tax_options;
	}
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Contact objModel = (Contact) getModel();

		
		if(objModel.getDisplayName() == null) {
			LiteralTag litDisplayName = new LiteralTag(this, "Child Contact Display Name");
			litDisplayName.setValue("New Contact");
		} else {
			LiteralTag litDisplayName = new LiteralTag(this, "Child Contact Display Name");
			litDisplayName.setValue(objModel.getDisplayName());
		}
		
		ComboTag cboType = new ComboTag(this, "Child", Contact.CONTACT_TYPES_GUID, objModel.getGuid(), objModel);
		cboType.setOptions(lstContactTypeOptions);
		
		TextTag txtGuid = new TextTag(this, "Child", Contact.GUID, objModel.getGuid(), objModel);
		txtGuid.setIsReadOnly(true);

		CheckTag chkIsDeleted = new CheckTag(this, "Child", Contact.IS_DELETED, objModel.getGuid(), objModel);
		TextTag txtDisplay = new TextTag(this, "Child", Contact.DISPLAY_NAME, objModel.getGuid(), objModel);
		CheckTag chkAllowed = new CheckTag(this, "Child", Contact.IS_ALLOWED, objModel.getGuid(), objModel);
		DateTag dtSince = new DateTag(this, "Child", Contact.CONTACT_SINCE, objModel.getGuid(), objModel);
		
		TextTag txtEmail = new TextTag(this, "Child", Contact.EMAIL_ADDRESS, objModel.getGuid(), objModel);
		TextTag txtOffice = new TextTag(this, "Child", Contact.OFFICE_PHONE, objModel.getGuid(), objModel);
		TextTag txtMobile = new TextTag(this, "Child", Contact.MOBILE_PHONE, objModel.getGuid(), objModel);
		TextTag txtHome = new TextTag(this, "Child", Contact.HOME_PHONE, objModel.getGuid(), objModel);
		TextTag txtFax = new TextTag(this, "Child", Contact.FAX_NUMBER, objModel.getGuid(), objModel);
		TextTag txtLine1 = new TextTag(this, "Child", Contact.LINE_1, objModel.getGuid(), objModel);
		TextTag txtLine2 = new TextTag(this, "Child", Contact.LINE_2, objModel.getGuid(), objModel);
		TextTag txtCity = new TextTag(this, "Child", Contact.CITY, objModel.getGuid(), objModel);
		TextTag txtState = new TextTag(this, "Child", Contact.STATE, objModel.getGuid(), objModel);
		TextTag txtZip = new TextTag(this, "Child", Contact.POSTAL_CODE, objModel.getGuid(), objModel);
		TextTag txtCountry = new TextTag(this, "Child", Contact.COUNTRY, objModel.getGuid(), objModel);

		ComboTag cboSalesTax = new ComboTag(this, "Child", Contact.TAX_GROUP_GUID, objModel.getGuid(), objModel);
		cboSalesTax.setOptions(lstSalesTaxOptions);
	}
	public History createHistory() throws Exception { return null; }
	
}
