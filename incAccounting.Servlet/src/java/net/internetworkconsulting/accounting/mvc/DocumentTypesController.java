package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DocumentTypesController extends EditController {
	public DocumentTypesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		DocumentType objModel = DocumentType.loadByGuid(getUser().login(), DocumentType.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(DocumentType.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return DocumentType.loadByGuid(getUser().login(), DocumentType.class, guid);
	}
	public Object handleNewRow() throws Exception {
		DocumentType objModel = new DocumentType();
		objModel.initialize();
		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		DocumentType objModel = (DocumentType) handleNonPostbackActions(model);		
		setDocument(new Template(readTemplate("~/templates/DocumentType.html"), new HtmlSyntax()));
		
		TransactionType objTranType = null;
		try { objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback()); }
		catch(Exception ex) { 
			objTranType = new TransactionType();
			objTranType.initialize();
			objTranType.setGuid(objModel.getGuid());
			objModel.setTransactionType(objTranType);
		}
		
		TextTag txtGuid = new TextTag(this, DocumentType.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		TextTag txtName = new TextTag(this, TransactionType.NAME, objTranType);
		txtName.setIsReadOnly(true);
		TextTag txtEditUrl = new TextTag(this, TransactionType.EDIT_URL, objTranType);
		TextTag txtListUrl = new TextTag(this, TransactionType.LIST_URL, objTranType);
		CheckTag chkAllowed = new CheckTag(this, TransactionType.IS_ALLOWED, objTranType);
	
		CheckTag cboSalesRelated = new CheckTag(this, DocumentType.IS_SALES_RELATED, objModel);
		cboSalesRelated.setIsReadOnly(true);
		
		CheckTag chkIsCreditMemo = new CheckTag(this, DocumentType.IS_CREDIT_MEMO, objModel);
		chkIsCreditMemo.setIsReadOnly(true);
		ComboTag cboAccount = new ComboTag(this, DocumentType.ACCOUNTS_GUID, objModel);
		List<Option> lstAccountOptions = Account.loadOptions(getUser().login());
		if(
			objModel.getGuid().equals(DocumentType.PURCHASE_INVOICE_GUID) ||
			objModel.getGuid().equals(DocumentType.PURCHASE_CREDIT_GUID) ||
			objModel.getGuid().equals(DocumentType.SALES_INVOICE_GUID) ||
			objModel.getGuid().equals(DocumentType.SALES_CREDIT_GUID)
		)
			lstAccountOptions.get(0).setDisplay("Cash Accounting");
		else {
			lstAccountOptions = new LinkedList<>();
			lstAccountOptions.add(new Option("Does Not Post", ""));
		}
		cboAccount.setOptions(lstAccountOptions);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		DocumentType objModel = (DocumentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());

		String sDisplay = "New Document Type";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objTranType.getName() + " Type";

		return new History(sDisplay, getRequest(), getUser());
	}
	public void btnSave_OnClick() throws Exception {
		DocumentType objModel = (DocumentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());

		if(objModel.getRowState() == RowState.Insert) {
			addError("Save", "You may not add document types. Document types are defined by modules.");
			return;
		}		
		
		try {
			getUser().login().begin(true);
			getUser().login().save(DocumentType.TABLE_NAME, objModel);
			getUser().login().save(TransactionType.TABLE_NAME, objTranType);
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "ed78375b5fa84617baae9b1067f5ab07");
			addError("Save", ex.getMessage());
			return;
		}

		redirect("~/incAccounting?App=DocumentType&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
