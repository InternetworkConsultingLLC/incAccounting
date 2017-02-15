package net.internetworkconsulting.accounting.mvc;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.data.RowInterface.RowState;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DocumentTypeEditController extends Controller {
	public DocumentTypeEditController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(read("templates/DocumentTypeEdit.html"), new HtmlSyntax()));
		
		DocumentType objModel = (DocumentType) model;
		if(!getIsPostback()) {
			String sGuid = getRequest().getParameter("GUID");
			if(sGuid != null)
				objModel = DocumentType.loadByGuid(getUser().login(), DocumentType.class, sGuid);
			else {
				objModel = new DocumentType();
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
		List<Option> lstOptions = Account.loadOptions(getUser().login(), true);
		if(
			objModel.getGuid().equals(DocumentType.PURCHASE_INVOICE_GUID) ||
			objModel.getGuid().equals(DocumentType.PURCHASE_CREDIT_GUID) ||
			objModel.getGuid().equals(DocumentType.SALES_INVOICE_GUID) ||
			objModel.getGuid().equals(DocumentType.SALES_CREDIT_GUID)
		)
			lstOptions.get(0).setDisplay("Cash Accounting");
		else {
			lstOptions = new LinkedList<>();
			lstOptions.add(new Option("Does Not Post", ""));
		}
		cboAccount.setOptions(lstOptions);
		
		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.setValue("Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
	}
	public History createHistory() throws Exception {
		DocumentType objModel = (DocumentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, false);

		String sDisplay = "New Document Type";
		if(objModel.getRowState() != RowState.Insert)
			sDisplay = objTranType.getName() + " Type";

		return new History(sDisplay, getRequest(), getUser());
	}
	public void btnSave_OnClick() throws Exception {
		DocumentType objModel = (DocumentType) getModel();
		TransactionType objTranType = objModel.loadTransactionType(getUser().login(), TransactionType.class, false);

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

		redirect("~/incAccounting?App=DocumentTypeEdit&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
}
