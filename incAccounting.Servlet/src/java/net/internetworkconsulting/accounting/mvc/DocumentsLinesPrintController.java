package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Department;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentLine;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.accounting.entities.Job;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class DocumentsLinesPrintController extends Controller {
	public DocumentsLinesPrintController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	private Document myParentDocument;
	void setParentDocument(Document value) { myParentDocument = value; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		DocumentLine objModel = (DocumentLine) getModel();
			
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		
		if(objModel.getItemsGuid() != null) {
			LiteralTag tagItem = new LiteralTag(this, "Row " + DocumentLine.ITEMS_GUID);
			Item bizItem = objModel.loadItem(getUser().login(), Item.class, !getIsPostback());
			tagItem.setValue(bizItem.getNumber());
		}
		
		if(objModel.getUnitMeasuresGuid() != null) {
			LiteralTag tagUm = new LiteralTag(this, "Row " + DocumentLine.UNIT_MEASURES_GUID);
			UnitMeasure bizUm = objModel.loadUnitMeasure(getUser().login(), UnitMeasure.class, !getIsPostback());
			tagUm.setValue(bizUm.getAbbreviation());
		}
		
		LiteralTag tagAccount = new LiteralTag(this, "Row " + DocumentLine.ACCOUNTS_GUID);
		Account bizAccount = objModel.loadAccount(getUser().login(), Account.class, !getIsPostback());
		tagAccount.setValue(bizAccount.getNumber() + " " + bizAccount.getNestedName());
		
		if(objModel.getJobsGuid() != null) {
			LiteralTag tagJob = new LiteralTag(this, "Row " + DocumentLine.JOBS_GUID);
			Job bizJob = objModel.loadJob(getUser().login(), Job.class, !getIsPostback());
			tagJob.setValue(bizJob.getNumber() + " " + bizJob.getNestedName());
		}
		
		if(objModel.getDepartmentsGuid() != null) {
			LiteralTag tagDepartment = new LiteralTag(this, "Row " + DocumentLine.DEPARTMENTS_GUID);
			Department bizDept = objModel.loadDepartment(getUser().login(), Department.class, !getIsPostback());
			tagDepartment.setValue(bizDept.getNumber() + " " + bizDept.getNestedName());
		}

		LiteralTag tagQty = new LiteralTag(this, "Row", DocumentLine.QUANTITY, "", objModel);
		tagQty.setFormat(sQtyFormat);
				
		LiteralTag tagUnitPrice = new LiteralTag(this, "Row", DocumentLine.UNIT_PRICE, "", objModel);
		tagUnitPrice.setFormat(sMoneyFormat);

		LiteralTag tagIsTaxed = new LiteralTag(this, "Row " + DocumentLine.IS_TAXED);
		if(objModel.getIsTaxed())
			tagIsTaxed.setValue("T");
		else
			tagIsTaxed.setValue("F");

		LiteralTag tagExtension = new LiteralTag(this, "Row", DocumentLine.EXTENSION, "", objModel);
		tagExtension.setFormat(sMoneyFormat);

		LiteralTag tagDescription = new LiteralTag(this, "Row", DocumentLine.DESCRIPTION, "", objModel);

	}
	public History createHistory() throws Exception { return null; }

}
