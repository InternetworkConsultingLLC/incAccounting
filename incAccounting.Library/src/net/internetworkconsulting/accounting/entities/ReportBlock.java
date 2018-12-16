package net.internetworkconsulting.accounting.entities;

import java.util.HashMap;
import java.util.List;
import net.internetworkconsulting.accounting.data.ReportBlocksRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.mysql.Statement;
import net.internetworkconsulting.template.Template;

public class ReportBlock extends ReportBlocksRow {
	public static List<Option> loadOptions(AdapterInterface adapter) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(ReportBlock.class, "ReportBlock.loadOptions.sql"));		
		List<Option> lst = adapter.load(Option.class, stmt, false);

		Option opt = new Option();
		opt.setDisplay("");
		opt.setValue("");

		lst.add(0, opt);
		return lst;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}

	private Object lstChildrenChildren = null;
	public <T extends ReportBlocksRow> List<T> loadChildren(AdapterInterface adapter, Class model, boolean force) throws Exception {
		if(lstChildrenChildren == null || force) {
			String sql = "SELECT * FROM \"%s\" WHERE \"%s\"={PRIMARYKEY} ORDER BY \"%s\"";
			sql = String.format(sql, ReportBlock.TABLE_NAME, ReportBlock.PARENT_BLOCK_GUID, ReportBlock.PRIORITY);
			Statement stmt = new Statement(sql);
			stmt.getParameters().put("{PRIMARYKEY}", this.getGuid());
			lstChildrenChildren = adapter.load(model, stmt, false);
		}
		return (List<T>) lstChildrenChildren;
	}
	public void generate(AdapterInterface adapter, Template document, HashMap<String, String> values) throws Exception {
		// create query and execute
		Statement stmt = new Statement(getSqlQuery());
		for(String key: values.keySet())
			stmt.getParameters().put("{" + key + "}", values.get(key));
		List<Row> lstRows = adapter.load(Row.class, stmt, false);
		
		// for each row
		for(Row row: lstRows) {
			// load isolated hash map
			HashMap<String, String> hmIsolated = new HashMap<>();
			hmIsolated.putAll(values);
			for(String column: row.getColumns().keySet())
				hmIsolated.put(column, Statement.convertObjectToString(row.get(column), null));
			
			// handle children
			List<ReportBlock> lstChildren = loadChildren(adapter, ReportBlock.class, true);
			for(ReportBlock child: lstChildren)
				child.generate(adapter, document, hmIsolated);
			
			// output this row
			for(String key: hmIsolated.keySet())
				document.set(key, hmIsolated.get(key));

			document.touch(getName());
			document.parse(getName());
		}				
	}

	public ReportBlock handleCopy(AdapterInterface adapter) throws Exception {
		ReportBlock objNew = new ReportBlock();
		objNew.initialize();
		for(String key : this.getOriginals().keySet())
			objNew.getChanges().put(key, this.getOriginals().get(key));

		objNew.setGuid(User.newGuid());
		
		List<ReportBlock> lstOldBlocks = this.loadChildren(adapter, ReportBlock.class, false);
		List<ReportBlock> lstNewBlocks = objNew.loadChildren(adapter, ReportBlock.class, false);
		for(ReportBlock block : lstOldBlocks) {
			ReportBlock newBlock = block.handleCopy(adapter);
			newBlock.setParentBlockGuid(objNew.getGuid());
			lstNewBlocks.add(newBlock);
		}
		
		return objNew;
	}
	public void handleSave(AdapterInterface adapter) throws Exception {
		adapter.save(ReportBlock.TABLE_NAME, this);
		
		List<ReportBlock> lstBlocks = this.loadChildren(adapter, ReportBlock.class, false);
		for(ReportBlock block : lstBlocks)
			block.handleSave(adapter);
	}	
	
	public String generateSqlInserts(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "INSERT INTO \"" + TABLE_NAME + "\" (";
		sql += " \"" + GUID + "\", ";
		sql += " \"" + NAME + "\", ";
		sql += " \"" + PARENT_BLOCK_GUID + "\", ";
		sql += " \"" + PRIORITY + "\", ";
		sql += " \"" + REPORTS_GUID + "\", ";
		sql += " \"" + SQL_QUERY + "\" ) VALUES (";
		sql += Statement.convertObjectToSql(this.getGuid()).replace("\n", "\\n") + ", ";
		sql += Statement.convertObjectToSql(this.getName()).replace("\n", "\\n") + ", ";
		sql += Statement.convertObjectToSql(this.getParentBlockGuid()).replace("\n", "\\n") + ", ";
		sql += Statement.convertObjectToSql(this.getPriority()).replace("\n", "\\n") + ", ";
		sql += Statement.convertObjectToSql(this.getReportsGuid()).replace("\n", "\\n") + ", ";
		sql += Statement.convertObjectToSql(this.getSqlQuery()).replace("\n", "\\n") + " );\n";

		List<ReportBlock> lstBlocks = loadChildren(adapter, ReportBlock.class, false);
		for(ReportBlock rb: lstBlocks)
			sql += rb.generateSqlInserts(adapter);
		
		return sql;
	}
	public String generateSqlDeletes(AdapterInterface adapter) throws Exception {
		String sql = "";
		sql += "DELETE FROM \"" + TABLE_NAME + "\" WHERE \"" + GUID + "\"=" + Statement.convertObjectToSql(this.getGuid()) + ";\n";

		List<ReportBlock> lstBlocks = loadChildren(adapter, ReportBlock.class, false);
		for(ReportBlock rb: lstBlocks)
			sql += rb.generateSqlDeletes(adapter);

		return sql;
	}	
}
