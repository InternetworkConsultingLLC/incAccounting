package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class DepositEditController extends Controller {
	private Deposit objModel;
	public DepositEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/DepositEdit.html"), new HtmlSyntax()));
		
		objModel = (Deposit) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Deposit.loadByGuid(getUser().login(), Deposit.class, sGuid);
			else {
				objModel = new Deposit();
				objModel.initialize();
			}
		}
		setModel(objModel);

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		TextTag txtGuid = new TextTag(this, Deposit.GUID, objModel);
		txtGuid.setIsReadOnly(true);
		
		DateTag dtDate = new DateTag(this, Deposit.DATE, objModel);
		dtDate.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtNumber = new TextTag(this, Deposit.NUMBER, objModel);
		txtNumber.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ComboTag cboAccount = new ComboTag(this, Deposit.ACCOUNTS_GUID, objModel);
		cboAccount.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		
		TextTag litItems = new TextTag(this, Deposit.ITEMS, objModel);
		litItems.setIsReadOnly(true);
		TextTag litTotal = new TextTag(this, Deposit.TOTAL, objModel);
		litTotal.setIsReadOnly(true);
		litTotal.setFormat(sMoneyFormat);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

		ButtonTag btnPrint = new ButtonTag(this, "Print");
		btnPrint.setIsReadOnly(objModel.getRowState() != RowState.NA);
		btnPrint.addOnClickEvent(new Event() { public void handle() throws Exception { btnPrint_OnClick(); } });
		
		ButtonTag btnPost = new ButtonTag(this, "Post");
		if(objModel.getRowState() == RowState.Insert)
			btnPost.setIsReadOnly(true);
		else if(objModel.getPostedTransactionsGuid() != null)
			btnPost.setValue("Unpost");
		btnPost.addOnClickEvent(new Event() { public void handle() throws Exception { btnPost_OnClick(); } });

		ButtonTag btnOpen = new ButtonTag(this, "Open");
		btnOpen.setValue("Open Transaction");
		btnOpen.setIsReadOnly(objModel.getPostedTransactionsGuid() == null);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		
		List<Payment> lstPayments = objModel.loadPaymentSelections(getUser().login(), false);
		for(Payment pay: lstPayments)
			createController(pay);
	}
	public History createHistory() throws Exception {
		String sDisplay = "New Deposit";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getNumber();

		return new History(sDisplay, getRequest(), getUser());
	}
	private void createController(Payment pay) {
		DepositEditPaymentController depc = new DepositEditPaymentController(this, "Payment");
		depc.setIsDocumentBlock(true);
		depc.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		depc.setModel(pay);
		depc.setDeposit(objModel);
	}

	private void btnSave_OnClick() throws Exception {
		try {
			getUser().login().begin(true);
			
			getUser().login().save(Deposit.TABLE_NAME, objModel);
			
			List<Payment> lstPayments = objModel.loadPaymentSelections(getUser().login(), false);
			for(Payment payment: lstPayments)
				payment.setSkipTransactionCheck(true);			
			getUser().login().save(Payment.TABLE_NAME, lstPayments);
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "668ed707c70b48648a25afa04dd5b4c8");
			addError("Save", ex.toString());
			return;
		}
		
		redirect("~/incAccounting?App=DepositEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnPost_OnClick() throws Exception { 
		try {
			getUser().login().begin(true);
			
			if(objModel.getPostedTransactionsGuid() == null)
				objModel.post(getUser().login());
			else
				objModel.unpost(getUser().login());
			
			getUser().login().commit(true);			
		}
		catch(Exception ex) {
			getUser().login().rollback(true);			
			getUser().logExcpetion(ex, "71ad5c15458344b5bef1637c02a81be6");
			addError("Post", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=DepositEdit&GUID=" + objModel.getGuid() + "&Error=Posted!");
	}
	private void btnPrint_OnClick() throws Exception {
		Deposit objModel = (Deposit) getModel();
		redirect("~/incAccounting?App=DepositPrint&GUID=" + objModel.getGuid());
	}
	private void btnOpen_OnClick() throws Exception {
		Deposit objModel = (Deposit) getModel();
		redirect("~/incAccounting?App=TransactionEdit&GUID=" + objModel.getGuid());
	}

	public void beforePopulate() throws Exception {
		objModel.calculate(getUser().login());
	}	
}
