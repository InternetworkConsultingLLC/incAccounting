package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Membership;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.User;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LiteralTag;
import net.internetworkconsulting.template.Template;

public class GroupMembershipController extends Controller {
	private LiteralTag lblUser;
	private CheckTag chkIncluded;
	public GroupMembershipController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	private Option myOptionUser = null;
	void setModelUser(Option value) { myOptionUser = value; }

	private String sGroupGuid = null;
	public String getGroupGuid() { return sGroupGuid; }
	public void setGroupGuid(String value) { sGroupGuid = value; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Membership objModel = (Membership) super.getModel();
		
		lblUser = new LiteralTag(this, "User " + User.SQL_USER);
		lblUser.setName(User.SQL_USER + myOptionUser.getValue());
		lblUser.setValue(myOptionUser.getDisplay());
		
		chkIncluded = new CheckTag(this, "User Included");
		chkIncluded.setName("Included" + myOptionUser.getValue());
		if(objModel == null)
			chkIncluded.setIsChecked(false);
		else
			chkIncluded.setIsChecked(true);
	}
	public History createHistory() throws Exception { return null; }
		
	public Object getModel() { 
		Membership objModel = (Membership) super.getModel();

		try {
			boolean bMember = objModel != null;
			boolean bCkecked = chkIncluded.getIsChecked(); 

			// Checked	Member	Action
			// T			F		Add
			// T			T		No Action
			// F			F		No Action
			// F			T		Delete
			if(bCkecked && !bMember) {
				objModel = new Membership();
				objModel.setGroupsGuid(getGroupGuid());
				objModel.setUsersGuid(myOptionUser.getValue());
			} else if(!bCkecked && bMember)
				objModel.setIsDeleted(true);
		}
		catch(Exception ex) {}
		
		return objModel;
	}
}
