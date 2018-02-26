package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Group;
import net.internetworkconsulting.accounting.entities.Securable;
import net.internetworkconsulting.accounting.entities.Permission;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.template.Template;

public class GroupPermissionsController extends Controller {
	private Securable objModel;
	
	private Group objGroup = null;
	public Group getGroup() { return objGroup; }
	public void setGroup(Group value) { objGroup = value; }

	private Permission objPermission;
	public Permission getPermission() { return objPermission; }
	
	public GroupPermissionsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		objModel = (Securable) super.getModel();
		objPermission = objModel.loadPerimissionsForGroup(getUser().login(), false, objGroup.getGuid());
		
		LabelTag tagSecurable = new LabelTag(this, "Securable", Securable.DISPLAY_NAME, objModel.getGuid(), objModel);
		CheckTag tagCreate = new CheckTag(this, "Securable", Permission.CAN_CREATE, objModel.getGuid(), objPermission);
		CheckTag tagRead = new CheckTag(this, "Securable", Permission.CAN_READ, objModel.getGuid(), objPermission);
		CheckTag tagUpdate = new CheckTag(this, "Securable", Permission.CAN_UPDATE, objModel.getGuid(), objPermission);
		CheckTag tagDelete = new CheckTag(this, "Securable", Permission.CAN_DELETE, objModel.getGuid(), objPermission);
	}
	public History createHistory() throws Exception { return null; }
	
}
