package net.internetworkconsulting.accounting.mvc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.RegisterEntry;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.accounting.entities.TransactionLine;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class RegisterEntriesController extends Controller {
	private List<TransactionLine> objModel = new LinkedList<>();
	private DateTag dtStarting;
	private DateTag dtEnding;
	private ComboTag tagAccount;
	private ButtonTag btnLoad;
	private ButtonTag btnSave;
	private ButtonTag btnAdd;
	private TextTag tagBegBal;
	private TextTag tagEndBal;

	public RegisterEntriesController(ControllerInterface controller, String document_keyword) {
		super(controller, document_keyword);
	}

	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		objModel = (List<TransactionLine>) model;
		if(objModel == null)
			objModel = new LinkedList<>();
		
		setModel(model);		
		setDocument(new Template(readTemplate("~/templates/RegisterEntries.html"), new HtmlSyntax()));
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		tagAccount = new ComboTag(this, "Account");
		tagAccount.setOptions(Account.loadOptions(getUser().login(), false));
		
		tagBegBal = new TextTag(this, "Beginning Balance");
		tagBegBal.setIsReadOnly(true);

		tagEndBal = new TextTag(this, "Ending Balance");
		tagEndBal.setIsReadOnly(true);
		
		dtStarting = new DateTag(this, "Starting");
		dtEnding = new DateTag(this, "Ending");
		
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			String sAccount = getRequest().getParameter("Account");
			String sStarting = getRequest().getParameter("Starting");
			String sEnding = getRequest().getParameter("Ending");

			if(sGuid != null) {
				TransactionLine objLine = TransactionLine.loadByGuid(getUser().login(), TransactionLine.class, sGuid);				
				Transaction objTran = objLine.loadTransaction(getUser().login(), Transaction.class, false);

				sAccount = objLine.getAccountsGuid();
				sStarting = Statement.convertObjectToString(objTran.getDate(), null);
				sEnding = Statement.convertObjectToString(objTran.getDate(), null);
			}

			if(sAccount != null)
				tagAccount.setValue(sAccount);

			Calendar calStarting = Calendar.getInstance();
			calStarting.add(Calendar.MONTH, -1);

			Calendar calEnding = Calendar.getInstance();

			if(sStarting != null)
				dtStarting.setValue(sStarting);
			else
				dtStarting.setValue(Statement.convertObjectToString(calStarting, null));

			if(sEnding != null)
				dtEnding.setValue(sEnding);
			else
				dtEnding.setValue(Statement.convertObjectToString(calEnding, null));
		}
		
		btnLoad = new ButtonTag(this, "Load");
		btnLoad.addOnClickEvent(new Event() { public void handle() throws Exception { btnLoad_Clicked(); } });
		
		btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_Clicked(); } });
		
		btnAdd = new ButtonTag(this, "Add");
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_Clicked(); } });
		
		for(TransactionLine entry : objModel)
			createController(entry);
		
		if(!getIsPostback() && tagAccount.getValue() != null && dtStarting.getValue() != null && dtEnding.getValue() != null)
			btnLoad_Clicked();
	}
	public History createHistory() throws Exception {
		String sDisplay = "Register";

		return new History(sDisplay, getRequest(), getUser());
	}
	private RegisterEntriesLinesController createController(TransactionLine line) throws Exception {
		RegisterEntriesLinesController relc = new RegisterEntriesLinesController(this, "Row");

		relc.setModel(line);
		relc.setIsDocumentBlock(true);		
		
		return relc;
	}	

	public void btnLoad_Clicked() throws Exception {
		if(tagAccount.getValue() == null) {
			addError("Cannot load!", "No account has been selected!");
			return;
		}

		setModel(TransactionLine.loadByAccountAndDates(getUser().login(), TransactionLine.class, tagAccount.getValue(), dtStarting.getValue(), dtEnding.getValue()));
		objModel = (List<TransactionLine>) getModel();

		List<ControllerInterface> lstRemoves = new LinkedList<>();
		for(ControllerInterface ctrlr : getControls())
			if(ctrlr.getClass().isAssignableFrom(RegisterEntriesLinesController.class))
				lstRemoves.add(ctrlr);
		for(ControllerInterface ci : lstRemoves)
			getControls().remove(ci);

		for(TransactionLine line : objModel) {
			RegisterEntriesLinesController relc = createController(line);
			doCreateControls(relc, false);
			doBeforeUpdate(relc);
			doBeforeHandle(relc);
			doHandleEvents(relc);
		}
	}
	public void btnSave_Clicked() throws Exception {
		for(ControllerInterface ctrlr : getControls())
			if(ctrlr.getClass().isAssignableFrom(RegisterEntriesLinesController.class)) {
				RegisterEntriesLinesController relc = (RegisterEntriesLinesController) ctrlr;
				relc.updateTransaction();
			}

		getUser().login().begin(true);
		try {
			for(TransactionLine line : objModel) {
				Transaction tran = line.loadTransaction(getUser().login(), Transaction.class, false);
				List<TransactionLine> lstLines = tran.loadTransactionLines(getUser().login(), TransactionLine.class, false);
				
				RegisterEntry entry = null;
				try { entry = (RegisterEntry) line.loadRegisterEntries(getUser().login(), RegisterEntry.class, false).get(0); }
				catch(Exception ex) { continue; }
				
				if(line.getReconciliationsGuid() != null)
					continue;

				tran.setSkipDocumentCheck(true);
				if(line.getIsDeleted()) {
					tran.setIsDeleted(true);
					entry.setIsDeleted(true);
					for(TransactionLine tlDelete : lstLines)
						tlDelete.setIsDeleted(true);

					getUser().login().save(RegisterEntry.TABLE_NAME, entry);
					getUser().login().save(TransactionLine.TABLE_NAME, lstLines);
					getUser().login().save(Transaction.TABLE_NAME, tran);
				} else {
					getUser().login().save(Transaction.TABLE_NAME, tran);
					getUser().login().save(TransactionLine.TABLE_NAME, lstLines);
					getUser().login().save(RegisterEntry.TABLE_NAME, entry);
				}
				
			}
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			getUser().logExcpetion(ex, "196c1ce0ef1b4dc19f1ccd54f4f07cb4");
			return;
		}

		String sUrl = String.format("~/incAccounting?App=RegisterEntries&Account=%s&Starting=%s&Ending=%s", tagAccount.getValue(), dtStarting.getValue(), dtEnding.getValue());
		redirect(sUrl);
	}
	public void btnAdd_Clicked() throws Exception {
		if(tagAccount.getValue() == null) {
			addError("Cannot load!", "No account has been selected!");
			return;
		}

		TransactionLine line = RegisterEntry.create(getUser().login(), tagAccount.getValue());
		objModel.add(line);

		RegisterEntriesLinesController relc = createController(line);
		doCreateControls(relc, false);
		doBeforeUpdate(relc);
		doBeforeHandle(relc);
		doHandleEvents(relc);
	}

	public void beforePopulate() throws Exception {
		if(tagAccount.getValue() == null)
			return;

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		BigDecimal dBeginningBalance = Account.getBeginningBalance(getUser().login(), tagAccount.getValue(), dtStarting.getValue());
		tagBegBal.setValue(String.format(sMoneyFormat, dBeginningBalance));
	}
	
	
}
