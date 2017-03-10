package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Deposit;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PostDepositsController extends Controller {
	private ComboTag cboStatus;
	private List<Deposit> objModel;
	private LinkedList<PostDepositsLinesController> lstControllers;
	public PostDepositsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/PostDeposits.html"), new HtmlSyntax()));

		String type_guid = Deposit.TRANSACTION_TYPE_GUID;
		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;

		cboStatus = new ComboTag(this, "Filter Status");
		List<Option> lstStatuses = new LinkedList<>();
		lstStatuses.add(new Option("Not Posted", ""));
		lstStatuses.add(new Option("Posted", "posted"));
		cboStatus.setOptions(lstStatuses);
		cboStatus.setValue(status);
		
		objModel = (List<Deposit>) model;
		if(!getIsPostback())
			objModel = Deposit.loadByPosted(getUser().login(), !(status == null || status.equals("") || status.equals("null")));
		setModel(objModel);
		
		lstControllers = new LinkedList<>();
		for(Deposit deposit: objModel)
			lstControllers.add(createController(deposit));
			
		ButtonTag btnFilter = new ButtonTag(this, "Filter");
		btnFilter.addOnClickEvent(new Event() { public void handle() throws Exception { btnFilter_OnClick(); } });
		
		ButtonTag btnProcess = new ButtonTag(this, "Process");
		btnProcess.addOnClickEvent(new Event() { public void handle() throws Exception { btnProcess_OnClick(); } });
}
	public History createHistory() throws Exception {
		String sDisplay = "";
		
		String type_guid = Deposit.TRANSACTION_TYPE_GUID;
		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;
		if(status == null || !status.equals("posted"))
			sDisplay = "Unposted Deposits";
		else
			sDisplay = "Posted Deposits";
		
		return new History(sDisplay, getRequest(), getUser());
	}
	private PostDepositsLinesController createController(Deposit deposit) {
		PostDepositsLinesController controller = new PostDepositsLinesController(this, "Row");
		controller.setIsDocumentBlock(true);
		controller.setModel(deposit);

		return controller;
	}
	
	private void btnFilter_OnClick() throws Exception {
		redirect("~/incAccounting?App=PostDeposits&Status=" + cboStatus.getValue());		
	}
	private void btnProcess_OnClick() throws Exception {
		try {
			getUser().login().begin(true);

			for(PostDepositsLinesController controller: lstControllers) {
				boolean isChecked = controller.getIsPosted();
				Deposit obj = (Deposit) controller.getModel();
				boolean isPosted = obj.getPostedTransactionsGuid() != null;
				// Checked	WasPosted
				//	T			T		==> Do Nothing
				//	T			F		==> Post
				//	F			T		==> Unpost
				//	F			F		==> Do Nothing
				if(isChecked && !isPosted)
					obj.post(getUser().login());
				if(!isChecked && isPosted)
					obj.unpost(getUser().login());
			}
			
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "67f46beb9fc34429862ece42cf5227b1");
			addError("Post", ex.getMessage());
			return;
		}
		
		btnFilter_OnClick();
	}	
}
