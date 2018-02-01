package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;

public class ItemsChildrenController extends Controller {
	private ButtonTag btnOpen;
	public ItemsChildrenController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return false; }

	private Item myParent;
	void setParent(Item value) { myParent = value; }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		Item objModel = (Item) getModel();

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";
		
		CheckTag chkDelete = new CheckTag(this, "Child", Item.IS_DELETED, objModel.getGuid(), objModel);
		TextTag txtSegment = new TextTag(this, "Child", Item.SEGMENT, objModel.getGuid(), objModel);
		TextTag txtNumber = new TextTag(this, "Child", Item.NUMBER, objModel.getGuid(), objModel);

		ComboTag cboUM = new ComboTag(this, "Child", Item.INVENTORY_UNIT_MEASURES_GUID, objModel.getGuid(), objModel);
		cboUM.setOptions(UnitMeasure.loadOptions(getUser().login(), !getIsPostback()));

		CheckTag chkTaxes = new CheckTag(this, "Child", Item.IS_SALES_TAXED, objModel.getGuid(), objModel);
		chkTaxes.setIsReadOnly(true);
		
		CheckTag chkSerialized = new CheckTag(this, "Child", Item.IS_SERIALIZED, objModel.getGuid(), objModel);
		chkSerialized.setIsReadOnly(true);

		TextAreaTag txtSalesDescription = new TextAreaTag(this, "Child", Item.SALES_DESCRIPTION, objModel.getGuid(), objModel);
		txtSalesDescription.setMaxLength("255");
		
		TextTag txtLastCost = new TextTag(this, "Child", Item.LAST_UNIT_COST, objModel.getGuid(), objModel);
		txtLastCost.setFormat(sMoneyFormat);

		TextTag txtMarkup = new TextTag(this, "Child", Item.SALES_MARK_UP, objModel.getGuid(), objModel);
		txtMarkup.setFormat(sMoneyFormat);
		
		TextTag txtSalesPrice = new TextTag(this, "Child", Item.SALES_UNIT_PRICE, objModel.getGuid(), objModel);
		txtSalesPrice.setFormat(sMoneyFormat);
		
		btnOpen = new ButtonTag(this, "Child", "Open", objModel.getGuid(), "Open");
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnOpen.setIsReadOnly(true);
	}
	public History createHistory() throws Exception { return null; }
	private void btnOpen_OnClick() throws Exception {
		Item objModel = (Item) getModel();
		redirect("~/incAccounting?App=Item&GUID=" + objModel.getGuid());
	}
	
	public void beforePopulate() throws Exception {
		super.beforePopulate();

		Item objModel = (Item) getModel();

		btnOpen.setValue("Open " + objModel.getNumber());
	}
}
