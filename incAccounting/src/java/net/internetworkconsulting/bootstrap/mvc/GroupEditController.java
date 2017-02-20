package net.internetworkconsulting.bootstrap.mvc;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.bootstrap.entities.Group;
import net.internetworkconsulting.bootstrap.entities.Membership;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class GroupEditController extends Controller {
	private LinkedList<GroupEditMembershipController> lstMemberships;

	public GroupEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read_url("~/templates/GroupEdit.html"), new HtmlSyntax()));
		
		Group objModel = (Group) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter(Group.GUID);
			if(sGuid != null)
				objModel = Group.loadByGuid(getUser().login(), Group.class, sGuid);
			else {
				objModel = new Group();
				objModel.initialize();
			}
		}
		setModel(objModel);

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
			GroupEditMembershipController gemc = new GroupEditMembershipController(this, "User");
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
		for(GroupEditMembershipController gemc: lstMemberships) {
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
		redirect("~/incBootstrap?App=GroupEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}