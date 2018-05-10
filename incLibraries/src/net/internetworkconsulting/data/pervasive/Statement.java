package net.internetworkconsulting.data.pervasive;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.TimeZone;
import net.internetworkconsulting.data.SessionInterface;
import net.internetworkconsulting.data.StatementInterface;

public class Statement implements StatementInterface {
	public Statement() {}
	public Statement(String command) { sCommand = command; }

	private String sCommand = "";
	public void setCommand(String value) { sCommand = value; }
	public String getCommand() { return sCommand; }

	HashMap<String, Object> mapParameters = new HashMap<>();
	public void setParameter(HashMap<String, Object> values) { mapParameters = values; }
	public HashMap<String, Object> getParameters() { return mapParameters; }

	public String lastExecutedSql = "";
	public String generate(SessionInterface session, boolean log_query) throws Exception {
		String ret = sCommand;
		for(String key: mapParameters.keySet())
			ret = ret.replace(key, convertObjectToSql(mapParameters.get(key)));

		return ret;
	}

	public static Class getJavaTypeForSqlType(String value) throws Exception {
		String sqlType = value.toLowerCase();

		if(sqlType.contains("char") || sqlType.contains("text"))
			return String.class;
		if(sqlType.contains("blob") || sqlType.contains("binary"))
			return byte[].class;
		if(sqlType.contains("bit") && sqlType.contains("(") && sqlType.contains(")"))
			return byte[].class;
		if(sqlType.contains("small") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("tiny") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("medium") && sqlType.contains("int"))
			return Integer.class;
		if(!sqlType.contains("unsigned") && !sqlType.contains("big") && sqlType.contains("int"))
			return Integer.class;
		if(sqlType.contains("big") && sqlType.contains("int") && !sqlType.contains("unsigned"))
			return Long.class;
		if(sqlType.contains("int") &&sqlType.contains("unsigned"))
			return Long.class;
		if(sqlType.contains("decimal"))
			return BigDecimal.class;
		if(sqlType.contains("bit") || sqlType.contains("boolean"))
			return Boolean.class;
		if(sqlType.contains("big") && sqlType.contains("int") && sqlType.contains("unsigned"))
			return BigInteger.class;
		if(sqlType.contains("date") || sqlType.contains("time"))
			return Date.class;
		else
			throw new Exception("Unsupported type: " + sqlType + "!");
	}
	public static String convertObjectToSql(Object value) throws Exception { return net.internetworkconsulting.data.mysql.Statement.convertObjectToSql(value); }
	public static String convertObjectToString(Object value, String format) throws Exception { return net.internetworkconsulting.data.mysql.Statement.convertObjectToString(value, format); }
	public static Object parseStringToValue(Class type, String value) throws Exception { return net.internetworkconsulting.data.mysql.Statement.parseStringToValue(type, value); }
	public static Object parseStringToValue(String type, String value) throws Exception { return net.internetworkconsulting.data.mysql.Statement.parseStringToValue(type, value); }

}
