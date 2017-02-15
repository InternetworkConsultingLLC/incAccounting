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
		else if(sApp.equals("ComputerList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ComputerListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ComputerEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ComputerEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("GroupList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new GroupListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("GroupEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new GroupEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("LogList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new LogListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("LogEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new LogEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportBlockEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportBlockEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("ReportView"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new ReportViewController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SecurableList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SecurableListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SecurableEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SecurableEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SettingList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SettingListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("SettingEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SettingEditController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("Setup"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SetupController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("System"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new SystemController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("UserList"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UserListController(this, TemplateController.BLOCK_BODY);
			} };
		else if(sApp.equals("UserEdit"))
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UserEditController(this, TemplateController.BLOCK_BODY);
			} };
		else {
			template = new TemplateController(req, resp, is_postback) { public void loadChildControls() {
				new UserEditController(this, TemplateController.BLOCK_BODY);
			} };
		}
		
		template.execute();
	}
	
}
