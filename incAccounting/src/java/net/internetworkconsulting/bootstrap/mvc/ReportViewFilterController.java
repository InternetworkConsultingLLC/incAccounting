package net.internetworkconsulting.bootstrap.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.ReportFilter;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.DateTag;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.Tag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class ReportViewFilterController extends Controller {
	private LabelTag lblPrompt;
	private Tag myTag;
	private ReportFilter objModel;
		
	public ReportViewFilterController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		objModel = (ReportFilter) getModel();
		
		lblPrompt = new LabelTag(this, "Filter " + ReportFilter.PROMPT);
		lblPrompt.bind(objModel, ReportFilter.PROMPT);
		
		myTag = null;
		if(objModel.getDataType().equals(ReportFilter.DT_DATE)) {
			myTag = new DateTag(this, "Filter " + ReportFilter.VALUE);
		} else if(objModel.getDataType().equals(ReportFilter.DT_DECIMAL)) {
			myTag = new TextTag(this, "Filter " + ReportFilter.VALUE);
		} else if(objModel.getDataType().equals(ReportFilter.DT_ENUM)) {
			ComboTag cbo = new ComboTag(this, "Filter " + ReportFilter.VALUE);
			myTag = cbo;
		} else if(objModel.getDataType().equals(ReportFilter.DT_INTEGER)) {
			myTag = new TextTag(this, "Filter " + ReportFilter.VALUE);
		} else if(objModel.getDataType().equals(ReportFilter.DT_TEXT)) {
			myTag = new TextTag(this, "Filter " + ReportFilter.VALUE);
		} else
			throw new Exception("The report filter's data type is invalid: " + objModel.getDataType() + "!");
		
		List<Row> lstResults = null;
		if(objModel.getQuery() != null) {
			Statement stmt = new Statement(objModel.getQuery());
			lstResults = getUser().login().load(Row.class, stmt);
		}
		
		if(lstResults != null && lstResults.size() > 0) {
			if(ReportFilter.DT_ENUM.equals(objModel.getDataType())) {
				// if combo, load options and set on combo
				if(!lstResults.get(0).getColumns().containsKey("Display"))
					throw new Exception("The enum type's query did not have a 'Display' column!");
				if(!lstResults.get(0).getColumns().containsKey("Value"))
					throw new Exception("The enum type's query did not have a 'Value' column!");

				List<Option> lstOptions = new LinkedList<Option>();
				for(Row r : lstResults) {
					Option opt = new Option();
					opt.setDisplay(r.get("Display").toString());
					opt.setValue(r.get("Value").toString());
					lstOptions.add(opt);
				}

				ComboTag cbo = (ComboTag) myTag;
				cbo.setOptions(lstOptions);
			} else {
				// not combo, run query and load results as value
				Object obj = lstResults.get(0).get("Value");
				objModel.setValue(Statement.convertObjectToString(obj, null));
			}
		}
		
		myTag.bind(objModel, ReportFilter.VALUE);
	}
	public History createHistory() throws Exception { return null; }
}