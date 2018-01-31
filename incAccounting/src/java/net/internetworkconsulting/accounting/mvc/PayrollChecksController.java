package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Employee;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.accounting.entities.PayrollCheck;
import net.internetworkconsulting.accounting.entities.PayrollField;
import net.internetworkconsulting.accounting.entities.PayrollFieldType;
import net.internetworkconsulting.accounting.entities.PayrollFieldValue;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PayrollChecksController extends EditController {
	private PayrollCheck objModel;
	private ButtonTag btnTemplate;
	public PayrollChecksController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		objModel = PayrollCheck.loadByGuid(getUser().login(), PayrollCheck.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(PayrollCheck.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return PayrollCheck.loadByGuid(getUser().login(), PayrollCheck.class, guid);
	}
	public Object handleNewRow() throws Exception {
		objModel = new PayrollCheck();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		objModel = (PayrollCheck) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/PayrollCheck.html"), new HtmlSyntax()));

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
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
		
		TextTag txtGuid = new TextTag(this, PayrollCheck.GUID, objModel);
		txtGuid.setIsReadOnly(true);
		
		CheckTag chkTemplate = new CheckTag(this, PayrollCheck.IS_TEMPLATE, objModel);
		chkTemplate.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		chkTemplate.addOnChangeEvent(new Event() { public void handle() throws Exception { chkTemplate_OnChange(); } });
		
		TextTag txtNumber = new TextTag(this, PayrollCheck.NUMBER, objModel);
		txtNumber.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		DateTag dtDate = new DateTag(this, PayrollCheck.DATE, objModel);
		dtDate.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtPayTo = new TextTag(this, PayrollCheck.PAY_TO_THE_ORDER_OF, objModel);
		txtPayTo.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtLine1 = new TextTag(this, PayrollCheck.LINE_1, objModel);
		txtLine1.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtLine2 = new TextTag(this, PayrollCheck.LINE_2, objModel);
		txtLine2.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtCountry = new TextTag(this, PayrollCheck.COUNTRY, objModel);
		txtCountry.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtCity = new TextTag(this, PayrollCheck.CITY, objModel);
		txtCity.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtState = new TextTag(this, PayrollCheck.STATE, objModel);
		txtState.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		TextTag txtZip = new TextTag(this, PayrollCheck.POSTAL_CODE, objModel);
		txtZip.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		DateTag dtEnding = new DateTag(this, PayrollCheck.ENDING, objModel);
		dtEnding.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
				
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
		
		ComboTag cboEmployeeGuid = new ComboTag(this, PayrollCheck.EMPLOYEES_GUID, objModel);
		cboEmployeeGuid.setOptions(Employee.loadOptions(getUser().login(), true));
		cboEmployeeGuid.addOnChangeEvent(new Event() { public void handle() throws Exception { cboEmployeeGuid_OnChange();  } });
		cboEmployeeGuid.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ComboTag cboAccount = new ComboTag(this, PayrollCheck.ACCOUNTS_GUID, objModel);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		cboAccount.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ComboTag cboDuration = new ComboTag(this, PayrollCheck.DURATION, objModel);
		cboDuration.setOptions(PayrollCheck.getDurationOptions());
		cboDuration.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		List<PayrollFieldValue> lstGross = objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback());
		List<PayrollFieldValue> lstEmployeePaid = objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback());
		List<PayrollFieldValue> lstCompanyPaid = objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback());

		for(PayrollFieldValue pfv: lstGross)
			createFieldController(pfv, "Gross", PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);
		
		for(PayrollFieldValue pfv: lstEmployeePaid)
			createFieldController(pfv, "Employee", PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID);

		for(PayrollFieldValue pfv: lstCompanyPaid)
			createFieldController(pfv, "Company", PayrollFieldType.TYPE_COMPANY_PAID_GUID);

		ButtonTag btnAddCompensation = new ButtonTag(this, "Add Gross");
		btnAddCompensation.setValue("Add");
		btnAddCompensation.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddCompensation_OnClick();  } });
		btnAddCompensation.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ButtonTag btnAddWithholding = new ButtonTag(this, "Add Employee");
		btnAddWithholding.setValue("Add");
		btnAddWithholding.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddWithholding_OnClick();  } });
		btnAddWithholding.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ButtonTag btnAddContribution = new ButtonTag(this, "Add Company");
		btnAddContribution.setValue("Add");
		btnAddContribution.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddContribution_OnClick();  } });
		btnAddContribution.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		btnSave.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		if((objModel.getNumber() == null || objModel.getNumber().isEmpty()) && objModel.getRowState() != RowState.Insert) {
			ButtonTag btnNumber = new ButtonTag(this, "Number Button");
			btnNumber.setValue("Number");
			btnNumber.addOnClickEvent(new Event() {public void handle() throws Exception { btnNumber_OnClicked(); } });
		}
		
		ButtonTag btnPost = new ButtonTag(this, "Post");
		btnPost.addOnClickEvent(new Event() { public void handle() throws Exception { btnPost_OnClick(); } });		
		if(objModel.getPostedTransactionsGuid() != null)
			btnPost.setValue("Unpost");
		if(objModel.getRowState() != RowState.NA || objModel.getNumber()== null || objModel.getNumber().length() < 1)
			btnPost.setIsReadOnly(true);
		
		btnTemplate = new ButtonTag(this, "Use Template");
		btnTemplate.addOnClickEvent(new Event() { public void handle() throws Exception { btnTemplate_OnClick(); } });		
		btnTemplate.setIsReadOnly(objModel.getPostedTransactionsGuid() != null);
		
		ButtonTag btnOpen = new ButtonTag(this, "Open Transaction");
		if(objModel.getPostedTransactionsGuid() == null)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		
		ButtonTag btnPrint = new ButtonTag(this, "Print");
		if(objModel.getRowState() != RowState.NA || objModel.getNumber()== null || objModel.getNumber().length() < 1)
			btnPrint.setIsReadOnly(true);
		btnPrint.addOnClickEvent(new Event() { public void handle() throws Exception { btnPrint_OnClick(); } });   		
		
	}
	private PayrollChecksFieldsController createFieldController(PayrollFieldValue objField, String sBlock, String sFieldTypeLimit) throws Exception {
		if(sFieldTypeLimit == null)
			throw new Exception("Field type limit not specified!");

		PayrollChecksFieldsController pcefc = new PayrollChecksFieldsController(this, sBlock);
		pcefc.setModel(objField);
		pcefc.setTemplatePrefix(sBlock);
		pcefc.setIsDocumentBlock(true);
		pcefc.setPosted(objModel.getPostedTransactionsGuid() != null);
		pcefc.setFieldOptions(PayrollField.loadOptionsByType(getUser().login(), true, sFieldTypeLimit));
		
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

	public void beforePopulate() throws Exception {
		if(objModel.getIsTemplate() != null && objModel.getIsTemplate())
			btnTemplate.setIsReadOnly(true);
		
		if(objModel.getEmployeesGuid() == null || objModel.getEmployeesGuid().length() != 32)
			btnTemplate.setIsReadOnly(true);
		
		if(!objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback()).isEmpty())
			btnTemplate.setIsReadOnly(true);
		if(!objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback()).isEmpty())
			btnTemplate.setIsReadOnly(true);
		if(!objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback()).isEmpty())
			btnTemplate.setIsReadOnly(true);
	}
	
	private void cboEmployeeGuid_OnChange() throws Exception {
		objModel.handleEmployeeGuid(getUser().login());
	}
	private void btnAddCompensation_OnClick() throws Exception {
		PayrollFieldValue pfv = new PayrollFieldValue();
		pfv.initialize(objModel);
		pfv.setPayrollCheck(objModel);
		pfv.setPayrollChecksGuid(objModel.getGuid());
		pfv.setIsGrossExpense(true);

		objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback()).add(pfv);
		
		PayrollChecksFieldsController pcefc = createFieldController(pfv, "Gross", PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);		
		doCreateControls(pcefc, false);
		doBeforeUpdate(pcefc);
		doUpdateControls(pcefc);
		doBeforeHandle(pcefc);
		doHandleEvents(pcefc);
	}
	private void btnAddWithholding_OnClick() throws Exception {
		PayrollFieldValue pfv = new PayrollFieldValue();
		pfv.initialize(objModel);
		pfv.setPayrollCheck(objModel);
		pfv.setPayrollChecksGuid(objModel.getGuid());
		pfv.setIsGrossExpense(false);
		
		objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback()).add(pfv);

		PayrollChecksFieldsController pcefc = createFieldController(pfv, "Employee", PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID);		
		doCreateControls(pcefc, false);
		doBeforeUpdate(pcefc);
		doUpdateControls(pcefc);
		doBeforeHandle(pcefc);
		doHandleEvents(pcefc);
	}
	private void btnAddContribution_OnClick() throws Exception {
		PayrollFieldValue pfv = new PayrollFieldValue();
		pfv.initialize(objModel);
		pfv.setPayrollCheck(objModel);
		pfv.setPayrollChecksGuid(objModel.getGuid());
		pfv.setIsGrossExpense(false);
		
		objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback()).add(pfv);

		PayrollChecksFieldsController pcefc = createFieldController(pfv, "Company", PayrollFieldType.TYPE_COMPANY_PAID_GUID);		
		doCreateControls(pcefc, false);
		//doBeforeUpdate(pcefc);
		//doUpdateControls(pcefc);
		//doBeforeHandle(pcefc);
		//doHandleEvents(pcefc);
	}
	private void chkTemplate_OnChange() throws Exception {}
	
	private void btnSave_OnClick() throws Exception {
		try {
			getUser().login().begin(true);
			
			objModel.calculate(getUser().login());
			
			getUser().login().save(PayrollCheck.TABLE_NAME, objModel);
			getUser().login().save(PayrollFieldValue.TABLE_NAME, objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback()));
			getUser().login().save(PayrollFieldValue.TABLE_NAME, objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback()));
			getUser().login().save(PayrollFieldValue.TABLE_NAME, objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback()));
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "170660b28cd8498e9c287cbc3441e1d2");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=PayrollCheck&GUID=" + objModel.getGuid());
	}
	public void btnNumber_OnClicked() throws Exception {
		try {
			getUser().login().begin(true);
			
			objModel.calculate(getUser().login());

			objModel.handleAutoNumber(getUser().login());
			
			getUser().login().save(PayrollCheck.TABLE_NAME, objModel);
					
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "43f331254d644592ab3ba412a224636a");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=PayrollCheck&GUID=" + objModel.getGuid() + "&Error=Numbered!");	
	}
	private void btnPost_OnClick() throws Exception {
		String sAction = "";

		try {
			getUser().login().begin(true);
			
			if(objModel.getPostedTransactionsGuid() == null) {
				objModel.post(getUser().login());
				sAction = "Posted";
			} else {
				objModel.unpost(getUser().login());
				sAction = "Unposted";
			}
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "bbb207c2c6944346aaa4f7644facda4e");
			addError("Post", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=PayrollCheck&GUID=" + objModel.getGuid() + "&Error=" + sAction + "!");
	}
	private void btnTemplate_OnClick() throws Exception {
		PayrollCheck objTemplate = PayrollCheck.loadTemplate(getUser().login(), objModel.getEmployeesGuid());
		
		// set check headers
		objModel.setAccountsGuid(objTemplate.getAccountsGuid());
		objModel.setCity(objTemplate.getCity());
		objModel.setCountry(objTemplate.getCountry());
		objModel.setDuration(objTemplate.getDuration());
		objModel.setIsDeleted(false);
		objModel.setIsTemplate(false);
		objModel.setLine1(objTemplate.getLine1());
		objModel.setLine2(objTemplate.getLine2());
		objModel.setPayToTheOrderOf(objTemplate.getPayToTheOrderOf());
		objModel.setPostalCode(objTemplate.getPostalCode());
		objModel.setState(objTemplate.getState());
				
		List<PayrollFieldValue> lstTemplateGrossExpenses = objTemplate.loadGrossExepnseValues(getUser().login(), true);
		for(PayrollFieldValue pfvTemplate : lstTemplateGrossExpenses) {
			PayrollFieldValue pfv = new PayrollFieldValue();
			pfv.initialize(objModel);
			pfv.setPayrollCheck(objModel);
			pfv.setPayrollChecksGuid(objModel.getGuid());
			
			pfv.setIsGrossExpense(pfvTemplate.getIsGrossExpense(getUser().login()));
			pfv.setPayrollFieldsGuid(pfvTemplate.getPayrollFieldsGuid());
			pfv.setQuantity(pfvTemplate.getQuantity());
			pfv.setRate(pfvTemplate.getRate());
			pfv.setTotal(pfvTemplate.getTotal());

			objModel.loadGrossExepnseValues(getUser().login(), !getIsPostback()).add(pfv);

			PayrollChecksFieldsController pcefc = createFieldController(pfv, "Gross", PayrollFieldType.TYPE_GROSS_EXPENSE_GUID);		
			doCreateControls(pcefc, false);
			//doBeforeUpdate(pcefc);
			//doUpdateControls(pcefc);
			//doBeforeHandle(pcefc);
			//doHandleEvents(pcefc);
		}
		
		List<PayrollFieldValue> lstTemplateEmployeePaid = objTemplate.loadEmployeePaidValues(getUser().login(), true);
		for(PayrollFieldValue pfvTemplate : lstTemplateEmployeePaid) {
			PayrollFieldValue pfv = new PayrollFieldValue();
			pfv.initialize(objModel);
			pfv.setPayrollCheck(objModel);
			pfv.setPayrollChecksGuid(objModel.getGuid());

			pfv.setIsGrossExpense(pfvTemplate.getIsGrossExpense(getUser().login()));
			pfv.setPayrollFieldsGuid(pfvTemplate.getPayrollFieldsGuid());
			pfv.setQuantity(pfvTemplate.getQuantity());
			pfv.setRate(pfvTemplate.getRate());
			pfv.setTotal(pfvTemplate.getTotal());

			objModel.loadEmployeePaidValues(getUser().login(), !getIsPostback()).add(pfv);

			PayrollChecksFieldsController pcefc = createFieldController(pfv, "Employee", PayrollFieldType.TYPE_EMPLOYEE_PAID_GUID);		
			doCreateControls(pcefc, false);
			//doBeforeUpdate(pcefc);
			//doUpdateControls(pcefc);
			//doBeforeHandle(pcefc);
			//doHandleEvents(pcefc);
		}

		List<PayrollFieldValue> lstTemplateCompanyPaid = objTemplate.loadCompanyPaidValues(getUser().login(), true);
		for(PayrollFieldValue pfvTemplate : lstTemplateCompanyPaid) {
			PayrollFieldValue pfv = new PayrollFieldValue();
			pfv.initialize(objModel);
			pfv.setPayrollCheck(objModel);
			pfv.setPayrollChecksGuid(objModel.getGuid());

			pfv.setIsGrossExpense(pfvTemplate.getIsGrossExpense(getUser().login()));
			pfv.setPayrollFieldsGuid(pfvTemplate.getPayrollFieldsGuid());
			pfv.setQuantity(pfvTemplate.getQuantity());
			pfv.setRate(pfvTemplate.getRate());
			pfv.setTotal(pfvTemplate.getTotal());

			objModel.loadCompanyPaidValues(getUser().login(), !getIsPostback()).add(pfv);

			PayrollChecksFieldsController pcefc = createFieldController(pfv, "Company", PayrollFieldType.TYPE_COMPANY_PAID_GUID);		
			doCreateControls(pcefc, false);
			//doBeforeUpdate(pcefc);
			//doUpdateControls(pcefc);
			//doBeforeHandle(pcefc);
			//doHandleEvents(pcefc);
		}
		
		objModel.calculate(getUser().login());
	}
	private void btnOpen_OnClick() throws Exception {
		PayrollCheck objModel = (PayrollCheck) getModel();
		redirect("~/incAccounting?App=Transaction&GUID=" + objModel.getGuid());
	}
	private void btnPrint_OnClick() throws Exception {
		PayrollCheck objModel = (PayrollCheck) getModel();
		redirect("~/incAccounting?App=PayrollCheckPrint&GUID=" + objModel.getGuid());
	}
}