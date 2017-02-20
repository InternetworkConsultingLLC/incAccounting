package net.internetworkconsulting.accounting.mvc;

import java.util.HashSet;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Reconciliation;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReconciliationEditController extends Controller {
	private TextTag litBeginningBalance;
	private TextTag litCleared;
	private TextTag litEndingBalance;
	private TextTag litUncleared;
	private TextTag litLedgerBalance;
	private DateTag dtDate;
	private ComboTag cboAccount;
	public ReconciliationEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/ReconciliationEdit.html"), new HtmlSyntax()));

		String sFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		
		Reconciliation objModel = (Reconciliation) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Reconciliation.loadByGuid(getUser().login(), Reconciliation.class, sGuid);
			else {
				objModel = new Reconciliation();
				objModel.initialize();
			}
		}
		setModel(objModel);
		
		TextTag txtGuid = new TextTag(this, Reconciliation.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		dtDate = new DateTag(this, Reconciliation.DATE, objModel);
		dtDate.addOnChangeEvent(new Event() { public void handle() throws Exception { dtDate_OnChange(); } });
		
		cboAccount = new ComboTag(this, Reconciliation.ACCOUNTS_GUID, objModel);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		cboAccount.addOnChangeEvent(new Event() { public void handle() throws Exception { cboAccount_OnChange(); } });
				
		litBeginningBalance = new TextTag(this, "Beginning Balance");
		litBeginningBalance.setFormat(sFormat);
		litBeginningBalance.setIsReadOnly(true);
		
		litCleared = new TextTag(this, "Cleared");
		litCleared.setFormat(sFormat);
		litCleared.setIsReadOnly(true);
		
		litEndingBalance = new TextTag(this, "Ending Balance");
		litEndingBalance.setFormat(sFormat);
		litEndingBalance.setIsReadOnly(true);
		
		litUncleared = new TextTag(this, "Uncleared");
		litUncleared.setFormat(sFormat);
		litUncleared.setIsReadOnly(true);
		
		litLedgerBalance = new TextTag(this, "Ledger Balance");
		litLedgerBalance.setFormat(sFormat);
		litLedgerBalance.setIsReadOnly(true);
			
		TextTag txtStatementBalance = new TextTag(this, Reconciliation.STATEMENT_ENDING_BALANCE, objModel);
		txtStatementBalance.setFormat(sFormat);
		txtStatementBalance.addOnChangeEvent(new Event() { public void handle() throws Exception { } });

		TextTag litOffBy = new TextTag(this, Reconciliation.OFF_BY, objModel);
		litOffBy.setFormat(sFormat);
		litOffBy.setIsReadOnly(true);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		if(objModel.getAccountsGuid() != null) {		
			List<TransactionLine> lstLines = objModel.loadTransactionLines(getUser().login(), TransactionLine.class, false);
			for(TransactionLine line: lstLines)
				createController(line);
		}
		
		if(objModel.getRowState() != RowInterface.RowState.Insert) {
			cboAccount.setIsReadOnly(true);
			dtDate.setIsReadOnly(true);
		}
	}
	private ReconciliationEditLineController createController(TransactionLine line) {
		Reconciliation objModel = (Reconciliation) getModel();
		ReconciliationEditLineController relc = new ReconciliationEditLineController(this, "Line");
		relc.setModel(line);
		relc.setIsDocumentBlock(true);
		relc.setReconciliation(objModel);
		
		return relc;
	}
	public History createHistory() throws Exception {
		Reconciliation objModel = (Reconciliation) getModel();

		String sDisplay = "New Reconciliation";
		if(objModel.getRowState() != RowInterface.RowState.Insert)
			sDisplay = objModel.getDate().toString();

		return new History(sDisplay, getRequest(), getUser());
	}

	private void btnSave_OnClick() throws Exception {
		Reconciliation objModel = (Reconciliation) getModel();

		try {
			getUser().login().begin(true);
			getUser().login().save(Reconciliation.TABLE_NAME, objModel);
			getUser().login().save(TransactionLine.TABLE_NAME, objModel.loadTransactionLines(getUser().login(), TransactionLine.class, false));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4f61c614a05e4c74b55c41153361eda2");
			addError("Save", ex.getMessage());
			return;
		}

		redirect("~/incAccounting?App=ReconciliationEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void cboAccount_OnChange() throws Exception { reloadLines(); }
	private void dtDate_OnChange() throws Exception { reloadLines(); }
	private void reloadLines() throws Exception {
		this.removeAllControllers(ReconciliationEditLineController.class);
		
		Reconciliation objModel = (Reconciliation) getModel();
		List<TransactionLine> lstLines = objModel.loadTransactionLines(getUser().login(), TransactionLine.class, true);
				
		for(TransactionLine line: lstLines) {
			ReconciliationEditLineController relc =  createController(line);
			doCreateControls(relc, false);
			doBeforeUpdate(relc);
			doUpdateControls(relc);
			doBeforeHandle(relc);
			doHandleEvents(relc);
		}
	}
	
	public void beforePopulate() throws Exception {
		Reconciliation objModel = (Reconciliation) getModel();

		String sFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		
		litBeginningBalance.setValue(Statement.convertObjectToString(objModel.calculateBeginningBalance(getUser().login()), sFormat));
		litCleared.setValue(Statement.convertObjectToString(objModel.calculateCleared(getUser().login()), sFormat));
		litEndingBalance.setValue(Statement.convertObjectToString(objModel.calculateEndingBalance(getUser().login()), sFormat));
		litLedgerBalance.setValue(Statement.convertObjectToString(objModel.calculateLedgerBalance(getUser().login()), sFormat));
		litUncleared.setValue(Statement.convertObjectToString(objModel.calculateUncleared(getUser().login()), sFormat));
		objModel.calculateBalanceOffBy(getUser().login());
		
		super.beforePopulate();
	}
}
