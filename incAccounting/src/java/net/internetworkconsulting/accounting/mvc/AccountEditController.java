package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.AccountType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class AccountEditController extends Controller {
	public AccountEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/AccountEdit.html"), new HtmlSyntax()));
		
		Account objModel = (Account) model;
		if(!getIsPostback()) {
			// if not a post back, load fresh
			String sGuid = getRequest().getParameter(Account.GUID);
			String sParentGuid = getRequest().getParameter(Account.PARENT_ACCOUNTS_GUID);
			if(sGuid != null)
				// load provided guid
				objModel = Account.loadByGuid(getUser().login(), Account.class, sGuid);
			else {
				// no guid, new
				objModel = new Account();
				objModel.initialize();
				
				if(sParentGuid != null) {
					// if parent, copy and set
					Account objParent = Account.loadByGuid(getUser().login(), Account.class, sParentGuid);
					objModel.setAccountTypesGuid(objParent.getAccountTypesGuid());
					objModel.setParentAccountsGuid(objParent.getGuid());
				}
			}
		}
		setModel(objModel);
		
		TextTag txtGuid = new TextTag(this, Account.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtSegment = new TextTag(this, Account.SEGMENT, objModel);
		
		TextTag txtNumber = new TextTag(this, Account.NUMBER, objModel);
		txtNumber.setIsReadOnly(true);
		
		TextTag txtNestedName = new TextTag(this, Account.NESTED_NAME, objModel);
		txtNestedName.setIsReadOnly(true);

		TextTag txtName = new TextTag(this, Account.NAME, objModel);
		CheckTag chkIsAllowed = new CheckTag(this, Account.IS_ALLOWED, objModel);

		ComboTag cboAccountType = new ComboTag(this, Account.ACCOUNT_TYPES_GUID, objModel);
		cboAccountType.setOptions(AccountType.loadOptions(getUser().login(), true));
		
		ComboTag cboParantAccount = new ComboTag(this, Account.PARENT_ACCOUNTS_GUID, objModel);
		cboParantAccount.setOptions(Account.loadOptions(getUser().login(), true));

		List<Account> lstChildren = objModel.loadChildren(getUser().login(), Account.class, false);
		for(Account child: lstChildren) 
			createController(child);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		ButtonTag btnAdd = new ButtonTag(this, "", "Add", "", "Add Sub-Account");
		if(objModel.getRowState() == RowState.Insert)
			btnAdd.setIsReadOnly(true);
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		ButtonTag btnOpen = new ButtonTag(this, "", "Open Parent", "", "Open Parent");
		if(objModel.getParentAccountsGuid() == null)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	private AccountEditChildController createController(Account child) {
		AccountEditChildController aecc = new AccountEditChildController(this, "Child");
		aecc.setIsDocumentBlock(true);
		aecc.setModel(child);
		return aecc;
	}
	public History createHistory() throws Exception {
		Account objModel = (Account) getModel();
		
		String sDisplay = "New Account";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getNumber();

		return new History(sDisplay, getRequest(), getUser());
	}

	private void btnOpen_OnClick() throws Exception {
		Account objModel = (Account) getModel();
		if(objModel.getParentAccountsGuid() == null)
			redirect("~/incAccounting?App=AccountList");
		else
			redirect("~/incAccounting?App=AccountEdit&GUID=" + objModel.getParentAccountsGuid());
	}
	private void btnSave_OnClick() throws Exception { 
		Account objModel = (Account) getModel();
		try { 
			getUser().login().begin(true);
			getUser().login().save(Account.TABLE_NAME, objModel); 
			getUser().login().save(Account.TABLE_NAME, objModel.loadChildren(getUser().login(), Account.class, false));
			getUser().login().commit(true);
		} 
		catch (Exception ex) {
			getUser().login().rollback(true);
			addError("Save Error", ex.getMessage());
			return;
		}
		redirect("~/incAccounting?App=AccountEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAdd_OnClick() throws Exception {
		Account objModel = (Account) getModel();

		Account accnt = new Account();
		accnt.initialize();
		accnt.setParentAccountsGuid(objModel.getGuid());
		accnt.setAccountTypesGuid(objModel.getAccountTypesGuid());
		objModel.loadChildren(getUser().login(), Account.class, false).add(accnt);
		
		AccountEditChildController controller = createController(accnt);
		doCreateControls(controller, false);
	}
}
