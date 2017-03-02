package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Report;
import net.internetworkconsulting.bootstrap.entities.ReportBlock;
import net.internetworkconsulting.bootstrap.entities.ReportFilter;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReportEditController extends Controller {
	public ReportEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/ReportEdit.html"), new HtmlSyntax()));
		
		Report objModel = (Report) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter(Report.GUID);
			if(sGuid != null) {
				objModel = Report.loadByGuid(getUser().login(), Report.class, sGuid);
			} else {
				objModel = new Report();
				objModel.initialize();
			}				
		}
		setModel(objModel);
		
		TextTag txtGuid = new TextTag(this, Report.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel, Report.GUID);
		
		TextTag txtName = new TextTag(this, Report.DISPLAY_NAME);
		txtName.bind(objModel, Report.DISPLAY_NAME);

		TextTag txtTitle = new TextTag(this, Report.TITLE);
		txtTitle.bind(objModel, Report.TITLE);
		
		TextAreaTag txtTemplate = new TextAreaTag(this, Report.HTML_TEMPLATE);
		txtTemplate.setRows("10");
		txtTemplate.bind(objModel, Report.HTML_TEMPLATE);
		
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
		
		List<ReportBlock> lstBlocks = objModel.loadBlocks(getUser().login(), ReportBlock.class, false);
		for(ReportBlock rb: lstBlocks)
			createBlock(rb);
		
		List<ReportFilter> lstFilters = objModel.loadFilters(getUser().login(), ReportFilter.class, false);
		for(ReportFilter rf: lstFilters)
			createFilter(rf);
	}
	private void createBlock(ReportBlock rb) {
		ReportBlockListController rebc = new ReportBlockListController(this, "Block");
		rebc.setModel(rb);
		rebc.setIsDocumentBlock(true);
	}
	private ReportEditFilterController createFilter(ReportFilter rf) {
		ReportEditFilterController refc = new ReportEditFilterController(this, "Filter");
		refc.setModel(rf);
		refc.setIsDocumentBlock(true);
		return refc;
	}
	public History createHistory() throws Exception {
		Report objModel = (Report) getModel();
		
		String sDisplay = "Edit New Report";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = "Edit " + objModel.getDisplayName();

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
		redirect("~/incBootstrap?App=ReportEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnAddBlock_OnClick() throws Exception {
		Report objModel = (Report) getModel();
		redirect("~/incBootstrap?App=ReportBlockEdit&" + ReportBlock.REPORTS_GUID + "=" + objModel.getGuid());
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
		ReportEditFilterController controller = createFilter(rf);
		doCreateControls(controller, false);
	}
	private void btncopy_OnClick() throws Exception {
		Report objModel = (Report) getModel();

		Report objCopy = objModel.createCopy(getUser().login());
	}
}
