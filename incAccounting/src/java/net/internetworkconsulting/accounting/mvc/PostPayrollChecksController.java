package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.PayrollCheck;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PostPayrollChecksController  extends Controller {
	private ComboTag cboStatus;
	private LinkedList<PostPayrollChecksLineController> lstControllers;
	private List<PayrollCheck> objModel;
	public PostPayrollChecksController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(readTemplate("~/templates/PostPayrollChecks.html"), new HtmlSyntax()));

		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;
				
		cboStatus = new ComboTag(this, "Filter Status");
		List<Option> lstStatuses = new LinkedList<>();
		lstStatuses.add(new Option("Not Posted", ""));
		lstStatuses.add(new Option("Posted", "posted"));
		cboStatus.setOptions(lstStatuses);
		cboStatus.setValue(status);
		
		objModel = (List<PayrollCheck>) model;
		if(!getIsPostback()) {
			if(status == null || status.equals("") || status.equals("null"))
				objModel = PayrollCheck.loadByPosted(getUser().login(), false);
			else
				objModel = PayrollCheck.loadByPosted(getUser().login(), true);
		}
		setModel(objModel);
		
		lstControllers = new LinkedList<>();
		for(PayrollCheck pc: objModel)
			lstControllers.add(createController(pc));
			
		ButtonTag btnFilter = new ButtonTag(this, "Filter");
		btnFilter.addOnClickEvent(new Event() { public void handle() throws Exception { btnFilter_OnClick(); } });
		
		ButtonTag btnInvert = new ButtonTag(this, "Invert");
		btnInvert.addOnClickEvent(new Event() { public void handle() throws Exception { btnInvert_OnClick(); } });
		
		ButtonTag btnProcess = new ButtonTag(this, "Process");
		btnProcess.addOnClickEvent(new Event() { public void handle() throws Exception { btnProcess_OnClick(); } });
	}
	public History createHistory() throws Exception {
		return new History("Post Payroll", getRequest(), getUser());
	}
	
	private void btnFilter_OnClick() throws Exception {		
		redirect("~/incAccounting?App=PostPayrollChecks&Status=" + cboStatus.getValue());		
	}
	private void btnProcess_OnClick() throws Exception {
		try {
			getUser().login().begin(true);

			for(PostPayrollChecksLineController ppclc: lstControllers) {
				boolean isChecked = ppclc.getIsChecked();
				PayrollCheck obj = (PayrollCheck) ppclc.getModel();
				boolean isPosted = obj.getAccountsGuid() != null && obj.getPostedTransactionsGuid() != null;

				if(isChecked) {
					if(isPosted)
						obj.unpost(getUser().login());
					else
						obj.post(getUser().login());
				}
			}
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "67f46beb9fc34429862ece42cf5227b1");
			addError("Process", ex.getMessage());
			return;
		}
		
		btnFilter_OnClick();
	}
	private void btnInvert_OnClick() throws Exception {
		for(PostPayrollChecksLineController ppclc: lstControllers)
			ppclc.setIsChecked(!ppclc.getIsChecked());
	}
	private PostPayrollChecksLineController createController(PayrollCheck pc) {
		PostPayrollChecksLineController pdlc = new PostPayrollChecksLineController(this, "Row");
		pdlc.setIsDocumentBlock(true);
		pdlc.setModel(pc);

		return pdlc;
	}
}
