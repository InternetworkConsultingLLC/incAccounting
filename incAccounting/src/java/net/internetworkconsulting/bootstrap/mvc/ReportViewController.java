package net.internetworkconsulting.bootstrap.mvc;

import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Report;
import net.internetworkconsulting.bootstrap.entities.ReportFilter;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ReportViewController extends Controller {
	private LiteralTag litOutput;
	public ReportViewController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public boolean getEnforceSecurity() { return true; }
	
	public void createControls(Template document, Object model) throws Exception {
		Report objModel = loadModel();
		setDocument(new Template(read_url("~/templates/ReportView.html"),  new HtmlSyntax()));

		LiteralTag litTitle = new LiteralTag(this, Report.TITLE);
		litTitle.setValue(objModel.getTitle());

		LiteralTag litName = new LiteralTag(this, Report.DISPLAY_NAME);
		litName.setValue(objModel.getDisplayName());
		
		List<ReportFilter> lstFilters = objModel.loadFilters(getUser().login(), ReportFilter.class, false);
		for(ReportFilter rf: lstFilters) {
			ReportViewFilterController rvfc = new ReportViewFilterController(this, "Filter");
			rvfc.setModel(rf);
			rvfc.setIsDocumentBlock(true);
		}
		
		ButtonTag btnLoad = new ButtonTag(this, "Load");
		btnLoad.setValue("Load");
		btnLoad.addOnClickEvent(new Event() { public void handle() throws Exception { btnLoad_OnClick(); } });
		
		litOutput = new LiteralTag(this, "Output");
		litOutput.setValue("");
	}
	public History createHistory() throws Exception {
		Report objModel = (Report) getModel();
		
		String sDisplay = "New Report";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	private Report loadModel() throws Exception {
		Report objModel = null;

		String sTemp = getRequest().getParameter("GUID");
		if(sTemp != null)
			objModel = Report.loadByGuid(getUser().login(), Report.class, sTemp);

		if(objModel == null) {
			sTemp = getRequest().getParameter(Report.DISPLAY_NAME);
			objModel = Report.loadByDisplayName(getUser().login(), Report.class, sTemp);
		}

		if(objModel == null)
			throw new Exception("Could not load the report by GUID or Display Name!");
		
		setModel(objModel);
		return objModel;
	}

	public void btnLoad_OnClick() throws Exception {				
		Report objModel = (Report) getModel();		
		litOutput.setValue(objModel.generate(getUser().login()));		
	}
}
