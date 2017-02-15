package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class DepositPrintController extends Controller {
	private Deposit objModel;
	public DepositPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/DepositPrint.html"), new HtmlSyntax()));
		
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
		
		LiteralTag txtGuid = new LiteralTag(this, Deposit.GUID, objModel);
		LiteralTag dtDate = new LiteralTag(this, Deposit.DATE, objModel);
		LiteralTag txtNumber = new LiteralTag(this, Deposit.NUMBER, objModel);
		LiteralTag litItems = new LiteralTag(this, Deposit.ITEMS, objModel);

		LiteralTag litTotal = new LiteralTag(this, Deposit.TOTAL, objModel);
		litTotal.setFormat(sMoneyFormat);
		
		ComboTag cboAccount = new ComboTag(this, Deposit.ACCOUNTS_GUID, objModel);
		cboAccount.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		
		LiteralTag txtAccount = new LiteralTag(this, Deposit.ACCOUNTS_GUID, null);
		Account objAcct = Account.loadByGuid(getUser().login(), Account.class, objModel.getAccountsGuid());
		txtAccount.setValue(objAcct.getNumber() + " " + objAcct.getName());
		
		List<Payment> lstPayments = objModel.loadPaymentSelections(getUser().login(), false);
		for(Payment pay: lstPayments)
			if(pay.getBankDepositsGuid() != null)
				createController(pay);
	}
	public History createHistory() throws Exception {
		String sDisplay = "New Deposit";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getNumber();

		return new History(sDisplay, getRequest(), getUser());
	}
	private void createController(Payment pay) {
		DepositPrintPaymentController depc = new DepositPrintPaymentController(this, "Payment");
		depc.setIsDocumentBlock(true);
		depc.setModel(pay);
		depc.setDeposit(objModel);
	}

	public void beforePopulate() throws Exception {
		objModel.calculate(getUser().login());
	}	
}
