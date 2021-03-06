package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Payment;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class PostPaymentsController extends Controller {
	private ComboTag cboType;
	private ComboTag cboStatus;
	private List<net.internetworkconsulting.accounting.entities.Payment> objModel;
	private LinkedList<PostPaymentsLinesController> lstControllers;
	private List<Option> lstPaymentTypeOptions;
	
	public PostPaymentsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(readTemplate("~/templates/PostPayments.html"), new HtmlSyntax()));

		String type_guid = getRequest().getParameter("Payment Types GUID");
		if(type_guid != null && type_guid.equals("null"))
			type_guid = null;
		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;

		lstPaymentTypeOptions = PaymentType.loadOptions(getUser().login());
		
		cboType = new ComboTag(this, "Filter Types");
		cboType.setOptions(lstPaymentTypeOptions);
		cboType.setValue(type_guid);
		
		cboStatus = new ComboTag(this, "Filter Status");
		List<Option> lstStatuses = new LinkedList<>();
		lstStatuses.add(new Option("Not Posted", ""));
		lstStatuses.add(new Option("Posted", "posted"));
		cboStatus.setOptions(lstStatuses);
		cboStatus.setValue(status);
		
		objModel = (List<Payment>) model;
		if(!getIsPostback()) {
			if(status == null || status.equals("") || status.equals("null"))
				objModel = Payment.loadByTypeAndPosted(getUser().login(), type_guid, false);
			else
				objModel = Payment.loadByTypeAndPosted(getUser().login(), type_guid, true);	
			
			cboType.setValue(type_guid);
		}
		setModel(objModel);
		
		lstControllers = new LinkedList<>();
		for(Payment payment: objModel)
			lstControllers.add(createController(payment));
			
		ButtonTag btnFilter = new ButtonTag(this, "Filter");
		btnFilter.addOnClickEvent(new Event() { public void handle() throws Exception { btnFilter_OnClick(); } });

		ButtonTag btnInvert = new ButtonTag(this, "Invert");
		btnInvert.addOnClickEvent(new Event() { public void handle() throws Exception { btnInvert_OnClick(); } });
		
		ButtonTag btnProcess = new ButtonTag(this, "Process");
		btnProcess.addOnClickEvent(new Event() { public void handle() throws Exception { btnProcess_OnClick(); } });
	}
	public History createHistory() throws Exception {
		return new History("Post Payments", getRequest(), getUser());
	}
	
	private void btnFilter_OnClick() throws Exception {		
		redirect("~/incAccounting?App=PostPayments&Payment Types GUID=" + cboType.getValue() + "&Status=" + cboStatus.getValue());		
	}
	private void btnProcess_OnClick() throws Exception {
		try {
			String sErrors = "";
			for(PostPaymentsLinesController pdlc: lstControllers) {
				boolean isChecked = pdlc.getIsChecked();
				Payment obj = (Payment) pdlc.getModel();
				boolean isPosted = obj.getPostedAccountsGuid() != null && obj.getPostedTransactionsGuid() != null;

				// since each posting is in it's own transaction, we want what 
				// can be posted to succeed, and what can't post should fail 
				// independently	
				try {
					if(isChecked) {
						if(isPosted)
							obj.unpost(getUser().login());
						else
							obj.post(getUser().login());
					}
				}
				catch(Exception ex) {
					getUser().login().rollback(true);
					sErrors += "Purchase payment " + obj.getOurNumber() + " could not be posted due to: " + ex.toString() + "\n\n";
				}
			}
			
			if(sErrors.length() > 0)
				throw new Exception(sErrors + "Please 'filter' again as the list of purchase payments is not valid!");
		}
		catch(Exception ex) {
			getUser().logExcpetion(ex, "67f46beb9fc34429862ece42cf5227b1");
			addError("Process", ex.getMessage());
			return;
		}
		
		btnFilter_OnClick();
	}
	private void btnInvert_OnClick() throws Exception {
		for(PostPaymentsLinesController pdlc: lstControllers)
			pdlc.setIsChecked(!pdlc.getIsChecked());
	}
	private PostPaymentsLinesController createController(Payment payment) {
		PostPaymentsLinesController controller = new PostPaymentsLinesController(this, "Row");
		controller.setIsDocumentBlock(true);
		controller.setModel(payment);

		return controller;
	}
}
