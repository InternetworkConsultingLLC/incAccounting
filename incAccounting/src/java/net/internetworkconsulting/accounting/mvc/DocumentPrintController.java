package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentLine;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.SalesTax;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DocumentPrintController extends Controller {
	public DocumentPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	private DocumentPrintLineController createController(DocumentLine line) {
		Document objModel = (Document) getModel();
		
		DocumentPrintLineController delc = new DocumentPrintLineController(this, "Row");
		delc.setModel(line);
		delc.setIsDocumentBlock(true);
		delc.setParentDocument(objModel);
		
		return delc;
	}

	public boolean getEnforceSecurity() { return true; }	
	public void createControls(Template document, Object model) throws Exception {
		Document objModel = (Document) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = Document.loadByGuid(getUser().login(), Document.class, sGuid);
			else {
				objModel = new net.internetworkconsulting.accounting.entities.Document();
				objModel.initialize(getUser().login());
			}				
		}
		setModel(objModel);

		String sDocumentType = objModel.loadDocumentType(getUser().login(), DocumentType.class, false).loadTransactionType(getUser().login(), TransactionType.class, false).getName();
		setDocument(new Template(read_url("~/templates/DocumentPrint-" + sDocumentType.replace(" ", "") + ".html"), new HtmlSyntax()));

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		LiteralTag tagDocType = new LiteralTag(this, Document.DOCUMENT_TYPES_GUID);
		tagDocType.setValue(sDocumentType);
		
		if(objModel.getPostedAccountsGuid() != null) {
			LiteralTag tagAccount = new LiteralTag(this, Document.POSTED_ACCOUNTS_GUID);
			Account bizAccount = objModel.loadAccount(getUser().login(), Account.class, false);
			tagAccount.setValue(bizAccount.getNumber() + " " + bizAccount.getNestedName());			
		}
		
		LiteralTag tagContact = new LiteralTag(this, Document.CONTACTS_GUID);
		Contact bizContact = objModel.loadContact(getUser().login(), Contact.class, false);
		tagContact.setValue(bizContact.getDisplayName());
		
		LiteralTag tagSalesTaxGuid = new LiteralTag(this, Document.SALES_TAXES_GUID, objModel);
		SalesTax bizTax = objModel.loadSalesTax(getUser().login(), SalesTax.class, false);
		tagSalesTaxGuid.setValue(bizTax.getDisplayName());
		
		LiteralTag tagBillContact = new LiteralTag(this, Document.BILLING_CONTACTS_GUID);
		Contact bizBillContact = objModel.loadBillingContact(getUser().login(), Contact.class, false);
		tagBillContact.setValue(bizBillContact.getDisplayName());
		
		LiteralTag tagShippingContact = new LiteralTag(this, Document.SHIPPING_CONTACTS_GUID, objModel);
		Contact bizShipContact = objModel.loadShippingContact(getUser().login(), Contact.class, false);
		tagShippingContact.setValue(bizShipContact.getDisplayName());

		LiteralTag tagGuid = new LiteralTag(this, Document.GUID, objModel);
		LiteralTag tagRefNumber = new LiteralTag(this, Document.REFERENCE_NUMBER, objModel);
		LiteralTag tagContactRef = new LiteralTag(this, Document.CUSTOMER_REFERENCE, objModel);
		LiteralTag tagDisplayName = new LiteralTag(this, Document.CONTACTS_DISPLAY_NAME, objModel);
		LiteralTag tagTerms = new LiteralTag(this, Document.TERMS, objModel);
		LiteralTag tagDate = new LiteralTag(this, Document.DATE, objModel);
		LiteralTag tagDueDate = new LiteralTag(this, Document.DUE_DATE, objModel);
		LiteralTag tagShipMethod = new LiteralTag(this, Document.SHIPPING_METHOD, objModel);
		LiteralTag tagShipNumber = new LiteralTag(this, Document.SHIPPING_NUMBER, objModel);
		
		LiteralTag tagBillingDisplay = new LiteralTag(this, Document.BILLING_ADDRESS_DISPLAY_NAME, objModel);
		LiteralTag tagBillingAddressLine1 = new LiteralTag(this, Document.BILLING_ADDRESS_LINE_1, objModel);
		LiteralTag tagBillingAddressLine2 = new LiteralTag(this, Document.BILLING_ADDRESS_LINE_2, objModel);
		LiteralTag tagBillingCity = new LiteralTag(this, Document.BILLING_ADDRESS_CITY, objModel);
		LiteralTag tagBillingState = new LiteralTag(this, Document.BILLING_ADDRESS_STATE, objModel);
		LiteralTag tagBillingPostalCode = new LiteralTag(this, Document.BILLING_ADDRESS_POSTAL_CODE, objModel);
		LiteralTag tagBillingCountry = new LiteralTag(this, Document.BILLING_ADDRESS_COUNTRY, objModel);
		
		LiteralTag tagShippingDisplay = new LiteralTag(this, Document.SHIPPING_ADDRESS_DISPLAY_NAME, objModel);
		LiteralTag tagShippingAddressLine1 = new LiteralTag(this, Document.SHIPPING_ADDRESS_LINE_1, objModel);
		LiteralTag tagShippingAddressLine2 = new LiteralTag(this, Document.SHIPPING_ADDRESS_LINE_2, objModel);
		LiteralTag tagShippingCity = new LiteralTag(this, Document.SHIPPING_ADDRESS_CITY, objModel);
		LiteralTag tagShippingState = new LiteralTag(this, Document.SHIPPING_ADDRESS_STATE, objModel);
		LiteralTag tagShippingPostalCode = new LiteralTag(this, Document.SHIPPING_ADDRESS_POSTAL_CODE, objModel);
		LiteralTag tagShippingCountry = new LiteralTag(this, Document.SHIPPING_ADDRESS_COUNTRY, objModel);
		
		LiteralTag tagTaxableSutotal = new LiteralTag(this, Document.TAXABLE_SUBTOTAL, objModel);
		tagTaxableSutotal.setFormat(sMoneyFormat);

		LiteralTag tagNontaxableSubtotal = new LiteralTag(this, Document.NONTAXABLE_SUBTOTAL, objModel);
		tagNontaxableSubtotal.setFormat(sMoneyFormat);

		LiteralTag tagSubtotal = new LiteralTag(this, Document.SUBTOTAL, objModel);
		tagSubtotal.setFormat(sMoneyFormat);

		LiteralTag tagTaxRate = new LiteralTag(this, Document.TAX_RATE, objModel);
		tagTaxRate.setFormat(sRateFormat);

		LiteralTag tagTaxes = new LiteralTag(this, Document.TAXES, objModel);
		tagTaxes.setFormat(sMoneyFormat);

		LiteralTag tagTotal = new LiteralTag(this, Document.TOTAL, objModel);
		tagTotal.setFormat(sMoneyFormat);

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
}
