package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Reconciliation;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class ReconciliationsLinesController extends Controller {
	private CheckTag chkCleared;
	private Reconciliation objReconciliation;

	public ReconciliationsLinesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	void setReconciliation(Reconciliation value) { objReconciliation = value; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		TransactionLine objModel = (TransactionLine) getModel();
		Transaction objTran = objModel.loadTransaction(getUser().login(), Transaction.class, false);
		TransactionType objType = objTran.loadTransactionType(getUser().login(), TransactionType.class, false);

		String sFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		
		chkCleared = new CheckTag(this, "Line Cleared");
		chkCleared.setName("Cleared" + objModel.getGuid());
		chkCleared.addOnChangeEvent(new Event() { public void handle() throws Exception { chkCleared_OnChange(); } });
		if(!getIsPostback()) {
			if(objModel.getReconciliationsGuid() == null)
				chkCleared.setIsChecked(false);
			else
				chkCleared.setIsChecked(true);
		}
				
		TextTag litDate = new TextTag(this, "Line", Transaction.DATE, objModel.getGuid(), objTran);
		litDate.setIsReadOnly(true);
		TextTag litRef = new TextTag(this, "Line", Transaction.REFERENCE_NUMBER, objModel.getGuid(), objTran);
		litRef.setIsReadOnly(true);
		TextTag litType = new TextTag(this, "Line", TransactionType.NAME, objModel.getGuid(), objType);
		litType.setIsReadOnly(true);
		TextAreaTag litDescription = new TextAreaTag(this, "Line", TransactionLine.DESCRIPTION, objModel.getGuid(), objModel);
		litDescription.setIsReadOnly(true);

		TextTag litDebit = new TextTag(this, "Line", TransactionLine.DEBIT, objModel.getGuid(), objModel);
		litDebit.setIsReadOnly(true);
		litDebit.setFormat(sFormat);
	}
	public History createHistory() throws Exception { return null; }
	
	public void chkCleared_OnChange() throws Exception {
		TransactionLine objModel = (TransactionLine) getModel();
		
		if(chkCleared.getIsChecked())
			objModel.setReconciliationsGuid(objReconciliation.getGuid());
		else
			objModel.setReconciliationsGuid(null);
	}
}
