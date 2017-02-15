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
package net.internetworkconsulting.template;

import java.io.*;
import java.util.*;

public class Template {

	private Node nRoot;
	public LinkedList<String> History;

	public Template(InputStream input, SyntaxInterface syntax) throws Exception {
		nRoot = new Node(syntax, ReadFile(input));
		History = new LinkedList<String>();
		History.add("CREATED");
	}
	public Template(String input, SyntaxInterface syntax) throws Exception {
		nRoot = new Node(syntax, input);
		History = new LinkedList<String>();
		History.add("CREATED");
	}

	public int set(String name, String value) {
		History.add("SET " + name + " = " + value);
		if(value == null)
			return nRoot.Set(name, "");
		else
			return nRoot.Set(name, value);
	}
	public int parse(String name) throws Exception {
		History.add("PARSE " + name);
		return nRoot.Parse(name);
	}
	public int touch(String name) {
		History.add("TOUCH " + name);
		return nRoot.Touch(name);
	}

	public String generate() throws Exception {
		History.add("GENERATE");
		return nRoot.Generate();
	}
	public String generateHistory() {
		java.io.StringWriter sw = new java.io.StringWriter();

		for(String log: History)
			sw.write(log);

		return sw.toString();
	}

	private String ReadFile(InputStream is) throws Exception {
		StringWriter sw = new StringWriter();
		while(is.available() > 0) {
			byte[] arrBuffer = new byte[1024];
			int iLen = is.read(arrBuffer);
			sw.write(ByteArrToCharArr(arrBuffer), 0, iLen);
		}
		is.close();
		return sw.toString();
	}
	private char[] ByteArrToCharArr(byte[] value) {
		char[] ret = new char[value.length];
		for(int cnt = 0; cnt < value.length; cnt++)
			ret[cnt] = (char) value[cnt];

		return ret;
	}
}
