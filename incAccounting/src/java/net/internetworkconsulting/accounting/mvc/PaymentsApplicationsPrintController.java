package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.accounting.entities.PaymentApplicationSelection;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

class PaymentsApplicationsPrintController extends Controller{
	private PaymentApplicationSelection objModel;

	private Payment objPayment;
	public void setPayment(Payment value) { objPayment = value; }
	
	public PaymentsApplicationsPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		
		objModel = (PaymentApplicationSelection) getModel();

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		LiteralTag litName = new LiteralTag(this, "Application", PaymentApplicationSelection.NAME, objModel.getDocumentsGuid(), objModel);
		LiteralTag litDate = new LiteralTag(this, "Application", PaymentApplicationSelection.DATE, objModel.getDocumentsGuid(), objModel);
		LiteralTag litOurNumber = new LiteralTag(this, "Application", PaymentApplicationSelection.REFERENCE_NUMBER, objModel.getDocumentsGuid(), objModel);
		LiteralTag litTheirNumber = new LiteralTag(this, "Application", PaymentApplicationSelection.CUSTOMER_NUMBER, objModel.getDocumentsGuid(), objModel);

		LiteralTag litBalance = new LiteralTag(this, "Application", PaymentApplicationSelection.BALANCE, objModel.getDocumentsGuid(), objModel);
		litBalance.setFormat(sMoneyFormat);
		
		LiteralTag txtAmount = new LiteralTag(this, "Application", PaymentApplicationSelection.AMOUNT, objModel.getDocumentsGuid(), objModel);
		txtAmount.setFormat(sMoneyFormat);				
	}
	public History createHistory() throws Exception { return null; }
	
	private boolean isReadOnly = false;
	void setIsReadOnly(boolean value) { isReadOnly = value;}
}
