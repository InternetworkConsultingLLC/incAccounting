package net.internetworkconsulting.accounting.mvc;

import java.util.List;
import net.internetworkconsulting.accounting.entities.Account;
import net.internetworkconsulting.accounting.entities.Contact;
import net.internetworkconsulting.accounting.entities.Document;
import net.internetworkconsulting.accounting.entities.Item;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.bootstrap.mvc.EditController;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.mvc.*;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class ItemsController extends EditController {
	public ItemsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }

	public void handleDeleteRow(String guid) throws Exception {
		Item objModel = Item.loadByGuid(getUser().login(), Item.class, guid);
		objModel.setIsDeleted(true);
		getUser().login().save(Item.TABLE_NAME, objModel);
	}
	public Object handleLoadRow(String guid) throws Exception {
		return Item.loadByGuid(getUser().login(), Item.class, guid);
	}
	public Object handleNewRow() throws Exception {
		Item objModel = new Item();
		objModel.initialize();

		String sParentGuid = getRequest().getParameter(Item.PARENT_ITEMS_GUID);
		if(sParentGuid != null && sParentGuid.length() == 32)
			objModel.setParentItemsGuid(sParentGuid);

		return objModel;
	}
	
	public void createControls(Template document, Object model) throws Exception {		
		Item objModel = (Item) handleNonPostbackActions(model);		
		setDocument(new Template(read_url("~/templates/Item.html"), new HtmlSyntax()));

		String sMoneyFormat = "%." + getUser().getSetting(Document.SETTING_MONEY_DECIMALS) + "f";
		String sRateFormat = "%." + getUser().getSetting(Document.SETTING_RATE_DECIMALS) + "f";
		String sQtyFormat = "%." + getUser().getSetting(Document.SETTING_QUANITY_DECIMALS) + "f";

		LiteralTag litMoneyDecimals = new LiteralTag(this, "Money Decimals");
		litMoneyDecimals.setValue(getUser().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		LiteralTag litRateDecimals = new LiteralTag(this, "Rate Decimals");
		litRateDecimals.setValue(getUser().getSetting(Document.SETTING_RATE_DECIMALS));
				
		LiteralTag litQuantityDecimals = new LiteralTag(this, "Quantity Decimals");
		litQuantityDecimals.setValue(getUser().getSetting(Document.SETTING_QUANITY_DECIMALS));
		
		TextTag txtGuid = new TextTag(this, Item.GUID, objModel);
		txtGuid.setIsReadOnly(true);

		ComboTag cboParentItem = new ComboTag(this, Item.PARENT_ITEMS_GUID, objModel);
		cboParentItem.setOptions(Item.loadOptions(getUser().login(), true));
		cboParentItem.addOnChangeEvent(new Event() { public void handle() throws Exception { cboParentItem_OnChange(); } });

		TextTag txtNumber = new TextTag(this, Item.NUMBER, objModel);
		txtNumber.setIsReadOnly(true);
		
		TextTag txtSegment = new TextTag(this, Item.SEGMENT, objModel);

		CheckTag chkIsAllowed = new CheckTag(this, Item.IS_ALLOWED, objModel);

		ComboTag cboSalesAccount = new ComboTag(this, Item.SALES_ACCOUNTS_GUID, objModel);
		cboSalesAccount.setOptions(Account.loadOptions(getUser().login(), true));

		TextAreaTag txtSalesDescription = new TextAreaTag(this, Item.SALES_DESCRIPTION, objModel);
//		txtSalesDescription.setRows(getUser().getSetting(Item.SETTING_DESCRIPTION_ROWS));
		txtSalesDescription.setMaxLength("255");

		TextTag txtSalesMarkup = new TextTag(this, Item.SALES_MARK_UP, objModel);
		txtSalesMarkup.setFormat(sRateFormat);
		
		CheckTag chkSalesIsTaxed = new CheckTag(this, Item.IS_SALES_TAXED, objModel);

		ComboTag cboPurchaseAccount = new ComboTag(this, Item.PURCHASE_ACCOUNTS_GUID, objModel);
		cboPurchaseAccount.setOptions(Account.loadOptions(getUser().login(), true));

		TextAreaTag txtPurchaseDescription = new TextAreaTag(this, Item.PURCHASE_DESCRIPTION, objModel);
//		txtPurchaseDescription.setRows(getUser().getSetting(Item.SETTING_DESCRIPTION_ROWS));
		txtPurchaseDescription.setMaxLength("255");

		ComboTag cboPurchaseContact = new ComboTag(this, Item.PURCHASE_CONTACTS_GUID, objModel);
		cboPurchaseContact.setOptions(Contact.loadOptions(getUser().login(), true));

		TextTag txtLastUnitCost = new TextTag(this, Item.LAST_UNIT_COST, objModel);
		txtLastUnitCost.setFormat(sMoneyFormat);

		TextTag txtSalesPrice = new TextTag(this, Item.SALES_UNIT_PRICE, objModel);
		txtSalesPrice.setFormat(sMoneyFormat);
		
		ComboTag cboInventoryAccount = new ComboTag(this, Item.INVENTORY_ACCOUNTS_GUID, objModel);
		cboInventoryAccount.setOptions(Account.loadOptions(getUser().login(), true));

		TextAreaTag txtInventoryDescription = new TextAreaTag(this, Item.INVENTORY_DESCRIPTION, objModel);
//		txtInventoryDescription.setRows(getUser().getSetting(Item.SETTING_DESCRIPTION_ROWS));
		txtInventoryDescription.setMaxLength("255");

		ComboTag cboUnitMeasure = new ComboTag(this, Item.INVENTORY_UNIT_MEASURES_GUID, objModel);
		cboUnitMeasure.setOptions(UnitMeasure.loadOptions(getUser().login(), true));

		CheckTag chkInventoryIsSerialized = new CheckTag(this, Item.IS_SERIALIZED, objModel);

		ButtonTag btnSave = new ButtonTag(this, "Save");
		btnSave.addOnClickEvent(new Event() { public void handle() throws Exception { btnSave_OnClick(); } });
		
		ButtonTag btnOpen = new ButtonTag(this, "", "Open", "", "Open Parent");
		if(objModel.getParentItemsGuid() == null)
			btnOpen.setIsReadOnly(true);
		btnOpen.addOnClickEvent(new Event() { public void handle() throws Exception { btnOpen_OnClick(); } });
		
		ButtonTag btnAdd = new ButtonTag(this, "Add");
		if(objModel.getRowState() == RowInterface.RowState.Insert)
			btnAdd.setIsReadOnly(true);
		btnAdd.addOnClickEvent(new Event() { public void handle() throws Exception { btnAdd_OnClick(); } });
		
		List<Item> lstItems = objModel.loadChildItems(getUser().login(), Item.class, false);
		for(Item item: lstItems)
			createController(item);
	}
	private ItemsChildrenController createController(Item item) {
		Item objModel = (Item) getModel();

		ItemsChildrenController iecc = new ItemsChildrenController(this, "Child");
		iecc.setIsDocumentBlock(true);
		iecc.setModel(item);
		iecc.setParent(objModel);

		return iecc;		
	}
	public History createHistory() throws Exception {
		Item objModel = (Item) getModel();

		String sDisplay = "New Item";
		if(objModel.getRowState() != RowInterface.RowState.Insert)
			sDisplay = objModel.getNumber();

		return new History(sDisplay, getRequest(), getUser());
	}

	private void btnSave_OnClick() throws Exception {
		Item objModel = (Item) getModel();
		
		try {
			getUser().login().begin(true);
			getUser().login().save(Item.TABLE_NAME, objModel);
			getUser().login().save(Item.TABLE_NAME, objModel.loadChildItems(getUser().login(), Item.class, false));
			getUser().login().commit(true);
		}
		catch(Exception ex) {
			getUser().login().rollback(true);
			getUser().logExcpetion(ex, "974745cc78f94d5181118777327f3f75");
			addError("Save", ex.getMessage());
			return;
		}
		
		redirect("~/incAccounting?App=Item&GUID=" + objModel.getGuid() + "&Error=Saved!");
	}
	private void btnOpen_OnClick() throws Exception {
		Item objModel = (Item) getModel();
		
		if(objModel.getParentItemsGuid() != null)
			redirect("~/incAccounting?App=Item&GUID=" + objModel.getParentItemsGuid());		
	}
	private void btnAdd_OnClick() throws Exception {
		Item objModel = (Item) getModel();
		redirect("~/incAccounting?App=Item&" + Item.PARENT_ITEMS_GUID + "=" + objModel.getGuid());	
	}
	private void cboParentItem_OnChange() throws Exception {
		Item objModel = (Item) getModel();
		objModel.setAdapter(getUser().login());
		objModel.handleParentItemsGuid();
	}
}
