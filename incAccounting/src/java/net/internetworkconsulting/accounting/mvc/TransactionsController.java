package net.internetworkconsulting.accounting.mvc;

import java.math.BigDecimal;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class TransactionsController  extends EditController{
	private TextTag txtBalance;
	private boolean bReadOnly;
	public TransactionsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	public void handleDeleteRow(String guid) throws Exception {
		throw new Exception("You must open the item and click the 'Delete' button!");
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Transaction.loadByGuid(getUser().login(), Transaction.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Transaction objModel = new Transaction();
		objModel.initialize();
		objModel.setTransactionTypesGuid(TransactionType.TRANSACTION_GUID);
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Transaction objModel = (Transaction) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Transaction.html"), new HtmlSyntax()));
		
		Account.loadOptions(getUser().login(), true);
		Job.loadOptions(getUser().login(), true);
		Department.loadOptions(getUser().login(), true);
		
		bReadOnly = objModel.getTransactionTypesGuid() != null && !objModel.getTransactionTypesGuid().equals(TransactionType.TRANSACTION_GUID);
		if(objModel.isReconciled(getUser().login()))		
			bReadOnly = true;
		
		TextTag txtGuid = new TextTag(this, Transaction.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		DateTag dtDate = new DateTag(this, Transaction.DATE, objModel);
		dtDate.setIsReadOnly(bReadOnly);

		TextTag txtReference = new TextTag(this, Transaction.REFERENCE_NUMBER, objModel);
		txtReference.setIsReadOnly(bReadOnly);
		txtReference.setMaxLength("128");

		ComboTag cboType = new ComboTag(this, Transaction.TRANSACTION_TYPES_GUID, objModel);
		cboType.setIsReadOnly(true);
		cboType.setOptions(TransactionType.loadOptions(getUser().login(), true));

		txtBalance = new TextTag(this, "Balance");
		txtBalance.setIsReadOnly(true);
		txtBalance.setFormat(getUser().getSetting(TransactionLine.SETTING_DEBIT_DECIMALS));
				
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setIsReadOnly(bReadOnly);
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		ButtonTag btnAdd = new ButtonTag(this, "Add");
		btnAdd.setIsReadOnly(bReadOnly);
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		ButtonTag btnOpen = new ButtonTag(this, "Open Document");
		btnOpen.setIsReadOnly(!bReadOnly);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });

		ButtonTag btnDelete = new ButtonTag(this, "Delete");
		btnDelete.setIsReadOnly(bReadOnly);
		btnDelete.addOnClickEvent(new Event() { public void handle() throws Exception { btnDelete_OnClick(); } });

		List<TransactionLine> lstLines = objModel.loadTransactionLines(getUser().login(), TransactionLine.class, !getIsPostback());
		for(TransactionLine line: lstLines)
			createController(line);
	}
	private TransactionsLinesController createController(TransactionLine line) {
		TransactionsLinesController telc = new TransactionsLinesController(this, "Line");
		telc.setModel(line);
		telc.setIsReadOnly(bReadOnly);
		telc.setIsDocumentBlock(true);
		return telc;
	}
	public History createHistory() throws Exception {
		Transaction objModel = (Transaction) getModel();
		
		String sDisplay = "New Transaction";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getReferenceNumber();

		return new History(sDisplay, getRequest(), getUser());
	}
	public void beforePopulate() throws Exception {
		Transaction objModel = (Transaction) getModel();
		BigDecimal dBalance = objModel.calculate(getUser().login());
		String sMoneyFormat = "%." + getUser().getSetting(TransactionLine.SETTING_DEBIT_DECIMALS) + "f";
		txtBalance.setValue(Statement.convertObjectToString(dBalance, sMoneyFormat));
	}
	
	private void btnOpen_OnClick() throws Exception {
		Transaction objModel = (Transaction) getModel();
		TransactionType objType = objModel.loadTransactionType(getUser().login(), TransactionType.class, true);
				
		redirect(objType.getRootUrl() + "&GUID=" + objModel.getGuid());
	}
	private void btnSave_OnClick() throws Exception {
		Transaction objModel = (Transaction) getModel();
		
		try {
			getUser().login().begin(true);
			objModel.handleAutoNumber(getUser().login());
			getUser().login().save(Transaction.TABLE_NAME, objModel);
			getUser().login().save(TransactionLine.TABLE_NAME, objModel.loadTransactionLines(getUser().login(), TransactionLine.class, !getIsPostback()));
			getUser().login().commit(true);			
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "f9f18fdc7c9a45a5b6466cf499628f48");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Transaction&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAdd_OnClick() throws Exception {
		Transaction objModel = (Transaction) getModel();
		
		TransactionLine line = new TransactionLine();
		line.initialize(objModel, getUser().login());
		
		objModel.loadTransactionLines(getUser().login(), TransactionLine.class, !getIsPostback()).add(line);
		
		TransactionsLinesController controller = createController(line);
		doCreateControls(controller, false);
		controller.setFocus();
	}
	private void btnDelete_OnClick() throws Exception {
		Transaction objModel = (Transaction) getModel();
		TransactionType objType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());
		
		try {
			if(objModel.getRowState() != RowState.NA)
				throw new Exception("You can only delete items that are not new and have not been modifited!");
			if(objModel.isReconciled(getUser().login()))
				throw new Exception("You cannot delete an item that has been reconciled!");

			List<TransactionLine> lstLines = objModel.loadTransactionLines(getUser().login(), TransactionLine.class , !getIsPostback());
			for(TransactionLine objLine : lstLines)
				objLine.setIsDeleted(true);

			objModel.setIsDeleted(true);
			
			getUser().login().begin(true);
			getUser().login().save(TransactionLine.TABLE_NAME, objModel.loadTransactionLines(getUser().login(), TransactionLine.class, !getIsPostback()));
			getUser().login().save(Transaction.TABLE_NAME, objModel);
			getUser().login().commit(true);			
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4b83eb064b1946ca8de60b53414704ad");
			addError("Delete", ex.getMessage());
			return;
		}

		redirect(objType.getListUrl());
	}
}
