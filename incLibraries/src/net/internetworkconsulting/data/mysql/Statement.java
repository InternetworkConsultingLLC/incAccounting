package net.internetworkconsulting.data.mysql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.data.StatementInterface;

public class Statement implements StatementInterface {
	public Statement() {
		sCommand = "";
		mapParameters = new HashMap<>();
	}
	public Statement(String command) {
		sCommand = command;
		mapParameters = new HashMap<>();
	}

	private String sCommand;
	public void setCommand(String value) {
		sCommand = value;
	}
	public String getCommand() {
		return sCommand;
	}

	private HashMap<String, Object> mapParameters;
	public void setParameter(HashMap<String, Object> values) {
		mapParameters = values;
	}
	public HashMap<String, Object> getParameters() {
		return mapParameters;
	}

	public String lastExecutedSql = "";
	public String generate(SessionInterface session, boolean log_query) throws Exception {
		String ret = sCommand;
		for(String key: mapParameters.keySet())
			ret = ret.replace(key, convertObjectToSql(mapParameters.get(key)));

		if(session != null && log_query)
			session.logSql(ret, "ed0012fcd68049f4b70fa9ad50530e4e");

		return ret;
	}
	public static Statement createInsert(RowInterface row, HashMap<String, String> hmColumns) throws Exception {
		if(row.getChanges().size() < 1)
			throw new Exception("Cannot insert a row without changes!");

		Statement stmt = new Statement("INSERT INTO \"%TABLE%\" ( %COLUMNS% ) VALUES ( %VALUES% );");
		stmt.setCommand(stmt.getCommand().replace("%TABLE%", row.getSqlTableName()));

		String sColumns = "";
		String sValues = "";

		// this needs changed to do a complete column list 
		// and set null where empty string or not present.
		for(String sCurrCol: hmColumns.keySet()) {
			sColumns += " \"" + sCurrCol + "\", ";
			sValues += " " + convertObjectToSql(row.getChanges().get(sCurrCol)) + ", ";
		}

		sColumns = sColumns.substring(0, sColumns.length() - 2).trim();
		stmt.setCommand(stmt.getCommand().replace("%COLUMNS%", sColumns));

		sValues = sValues.substring(0, sValues.length() - 2).trim();
		stmt.setCommand(stmt.getCommand().replace("%VALUES%", sValues));

		return stmt;
	}
	public static Statement createUpdate(RowInterface row, HashMap<String, String> hmColumns) throws Exception {
		if(row.getOriginals().size() < 1)
			throw new Exception("Cannot update a row without originals!");
		if(row.getChanges().size() < 1)
			throw new Exception("Cannot update a row without changes!");

		Statement stmt = new Statement("UPDATE \"%TABLE%\" SET %VALUES% WHERE %WHERE%;");
		stmt.setCommand(stmt.getCommand().replace("%TABLE%", row.getSqlTableName()));

		String sWhere = "";
		String sSets = "";

		for(String sCurrCol: hmColumns.keySet())
			if(row.getOriginals().get(sCurrCol) == null)
				sWhere += " \"" + sCurrCol + "\" IS NULL AND ";
			else if(row.getOriginals().get(sCurrCol).equals(""))
				sWhere += " \"" + sCurrCol + "\" = '' AND ";
			else
				sWhere += " \"" + sCurrCol + "\"=" + convertObjectToSql(row.getOriginals().get(sCurrCol)) + " AND ";

		for(String sCurrCol: row.getChanges().keySet())
			if(row.getChanges().get(sCurrCol) == null)
				sSets += " \"" + sCurrCol + "\"=NULL, ";
			else
				sSets += " \"" + sCurrCol + "\"=" + convertObjectToSql(row.getChanges().get(sCurrCol)) + ", ";

		sWhere = sWhere.substring(0, sWhere.length() - 4).trim();
		stmt.setCommand(stmt.getCommand().replace("%WHERE%", sWhere));

		sSets = sSets.substring(0, sSets.length() - 2).trim();
		stmt.setCommand(stmt.getCommand().replace("%VALUES%", sSets));

		return stmt;
	}
	public static Statement createDelete(RowInterface row, HashMap<String, String> hmColumns) throws Exception {
		if(row.getOriginals().size() < 1)
			throw new Exception("Cannot delete a row without originals!");

		Statement stmt = new Statement("DELETE FROM \"%TABLE%\" WHERE %WHERE%;");
		stmt.setCommand(stmt.getCommand().replace("%TABLE%", row.getSqlTableName()));

		String sWhere = "";

		for(String sCurrCol: hmColumns.keySet())
			if(row.getOriginals().get(sCurrCol) == null)
				sWhere += " \"" + sCurrCol + "\" IS NULL AND ";
			else if(row.getOriginals().get(sCurrCol).equals(""))
				sWhere += " \"" + sCurrCol + "\" = '' AND ";
			else
				sWhere += " \"" + sCurrCol + "\"=" + convertObjectToSql(row.getOriginals().get(sCurrCol)) + " AND ";

		sWhere = sWhere.substring(0, sWhere.length() - 4).trim();
		stmt.setCommand(stmt.getCommand().replace("%WHERE%", sWhere));

		return stmt;
	}

