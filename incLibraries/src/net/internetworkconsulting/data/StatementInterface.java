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
 */
package net.internetworkconsulting.data;

import java.util.HashMap;

public interface StatementInterface {
	void setCommand(String value);
	String getCommand();

	void setParameter(HashMap<String, Object> values);
	HashMap<String, Object> getParameters();
		
	String generate(SessionInterface session, boolean log_query) throws Exception;
}
