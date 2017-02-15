package net.internetworkconsulting.accounting.mvc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.internetworkconsulting.mvc.TemplateController;

public class Servlet extends net.internetworkconsulting.mvc.Servlet {
	protected void doAction(HttpServletRequest req, HttpServletResponse resp, boolean is_postback) throws ServletException, IOException {
		String sApp = req.getParameter("App");
		TemplateController template = null;
		if(sApp == null) {
			resp.sendRedirect("incBootstrap?App=Login");
			resp.flushBuffer();
			return;
		} else if(sApp.equals("AccountEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new AccountEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("AccountList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new AccountListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepartmentList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DepartmentListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepartmentEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DepartmentEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("JobList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new JobListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("JobEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new JobEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setup"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SetupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("TransactionList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new TransactionListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("TransactionEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new TransactionEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("UnitMeasureList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UnitMeasureListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("UnitMeasureEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UnitMeasureEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ContactList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ContactListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ContactEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ContactEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ItemList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ItemListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ItemEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ItemEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SalesTaxList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SalesTaxListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SalesTaxEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SalesTaxEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReconciliationList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReconciliationListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReconciliationEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReconciliationEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DocumentListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DocumentEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentPrint"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DocumentPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ContactTypeList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ContactTypeListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ContactTypeEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ContactTypeEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentTypeList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DocumentTypeListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DocumentTypeEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DocumentTypeEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostDocuments"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PostDocumentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PaymentListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PaymentEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentPrint"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PaymentPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentTypeList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PaymentTypeListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PaymentTypeEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PaymentTypeEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostPayments"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PostPaymentsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepositList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DepositListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepositEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DepositEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("DepositPrint"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new DepositPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostDeposits"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PostDepositsController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("EmployeeList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new EmployeeListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("EmployeeEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new EmployeeEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollFieldList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PayrollFieldListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollFieldEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PayrollFieldEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollCheckList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PayrollCheckListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollCheckEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PayrollCheckEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PayrollCheckPrint"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PayrollCheckPrintController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("PostPayrollChecks"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new PostPayrollCheckController(this, TemplateController.BLOCK_BODY);
			} };
		else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND, "The GET parameter App is is not valid!");
			resp.flushBuffer();
			return;
		}
		
		template.execute();
	}
	
}
