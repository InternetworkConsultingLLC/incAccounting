package net.internetworkconsulting.accounting.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.ItemsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class Item extends ItemsRow {
//	public static String SETTING_DESCRIPTION_ROWS = "Item Description Rows";
	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);		
	}
	public void initialize(Item parent) throws Exception {
		initialize();
		
		this.setInventoryAccountsGuid(parent.getInventoryAccountsGuid());
		this.setInventoryUnitMeasuresGuid(parent.getInventoryUnitMeasuresGuid());
		this.setIsSalesTaxed(parent.getIsSalesTaxed());
		this.setIsSerialized(parent.getIsSerialized());
		this.setParentItemsGuid(parent.getGuid());
		this.setPurchaseAccountsGuid(parent.getPurchaseAccountsGuid());
		this.setPurchaseContactsGuid(parent.getPurchaseContactsGuid());
		this.setSalesAccountsGuid(parent.getSalesAccountsGuid());
		this.setSalesMarkUp(parent.getSalesMarkUp());
		this.setSalesUnitPrice(parent.getSalesUnitPrice());
		this.setLastUnitCost(parent.getLastUnitCost());
		//this.setInventoryDescription(parent.getInventoryDescription());
		this.setIsAllowed(parent.getIsAllowed());
		//this.setPurchaseDescription(parent.getPurchaseDescription());
		//this.setSalesDescription(parent.getSalesDescription());
		
	}

	private transient AdapterInterface myAdapter = null;
 	public AdapterInterface getAdapter() { return myAdapter; }
	public void setAdapter(AdapterInterface value) { myAdapter = value; }
	
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(Item.class, "Item.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}
	
	private static List<Item> lstItems;
	public static List<Item> loadAll(AdapterInterface adapter, boolean force) throws Exception {
		if(lstItems != null && !force)
			return lstItems;
		
		Statement stmt = new Statement("SELECT * FROM \"" + TABLE_NAME + "\" ORDER BY \"" + NUMBER + "\"");
		lstItems = adapter.load(Item.class, stmt, true);
		
		return lstItems;
	}


	public void beforeSave(AdapterInterface adapter) throws Exception {
		Item parent;

		// construct account number in the form of:
		// parent.segment +  "-" + ... +  "-" parent.segment +  "-" + this.segment
		String sSeperator = Account.getSeperator(adapter);
		
		// prime the loop
		String sNumber = getSegment();
		if(this.getParentItemsGuid() == null)
			parent = null;
		else
			parent = this.loadParentItem(adapter, Item.class, false);
		
		// loop to root
		while(parent != null) {
			sNumber = parent.getSegment() + sSeperator + sNumber;
			if(parent.getParentItemsGuid() == null)
				parent = null;
			else
				parent = parent.loadParentItem(adapter, Item.class, false);
		}
		
		// set computed number
		this.setNumber(sNumber);
	}
	public void handleParentItemsGuid() throws Exception {
		if(getParentItemsGuid() == null)
			return;
		
		Item itm = loadParentItem(myAdapter, Item.class, true);
				
		this.setInventoryAccountsGuid(itm.getInventoryAccountsGuid());
		this.setInventoryUnitMeasuresGuid(itm.getInventoryUnitMeasuresGuid());
		this.setIsAllowed(itm.getIsAllowed());
		this.setIsSalesTaxed(itm.getIsSalesTaxed());
		this.setPurchaseAccountsGuid(itm.getPurchaseAccountsGuid());
		this.setPurchaseContactsGuid(itm.getPurchaseContactsGuid());
		this.setSalesAccountsGuid(itm.getSalesAccountsGuid());
		this.setSalesMarkUp(itm.getSalesMarkUp());
	}

	public BigDecimal calculateCost(AdapterInterface adapter, DocumentLine dl, boolean post) throws Exception {
		// Sales	CM		Mult
		//	T		T		1
		//	T		F		-1
		//	F		T		-1
		//	F		F		1
		
		List<ItemCost> lstCosts = ItemCost.loadForItem(adapter, getGuid());
		if(lstCosts.size() < 1)
			throw new Exception("No items posted purchases, cannot calculate cost!");
		
		List<ItemPosting> lstPostings = new LinkedList<>();

		int iQtyDecimals = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_QUANITY_DECIMALS));
		int iMoneyDecimals = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_MONEY_DECIMALS));
		
		BigDecimal qtyFound = BigDecimal.ZERO;
		BigDecimal qtyToFind = dl.getQuantity();
		BigDecimal costsFound = BigDecimal.ZERO;
		
		for(ItemCost rowCosts : lstCosts) {
			if(qtyToFind.compareTo(rowCosts.getAvailable()) <= 0) {
				// toFind <= available
				ItemPosting rowUsed = new ItemPosting();
				rowUsed.initialize();
				rowUsed.setDecreaseQuantity(qtyToFind.setScale(iQtyDecimals, RoundingMode.HALF_UP));
				rowUsed.setDecreasingDocumentLinesGuid(dl.getGuid());
				rowUsed.setIncreasingDocumentLinesGuid(rowCosts.getDocumentLinesGUID());
				lstPostings.add(rowUsed);
				
				costsFound = costsFound.add(rowCosts.getUnitCost().multiply(qtyToFind));
				qtyFound = qtyFound.add(qtyToFind);
				qtyToFind = qtyToFind.subtract(qtyToFind);
			} else {
				// toFind > available
				ItemPosting rowUsed = new ItemPosting();
				rowUsed.initialize();
				rowUsed.setDecreaseQuantity(rowCosts.getAvailable().setScale(iQtyDecimals, RoundingMode.HALF_UP));
				rowUsed.setDecreasingDocumentLinesGuid(dl.getGuid());
				rowUsed.setIncreasingDocumentLinesGuid(rowCosts.getDocumentLinesGUID());
				lstPostings.add(rowUsed);
				
				costsFound = costsFound.add(rowCosts.getUnitCost().multiply(rowCosts.getAvailable()));
				qtyFound = qtyFound.add(rowCosts.getAvailable());
				qtyToFind = qtyToFind.subtract(rowCosts.getAvailable());
			}
			
			if(qtyToFind.setScale(iQtyDecimals, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) <= 0)
				break;
		}
		
		if(post)
			adapter.save(ItemPosting.TABLE_NAME, lstPostings);
		
		return costsFound.setScale(iMoneyDecimals, RoundingMode.HALF_UP);
	}
}
