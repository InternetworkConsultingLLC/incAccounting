package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Report;
import net.internetworkconsulting.accounting.entities.ReportBlock;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReportBlockController extends EditController {
	public ReportBlockController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		ReportBlock objModel = ReportBlock.loadByGuid(getUser().login(), ReportBlock.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(ReportBlock.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return ReportBlock.loadByGuid(getUser().login(), ReportBlock.class, guid);
	}
	public Object handleNewRow() throws Exception {
		ReportBlock objModel = new ReportBlock();
		objModel.initialize();

		String sParentGuid = getRequest().getParameter(ReportBlock.PARENT_BLOCK_GUID);
		String sReportGuid = getRequest().getParameter(ReportBlock.REPORTS_GUID);

		if(sParentGuid != null && sReportGuid != null)
			throw new Exception("A block can either be a child to another block, or child to a report, but not both!");
		if(sParentGuid == null && sReportGuid == null)
			throw new Exception("A block must be either be a child to another block or report!");
		
		objModel.setParentBlockGuid(sParentGuid);
		objModel.setReportsGuid(sReportGuid);
		
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		ReportBlock objModel = (ReportBlock) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/ReportBlock.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, ReportBlock.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel);
		
		ComboTag cboReport = new ComboTag(this, ReportBlock.REPORTS_GUID);
		cboReport.setOptions(Report.loadOptions(getUser().login(), true));
		cboReport.bind(objModel);
		
		ComboTag cboBlock = new ComboTag(this, ReportBlock.PARENT_BLOCK_GUID);
		cboBlock.setOptions(ReportBlock.loadOptions(getUser().login(), true));
		cboBlock.bind(objModel);
		
		TextTag txtPriority = new TextTag(this, ReportBlock.PRIORITY);
		txtPriority.setMaxLength("4");
		txtPriority.bind(objModel);
		
		TextTag txtName = new TextTag(this, ReportBlock.NAME);
		txtName.bind(objModel);
		
		TextAreaTag txtQuery = new TextAreaTag(this, ReportBlock.SQL_QUERY);
		txtQuery.setRows("12");
		txtQuery.setCols("40");
		txtQuery.bind(objModel);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });

		if(objModel.getRowState() == RowState.NA) {
			ButtonTag btnAddBlock = new ButtonTag(this, "Add Block");
			btnAddBlock.setValue("Add Block");
			btnAddBlock.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddBlock_OnClick(); } });
		}
		
		if(objModel.getReportsGuid() != null) {
			ButtonTag btnOpenReport = new ButtonTag(this, "Open Report");
			btnOpenReport.setValue("Open Report");
			btnOpenReport.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpenReport_OnClick(); } });
		}

		if(objModel.getParentBlockGuid() != null) {
			ButtonTag btnOpenParent = new ButtonTag(this, "Open Block");
			btnOpenParent.setValue("Open Block");
			btnOpenParent.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpenParent_OnClick(); } });
		}
		
		List<ReportBlock> lstBlocks = objModel.loadChildren(getUser().login(), ReportBlock.class, !getIsPostback());
		for(ReportBlock rb: lstBlocks)
			createBlock(rb);
	}
	private void createBlock(ReportBlock rb) {
		ChildReportBlocksController rebc = new ChildReportBlocksController(this, "Block");
		rebc.setModel(rb);
		rebc.setIsDocumentBlock(true);
	}
	public History createHistory() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		
		String sDisplay = "New Block";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnOpenReport_OnClick() throws Exception { 
		ReportBlock objModel = (ReportBlock) getModel();		
		if(objModel.getReportsGuid() == null)
			addError("Error", "This block is not a report's direct decendent!");
		else
			redirect("~/incBootstrap?App=Report&GUID=" + objModel.getReportsGuid());
	}
	private void btnOpenParent_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		if(objModel.getParentBlockGuid() == null)
			addError("Error", "This block is not another block's direct decendent!");
		else
			redirect("~/incBootstrap?App=ReportBlock&GUID=" + objModel.getParentBlockGuid());
	}
	private void btnSave_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		try {
			getUser().login().begin(true);

			getUser().login().save(ReportBlock.TABLE_NAME, objModel);
			getUser().login().save(ReportBlock.TABLE_NAME, objModel.loadChildren(getUser().login(), ReportBlock.class, !getIsPostback()));

			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}

		redirect("~/incBootstrap?App=ReportBlock&GUID=" + objModel.getGuid());
	}
	private void btnAddBlock_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		redirect("~/incBootstrap?App=ReportBlock&" + ReportBlock.PARENT_BLOCK_GUID + "=" + objModel.getGuid());
	}
}
