package net.internetworkconsulting.accounting.entities;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.accounting.data.ReportFiltersRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class ReportFilter extends ReportFiltersRow {

	public static String DT_TEXT = "Text";
	public static String DT_INTEGER = "Integer";
	public static String DT_DECIMAL = "Decimal";
	public static String DT_DATE = "Date";
	public static String DT_DATETIME = "DateTime";
	public static String DT_ENUM = "Enum";

	public static String VALUE = "Value";
	private String sValue = null;
	public String getValue() { return sValue; }
	public void setValue(String value) { sValue = value; }
	
	public List<Option> getDataTypeOptions() throws Exception {
		LinkedList<Option> lstOptions = new LinkedList<>();
		lstOptions.add(new Option("", ""));
		lstOptions.add(new Option(DT_TEXT, DT_TEXT));
		lstOptions.add(new Option(DT_INTEGER, DT_INTEGER));
		lstOptions.add(new Option(DT_DECIMAL, DT_DECIMAL));
		lstOptions.add(new Option(DT_DATE, DT_DATE));
		lstOptions.add(new Option(DT_DATETIME, DT_DATETIME));
		lstOptions.add(new Option(DT_ENUM, DT_ENUM));
		return lstOptions;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	public ReportFilter handleCopy(AdapterInterface adapter) throws Exception {
		ReportFilter objNew = new ReportFilter();
		objNew.initialize();
		for(String key : this.getOriginals().keySet())
			objNew.getChanges().put(key, this.getOriginals().get(key));
		
		objNew.setGuid(User.newGuid());
		
		return objNew;
	}
	
	public String generateSqlInserts(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "INSERT INTO \"" + TABLE_NAME + "\" (";
		sql += " \"" + DATA_TYPE + "\", ";
		sql += " \"" + GUID + "\", ";
		sql += " \"" + PRIORITY + "\", ";
		sql += " \"" + PROMPT + "\", ";
		sql += " \"" + QUERY + "\", ";
		sql += " \"" + REPORTS_GUID + "\" ) VALUES (";
		sql += Statement.convertObjectToSql(this.getDataType()) + ", ";
		sql += Statement.convertObjectToSql(this.getGuid()) + ", ";
		sql += Statement.convertObjectToSql(this.getPriority()) + ", ";
		sql += Statement.convertObjectToSql(this.getPrompt()) + ", ";
		sql += Statement.convertObjectToSql(this.getQuery()) + ", ";
		sql += Statement.convertObjectToSql(this.getReportsGuid()) + " );\n";

		return sql;
	}
	public String generateSqlDeletes(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "DELETE FROM \"" + TABLE_NAME + "\" WHERE \"" + GUID + "\"=" + Statement.convertObjectToSql(this.getGuid()) + ";\n";
		return sql;
	}	
}
