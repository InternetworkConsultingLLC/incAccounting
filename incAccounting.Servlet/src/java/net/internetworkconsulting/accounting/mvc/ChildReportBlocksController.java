package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.ReportBlock;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class ChildReportBlocksController extends Controller {
	public ChildReportBlocksController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }	

	public boolean getEnforceSecurity() { return true; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		ReportBlock objModel = (ReportBlock) getModel();
		
		CheckTag chkIsDeleted = new CheckTag(this, "Block " + ReportBlock.IS_DELETED);
		chkIsDeleted.setName(ReportBlock.IS_DELETED + objModel.getGuid());
		chkIsDeleted.bind(objModel, ReportBlock.IS_DELETED);
		
		TextTag txtPriority = new TextTag(this, "Block " + ReportBlock.PRIORITY);
		txtPriority.setName(ReportBlock.PRIORITY + objModel.getGuid());
		txtPriority.bind(objModel, ReportBlock.PRIORITY);
		
		TextTag txtName = new TextTag(this, "Block " + ReportBlock.NAME);
		txtName.setName(ReportBlock.NAME + objModel.getGuid());
		txtName.bind(objModel, ReportBlock.NAME);
		
		TextTag txtGuid = new TextTag(this, "Block " + ReportBlock.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel, ReportBlock.GUID);
		
		ButtonTag btnOpen = new ButtonTag(this, "Block Open Block");
		btnOpen.setName("Block Open Block" + objModel.getGuid());
		btnOpen.setValue("Open");
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	public History createHistory() throws Exception { return null; }
	
	private void btnOpen_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		redirect("~/incBootstrap?App=ReportBlock&GUID=" + objModel.getGuid());
	}
}
