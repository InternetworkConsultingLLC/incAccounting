package net.internetworkconsulting.accounting.entities;

import net.internetworkconsulting.accounting.data.ListsRow;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.mysql.Statement;

public class List extends ListsRow {
	public void initialize() throws Exception {
		setGuid(User.newGuid());
	}

	public static <T extends ListsRow> java.util.List<T> loadSearch(AdapterInterface adapter, Class biz, String display_name) throws Exception {
		Statement stmt = new Statement(adapter.getSession().readJar(User.class, "List.loadSearch.sql"));
		stmt.getParameters().put("@DisplayName", display_name);
		java.util.List<T> lst = adapter.load(biz, stmt, true);
		return lst;
	}
	
	public void beforeSave(AdapterInterface adapter) throws Exception {
		Securable sec = null;
				
		if(getRowState() == RowState.Insert) {
			sec = new Securable();
			sec.setGuid(this.getGuid());
		} else if(getRowState() == RowState.Update) {
			sec = this.loadSecurable(adapter, Securable.class, false);
		}		

		if(sec != null) {
			sec.setDisplayName("Lists - " + this.getDisplayName());
			adapter.save(Securable.TABLE_NAME, sec);			
		}
		
		if(getRowState() == RowState.Insert)
			adapter.getSession().canCreate(this.getGuid());
		else if(getRowState() == RowState.Update)
			adapter.getSession().canUpdate(this.getGuid());
		else if(getRowState() == RowState.Delete)
			adapter.getSession().canDelete(this.getGuid());
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
		if(getRowState() == RowState.Delete) {
			Securable sec = this.loadSecurable(adapter, Securable.class, false);
			sec.setIsDeleted(true);
			adapter.save(Securable.TABLE_NAME, sec);			
		}		
	}	
	
}
