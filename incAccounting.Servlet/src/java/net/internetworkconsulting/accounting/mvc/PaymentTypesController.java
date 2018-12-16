package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.PaymentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.HtmlSyntax;
import net.internetworkconsulting.template.Template;

public class PaymentTypesController extends EditController {
	private List<Option> lstAccountOptions;
	public PaymentTypesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		PaymentType objModel = PaymentType.loadByGuid(getUser().login(), PaymentType.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(PaymentType.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return PaymentType.loadByGuid(getUser().login(), PaymentType.class, guid);
	}
	public Object handleNewRow() throws Exception {
		PaymentType objModel = new PaymentType();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		PaymentType objModel = (PaymentType) handleNonPostbackActions(model);
		setDocument(new Template(readTemplate("~/templates/PaymentType.html"), new HtmlSyntax()));
		
		lstAccountOptions = Account.loadOptions(getUser().login());

		TransactionType objTranType = null;
		try { objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback()); }
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
		TextTag txtListUrl = new TextTag(this, TransactionType.LIST_URL, objTranType);
		CheckTag chkAllowed = new CheckTag(this, TransactionType.IS_ALLOWED, objTranType);
	
		CheckTag cboSalesRelated = new CheckTag(this, PaymentType.IS_SALES_RELATED, objModel);
		cboSalesRelated.setIsReadOnly(true);
		
		ComboTag cboAccount = new ComboTag(this, PaymentType.ACCOUNTS_GUID, objModel);
		cboAccount.setOptions(lstAccountOptions);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		PaymentType objModel = (PaymentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());

		String sDisplay = "New Payment Type";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objTranType.getName() + " Type";

		return new History(sDisplay, getRequest(), getUser());
	}
	public void btnSave_OnClick() throws Exception {
		PaymentType objModel = (PaymentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());

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

		redirect("~/incAccounting?App=PaymentType&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
