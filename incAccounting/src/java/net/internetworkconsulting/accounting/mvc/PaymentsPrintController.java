package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.PaymentApplicationSelection;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PaymentsPrintController extends Controller {
	private Payment objModel;
	private ComboTag cboAccount;
	private ComboTag cboType;
	private ComboTag cboContact;
	private ComboTag cboBilingContact;
	public PaymentsPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {		
		objModel = (Payment) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Payment.loadByGuid(getUser().login(), Payment.class, sGuid);
			else {
				objModel = new Payment();
				objModel.initialize(getUser().login());
			}
		}
		setModel(objModel);
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		String sPaymentType = "";
		sPaymentType = objModel.loadPaymentType(getUser().login(), PaymentType.class, !getIsPostback()).loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback()).getName();
		setDocument(new Template(readTemplate("~/templates/PaymentPrint-" + sPaymentType.replace(" ", "") + ".html"), new HtmlSyntax()));
		
		LiteralTag txtGuid = new LiteralTag(this, Payment.GUID, objModel);

		LiteralTag txtOurNumber = new LiteralTag(this, Payment.OUR_NUMBER, objModel);
		LiteralTag dtDate = new LiteralTag(this, Payment.DATE, objModel);
		LiteralTag txtName = new LiteralTag(this, Payment.CONTACTS_DISPLAY_NAME, objModel);
		LiteralTag txtTheirNumber = new LiteralTag(this, Payment.THEIR_NUMBER, objModel);
		
		LiteralTag txtBillName = new LiteralTag(this, Payment.BILLING_ADDRESS_DISPLAY_NAME, objModel);
		LiteralTag txtBillAddress1 = new LiteralTag(this, Payment.BILLING_ADDRESS_LINE_1, objModel);
		LiteralTag txtBillAddress2 = new LiteralTag(this, Payment.BILLING_ADDRESS_LINE_2, objModel);
		LiteralTag txtBillCity = new LiteralTag(this, Payment.BILLING_ADDRESS_CITY, objModel);
		LiteralTag txtBillState = new LiteralTag(this, Payment.BILLING_ADDRESS_STATE, objModel);
		LiteralTag txtBillZip = new LiteralTag(this, Payment.BILLING_ADDRESS_POSTAL_CODE, objModel);
		LiteralTag txtBillCountry = new LiteralTag(this, Payment.BILLING_ADDRESS_COUNTRY, objModel);
		
		LiteralTag litTotal = new LiteralTag(this, Payment.TOTAL, objModel);
		litTotal.setFormat(sMoneyFormat);
					
		LiteralTag litPaymenType = new LiteralTag(this, Payment.PAYMENT_TYPES_GUID, null);
		litPaymenType.setValue(sPaymentType);
		
		LiteralTag litPostedAccount = new LiteralTag(this, Payment.POSTED_ACCOUNTS_GUID, null);
		String sPostAccountsGuid = "";
		if(objModel.getPostedAccountsGuid() != null) {
			Account acct = Account.loadByGuid(getUser().login(), Account.class, objModel.getPostedAccountsGuid());
			sPostAccountsGuid = acct.getNumber() + " " + acct.getName();
		}
		litPostedAccount.setValue(sPostAccountsGuid);
		
		LiteralTag litContact = new LiteralTag(this, Payment.CONTACTS_GUID, null);
		litContact.setValue(objModel.loadContact(getUser().login(), Contact.class, !getIsPostback()).getDisplayName());
		
		LiteralTag litBillingContact = new LiteralTag(this, Payment.BILLING_CONTACTS_GUID, null);
		litContact.setValue(objModel.loadBillingContact(getUser().login(), Contact.class, !getIsPostback()).getDisplayName());
				
		if(objModel.getPaymentTypesGuid() != null) {
			List<PaymentApplicationSelection> lstApplications = objModel.loadPaymentApplicationSelection(getUser().login(), !getIsPostback());
			for(PaymentApplicationSelection app: lstApplications)
				createApplicationController(app);
		}
		
		LiteralTag litAmountAsText = new LiteralTag(this, "Amount as Text");
		litAmountAsText.setValue(objModel.getTotalAsText());				

	}
	public History createHistory() throws Exception {
		String sDisplay = "New Payment";
		if(objModel.getRowState() != RowState.Insert)
			if(objModel.getOurNumber() == null)
				sDisplay = "Payment";
			else
				sDisplay = objModel.getOurNumber();

		return new History(sDisplay, getRequest(), getUser());
	}
	private PaymentsApplicationsPrintController createApplicationController(PaymentApplicationSelection app) {
		PaymentsApplicationsPrintController peac = new PaymentsApplicationsPrintController(this, "Application");
		peac.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		peac.setModel(app);
		peac.setIsDocumentBlock(true);
		peac.setPayment(objModel);
		
		return peac;
	}
	
	private void LoadApplicationSelections() throws Exception {
		this.removeAllControllers(PaymentsApplicationsController.class);
		
		if(objModel.getPaymentTypesGuid() != null && objModel.getContactsGuid() != null) {
			List<PaymentApplicationSelection> lstApplications = objModel.loadPaymentApplicationSelection(getUser().login(), !getIsPostback());
			for(PaymentApplicationSelection app: lstApplications) {
				PaymentsApplicationsPrintController paec = createApplicationController(app);
				doCreateControls(paec, false);
				doBeforeUpdate(paec);
				doUpdateControls(paec);
				doBeforeHandle(paec);
				doHandleEvents(paec);
			}
		}
	}
}
