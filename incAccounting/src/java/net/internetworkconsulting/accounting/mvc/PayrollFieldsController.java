package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.PayrollField;
import net.internetworkconsulting.accounting.entities.PayrollFieldType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PayrollFieldsController extends EditController {
	private PayrollField objModel;
	private ComboTag cboCreditAccount;
	private ComboTag cboDebitAccount;
	public PayrollFieldsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		 objModel = PayrollField.loadByGuid(getUser().login(), PayrollField.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(PayrollField.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return PayrollField.loadByGuid(getUser().login(), PayrollField.class, guid);
	}
	public Object handleNewRow() throws Exception {
		objModel = new PayrollField();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		objModel = (PayrollField) handleNonPostbackActions(model);
		setDocument(new Template(read_url("~/templates/PayrollField.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, PayrollField.GUID, objModel);
		txtGuid.setIsReadOnly(true);
		
		TextTag txtName = new TextTag(this, PayrollField.NAME, objModel);
		
		ComboTag cboFieldType = new ComboTag(this, PayrollField.PAYROLL_FIELD_TYPES_GUID, objModel);
		cboFieldType.setOptions(PayrollFieldType.loadOptions(getUser().login(), true));
		cboFieldType.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
		
		cboDebitAccount = new ComboTag(this, PayrollField.DEBIT_ACCOUNTS_GUID, objModel);
		cboDebitAccount.setOptions(Account.loadOptions(getUser().login(), true));
		
		cboCreditAccount = new ComboTag(this, PayrollField.CREDIT_ACCOUNTS_GUID, objModel);
		cboCreditAccount.setOptions(Account.loadOptions(getUser().login(), false));
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public void beforePopulate() throws Exception {
		if(objModel.getPayrollFieldTypesGuid() == null) {
			cboCreditAccount.setIsReadOnly(true);
			cboCreditAccount.setValue(null);
			cboDebitAccount.setIsReadOnly(true);
			cboDebitAccount.setValue(null);
		} else if(objModel.getPayrollFieldTypesGuid().equals(PayrollFieldType.TYPE_GROSS_EXPENSE_GUID)) {
			cboCreditAccount.setIsReadOnly(true);
			cboCreditAccount.setValue(null);
			cboDebitAccount.setIsReadOnly(false);
		} else if(objModel.getPayrollFieldTypesGuid().equals(PayrollFieldType.TYPE_COMPANY_PAID_GUID)) {
			cboCreditAccount.setIsReadOnly(false);
			cboDebitAccount.setIsReadOnly(false);
		} else if(objModel.getPayrollFieldTypesGuid().equals(PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID)) {
			cboCreditAccount.setIsReadOnly(false);
			cboDebitAccount.setIsReadOnly(true);
			cboDebitAccount.setValue(null);
		}			
	}
	public History createHistory() throws Exception {
		String sDisplay = "New Payroll Field";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnSave_OnClick() throws Exception {
		try {
			getUser().login().begin(true);
			getUser().login().save(PayrollField.TABLE_NAME, objModel);
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "d70e1cbad5254e56a41c3e166572d616");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=PayrollField&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	
}
