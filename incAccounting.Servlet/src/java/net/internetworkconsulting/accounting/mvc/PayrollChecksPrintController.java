package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.PayrollCheck;
import net.internetworkconsulting.accounting.entities.PayrollField;
import net.internetworkconsulting.accounting.entities.PayrollFieldType;
import net.internetworkconsulting.accounting.entities.PayrollFieldValue;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PayrollChecksPrintController extends Controller {
	private PayrollCheck objModel;
	private ButtonTag btnTemplate;
	private List<Option> lstGrossExpenseFieldOptions;
	private List<Option> lstEmployeePaidFieldOptions;
	private List<Option> lstCompanyPaidFieldOptions;
	
	public PayrollChecksPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";

		setDocument(new Template(readTemplate("~/templates/PayrollCheckPrint.html"), new HtmlSyntax()));
		
		objModel = (PayrollCheck) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = PayrollCheck.loadByGuid(getUser().login(), PayrollCheck.class, sGuid);
			else {
				objModel = new PayrollCheck();
				objModel.initialize();
			}
		}
		setModel(objModel);
		
		LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));

		LiteralTag litRateDecimals = new LiteralTag(this, "Rate Decimals");
		litRateDecimals.setValue(getUser().getSetting(Document.SETTING_RATE_DECIMALS));

		LiteralTag litQuantityDecimals = new LiteralTag(this, "Quantity Decimals");
		litQuantityDecimals.setValue(getUser().getSetting(Document.SETTING_QUANITY_DECIMALS));
		
		LiteralTag txtGuid = new LiteralTag(this, PayrollCheck.GUID, objModel);
		LiteralTag txtNumber = new LiteralTag(this, PayrollCheck.NUMBER, objModel);
		LiteralTag dtDate = new LiteralTag(this, PayrollCheck.DATE, objModel);
		dtDate.setFormat("yyyy-MM-dd");
		
		LiteralTag txtPayTo = new LiteralTag(this, PayrollCheck.PAY_TO_THE_ORDER_OF, objModel);
		LiteralTag txtLine1 = new LiteralTag(this, PayrollCheck.LINE_1, objModel);
		LiteralTag txtLine2 = new LiteralTag(this, PayrollCheck.LINE_2, objModel);
		LiteralTag txtCountry = new LiteralTag(this, PayrollCheck.COUNTRY, objModel);
		LiteralTag txtCity = new LiteralTag(this, PayrollCheck.CITY, objModel);
		LiteralTag txtState = new LiteralTag(this, PayrollCheck.STATE, objModel);
		LiteralTag txtZip = new LiteralTag(this, PayrollCheck.POSTAL_CODE, objModel);
		LiteralTag dtEnding = new LiteralTag(this, PayrollCheck.ENDING, objModel);
		
		LiteralTag litCompensation = new LiteralTag(this, PayrollCheck.COMPENSATION, objModel);
		litCompensation.setFormat(sMoneyFormat);

		LiteralTag litAdjGross = new LiteralTag(this, PayrollCheck.ADJUSTED_GROSS, objModel);
		litAdjGross.setFormat(sMoneyFormat);

		LiteralTag litEmployeePaid = new LiteralTag(this, PayrollCheck.EMPLOYEE_PAID, objModel);
		litEmployeePaid.setFormat(sMoneyFormat);

		LiteralTag litPaycheckAmount = new LiteralTag(this, PayrollCheck.PAYCHECK_AMOUNT, objModel);
		litPaycheckAmount.setFormat(sMoneyFormat);

		LiteralTag litCompanyPaid = new LiteralTag(this, PayrollCheck.COMPANY_PAID, objModel);
		litCompanyPaid.setFormat(sMoneyFormat);

		LiteralTag litTotalCosts = new LiteralTag(this, PayrollCheck.TOTAL_COSTS, objModel);
		litTotalCosts.setFormat(sMoneyFormat);
				
		LiteralTag litEmployeeGuid = new LiteralTag(this, PayrollCheck.EMPLOYEES_GUID, null);
		litEmployeeGuid.setValue(objModel.loadEmployeeGuid(getUser().login(), Employee.class, !getIsPostback()).loadContact(getUser().login(), Contact.class, !getIsPostback()).getDisplayName());

		LiteralTag litAccount = new LiteralTag(this, PayrollCheck.ACCOUNTS_GUID, null);
		Account bizAccount = objModel.loadAccountsGuid(getUser().login(), Account.class, !getIsPostback());
		litAccount.setValue(bizAccount.getNumber() + " " + bizAccount.getName());
		
		LiteralTag litDuration = new LiteralTag(this, PayrollCheck.DURATION, null);
		for(Option option : PayrollCheck.getDurationOptions())
			if(!option.getValue().isEmpty() && Integer.parseInt(option.getValue()) == objModel.getDuration())
				litDuration.setValue(option.getDisplay());
		
		List<PayrollFieldValue> lstGross = objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback());
		List<PayrollFieldValue> lstEmployeePaid = objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback());
		List<PayrollFieldValue> lstCompanyPaid = objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback());

		lstGrossExpenseFieldOptions = PayrollField.loadOptionsByType(getUser().login(), PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);
		lstEmployeePaidFieldOptions = PayrollField.loadOptionsByType(getUser().login(), PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID);
		lstCompanyPaidFieldOptions = PayrollField.loadOptionsByType(getUser().login(), PayrollFieldType.TYPE_COMPANY_PAID_GUID);

		for(PayrollFieldValue pfv: lstGross)
			createFieldController(pfv, "Gross", lstGrossExpenseFieldOptions);
		
		for(PayrollFieldValue pfv: lstEmployeePaid)
			createFieldController(pfv, "Employee", lstEmployeePaidFieldOptions);

		for(PayrollFieldValue pfv: lstCompanyPaid)
			createFieldController(pfv, "Company", lstCompanyPaidFieldOptions);
		
		LiteralTag litAmountAsText = new LiteralTag(this, "Amount as Text");
		litAmountAsText.setValue(objModel.getPaycheckAmountAsText());				
	}
	private PayrollChecksFieldsPrintController createFieldController(PayrollFieldValue objField, String sBlock, List<Option> options) throws Exception {
		PayrollChecksFieldsPrintController pcefc = new PayrollChecksFieldsPrintController(this, sBlock);
		pcefc.setModel(objField);
		pcefc.setTemplatePrefix(sBlock);
		pcefc.setIsDocumentBlock(true);
		pcefc.setPosted(objModel.getPostedTransactionsGuid() != null);
		pcefc.setFieldOptions(options);
		
		return pcefc;
	}
	public History createHistory() throws Exception {
		String sDisplay = "New Payroll Check";
		if(objModel.getRowState() != RowState.Insert)
			if(objModel.getNumber() == null)
				sDisplay = "Payroll Check";
			else
				sDisplay = objModel.getNumber();

		return new History(sDisplay, getRequest(), getUser());
	}
}