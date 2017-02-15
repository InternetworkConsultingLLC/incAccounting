package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.SalesTaxMembershipOption;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class SalesTaxEditOptionController extends Controller {
	public SalesTaxEditOptionController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		SalesTaxMembershipOption objModel = (SalesTaxMembershipOption) getModel();
		
		CheckTag chkIncluded = new CheckTag(this, "Option", SalesTaxMembershipOption.IS_INCLUDED, objModel.getGuid(), objModel);
		chkIncluded.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
		
		LiteralTag litDisplayName = new LiteralTag(this, "Option", SalesTaxMembershipOption.DISPLAY_NAME, objModel.getGuid(), objModel);
		LiteralTag litRate = new LiteralTag(this, "Option", SalesTaxMembershipOption.TAX_RATE, objModel.getGuid(), objModel);
	}
	public History createHistory() throws Exception { return null; }
	
}
