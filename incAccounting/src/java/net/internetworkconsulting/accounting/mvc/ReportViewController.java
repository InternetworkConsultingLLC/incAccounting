package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Report;
import net.internetworkconsulting.accounting.entities.ReportFilter;
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
		setDocument(new Template(readTemplate("~/templates/ReportView.html"),  new HtmlSyntax()));

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
		
		if(objModel.loadFilters(getUser().login(), ReportFilter.class, false).size() > 0) {
			ButtonTag btnLoad = new ButtonTag(this, "Load");
			btnLoad.setValue("Load");
			btnLoad.addOnClickEvent(new Event() { public void handle() throws Exception { btnLoad_OnClick(); } });
		}
				
		litOutput = new LiteralTag(this, "Output");
	}
	public History createHistory() throws Exception {
		Report objModel = (Report) getModel();
		
		String sDisplay = "New Report";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getTitle();

		return new History(sDisplay, getRequest(), getUser());
	}
	private Report loadModel() throws Exception {
		Report objModel = null;

		String sTemp = getRequest().getParameter("GUID");
		if(sTemp != null)
			objModel = Report.loadByGuid(getUser().login(), Report.class, sTemp);

		if(objModel == null) {
			sTemp = getRequest().getParameter(Report.DISPLAY_NAME);
			objModel = Report.loadByDisplayName(getUser().login(), sTemp);
		}

		if(objModel == null)
			throw new Exception("Could not load the report by GUID or Display Name!");
		
		setModel(objModel);
		return objModel;
	}
	public void beforePopulate() throws Exception {
		Report objModel = (Report) getModel();
		if(
			objModel.getAutoLoad() || 
			objModel.loadFilters(getUser().login(), ReportFilter.class, false).size() == 0 || 
			(!getIsPostback() && getRequest().getParameter("AutoLoad") != null)
		) {
			btnLoad_OnClick();
		}
	}

	
	
	public void btnLoad_OnClick() throws Exception {				
		Report objModel = (Report) getModel();		
		litOutput.setValue(objModel.generate(getUser().login()));		
	}
}
