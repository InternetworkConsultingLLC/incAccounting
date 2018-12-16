package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class TransactionsLinesController extends Controller{
	private TextAreaTag txtDescription;
	private final List<Option> lstAccountOptions;
	private final List<Option> lstJobOptions;
	private final List<Option> lstDepartmentOptions;
	
	public TransactionsLinesController(ControllerInterface controller, String document_keyword, List<Option> account_options, List<Option> job_options, List<Option> department_options) { 
		super(controller, document_keyword); 
		
		lstAccountOptions = account_options;
		lstJobOptions = job_options;
		lstDepartmentOptions = department_options;
	}
	public boolean getEnforceSecurity() { return false; }

	public void createControls(Template document, Object model) throws Exception { 
		setDocument(document);
		
		TransactionLine objModel = (TransactionLine) getModel();
		
		CheckTag chkDelete = new CheckTag(this, "Line", TransactionLine.IS_DELETED, objModel.getGuid(), objModel);
		
		ComboTag cboAccount = new ComboTag(this, "Line", TransactionLine.ACCOUNTS_GUID, objModel.getGuid(), objModel);
		cboAccount.setIsReadOnly(bReadOnly);
		cboAccount.setOptions(lstAccountOptions);

		txtDescription = new TextAreaTag(this, "Line", TransactionLine.DESCRIPTION, objModel.getGuid(), objModel);
		txtDescription.setIsReadOnly(bReadOnly);
		txtDescription.setMaxLength("255");

		ComboTag cboJob = new ComboTag(this, "Line", TransactionLine.JOBS_GUID, objModel.getGuid(), objModel);
		cboJob.setIsReadOnly(bReadOnly);
		cboJob.setOptions(lstJobOptions);

		ComboTag cboDepartments = new ComboTag(this, "Line", TransactionLine.DEPARTMENTS_GUID, objModel.getGuid(), objModel);
		cboDepartments.setIsReadOnly(bReadOnly);
		cboDepartments.setOptions(lstDepartmentOptions);
                
        LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));

		String sMoneyFormat = "%." + getUser().getSetting(TransactionLine.SETTING_DEBIT_DECIMALS) + "f";
			
		TextTag txtDebits = new TextTag(this, "Line", TransactionLine.DEBIT, objModel.getGuid(), objModel);
		txtDebits.setIsReadOnly(bReadOnly);
		txtDebits.setFormat(sMoneyFormat);
//		txtDebits.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
	}
	public History createHistory() throws Exception { return null; }
	
	private boolean bReadOnly = false;
	public void setIsReadOnly(boolean value) { bReadOnly = value; }
	public void setFocus() throws Exception { txtDescription.setFocus(); }
	
}
