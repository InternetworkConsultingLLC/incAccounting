package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PaymentTypeEditController extends Controller {
	public PaymentTypeEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/PaymentTypeEdit.html"), new HtmlSyntax()));
		
		PaymentType objModel = (PaymentType) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = PaymentType.loadByGuid(getUser().login(), PaymentType.class, sGuid);
			else {
				objModel = new PaymentType();
				objModel.initialize();
			}
		}
		setModel(objModel);
		
		TransactionType objTranType = null;
		try { objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, false); }
		catch(Exception ex) { 
			objTranType = new TransactionType();
			objTranType.initialize();
			objTranType.setGuid(objModel.getGuid());
			objModel.setTransactionType(objTranType);
		}
		
		TextTag txtGuid = new TextTag(this, PaymentType.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtName = new TextTag(this, TransactionType.NAME, objTranType);
		txtName.setIsReadOnly(true);
		TextTag txtEditUrl = new TextTag(this, TransactionType.EDIT_URL, objTranType);
		txtEditUrl.setIsReadOnly(true);
		TextTag txtListUrl = new TextTag(this, TransactionType.LIST_URL, objTranType);
		txtListUrl.setIsReadOnly(true);
		CheckTag chkAllowed = new CheckTag(this, TransactionType.IS_ALLOWED, objTranType);
	
		CheckTag cboSalesRelated = new CheckTag(this, PaymentType.IS_SALES_RELATED, objModel);
		cboSalesRelated.setIsReadOnly(true);
		
		ComboTag cboAccount = new ComboTag(this, PaymentType.ACCOUNTS_GUID, objModel);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), true));
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		PaymentType objModel = (PaymentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, false);

		String sDisplay = "New Payment Type";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objTranType.getName() + " Type";

		return new History(sDisplay, getRequest(), getUser());
	}
	public void btnSave_OnClick() throws Exception {
		PaymentType objModel = (PaymentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, false);

		if(objModel.getRowState() == RowState.Insert) {
			addError("Save", "You may not add document types. Payment types are defined by modules.");
			return;
		}		
		
		try {
			getUser().login().begin(true);
			getUser().login().save(PaymentType.TABLE_NAME, objModel);
			//getUser().login().save(TransactionType.TABLE_NAME, objTranType);
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "ed78375b5fa84617baae9b1067f5ab07");
			addError("Save", ex.getMessage());
			return;
		}

		redirect("~/incAccounting?App=PaymentTypeEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
