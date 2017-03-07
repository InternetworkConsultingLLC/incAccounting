package net.internetworkconsulting.bootstrap.mvc;

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
			resp.sendRedirect("incBootstrap?App=Login&Error=No GET parameter App was provided!");
			resp.flushBuffer();
			return;
		} else if(sApp.equals("Login"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new LoginController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ChangePassword"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ChangePasswordController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Computer"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ComputerController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Group"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new GroupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Log"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new LogController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportBlock"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportBlockController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Report"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportView"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportViewController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Securable"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SecurableController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setting"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SettingController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setup"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SetupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("User"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UserController(this, TemplateController.BLOCK_BODY);
			} };
		else {
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UserController(this, TemplateController.BLOCK_BODY);
			} };
		}
		
		template.execute();
	}
	
}
