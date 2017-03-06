package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.data.PayrollFieldsRow;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.PayrollFieldValue;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class PayrollChecksFieldsController extends Controller {
	private PayrollFieldValue objModel;
	
	private boolean bIsQtyCalculated = false;
	private PayrollFieldsRow objField;
	private CheckTag chkDeleted;
	public void setIsQtyCalculated(boolean value) { bIsQtyCalculated = value; }
	
	private String sTemplatePrefix = null;
	public void setTemplatePrefix(String value) { sTemplatePrefix = value; }
	
	private List<Option> lstFieldOptions = null;
	public void setFieldOptions(List<Option> value) { lstFieldOptions = value; }
	
	private boolean bPosted = false;
	public void setPosted(boolean value) { bPosted = value; }
	
	public PayrollChecksFieldsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }
	public void createControls(Template document, Object model) throws Exception {
		if(sTemplatePrefix == null)
			throw new Exception("This controller requires a 'TemplatePrefix' to be set!");
		if(lstFieldOptions == null)
			throw new Exception("This controller requires a 'FieldOptions' to be set!");

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		setDocument(document);
		
		objModel = (PayrollFieldValue) getModel();			
		
		chkDeleted = new CheckTag(this, sTemplatePrefix, PayrollFieldValue.IS_DELETED, objModel.getGuid(), objModel);
		chkDeleted.setIsReadOnly(bPosted);
		
		ComboTag cboField = new ComboTag(this, sTemplatePrefix, PayrollFieldValue.PAYROLL_FIELDS_GUID, objModel.getGuid(), objModel);
		cboField.setOptions(lstFieldOptions);
		cboField.setIsReadOnly(bPosted);
		
		TextTag txtTotal = new TextTag(this, sTemplatePrefix, PayrollFieldValue.TOTAL, objModel.getGuid(), objModel);
		txtTotal.setFormat(sMoneyFormat);
		txtTotal.setIsReadOnly(bPosted);

		TextTag txtRate = new TextTag(this, sTemplatePrefix, PayrollFieldValue.RATE, objModel.getGuid(), objModel);
		txtRate.setFormat(sMoneyFormat);
		//txtRate.addOnChangeEvent(new Event() { public void handle() throws Exception { objModel.handleRate(getUser().login()); } });
		txtRate.setIsReadOnly(bPosted);
			
		TextTag txtQty = new TextTag(this, sTemplatePrefix, PayrollFieldValue.QUANTITY, objModel.getGuid(), objModel);
		txtQty.setFormat(sMoneyFormat);
		///txtQty.addOnChangeEvent(new Event() { public void handle() throws Exception { objModel.handleQuantity(getUser().login()); } });
		txtQty.setIsReadOnly(bPosted);

		txtTotal.setIsReadOnly(true);
	}
	public History createHistory() throws Exception { return null; }
}
