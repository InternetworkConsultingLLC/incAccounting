package net.internetworkconsulting.accounting.mvc;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Group;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.Permission;
import net.internetworkconsulting.accounting.entities.Securable;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class SecurableController extends EditController {
	HashMap<String, Permission> hmGroupsPerms;
	
	public SecurableController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		throw new Exception("You cannot delete a securable!");
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Securable.loadByGuid(getUser().login(), Securable.class, guid);
	}
	public Object handleNewRow() throws Exception {
		throw new Exception("You cannot create a securable!");
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Securable objModel = (Securable) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/Securable.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, Securable.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(getModel(), Securable.GUID);
		
		TextTag txtDisplay = new TextTag(this, Securable.DISPLAY_NAME);
		txtDisplay.setIsReadOnly(true);
		txtDisplay.bind(getModel(), Securable.DISPLAY_NAME);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		createPermissions();
	}
	private void createPermissions() throws Exception {
		Securable objModel = (Securable) getModel();
		
		List<Permission> lstPerms = objModel.loadPermissions(getUser().login(), Permission.class, !getIsPostback());
		hmGroupsPerms = new HashMap<>();
		for(Permission perm: lstPerms)
			hmGroupsPerms.put(perm.getGroupsGuid(), perm);
		
		List<Option> lstGroups = Group.loadOptions(getUser().login(), false);
		lstGroups.remove(0);

		HashMap<String, String> hmGroups = new HashMap<>();
		for(Option group: lstGroups) {
			hmGroups.put(group.getValue(), group.getDisplay());
			
			Permission perm;
			if(!hmGroupsPerms.containsKey(group.getValue())) {
				perm = new Permission();
				perm.initialize();
				perm.setGroupsGuid(group.getValue());
				perm.setSecurablesGuid(objModel.getGuid());
				perm.setCanCreate(false);
				perm.setCanRead(false);
				perm.setCanUpdate(false);
				perm.setCanDelete(false);
				hmGroupsPerms.put(group.getValue(), perm);
			}
			perm = hmGroupsPerms.get(group.getValue());			

			if(group.getValue().equals(Group.ADMINISTRATORS_GUID)) {
				perm.setCanCreate(true);
				perm.setCanRead(true);
				perm.setCanUpdate(true);
				perm.setCanDelete(true);
			}
			
			ChildPermissionsController sepc = new ChildPermissionsController(this, "Securable");
			sepc.setModel(perm);
			sepc.setGroups(hmGroups);
			sepc.setIsDocumentBlock(true);			
		}
	}
	public History createHistory() throws Exception {
		Securable objModel = (Securable) getModel();
		
		String sDisplay = "New Securable";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}

	private void btnSave_OnClick() throws Exception {
		Securable objModel = (Securable) getModel();
		
		try {
			getUser().login().begin(true);
			
			// cannot change securable
			//getUser().login().save(Securable.TABLE_NAME, objModel);
			
			for(String groupGuid: hmGroupsPerms.keySet())
				getUser().login().save(Permission.TABLE_NAME, hmGroupsPerms.get(groupGuid));
			
			getUser().login().commit(true);
		} catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.toString());
			return;
		}
		
		redirect("~/incBootstrap?App=Securable&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}	
}
