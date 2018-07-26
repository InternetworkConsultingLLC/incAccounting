package net.internetworkconsulting.generator.MySQL;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Helper;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.template.CSyntax;
import net.internetworkconsulting.template.Template;

public class Generator {
	private final String sDatabase = "incllc";
	private final String sServer = "localhost";
	private final String sUser = "root";
	private final String sPassword = "Welcome123";
	
	private final String sNamespace = "net.internetworkconsulting.accounting.data";

	private HashMap<String, Template> hmJsDocuments = null;
	private final String sJsOutputFile = "E:\\Repositories\\NetBeans\\incAccounting\\web\\soap\\scripts\\";
	
	private final Statement stmtTables = new Statement("SELECT * FROM information_schema.TABLES WHERE TABLE_SCHEMA = {database};");
	private final Statement stmtColumns = new Statement("SELECT * FROM information_schema.COLUMNS WHERE TABLE_NAME = {table} AND TABLE_SCHEMA = {database};");
	private final Statement stmtForeignKeys = new Statement("SELECT * FROM information_schema.KEY_COLUMN_USAGE WHERE TABLE_SCHEMA = {database} AND REFERENCED_TABLE_NAME IS NOT NULL;");
	private final Statement stmtUniqueKeys = new Statement();
	
	private AdapterInterface dbAdapter;	
	private List lstForeignKeys;
	private List lstUniqueKeys;
	private HashMap<String, String> hmFolders;
	private HashMap<String, String> hmFileNames;
	private HashMap<String, Template> hmDocuments;
	private HashMap<String, Family> hmFamilies;
	private HashMap<String, Row> hmTables;
	
	private HashSet<String> hsMethods = null;
	
