package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.data.TransactionsRow;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentLine;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.accounting.entities.Transaction;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class DocumentsLinesController extends Controller {
	private ComboTag cboItem;
	private List<Option> lstItemOptions;
	private List<Option> lstUmOptions;
	private List<Option> lstAccountOptions;
	private List<Option> lstJobOptions;
	private List<Option> lstDepartmentOptions;
	
	public DocumentsLinesController(
		ControllerInterface controller, String document_keyword, 
		List<Option> item_options, List<Option> um_options, List<Option> account_options, List<Option> job_options, List<Option> department_options
	) { 
		super(controller, document_keyword); 

		lstItemOptions = item_options;
		lstUmOptions = um_options;
		lstAccountOptions = account_options;
		lstJobOptions = job_options;
		lstDepartmentOptions = department_options;
	}
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
		
		cboItem = new ComboTag(this, "Row", DocumentLine.ITEMS_GUID, objModel.getGuid(), objModel);
		cboItem.setIsReadOnly(objTransaction != null);
		cboItem.setOptions(lstItemOptions);

		ComboTag cboUm = new ComboTag(this, "Row", DocumentLine.UNIT_MEASURES_GUID, objModel.getGuid(), objModel);
		cboUm.setIsReadOnly(objTransaction != null);
		cboUm.setOptions(lstUmOptions);

		TextTag txtUnitPrice = new TextTag(this, "Row", DocumentLine.UNIT_PRICE, objModel.getGuid(), objModel);
		txtUnitPrice.setIsReadOnly(objTransaction != null);
		txtUnitPrice.setFormat(sMoneyFormat);

		CheckTag chkIsTaxed = new CheckTag(this, "Row", DocumentLine.IS_TAXED, objModel.getGuid(), objModel);
		chkIsTaxed.setIsReadOnly(objTransaction != null);

		TextTag txtExtension = new TextTag(this, "Row", DocumentLine.EXTENSION, objModel.getGuid(), objModel);
		txtExtension.setIsReadOnly(objTransaction != null);
		txtExtension.setFormat(sMoneyFormat);

		TextAreaTag txtDescription = new TextAreaTag(this, "Row", DocumentLine.DESCRIPTION, objModel.getGuid(), objModel);
		txtDescription.setIsReadOnly(objTransaction != null);
		txtDescription.setMaxLength("255");

		ComboTag cboAccount = new ComboTag(this, "Row", DocumentLine.ACCOUNTS_GUID, objModel.getGuid(), objModel);
		cboAccount.setIsReadOnly(objTransaction != null);
		cboAccount.setOptions(lstAccountOptions);

		ComboTag cboJob = new ComboTag(this, "Row", DocumentLine.JOBS_GUID, objModel.getGuid(), objModel);
		cboJob.setIsReadOnly(objTransaction != null);
		cboJob.setOptions(lstJobOptions);

		ComboTag cboDepartment = new ComboTag(this, "Row", DocumentLine.DEPARTMENTS_GUID, objModel.getGuid(), objModel);
		cboDepartment.setIsReadOnly(objTransaction != null);
		cboDepartment.setOptions(lstDepartmentOptions);
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
