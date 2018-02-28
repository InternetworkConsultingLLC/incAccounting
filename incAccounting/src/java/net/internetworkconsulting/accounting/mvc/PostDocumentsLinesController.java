package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.DocumentType;
import net.internetworkconsulting.accounting.entities.TransactionType;
import net.internetworkconsulting.mvc.ButtonTag;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.Event;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class PostDocumentsLinesController extends Controller {
	private CheckTag chkIsPosted;
	public PostDocumentsLinesController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Document objModel = (Document) getModel();
		TransactionType objType = objModel.loadDocumentType(getUser().login(), DocumentType.class, !getIsPostback()).loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());

		chkIsPosted = new CheckTag(this, "Row Posted");
		chkIsPosted.setName("Posted" + objModel.getGuid());
		
		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		TextTag litDocumentType = new TextTag(this, "Row", TransactionType.NAME, objModel.getGuid(), objType);
		litDocumentType.setIsReadOnly(true);
		TextTag litDate = new TextTag(this, "Row", Document.DATE, objModel.getGuid(), objModel);
		litDate.setFormat("yyyy-MM-dd");
		litDate.setIsReadOnly(true);
		TextTag litNumber = new TextTag(this, "Row", Document.REFERENCE_NUMBER, objModel.getGuid(), objModel);
		litNumber.setIsReadOnly(true);
		TextTag litTotal = new TextTag(this, "Row", Document.TOTAL, objModel.getGuid(), objModel);
		litTotal.setIsReadOnly(true);
		litTotal.setFormat(sMoneyFormat);

		ButtonTag btnOpen = new ButtonTag(this, "Row", "Open", objModel.getGuid(), "Open");
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
	}
	public History createHistory() throws Exception { return null; }
	
	public boolean setIsChecked(boolean value) throws Exception {
		return chkIsPosted.setIsChecked(value);
	}	
	public boolean getIsChecked() throws Exception {
		return chkIsPosted.getIsChecked();
	}
	private void btnOpen_OnClick() throws Exception {
		Document objModel = (Document) getModel();
		TransactionType objType = objModel.loadDocumentType(getUser().login(), DocumentType.class, !getIsPostback()).loadTransactionType(getUser().login(), TransactionType.class, !getIsPostback());		
		redirect(objType.getRootUrl() + "&GUID=" + objModel.getGuid());
	}
  }
