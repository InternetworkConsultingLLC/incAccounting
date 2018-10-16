package net.internetworkconsulting.accounting.entities;

import java.util.LinkedList;
import net.internetworkconsulting.accounting.data.ListFiltersRow;

public class ListFilter extends ListFiltersRow {
	public static String DT_TEXT = "Text";
	public static String DT_INTEGER = "Integer";
	public static String DT_DECIMAL = "Decimal";
	public static String DT_DATE = "Date";
	public static String DT_TIME = "Time";
	public static String DT_DATETIME = "DateTime";
	public static String DT_BOOLEAN = "Boolean";
	public static String DT_ENUM = "Enum";	
	
	public static java.util.List<Option> getDataTypeOptions() throws Exception {
		java.util.List<Option> lstOptions = new LinkedList<>();
		lstOptions.add(new Option("", ""));
		lstOptions.add(new Option(DT_TEXT, DT_TEXT));
		lstOptions.add(new Option(DT_INTEGER, DT_INTEGER));
		lstOptions.add(new Option(DT_DECIMAL, DT_DECIMAL));
		lstOptions.add(new Option(DT_DATE, DT_DATE));
		lstOptions.add(new Option(DT_TIME, DT_TIME));
		lstOptions.add(new Option(DT_DATETIME, DT_DATETIME));
		lstOptions.add(new Option(DT_BOOLEAN, DT_BOOLEAN));
		lstOptions.add(new Option(DT_ENUM, DT_ENUM));
		return lstOptions;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	private String sValue = null;
	public String getValue() { return sValue; }
	public void setValue(String value) { sValue = value; }
}