	public int execute() throws Exception {
		dbAdapter = new Adapter(sServer, sDatabase, sUser, sPassword, false);

		stmtUniqueKeys.setCommand(Helper.InputStreamToString(Generator.class.getResourceAsStream("SelectUniqueKeys.sql")));
		
		List<String> arrConfig = Helper.InputStreamToStringList(Generator.class.getResourceAsStream("files.config"));
		hmFileNames = new HashMap<>();
		hmFolders = new HashMap<>();
		for(String sLine : arrConfig) {
			String[] arrColumns = sLine.split("\\|");			
			if(arrColumns.length < 1)
				continue;
				
			hmFileNames.put(arrColumns[0], arrColumns[1]); // template to output file name
			hmFolders.put(arrColumns[0], arrColumns[2]); // template to output folder name
		}
		
		stmtTables.getParameters().put("{database}", sDatabase);
		List<Row> lstTables = dbAdapter.load(Row.class, stmtTables, false);
		hmTables = new HashMap<>();
		for(Row table : lstTables)
			hmTables.put(table.get("TABLE_NAME").toString(), table);
						
		stmtUniqueKeys.getParameters().put("{database}", sDatabase);
		lstUniqueKeys = dbAdapter.load(Row.class, stmtUniqueKeys, false);
		
		stmtForeignKeys.getParameters().put("{database}", sDatabase);
		lstForeignKeys = dbAdapter.load(Row.class, stmtForeignKeys, false);
		
		processTables(lstTables);
	
		return 0;
	}
	private void processTables(List<Row> lstTables) throws Exception {
		//loadJsDocuments();
		
		for(int cnt = 0; cnt < lstTables.size(); cnt++) {
			Row row = lstTables.get(cnt);
			hsMethods = new HashSet<>();
			
			// read templates
			loadDocuments();
			
			// populate with table meta data
			addToDocument("database", sDatabase);
			addToDocument("namespace", sNamespace);			
			addToDocument("table", row.get("TABLE_NAME").toString());
		
			processTable(row);
//			touchJsDocuments("TABLES");
//			parseJsDocuments("TABLES");

			// save to file
			saveDocuments(row.get("TABLE_NAME").toString());
		}
		
		//saveJsDocuments();
	}
	private void processTable(Row table) throws Exception {
		stmtColumns.getParameters().clear();
		stmtColumns.getParameters().put("{table}", table.get("TABLE_NAME").toString());
		stmtColumns.getParameters().put("{database}", sDatabase);
		List<Row> lstColumns = dbAdapter.load(Row.class, stmtColumns, false);
		for(int cnt = 0; cnt < lstColumns.size(); cnt++) {
			Row rColumn = lstColumns.get(cnt);
			
			processColumn(table, rColumn);
			processParents(table, rColumn);
			processChildren(table, rColumn);
			processUniqueKeys(table, rColumn);			
		}
	}
	private void processColumn(RowInterface rTable, RowInterface rColumn) throws Exception {
		addToDocument("table", rTable.get("TABLE_NAME").toString());
		addToDocument("column", rColumn.get("COLUMN_NAME").toString());
		addToDocument("data_type", rColumn.get("DATA_TYPE").toString());
		setDocumentsValue("type_raw", rColumn.get("DATA_TYPE").toString());
		setDocumentsValue("type_java", formatJavaType(rColumn));

		touchDocuments("COLUMNS");
		parseDocuments("COLUMNS");
	}
	private void processParents(Row table, Row column) throws Exception {
		processRelationship(table, column, "TABLE_NAME", "COLUMN_NAME", "PARENTS");
	}
	private void processChildren(Row table, Row column) throws Exception {
		processRelationship(table, column, "REFERENCED_TABLE_NAME", "REFERENCED_COLUMN_NAME", "CHILDREN");
	}
	private void processRelationship(Row table, Row column, String fkTableColumn, String fkColumnColumn, String sSection) throws Exception {
		for(Object objRel : lstForeignKeys) {
			RowInterface rRel = (RowInterface) objRel;
			if(rRel.get(fkTableColumn).equals(table.get("TABLE_NAME").toString()) && rRel.get(fkColumnColumn).equals(column.get("COLUMN_NAME").toString())) {
				String sLoadParent = "";
				String sLoadChildren = "";
				String sConstraint = rRel.get("CONSTRAINT_NAME").toString();
				if(sConstraint.contains("<")) {
					String[] arrSplit = sConstraint.split("\\<");
					sLoadParent = arrSplit[0].trim();
					sLoadChildren = arrSplit[1].trim();				
					processParentChildren(sSection, rRel, sLoadParent, sLoadChildren);
				} else if(sConstraint.contains(">")) {
					String[] arrSplit = sConstraint.split("\\>");
					sLoadParent = arrSplit[1].trim();
					sLoadChildren = arrSplit[0].trim();
					processParentChildren(sSection, rRel, sLoadParent, sLoadChildren);
				} else
					throw new Exception("You must name the constrain in 'Parent<Children' or 'Children>Parent' style so that the sign points to the parent.");				
			}
		}
	}
	private void processParentChildren(String sSection, RowInterface rRel, String sLoadParent, String sLoadChildren) throws Exception {
		String sParentTable = rRel.get("REFERENCED_TABLE_NAME").toString();
		String sParentColumn = rRel.get("REFERENCED_COLUMN_NAME").toString();
		String sChildTable = rRel.get("TABLE_NAME").toString();
		String sChildColumn = rRel.get("COLUMN_NAME").toString();

		addToDocument("load_parent", sLoadParent);
		addToDocument("load_children", sLoadChildren);
		addToDocument("parent_table", sParentTable);
		addToDocument("parent_column", sParentColumn);
		addToDocument("child_table", sChildTable);
		addToDocument("child_column", sChildColumn);

		touchDocuments(sSection);
		parseDocuments(sSection);
	}
	private void processUniqueKeys(RowInterface rTable, RowInterface rColumn) throws Exception {		
		for(Object key : lstUniqueKeys) {
			RowInterface rKey = (RowInterface) key;
			
			if(rKey.get("TABLE_NAME").equals(rTable.get("TABLE_NAME")) && rColumn.get("COLUMN_NAME").equals(rKey.get("COLUMN_NAME"))) {
				if(hsMethods.contains(rTable.get("TABLE_NAME").toString() + "|" + rColumn.get("COLUMN_NAME").toString()))
					continue;
				hsMethods.add(rTable.get("TABLE_NAME").toString() + "|" + rColumn.get("COLUMN_NAME").toString());
				
				addToDocument("table", rTable.get("TABLE_NAME").toString());
				addToDocument("column", rColumn.get("COLUMN_NAME").toString());
				addToDocument("data_type", rColumn.get("DATA_TYPE").toString());
				setDocumentsValue("type_raw", rColumn.get("DATA_TYPE").toString());
				setDocumentsValue("type_java", formatJavaType(rColumn));

				touchDocuments("LOADER");
				parseDocuments("LOADER");
			}
		}
	}

