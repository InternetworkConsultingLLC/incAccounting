package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionTypesRow;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class PostDocumentsController extends Controller {
	private ComboTag cboType;
	private ComboTag cboStatus;
	private List<net.internetworkconsulting.accounting.entities.Document> objModel;
	private LinkedList<PostDocumentsLinesController> lstControllers;
	public PostDocumentsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/PostDocuments.html"), new HtmlSyntax()));

		String type_guid = getRequest().getParameter("Document Types GUID");
		if(type_guid != null && type_guid.equals("null"))
			type_guid = null;
		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;

		
		cboType = new ComboTag(this, "Filter Types");
		cboType.setOptions(DocumentType.loadPostableOptions(getUser().login(), true));
		cboType.setValue(type_guid);
		
		cboStatus = new ComboTag(this, "Filter Status");
		List<Option> lstStatuses = new LinkedList<>();
		lstStatuses.add(new Option("Not Posted", ""));
		lstStatuses.add(new Option("Posted", "posted"));
		cboStatus.setOptions(lstStatuses);
		cboStatus.setValue(status);
		
		objModel = (List<net.internetworkconsulting.accounting.entities.Document>) model;
		if(!getIsPostback()) {
			if(status == null || status.equals("") || status.equals("null"))
				objModel = Document.loadByTypeAndPosted(getUser().login(), type_guid, false);
			else
				objModel = Document.loadByTypeAndPosted(getUser().login(), type_guid, true);	
			
			cboType.setValue(type_guid);
		}
		setModel(objModel);
		
		lstControllers = new LinkedList<>();
		for(Document doc: objModel)
			lstControllers.add(createController(doc));
			
		ButtonTag btnFilter = new ButtonTag(this, "Filter");
		btnFilter.addOnClickEvent(new Event() { public void handle() throws Exception { btnFilter_OnClick(); } });
		
		ButtonTag btnProcess = new ButtonTag(this, "Process");
		btnProcess.addOnClickEvent(new Event() { public void handle() throws Exception { btnProcess_OnClick(); } });
	}
	public History createHistory() throws Exception {
		String sDisplay = "";
		
		String type_guid = getRequest().getParameter("Document Types GUID");
		if(type_guid != null && type_guid.equals("null"))
			type_guid = null;
		String status = getRequest().getParameter("Status");
		if(status != null && status.equals("null"))
			status = null;

		if(status == null || !status.equals("posted"))
			if(type_guid == null)
				sDisplay = "All Unposted";
			else {
				TransactionTypesRow objType = TransactionType.loadByGuid(getUser().login(), TransactionType.class, type_guid);
				sDisplay = "Unposted " + objType.getName();
			}
		else
			if(type_guid == null)
				sDisplay = "All Posted";
			else {
				TransactionTypesRow objType = TransactionType.loadByGuid(getUser().login(), TransactionType.class, type_guid);
				sDisplay = "Posted " + objType.getName();
			}			
		
		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnFilter_OnClick() throws Exception {		
		redirect("~/incAccounting?App=PostDocuments&Document Types GUID=" + cboType.getValue() + "&Status=" + cboStatus.getValue());		
	}
	private void btnProcess_OnClick() throws Exception {
		try {
			getUser().login().begin(true);

			for(PostDocumentsLinesController pdlc: lstControllers) {
				boolean isChecked = pdlc.getIsPosted();
				Document doc = (Document) pdlc.getModel();
				boolean isPosted = doc.getPostedAccountsGuid() != null && doc.getPostedTransactionsGuid() != null;
				// Checked	WasPosted
				//	T			T		==> Do Nothing
				//	T			F		==> Post
				//	F			T		==> Unpost
				//	F			F		==> Do Nothing
				if(isChecked && !isPosted)
					doc.post(getUser().login());
				if(!isChecked && isPosted)
					doc.unpost(getUser().login());
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
	private PostDocumentsLinesController createController(Document doc) {
		PostDocumentsLinesController pdlc = new PostDocumentsLinesController(this, "Row");
		pdlc.setIsDocumentBlock(true);
		pdlc.setModel(doc);

		return pdlc;
	}
}
