package net.internetworkconsulting.bootstrap.mvc;

import java.util.HashMap;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.Permission;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.template.Template;

public class SecurableEditPermissionsController extends Controller {
	public SecurableEditPermissionsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	private HashMap<String, String> hmGroups;
	public HashMap<String, String> getGroups() { return hmGroups; }
	public void setGroups(HashMap<String, String> value) { hmGroups = value; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(getController().getDocument());
		Permission objModel = (Permission) getModel();
		
		LabelTag lblDisplay = new LabelTag(this, "Securable " + Option.DISPLAY);
		lblDisplay.setValue(hmGroups.get(objModel.getGroupsGuid()));
		
		CheckTag chk;
		
		chk = new CheckTag(this, "Securable " + Permission.CAN_CREATE);
		chk.setName(Permission.CAN_CREATE + objModel.getGroupsGuid());
		chk.bind(objModel, Permission.CAN_CREATE);

		chk = new CheckTag(this, "Securable " + Permission.CAN_READ);
		chk.setName(Permission.CAN_READ + objModel.getGroupsGuid());
		chk.bind(objModel, Permission.CAN_READ);

		chk = new CheckTag(this, "Securable " + Permission.CAN_UPDATE);
		chk.setName(Permission.CAN_UPDATE + objModel.getGroupsGuid());
		chk.bind(objModel, Permission.CAN_UPDATE);

		chk = new CheckTag(this, "Securable " + Permission.CAN_DELETE);
		chk.setName(Permission.CAN_DELETE + objModel.getGroupsGuid());
		chk.bind(objModel, Permission.CAN_DELETE);
	}		
	public History createHistory() throws Exception {return null; }
	
}
