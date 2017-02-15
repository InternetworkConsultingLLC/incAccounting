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
 * 
 */
package net.internetworkconsulting.data;

import java.util.HashMap;


public interface RowInterface  {
	public enum RowState { NA, Insert, Update, Delete }

	void initialize() throws Exception;
	
	String getSqlTableName();
	void setSqlTableName(String value);
	
	String getSqlSecurableGuid();
	void setSqlSecurableGuid(String value);
	
	HashMap<String, String> getColumns();
	void setColumns(HashMap<String, String> value);
	
	void setChanges(HashMap<String, Object> value);
	HashMap<String, Object> getChanges();
	void setOriginals(HashMap<String, Object> value);
	HashMap<String, Object> getOriginals();

	RowState getRowState();
	void setIsDeleted(boolean value);
	boolean getIsDeleted();
	void reset();

	boolean set(String name, Object value) throws Exception;
	Object get(String name);
	
	void beforeSave(AdapterInterface adapter) throws Exception;
	void afterSave(AdapterInterface adapter) throws Exception;
}
