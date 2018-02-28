package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.PayrollCheck;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class PostPayrollChecksLineController extends Controller {
	private CheckTag chkIsPosted;
	public PostPayrollChecksLineController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		PayrollCheck objModel = (PayrollCheck) getModel();
		TransactionType objType = TransactionType.loadByGuid(getUser().login(), TransactionType.class, PayrollCheck.TRANSACTION_TYPE_GUID);

		chkIsPosted = new CheckTag(this, "Row Posted");
		chkIsPosted.setName("Posted" + objModel.getGuid());
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		TextTag litDate = new TextTag(this, "Row", PayrollCheck.DATE, objModel.getGuid(), objModel);
		litDate.setFormat("yyyy-MM-dd");
		litDate.setIsReadOnly(true);
		TextTag litNumber = new TextTag(this, "Row", PayrollCheck.NUMBER, objModel.getGuid(), objModel);
		litNumber.setIsReadOnly(true);
		TextTag litPayee = new TextTag(this, "Row", PayrollCheck.PAY_TO_THE_ORDER_OF, objModel.getGuid(), objModel);
		litPayee.setIsReadOnly(true);
		TextTag litCheckAmount = new TextTag(this, "Row", PayrollCheck.PAYCHECK_AMOUNT, objModel.getGuid(), objModel);
		litCheckAmount.setIsReadOnly(true);
		litCheckAmount.setFormat(sMoneyFormat);
		TextTag litTotalCosts = new TextTag(this, "Row", PayrollCheck.TOTAL_COSTS, objModel.getGuid(), objModel);
		litTotalCosts.setIsReadOnly(true);
		litTotalCosts.setFormat(sMoneyFormat);

		ButtonTag btnOpen = new ButtonTag(this, "Row", "Open", objModel.getGuid(), "Open");
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	public History createHistory() throws Exception { return null; }
	
	public boolean setIsChecked(boolean value) throws Exception {
		return chkIsPosted.setIsChecked(value);
	}
	public boolean getIsChecked() throws Exception {
		return chkIsPosted.getIsChecked();
	}
	private void btnOpen_OnClick() throws Exception {
		PayrollCheck objModel = (PayrollCheck) getModel();
		TransactionType objType = TransactionType.loadByGuid(getUser().login(), TransactionType.class, PayrollCheck.TRANSACTION_TYPE_GUID);
		redirect(objType.getRootUrl() + "&GUID=" + objModel.getGuid());
	}
  }
