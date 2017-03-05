package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentLine;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.SalesTax;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DocumentEditController extends Controller {
	private ComboTag cboShippingContact;
	private ComboTag cboBillingContact;
	private ComboTag cboSalesTax;
	Transaction objTransaction;
	
	public DocumentEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	private DocumentEditLineController createController(DocumentLine line) {
		Document objModel = (Document) getModel();
		
		DocumentEditLineController delc = new DocumentEditLineController(this, "Row");
		delc.setModel(line);
		delc.setTransaction(objTransaction);
		delc.setIsDocumentBlock(true);
		delc.setParentDocument(objModel);
		
		return delc;
	}

	public boolean getEnforceSecurity() { return true; }	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/DocumentEdit.html"), new HtmlSyntax()));
		
		Document objModel = (Document) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Document.loadByGuid(getUser().login(), Document.class, sGuid);
			else {
				objModel = new net.internetworkconsulting.accounting.entities.Document();
				objModel.initialize(getUser().login());
				
				String sDocuentTypesGuid = getRequest().getParameter(Document.DOCUMENT_TYPES_GUID);
				if(sDocuentTypesGuid != null)
					objModel.setDocumentTypesGuid(sDocuentTypesGuid);
			}				
		}
		setModel(objModel);

		objTransaction = null;
		try { objTransaction = objModel.loadTransaction(getUser().login(), Transaction.class, false); }
		catch(Exception ex) { }
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		LiteralTag litDaysTillDue = new LiteralTag(this, "Due Days");
		litDaysTillDue.setValue(getUser().getSetting(Document.SETTING_DUE_DAYS));
		
		LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		LiteralTag litRateDecimals = new LiteralTag(this, "Rate Decimals");
		litRateDecimals.setValue(getUser().getSetting(Document.SETTING_RATE_DECIMALS));
				
		LiteralTag litQuantityDecimals = new LiteralTag(this, "Quantity Decimals");
		litQuantityDecimals.setValue(getUser().getSetting(Document.SETTING_QUANITY_DECIMALS));
		
		TextTag txtGuid = new TextTag(this, Document.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		ComboTag cboDocType = new ComboTag(this, Document.DOCUMENT_TYPES_GUID, objModel);
		cboDocType.setIsReadOnly(objTransaction != null);
		cboDocType.setOptions(DocumentType.loadOptions(getUser().login(), true));
		cboDocType.addOnChangeEvent(new Event() { public void handle() throws Exception { cboDocType_OnChange(); } });
		
		ComboTag cboAccount = new ComboTag(this, Document.POSTED_ACCOUNTS_GUID, objModel);
		cboAccount.setIsReadOnly(objTransaction != null);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));

		ComboTag cboContact = new ComboTag(this, Document.CONTACTS_GUID, objModel);
		cboContact.setIsReadOnly(objTransaction != null);
		cboContact.setOptions(Contact.loadOptions(getUser().login(), true));
		cboContact.addOnChangeEvent(new Event() { public void handle() throws Exception { cboContact_OnChange(); } });

		TextTag txtRef = new TextTag(this, Document.REFERENCE_NUMBER, objModel);
		txtRef.setIsReadOnly(objTransaction != null);
		TextTag txtContactsRef = new TextTag(this, Document.CUSTOMER_REFERENCE, objModel);
		txtContactsRef.setIsReadOnly(objTransaction != null);
		TextTag txtContactName = new TextTag(this, Document.CONTACTS_DISPLAY_NAME, objModel);
		txtContactName.setIsReadOnly(objTransaction != null);
		TextTag txtTerms = new TextTag(this, Document.TERMS, objModel);
		txtTerms.setIsReadOnly(objTransaction != null);
		DateTag dtDate = new DateTag(this, Document.DATE, objModel);
		dtDate.setIsReadOnly(objTransaction != null);

		cboSalesTax = new ComboTag(this, Document.SALES_TAXES_GUID, objModel);
		cboSalesTax.setIsReadOnly(objTransaction != null);
		cboSalesTax.addOnChangeEvent(new Event() { public void handle() throws Exception { cboSalesTax_OnChange(); } });

		DateTag dtDueDate = new DateTag(this, Document.DUE_DATE, objModel);
		dtDueDate.setIsReadOnly(objTransaction != null);
		TextTag txtShippingMethod = new TextTag(this, Document.SHIPPING_METHOD, objModel);
		txtShippingMethod.setIsReadOnly(objTransaction != null);
		TextTag txtShippingNumber = new TextTag(this, Document.SHIPPING_NUMBER, objModel);
		txtShippingNumber.setIsReadOnly(objTransaction != null);

		cboBillingContact = new ComboTag(this, Document.BILLING_CONTACTS_GUID, objModel);
		cboBillingContact.setIsReadOnly(objTransaction != null);
		cboBillingContact.addOnChangeEvent(new Event() { public void handle() throws Exception { cboBilling_OnChange(); } });
		
		TextTag txtBillingDisplay = new TextTag(this, Document.BILLING_ADDRESS_DISPLAY_NAME, objModel);
		txtBillingDisplay.setIsReadOnly(objTransaction != null);
		TextTag txtBillingAddressLine1 = new TextTag(this, Document.BILLING_ADDRESS_LINE_1, objModel);
		txtBillingAddressLine1.setIsReadOnly(objTransaction != null);
		TextTag txtBillingAddressLine2 = new TextTag(this, Document.BILLING_ADDRESS_LINE_2, objModel);
		txtBillingAddressLine2.setIsReadOnly(objTransaction != null);
		TextTag txtBillingCity = new TextTag(this, Document.BILLING_ADDRESS_CITY, objModel);
		txtBillingCity.setIsReadOnly(objTransaction != null);
		TextTag txtBillingState = new TextTag(this, Document.BILLING_ADDRESS_STATE, objModel);
		txtBillingState.setIsReadOnly(objTransaction != null);
		TextTag txtBillingPostalCode = new TextTag(this, Document.BILLING_ADDRESS_POSTAL_CODE, objModel);
		txtBillingPostalCode.setIsReadOnly(objTransaction != null);
		TextTag txtBillingCountry = new TextTag(this, Document.BILLING_ADDRESS_COUNTRY, objModel);
		txtBillingCountry.setIsReadOnly(objTransaction != null);

		cboShippingContact = new ComboTag(this, Document.SHIPPING_CONTACTS_GUID, objModel);
		cboShippingContact.setIsReadOnly(objTransaction != null);
		cboShippingContact.addOnChangeEvent(new Event() { public void handle() throws Exception { cboShipping_OnChange(); } });
		cboShippingContact.setIsReadOnly(objTransaction != null);
		
		TextTag txtShippingDisplay = new TextTag(this, Document.SHIPPING_ADDRESS_DISPLAY_NAME, objModel);
		txtShippingDisplay.setIsReadOnly(objTransaction != null);
		TextTag txtShippingAddressLine1 = new TextTag(this, Document.SHIPPING_ADDRESS_LINE_1, objModel);
		txtShippingAddressLine1.setIsReadOnly(objTransaction != null);
		TextTag txtShippingAddressLine2 = new TextTag(this, Document.SHIPPING_ADDRESS_LINE_2, objModel);
		txtShippingAddressLine2.setIsReadOnly(objTransaction != null);
		TextTag txtShippingCity = new TextTag(this, Document.SHIPPING_ADDRESS_CITY, objModel);
		txtShippingCity.setIsReadOnly(objTransaction != null);
		TextTag txtShippingState = new TextTag(this, Document.SHIPPING_ADDRESS_STATE, objModel);
		txtShippingState.setIsReadOnly(objTransaction != null);
		TextTag txtShippingPostalCode = new TextTag(this, Document.SHIPPING_ADDRESS_POSTAL_CODE, objModel);
		txtShippingPostalCode.setIsReadOnly(objTransaction != null);
		TextTag txtShippingCountry = new TextTag(this, Document.SHIPPING_ADDRESS_COUNTRY, objModel);
		txtShippingCountry.setIsReadOnly(objTransaction != null);
		
		TextTag lblTaxableSutotal = new TextTag(this, Document.TAXABLE_SUBTOTAL, objModel);
		lblTaxableSutotal.setIsReadOnly(true);
		lblTaxableSutotal.setFormat(sMoneyFormat);

		TextTag lblNontaxableSubtotal = new TextTag(this, Document.NONTAXABLE_SUBTOTAL, objModel);
		lblNontaxableSubtotal.setIsReadOnly(true);
		lblNontaxableSubtotal.setFormat(sMoneyFormat);

		TextTag lblSubtotal = new TextTag(this, Document.SUBTOTAL, objModel);
		lblSubtotal.setIsReadOnly(true);
		lblSubtotal.setFormat(sMoneyFormat);

		TextTag txtTaxRate = new TextTag(this, Document.TAX_RATE, objModel);
		txtTaxRate.setIsReadOnly(true);
		txtTaxRate.setFormat(sRateFormat);

		TextTag lblTaxes = new TextTag(this, Document.TAXES, objModel);
		lblTaxes.setIsReadOnly(true);
		lblTaxes.setFormat(sMoneyFormat);

		TextTag lblTotal = new TextTag(this, Document.TOTAL, objModel);
		lblTotal.setIsReadOnly(true);
		lblTotal.setFormat(sMoneyFormat);

		if(objModel.getReferenceNumber() != null && !objModel.getReferenceNumber().isEmpty()) {
			ButtonTag btnPost = new ButtonTag(this, "Post");
			btnPost.addOnClickEvent(new Event() { public void handle() throws Exception { btnPost_OnClick(); } });		
			if(objTransaction == null)
				btnPost.setValue("Post");
			else
				btnPost.setValue("Unpost");
		}
				
		if(objTransaction == null) {
			ButtonTag btnSave = new ButtonTag(this, "Save");
			btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

			ButtonTag btnAdd = new ButtonTag(this, "Add");
			btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		}

		if(objTransaction != null) {
			ButtonTag btnOpen = new ButtonTag(this, "Open Transaction");
			btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		}
		
		if(objTransaction == null && objModel.getRowState() != RowState.Insert) {
			ButtonTag btnDelete = new ButtonTag(this, "Delete");
			btnDelete.addOnClickEvent(new Event() { public void handle() throws Exception { btnDelete_OnClick(); } });
		}
		
		if(objModel.getRowState() == RowState.NA && (objModel.getReferenceNumber() == null || objModel.getReferenceNumber().isEmpty())) {
			ButtonTag btnNumber = new ButtonTag(this, "Number");
			btnNumber.addOnClickEvent(new Event() { public void handle() throws Exception { btnNumber_OnClick(); } });
		}

		if(objModel.getRowState() == RowState.NA) {
			ButtonTag btnCopy = new ButtonTag(this, "Copy");
			btnCopy.addOnClickEvent(new Event() { public void handle() throws Exception { btnCopy_OnClick(); } });
		}

		if(objModel.getRowState() != RowState.NA || objModel.getReferenceNumber() != null) {
			if(DocumentType.PURCHASE_QUOTE_GUID.equals(objModel.getDocumentTypesGuid()) || DocumentType.SALES_QUOTE_GUID.equals(objModel.getDocumentTypesGuid())) {
				ButtonTag btnCreateOrder = new ButtonTag(this, "Create Order");
				btnCreateOrder.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateOrder_OnClick(); } });
			}

			if(
				DocumentType.PURCHASE_QUOTE_GUID.equals(objModel.getDocumentTypesGuid()) || DocumentType.SALES_QUOTE_GUID.equals(objModel.getDocumentTypesGuid()) ||
				DocumentType.PURCHASE_ORDER_GUID.equals(objModel.getDocumentTypesGuid()) || DocumentType.SALES_ORDER_GUID.equals(objModel.getDocumentTypesGuid())
			) {
				ButtonTag btnCreateInvoice = new ButtonTag(this, "Create Invoice");
				btnCreateInvoice.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateInvoice_OnClick(); } });
			}

			if(DocumentType.PURCHASE_INVOICE_GUID.equals(objModel.getDocumentTypesGuid()) || DocumentType.SALES_INVOICE_GUID.equals(objModel.getDocumentTypesGuid())) {
				ButtonTag btnCreateCM = new ButtonTag(this, "Create CM");
				btnCreateCM.addOnClickEvent(new Event() { public void handle() throws Exception { btnCreateCM_OnClick(); } });
			}
		}
		
		if(objModel.getRowState() == RowState.NA && objModel.getReferenceNumber() != null && !objModel.getReferenceNumber().isEmpty()) {
			ButtonTag btnPrint = new ButtonTag(this, "Print");
			btnPrint.addOnClickEvent(new Event() { public void handle() throws Exception { btnPrint_OnClick(); } });    
		}
		
		List<DocumentLine> lstLines = objModel.loadDocumentLines(getUser().login(), DocumentLine.class, false);
		for(DocumentLine line: lstLines)
			createController(line);
	}
	public History createHistory() throws Exception {
		Document objModel = (Document) getModel();
		
		String sDisplay = "New Document";
		if(objModel.getRowState() != RowState.Insert)
			if(objModel.getReferenceNumber() == null)
				sDisplay = "Document";
			else
				sDisplay = objModel.getReferenceNumber();

		return new History(sDisplay, getRequest(), getUser());
	}

	private void cboDocType_OnChange() throws Exception {
		Document objModel = (Document) getModel();
		objModel.handleDocumentTypesGuid(getUser().login());
	}
	private void cboContact_OnChange() throws Exception {
		Document objModel = (Document) getModel();
		objModel.handleContactsGuid(getUser().login());
	}
	private void cboBilling_OnChange() throws Exception {
		Document objModel = (Document) getModel();
		objModel.handleBillingContactsGuid(getUser().login());
	}
	private void cboShipping_OnChange() throws Exception {		
		Document objModel = (Document) getModel();
		objModel.handleShippingContactsGuid(getUser().login());
	 }
	private void cboSalesTax_OnChange() throws Exception {
		Document objModel = (Document) getModel();
		objModel.handleSalesTaxesGuid(getUser().login());
	}

	private void btnDelete_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		
		try {
			getUser().login().begin(true);

			List<DocumentLine> lstLines = objModel.loadDocumentLines(getUser().login(), DocumentLine.class, false);
			for(DocumentLine line : lstLines)
				line.setIsDeleted(true);
			objModel.setIsDeleted(true);
			
			getUser().login().save(DocumentLine.TABLE_NAME, lstLines);
			getUser().login().save(Document.TABLE_NAME, objModel);
			
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "fb5e47d3b5964075a46e7192a68d43c1");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=DocumentList");
	}
	private void btnSave_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		objModel.calculate(getUser().login());
		
		try {
			getUser().login().begin(true);
			getUser().login().save(Document.TABLE_NAME, objModel);
			getUser().login().save(DocumentLine.TABLE_NAME, objModel.loadDocumentLines(getUser().login(), DocumentLine.class, false));
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4722ae08ea614fec8c15278e296e337c");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAdd_OnClick() throws Exception { 
		Document objModel = (Document) getModel();
		objModel.calculate(getUser().login());
		
		DocumentLine line = new DocumentLine();
		line.initialize(getUser().login(), objModel);
				
		DocumentEditLineController delc = createController(line);
		doCreateControls(delc, false);
		doBeforeUpdate(delc);
		//doUpdateControls(delc);
		doBeforeHandle(delc);
		doHandleEvents(delc);
		
		objModel.loadDocumentLines(getUser().login(), DocumentLine.class, false).add(line);
	}
	private void btnPost_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		objModel.calculate(getUser().login());
		
		try {
			getUser().login().begin(true);
			
			if(objTransaction == null)
				objModel.post(getUser().login());
			else
				objModel.unpost(getUser().login());

			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "ffdb21bc5ff54f2cad8a57cc65e257ef");
			addError("Post", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objModel.getGuid() + "&Error=Posted!");
	}
	private void btnOpen_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		redirect("~/incAccounting?App=TransactionEdit&GUID=" + objModel.getGuid());
	}
	private void btnPrint_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		objModel.calculate(getUser().login());

		redirect("~/incAccounting?App=DocumentPrint&GUID=" + objModel.getGuid());
	}
	private void btnNumber_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		objModel.calculate(getUser().login());
				
		try {
			getUser().login().begin(true);

			objModel.handleAutoNumber(getUser().login());
			
			getUser().login().save(Document.TABLE_NAME, objModel);
			getUser().login().save(DocumentLine.TABLE_NAME, objModel.loadDocumentLines(getUser().login(), DocumentLine.class, false));
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "4722ae08ea614fec8c15278e296e337c");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");		
	}
	private void btnCreateOrder_OnClick() throws Exception {
		Document objNew = null;
		
		Document objModel = (Document) getModel();
		DocumentType docType = objModel.loadDocumentType(getUser().login(), DocumentType.class, false);
		if(docType.getIsSalesRelated())
			objNew = objModel.handleCopy(getUser().login(), DocumentType.SALES_ORDER_GUID);
		else
			objNew = objModel.handleCopy(getUser().login(), DocumentType.PURCHASE_ORDER_GUID);

		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objNew.getGuid());
	}
	private void btnCreateInvoice_OnClick() throws Exception {
		Document objNew = null;
		
		Document objModel = (Document) getModel();
		DocumentType docType = objModel.loadDocumentType(getUser().login(), DocumentType.class, false);
		if(docType.getIsSalesRelated())
			objNew = objModel.handleCopy(getUser().login(), DocumentType.SALES_INVOICE_GUID);
		else
			objNew = objModel.handleCopy(getUser().login(), DocumentType.PURCHASE_INVOICE_GUID);

		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objNew.getGuid());
	}
	private void btnCreateCM_OnClick() throws Exception {
		Document objNew = null;
		
		Document objModel = (Document) getModel();
		DocumentType docType = objModel.loadDocumentType(getUser().login(), DocumentType.class, false);
		if(docType.getIsSalesRelated())
			objNew = objModel.handleCopy(getUser().login(), DocumentType.SALES_CREDIT_GUID);
		else
			objNew = objModel.handleCopy(getUser().login(), DocumentType.PURCHASE_CREDIT_GUID);

		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objNew.getGuid());
	}
	private void btnCopy_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		Document objNew = objModel.handleCopy(getUser().login(), objModel.getDocumentTypesGuid());
		redirect("~/incAccounting?App=DocumentEdit&GUID=" + objNew.getGuid());
	}
	
	public void beforePopulate() throws Exception {
		super.beforePopulate();

		Document objModel = (Document) getModel();
		
		cboSalesTax.setOptions(SalesTax.loadOptions(getUser().login(), true));
		
		if(objModel.getContactsGuid() != null) {
			Contact cntct = objModel.loadContact(getUser().login(), Contact.class, false);
			cboBillingContact.setOptions(cntct.loadChildOptions(getUser().login(), false));
			
			cntct = objModel.loadContact(getUser().login(), Contact.class, false);
			cboShippingContact.setOptions(cntct.loadChildOptions(getUser().login(), false));
		}
	}
}
