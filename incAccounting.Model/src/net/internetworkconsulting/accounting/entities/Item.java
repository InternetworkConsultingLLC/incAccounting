/*
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation, version 3 of the License.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 * more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.accounting.entities;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.ItemsRow;
import net.internetworkconsulting.bootstrap.entities.Option;
import net.internetworkconsulting.bootstrap.entities.User;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
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
	
	private static List<Option> lstOptions;
	public static List<Option> loadOptions(AdapterInterface adapter, boolean force) throws Exception {
		if(lstOptions != null && !force)
			return lstOptions;
		
		Statement stmt = new Statement(adapter.getSession().readFile("sql/Item.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		lstOptions = lst;
		return lst;
	}
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Item.class, columns, search); }
	public static List getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(Item.NUMBER);
		lstColumns.add(Item.IS_ALLOWED);
		return lstColumns;
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
		//this.setInventoryDescription(itm.getInventoryDescription());
		this.setInventoryUnitMeasuresGuid(itm.getInventoryUnitMeasuresGuid());
		this.setIsAllowed(itm.getIsAllowed());
		this.setIsSalesTaxed(itm.getIsSalesTaxed());
		this.setPurchaseAccountsGuid(itm.getPurchaseAccountsGuid());
		this.setPurchaseContactsGuid(itm.getPurchaseContactsGuid());
		//this.setPurchaseDescription(itm.getPurchaseDescription());
		this.setSalesAccountsGuid(itm.getSalesAccountsGuid());
		//this.setSalesDescription(itm.getSalesDescription());
		this.setSalesMarkUp(itm.getSalesMarkUp());
		this.setSalesUnitPrice(itm.getSalesUnitPrice());
	}
 }