	private String formatTableName(String input) throws Exception {
		return input.toLowerCase();
	}
	private String formatCamelCase(String input) throws Exception {
		if(input.length() <= 1)
			throw new Exception("Cannot process " + input);
		
		String sInput = input.replace("_", " ");
		
		char cLast = ' ';
		String sRet = "";
		for(int cnt = 0; cnt < sInput.length(); cnt++) {
			char cCurrent = sInput.charAt(cnt);
			boolean bIsCurrentValid = Character.isUpperCase(cCurrent) || Character.isLowerCase(cCurrent) || Character.isDigit(cCurrent);
			boolean bIsLastValid = Character.isUpperCase(cLast) || Character.isLowerCase(cLast) || Character.isDigit(cLast);
			boolean bClassChanged = Character.isLowerCase(cLast) != Character.isLowerCase(cCurrent) ||
					Character.isUpperCase(cLast) != Character.isUpperCase(cCurrent) ||
					Character.isDigit(cLast) != Character.isDigit(cCurrent) ||
					bIsCurrentValid != bIsLastValid;
			boolean bChangedToLower = Character.isUpperCase(cLast) && Character.isLowerCase(cCurrent);
			
			if(bIsCurrentValid && bClassChanged && !bChangedToLower )
				sRet += Character.toUpperCase(cCurrent);
			else if(bIsCurrentValid && bClassChanged && bChangedToLower)
				sRet += Character.toLowerCase(cCurrent);
			else if(bIsCurrentValid && !bClassChanged)
				sRet += Character.toLowerCase(cCurrent);
			
			cLast = sInput.charAt(cnt);
		}

		return sRet;
	}
	private String formatAllCaps(String input) throws Exception {
		if(input.length() <= 1)
			throw new Exception("Cannot process " + input);
		
		String sInput = input.replace(" ", "_");
		
		String sRet = "";
		for(int cnt = 0; cnt < sInput.length(); cnt++)
			if(Character.isUpperCase(sInput.charAt(cnt)) || Character.isLowerCase(sInput.charAt(cnt)) || Character.isDigit(sInput.charAt(cnt)) || sInput.charAt(cnt) == '_')
				sRet = sRet + Character.toUpperCase(sInput.charAt(cnt));

		return sRet;
	}
	private String formatAllLowers(String input) throws Exception {
		if(input.length() <= 1)
			throw new Exception("Cannot process " + input);
		
		String sInput = input.replace(" ", "_");
		
		String sRet = "";
		for(int cnt = 0; cnt < sInput.length(); cnt++)
			if(Character.isUpperCase(sInput.charAt(cnt)) || Character.isLowerCase(sInput.charAt(cnt)) || Character.isDigit(sInput.charAt(cnt)) || sInput.charAt(cnt) == '_')
				sRet += Character.toLowerCase(sInput.charAt(cnt));

		return sRet;
	}
	private String formatQuotedString(String input) {
		return "\"" + input.replace("\"", "\\\"") + "\"";
	}
	private String formatJavaType(RowInterface rColumn) throws Exception {
		String sColumnType = (String) rColumn.get("COLUMN_TYPE");
		
		if(sColumnType.contains("char") || sColumnType.contains("text"))
			return "java.lang.String";
		else if(sColumnType.contains("bit(1)"))
			return "java.lang.Boolean";
		else if(sColumnType.contains("bit") || sColumnType.contains("binary") || sColumnType.contains("blob"))
			return "byte[]";
		else if((sColumnType.contains("bigint") && !sColumnType.contains("unsigned")) ||
				(sColumnType.contains("int") && sColumnType.contains("unsigned")))
			return "java.lang.Long";
		else if(sColumnType.contains("bigint") && sColumnType.contains("unsigned"))
			return "java.math.BigInteger";
		else if(sColumnType.contains("tinyint") || sColumnType.contains("smallint)") || sColumnType.contains("mediumint") || sColumnType.contains("int"))
			return "java.lang.Integer";
		else if(sColumnType.contains("decimal"))
			return "java.math.BigDecimal";
		else if(sColumnType.contains("datetime") || sColumnType.contains("timestamp") || sColumnType.contains("date") || sColumnType.contains("year"))
			return "java.util.Date";
		else if(sColumnType.contains("double"))
			return "java.lang.Double";
		
		throw new Exception("Unknown type!");
	}
	private String formatMd5(String value) throws Exception {
		String sLower = value.toLowerCase();
		byte[] arr = MessageDigest.getInstance("MD5").digest(sLower.getBytes("UTF-8"));
		String sRet = "";
		for(int cnt = 0; cnt < arr.length; cnt++)
			sRet += String.format("%02x", arr[cnt]);

		return sRet;
	}

