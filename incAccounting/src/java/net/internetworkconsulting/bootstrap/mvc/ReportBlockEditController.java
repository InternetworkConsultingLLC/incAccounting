package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Report;
import net.internetworkconsulting.bootstrap.entities.ReportBlock;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReportBlockEditController extends Controller {
	public ReportBlockEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/ReportBlockEdit.html"), new HtmlSyntax()));
		
		ReportBlock objModel = (ReportBlock) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter(ReportBlock.GUID);
			String sParentGuid = getRequest().getParameter(ReportBlock.PARENT_BLOCK_GUID);
			String sReportGuid = getRequest().getParameter(ReportBlock.REPORTS_GUID);
			if(sGuid != null) {
				objModel = ReportBlock.loadByGuid(getUser().login(), ReportBlock.class, sGuid);
			} else if(sParentGuid != null) {
				objModel = new ReportBlock();
				objModel.initialize();
				objModel.setParentBlockGuid(sParentGuid);
			} else if(sReportGuid != null) {
				objModel = new ReportBlock();
				objModel.initialize();
				objModel.setReportsGuid(sReportGuid);
			} else
				throw new Exception("You must provide a blocks GUID, reports GUID, or the GUID of the parent block!");
		}
		setModel(objModel);
		
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
		
		ButtonTag btnAddBlock = new ButtonTag(this, "Add Block");
		btnAddBlock.setValue("Add Block");
		btnAddBlock.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddBlock_OnClick(); } });
		
		ButtonTag btnOpenReport = new ButtonTag(this, "Open Report");
		btnOpenReport.setValue("Open");
		btnOpenReport.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpenReport_OnClick(); } });

		ButtonTag btnOpenParent = new ButtonTag(this, "Open Block");
		btnOpenParent.setValue("Open");
		btnOpenParent.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpenParent_OnClick(); } });
		
		List<ReportBlock> lstBlocks = objModel.loadChildren(getUser().login(), ReportBlock.class, false);
		for(ReportBlock rb: lstBlocks)
			createBlock(rb);
	}
	private void createBlock(ReportBlock rb) {
		ReportBlockListController rebc = new ReportBlockListController(this, "Block");
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
			redirect("~/incBootstrap?App=ReportEdit&GUID=" + objModel.getReportsGuid());
	}
	private void btnOpenParent_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		if(objModel.getParentBlockGuid() == null)
			addError("Error", "This block is not another block's direct decendent!");
		else
			redirect("~/incBootstrap?App=ReportBlockEdit&GUID=" + objModel.getParentBlockGuid());
	}
	private void btnSave_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		try {
			getUser().login().begin(true);

			getUser().login().save(ReportBlock.TABLE_NAME, objModel);
			getUser().login().save(ReportBlock.TABLE_NAME, objModel.loadChildren(getUser().login(), ReportBlock.class, false));

			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}

		if(objModel.getParentBlockGuid() != null)
			redirect("~/incBootstrap?App=ReportBlockEdit&GUID=" + objModel.getParentBlockGuid());
		else if(objModel.getReportsGuid() != null)
			redirect("~/incBootstrap?App=ReportEdit&GUID=" + objModel.getReportsGuid());
		else
			throw new Exception("This block is not a child to another block or a report!");
	}
	private void btnAddBlock_OnClick() throws Exception {
		ReportBlock objModel = (ReportBlock) getModel();
		redirect("~/incBootstrap?App=ReportBlockEdit&" + ReportBlock.PARENT_BLOCK_GUID + "=" + objModel.getGuid());
	}
}
