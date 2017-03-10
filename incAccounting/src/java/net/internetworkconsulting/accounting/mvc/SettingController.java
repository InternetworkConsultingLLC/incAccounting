package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.Setting;
import net.internetworkconsulting.accounting.entities.User;
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

public class SettingController extends EditController {
	public SettingController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void handleDeleteRow(String guid) throws Exception {
		Setting objModel = Setting.loadByGuid(getUser().login(), Setting.class, guid);

		if(objModel.getUsersGuid() == null)
			throw new Exception("You cannot delete system wide settings!");

		objModel.setIsDeleted(true);
		getUser().login().save(Setting.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Setting.loadByGuid(getUser().login(), Setting.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Setting objModel = new Setting();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {
		Setting objModel = (Setting) handleNonPostbackActions(model);
		setDocument(new Template(read_url("~/templates/Setting.html"), new HtmlSyntax()));
		
		TextTag txtGuid = new TextTag(this, Setting.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(getModel(), Setting.GUID);
		
		ComboTag cboUser = new ComboTag(this, Setting.USERS_GUID);
		cboUser.setOptions(User.loadOptions(getUser().login(), true));
		cboUser.bind(getModel(), Setting.USERS_GUID);
		
		TextTag txtKey = new TextTag(this, Setting.KEY);
		txtKey.bind(getModel(), Setting.KEY);
		
		ComboTag cboType = new ComboTag(this, Setting.TYPE);
		List<Option> lstOptions = new LinkedList<>();
		lstOptions.add(new Option("", ""));
		lstOptions.add(new Option("Boolean", Setting.TYPE_BOOLEAN));
		lstOptions.add(new Option("GUID", Setting.TYPE_GUID));
		lstOptions.add(new Option("Number", Setting.TYPE_NUMBER));
		lstOptions.add(new Option("String", Setting.TYPE_STRING));
		cboType.setOptions(lstOptions);
		cboType.bind(getModel(), Setting.TYPE);
		
		TextTag txtValue = new TextTag(this, Setting.VALUE);
		txtValue.bind(getModel(), Setting.VALUE);
		
		TextAreaTag txtQuery = new TextAreaTag(this, Setting.OPTION_QUERY);
		txtQuery.setRows("12");
		txtQuery.setCols("40");
		txtQuery.bind(getModel(), Setting.OPTION_QUERY);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		Setting objModel = (Setting) getModel();
		
		String sDisplay = "New Setting";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getKey();

		return new History(sDisplay, getRequest(), getUser());
	}

	public boolean getEnforceSecurity() { return true; }

	private void btnSave_OnClick() throws Exception {
		Setting objModel = (Setting) getModel();
		try { 
			getUser().login().begin(true);
			getUser().login().save(Setting.TABLE_NAME, objModel); 
			getUser().login().commit(true);
		} 
		catch (Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}
		redirect("~/incBootstrap?App=Setting&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
