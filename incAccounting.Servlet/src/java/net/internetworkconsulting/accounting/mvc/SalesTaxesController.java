package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.SalesTax;
import net.internetworkconsulting.accounting.entities.SalesTaxMembership;
import net.internetworkconsulting.accounting.entities.SalesTaxMembershipOption;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.data.pervasive.Statement;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class SalesTaxesController extends EditController {
	private List<Option> lstAccountOptions;
	private List<Option> lstContactOptions;
	public SalesTaxesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		SalesTax objModel = SalesTax.loadByGuid(getUser().login(), SalesTax.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(SalesTax.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return SalesTax.loadByGuid(getUser().login(), SalesTax.class, guid);
	}
	public Object handleNewRow() throws Exception {
		SalesTax objModel = new SalesTax();
		objModel.initialize();
		
		String sGroup = getRequest().getParameter(SalesTax.IS_GROUP);
		if(sGroup != null) {
			boolean bGroup = (boolean) Statement.parseStringToValue(boolean.class, sGroup);
			objModel.setIsGroup(bGroup);
		}

		return objModel;		
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		SalesTax objModel = (SalesTax) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/SalesTax.html"), new HtmlSyntax()));
		
		lstAccountOptions = Account.loadOptions(getUser().login());
		lstContactOptions = Contact.loadOptions(getUser().login());
		
		TextTag txtGuid = new TextTag(this, SalesTax.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtDisplayName = new TextTag(this, SalesTax.DISPLAY_NAME, objModel);
		TextTag txtTaxRate = new TextTag(this, SalesTax.TAX_RATE, objModel);
		
		ComboTag cboTaxAccount = new ComboTag(this, SalesTax.ACCOUNTS_GUID, objModel);
		cboTaxAccount.setOptions(lstAccountOptions);
		
		ComboTag cboContacts = new ComboTag(this, SalesTax.CONTACTS_GUID, objModel);
		cboContacts.setOptions(lstContactOptions);
		
		CheckTag chkIsGroup = new CheckTag(this, SalesTax.IS_GROUP, objModel);
		chkIsGroup.addOnChangeEvent(new Event() { public void handle() throws Exception { chkIsGroup_OnChange(); } });		
		if(objModel.getRowState() == RowState.Insert)
			chkIsGroup.setIsReadOnly(false);
		else
			chkIsGroup.setIsReadOnly(true);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		List<SalesTaxMembershipOption> lstOptions = objModel.loadMembershipOptions(getUser().login(), !getIsPostback());
		for(SalesTaxMembershipOption option: lstOptions)
			createController(option);
	}
	public History createHistory() throws Exception {
		SalesTax objModel = (SalesTax) getModel();
		
		String sDisplay = "New Sales Tax";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	private SalesTaxesOptionsController createController(SalesTaxMembershipOption option) {
		SalesTaxesOptionsController steoc = new SalesTaxesOptionsController(this, "Option");
		steoc.setModel(option);
		steoc.setIsDocumentBlock(true);
		
		return steoc;
	}
	
	public void btnSave_OnClick() throws Exception {
		SalesTax objModel = (SalesTax) getModel();
		
		try {
			getUser().login().begin(true);
			getUser().login().save(SalesTax.TABLE_NAME, objModel);
			getUser().login().save(SalesTaxMembership.TABLE_NAME, objModel.loadMembershipsFromOptions(getUser().login()));
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4f61c614a05e4c74b55c41153361eda2");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=SalesTax&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	public void chkIsGroup_OnChange() throws Exception {
		SalesTax objModel = (SalesTax) getModel();

		if(objModel.getIsGroup()) {
		} else {
		}
	}

	public void beforePopulate() throws Exception {
		SalesTax objModel = (SalesTax) getModel();
		objModel.calculateTaxRate(getUser().login());
	}	
	public void populateDocument() throws Exception {
		SalesTax objModel = (SalesTax) getModel();
	
		if(objModel.getIsGroup()) {
			getDocument().touch("Group");
			getDocument().parse("Group");
		} else {
			getDocument().touch("Authority");
			getDocument().parse("Authority");
		}
		
		super.populateDocument();
	}
	
	
}
