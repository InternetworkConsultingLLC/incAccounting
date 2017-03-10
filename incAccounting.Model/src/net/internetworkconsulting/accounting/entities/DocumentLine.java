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

import java.math.BigDecimal;
import java.math.RoundingMode;
import net.internetworkconsulting.accounting.data.DocumentLinesRow;
import net.internetworkconsulting.data.AdapterInterface;

public class DocumentLine extends DocumentLinesRow {
//	public static String SETTING_TAXABLE = "Document Line - Taxable";

	public void initialize() throws Exception {
		throw new Exception("You must use the 'initialize(AdapterInterface adapter, Document parent)' method!");
	}
	public void initialize(AdapterInterface adapter, Document document) throws Exception {
		super.setDocumentsGuid(document.getGuid());
		super.setGuid(User.newGuid());
		super.setIsBackwardsCalculated(Boolean.FALSE);
		super.setIsTaxed(Boolean.TRUE);
		super.setQuantity(BigDecimal.ONE);
		super.setUnitPrice(BigDecimal.ZERO);
		super.setExtension(BigDecimal.ZERO);
		super.setSortOrder(document.getNextSortOrder(adapter));
	}

	public boolean setDocumentsGuid(String value) throws Exception {
		return super.setDocumentsGuid(value);
	}

	public void handleIsTaxed(AdapterInterface adapter, Document document) throws Exception { document.calculate(adapter); }
	public void handleQuantity(AdapterInterface adapter, Document document) throws Exception {
		if(this.getUnitPrice() == null)
			super.setUnitPrice(BigDecimal.ZERO);

		if(this.getQuantity() == null)
			super.setQuantity(BigDecimal.ONE);

		BigDecimal dValue = Document.round(adapter, getQuantity().multiply(getUnitPrice()), Document.SETTING_QUANITY_DECIMALS);
		super.setExtension(dValue);
		
		document.calculate(adapter);
	}
	public void handleUnitPrice(AdapterInterface adapter, Document document) throws Exception {
		if(this.getUnitPrice() == null)
			super.setUnitPrice(BigDecimal.ZERO);

		if(this.getQuantity() == null)
			super.setQuantity(BigDecimal.ONE);

		BigDecimal dValue = Document.round(adapter, this.getQuantity().multiply(this.getUnitPrice()), Document.SETTING_MONEY_DECIMALS);
		super.setExtension(dValue);
		
		document.calculate(adapter);
	}
	public void handleExtension(AdapterInterface adapter, Document document) throws Exception {
		if(this.getExtension() == null)
			super.setExtension(BigDecimal.ZERO);

		if(this.getQuantity() == null)
			this.setQuantity(BigDecimal.ONE);

		int iDecimals = Integer.parseInt(adapter.getSession().getSetting(Document.SETTING_RATE_DECIMALS));
		BigDecimal dValue = this.getExtension().divide(this.getQuantity(), iDecimals, RoundingMode.HALF_UP);
		super.setUnitPrice(Document.round(adapter, dValue, Document.SETTING_MONEY_DECIMALS));

		document.calculate(adapter);
	}
	public void handleItemsGuid(AdapterInterface adapter, Document document) throws Exception {
		if(getItemsGuid() == null)
			return;
		
		Item itm = Item.loadByGuid(adapter, Item.class, this.getItemsGuid());
		if(itm == null) {
			super.setItemsGuid(null);
			return;
		}

		// Revenue		Asset		Expenses		Document Type (Multiplier)
		// + Crdt (L)	+ Dbt (D)					Sales Document (1)
		//				- Crdt (D)	+ Dbt (L)		Purchase Document (-1)
		DocumentType rDocType = document.loadDocumentType(adapter, DocumentType.class, true);		
		if(rDocType.getIsSalesRelated()) {
			// sales document
			super.setAccountsGuid(itm.getSalesAccountsGuid());
			super.setDescription(itm.getSalesDescription());
			super.setUnitPrice(Document.round(adapter, itm.getSalesUnitPrice(), Document.SETTING_MONEY_DECIMALS));
		} else {
			// purchase document
			if(itm.getInventoryAccountsGuid() != null)
				super.setAccountsGuid(itm.getInventoryAccountsGuid());
			else
				super.setAccountsGuid(itm.getPurchaseAccountsGuid());
			super.setDescription(itm.getPurchaseDescription());
			super.setUnitPrice(Document.round(adapter, itm.getLastUnitCost(), Document.SETTING_MONEY_DECIMALS));
		}

		super.setIsTaxed(itm.getIsSalesTaxed());
		super.setUnitMeasuresGuid(itm.getInventoryUnitMeasuresGuid());

		if(this.getUnitPrice() == null)
			super.setUnitPrice(BigDecimal.ZERO);
		if(this.getQuantity() == null)
			super.setQuantity(BigDecimal.ONE);

		BigDecimal dValue = Document.round(adapter, getQuantity().multiply(getUnitPrice()), Document.SETTING_MONEY_DECIMALS);
		super.setExtension(dValue);

		document.calculate(adapter);
	}
	public void handleUnitMeasuresGuid() throws Exception {
//		Item itm = this.loadItem(myAdapter, Item.class, true);
//		UnitMeasure itmUm = (UnitMeasure) itm.loadInventoryUnitMeasure(myAdapter, UnitMeasure.class, true);
//		HashMap<String, BigDecimal> hmMultipliers = itmUm.loadConversionMap(myAdapter);
//		
//		if(!hmMultipliers.containsKey(value))
//			throw new Exception("For the selection item, their is no direct convertion to the selected unit measure!");
//		
//		BigDecimal bdItemPrice = BigDecimal.ZERO;
//		DocumentType dt = (DocumentType) myDocument.loadDocumentType(myAdapter, DocumentType.class, false);
//
//		int iDecimals = Integer.parseInt(myAdapter.getSession().getSetting(Document.SETTING_QUANITY_DECIMALS));
//		this.setQuantity(this.getQuantity().multiply(hmMultipliers.get(value)));
//		if(dt.getIsSalesRelated())
//			this.handleUnitPrice(itm.getSalesUnitPrice().divide(hmMultipliers.get(value), iDecimals, RoundingMode.HALF_UP));
//		else
//			this.handleUnitPrice(itm.getLastUnitCost().divide(hmMultipliers.get(value), iDecimals, RoundingMode.HALF_UP));
	}
	
	public void beforeSave(AdapterInterface adapter) throws Exception {
		// check if posted
		Document postedCheckDocument = Document.loadByGuid(adapter, Document.class, getDocumentsGuid());
		Transaction postedCheckTransaction = null;
		try { postedCheckTransaction = postedCheckDocument.loadTransaction(adapter, Transaction.class, false); }
		catch(Exception ex) {}
		if(postedCheckTransaction != null)
			throw new Exception("You cannot change a document line that has been posted!");
	}
}