	public static String convertObjectToSql(Object value) throws Exception {
		/*
		 * Binary 10 byte[]
		 * Meduim Int Unsigned java.lang.Integer
		 * Char 10 java.lang.String
		 * Decimal Unsigned java.math.BigDecimal
		 * Year java.sql.Date
		 * Time java.sql.Time
		 * Vachar 10 java.lang.String
		 * Medium Text java.lang.String
		 * Decimal java.math.BigDecimal
		 * 64 Bits byte[]
		 * Date java.sql.Date
		 * DateTime java.sql.Timestamp
		 * Long BLob byte[]
		 * Int Unsigned java.lang.Long
		 * Meduim Int java.lang.Integer
		 * Int java.lang.Integer
		 * Signle Bit java.lang.Boolean
		 * Tiny Text java.lang.String
		 * Small Int Unsigned java.lang.Integer
		 * Small Int java.lang.Integer
		 * Integer Unsigned java.lang.Long
		 * Medium Blob byte[]
		 * Long Text java.lang.String
		 * Boolean java.lang.Boolean
		 * Timestamp java.sql.Timestamp
		 * Tiny Blob byte[]
		 * Text java.lang.String
		 * Varbinary 10 byte[]
		 * TinyInt Unsigned java.lang.Integer
		 * Integer java.lang.Integer
		 * TinyInt java.lang.Integer
		 * BigInt Unsigned java.math.BigInteger
		 * BigInt java.lang.Long
		 */
		SimpleDateFormat sdf;
		if(value == null)
			return "NULL";
		switch(value.getClass().getCanonicalName()) {
			case "boolean":
			case "java.lang.Boolean":
				if((boolean) value)
					return "1";
				else
					return "0";
			case "byte[]":
				String ret = "";
				for(byte b: (byte[]) value)
					ret += "0x" + String.format("%2x", b);
				return ret;
			case "byte":
			case "short":
			case "int":
			case "long":
			case "java.lang.Integer":
			case "java.lang.Long":
				return value.toString();
			case "char":
			case "java.lang.String":
				if(((String) value).length() == 0)
					return "NULL";
				else
					return "'" + value.toString().replace("'", "''") + "'";
			case "float":
			case "double":
			case "java.math.BigDecimal":
				return String.format("%f", value);
			case "java.sql.Date":
				java.sql.Date objDate = (java.sql.Date) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return "'" + sdf.format(objDate) + "'";
			case "java.sql.Time":
				java.sql.Time objTime = (java.sql.Time) value;
				sdf = new SimpleDateFormat("HH:mm:ss");
				return "'" + sdf.format(objTime) + "'";
			case "java.sql.Timestamp":
				java.sql.Timestamp objTs = (java.sql.Timestamp) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return "'" + sdf.format(objTs) + "'";
			case "java.util.GregorianCalendar":
				java.util.Calendar cal = (java.util.Calendar) value;
				java.sql.Timestamp objTsCal = new Timestamp(cal.toInstant().toEpochMilli());
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return "'" + sdf.format(objTsCal) + "'";
		}

		throw new Exception("Not a valid type (" + value.getClass().getCanonicalName() + ")!");
	}
	public static String convertObjectToString(Object value, String format) throws Exception {
		if(value == null)
			return "";

		SimpleDateFormat sdf;
		switch(value.getClass().getCanonicalName()) {
			case "boolean":
			case "java.lang.Boolean":
				if((boolean) value)
					return "true";
				else
					return "false";
			case "byte[]":
				return javax.xml.bind.DatatypeConverter.printHexBinary(((byte[]) value)).toLowerCase().replace("0x", "");
			case "byte":
			case "short":
			case "int":
			case "long":
			case "java.lang.Integer":
			case "java.lang.Long":
				if(format != null && format.length() > 0)
					return String.format(format, value);
				else
					return value.toString();
			case "char":
			case "java.lang.String":
				return value.toString();
			case "float":
			case "double":
			case "java.math.BigDecimal":
				if(format != null && format.length() > 0)
					return String.format(format, value);
				else
					return ((BigDecimal) value).toString();
			case "java.sql.Date":
				java.sql.Date objDate = (java.sql.Date) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(objDate);
			case "java.sql.Time":
				java.sql.Time objTime = (java.sql.Time) value;
				sdf = new SimpleDateFormat("HH:mm:ss");
				return sdf.format(objTime);
			case "java.sql.Timestamp":
				java.sql.Timestamp objTs = (java.sql.Timestamp) value;
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.format(objTs);
			case "java.util.GregorianCalendar":
				java.util.Calendar cal = (java.util.Calendar) value;
				java.sql.Timestamp objTsCal = new Timestamp(cal.toInstant().toEpochMilli());
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.format(objTsCal);
		}

		throw new Exception("Not a valid type (" + value.getClass().getCanonicalName() + ")!");
	}
	public static Object parseStringToValue(Class type, String value) throws Exception {
		switch(type.getCanonicalName()) {
			case "byte[]":
				try {
					return javax.xml.bind.DatatypeConverter.parseHexBinary(value.toLowerCase().replace("0x", ""));
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.Integer":
				try {
					return new Integer(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.String":
				if(value != null && value.equals(""))
					return null;
				else
					return value;
			case "java.math.BigDecimal":
				try {
					return new BigDecimal(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "java.sql.Date":
				try {
					return new Date((new SimpleDateFormat("yyyy-MM-dd")).parse(value).getTime());
				}
				catch(Exception ex) {
					return null;
				}
			case "java.sql.Time":
				try {
					return new Time((new SimpleDateFormat("HH:mm:ss")).parse(value).getTime());
				}
				catch(Exception ex) {
					return null;
				}
			case "java.sql.Timestamp":
				try {
					if(value.length() == ("yyyy-MM-dd HH:mm:ss").length())
						return new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(value).getTime());
					else if(value.length() == ("yyyy-MM-dd HH:mm").length())
						return new Timestamp((new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(value).getTime());
					else if(value.length() == ("yyyy-MM-dd").length())
						return new Timestamp((new SimpleDateFormat("yyyy-MM-dd")).parse(value).getTime());
				}
				catch(Exception ex) {
					return null;
				}
			case "java.lang.Long":
				try {
					return new Long(value);
				}
				catch(Exception ex) {
					return null;
				}
			case "boolean":
			case "java.lang.Boolean":
				try {
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 't')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'f')
						return false;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'y')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == 'n')
						return false;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == '1')
						return true;
					if(value.length() > 0 && value.toLowerCase().toCharArray()[0] == '0')
						return false;
					return false;
				}
				catch(Exception ex) { return false; }
			case "java.math.BigInteger":
				try {
					return new BigInteger(value);
				}
				catch(Exception ex) {
					return null;
				}
		}

		throw new Exception("'" + type.getCanonicalName() + "' is not a supported data type!");
	}
}
