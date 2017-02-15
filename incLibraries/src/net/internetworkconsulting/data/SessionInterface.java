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

public interface SessionInterface {
	AdapterInterface getAdapter();

	String getVersion();	

	String getSetting(String key);
	void setSetting(String key, String value);

	void logEvent(String message, String code_guid);
	void logExcpetion(Exception ex, String code_guid);
	void logSql(String sql, String code_guid);
	
	void canCreate(String securable_guid) throws Exception;
	void canDelete(String securable_guid) throws Exception;
	void canRead(String securable_guid) throws Exception;
	void canUpdate(String securable_guid) throws Exception;	

	String readFile(String filename)  throws Exception;
}
