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
package net.internetworkconsulting.generator.MySQL;

import net.internetworkconsulting.data.mysql.Adapter;
import net.internetworkconsulting.data.AdapterInterface;

public class Main {
	public static void main(String[] args) throws Exception {
		AdapterInterface adapter;		
		adapter = new Adapter("localhost", "ia-incllc", "root", "Welcome123", false);
		Generator gen = new Generator(adapter);
		gen.execute();			
	}	
}
