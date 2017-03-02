package net.internetworkconsulting.bootstrap.mvc;

import net.internetworkconsulting.bootstrap.entities.ReportFilter;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.TextAreaTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class ReportEditFilterController extends Controller {
	public ReportEditFilterController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }	
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		ReportFilter objModel = (ReportFilter) getModel();
		
		LiteralTag litGuid = new LiteralTag(this, "Filter " + ReportFilter.GUID);
		litGuid.bind(objModel, ReportFilter.GUID);
		
		CheckTag chkDelete = new CheckTag(this, "Filter " + ReportFilter.IS_DELETED);
		chkDelete.setName(ReportFilter.IS_DELETED + objModel.getGuid());
		chkDelete.bind(objModel, ReportFilter.IS_DELETED);

		TextTag txtPrompt = new TextTag(this, "Filter " + ReportFilter.PROMPT);
		txtPrompt.setName(ReportFilter.PROMPT + objModel.getGuid());
		txtPrompt.bind(objModel, ReportFilter.PROMPT);
		
		ComboTag cboDataType = new ComboTag(this, "Filter " + ReportFilter.DATA_TYPE);
		cboDataType.setName(ReportFilter.DATA_TYPE + objModel.getGuid());
		cboDataType.setOptions(objModel.getDataTypeOptions());
		cboDataType.bind(objModel, ReportFilter.DATA_TYPE);
		
		TextAreaTag txtQuery = new TextAreaTag(this, "Filter " + ReportFilter.QUERY);
		txtQuery.setName(ReportFilter.QUERY + objModel.getGuid());
		txtQuery.bind(objModel, ReportFilter.QUERY);
	}
	public History createHistory() throws Exception { return null; }
}