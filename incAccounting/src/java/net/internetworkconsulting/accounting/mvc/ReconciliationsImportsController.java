package net.internetworkconsulting.accounting.mvc;

import java.text.SimpleDateFormat;
import net.internetworkconsulting.data.ofx.Transaction;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.template.Template;

public class ReconciliationsImportsController extends Controller{
	private Transaction objModel;
	
	private CheckTag chkMatched;
	public boolean getMatched() throws Exception { return chkMatched.getIsChecked(); }
	public void getMatched(boolean value) throws Exception { chkMatched.setIsChecked(value); }

	public ReconciliationsImportsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		objModel = (net.internetworkconsulting.data.ofx.Transaction) getModel();
		
		LabelTag lblAmount = new LabelTag(this, "Import", "Amount", objModel.getIdentifier(), objModel);
		LabelTag lblCheckNumber = new LabelTag(this, "Import", "CheckNumber", objModel.getIdentifier(), objModel);
		LabelTag lblId = new LabelTag(this, "Import", "Identifier", objModel.getIdentifier(), objModel);

		LabelTag lblName = new LabelTag(this, "Import Name");
		if(objModel.getMemo() != null && objModel.getName() != null) {
			if(objModel.getMemo().contains(objModel.getName()))
				lblName.setValue(objModel.getMemo());
			else
				lblName.setValue(objModel.getName() + " Memo: " + objModel.getMemo());
		} else if(objModel.getMemo() != null && objModel.getName() == null)
				lblName.setValue(objModel.getMemo());
		else if(objModel.getMemo() == null && objModel.getName() != null)
				lblName.setValue(objModel.getName());
		else if(objModel.getMemo() == null && objModel.getName() == null)
				lblName.setValue("");

		LabelTag lblType = new LabelTag(this, "Import", "TransactionType", objModel.getIdentifier(), objModel);

		LabelTag lblPosted = new LabelTag(this, "Import Posted");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		lblPosted.setValue(sdf.format(objModel.getPosted()));
		
		chkMatched = new CheckTag(this, "Import Matched");
		chkMatched.setName("ImportMatched" + objModel.getIdentifier());
	}
	public History createHistory() throws Exception { return null; }
	
}
