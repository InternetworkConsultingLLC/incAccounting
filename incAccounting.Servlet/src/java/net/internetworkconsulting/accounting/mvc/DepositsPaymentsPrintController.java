package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;

public class DepositsPaymentsPrintController extends Controller {
	private Payment objModel;
	private CheckTag chkIncluded;

	public DepositsPaymentsPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		
		objModel = (Payment) getModel();

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		LiteralTag litDate = new LiteralTag(this, "Payment", Payment.DATE, objModel.getGuid(), objModel);
		litDate.setFormat("yyyy-MM-dd");
		LiteralTag litContact = new LiteralTag(this, "Payment", Payment.CONTACTS_DISPLAY_NAME, objModel.getGuid(), objModel);
		LiteralTag litNumber = new LiteralTag(this, "Payment", Payment.THEIR_NUMBER, objModel.getGuid(), objModel);		
		LiteralTag litAmount = new LiteralTag(this, "Payment", Payment.TOTAL, objModel.getGuid(), objModel);
		litAmount.setFormat(sMoneyFormat);
		
		chkIncluded = new CheckTag(this, "Payment Included");
		chkIncluded.setIsReadOnly(isReadOnly);
		chkIncluded.setName("Payment Included" + objModel.getGuid());
		chkIncluded.addOnChangeEvent(new Event() { public void handle() throws Exception { chkIncluded_Changed(); } });
		
		if(!getIsPostback())
			chkIncluded.setIsChecked(objModel.getBankDepositsGuid() != null);
	}

	public History createHistory() throws Exception { return null; }
	private void chkIncluded_Changed() throws Exception {
		if(chkIncluded.getIsChecked())
			objModel.setBankDepositsGuid(objDeposit.getGuid());
		else
			objModel.setBankDepositsGuid(null);
	}

	private Deposit objDeposit = null;
	void setDeposit(Deposit value) { objDeposit = value; }
	
	private boolean isReadOnly = false;
	void setIsReadOnly(boolean value) { isReadOnly = value; }
}
