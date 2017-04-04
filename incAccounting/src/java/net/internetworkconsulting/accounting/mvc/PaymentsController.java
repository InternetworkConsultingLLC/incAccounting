package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.PaymentApplicationSelection;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PaymentsController extends EditController {
	private Payment objModel;
	private ComboTag cboAccount;
	private ComboTag cboType;
	private ComboTag cboContact;
	private ComboTag cboBilingContact;
	public PaymentsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		objModel = Payment.loadByGuid(getUser().login(), Payment.class, guid);
		objModel.setIsDeleted(true);
		
		Document objDocument = objModel.loadPrepaymentDocument(getUser().login(), false);
		objDocument.setIsDeleted(true);
		
		getUser().login().save(Payment.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		Payment objModel = Payment.loadByGuid(getUser().login(), Payment.class, guid);
		Document docPrepayment = objModel.loadPrepaymentDocument(getUser().login(), false);
		
		return objModel;
	}
	public Object handleNewRow() throws Exception {
		objModel = new Payment();
		objModel.initialize(getUser().login());
		
		String sPaymentTypesGuid = getRequest().getParameter(Payment.PAYMENT_TYPES_GUID);
		if(sPaymentTypesGuid != null) {
			objModel.setPaymentTypesGuid(sPaymentTypesGuid);
			objModel.handlePaymentType(getUser().login());
		}
		
		Document docPrepayment = objModel.loadPrepaymentDocument(getUser().login(), false);

		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		objModel = (Payment) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Payment.html"), new HtmlSyntax()));
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";

		LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		LiteralTag litRateDecimals = new LiteralTag(this, "Rate Decimals");
		litRateDecimals.setValue(getUser().getSetting(Document.SETTING_RATE_DECIMALS));
				
		LiteralTag litQuantityDecimals = new LiteralTag(this, "Quantity Decimals");
		litQuantityDecimals.setValue(getUser().getSetting(Document.SETTING_QUANITY_DECIMALS));
		
		TextTag txtGuid = new TextTag(this, Payment.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtOurNumber = new TextTag(this, Payment.OUR_NUMBER, objModel);
		txtOurNumber.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		DateTag dtDate = new DateTag(this, Payment.DATE, objModel);
		dtDate.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtName = new TextTag(this, Payment.CONTACTS_DISPLAY_NAME, objModel);
		txtName.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtTheirNumber = new TextTag(this, Payment.THEIR_NUMBER, objModel);
		txtTheirNumber.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtBillName = new TextTag(this, Payment.BILLING_ADDRESS_DISPLAY_NAME, objModel);
		txtBillName.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillAddress1 = new TextTag(this, Payment.BILLING_ADDRESS_LINE_1, objModel);
		txtBillAddress1.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillAddress2 = new TextTag(this, Payment.BILLING_ADDRESS_LINE_2, objModel);
		txtBillAddress2.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillCity = new TextTag(this, Payment.BILLING_ADDRESS_CITY, objModel);
		txtBillCity.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillState = new TextTag(this, Payment.BILLING_ADDRESS_STATE, objModel);
		txtBillState.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillZip = new TextTag(this, Payment.BILLING_ADDRESS_POSTAL_CODE, objModel);
		txtBillZip.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		TextTag txtBillCountry = new TextTag(this, Payment.BILLING_ADDRESS_COUNTRY, objModel);
		txtBillCountry.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag litTotal = new TextTag(this, Payment.TOTAL, objModel);
		litTotal.setFormat(sMoneyFormat);
		litTotal.setIsReadOnly(true);
		
		cboType = new ComboTag(this, Payment.PAYMENT_TYPES_GUID, objModel);
		cboType.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboType.setOptions(PaymentType.loadOptions(getUser().login(), true));
		cboType.addOnChangeEvent(new Event() { public void handle() throws Exception { cboType_OnChange(); } });
		
		cboAccount = new ComboTag(this, Payment.POSTED_ACCOUNTS_GUID, objModel);
		cboAccount.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		
		cboContact = new ComboTag(this, Payment.CONTACTS_GUID, objModel);
		cboContact.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboContact.setOptions(Contact.loadOptions(getUser().login(), true));
		cboContact.addOnChangeEvent(new Event() { public void handle() throws Exception { cboContact_OnChange(); } });
		
		cboBilingContact = new ComboTag(this, Payment.BILLING_CONTACTS_GUID, objModel);
		cboBilingContact.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		cboBilingContact.addOnChangeEvent(new Event() { public void handle() throws Exception { cboBilingContact_OnChange(); } });
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClicked(); } });

		if(objModel.getOurNumber() == null || objModel.getOurNumber().isEmpty()) {
			ButtonTag btnNumber = new ButtonTag(this, "Number");
			btnNumber.addOnClickEvent(new Event() { public void handle() throws Exception { btnNumber_OnClicked(); } });			
		}
			
		
		if(objModel.getRowState() == RowState.NA && objModel.getOurNumber() != null && !objModel.getOurNumber().isEmpty()) {
			ButtonTag btnPost = new ButtonTag(this, "Post");
			btnPost.addOnClickEvent(new Event() { public void handle() throws Exception { btnPost_OnClicked(); } });
			if(objModel.getRowState() == RowState.Insert )
				btnPost.setIsReadOnly(true);
			else if(objModel.getPostedTransactionsGuid() != null)
				btnPost.setValue("Unpost");
		}

		if(objModel.getPostedTransactionsGuid() != null) {
			ButtonTag btnOpen = new ButtonTag(this, "Open Transaction");
			btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClicked(); } });
		}

		if(objModel.getOurNumber() != null && !objModel.getOurNumber().isEmpty() && objModel.getRowState() == RowState.NA) {
			ButtonTag btnPrint = new ButtonTag(this, "Print");
			btnPrint.addOnClickEvent(new Event() { public void handle() throws Exception { btnPrint_OnClicked(); } });
		}		
		
		if(objModel.getPaymentTypesGuid() != null) {
			List<PaymentApplicationSelection> lstApplications = objModel.loadPaymentApplicationSelection(getUser().login(), false);
			for(PaymentApplicationSelection app: lstApplications)
				createApplicationController(app);
		}
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
	private PaymentsApplicationsController createApplicationController(PaymentApplicationSelection app) {
		PaymentsApplicationsController peac = new PaymentsApplicationsController(this, "Application");
		peac.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		peac.setModel(app);
		peac.setIsDocumentBlock(true);
		peac.setPayment(objModel);
		
		return peac;
	}
	
	private void cboType_OnChange() throws Exception {
		objModel.handlePaymentType(getUser().login());
		LoadApplicationSelections();
	}
	private void cboContact_OnChange() throws Exception {		
		objModel.handleContact(getUser().login());
		
		objModel.setBillingContactsGuid(objModel.getContactsGuid());
		objModel.handleBillingContact(getUser().login());
		
		LoadApplicationSelections();
	}
	private void LoadApplicationSelections() throws Exception {
		this.removeAllControllers(PaymentsApplicationsController.class);
		
		if(objModel.getPaymentTypesGuid() != null && objModel.getContactsGuid() != null) {
			List<PaymentApplicationSelection> lstApplications = objModel.loadPaymentApplicationSelection(getUser().login(), false);
			for(PaymentApplicationSelection app: lstApplications) {
				PaymentsApplicationsController paec = createApplicationController(app);
				doCreateControls(paec, false);
				doBeforeUpdate(paec);
				doUpdateControls(paec);
				doBeforeHandle(paec);
				doHandleEvents(paec);
			}
		}
	}

	private void cboBilingContact_OnChange() throws Exception {
		objModel.handleBillingContact(getUser().login());
	}
	
	public void btnPost_OnClicked() throws Exception {
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
			getUser().logExcpetion(ex, "38ad72e5d94545d5bc94f9d717b91607");
			addError("Post", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Payment&GUID=" + objModel.getGuid() + "&Error=Posted!");
	}
	public void btnSave_OnClicked() throws Exception {
		try {
			getUser().login().begin(true);
			
			objModel.calculate(getUser().login());
			
			getUser().login().save(Payment.TABLE_NAME, objModel);
			getUser().login().save(Document.TABLE_NAME, objModel.loadPrepaymentDocument(getUser().login(), false));
			
			objModel.savePaymentApplicationSelections(getUser().login());
					
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "38ad72e5d94545d5bc94f9d717b91607");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Payment&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	public void btnOpen_OnClicked() throws Exception {
		redirect("~/incAccounting?App=Transaction&GUID=" + objModel.getPostedTransactionsGuid());
	}
	public void btnPrint_OnClicked() throws Exception {
		redirect("~/incAccounting?App=PaymentPrint&GUID=" + objModel.getGuid());
	}
	public void btnNumber_OnClicked() throws Exception {
		try {
			getUser().login().begin(true);
			
			objModel.calculate(getUser().login());

			objModel.handleAutoNumber(getUser().login());
			
			getUser().login().save(Payment.TABLE_NAME, objModel);
			getUser().login().save(Document.TABLE_NAME, objModel.loadPrepaymentDocument(getUser().login(), false));
			
			objModel.savePaymentApplicationSelections(getUser().login());
					
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "6629d0a7ee974cc59f231658e30296cf");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Payment&GUID=" + objModel.getGuid() + "&Error=Numbered!");	
	}

	public void beforePopulate() throws Exception {
		if(objModel.getContactsGuid() != null) {
			Contact cntct = objModel.loadContact(getUser().login(), Contact.class, false);
			cboBilingContact.setOptions(cntct.loadChildOptions(getUser().login(), false));
		}
		
		objModel.calculate(getUser().login());
	}
}
