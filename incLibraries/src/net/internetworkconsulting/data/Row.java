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

import net.internetworkconsulting.data.mysql.Statement;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Row implements RowInterface, Serializable {
	public Row() {
		mapOriginals = new java.util.HashMap<>();
		mapChanges = new java.util.HashMap<>();
	}

	public RowState getRowState() {
		boolean bNew = mapOriginals.isEmpty();
		boolean bChanged = !mapChanges.isEmpty();
		boolean bDeleted = myIsDeleted;

		if(bDeleted && !bNew)
			return RowState.Delete;
		else if(bNew)
			return RowState.Insert;
		else if(bChanged)
			return RowState.Update;

		return RowState.NA;
	}

	private java.util.HashMap<String, Object> mapOriginals;
	public java.util.HashMap<String, Object> getOriginals() {
		return mapOriginals;
	}
	public void setOriginals(java.util.HashMap<String, Object> value) {
		mapOriginals = value;
	}

	private java.util.HashMap<String, Object> mapChanges;
	public java.util.HashMap<String, Object> getChanges() {
		return mapChanges;
	}
	public void setChanges(java.util.HashMap<String, Object> value) {
		mapChanges = value;
	}

	public Object get(String name) {
		if(mapChanges.containsKey(name))
			return mapChanges.get(name);
		else if(mapOriginals.containsKey(name))
			return mapOriginals.get(name);
		else
			return null;
	}
	public boolean set(String name, Object value) throws Exception {
		// original		changes		action
		// !=			!=			add to changes
		// =			!=			remove from changes
		// !=			=			nothing
		// =			=			remove from changes

		Object original = mapOriginals.containsKey(name) ? mapOriginals.get(name) : null;
		Object change = mapChanges.containsKey(name) ? mapChanges.get(name) : null;

		boolean isOriginal =  mapOriginals.containsKey(name) && (
					(original == null && value == null) || (original != null && value != null && original.equals(value))
				);
		boolean isChanged = mapChanges.containsKey(name) && (
					(change == null && value == null) || (change != null && value != null && change.equals(value))
				);

		if(!isOriginal && !isChanged) {
			mapChanges.put(name, value);
			return true;
		} else if(isOriginal && !isChanged) {
			mapChanges.remove(name);
			return true;
		} else if(!isOriginal && isChanged)
			return false;
		else if(isOriginal && isChanged) {
			mapChanges.remove(name);
			return false;
		}

		// since we accouinted for all cases,
		// we should NEVER get here
		return false;
	}

	public static String IS_DELETED = "Is Deleted";
	private boolean myIsDeleted = false;
	public boolean getIsDeleted() {
		return myIsDeleted;
	}
	public void setIsDeleted(boolean value) {
		myIsDeleted = value;
	}

	public void reset() {
		myIsDeleted = false;
		mapChanges = new java.util.HashMap<>();
	}

	private String sTableName;
	public String getSqlTableName() {
		return sTableName;
	}
	public void setSqlTableName(String value) {
		sTableName = value;
	}

	private String sSqlSecurableGuid = null;
	public String getSqlSecurableGuid() {
		return sSqlSecurableGuid;
	}
	public void setSqlSecurableGuid(String value) {
		sSqlSecurableGuid = value;
	}

	HashMap<String, String> hsColumns = new HashMap<String, String>();
	public HashMap<String, String> getColumns() {
		return hsColumns;
	}
	public void setColumns(HashMap<String, String> value) {
		hsColumns = value;
	}

	public static String getReource(Class cls, String file) throws Exception {
		InputStream is = cls.getResourceAsStream(file);
		return Helper.InputStreamToString(is);
	}

	public void beforeSave(AdapterInterface adapter) throws Exception {
	}
	public void afterSave(AdapterInterface adapter) throws Exception {
	}

	public void initialize() throws Exception { }
}
