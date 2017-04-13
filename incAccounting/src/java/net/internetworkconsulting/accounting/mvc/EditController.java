package net.internetworkconsulting.accounting.mvc;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;

public abstract class EditController extends Controller {
	public EditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public EditController(HttpServletRequest request, HttpServletResponse response, ServletContext context, boolean is_postback) { super(request, response, context, is_postback); }

	public Object handleNonPostbackActions(Object model) throws Exception {
		setModel(model);
		
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			String sAction = getRequest().getParameter("Action");
			String sReturn = getRequest().getParameter("Return");

			if(sGuid != null && sAction != null && sAction.equals("Delete") && sReturn != null) {
				handleDeleteRow(sGuid);
				redirect(sReturn);
			} else if(sGuid != null) {
				setModel(handleLoadRow(sGuid));
			} else {
				setModel(handleNewRow());
			}
		}
		
		return getModel();
	}
	public abstract void handleDeleteRow(String guid) throws Exception;
	public abstract Object handleLoadRow(String guid) throws Exception;
	public abstract Object handleNewRow() throws Exception;
}
