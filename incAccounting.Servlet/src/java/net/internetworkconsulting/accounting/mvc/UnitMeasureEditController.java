package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Conversion;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class UnitMeasureEditController extends EditController {
	public UnitMeasureEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }


	public void handleDeleteRow(String guid) throws Exception {
		UnitMeasure objModel = UnitMeasure.loadByGuid(getUser().login(), UnitMeasure.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(UnitMeasure.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return UnitMeasure.loadByGuid(getUser().login(), UnitMeasure.class, guid);
	}
	public Object handleNewRow() throws Exception {
		UnitMeasure objModel = new UnitMeasure();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		UnitMeasure objModel = (UnitMeasure) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/UnitMeasure.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, UnitMeasure.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel);
		
		TextTag txtName = new TextTag(this, UnitMeasure.DISPLAY_NAME);
		txtName.bind(objModel);
		
		TextTag txtAbbreviation = new TextTag(this, UnitMeasure.ABBREVIATION);
		txtAbbreviation.bind(objModel);
		
		CheckTag chkAllowed = new CheckTag(this, UnitMeasure.IS_ALLOWED);
		chkAllowed.bind(objModel);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		ButtonTag btnAdd = new ButtonTag(this, "Add Conversion");
		btnAdd.setValue("Add Conversion");
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		// this is doen once to cache a fresh copy for use on each conversion
		// rather than cycling back to the database x times.
		UnitMeasure.loadOptions(getUser().login(), false);
		
		List<Conversion> lstConversion = objModel.loadLeftConversions(getUser().login(), Conversion.class, !getIsPostback());
		for(Conversion conv: lstConversion)
			createController(conv);
	}
	private UnitMeasuresConversionsController createController(Conversion conv) throws Exception {
		UnitMeasure objModel = (UnitMeasure) getModel();

		UnitMeasuresConversionsController umecc = new UnitMeasuresConversionsController(this, "Conversion");
		umecc.setModel(conv);
		umecc.setIsDocumentBlock(true);
		umecc.setLeftUnitMeasure(objModel);
		
		return umecc;		
	}
	public History createHistory() throws Exception {
		UnitMeasure objModel = (UnitMeasure) getModel();
		
		String sDisplay = "New Unit Measure";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	private void btnAdd_OnClick() throws Exception {
		UnitMeasure objModel = (UnitMeasure) getModel();
		
		Conversion conv = new Conversion();
		conv.initialize();
		conv.setLeftUnitMeasuresGuid(objModel.getGuid());
		objModel.loadLeftConversions(getUser().login(), Conversion.class, !getIsPostback()).add(conv);
		
		UnitMeasuresConversionsController controller = createController(conv);
		doCreateControls(controller, false);
	}
	private void btnSave_OnClick() throws Exception {
		UnitMeasure objModel = (UnitMeasure) getModel();
		try {
			getUser().login().begin(true);
			getUser().login().save(UnitMeasure.TABLE_NAME, objModel);
			getUser().login().save(Conversion.TABLE_NAME, objModel.loadLeftConversions(getUser().login(), Conversion.class, false));
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Save Error", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=UnitMeasure&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
