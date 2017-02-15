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
import java.util.HashMap;
import java.util.List;

public interface AdapterInterface {
	java.sql.Connection getConnection();

	long begin(boolean is_final) throws Exception;
	long rollback(boolean is_final) throws Exception;
	long commit(boolean is_final) throws Exception;

	boolean execute(Statement stmt, boolean log_query) throws Exception;
	<R extends RowInterface> List<R> load(Class<R> cls, StatementInterface stmt) throws Exception;
	<R extends RowInterface> long save(String table_name, List<R> table, boolean before_and_after) throws Exception;
	<R extends RowInterface> long save(String table_name, R row, boolean before_and_after) throws Exception;
	<R extends RowInterface> long save(String table_name, List<R> table) throws Exception;
	<R extends RowInterface> long save(String table_name, R row) throws Exception;

	<R extends RowInterface> HashMap<String, String> getColumns(Class<R> cls, StatementInterface stmt) throws Exception;
		
	void setSession(SessionInterface session);
	SessionInterface getSession();
}
