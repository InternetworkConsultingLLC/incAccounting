package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class TransactionEditLineController extends Controller{
	public TransactionEditLineController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception { 
		setDocument(document);
		
		TransactionLine objModel = (TransactionLine) getModel();
		
		CheckTag chkDeleted = new CheckTag(this, "Line", TransactionLine.IS_DELETED, objModel.getGuid(), objModel);
		chkDeleted.setIsReadOnly(bReadOnly);

		ComboTag cboAccount = new ComboTag(this, "Line", TransactionLine.ACCOUNTS_GUID, objModel.getGuid(), objModel);
		cboAccount.setIsReadOnly(bReadOnly);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), false));

		TextAreaTag txtDescription = new TextAreaTag(this, "Line", TransactionLine.DESCRIPTION, objModel.getGuid(), objModel);
		txtDescription.setIsReadOnly(bReadOnly);
		txtDescription.setMaxLength("255");

		ComboTag cboJob = new ComboTag(this, "Line", TransactionLine.JOBS_GUID, objModel.getGuid(), objModel);
		cboJob.setIsReadOnly(bReadOnly);
		cboJob.setOptions(Job.loadOptions(getUser().login(), false));

		ComboTag cboDepartments = new ComboTag(this, "Line", TransactionLine.DEPARTMENTS_GUID, objModel.getGuid(), objModel);
		cboDepartments.setIsReadOnly(bReadOnly);
		cboDepartments.setOptions(Department.loadOptions(getUser().login(), false));

		String sMoneyFormat = "%." + getUser().getSetting(TransactionLine.SETTING_DEBIT_DECIMALS) + "f";
			
		TextTag txtDebits = new TextTag(this, "Line", TransactionLine.DEBIT, objModel.getGuid(), objModel);
		txtDebits.setIsReadOnly(bReadOnly);
		txtDebits.setFormat(sMoneyFormat);
		txtDebits.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
	}
	public History createHistory() throws Exception { return null; }
	
	private boolean bReadOnly = false;
	void setIsReadOnly(boolean value) { bReadOnly = value; }
	
}
