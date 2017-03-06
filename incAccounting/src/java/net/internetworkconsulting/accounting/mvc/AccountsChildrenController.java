package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;



public class AccountsChildrenController extends Controller {
	private ButtonTag btnOpen;

	public AccountsChildrenController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Account objModel = (Account) getModel();
		
		CheckTag chkDelete = new CheckTag(this, "Child", Account.IS_DELETED, objModel.getGuid(), objModel);
		TextTag txtSegment = new TextTag(this, "Child", Account.SEGMENT, objModel.getGuid(), objModel);
		TextTag txtNumber = new TextTag(this, "Child", Account.NUMBER, objModel.getGuid(), objModel);
		txtNumber.setIsReadOnly(true);
		TextTag txtName = new TextTag(this, "Child", Account.NAME, objModel.getGuid(), objModel);
		CheckTag chkAllowed = new CheckTag(this, "Child", Account.IS_ALLOWED, objModel.getGuid(), objModel);
		
//		LinkTag lnkOpen = new LinkTag(this, "Child Open");
//		lnkOpen.setValue("Open");
//		lnkOpen.setUrl("~/AccountEdit?GUID=" + objModel.getGuid());
		
		btnOpen = new ButtonTag(this, "Child Open");
		btnOpen.setName("Open" + objModel.getGuid());
		btnOpen.setValue("Open Child");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	public History createHistory() throws Exception { return null; }
	
	
	private void btnOpen_OnClick() throws Exception {
		Account objModel = (Account) getModel();
		redirect("~/incAccounting?App=Account&GUID=" + objModel.getGuid());
	}
	
	public void beforePopulate() throws Exception {
		super.beforePopulate();
		Account objModel = (Account) getModel();		
		btnOpen.setValue("Open " + objModel.getNumber());
	}
}
