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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

public class Helper {
	public static String InputStreamToString(InputStream input_stream) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
		
		String sBuffer = br.readLine();
		while(sBuffer != null) {
			sb.append(sBuffer);
			sb.append(System.lineSeparator());
			sBuffer = br.readLine();
		}
		
		return sb.toString();
	}
	public static List<String> InputStreamToStringList(InputStream input_stream) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(input_stream, "UTF-8"));
		List<String> lstRet = new LinkedList<>();
		
		String sBuffer = br.readLine();
		while(sBuffer != null) {
			lstRet.add(sBuffer);
			sBuffer = br.readLine();
		}
		
		return lstRet;
	}
	public static String[] InputStreamToStringArray(InputStream input_stream) throws Exception {
		return InputStreamToStringList(input_stream).toArray(new String[0]);
	}
	
	public static String FileToString(String file_name) throws Exception {
		FileInputStream fis = new FileInputStream(file_name);
		return InputStreamToString(fis);
	}
	
	final private static char[] arrHexChars = "0123456789ABCDEF".toCharArray();
	public static String ByteArrayToHex(byte[] bytes) {
		char[] arrRetChar = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			arrRetChar[j * 2] = arrHexChars[v >>> 4];
			arrRetChar[j * 2 + 1] = arrHexChars[v & 0x0F];
		}
		return new String(arrRetChar);
	}
		
	public static byte[] HexToByteArray(String hex) {
		int len = hex.length();
		byte[] ret = new byte[len / 2];
		for(int cnt = 0; cnt < len; cnt += 2)
			ret[cnt / 2] = (byte) ( (Character.digit(hex.charAt(cnt), 16) << 4) + (Character.digit(hex.charAt(cnt + 1), 16)) );
		return ret;
	}
	public static String Increment(String value) {
		BigInteger bi = new BigInteger(value, 10);
		return bi.add(BigInteger.ONE).toString(10);
	}
}