	private void loadDocuments() throws Exception {
		hmDocuments = new HashMap<>();
		InputStream is;
		Template doc;
		
		for(String template : hmFileNames.keySet()) {
			is = getClass().getResourceAsStream(template);
			doc = new Template(is, new CSyntax());
			hmDocuments.put(template, doc);
		}
	}
//	private void loadJsDocuments() throws Exception {
//		hmJsDocuments = new HashMap<>();
//		
//		InputStream is = getClass().getResourceAsStream("inc.accounting.data.js");
//		hmJsDocuments.put("inc.accounting.data.js", new Template(is, new CSyntax()));
//	}

	private void addToDocument(String name, String value) throws Exception {
		setDocumentsValue(name + "_raw", value);
		setDocumentsValue(name + "_tn", formatTableName(value));
		setDocumentsValue(name + "_md5", formatMd5(value));
		setDocumentsValue(name + "_qs", formatQuotedString(value));
		setDocumentsValue(name + "_cc", formatCamelCase(value));
		setDocumentsValue(name + "_ac", formatAllCaps(value));
		setDocumentsValue(name + "_al", formatAllLowers(value));

		setDocumentsValue(name + "_al", formatAllLowers(value));
	}
	private void setDocumentsValue(String name, String value) {
		for(String template : hmFileNames.keySet())
			hmDocuments.get(template).set(name, value);
		
//		for(String template : hmJsDocuments.keySet())
//			hmJsDocuments.get(template).set(name, value);
	}

	private void touchDocuments(String section) {
		for(String template : hmFileNames.keySet())
			hmDocuments.get(template).touch(section);

//		for(String template : hmJsDocuments.keySet())
//			hmJsDocuments.get(template).touch(section);
	}
	private void parseDocuments(String section) throws Exception {
		for(String template : hmFileNames.keySet())
			hmDocuments.get(template).parse(section);

//		for(String template : hmJsDocuments.keySet())
//			hmJsDocuments.get(template).parse(section);
	}
//	private void touchJsDocuments(String section) {
//		for(String template : hmJsDocuments.keySet())
//			hmJsDocuments.get(template).touch(section);
//	}
//	private void parseJsDocuments(String section) throws Exception {
//		for(String template : hmJsDocuments.keySet())
//			hmJsDocuments.get(template).parse(section);
//	}
	
	private void saveDocuments(String TABLE_NAME) throws Exception {
		for(String template : hmFileNames.keySet()) {
			String sFile;
			String sOutputFolder = hmFolders.get(template);
			
			if(sOutputFolder.endsWith(File.separator))
				sFile = sOutputFolder + hmFileNames.get(template).replace("%TABLE%", formatCamelCase(TABLE_NAME));
			else
				sFile = sOutputFolder + File.separator + hmFileNames.get(template).replace("%TABLE%", formatCamelCase(TABLE_NAME));
			File f = new File(sFile);
			if(f.exists())
				f.delete();
			try {
				f.createNewFile();
			}
			catch(Exception ex) {
				System.err.println(ex.toString());
			}

			FileWriter fw = new FileWriter(sFile);
			fw.write(hmDocuments.get(template).generate());
			fw.flush();
			fw.close();
		}
	}
//	private void saveJsDocuments() throws Exception {
//		for(String sFile : hmJsDocuments.keySet()) {
//			File f = new File(sJsOutputFile + sFile);
//			if(f.exists())
//				f.delete();
//			
//			try { f.createNewFile(); }
//			catch(Exception ex) { System.err.println(ex.toString()); }
//
//			FileWriter fw = new FileWriter(sJsOutputFile + sFile);
//			String sContents = hmJsDocuments.get(sFile).generate();
//			fw.write(sContents);
//			fw.flush();
//			fw.close();
//		}
//	}
}
