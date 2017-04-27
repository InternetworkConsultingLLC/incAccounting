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

import net.internetworkconsulting.data.Row;

public class Option extends Row {
	public Option() { 
		super(); 
		setSqlSecurableGuid("c8276ec6738a4fc0bb0fe7af7815f045");
	}
	public Option(String display, String value) throws Exception {
		super();
		setDisplay(display);
		setValue(value);
	}
	
	public static String DISPLAY = "Display";
	public java.lang.String getDisplay() { return (java.lang.String) get(DISPLAY); }
	public void setDisplay(java.lang.String value) throws Exception { set(DISPLAY, value); }
	
	public static String VALUE = "Value";
	public java.lang.String getValue() { return (String) get(VALUE); }
	public void setValue(java.lang.String value) throws Exception { set(VALUE, value); }	
}
