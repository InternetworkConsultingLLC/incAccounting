package net.internetworkconsulting.accounting.mvc;

import java.math.BigDecimal;
import java.util.List;
import net.internetworkconsulting.accounting.data.ContactsRow;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.RegisterEntry;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class RegisterEntriesLinesController extends Controller {
	private TransactionLine objModel = null;
	private TransactionLine objExpense = null;
	private RegisterEntry objEntry = null;
	private Transaction objTransaction = null;
	private TransactionType objTransactionType = null;
	private ContactsRow objContact = null;
	private List<TransactionLine> lstTransactionLines;

	public RegisterEntriesLinesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public History createHistory() throws Exception { return null; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		objModel = (TransactionLine) getModel();
		objTransaction = objModel.loadTransaction(getUser().login(), Transaction.class, false);
		lstTransactionLines = objTransaction.loadTransactionLines(getUser().login(), TransactionLine.class, false);
		objTransactionType = objTransaction.loadTransactionType(getUser().login(), TransactionType.class, false);
		try { 
			objEntry = (RegisterEntry) objModel.loadRegisterEntries(getUser().login(), RegisterEntry.class, false).get(0);
			
			if(lstTransactionLines.size() != 2)
				throw new Exception("We encountered a Register entry with more than two transction lines (GUID " + objModel.getGuid() + ")!");
			
			for(TransactionLine line : lstTransactionLines)
				if(!line.getGuid().equals(objModel.getGuid()))
					objExpense = line;

			objContact = objEntry.loadContact(getUser().login(), Contact.class, false);
		}
		catch(Exception ex) { /* do nothing */ }

		boolean bReadonly = objEntry == null || objModel.getReconciliationsGuid() != null;
		if(!bReadonly) {
			LabelTag tagDeleteLabel = new LabelTag(this, "Row Delete Label");
			tagDeleteLabel.setValue("Delete:");

			CheckTag tagDelete = new CheckTag(this, "Row", TransactionLine.IS_DELETED, objModel.getGuid(), objModel);
		} else {
			LabelTag tagDeleteLabel = new LabelTag(this, "Row Delete Blank");
			tagDeleteLabel.setValue("blank");
		}
		
		DateTag tagDate = new DateTag(this, "Row", Transaction.DATE, objModel.getGuid(), objTransaction);
		tagDate.setIsReadOnly(bReadonly);
		
		TextTag tagNumber = new TextTag(this, "Row", Transaction.REFERENCE_NUMBER, objModel.getGuid(), objTransaction);
		tagNumber.setIsReadOnly(bReadonly);
				
		if(objEntry != null) {
			TextTag tagDescription = new TextTag(this, "Row", TransactionLine.DESCRIPTION, objModel.getGuid(), objExpense);
			tagDescription.setIsReadOnly(bReadonly);

			// not an entry - show expense account
			LabelTag label = new LabelTag(this, "Row Type Label");
			label.setValue("Expense");
			
			ComboTag tag = new ComboTag(this, "Row Type Combo");
			tag.setName("RowTypeCombo" + objModel.getGuid());
			tag.bind(objExpense, TransactionLine.ACCOUNTS_GUID);
			tag.setOptions(Account.loadOptions(getUser().login(), false));
			tag.setIsReadOnly(bReadonly);
			
			ComboTag tagContact = new ComboTag(this, "Row", RegisterEntry.CONTACTS_GUID, objModel.getGuid(), objEntry);
			tagContact.setOptions(Contact.loadOptions(getUser().login(), false));
			tagContact.setIsReadOnly(bReadonly);

			ComboTag tagDepartment = new ComboTag(this, "Row", TransactionLine.DEPARTMENTS_GUID, objModel.getGuid(), objExpense);
			tagDepartment.setOptions(Department.loadOptions(getUser().login(), false));
			tagDepartment.setIsReadOnly(bReadonly);

			ComboTag tagJob = new ComboTag(this, "Row", TransactionLine.JOBS_GUID, objModel.getGuid(), objExpense);
			tagJob.setOptions(Job.loadOptions(getUser().login(), false));
			tagJob.setIsReadOnly(bReadonly);
			
		} else {
			TextTag tagDescription = new TextTag(this, "Row", TransactionLine.DESCRIPTION, objModel.getGuid(), objModel);
			tagDescription.setIsReadOnly(bReadonly);

			// not an entry - show transaction type
			LabelTag label = new LabelTag(this, "Row Type Label");
			label.setValue("Type");
			
			ComboTag tag = new ComboTag(this, "Row Type Combo");
			tag.setName("RowTypeCombo" + objModel.getGuid());
			tag.bind(objTransaction, Transaction.TRANSACTION_TYPES_GUID);
			tag.setOptions(TransactionType.loadOptions(getUser().login(), false));
			tag.setIsReadOnly(bReadonly);
			
			ComboTag tagContact = new ComboTag(this, "Row " + RegisterEntry.CONTACTS_GUID);
			tagContact.setIsReadOnly(bReadonly);

			ComboTag tagDepartment = new ComboTag(this, "Row", TransactionLine.DEPARTMENTS_GUID, objModel.getGuid(), objModel);
			tagDepartment.setOptions(Department.loadOptions(getUser().login(), false));
			tagDepartment.setIsReadOnly(bReadonly);

			ComboTag tagJob = new ComboTag(this, "Row", TransactionLine.JOBS_GUID, objModel.getGuid(), objModel);
			tagJob.setOptions(Job.loadOptions(getUser().login(), false));
			tagJob.setIsReadOnly(bReadonly);
		}
		
		TextTag tagDebit = new TextTag(this, "Row", TransactionLine.DEBIT, objModel.getGuid(), objModel);
		tagDebit.setFormat(sMoneyFormat);
		tagDebit.setIsReadOnly(bReadonly);		

		TextTag tagBalance = new TextTag(this, "Row", "Balance", objModel.getGuid(), objModel);
		tagBalance.setFormat(sMoneyFormat);
		tagBalance.setIsReadOnly(true);		
	}
	void updateTransaction() throws Exception {
		if(objEntry == null)
			return;
		
		if(objModel.getDebit().compareTo(BigDecimal.ZERO) == 0)
			throw new Exception("A register entry cannot have a $0.00 amount!");
		
		objExpense.setDebit(objModel.getDebit().multiply(new BigDecimal(-1)));
		objModel.setDescription(objExpense.getDescription());
	}
}
