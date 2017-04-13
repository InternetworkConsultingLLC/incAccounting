package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Report;
import net.internetworkconsulting.accounting.entities.ReportBlock;
import net.internetworkconsulting.accounting.entities.ReportFilter;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReportController extends EditController {
	public ReportController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Report objModel = Report.loadByGuid(getUser().login(), Report.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Report.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Report.loadByGuid(getUser().login(), Report.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Report objModel = new Report();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Report objModel = (Report) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Report.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, Report.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel, Report.GUID);
		
		TextTag txtName = new TextTag(this, Report.DISPLAY_NAME);
		txtName.bind(objModel, Report.DISPLAY_NAME);

		TextTag txtTitle = new TextTag(this, Report.TITLE);
		txtTitle.bind(objModel, Report.TITLE);
		
		TextAreaTag txtTemplate = new TextAreaTag(this, Report.HTML_TEMPLATE);
		txtTemplate.bind(objModel, Report.HTML_TEMPLATE);
		
		CheckTag chkAutoLoad = new CheckTag(this, Report.AUTO_LOAD);
		chkAutoLoad.bind(objModel, Report.AUTO_LOAD);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });		
		
		ButtonTag btnAddBlock = new ButtonTag(this, "Add Block");
		btnAddBlock.setValue("Add Block");
		btnAddBlock.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddBlock_OnClick(); } });		
		
		ButtonTag btnView = new ButtonTag(this, "View");
		btnView.setValue("View");
		btnView.addOnClickEvent(new Event() { public void handle() throws Exception { btnView_OnClick(); } });
		
		ButtonTag btnAddFilter = new ButtonTag(this, "Add Filter");
		btnAddFilter.setValue("Add Filter");
		btnAddFilter.addOnClickEvent(new Event() { public void handle() throws Exception { btnAddFilter_OnClick(); } });		

		if(objModel.getRowState() == RowState.NA) {
			ButtonTag btnCopy = new ButtonTag(this, "Copy");
			btnCopy.setValue("Copy");
			btnCopy.addOnClickEvent(new Event() { public void handle() throws Exception { btnCopy_OnClick(); } });
		}
		
		List<ReportBlock> lstBlocks = objModel.loadBlocks(getUser().login(), ReportBlock.class, false);
		for(ReportBlock rb: lstBlocks)
			createBlock(rb);
		
		List<ReportFilter> lstFilters = objModel.loadFilters(getUser().login(), ReportFilter.class, false);
		for(ReportFilter rf: lstFilters)
			createFilter(rf);
	}
	private void createBlock(ReportBlock rb) {
		ChildReportBlocksController rebc = new ChildReportBlocksController(this, "Block");
		rebc.setModel(rb);
		rebc.setIsDocumentBlock(true);
	}
	private ChildReportFiltersController createFilter(ReportFilter rf) {
		ChildReportFiltersController refc = new ChildReportFiltersController(this, "Filter");
		refc.setModel(rf);
		refc.setIsDocumentBlock(true);
		return refc;
	}
	public History createHistory() throws Exception {
		Report objModel = (Report) getModel();
		
		String sDisplay = "Edit New Report";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnSave_OnClick() throws Exception {
		Report objModel = (Report) getModel();
		try {
			getUser().login().begin(true);

			objModel.setHtmlTemplate(objModel.getHtmlTemplate().replace(getRootUrl(), "~/"));

			getUser().login().save(Report.TABLE_NAME, objModel);
			getUser().login().save(ReportFilter.TABLE_NAME, objModel.loadFilters(getUser().login(), ReportFilter.class, false));
			getUser().login().save(ReportBlock.TABLE_NAME, objModel.loadBlocks(getUser().login(), ReportBlock.class, false));

			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}
		redirect("~/incBootstrap?App=Report&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAddBlock_OnClick() throws Exception {
		Report objModel = (Report) getModel();
		redirect("~/incBootstrap?App=ReportBlock&" + ReportBlock.REPORTS_GUID + "=" + objModel.getGuid());
	}
	private void btnView_OnClick() throws Exception {
		Report objModel = (Report) getModel();
		redirect("~/incBootstrap?App=ReportView&GUID=" + objModel.getGuid());
	}
	private void btnAddFilter_OnClick() throws Exception {
		Report objModel = (Report) getModel();
		
		ReportFilter rf = new ReportFilter();
		rf.initialize();
		rf.setReportsGuid(objModel.getGuid());

		objModel.loadFilters(getUser().login(), ReportFilter.class, false).add(rf);
		ChildReportFiltersController controller = createFilter(rf);
		doCreateControls(controller, false);
	}
	private void btnCopy_OnClick() throws Exception {
		Report objModel = (Report) getModel();

		Report objCopy = objModel.handleCopy(getUser().login());
		objCopy.handleSave(getUser().login());

		redirect("~/incBootstrap?App=Report&GUID=" + objCopy.getGuid());
	}
}
