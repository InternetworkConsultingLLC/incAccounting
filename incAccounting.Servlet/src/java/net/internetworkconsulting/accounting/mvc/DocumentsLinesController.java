package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.data.TransactionsRow;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentLine;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class DocumentsLinesController extends Controller {
	private ComboTag cboItem;
	public DocumentsLinesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	private Document myParentDocument;
	void setParentDocument(Document value) { myParentDocument = value; }

	private TransactionsRow objTransaction = null;
	public void setTransaction(Transaction value) { objTransaction = value; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		DocumentLine objModel = (DocumentLine) getModel();
			
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		CheckTag chkIsDeleted = new CheckTag(this, "Row", DocumentLine.IS_DELETED, objModel.getGuid(), objModel);
		chkIsDeleted.setIsReadOnly(objTransaction != null);
		
		TextTag txtSortOrder = new TextTag(this, "Row", DocumentLine.SORT_ORDER, objModel.getGuid(), objModel);
		txtSortOrder.setIsReadOnly(objTransaction != null);
		
		TextTag txtQty = new TextTag(this, "Row", DocumentLine.QUANTITY, objModel.getGuid(), objModel);
		txtQty.setIsReadOnly(objTransaction != null);
		txtQty.setFormat(sQtyFormat);
		//txtQty.addOnChangeEvent(new Event() { public void handle() throws Exception { txtQty_OnChange(); } });
		
		cboItem = new ComboTag(this, "Row", DocumentLine.ITEMS_GUID, objModel.getGuid(), objModel);
		cboItem.setIsReadOnly(objTransaction != null);
		cboItem.setOptions(Item.loadOptions(getUser().login(), false));
		//cboItem.addOnChangeEvent(new Event() { public void handle() throws Exception { cboItem_OnChange(); } });

		ComboTag cboUm = new ComboTag(this, "Row", DocumentLine.UNIT_MEASURES_GUID, objModel.getGuid(), objModel);
		cboUm.setIsReadOnly(objTransaction != null);
		cboUm.setOptions(UnitMeasure.loadOptions(getUser().login(), false));
		//cboUm.addOnChangeEvent(new Event() { public void handle() throws Exception { cboUm_OnChange();  } });

		TextTag txtUnitPrice = new TextTag(this, "Row", DocumentLine.UNIT_PRICE, objModel.getGuid(), objModel);
		txtUnitPrice.setIsReadOnly(objTransaction != null);
		txtUnitPrice.setFormat(sMoneyFormat);
		//txtUnitPrice.addOnChangeEvent(new Event() { public void handle() throws Exception { txtUnitPrice_OnChange(); } });

		CheckTag chkIsTaxed = new CheckTag(this, "Row", DocumentLine.IS_TAXED, objModel.getGuid(), objModel);
		chkIsTaxed.setIsReadOnly(objTransaction != null);
		//chkIsTaxed.addOnChangeEvent(new Event() { public void handle() throws Exception { chkIsTaxed_OnChange(); } });

		TextTag txtExtension = new TextTag(this, "Row", DocumentLine.EXTENSION, objModel.getGuid(), objModel);
		txtExtension.setIsReadOnly(objTransaction != null);
		txtExtension.setFormat(sMoneyFormat);
		//txtExtension.addOnChangeEvent(new Event() { public void handle() throws Exception { txtExtension_OnChange(); } });

		TextAreaTag txtDescription = new TextAreaTag(this, "Row", DocumentLine.DESCRIPTION, objModel.getGuid(), objModel);
		txtDescription.setIsReadOnly(objTransaction != null);
		txtDescription.setMaxLength("255");

		ComboTag cboAccount = new ComboTag(this, "Row", DocumentLine.ACCOUNTS_GUID, objModel.getGuid(), objModel);
		cboAccount.setIsReadOnly(objTransaction != null);
		cboAccount.setOptions(Account.loadOptions(getUser().login(), false));

		ComboTag cboJob = new ComboTag(this, "Row", DocumentLine.JOBS_GUID, objModel.getGuid(), objModel);
		cboJob.setIsReadOnly(objTransaction != null);
		cboJob.setOptions(Job.loadOptions(getUser().login(), false));

		ComboTag cboDepartment = new ComboTag(this, "Row", DocumentLine.DEPARTMENTS_GUID, objModel.getGuid(), objModel);
		cboDepartment.setIsReadOnly(objTransaction != null);
		cboDepartment.setOptions(Department.loadOptions(getUser().login(), false));
	}
	public History createHistory() throws Exception { return null; }

	private void txtQty_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleQuantity(getUser().login(), myParentDocument);
	}
	private void cboItem_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleItemsGuid(getUser().login(), myParentDocument);
	}
	private void cboUm_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleUnitMeasuresGuid();
	}
	private void txtUnitPrice_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleUnitPrice(getUser().login(), myParentDocument);
	}
	private void chkIsTaxed_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleIsTaxed(getUser().login(), myParentDocument);
	}
	private void txtExtension_OnChange() throws Exception {
		DocumentLine objModel = (DocumentLine) getModel();
		objModel.handleExtension(getUser().login(), myParentDocument);
	}
	void setFocus() throws Exception {
		this.setFocus(cboItem.getID());
	}
}
