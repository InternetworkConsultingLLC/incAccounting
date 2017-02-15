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
package net.internetworkconsulting.bootstrap.entities;

import java.util.LinkedList;
import java.util.List;
import net.internetworkconsulting.data.AdapterInterface;
import net.internetworkconsulting.data.Row;

public class Computer extends net.internetworkconsulting.bootstrap.data.ComputersRow {
	private static final long serialVersionUID = 1L;
	public static String SETTING_ENFORCE_COMPUTERS = "Enforce Computers";

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
		this.setIsAllowed(true);
	}
	
	public static List loadSearch(AdapterInterface adapter, List<String> columns, String search) throws Exception { return Row.loadSearch(adapter, Computer.class, columns, search); }
	public static List<String> getSearchColumns() {
		LinkedList<String> lstColumns = new LinkedList<>();
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Computer.DESCRIPTION);
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Computer.MAC_ADDRESS);
		lstColumns.add(net.internetworkconsulting.bootstrap.entities.Computer.IS_ALLOWED);
		return lstColumns;
	}

	public Computer() { super(); }
}
