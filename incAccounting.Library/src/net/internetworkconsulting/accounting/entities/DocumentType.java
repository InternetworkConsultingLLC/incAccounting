package net.internetworkconsulting.accounting.entities;

import java.util.List;
import net.internetworkconsulting.accounting.data.DocumentTypesRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class DocumentType extends DocumentTypesRow {
	public static String SALES_QUOTE_GUID = "276db4afcf634b6fbc4a5821c9858ab9";
	public static String SALES_ORDER_GUID = "5f756fc5f7c5493ca0d86f2d0ead2fda";
	public static String SALES_INVOICE_GUID = "81e2917ac5c34d1cb6f9d168cd0439db";
	public static String SALES_CREDIT_GUID = "86af180c412f40c5a660678e3895694b";
	public static String PURCHASE_QUOTE_GUID = "dedf79eddf7c4e348918d42e25b53309";
	public static String PURCHASE_ORDER_GUID = "e56b2b6aa42b479890085b74b69275f3";
	public static String PURCHASE_INVOICE_GUID = "9d3821afd6fb47f9b2713d3cc574ceff";
	public static String PURCHASE_CREDIT_GUID = "6632ec00f5824aeca4a49bf21cbdaece";

	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(DocumentType.class, "DocumentType.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public static List<Option> loadPostableOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(DocumentType.class, "DocumentType.loadPostableOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, true);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	public void setTransactionType(TransactionType value) {
		this.rTransactionTypeParent = value;
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
		if(getRowState() == RowState.Delete)
			throw new Exception("You cannot delete a document type!");
	}
	
	
	
}
