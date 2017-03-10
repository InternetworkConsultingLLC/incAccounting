package net.internetworkconsulting.accounting.mvc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Group;
import net.internetworkconsulting.accounting.entities.Membership;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class GroupController extends EditController {
	private LinkedList<GroupMembershipController> lstMemberships;

	public GroupController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Group objModel = Group.loadByGuid(getUser().login(), Group.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Group.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Group.loadByGuid(getUser().login(), Group.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Group objModel = new Group();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Group objModel = (Group) handleNonPostbackActions(model);
		setDocument(new Template(read_url("~/templates/Group.html"), new HtmlSyntax()));

		TextTag txtGuid = new TextTag(this, Group.GUID);
		txtGuid.setIsReadOnly(true);
		txtGuid.bind(objModel, Group.GUID);
		
		TextTag txtDisplay = new TextTag(this, Group.DISPLAY_NAME);
		txtDisplay.bind(objModel, Group.DISPLAY_NAME);
		
		TextTag txtEmail = new TextTag(this, Group.EMAIL_ADDRESS);
		txtEmail.bind(objModel, Group.EMAIL_ADDRESS);
		
		CheckTag chkIsAllowed = new CheckTag(this, Group.IS_ALLOWED);
		chkIsAllowed.bind(objModel, Group.IS_ALLOWED);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		// organize groups members by user guid
		List<Membership> lstMembers = objModel.loadMemberships(getUser().login(), Membership.class, false);
		HashMap<String, Membership> hmMembershipsByUser = new HashMap<>();
		for(Membership mbr: lstMembers)
			hmMembershipsByUser.put(mbr.getUsersGuid(), mbr);
		
		// get list of all users by guid
		List<Option> lstUserOptions = User.loadOptions(getUser().login(), true);
		lstUserOptions.remove(0);
		HashMap<String, Option> hmUsersByGuid = new HashMap<>();
		for(Option opt: lstUserOptions)
			hmUsersByGuid.put(opt.getValue(), opt);
		
		// create group membership controllers for each user
		lstMemberships = new LinkedList<>();
		for(Option user: lstUserOptions) {
			GroupMembershipController gemc = new GroupMembershipController(this, "User");
			gemc.setIsDocumentBlock(true);
			gemc.setModelUser(user);
			gemc.setGroupGuid(objModel.getGuid());
			if(hmMembershipsByUser.containsKey(user.getValue()))
				gemc.setModel(hmMembershipsByUser.get(user.getValue()));
			else
				gemc.setModel(null);
			lstMemberships.add(gemc);
		}
		
	}
	public History createHistory() throws Exception {
		Group objModel = (Group) getModel();
		
		String sDisplay = "New Group";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objModel.getDisplayName();

		return new History(sDisplay, getRequest(), getUser());
	}
	
	public void btnSave_OnClick() throws Exception {
		Group objModel = (Group) getModel();		
		
		// get updated model from membership controller
		List<Membership> lstMembershipsToSave = new LinkedList<>();
		for(GroupMembershipController gemc: lstMemberships) {
			Membership mbr = (Membership) gemc.getModel();
			if(mbr != null)
				lstMembershipsToSave.add(mbr);
		}				
		
		// save
		try {
			getUser().login().begin(true);

			getUser().login().save(Group.TABLE_NAME, objModel);
			if(!objModel.getGuid().equals(Group.EVERYONE_GUID))
				getUser().login().save(Membership.TABLE_NAME, lstMembershipsToSave);

			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			addError("Error", ex.getMessage());
			return;
		}
		
		// all good, redirect
		redirect("~/incBootstrap?App=Group&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}