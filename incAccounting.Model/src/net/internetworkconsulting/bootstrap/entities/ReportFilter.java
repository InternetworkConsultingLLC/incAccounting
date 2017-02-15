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
import net.internetworkconsulting.bootstrap.data.ReportFiltersRow;

public class ReportFilter extends ReportFiltersRow {

	public static String DT_TEXT = "Text";
	public static String DT_INTEGER = "Integer";
	public static String DT_DECIMAL = "Decimal";
	public static String DT_DATE = "Date";
	public static String DT_DATETIME = "DateTime";
	public static String DT_ENUM = "Enum";

	public static String VALUE = "Value";
	private String sValue = null;
	public String getValue() { return sValue; }
	public void setValue(String value) { sValue = value; }
	
	public List<Option> getDataTypeOptions() throws Exception {
		LinkedList<Option> lstOptions = new LinkedList<>();
		lstOptions.add(new Option("", ""));
		lstOptions.add(new Option(DT_TEXT, DT_TEXT));
		lstOptions.add(new Option(DT_INTEGER, DT_INTEGER));
		lstOptions.add(new Option(DT_DECIMAL, DT_DECIMAL));
		lstOptions.add(new Option(DT_DATE, DT_DATE));
		lstOptions.add(new Option(DT_DATETIME, DT_DATETIME));
		lstOptions.add(new Option(DT_ENUM, DT_ENUM));
		return lstOptions;
	}

	public void initialize() throws Exception {
		this.setGuid(User.newGuid());
	}
	
	
 }
