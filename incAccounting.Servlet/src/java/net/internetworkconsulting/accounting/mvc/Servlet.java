package net.internetworkconsulting.accounting.mvc;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.internetworkconsulting.mvc.TemplateController;

public class Servlet extends net.internetworkconsulting.mvc.Servlet {
	protected void doAction(HttpServletRequest req, HttpServletResponse resp, boolean is_postback) throws ServletException, IOException {
		String sApp = req.getParameter("App");
		TemplateController template = null;
		ServletContext context = getServletContext();
		
		if(sApp == null) {
			resp.sendRedirect("incBootstrap?App=Login");
			resp.flushBuffer();
			return;
		} else if(sApp.equals("Login"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new LoginController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ChangePassword"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ChangePasswordController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Group"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new GroupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Log"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new LogController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportBlock"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ReportBlockController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Report"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ReportController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportView"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ReportViewController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Securable"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new SecurableController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setting"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new SettingController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setup"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new SetupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("User"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new UserController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Account"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new AccountsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Department"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DepartmentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Job"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new JobsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setup"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new SetupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Transaction"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new TransactionsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("UnitMeasure"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new UnitMeasureEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Contact"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ContactsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Item"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ItemsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SalesTax"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new SalesTaxesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Reconciliation"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ReconciliationsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Document"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DocumentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentPrint"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DocumentsPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ContactType"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new ContactTypesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentType"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DocumentTypesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostDocuments"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PostDocumentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Payment"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PaymentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentPrint"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PaymentsPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentType"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PaymentTypesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostPayments"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PostPaymentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Deposit"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DepositsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepositPrint"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new DepositsPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostDeposits"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PostDepositsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Employee"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new EmployeesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollField"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PayrollFieldsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollCheck"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PayrollChecksController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollCheckPrint"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PayrollChecksPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostPayrollChecks"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new PostPayrollChecksController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("RegisterEntries"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new RegisterEntriesController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("TimeEntry"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new TimeEntryController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("TimeEntryType"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new TimeEntryTypeController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("TimeSheet"))
			template = new TemplateController(req, resp, context, is_postback) { public void loadChildControls() {
				new TimeSheetController(this, TemplateController.BLOCK_BODY);
			} };
		else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "The GET parameter App is is not valid!");
			resp.flushBuffer();
			return;
		}
		
		template.execute();
	}
	
}
